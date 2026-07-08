<script setup lang="ts">
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { UsersService } from '@/api'

const route = useRoute()
const router = useRouter()

const email = ref('')
const password = ref('')
const loading = ref(false)
const error = ref('')

async function submit() {
  error.value = ''
  if (!email.value || !password.value) {
    error.value = 'Email et mot de passe requis'
    return
  }
  loading.value = true
  try {
    await UsersService.login({ email: email.value.trim(), password: password.value })
    const redirect = (route.query.redirect as string) || '/'
    router.replace(redirect)
  } catch (e: any) {
    error.value = 'Identifiants invalides'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="flex items-center justify-center min-h-screen bg-gray-100 p-4">
    <div class="w-full max-w-md bg-white rounded-lg shadow p-6">
      <h1 class="text-2xl font-semibold mb-6 text-gray-800">Connexion</h1>
      <form @submit.prevent="submit" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Email</label>
          <input v-model="email" type="email" class="w-full text-black border rounded px-3 py-2 focus:outline-none focus:ring" autocomplete="email" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Mot de passe</label>
          <input v-model="password" type="password" class="w-full text-black border rounded px-3 py-2 focus:outline-none focus:ring" autocomplete="current-password" />
        </div>
        <div v-if="error" class="text-red-600 text-sm">{{ error }}</div>
        <div class="pt-6">
          <button type="submit" :disabled="loading" class="w-full bg-[var(--color-primgreen)] text-white py-2 rounded hover:opacity-90 disabled:opacity-50">
            {{ loading ? 'Connexion...' : 'Se connecter' }}
          </button>
        </div>
      </form>
      <div class="text-center text-sm mt-4">
        <router-link class="text-green-700 hover:underline" to="/register">Pas de compte ? S'inscrire</router-link>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>
