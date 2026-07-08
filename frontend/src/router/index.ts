import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '@/views/HomePage.vue'
import DeclarePage from '@/views/DeclarePage.vue'
import ShopPage from '@/views/ShopPage.vue'
import LoginPage from '@/views/LoginPage.vue'
import RegisterPage from '@/views/RegisterPage.vue'
import LeaderboardPage from '@/views/LeaderboardPage.vue'
import { isAuthenticated } from '@/api'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/login',
            name: 'login',
            component: LoginPage,
            meta: { public: true }
        },
        {
            path: '/register',
            name: 'register',
            component: RegisterPage,
            meta: { public: true }
        },
        {
            path: '/',
            name: 'home',
            component: HomePage,
        },
        {
            path: '/declare',
            name: 'declare',
            component: DeclarePage,
        },
        {
            path: '/shop',
            name: 'shop',
            component: ShopPage,
        },
        {
            path: '/leaderboard',
            name: 'leaderboard',
            component: LeaderboardPage,
        },
    ],
    scrollBehavior() {
        return { left: 0, top: 0 }
    },
})

router.beforeEach((to, from, next) => {
    const isPublic = !!to.meta.public
    if (!isPublic && !isAuthenticated()) {
        return next({ path: '/login', query: { redirect: to.fullPath } })
    }
    if ((to.path === '/login' || to.path === '/register') && isAuthenticated()) {
        return next((to.query.redirect as string) || '/')
    }
    next()
})

export default router
