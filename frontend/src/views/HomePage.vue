<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { UsersService } from "../api";

const carbonPoints = ref(0);
const kilometers = ref(0);
const co2Saved = ref(0);

// Equivalences approximatives pour rendre l'impact concret
const treesEquivalent = computed(() => Math.round(co2Saved.value / 25));
const carTripsAvoided = computed(() => Math.round(kilometers.value / 12));

const quickActions = [
  { to: "/declare", label: "Déclarer un trajet", desc: "Importez vos activités Strava éligibles", icon: "pi pi-plus-circle", color: "var(--color-primgreen)" },
  { to: "/leaderboard", label: "Classement", desc: "Comparez votre impact à celui des autres", icon: "pi pi-trophy", color: "#f59e0b" },
  { to: "/profile", label: "Mon Profil", desc: "Gérez votre entreprise et vos paramètres", icon: "pi pi-user", color: "var(--color-primblue)" },
];

const tips = [
  { icon: "pi pi-directions", text: "Le vélo sur moins de 5 km est souvent plus rapide que la voiture en ville." },
  { icon: "pi pi-directions", text: "Pensez à renseigner vos horaires de bureau pour affiner l'éligibilité de vos trajets." },
  { icon: "pi pi-sparkles", text: "Chaque trajet déclaré convertit vos km économisés en points carbone." },
];

async function getMyInfo(){
  try {
    const me = await UsersService.me.get();
    if (me) {
      carbonPoints.value = me.carbonPointsBalance || 0;
      co2Saved.value = me.totalCo2Saved || 0;
      kilometers.value = me.totalKm || 0;
    }
  } catch (err) {
    console.error("Could not fetch user stats:", err);
  }
}

onMounted(() => {
  getMyInfo();
});
</script>

<template>
  <div class="min-h-screen bg-slate-50 p-10">
    <h1 class="mb-8 text-center text-4xl font-bold text-[var(--color-primblue)]">
      Tableau de bord
    </h1>

    <div class="mx-auto max-w-5xl space-y-16">
      <!-- Statistiques -->
      <section class="grid gap-6 md:grid-cols-3">
        <div class="rounded-2xl bg-white p-8 shadow">
          <p class="text-gray-500"><i class="pi pi-sparkles"></i> Solde de points carbone</p>

          <p class="mt-4 text-5xl font-bold text-[var(--color-primgreen)]">
            {{ carbonPoints }}
          </p>

          <p class="mt-2 text-sm text-gray-400">
            points
          </p>
        </div>

        <div class="rounded-2xl bg-white p-8 shadow">
          <p class="text-gray-500"><i class="pi pi-directions"></i> Kilomètres cumulés</p>

          <p class="mt-4 text-5xl font-bold text-[var(--color-primblue)]">
            {{ kilometers.toLocaleString('fr-FR', { maximumFractionDigits: 2 }) }}
          </p>

          <p class="mt-2 text-sm text-gray-400">
            km
          </p>
        </div>

        <div class="rounded-2xl bg-white p-8 shadow">
          <p class="text-gray-500"><i class="pi pi-globe"></i> CO₂ évité</p>

          <p class="mt-4 text-5xl font-bold text-[var(--color-primgreen)]">
            {{ co2Saved.toLocaleString('fr-FR', { maximumFractionDigits: 2 }) }}
          </p>

          <p class="mt-2 text-sm text-gray-400">
            kg
          </p>
        </div>
      </section>

      <!-- Impact -->
      <section class="rounded-2xl bg-white p-8 shadow">
        <h2 class="mb-6 text-lg font-bold text-slate-800">Votre impact en clair</h2>
        <div class="grid gap-6 sm:grid-cols-2">
          <div class="flex items-center gap-4 rounded-xl bg-slate-50 p-5">
            <div class="flex h-14 w-14 flex-shrink-0 items-center justify-center rounded-full bg-[var(--color-primgreen)]/10 text-2xl text-[var(--color-primgreen)]">
              <i class="pi pi-globe"></i>
            </div>
            <p class="text-sm text-slate-600">
              Équivalent à <span class="font-bold text-[var(--color-primgreen)]">{{ treesEquivalent }}</span> arbres plantés sur une année.
            </p>
          </div>
          <div class="flex items-center gap-4 rounded-xl bg-slate-50 p-5">
            <div class="flex h-14 w-14 flex-shrink-0 items-center justify-center rounded-full bg-[var(--color-primblue)]/10 text-2xl text-[var(--color-primblue)]">
              <i class="pi pi-car"></i>
            </div>
            <p class="text-sm text-slate-600">
              Environ <span class="font-bold text-[var(--color-primblue)]">{{ carTripsAvoided }}</span> trajets en voiture évités.
            </p>
          </div>
        </div>
      </section>

      <!-- Actions -->
      <section>
        <h2 class="mb-6 text-lg font-bold text-slate-800">Actions rapides</h2>
        <div class="grid gap-6 md:grid-cols-3">
          <router-link
            v-for="action in quickActions"
            :key="action.to"
            :to="action.to"
            class="group rounded-2xl bg-white p-6 shadow transition duration-200 hover:-translate-y-1 hover:shadow-lg"
          >
            <div
              class="mb-4 flex h-12 w-12 items-center justify-center rounded-xl text-xl text-white"
              :style="{ backgroundColor: action.color }"
            >
              <i :class="action.icon"></i>
            </div>
            <h3 class="font-bold text-slate-800 group-hover:text-[var(--color-primblue)]">{{ action.label }}</h3>
            <p class="mt-1 text-sm text-slate-500">{{ action.desc }}</p>
          </router-link>
        </div>
      </section>

      <!-- Astuces -->
      <section class="rounded-2xl bg-white p-8 shadow">
        <h2 class="mb-6 text-lg font-bold text-slate-800">Astuces</h2>
        <ul class="space-y-4">
          <li v-for="tip in tips" :key="tip.text" class="flex items-start gap-3 text-sm text-slate-600">
            <i :class="tip.icon" class="mt-0.5 text-[var(--color-primgreen)]"></i>
            <span>{{ tip.text }}</span>
          </li>
        </ul>
      </section>
    </div>
  </div>
</template>