import { createRouter, createWebHistory } from 'vue-router'
import GuestHome from '@/views/guest/GuestHome.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'guestHome',
      component: GuestHome
    }
  ]
})

export default router
