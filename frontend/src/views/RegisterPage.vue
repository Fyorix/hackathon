<script setup lang="ts">
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { UsersService } from '@/api'

const route = useRoute()
const router = useRouter()

const name = ref('')
const email = ref('')
const password = ref('')
const companyId = ref<number | null>(null)
const loading = ref(false)
const error = ref('')

async function submit() {
  error.value = ''
  if (!name.value || !email.value || !password.value || companyId.value === null) {
    error.value = 'Tous les champs sont requis'
    return
  }
  loading.value = true
  try {
    await UsersService.register({ name: name.value.trim(), email: email.value.trim(), password: password.value, companyId: Number(companyId.value) })
    await UsersService.login({ email: email.value.trim(), password: password.value })
    const redirect = (route.query.redirect as string) || '/'
    router.replace(redirect)
  } catch (e: any) {
    error.value = 'Inscription impossible'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="flex items-center justify-center min-h-screen bg-gray-100 p-4">
    <div class="w-full max-w-md bg-white rounded-lg shadow p-6">
      <h1 class="text-2xl font-semibold mb-6 text-gray-800">Créer un compte</h1>
      <form @submit.prevent="submit" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Nom</label>
          <input v-model="name" type="text" class="w-full text-black border border-black rounded px-3 py-2 focus:outline-none focus:ring" autocomplete="name" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Email</label>
          <input v-model="email" type="email" class="w-full text-black border border-black rounded px-3 py-2 focus:outline-none focus:ring" autocomplete="email" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Mot de passe</label>
          <input v-model="password" type="password" class="w-full text-black border-black border rounded px-3 py-2 focus:outline-none focus:ring" autocomplete="new-password" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">ID de l'entreprise</label>
          <input v-model.number="companyId" type="number" min="1" class="w-full border text-black border-black rounded px-3 py-2 focus:outline-none focus:ring" />
        </div>
        <div v-if="error" class="text-red-600 text-sm">{{ error }}</div>
        <button type="submit" :disabled="loading" class="w-full border-black text-black bg-green-600 text-white py-2 rounded hover:bg-green-700 disabled:opacity-50">
          {{ loading ? 'Création...' : "S'inscrire" }}
        </button>
      </form>
      <div class="text-center text-sm mt-4">
        <router-link class="text-green-700 hover:underline" to="/login">Déjà un compte ? Se connecter</router-link>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>
