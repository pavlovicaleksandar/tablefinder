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
      <router-link to="/restaurants" v-if="user != null"><v-btn>Restaurants</v-btn></router-link>
      <router-link to="/reservations" v-if="user != null"><v-btn>Reservations</v-btn></router-link>
      <router-link to="/users" v-if="user != null"><v-btn>Users</v-btn></router-link>
      <router-link to="/registration" v-if="user == null"><v-btn>Register</v-btn></router-link>
      <router-link to="/" v-if="user == null"><v-btn>Login</v-btn></router-link>
      <v-btn @click="logout" v-if="user != null">Logout ({{user.username}})</v-btn>
    </div>
    <div>

    </div>
  </v-app-bar>
</template>

<script>
export default {
  data() {
    return {
      user: null
    }
  },
  mounted() {
    this.user = JSON.parse(localStorage.getItem("user"))
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
