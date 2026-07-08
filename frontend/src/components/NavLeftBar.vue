<script setup lang="ts">
import router from '@/router';
import logoUrl from '@/assets/greenTrip.PNG';
import { clearToken } from '@/api';

const navItems = [
  { to: '/', label: 'Accueil', icon: 'pi pi-home' },
  { to: '/declare', label: 'Déclarer un trajet', icon: 'pi pi-plus-circle' },
  { to: '/leaderboard', label: 'Classement', icon: 'pi pi-trophy' },
  { to: '/profile', label: 'Mon Profil', icon: 'pi pi-user' },
];

const logout = () => {
    clearToken();
    router.push('/login');
}
</script>

<template>
  <nav class="floating-nav">
    <div class="bubble logo-bubble">
      <span class="bubble-circle logo-circle">
        <img :src="logoUrl" alt="GreenTrip Logo" class="logo-img" />
      </span>
      <span class="bubble-label">GreenTrip</span>
    </div>

    <router-link
      v-for="item in navItems"
      :key="item.to"
      :to="item.to"
      class="bubble nav-bubble"
      active-class="bubble-active"
    >
      <span class="bubble-circle">
        <i :class="item.icon"></i>
      </span>
      <span class="bubble-label">{{ item.label }}</span>
    </router-link>

    <button class="bubble logout-bubble" @click="logout">
      <span class="bubble-circle">
        <i class="pi pi-sign-out"></i>
      </span>
      <span class="bubble-label">Déconnexion</span>
    </button>
  </nav>
</template>

<style scoped>
.floating-nav {
  position: fixed;
  top: 24px;
  left: 24px;
  z-index: 200;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 14px;
}

.bubble {
  display: flex;
  align-items: center;
  gap: 12px;
  text-decoration: none;
  border: none;
  background: transparent;
  padding: 0;
  cursor: pointer;
}

.bubble-label {
  color: #fff;
  background: rgba(27, 60, 43, 0.85);
  font-weight: 600;
  font-size: 0.85rem;
  white-space: nowrap;
  padding: 6px 12px;
  border-radius: 8px;
  max-width: 0;
  opacity: 0;
  overflow: hidden;
  transition: max-width 0.25s ease, opacity 0.2s ease, padding 0.25s ease;
}

.bubble:hover .bubble-label {
  max-width: 220px;
  opacity: 1;
}

.bubble-circle {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 52px;
  height: 52px;
  border-radius: 50%;
  background: var(--color-primgreen, #1b7a43);
  color: #fff;
  font-size: 1.3rem;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.25);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.bubble:hover .bubble-circle {
  transform: scale(1.15);
  box-shadow: 0 5px 16px rgba(0, 0, 0, 0.35);
}

.bubble-active .bubble-circle {
  box-shadow: 0 0 0 3px #fff, 0 3px 10px rgba(0, 0, 0, 0.25);
}

.logout-bubble .bubble-circle {
  background: #b3312c;
}

.logo-bubble {
  cursor: default;
}

.logo-bubble .bubble-circle {
  width: 64px;
  height: 64px;
  background: #fff;
  overflow: hidden;
}

.logo-img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
}

@media (max-width: 767px) {
  .floating-nav {
    top: auto;
    bottom: 0;
    left: 0;
    right: 0;
    width: 100%;
    flex-direction: row;
    align-items: center;
    justify-content: space-around;
    gap: 0;
    padding: 8px 6px;
    background: rgba(255, 255, 255, 0.97);
    box-shadow: 0 -2px 14px rgba(0, 0, 0, 0.15);
  }

  .logo-bubble {
    display: none;
  }

  .bubble-label {
    display: none;
  }

  .bubble-circle {
    width: 46px;
    height: 46px;
    font-size: 1.15rem;
  }
}
</style>
