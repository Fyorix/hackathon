<script setup lang="ts">
import { ref, onMounted, computed } from "vue";
import { StravaService } from "../api";
import type { StravaCommuteCandidateResponse } from "../api/types";

// Common State
const errorMsg = ref("");
const successMsg = ref("");

// Strava Integration State
const isStravaLinked = ref(false);
const isLocationMissing = ref(false);
const loadingStrava = ref(false);
const candidates = ref<StravaCommuteCandidateResponse[]>([]);
const selectedActivityIds = ref<number[]>([]);
const importing = ref(false);

// Fetch Strava candidates
async function fetchCandidates() {
  loadingStrava.value = true;
  isLocationMissing.value = false;
  errorMsg.value = "";
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
    const importedCount = res.imported?.length || 0;
    const skippedCount = res.skipped?.length || 0;
    successMsg.value = `${importedCount} trajet(s) importé(s) avec succès !` + 
      (skippedCount > 0 ? ` (${skippedCount} ignoré(s) car hors-limite ou déjà importé(s))` : "");
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

onMounted(() => {
  fetchCandidates();
});
</script>

<template>
  <div class="flex justify-center p-6 md:p-10 text-slate-800">
    <div class="w-full max-w-xl rounded-2xl bg-white p-8 shadow-lg space-y-6">
      <h1 class="text-3xl font-extrabold text-[var(--color-primblue)]">
        Déclarer un trajet
      </h1>

      <!-- Subtitle/context -->
      <p class="text-sm text-slate-500 font-light leading-relaxed">
        Synchronisez vos trajets domicile-travail directement à partir de vos activités Strava récentes.
      </p>

      <!-- Alert Banners -->
      <div v-if="errorMsg" class="p-4 rounded-xl bg-red-50 text-red-700 border border-red-200 text-sm font-semibold">
        <i class="pi pi-exclamation-triangle"></i> {{ errorMsg }}
      </div>
      <div v-if="successMsg" class="p-4 rounded-xl bg-green-50 text-green-800 border border-green-200 text-sm font-semibold">
        <i class="pi pi-check-circle"></i> {{ successMsg }}
      </div>

      <!-- Strava content -->
      <div class="space-y-6">
        <!-- Loading State -->
        <div v-if="loadingStrava" class="flex flex-col items-center justify-center py-12">
          <div class="animate-spin rounded-full h-10 w-10 border-b-2 border-[#FC5200]"></div>
          <span class="text-slate-400 text-sm mt-3 font-medium">Recherche d'activités éligibles...</span>
        </div>

        <div v-else>
          <!-- Not Linked State -->
          <div v-if="!isStravaLinked" class="text-center py-8 space-y-4">
            <div class="text-5xl text-[#FC5200]"><i class="pi pi-link"></i></div>
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
            <div class="text-5xl text-[var(--color-primblue)]"><i class="pi pi-building"></i></div>
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
              <div class="text-4xl text-[var(--color-primgreen)]"><i class="pi pi-directions"></i></div>
              <h3 class="text-base font-bold text-slate-700">Aucun trajet éligible trouvé</h3>
              <p class="text-slate-500 text-xs max-w-xs mx-auto">
                Nous n'avons détecté aucune activité récente débutant ou se terminant dans un rayon de 500m autour de votre bureau.
              </p>
              <button 
                @click="fetchCandidates" 
                class="mt-2 text-xs font-semibold text-[var(--color-primblue)] hover:underline cursor-pointer"
              >
<i class="pi pi-refresh"></i> Rafraîchir les activités
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
                      <span><i class="pi pi-arrows-h"></i> {{ act.distanceKm.toFixed(1) }} km</span>
                      <span><i class="pi pi-calendar"></i> {{ formatDate(act.startDate) }}</span>
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
                  <i class="pi pi-refresh"></i> Rafraîchir
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>