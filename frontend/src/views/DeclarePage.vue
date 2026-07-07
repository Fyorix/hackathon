<script setup lang="ts">
import { ref } from "vue";

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

const distance = ref<number | null>(null);
const transport = ref<string>(transportModes[0].value);
const date = ref<string>("");

const submit = () => {
  const payload = {
    distance,
    transport,
    date,
  };

  console.log(payload);

  // TODO : appel backend
};
</script>

<template>
  <div class="flex justify-center p-10">
    <div class="w-full max-w-xl rounded-xl bg-white p-8 shadow-lg">
      <h1 class="mb-8 text-3xl font-bold text-black text-[var(--color-primblue)]">
        Déclarer un trajet
      </h1>

      <form class="space-y-6" @submit.prevent="submit">

        <!-- Distance -->
        <div>
          <label class="mb-2 block text-black font-medium">
            Nombre de kilomètres
          </label>

          <input
              v-model="distance"
              type="number"
              min="0"
              step="0.1"
              placeholder="Ex : 12.5"
              class="w-full text-black rounded-lg border border-gray-300 p-3 focus:border-[var(--color-primgreen)] focus:outline-none"
          />
        </div>

        <!-- Moyen de transport -->
        <div>
          <label class="mb-2 block text-black font-medium">
            Moyen de transport
          </label>

          <select
              v-model="transport"
              class="w-full rounded-lg text-black border border-gray-300 p-3 focus:border-[var(--color-primgreen)] focus:outline-none"
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
          <label class="mb-2 text-black block font-medium">
            Date du trajet
          </label>

          <input
              v-model="date"
              type="date"
              class="w-full text-black rounded-lg border border-gray-300 p-3 focus:border-[var(--color-primgreen)] focus:outline-none"
          />
        </div>

        <div class="pt-6">
          <button
              type="submit"
              class="w-full rounded-lg bg-[var(--color-primgreen)] py-3 font-semibold text-white transition hover:opacity-90"
          >
            Déclarer le trajet
          </button>
        </div>

      </form>
    </div>
  </div>
</template>