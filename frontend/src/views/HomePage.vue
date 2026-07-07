<script setup lang="ts">
import {UsersService} from "../api";
let carbonPoints = 0;
let kilometers = 0;
let co2Saved = 0;
const goal = 150;

function onMounted() {
  getMyInfo();
}

async function getMyInfo(){
  const me = await UsersService.me.get();
  carbonPoints = me?.carbonPointsBalance || 0;
  co2Saved = me.totalCo2Saved || 0;
}

const progress = (co2Saved / goal) * 100;
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
          {{ kilometers }}
        </p>

        <p class="mt-2 text-sm text-gray-400">
          km
        </p>
      </div>

      <div class="rounded-2xl bg-white p-8 shadow">
        <p class="text-gray-500">🌍 CO₂ évité</p>

        <p class="mt-4 text-5xl font-bold text-green-600">
          {{ co2Saved }}
        </p>

        <p class="mt-2 text-sm text-gray-400">
          kg
        </p>
      </div>
    </div>

    <!-- Jauge -->
    <div class="mx-auto mt-12 max-w-5xl rounded-2xl bg-white p-8 shadow">
      <div class="mb-4 flex justify-between">
        <h2 class="text-2xl font-bold text-[var(--color-primblue)]">
          Objectif écologique
        </h2>

        <span class="font-semibold">
          {{ co2Saved }} / {{ goal }} kg
        </span>
      </div>

      <div class="h-6 overflow-hidden rounded-full bg-gray-200">
        <div
            class="h-full rounded-full bg-[var(--color-primgreen)] transition-all duration-500"
            :style="{ width: progress + '%' }"
        />
      </div>

      <p class="mt-4 text-center text-gray-600">
        Vous avez déjà évité
        <span class="font-bold text-[var(--color-primgreen)]">
          {{ co2Saved }} kg de CO₂
        </span>
        grâce à vos trajets responsables.
      </p>
    </div>
  </div>
</template>