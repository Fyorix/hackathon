<script setup lang="ts">
import { ref, onMounted, computed } from "vue";
import { TripsService, StravaService } from "../api";
import type { StravaCommuteCandidateResponse } from "../api/types";

interface TransportMode {
  label: string;
  value: string;
}

const transportModes: TransportMode[] = [
  {
    label: "Vélo",
    value: "BIKE",
  },
  {
    label: "Vélo électrique",
    value: "ELECTRIC_BIKE",
  },
  {
    label: "Marche",
    value: "WALK",
  },
  {
    label: "Autre transport 0 émission",
    value: "OTHER_ZERO_EMISSION",
  },
];

// Tabs State
const activeTab = ref<"manual" | "strava">("manual");

// Common State
const loading = ref(false);
const errorMsg = ref("");
const successMsg = ref("");

// Manual Declaration Form State
const distance = ref<number | null>(null);
const transport = ref<string>(transportModes[0].value);
const date = ref<string>(new Date().toISOString().split("T")[0]);

// Strava Integration State
const isStravaLinked = ref(false);
const isLocationMissing = ref(false);
const loadingStrava = ref(false);
const candidates = ref<StravaCommuteCandidateResponse[]>([]);
const selectedActivityIds = ref<number[]>([]);
const importing = ref(false);

// Submit manual trip declaration
async function submitManual() {
  if (distance.value === null || distance.value <= 0) {
    errorMsg.value = "La distance doit être strictement supérieure à 0.";
    return;
  }
  loading.value = true;
  errorMsg.value = "";
  successMsg.value = "";

  try {
    await TripsService.create({
      distanceKm: distance.value,
      type: transport.value as any,
    });
    successMsg.value = "Votre trajet a été déclaré avec succès !";
    distance.value = null;
  } catch (err: any) {
    console.error("Error creating trip:", err);
    errorMsg.value = err.message || "Une erreur est survenue lors de la déclaration.";
  } finally {
    loading.value = false;
  }
}

// Fetch Strava candidates
async function fetchCandidates() {
  loadingStrava.value = true;
  isLocationMissing.value = false;
  try {
    const res = await StravaService.getCommuteCandidates();
    candidates.value = res || [];
    isStravaLinked.value = true;
  } catch (err: any) {
    const status = err.status;
    if (status === 409) {
      isStravaLinked.value = false;
    } else if (status === 400) {
      // Coordinates missing
      isStravaLinked.value = true;
      isLocationMissing.value = true;
    } else {
      isStravaLinked.value = false;
    }
  } finally {
    loadingStrava.value = false;
  }
}

// Import selected Strava activities
async function importSelected() {
  if (selectedActivityIds.value.length === 0) return;
  importing.value = true;
  errorMsg.value = "";
  successMsg.value = "";

  try {
    const res = await StravaService.importCommuteCandidates({
      stravaActivityIds: selectedActivityIds.value,
    });
    successMsg.value = `${res.importedCount} trajet(s) importé(s) avec succès !` + 
      (res.skippedCount > 0 ? ` (${res.skippedCount} ignoré(s) car hors-limite journalière)` : "");
    selectedActivityIds.value = [];
    await fetchCandidates();
  } catch (err: any) {
    console.error("Error importing Strava activities:", err);
    errorMsg.value = err.message || "Une erreur est survenue lors de l'importation.";
  } finally {
    importing.value = false;
  }
}

// Selection helpers
const allSelected = computed({
  get() {
    return candidates.value.length > 0 && selectedActivityIds.value.length === candidates.value.length;
  },
  set(value) {
    if (value) {
      selectedActivityIds.value = candidates.value.map(c => c.stravaActivityId);
    } else {
      selectedActivityIds.value = [];
    }
  }
});

function toggleSelectAll() {
  allSelected.value = !allSelected.value;
}

// Formatters & helpers
function formatDate(dateStr: string) {
  if (!dateStr) return "";
  const d = new Date(dateStr);
  return d.toLocaleDateString("fr-FR", {
    day: "2-digit",
    month: "short",
    hour: "2-digit",
    minute: "2-digit",
  });
}

// Map activity type to human label
function getTransportLabel(type: string) {
  if (type === "Ride") return "Vélo";
  if (type === "Run") return "Course";
  if (type === "Walk") return "Marche";
  return type;
}

function getTransportBadgeClass(type: string) {
  if (type === "Ride") return "bg-sky-50 text-sky-600 border border-sky-100";
  if (type === "Run") return "bg-orange-50 text-orange-600 border border-orange-100";
  if (type === "Walk") return "bg-emerald-50 text-emerald-600 border border-emerald-100";
  return "bg-slate-50 text-slate-600 border border-slate-100";
}

// Watch activeTab to load Strava candidates automatically
function handleTabChange(tab: "manual" | "strava") {
  activeTab.value = tab;
  errorMsg.value = "";
  successMsg.value = "";
  if (tab === "strava") {
    fetchCandidates();
  }
}

onMounted(() => {
  // If redirected back from Strava OAuth redirect, pre-activate Strava tab
  const params = new URLSearchParams(window.location.search);
  if (params.has("strava")) {
    handleTabChange("strava");
  }
});
</script>

<template>
  <div class="flex justify-center p-6 md:p-10 text-slate-800">
    <div class="w-full max-w-xl rounded-2xl bg-white p-8 shadow-lg space-y-6">
      <h1 class="text-3xl font-extrabold text-[var(--color-primblue)]">
        Déclarer un trajet
      </h1>

      <!-- Navigation Tabs -->
      <div class="flex border-b border-slate-200">
        <button 
          @click="handleTabChange('manual')" 
          class="flex-1 py-3 text-center font-bold text-sm border-b-2 transition duration-200 cursor-pointer"
          :class="activeTab === 'manual' ? 'border-[var(--color-primgreen)] text-[var(--color-primgreen)]' : 'border-transparent text-slate-500 hover:text-slate-700'"
        >
          ✍️ Déclaration Manuelle
        </button>
        <button 
          @click="handleTabChange('strava')" 
          class="flex-1 py-3 text-center font-bold text-sm border-b-2 transition duration-200 cursor-pointer"
          :class="activeTab === 'strava' ? 'border-[var(--color-primgreen)] text-[var(--color-primgreen)]' : 'border-transparent text-slate-500 hover:text-slate-700'"
        >
          🟠 Synchronisation Strava
        </button>
      </div>

      <!-- Alert Banners -->
      <div v-if="errorMsg" class="p-4 rounded-xl bg-red-50 text-red-700 border border-red-200 text-sm font-semibold">
        ⚠️ {{ errorMsg }}
      </div>
      <div v-if="successMsg" class="p-4 rounded-xl bg-green-50 text-green-800 border border-green-200 text-sm font-semibold">
        ✅ {{ successMsg }}
      </div>

      <!-- Tab Content: Manual -->
      <div v-if="activeTab === 'manual'">
        <form class="space-y-6" @submit.prevent="submitManual">
          <!-- Distance -->
          <div>
            <label class="mb-2 block font-semibold text-slate-700 text-sm">
              Nombre de kilomètres
            </label>
            <input
              v-model="distance"
              type="number"
              min="0"
              step="0.1"
              required
              placeholder="Ex : 12.5"
              class="w-full rounded-xl border border-slate-300 p-3 focus:border-[var(--color-primblue)] focus:ring-2 focus:ring-[var(--color-primblue)]/10 focus:outline-none transition"
            />
          </div>

          <!-- Moyen de transport -->
          <div>
            <label class="mb-2 block font-semibold text-slate-700 text-sm">
              Moyen de transport
            </label>
            <select
              v-model="transport"
              class="w-full rounded-xl border border-slate-300 p-3 focus:border-[var(--color-primblue)] focus:ring-2 focus:ring-[var(--color-primblue)]/10 focus:outline-none transition"
            >
              <option
                v-for="mode in transportModes"
                :key="mode.value"
                :value="mode.value"
              >
                {{ mode.label }}
              </option>
            </select>
          </div>

          <!-- Date -->
          <div>
            <label class="mb-2 block font-semibold text-slate-700 text-sm">
              Date du trajet
            </label>
            <input
              v-model="date"
              type="date"
              required
              class="w-full rounded-xl border border-slate-300 p-3 focus:border-[var(--color-primblue)] focus:ring-2 focus:ring-[var(--color-primblue)]/10 focus:outline-none transition"
            />
          </div>

          <div class="pt-4">
            <button
              type="submit"
              :disabled="loading"
              class="w-full rounded-xl bg-[var(--color-primgreen)] py-3.5 font-bold text-white transition hover:opacity-90 shadow-md cursor-pointer disabled:opacity-50"
            >
              {{ loading ? 'Enregistrement...' : 'Déclarer mon trajet' }}
            </button>
          </div>
        </form>
      </div>

      <!-- Tab Content: Strava -->
      <div v-else class="space-y-6">
        <!-- Loading State -->
        <div v-if="loadingStrava" class="flex flex-col items-center justify-center py-12">
          <div class="animate-spin rounded-full h-10 w-10 border-b-2 border-[#FC5200]"></div>
          <span class="text-slate-400 text-sm mt-3 font-medium">Recherche d'activités éligibles...</span>
        </div>

        <div v-else>
          <!-- Not Linked State -->
          <div v-if="!isStravaLinked" class="text-center py-8 space-y-4">
            <div class="text-5xl">🟠</div>
            <h3 class="text-lg font-bold text-slate-800">Compte Strava non connecté</h3>
            <p class="text-slate-500 text-sm max-w-sm mx-auto">
              Pour importer automatiquement vos activités de trajet, vous devez associer votre compte Strava depuis vos paramètres.
            </p>
            <router-link 
              to="/profile" 
              class="inline-block bg-[#FC5200] hover:bg-[#e04900] text-white font-bold px-6 py-3 rounded-xl shadow-md transition duration-200"
            >
              Associer mon compte Strava
            </router-link>
          </div>

          <!-- Location Missing State -->
          <div v-else-if="isLocationMissing" class="text-center py-8 space-y-4">
            <div class="text-5xl">🏢</div>
            <h3 class="text-lg font-bold text-slate-800">Coordonnées professionnelles manquantes</h3>
            <p class="text-slate-500 text-sm max-w-sm mx-auto">
              Pour valider l'éligibilité de vos trajets Strava (moins de 500m de votre bureau), vous devez renseigner la localisation de votre entreprise dans votre Profil.
            </p>
            <router-link 
              to="/profile" 
              class="inline-block bg-[var(--color-primblue)] hover:opacity-90 text-white font-bold px-6 py-3 rounded-xl shadow-md transition duration-200"
            >
              Définir ma position de bureau
            </router-link>
          </div>

          <!-- Linked State with Candidates -->
          <div v-else class="space-y-6">
            <!-- Empty Candidates State -->
            <div v-if="candidates.length === 0" class="text-center py-8 space-y-2">
              <div class="text-4xl">🚲</div>
              <h3 class="text-base font-bold text-slate-700">Aucun trajet éligible trouvé</h3>
              <p class="text-slate-500 text-xs max-w-xs mx-auto">
                Nous n'avons détecté aucune activité récente débutant ou se terminant dans un rayon de 500m autour de votre bureau.
              </p>
              <button 
                @click="fetchCandidates" 
                class="mt-2 text-xs font-semibold text-[var(--color-primblue)] hover:underline cursor-pointer"
              >
                🔄 Rafraîchir les activités
              </button>
            </div>

            <!-- List candidates to import -->
            <div v-else class="space-y-4">
              <div class="flex items-center justify-between text-xs text-slate-400 font-bold border-b border-slate-100 pb-2">
                <span>{{ candidates.length }} activité(s) disponible(s)</span>
                <button @click="toggleSelectAll" class="text-[var(--color-primblue)] hover:underline cursor-pointer">
                  {{ allSelected ? "Tout décocher" : "Tout cocher" }}
                </button>
              </div>

              <!-- Candidates Box List -->
              <div class="space-y-3 max-h-80 overflow-y-auto pr-1">
                <div 
                  v-for="act in candidates" 
                  :key="act.stravaActivityId" 
                  class="flex items-center gap-4 p-4 border border-slate-100 rounded-xl hover:bg-slate-50/50 transition"
                >
                  <input 
                    type="checkbox" 
                    :id="'act-' + act.stravaActivityId" 
                    :value="act.stravaActivityId" 
                    v-model="selectedActivityIds"
                    class="w-5 h-5 text-[var(--color-primgreen)] border-slate-300 rounded focus:ring-[var(--color-primgreen)] cursor-pointer"
                  />
                  <label :for="'act-' + act.stravaActivityId" class="flex-grow cursor-pointer select-none">
                    <div class="flex items-center justify-between">
                      <span class="font-bold text-slate-800 text-sm truncate max-w-[220px]">{{ act.name }}</span>
                      <span class="px-2 py-0.5 rounded-full text-[10px] font-bold tracking-wide uppercase" :class="getTransportBadgeClass(act.type)">
                        {{ getTransportLabel(act.type) }}
                      </span>
                    </div>
                    <div class="flex gap-4 text-xs text-slate-400 mt-1">
                      <span>📏 {{ act.distanceKm.toFixed(1) }} km</span>
                      <span>📅 {{ formatDate(act.startDate) }}</span>
                    </div>
                  </label>
                </div>
              </div>

              <!-- Action buttons -->
              <div class="pt-4 space-y-3">
                <button 
                  @click="importSelected"
                  :disabled="selectedActivityIds.length === 0 || importing"
                  class="w-full bg-[var(--color-primgreen)] py-3.5 font-bold text-white transition hover:opacity-90 rounded-xl disabled:opacity-50 cursor-pointer flex items-center justify-center gap-2 shadow-md"
                >
                  <span v-if="importing" class="animate-spin rounded-full h-4 w-4 border-b-2 border-white"></span>
                  <span>Importer la sélection ({{ selectedActivityIds.length }})</span>
                </button>
                <button 
                  @click="fetchCandidates" 
                  :disabled="importing"
                  class="w-full border border-slate-200 hover:bg-slate-50 py-3 text-sm font-semibold text-slate-600 rounded-xl transition cursor-pointer"
                >
                  🔄 Rafraîchir
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>