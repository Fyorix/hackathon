<script setup>
import NavLeftBar from './components/NavLeftBar.vue'
</script>

<template>
  <div class="flex flex-col h-screen w-screen bg-gray-100">
    <NavLeftBar v-if="$route.path !== '/login' && $route.path !== '/register'" />
    <main
      class="flex-1 overflow-auto"
      :class="{ 'pl-24 md:pl-28': $route.path !== '/login' && $route.path !== '/register' }"
    >
      <router-view />
    </main>
  </div>
</template>

<style>

</style>
