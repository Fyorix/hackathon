<script setup lang="ts">
import router from '@/router';
import logoUrl from '@/assets/greenTrip.PNG';
import { clearToken } from '@/api';

const logout = () => {
    clearToken();
    router.push('/login');
}
</script>

<template>
  <nav class="topnav">
    <div class="topnav-inner">
      <!-- Logo -->
      <router-link to="/" class="logo-link">
        <img :src="logoUrl" alt="GreenTrip Logo" class="logo" />
      </router-link>

      <!-- Nav links -->
      <div class="nav-links">
        <router-link class="nav-link" to="/">Accueil</router-link>
        <router-link class="nav-link" to="/declare">Déclarer un trajet</router-link>
        <router-link class="nav-link" to="/leaderboard">Classement</router-link>
        <router-link class="nav-link" to="/profile">Mon Profil</router-link>
      </div>

      <!-- Logout -->
      <button @click="logout" class="logout-btn">
        🚪 Déconnexion
      </button>
    </div>
  </nav>
</template>

<style scoped>
.topnav {
  position: sticky;
  top: 0;
  z-index: 100;
  background: var(--color-primblue);
  width: 100%;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.18);
}

.topnav-inner {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 0 24px;
  height: 62px;
}

.logo-link {
  display: flex;
  align-items: center;
  flex-shrink: 0;
  margin-right: 16px;
}

.logo {
  height: 40px;
  width: auto;
}

.nav-links {
  display: flex;
  align-items: center;
  gap: 4px;
  flex: 1;
}

.nav-link {
  color: rgba(255, 255, 255, 0.8) !important;
  text-decoration: none;
  font-weight: 500;
  font-size: 0.92rem;
  padding: 8px 16px;
  border-radius: 10px;
  transition: all 0.18s ease-in-out;
  white-space: nowrap;
}

.nav-link:hover {
  color: white !important;
  background-color: rgba(255, 255, 255, 0.12);
}

.nav-link.router-link-active {
  color: white !important;
  background-color: var(--color-primgreen) !important;
  font-weight: 600;
  box-shadow: 0 3px 10px rgba(27, 122, 67, 0.35);
}

.logout-btn {
  margin-left: auto;
  flex-shrink: 0;
  color: rgba(255, 255, 255, 0.75);
  background: transparent;
  border: 1px solid rgba(255, 255, 255, 0.18);
  font-weight: 500;
  font-size: 0.88rem;
  padding: 7px 16px;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.18s ease-in-out;
}

.logout-btn:hover {
  color: #ff6b6b;
  border-color: rgba(255, 107, 107, 0.4);
  background-color: rgba(255, 107, 107, 0.08);
}
</style>