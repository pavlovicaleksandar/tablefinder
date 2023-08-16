// Composables
import { createRouter, createWebHistory } from 'vue-router'
import axios from "axios";
import {getCurrentlyLoggedInUser} from "@/helpers";

let loggedInUser = JSON.parse(localStorage.getItem( 'user'))

if (loggedInUser != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem( 'user')).token}`
}

const routes = [
  {
    path: '/',
    component: () => import('@/layouts/default/Default.vue'),
    children: [
      {
        path: '',
        name: 'Home',
        beforeEnter: (to, from) => {
          window.location.href = '/restaurants'
        },
      },
      {
        path: 'login',
        name: 'Login',
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
        path: 'users/:id',
        name: 'EditUser',
        component: () => import(/* webpackChunkName: "home" */ '@/views/EditUser.vue'),
        props: true,
        beforeEnter: (to, from) => {
          getCurrentlyLoggedInUser().then(userInfo => {
            if (userInfo == null) {
              window.location.href = '/login'
            }
          })
        },
      },
      {
        path: 'restaurants/edit/:id',
        name: 'EditRestaurant',
        component: () => import(/* webpackChunkName: "home" */ '@/views/EditRestaurant.vue'),
        props: true,
        beforeEnter: (to, from) => {
          getCurrentlyLoggedInUser().then(userInfo => {
            if (userInfo == null) {
              window.location.href = '/login'
            }
          })
        },
      },
      {
        path: 'reservations',
        name: 'Reservations',
        component: () => import(/* webpackChunkName: "home" */ '@/views/Reservations.vue'),
        props: true,
        beforeEnter: (to, from) => {
          // reject the navigation
          getCurrentlyLoggedInUser().then(userInfo => {
            if (userInfo == null) {
              window.location.href = '/login'
            }
          })
        },
      },
      {
        path: 'users',
        name: 'Users',
        component: () => import(/* webpackChunkName: "home" */ '@/views/Users.vue'),
        props: true,
        beforeEnter: (to, from) => {
          // reject the navigation
          getCurrentlyLoggedInUser().then(userInfo => {
            if (userInfo == null) {
              window.location.href = '/login'
            }
          })
        },
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
        props: true,
        beforeEnter: (to, from) => {
          // reject the navigation
          getCurrentlyLoggedInUser().then(userInfo => {
            if (userInfo == null) {
              window.location.href = '/login'
            }
          })
        },
      },
      {
        path: 'profile',
        name: 'MyProfile',
        component: () => import(/* webpackChunkName: "home" */ '@/views/MyProfile.vue'),
        props: true,
        beforeEnter: (to, from) => {
          // reject the navigation
          getCurrentlyLoggedInUser().then(userInfo => {
            if (userInfo == null) {
              window.location.href = '/login'
            }
          })
        },
      }
    ],
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router
