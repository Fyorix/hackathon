<script setup lang="ts">
import router from '@/router';
import logoUrl from '@/assets/greenTrip.PNG';

const showSitePage = () => {
    router.push(`/declare`);
}
</script>

<template>
  <div class="sidebar flex flex-col">
    <div class="flex justify-center py-4">
      <img
          :src="logoUrl"
          alt="Logo"
          class="w-24 h-auto"
      />
    </div>
    <div class="sidebar-header flex-shrink-0 flex flex-col gap-2 p-2">
      <router-link
          class="nav-link flex items-center space-x-2 rounded-xl"
          to="/">
        <span>Accueil</span>
      </router-link>
      <router-link
          class="nav-link flex items-center space-x-2 rounded-xl"
          to="/declare">
        <span>Déclarer un trajet</span>
      </router-link>
      <router-link
          class="nav-link flex items-center space-x-2 rounded-xl"
          to="/leaderboard">
        <span>Classement</span>
      </router-link>
      <router-link
          class="nav-link flex items-center space-x-2 rounded-xl"
          to="/profile">
        <span>Mon Profil</span>
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

.nav-link {
  color: rgba(255, 255, 255, 0.8) !important;
  text-decoration: none;
  font-weight: 500;
  padding: 10px 14px;
  transition: all 0.2s ease-in-out;
}

.nav-link:hover {
  color: white !important;
  background-color: rgba(255, 255, 255, 0.1);
  background-image: none !important;
}

.nav-link.router-link-active {
  color: white !important;
  background-color: var(--color-primgreen) !important;
  font-weight: 600;
  box-shadow: 0 4px 12px rgba(27, 122, 67, 0.35);
}
</style>