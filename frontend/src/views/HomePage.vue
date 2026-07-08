<script setup lang="ts">
import { ref, onMounted } from "vue";
import { UsersService } from "../api";

const carbonPoints = ref(0);
const kilometers = ref(0);
const co2Saved = ref(0);

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

    <!-- Statistiques -->
    <div class="mx-auto grid  gap-6 md:grid-cols-3">
      <div class="rounded-2xl bg-white p-8 shadow">
        <p class="text-gray-500">🌿 Solde de points carbone</p>

        <p class="mt-4 text-5xl font-bold text-[var(--color-primgreen)]">
          {{ carbonPoints }}
        </p>

        <p class="mt-2 text-sm text-gray-400">
          points
        </p>
      </div>

      <div class="rounded-2xl bg-white p-8 shadow">
        <p class="text-gray-500">🚲 Kilomètres cumulés</p>

        <p class="mt-4 text-5xl font-bold text-[var(--color-primblue)]">
          {{ kilometers.toLocaleString('fr-FR', { maximumFractionDigits: 2 }) }}
        </p>

        <p class="mt-2 text-sm text-gray-400">
          km
        </p>
      </div>

      <div class="rounded-2xl bg-white p-8 shadow">
        <p class="text-gray-500">🌍 CO₂ évité</p>

        <p class="mt-4 text-5xl font-bold text-[var(--color-primgreen)]">
          {{ co2Saved.toLocaleString('fr-FR', { maximumFractionDigits: 2 }) }}
        </p>

        <p class="mt-2 text-sm text-gray-400">
          kg
        </p>
      </div>
    </div>
  </div>
</template>