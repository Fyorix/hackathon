<script setup lang="ts">
import { ref, computed } from 'vue'

const props = defineProps<{
  items: any[]
  loading: boolean
  hasMore: boolean
  sortBy: string
  desc: boolean
  highlightId?: number | null
  highlightLabel?: string
  isUserType?: boolean // true for Users leaderboard, false for Companies leaderboard
}>()

const emit = defineEmits<{
  (e: 'load-more'): void
  (e: 'change-sort', field: string): void
  (e: 'click-item', item: any): void
}>()

// Track logos that failed to load
const failedLogos = ref<Record<number, boolean>>({})

// Resolve backend image filenames to local assets
function getLogoUrl(logoPath?: string) {
  if (!logoPath) return ''
  const filename = logoPath.split('/').pop() || logoPath
  try {
    return new URL(`../assets/companies/${filename}`, import.meta.url).href
  } catch (e) {
    console.error('Error resolving logo URL:', e)
    return ''
  }
}

function handleImageError(companyId?: number) {
  if (companyId) {
    failedLogos.value[companyId] = true
  }
}

// Background for initial letter avatar
function getAvatarBg(name: string = '') {
  const hash = name.split('').reduce((acc, char) => acc + char.charCodeAt(0), 0)
  const hues = [140, 160, 180, 200, 220, 280, 320]
  const hue = hues[hash % hues.length]
  return `hsl(${hue}, 60%, 45%)`
}

// Format values depending on sorting
function formatValue(item: any) {
  if (props.sortBy === 'CO2') {
    return `${(item.totalCo2Saved || 0).toLocaleString('fr-FR', { maximumFractionDigits: 1 })} kg CO₂`
  } else if (props.sortBy === 'POINTS') {
    const pts = props.isUserType ? item.carbonPointsBalance : item.totalPoints
    return `${(pts || 0).toLocaleString('fr-FR')} pts`
  } else if (props.sortBy === 'KM') {
    return `${(item.totalKm || 0).toLocaleString('fr-FR', { maximumFractionDigits: 1 })} km`
  } else {
    // EMPLOYEES (only for companies)
    return `${(item.totalEmployees || 0).toLocaleString('fr-FR')} emp.`
  }
}

// Podium split (1st, 2nd, 3rd)
const podium = computed(() => {
  if (props.items.length === 0) return { first: null, second: null, third: null }
  return {
    first: props.items[0] || null,
    second: props.items[1] || null,
    third: props.items[2] || null
  }
})

// Remaining list items (rank 4+)
const listItems = computed(() => {
  return props.items.slice(3)
})
</script>

<template>
  <div>
    <!-- Sorting Nav / Filter Tabs -->
    <div class="mb-8 flex flex-col md:flex-row gap-4 items-center justify-between">
      <h2 class="text-xl font-bold text-slate-800 flex items-center gap-2">
        <span>{{ isUserType ? 'Classement des employés' : 'Classement général' }}</span>
        <span class="text-xs font-normal text-slate-500 bg-slate-200 px-2 py-0.5 rounded-md">
          {{ items.length }} affichés
        </span>
      </h2>
      
      <div class="w-full md:w-auto overflow-x-auto no-scrollbar py-1">
        <div class="flex items-center gap-2 bg-slate-200/80 p-1.5 rounded-xl border border-slate-300/40 w-max">
          <!-- CO2 -->
          <button 
            id="sort-co2"
            @click="emit('change-sort', 'CO2')" 
            class="flex items-center gap-1.5 px-4 py-2 rounded-lg text-sm font-semibold transition duration-200 cursor-pointer"
            :class="sortBy === 'CO2' ? 'bg-white text-[var(--color-primblue)] shadow-sm' : 'text-slate-600 hover:text-slate-900 hover:bg-white/40'"
          >
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-4 h-4 text-[var(--color-primgreen)]">
              <path d="M12 2C6.48 2 2 6.48 2 12c0 4.42 2.87 8.17 6.84 9.39.4.08.56-.17.56-.38v-1.34c-2.78.6-3.37-1.34-3.37-1.34-.46-1.16-1.11-1.47-1.11-1.47-.9-.62.07-.6.07-.6 1 .07 1.53 1.03 1.53 1.03.9 1.52 2.34 1.07 2.91.83.09-.65.35-1.09.63-1.34-2.22-.25-4.55-1.11-4.55-4.94 0-1.1.39-1.99 1.03-2.69-.1-.25-.45-1.27.1-2.64 0 0 .84-.27 2.75 1.02.79-.22 1.65-.33 2.5-.33.85 0 1.71.11 2.5.33 1.91-1.29 2.75-1.02 2.75-1.02.55 1.37.2 2.39.1 2.64.65.7 1.03 1.6 1.03 2.69 0 3.84-2.34 4.68-4.57 4.93.36.31.68.92.68 1.85v2.75c0 .22.15.47.57.38C19.14 20.16 22 16.42 22 12A10 10 0 0012 2z" />
            </svg>
            CO₂ évité
            <span v-if="sortBy === 'CO2'" class="text-[10px]">{{ desc ? '▼' : '▲' }}</span>
          </button>

          <!-- POINTS -->
          <button 
            id="sort-points"
            @click="emit('change-sort', 'POINTS')" 
            class="flex items-center gap-1.5 px-4 py-2 rounded-lg text-sm font-semibold transition duration-200 cursor-pointer"
            :class="sortBy === 'POINTS' ? 'bg-white text-[var(--color-primblue)] shadow-sm' : 'text-slate-600 hover:text-slate-900 hover:bg-white/40'"
          >
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-4 h-4 text-amber-500">
              <path fill-rule="evenodd" d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25zM12 19c-3.86 0-7-3.14-7-7s3.14-7 7-7 7 3.14 7 7-3.14 7-7 7zm-1.5-11.5a1.5 1.5 0 100 3 1.5 1.5 0 000-3zm4.5 5.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0zm-5.25 4.5h4.5a.75.75 0 000-1.5h-4.5a.75.75 0 000 1.5z" clip-rule="evenodd" />
            </svg>
            Points
            <span v-if="sortBy === 'POINTS'" class="text-[10px]">{{ desc ? '▼' : '▲' }}</span>
          </button>

          <!-- KM -->
          <button 
            id="sort-km"
            @click="emit('change-sort', 'KM')" 
            class="flex items-center gap-1.5 px-4 py-2 rounded-lg text-sm font-semibold transition duration-200 cursor-pointer"
            :class="sortBy === 'KM' ? 'bg-white text-[var(--color-primblue)] shadow-sm' : 'text-slate-600 hover:text-slate-900 hover:bg-white/40'"
          >
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-4 h-4 text-sky-500">
              <path fill-rule="evenodd" d="M1.5 13.5a8.25 8.25 0 0113.71-6.172 6.75 6.75 0 00-2.825 4.908c-.127.818-.223 1.63-.382 2.441a12.82 12.82 0 00-.236 1.83.75.75 0 01-1.49-.136 11.23 11.23 0 01.196-1.54l.002-.008c.137-.706.223-1.41.332-2.115a5.25 5.25 0 013.91-4.225A6.75 6.75 0 002.25 13.5H1.5z" clip-rule="evenodd" />
              <path fill-rule="evenodd" d="M12 2.25a.75.75 0 01.75.75v1.5a.75.75 0 01-1.5 0V3a.75.75 0 01.75-.75zM6.16 3.56a.75.75 0 011.06 0l1.06 1.06a.75.75 0 11-1.06 1.06L6.16 4.62a.75.75 0 010-1.06zm10.62 0a.75.75 0 010 1.06l-1.06 1.06a.75.75 0 11-1.06-1.06l1.06-1.06a.75.75 0 011.06 0zM3 12a.75.75 0 01.75-.75h1.5a.75.75 0 010 1.5H3.75A.75.75 0 013 12zm15 0a.75.75 0 01.75-.75h1.5a.75.75 0 010 1.5h-1.5A.75.75 0 0118 12zM4.62 16.78a.75.75 0 011.06 0l1.06 1.06a.75.75 0 11-1.06 1.06l-1.06-1.06a.75.75 0 010-1.06zm13.72 0a.75.75 0 010 1.06l-1.06 1.06a.75.75 0 11-1.06-1.06l1.06-1.06a.75.75 0 011.06 0zM12 18a.75.75 0 01.75.75v1.5a.75.75 0 01-1.5 0v-1.5A.75.75 0 0112 18z" clip-rule="evenodd" />
            </svg>
            Distance
            <span v-if="sortBy === 'KM'" class="text-[10px]">{{ desc ? '▼' : '▲' }}</span>
          </button>

          <!-- EFFECTIFS (Only for companies) -->
          <button 
            v-if="!isUserType"
            id="sort-employees"
            @click="emit('change-sort', 'EMPLOYEES')" 
            class="flex items-center gap-1.5 px-4 py-2 rounded-lg text-sm font-semibold transition duration-200 cursor-pointer"
            :class="sortBy === 'EMPLOYEES' ? 'bg-white text-[var(--color-primblue)] shadow-sm' : 'text-slate-600 hover:text-slate-900 hover:bg-white/40'"
          >
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-4 h-4 text-violet-500">
              <path d="M4.5 6.375a4.125 4.125 0 118.25 0 4.125 4.125 0 01-8.25 0zM14.25 8.625a3.375 3.375 0 116.75 0 3.375 3.375 0 01-6.75 0zM1.5 19.125a7.125 7.125 0 0114.25 0v.003l-.001.119a.75.75 0 01-.363.63 13.067 13.067 0 01-6.761 1.873c-2.472 0-4.786-.684-6.76-1.873a.75.75 0 01-.364-.63l-.001-.122zM17.25 19.128l-.001.144a2.25 2.25 0 01-.233.96 10.088 10.088 0 01-3.627 3.518c.27-.015.539-.033.807-.053a8.261 8.261 0 006.753-8.22v-.004l-.001-.122a.75.75 0 00-.363-.63 13.065 13.065 0 00-3.338-1.593z" />
            </svg>
            Effectifs
            <span v-if="sortBy === 'EMPLOYEES'" class="text-[10px]">{{ desc ? '▼' : '▲' }}</span>
          </button>
        </div>
      </div>
    </div>
    <!-- PODIUM FOR TOP 3 -->
    <div class="flex justify-center items-end gap-3 md:gap-6 max-w-3xl mx-auto mb-12 px-2 pt-6">
      
      <!-- 2nd Place -->
      <div v-if="podium.second" class="flex flex-col items-center order-1 w-28 md:w-36 flex-shrink-0">
        <div class="relative group cursor-pointer mb-3" @click="emit('click-item', podium.second)">
          <div class="absolute inset-0 bg-slate-300 rounded-full blur-md opacity-40 group-hover:opacity-70 transition duration-300"></div>
          
          <!-- Avatar -->
          <img v-if="!isUserType && podium.second.logoPath && !failedLogos[podium.second.id || 0]" 
               :src="getLogoUrl(podium.second.logoPath)" 
               :alt="podium.second.name" 
               @error="handleImageError(podium.second.id)"
               class="relative w-16 h-16 md:w-20 md:h-20 rounded-full object-cover border-4 border-slate-300 bg-white z-10" />
          <div v-else :style="{ backgroundColor: getAvatarBg(podium.second.name) }"
               class="relative w-16 h-16 md:w-20 md:h-20 rounded-full flex items-center justify-center font-bold text-white text-xl md:text-2xl border-4 border-slate-300 z-10">
            {{ podium.second.name?.charAt(0).toUpperCase() }}
          </div>
          
          <!-- Badge -->
          <div class="absolute -bottom-2 -right-1 bg-slate-300 text-slate-800 text-xs md:text-sm font-black w-6 h-6 md:w-7 md:h-7 rounded-full flex items-center justify-center border-2 border-white shadow-md z-20">
            2
          </div>
        </div>
        
        <div class="text-center w-full mb-1">
          <h3 class="font-bold text-slate-800 text-sm md:text-base truncate max-w-[110px] md:max-w-none px-1" :title="podium.second.name">
            {{ podium.second.name }}
          </h3>
          <p class="text-xs font-semibold text-slate-500">{{ formatValue(podium.second) }}</p>
        </div>
        
        <!-- Podium Pillar -->
        <div class="w-full bg-gradient-to-t from-slate-200 to-slate-100/90 h-28 md:h-36 rounded-t-2xl shadow-inner border border-b-0 border-slate-300/30 flex items-center justify-center">
          <span class="text-slate-400 font-extrabold text-2xl md:text-3xl">2nd</span>
        </div>
      </div>

      <!-- 1st Place -->
      <div v-if="podium.first" class="flex flex-col items-center order-2 w-32 md:w-44 flex-shrink-0">
        <div class="relative group cursor-pointer mb-4" @click="emit('click-item', podium.first)">
          <!-- Golden glow -->
          <div class="absolute inset-0 bg-amber-400 rounded-full blur-lg opacity-40 group-hover:opacity-80 transition duration-300 animate-pulse"></div>
          
          <!-- Crown -->
          <div class="absolute -top-6 left-1/2 -translate-x-1/2 z-20 text-amber-500 drop-shadow">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-7 h-7 md:w-9 md:h-9 animate-bounce duration-1000">
              <path d="M2 19h20v2H2v-2zm2-2h16l-3-8-4 4-1-8-1 8-4-4-3 8z" />
            </svg>
          </div>
          
          <!-- Avatar -->
          <img v-if="!isUserType && podium.first.logoPath && !failedLogos[podium.first.id || 0]" 
               :src="getLogoUrl(podium.first.logoPath)" 
               :alt="podium.first.name" 
               @error="handleImageError(podium.first.id)"
               class="relative w-20 h-20 md:w-26 md:h-26 rounded-full object-cover border-4 border-amber-400 bg-white z-10 shadow-lg" />
          <div v-else :style="{ backgroundColor: getAvatarBg(podium.first.name) }"
               class="relative w-20 h-20 md:w-26 md:h-26 rounded-full flex items-center justify-center font-bold text-white text-2xl md:text-3xl border-4 border-amber-400 z-10 shadow-lg">
            {{ podium.first.name?.charAt(0).toUpperCase() }}
          </div>
          
          <!-- Badge -->
          <div class="absolute -bottom-2 left-1/2 -translate-x-1/2 bg-amber-400 text-amber-950 text-xs md:text-sm font-black w-7 h-7 md:w-8 md:h-8 rounded-full flex items-center justify-center border-2 border-white shadow-md z-20">
            1
          </div>
        </div>
        
        <div class="text-center w-full mb-1">
          <h3 class="font-extrabold text-slate-900 text-sm md:text-lg truncate max-w-[130px] md:max-w-none px-1" :title="podium.first.name">
            {{ podium.first.name }}
          </h3>
          <p class="text-xs md:text-sm font-bold text-amber-600">{{ formatValue(podium.first) }}</p>
        </div>
        
        <!-- Podium Pillar -->
        <div class="w-full bg-gradient-to-t from-amber-100 to-amber-50 h-36 md:h-48 rounded-t-3xl shadow-md border border-b-0 border-amber-200/50 flex items-center justify-center">
          <span class="text-amber-500 font-extrabold text-3xl md:text-4xl">1st</span>
        </div>
      </div>

      <!-- 3rd Place -->
      <div v-if="podium.third" class="flex flex-col items-center order-3 w-28 md:w-36 flex-shrink-0">
        <div class="relative group cursor-pointer mb-3" @click="emit('click-item', podium.third)">
          <div class="absolute inset-0 bg-amber-700 rounded-full blur-md opacity-30 group-hover:opacity-60 transition duration-300"></div>
          
          <!-- Avatar -->
          <img v-if="!isUserType && podium.third.logoPath && !failedLogos[podium.third.id || 0]" 
               :src="getLogoUrl(podium.third.logoPath)" 
               :alt="podium.third.name" 
               @error="handleImageError(podium.third.id)"
               class="relative w-14 h-14 md:w-18 md:h-18 rounded-full object-cover border-4 border-amber-700/60 bg-white z-10" />
          <div v-else :style="{ backgroundColor: getAvatarBg(podium.third.name) }"
               class="relative w-14 h-14 md:w-18 md:h-18 rounded-full flex items-center justify-center font-bold text-white text-lg md:text-xl border-4 border-amber-700/60 z-10">
            {{ podium.third.name?.charAt(0).toUpperCase() }}
          </div>
          
          <!-- Badge -->
          <div class="absolute -bottom-2 -right-1 bg-amber-700/80 text-amber-50 text-xs md:text-sm font-black w-6 h-6 md:w-7 md:h-7 rounded-full flex items-center justify-center border-2 border-white shadow-md z-20">
            3
          </div>
        </div>
        
        <div class="text-center w-full mb-1">
          <h3 class="font-bold text-slate-800 text-sm md:text-base truncate max-w-[110px] md:max-w-none px-1" :title="podium.third.name">
            {{ podium.third.name }}
          </h3>
          <p class="text-xs font-semibold text-slate-500">{{ formatValue(podium.third) }}</p>
        </div>
        
        <!-- Podium Pillar -->
        <div class="w-full bg-gradient-to-t from-orange-100 to-orange-50/70 h-20 md:h-28 rounded-t-2xl shadow-inner border border-b-0 border-orange-200/20 flex items-center justify-center">
          <span class="text-amber-800/60 font-extrabold text-xl md:text-2xl">3rd</span>
        </div>
      </div>
    </div>
    <!-- LIST OF OTHER ITEMS -->
    <div class="bg-white rounded-3xl shadow-lg border border-slate-100 overflow-hidden mb-6">
      <div class="overflow-x-auto">
        <table class="w-full text-left border-collapse">
          <thead>
            <tr class="bg-slate-50/80 text-slate-400 text-xs font-bold uppercase tracking-wider border-b border-slate-100">
              <th class="py-4 px-6 text-center w-20">Rang</th>
              <th class="py-4 px-6">{{ isUserType ? 'Employé' : 'Entreprise' }}</th>
              <th v-if="!isUserType" class="py-4 px-6 text-center">Effectif</th>
              <th class="py-4 px-6 text-right">Distance</th>
              <th class="py-4 px-6 text-right">Score Points</th>
              <th class="py-4 px-6 text-right text-[var(--color-primblue)]">Impact CO₂</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-slate-100">
            
            <tr 
              v-for="(item, idx) in listItems" 
              :key="item.id"
              @click="emit('click-item', item)"
              class="group transition-colors duration-150"
              :class="[
                highlightId && highlightId === item.id ? 'bg-emerald-50/40 border-l-4 border-l-[var(--color-primgreen)]' : 'hover:bg-slate-50/80',
                !isUserType ? 'cursor-pointer' : ''
              ]"
            >
              <!-- Rank -->
              <td class="py-4 px-6 text-center font-bold text-slate-600">
                <span class="inline-flex items-center justify-center w-8 h-8 rounded-full bg-slate-100 group-hover:bg-slate-200 text-xs transition duration-150">
                  {{ idx + 4 }}
                </span>
              </td>
              
              <!-- Info -->
              <td class="py-4 px-6">
                <div class="flex items-center gap-3">
                  <!-- Company logo or user placeholder -->
                  <template v-if="!isUserType">
                    <img v-if="item.logoPath && !failedLogos[item.id || 0]" 
                         :src="getLogoUrl(item.logoPath)" 
                         :alt="item.name" 
                         @error="handleImageError(item.id)"
                         class="w-10 h-10 rounded-xl object-cover border border-slate-200/80 bg-white" />
                    <div v-else :style="{ backgroundColor: getAvatarBg(item.name) }"
                         class="w-10 h-10 rounded-xl flex items-center justify-center font-bold text-white text-sm">
                      {{ item.name?.substring(0, 2).toUpperCase() }}
                    </div>
                  </template>
                  <template v-else>
                    <div :style="{ backgroundColor: getAvatarBg(item.name) }"
                         class="w-10 h-10 rounded-xl flex items-center justify-center font-bold text-white text-sm">
                      {{ item.name?.substring(0, 2).toUpperCase() }}
                    </div>
                  </template>
                  
                  <div>
                    <span class="font-bold text-slate-800 text-sm md:text-base flex items-center gap-1.5">
                      {{ item.name }}
                      <span v-if="highlightId && highlightId === item.id" class="text-[10px] bg-[var(--color-primgreen)] text-white px-2 py-0.5 rounded-full font-bold uppercase tracking-wider">
                        {{ highlightLabel }}
                      </span>
                    </span>
                    <span class="text-xs text-slate-400 block font-light">
                      {{ isUserType ? item.email : `SIREN: ${item.sirenNumber}` }}
                    </span>
                  </div>
                </div>
              </td>
              
              <!-- Total Employees (Only for companies) -->
              <td v-if="!isUserType" class="py-4 px-6 text-center text-sm font-semibold text-slate-600">
                {{ (item.totalEmployees || 0).toLocaleString('fr-FR') }}
              </td>
              
              <!-- Total Distance -->
              <td class="py-4 px-6 text-right text-sm font-semibold text-slate-600">
                {{ (item.totalKm || 0).toLocaleString('fr-FR', { maximumFractionDigits: 1 }) }} km
              </td>

              <!-- Total Points -->
              <td class="py-4 px-6 text-right">
                <span class="inline-flex items-center gap-1 text-sm font-bold text-amber-600 bg-amber-50 px-2.5 py-1 rounded-lg">
                  {{ ((isUserType ? item.carbonPointsBalance : item.totalPoints) || 0).toLocaleString('fr-FR') }}
                </span>
              </td>

              <!-- Total CO2 -->
              <td class="py-4 px-6 text-right">
                <span class="inline-flex items-center gap-1 text-sm font-extrabold text-[var(--color-primgreen)] bg-[var(--color-primgreen)]/10 px-2.5 py-1 rounded-lg">
                  🌿 {{ (item.totalCo2Saved || 0).toLocaleString('fr-FR', { maximumFractionDigits: 1 }) }} kg
                </span>
              </td>
            </tr>

          </tbody>
        </table>
      </div>
    </div>

    <!-- LOAD MORE / PAGINATION FOOTER -->
    <div class="mt-8 flex flex-col items-center justify-center gap-4">
      <button 
        v-if="hasMore" 
        id="load-more-btn"
        @click="emit('load-more')" 
        :disabled="loading"
        class="px-8 py-3 rounded-full bg-[var(--color-primblue)] hover:bg-[#095487] active:scale-95 disabled:opacity-50 text-white font-bold text-sm tracking-wide shadow-md hover:shadow-lg transition-all duration-200 flex items-center gap-2 cursor-pointer"
      >
        <span v-if="loading" class="w-4 h-4 border-2 border-white/20 border-t-white rounded-full animate-spin"></span>
        {{ loading ? 'Chargement en cours...' : (isUserType ? 'Charger plus d\'employés' : 'Charger plus d\'entreprises') }}
      </button>
      
      <div v-else class="text-center py-6">
        <p class="text-slate-400 font-medium text-sm flex items-center gap-1.5 justify-center">
          <span>Vous êtes à jour ! Fin du classement.</span>
          <span>🌱</span>
        </p>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Custom styling for hiding scrollbar but keeping scrolling */
.no-scrollbar::-webkit-scrollbar {
  display: none;
}
.no-scrollbar {
  -ms-overflow-style: none;
  scrollbar-width: none;
}
</style>
