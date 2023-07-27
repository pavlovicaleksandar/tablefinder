<template>
  <v-app-bar flat>
    <v-app-bar-title class="blue--text font-weight-bold">
<!--      <v-icon icon="mdi-circle-slice-6" />-->
      <div
        class="text-no-wrap bg-secondary"
        style="width: 4.8rem; font-size: xx-large"
      >
      TableFinder
      </div>
    </v-app-bar-title>
    <div>
      <router-link to="/restaurants" style="color: blue"><v-btn>Restaurants</v-btn></router-link>
      <router-link to="/reservations" v-if="loggedInUser != null" style="color: blue"><v-btn>Reservations</v-btn></router-link>
      <router-link to="/users" v-if="loggedInUser != null && loggedInUser.role === 'Admin'" style="color: blue"><v-btn>Users</v-btn></router-link>
      <router-link to="/registration" v-if="loggedInUser == null" style="color: blue"><v-btn>Register</v-btn></router-link>
      <router-link to="/profile" v-if="loggedInUser != null" style="color: blue"><v-btn>My profile</v-btn></router-link>
      <router-link to="/login" v-if="loggedInUser == null" style="color: blue"><v-btn>Login</v-btn></router-link>
      <v-btn @click="logout" v-if="loggedInUser != null">Logout ({{ loggedInUser.username }})</v-btn>
    </div>
    <div>

    </div>
  </v-app-bar>
</template>

<script>
import { getCurrentlyLoggedInUser } from '../../helpers'

export default {
  data() {
    return {
      loggedInUser: null
    }
  },
  mounted() {
    getCurrentlyLoggedInUser().then(userInfo => {
      this.loggedInUser = userInfo
    })
  },
  methods: {
    logout() {
      localStorage.removeItem("user")
      window.location.reload()
      window.location.href = '/';
    }
  }
}
</script>
