// Composables
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    component: () => import('@/layouts/default/Default.vue'),
    children: [
      {
        path: '',
        name: 'Home',
        component: () => import(/* webpackChunkName: "home" */ '@/views/Login.vue'),
      },
      {
        path: 'restaurants',
        name: 'Restaurants',
        component: () => import(/* webpackChunkName: "home" */ '@/views/Restaurants.vue'),
      },
      {
        path: 'restaurants/:id',
        name: 'RestaurantDetails',
        component: () => import(/* webpackChunkName: "home" */ '@/views/RestaurantDetails.vue'),
        props: true
      },
      {
        path: 'restaurants/edit/:id',
        name: 'EditRestaurant',
        component: () => import(/* webpackChunkName: "home" */ '@/views/EditRestaurant.vue'),
        props: true
      },
      {
        path: 'reservations',
        name: 'Reservations',
        component: () => import(/* webpackChunkName: "home" */ '@/views/Reservations.vue'),
        props: true
      },
      {
        path: 'users',
        name: 'Users',
        component: () => import(/* webpackChunkName: "home" */ '@/views/Users.vue'),
        props: true
      },
      {
        path: 'registration',
        name: 'UserRegistration',
        component: () => import(/* webpackChunkName: "home" */ '@/views/UserRegistration.vue'),
        props: true
      },
      {
        path: 'restaurants/add',
        name: 'AddNewRestaurant',
        component: () => import(/* webpackChunkName: "home" */ '@/views/AddNewRestaurant.vue'),
        props: true
      },
      {
        path: 'profile',
        name: 'MyProfile',
        component: () => import(/* webpackChunkName: "home" */ '@/views/MyProfile.vue'),
        props: true
      }
    ],
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router
