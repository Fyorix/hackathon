<script setup lang="ts">
import { ref, onMounted } from "vue";
import { UsersService, CompaniesService } from "../api";
import type { CompanyResponse, UserResponse } from "../api/types";

const loading = ref(false);
const user = ref<UserResponse | null>(null);
const companies = ref<CompanyResponse[]>([]);
const currentCompany = ref<CompanyResponse | null>(null);

// Form fields
const selectedCompanyId = ref<number | null>(null);
const workLat = ref<number | null>(null);
const workLng = ref<number | null>(null);
const workStartTime = ref<string>("09:00:00");
const workEndTime = ref<string>("18:00:00");

const isEditing = ref(false);
const errorMsg = ref("");
const successMsg = ref("");

async function loadData() {
  loading.value = true;
  errorMsg.value = "";
  try {
    // 1. Fetch current user info
    const me = await UsersService.me.get();
    user.value = me;

    // 2. Fetch all companies for the select dropdown
    const companiesList = await CompaniesService.leaderboard({ page: 0, size: 100 });
    companies.value = companiesList;

    if (me?.companyId) {
      selectedCompanyId.value = me.companyId;
      workLat.value = me.workLat || null;
      workLng.value = me.workLng || null;
      if (me.workStartTime) workStartTime.value = String(me.workStartTime);
      if (me.workEndTime) workEndTime.value = String(me.workEndTime);

      // Fetch active company details
      currentCompany.value = await CompaniesService.getById(me.companyId);
    } else {
      isEditing.value = true;
    }
  } catch (err) {
    console.error("Error loading profile data:", err);
    errorMsg.value = "Impossible de charger les données du profil.";
  } finally {
    loading.value = false;
  }
}

// Prefill coordinates with HTML5 Geolocation API
function geolocateUser() {
  if (!navigator.geolocation) {
    errorMsg.value = "La géolocalisation n'est pas supportée par votre navigateur.";
    return;
  }
  navigator.geolocation.getCurrentPosition(
    (position) => {
      workLat.value = Number(position.coords.latitude.toFixed(6));
      workLng.value = Number(position.coords.longitude.toFixed(6));
      successMsg.value = "Position récupérée avec succès !";
      setTimeout(() => (successMsg.value = ""), 3000);
    },
    (err) => {
      console.error(err);
      errorMsg.value = "Impossible de récupérer votre position géographique.";
    }
  );
}

// Save company association
async function handleJoinCompany() {
  if (!selectedCompanyId.value) {
    errorMsg.value = "Veuillez sélectionner une entreprise.";
    return;
  }
  if (workLat.value === null || workLng.value === null) {
    errorMsg.value = "Les coordonnées géographiques du bureau sont requises pour la validation de vos trajets.";
    return;
  }

  loading.value = true;
  errorMsg.value = "";
  successMsg.value = "";

  try {
    const payload = {
      companyId: selectedCompanyId.value,
      workLat: workLat.value,
      workLng: workLng.value,
      workStartTime: workStartTime.value || undefined,
      workEndTime: workEndTime.value || undefined
    };

    await UsersService.joinCompany(payload);
    successMsg.value = "Profil mis à jour avec succès !";
    isEditing.value = false;
    await loadData();
  } catch (err: any) {
    console.error(err);
    errorMsg.value = err.response?.data?.message || "Une erreur est survenue lors de la mise à jour.";
  } finally {
    loading.value = false;
  }
}

function getLogoUrl(logoPath?: string) {
  if (!logoPath) return "";
  const filename = logoPath.split("/").pop() || logoPath;
  try {
    return new URL(`../assets/companies/${filename}`, import.meta.url).href;
  } catch (e) {
    console.error("Error resolving logo URL:", e);
    return "";
  }
}

onMounted(() => {
  loadData();
});
</script>

<template>
  <div class="min-h-screen bg-slate-50 p-6 md:p-10 text-slate-800">
    <div class="max-w-4xl mx-auto">
      <h1 class="mb-8 text-center text-4xl font-bold text-[var(--color-primblue)]">
        Mon Profil
      </h1>

      <!-- Loading State -->
      <div v-if="loading && !user" class="flex justify-center items-center py-20">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-[var(--color-primblue)]"></div>
      </div>

      <div v-else class="space-y-8">
        <!-- Profile Overview Card -->
        <div class="rounded-2xl bg-white p-8 shadow relative overflow-hidden">
          <div class="absolute right-0 top-0 w-32 h-32 bg-[var(--color-primgreen)]/5 rounded-full blur-2xl"></div>
          <div class="flex flex-col md:flex-row items-center gap-6">
            <div class="w-20 h-20 rounded-full bg-[var(--color-primblue)] text-white flex items-center justify-center text-3xl font-bold shadow-md">
              {{ user?.name?.charAt(0).toUpperCase() || 'U' }}
            </div>
            <div class="text-center md:text-left flex-grow">
              <h2 class="text-2xl font-bold text-[var(--color-primblue)]">{{ user?.name }}</h2>
              <p class="text-slate-500 font-medium">{{ user?.email }}</p>
              <div class="mt-2 inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-bold bg-slate-100 text-slate-600">
                👤 Rôle : {{ user?.role }}
              </div>
            </div>

            <!-- Stats (matches HomePage) -->
            <div class="grid grid-cols-2 gap-4 w-full md:w-auto">
              <div class="bg-slate-50 p-4 rounded-xl text-center shadow-sm">
                <span class="text-xs text-gray-500 block mb-1">🌿 Points Carbone</span>
                <span class="text-2xl font-black text-[var(--color-primgreen)]">{{ user?.carbonPointsBalance || 0 }}</span>
              </div>
              <div class="bg-slate-50 p-4 rounded-xl text-center shadow-sm">
                <span class="text-xs text-gray-500 block mb-1">🌍 CO₂ Évité</span>
                <span class="text-2xl font-black text-green-600">{{ user?.totalCo2Saved || 0 }} kg</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Alert messages -->
        <div v-if="errorMsg" class="p-4 rounded-xl bg-red-50 text-red-700 border border-red-200 text-sm font-semibold shadow-sm">
          ⚠️ {{ errorMsg }}
        </div>
        <div v-if="successMsg" class="p-4 rounded-xl bg-green-50 text-[var(--color-primgreen)] border border-green-200 text-sm font-semibold shadow-sm">
          ✅ {{ successMsg }}
        </div>

        <!-- Company details state (When not editing and has company) -->
        <div v-if="!isEditing && currentCompany" class="rounded-2xl bg-white p-8 shadow space-y-6">
          <div class="flex justify-between items-center border-b border-slate-100 pb-4">
            <h3 class="text-xl font-bold text-[var(--color-primblue)]">Mon Entreprise</h3>
            <button 
              @click="isEditing = true"
              class="px-4 py-2 text-sm font-bold text-[var(--color-primblue)] hover:text-white border-2 border-[var(--color-primblue)] hover:bg-[var(--color-primblue)] rounded-xl transition duration-200 cursor-pointer"
            >
              Changer d'entreprise
            </button>
          </div>

          <div class="flex flex-col md:flex-row gap-8 items-start md:items-center">
            <!-- Company Info -->
            <div class="flex items-center gap-4 flex-grow">
              <div class="w-16 h-16 rounded-xl bg-slate-100 p-2 flex items-center justify-center border border-slate-200 shadow-inner flex-shrink-0">
                <img 
                  v-if="currentCompany.logoPath" 
                  :src="getLogoUrl(currentCompany.logoPath)" 
                  class="max-w-full max-h-full object-contain"
                  alt="Logo"
                />
                <span v-else class="text-2xl font-bold text-slate-400">🏢</span>
              </div>
              <div>
                <h4 class="text-xl font-bold text-slate-900">{{ currentCompany.name }}</h4>
                <p class="text-sm text-slate-400 font-mono">SIREN: {{ currentCompany.sirenNumber }}</p>
                <p class="text-xs text-slate-500 font-medium mt-1">👥 {{ currentCompany.totalEmployees || 1 }} employés actifs</p>
              </div>
            </div>

            <!-- Commute Settings Summary -->
            <div class="w-full md:w-80 bg-slate-50 p-6 rounded-2xl shadow-sm border border-slate-100 space-y-3 text-sm">
              <h5 class="font-bold text-slate-700 border-b border-slate-200 pb-2">📍 Paramètres de trajet</h5>
              <div class="flex justify-between">
                <span class="text-slate-500">Latitude bureau:</span>
                <span class="font-mono font-semibold">{{ workLat }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-slate-500">Longitude bureau:</span>
                <span class="font-mono font-semibold">{{ workLng }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-slate-500">Début journée:</span>
                <span class="font-semibold">{{ user?.workStartTime || 'Non défini' }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-slate-500">Fin journée:</span>
                <span class="font-semibold">{{ user?.workEndTime || 'Non défini' }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Join / Edit Form -->
        <div v-else class="rounded-2xl bg-white p-8 shadow space-y-6">
          <div class="flex justify-between items-center border-b border-slate-100 pb-4">
            <h3 class="text-xl font-bold text-[var(--color-primblue)]">
              {{ currentCompany ? "Changer d'Entreprise" : "Rejoindre une Entreprise" }}
            </h3>
            <button 
              v-if="currentCompany"
              @click="isEditing = false"
              class="text-sm text-slate-500 hover:text-slate-700 font-bold"
            >
              Annuler
            </button>
          </div>

          <form @submit.prevent="handleJoinCompany" class="space-y-6">
            <!-- Company Selection -->
            <div>
              <label class="block text-sm font-semibold text-slate-600 mb-2">Sélectionnez votre entreprise</label>
              <select 
                v-model="selectedCompanyId" 
                class="w-full border border-slate-300 bg-white rounded-xl px-4 py-3 text-slate-800 focus:outline-none focus:ring-2 focus:ring-[var(--color-primblue)] focus:border-transparent transition"
                required
              >
                <option :value="null" disabled>Choisir une entreprise...</option>
                <option v-for="comp in companies" :key="comp.id" :value="comp.id">
                  {{ comp.name }} (SIREN: {{ comp.sirenNumber }})
                </option>
              </select>
            </div>

            <!-- Coordinates Settings -->
            <div class="space-y-4">
              <div class="flex justify-between items-center">
                <label class="block text-sm font-semibold text-slate-600">Localisation précise de votre bureau</label>
                <button 
                  type="button"
                  @click="geolocateUser"
                  class="inline-flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-slate-100 hover:bg-slate-200 text-slate-700 text-xs font-bold transition cursor-pointer"
                >
                  📍 Me géolocaliser
                </button>
              </div>

              <div class="grid grid-cols-2 gap-4">
                <div>
                  <span class="text-xs text-slate-400 block mb-1">Latitude</span>
                  <input 
                    v-model.number="workLat" 
                    type="number" 
                    step="0.000001" 
                    placeholder="ex: 48.8584"
                    class="w-full border border-slate-300 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-[var(--color-primblue)] transition"
                    required
                  />
                </div>
                <div>
                  <span class="text-xs text-slate-400 block mb-1">Longitude</span>
                  <input 
                    v-model.number="workLng" 
                    type="number" 
                    step="0.000001" 
                    placeholder="ex: 2.2945"
                    class="w-full border border-slate-300 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-[var(--color-primblue)] transition"
                    required
                  />
                </div>
              </div>
              <p class="text-xs text-slate-400 italic">
                * Ces coordonnées sont obligatoires. Vos trajets déclarés devront commencer ou se terminer à moins de 500m de ce point.
              </p>
            </div>

            <!-- Working Hours (Optional) -->
            <div class="grid grid-cols-2 gap-4 border-t border-slate-100 pt-4">
              <div>
                <label class="block text-sm font-semibold text-slate-600 mb-2">Début journée (travail)</label>
                <input 
                  v-model="workStartTime" 
                  type="time" 
                  step="1"
                  class="w-full border border-slate-300 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-[var(--color-primblue)] transition"
                />
              </div>
              <div>
                <label class="block text-sm font-semibold text-slate-600 mb-2">Fin journée (travail)</label>
                <input 
                  v-model="workEndTime" 
                  type="time" 
                  step="1"
                  class="w-full border border-slate-300 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-[var(--color-primblue)] transition"
                />
              </div>
            </div>

            <!-- Submit -->
            <div class="pt-4">
              <button 
                type="submit" 
                :disabled="loading"
                class="w-full bg-[var(--color-primgreen)] hover:opacity-90 text-white font-bold py-3.5 px-6 rounded-xl shadow-lg transition duration-200 disabled:opacity-50 cursor-pointer"
              >
                {{ loading ? 'Enregistrement...' : "Confirmer et rejoindre l'entreprise" }}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>
