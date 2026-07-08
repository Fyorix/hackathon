<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { CompaniesService, UsersService } from '@/api'
import type { CompanyResponse, UserResponse } from '@/api/types'
import GenericLeaderboard from '@/components/GenericLeaderboard.vue'

// Common state
const loading = ref(false)
const errorMsg = ref('')
const selectedCompany = ref<CompanyResponse | null>(null)

// Companies leaderboard state
const companies = ref<CompanyResponse[]>([])
const myCompany = ref<CompanyResponse | null>(null)
const companyPage = ref(0)
const companySize = 10
const companySortBy = ref<'CO2' | 'POINTS' | 'KM' | 'EMPLOYEES'>('CO2')
const companyDesc = ref(true)
const companyHasMore = ref(true)
const cachedCompanyNextPage = ref<CompanyResponse[] | null>(null)

// Users leaderboard state
const users = ref<UserResponse[]>([])
const currentUser = ref<UserResponse | null>(null)
const userPage = ref(0)
const userSize = 10
const userSortBy = ref<'CO2' | 'POINTS' | 'KM'>('CO2')
const userDesc = ref(true)
const userHasMore = ref(true)
const cachedUserNextPage = ref<UserResponse[] | null>(null)

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

// Prefetch next page of companies to see if more exist
function prefetchNextCompanies() {
  const nextPage = companyPage.value + 1
  CompaniesService.leaderboard({
    page: nextPage,
    size: companySize,
    sortBy: companySortBy.value,
    desc: companyDesc.value
  }).then(nextData => {
    if (!nextData || nextData.length === 0) {
      companyHasMore.value = false
    } else {
      cachedCompanyNextPage.value = nextData
    }
  }).catch(err => {
    console.warn('Prefetch error:', err)
  })
}

// Fetch companies leaderboard data
async function fetchCompanyLeaderboard(loadMore = false) {
  if (!loadMore) {
    companyPage.value = 0
    companies.value = []
    companyHasMore.value = true
    cachedCompanyNextPage.value = null
    
    loading.value = true
    errorMsg.value = ''
    
    try {
      const data = await CompaniesService.leaderboard({
        page: 0,
        size: companySize,
        sortBy: companySortBy.value,
        desc: companyDesc.value
      })
      
      if (data && data.length > 0) {
        companies.value = data
        if (data.length < companySize) {
          companyHasMore.value = false
        } else {
          prefetchNextCompanies()
        }
      } else {
        companyHasMore.value = false
      }
    } catch (err: any) {
      console.error('Error fetching company leaderboard:', err)
      errorMsg.value = 'Impossible de charger le classement des entreprises.'
    } finally {
      loading.value = false
    }
  } else {
    // If we have cached next page, load it instantly
    if (cachedCompanyNextPage.value) {
      const currentIds = new Set(companies.value.map(c => c.id))
      const uniqueNew = cachedCompanyNextPage.value.filter(c => !currentIds.has(c.id))
      companies.value.push(...uniqueNew)
      companyPage.value += 1
      
      const lastFetchLength = cachedCompanyNextPage.value.length
      cachedCompanyNextPage.value = null
      
      if (lastFetchLength < companySize) {
        companyHasMore.value = false
      } else {
        prefetchNextCompanies()
      }
    } else {
      // Fallback in case cache is empty
      loading.value = true
      try {
        companyPage.value += 1
        const data = await CompaniesService.leaderboard({
          page: companyPage.value,
          size: companySize,
          sortBy: companySortBy.value,
          desc: companyDesc.value
        })
        if (data && data.length > 0) {
          const currentIds = new Set(companies.value.map(c => c.id))
          const uniqueNew = data.filter(c => !currentIds.has(c.id))
          companies.value.push(...uniqueNew)
          if (data.length < companySize) {
            companyHasMore.value = false
          } else {
            prefetchNextCompanies()
          }
        } else {
          companyHasMore.value = false
        }
      } catch (err) {
        console.error('Error fetching next company page:', err)
        errorMsg.value = 'Impossible de charger plus d\'entreprises.'
      } finally {
        loading.value = false
      }
    }
  }
}

// Prefetch next page of users to see if more exist
function prefetchNextUsers() {
  if (!selectedCompany.value?.id) return
  const nextPage = userPage.value + 1
  CompaniesService.userLeaderboard(selectedCompany.value.id, {
    page: nextPage,
    size: userSize,
    sortBy: userSortBy.value,
    desc: userDesc.value
  }).then(nextData => {
    if (!nextData || nextData.length === 0) {
      userHasMore.value = false
    } else {
      cachedUserNextPage.value = nextData
    }
  }).catch(err => {
    console.warn('Prefetch error:', err)
  })
}

// Fetch users leaderboard for a specific company
async function fetchUserLeaderboard(loadMore = false) {
  if (!selectedCompany.value?.id) return
  
  if (!loadMore) {
    userPage.value = 0
    users.value = []
    userHasMore.value = true
    cachedUserNextPage.value = null
    
    loading.value = true
    errorMsg.value = ''
    
    try {
      const data = await CompaniesService.userLeaderboard(selectedCompany.value.id, {
        page: 0,
        size: userSize,
        sortBy: userSortBy.value,
        desc: userDesc.value
      })
      
      if (data && data.length > 0) {
        users.value = data
        if (data.length < userSize) {
          userHasMore.value = false
        } else {
          prefetchNextUsers()
        }
      } else {
        userHasMore.value = false
      }
    } catch (err: any) {
      console.error('Error fetching user leaderboard:', err)
      errorMsg.value = `Impossible de charger le classement des employés de ${selectedCompany.value?.name}.`
    } finally {
      loading.value = false
    }
  } else {
    // If we have cached next page, load it instantly
    if (cachedUserNextPage.value) {
      const currentIds = new Set(users.value.map(u => u.id))
      const uniqueNew = cachedUserNextPage.value.filter(u => !currentIds.has(u.id))
      users.value.push(...uniqueNew)
      userPage.value += 1
      
      const lastFetchLength = cachedUserNextPage.value.length
      cachedUserNextPage.value = null
      
      if (lastFetchLength < userSize) {
        userHasMore.value = false
      } else {
        prefetchNextUsers()
      }
    } else {
      loading.value = true
      try {
        userPage.value += 1
        const data = await CompaniesService.userLeaderboard(selectedCompany.value.id, {
          page: userPage.value,
          size: userSize,
          sortBy: userSortBy.value,
          desc: userDesc.value
        })
        if (data && data.length > 0) {
          const currentIds = new Set(users.value.map(u => u.id))
          const uniqueNew = data.filter(u => !currentIds.has(u.id))
          users.value.push(...uniqueNew)
          if (data.length < userSize) {
            userHasMore.value = false
          } else {
            prefetchNextUsers()
          }
        } else {
          userHasMore.value = false
        }
      } catch (err) {
        console.error('Error fetching next user page:', err)
        errorMsg.value = 'Impossible de charger plus d\'employés.'
      } finally {
        loading.value = false
      }
    }
  }
}

// Fetch current user details
async function fetchCurrentUser() {
  try {
    const data = await UsersService.me.get()
    if (data) {
      currentUser.value = data
    }
  } catch (err) {
    console.warn('Could not fetch current user details:', err)
  }
}

// Fetch current user's company info to highlight it
async function fetchMyCompany() {
  try {
    const data = await CompaniesService.me()
    if (data) {
      myCompany.value = data
    }
  } catch (err) {
    console.warn('Could not fetch user company info:', err)
  }
}

// Sorting handler
function changeSort(field: string) {
  if (selectedCompany.value) {
    // User mode sorting
    const typedField = field as 'CO2' | 'POINTS' | 'KM'
    if (userSortBy.value === typedField) {
      userDesc.value = !userDesc.value
    } else {
      userSortBy.value = typedField
      userDesc.value = true
    }
    fetchUserLeaderboard(false)
  } else {
    // Company mode sorting
    const typedField = field as 'CO2' | 'POINTS' | 'KM' | 'EMPLOYEES'
    if (companySortBy.value === typedField) {
      companyDesc.value = !companyDesc.value
    } else {
      companySortBy.value = typedField
      companyDesc.value = true
    }
    fetchCompanyLeaderboard(false)
  }
}

// Load more handler
function loadMore() {
  if (loading.value) return
  if (selectedCompany.value) {
    fetchUserLeaderboard(true)
  } else {
    fetchCompanyLeaderboard(true)
  }
}

// Item clicked: click on company loads user ranking
function handleItemClick(item: any) {
  if (!selectedCompany.value) {
    selectedCompany.value = item
    fetchUserLeaderboard(false)
  }
}

// Back to companies leaderboard
function backToCompanies() {
  selectedCompany.value = null
  errorMsg.value = ''
}

// Background for initial letter avatar
function getAvatarBg(name: string = '') {
  const hash = name.split('').reduce((acc, char) => acc + char.charCodeAt(0), 0)
  const hues = [140, 160, 180, 200, 220, 280, 320]
  const hue = hues[hash % hues.length]
  return `hsl(${hue}, 60%, 45%)`
}

onMounted(() => {
  fetchCompanyLeaderboard()
  fetchMyCompany()
  fetchCurrentUser()
})
</script>

<template>
  <div class="min-h-screen bg-gradient-to-b from-slate-50 to-slate-100 p-6 md:p-10">
    <div class="max-w-6xl mx-auto">
      
      <!-- Premium Header Card (Company Mode) -->
      <header v-if="!selectedCompany" class="mb-10 text-center relative overflow-hidden rounded-3xl bg-gradient-to-r from-[var(--color-primblue)] to-[#095487] text-white p-8 md:p-12 shadow-xl animate-fade-in">
        <div class="absolute -right-10 -top-10 w-40 h-40 bg-white/5 rounded-full blur-2xl"></div>
        <div class="absolute -left-10 -bottom-10 w-52 h-52 bg-[var(--color-primgreen)]/10 rounded-full blur-3xl"></div>
        
        <span class="inline-flex items-center gap-1.5 px-3 py-1.5 rounded-full text-xs font-semibold bg-[var(--color-primgreen)] text-white mb-4 animate-pulse">
          🌱 Compétition RSE
        </span>
        
        <h1 class="text-3xl md:text-5xl font-black tracking-tight mb-3">
          Leaderboard des Entreprises
        </h1>
        <p class="text-slate-200 text-sm md:text-base max-w-2xl mx-auto font-light leading-relaxed">
          Suivez l'impact écologique collectif de chaque entreprise. Comparez les kilomètres parcourus à vélo, en marche ou en covoiturage, et les points carbone accumulés ! Cliquez sur une entreprise pour voir ses employés.
        </p>
      </header>

      <!-- Premium Header Card (User/Employee Mode) -->
      <div v-else class="mb-10 animate-fade-in">
        <!-- Back Button -->
        <button 
          @click="backToCompanies"
          class="mb-6 inline-flex items-center gap-2 px-4 py-2 rounded-xl bg-white hover:bg-slate-100 text-slate-700 hover:text-slate-900 border border-slate-200 shadow-sm font-bold text-sm transition-all duration-200 cursor-pointer"
        >
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="w-4 h-4">
            <path stroke-linecap="round" stroke-linejoin="round" d="M10.5 19.5L3 12m0 0l7.5-7.5M3 12h18" />
          </svg>
          Retour au classement des entreprises
        </button>

        <header class="relative overflow-hidden rounded-3xl bg-gradient-to-r from-slate-900 via-slate-800 to-slate-950 text-white p-8 shadow-xl">
          <div class="absolute -right-10 -top-10 w-40 h-40 bg-white/5 rounded-full blur-2xl"></div>
          
          <div class="flex flex-col md:flex-row items-center gap-6 relative z-10">
            <!-- Company Logo -->
            <img v-if="selectedCompany.logoPath && !failedLogos[selectedCompany.id || 0]" 
                 :src="getLogoUrl(selectedCompany.logoPath)" 
                 :alt="selectedCompany.name" 
                 @error="handleImageError(selectedCompany.id)"
                 class="w-20 h-20 md:w-24 md:h-24 rounded-2xl object-cover border-4 border-slate-750 bg-white shadow-md" />
            <div v-else :style="{ backgroundColor: getAvatarBg(selectedCompany.name) }"
                 class="w-20 h-20 md:w-24 md:h-24 rounded-2xl flex items-center justify-center font-bold text-white text-3xl md:text-4xl shadow-md border-4 border-slate-700">
              {{ selectedCompany.name?.substring(0, 2).toUpperCase() }}
            </div>

            <!-- Company Text Details -->
            <div class="text-center md:text-left flex-1">
              <span class="text-xs font-bold text-emerald-400 bg-emerald-950/60 px-3 py-1 rounded-full border border-emerald-900/50">
                🏢 Impact d'Équipe
              </span>
              <h1 class="text-3xl md:text-4xl font-extrabold tracking-tight mt-2 text-white">
                {{ selectedCompany.name }}
              </h1>
              <p class="text-slate-400 text-xs md:text-sm font-light mt-1">
                Numéro SIREN : {{ selectedCompany.sirenNumber }}
              </p>
            </div>

            <!-- Stats grid -->
            <div class="grid grid-cols-2 md:grid-cols-4 gap-4 w-full md:w-auto">
              <div class="bg-white/5 border border-white/10 rounded-2xl p-4 text-center">
                <span class="text-slate-400 text-xs block font-light">Effectif</span>
                <span class="text-xl font-bold text-white mt-1 block">
                  {{ (selectedCompany.totalEmployees || 0).toLocaleString('fr-FR') }}
                </span>
              </div>
              <div class="bg-white/5 border border-white/10 rounded-2xl p-4 text-center">
                <span class="text-slate-400 text-xs block font-light">Distance</span>
                <span class="text-xl font-bold text-white mt-1 block">
                  {{ (selectedCompany.totalKm || 0).toLocaleString('fr-FR', { maximumFractionDigits: 1 }) }} km
                </span>
              </div>
              <div class="bg-white/5 border border-white/10 rounded-2xl p-4 text-center">
                <span class="text-slate-400 text-xs block font-light">Points RSE</span>
                <span class="text-xl font-bold text-amber-400 mt-1 block">
                  {{ (selectedCompany.totalPoints || 0).toLocaleString('fr-FR') }}
                </span>
              </div>
              <div class="bg-white/5 border border-white/10 rounded-2xl p-4 text-center">
                <span class="text-slate-400 text-xs block font-light">CO₂ Évité</span>
                <span class="text-xl font-bold text-emerald-400 mt-1 block">
                  {{ (selectedCompany.totalCo2Saved || 0).toLocaleString('fr-FR', { maximumFractionDigits: 1 }) }} kg
                </span>
              </div>
            </div>
          </div>
        </header>
      </div>

      <!-- Main Leaderboard Component Container -->
      <div class="bg-slate-50 rounded-3xl p-2">
        <p v-if="errorMsg" class="mb-4 text-red-500 font-semibold text-center">{{ errorMsg }}</p>

        <!-- Company Leaderboard -->
        <GenericLeaderboard
          v-if="!selectedCompany"
          :items="companies"
          :loading="loading"
          :has-more="companyHasMore"
          :sort-by="companySortBy"
          :desc="companyDesc"
          :highlight-id="myCompany?.id"
          highlight-label="Mon entreprise"
          :is-user-type="false"
          @change-sort="changeSort"
          @load-more="loadMore"
          @click-item="handleItemClick"
        />

        <!-- Employee Leaderboard -->
        <GenericLeaderboard
          v-else
          :items="users"
          :loading="loading"
          :has-more="userHasMore"
          :sort-by="userSortBy"
          :desc="userDesc"
          :highlight-id="currentUser?.id"
          highlight-label="Moi"
          :is-user-type="true"
          @change-sort="changeSort"
          @load-more="loadMore"
        />
      </div>

    </div>
  </div>
</template>

<style scoped>
.animate-fade-in {
  animation: fadeIn 0.3s ease-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(4px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
