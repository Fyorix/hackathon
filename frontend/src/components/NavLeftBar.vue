<script setup lang="ts">

import router from '@/router';


const showSitePage = () => {
    router.push(`/declare`);
}
</script>

<template>
  <div class="sidebar flex flex-col">
    <div class="flex justify-center py-4">
      <img
          src="/logo.png"
          alt="Logo"
          class="w-24 h-auto"
      />
    </div>
    <div class="sidebar-header flex-shrink-0">
      <router-link
          class="flex items-center space-x-1 rounded p-1"
          to="/">
        <span>Accueil</span>
      </router-link>
      <router-link
          class="flex items-center space-x-1 rounded p-1"
          to="/declare">
        <span>Déclarer un trajet</span>
      </router-link>
      <router-link
          class="flex items-center space-x-1 rounded p-1"
          to="/shop">
        <span>Boutique RSE</span>
      </router-link>
      <router-link
          class="flex items-center space-x-1 rounded p-1"
          to="/leaderboard">
        <span>Classement</span>
      </router-link>
    </div>
  </div>
</template>



<style scoped>
.sidebar {
  margin-top: 0;
  padding-top: 5px;
  background: var(--color-primblue);
  width: var(--navleftbar-width);
  height: 100vh;
  color: white;
  padding: 5px;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
}

</style>