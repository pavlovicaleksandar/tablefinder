<template>
  <v-app-bar flat>
    <v-app-bar-title class="blue--text font-weight-bold">
<!--      <v-icon icon="mdi-circle-slice-6" />-->
      <div
        class="text-no-wrap bg-secondary"
        style="width: 3.6rem; font-size: x-large"
      >
      TableFinder
      </div>
      <div>
        <router-link to="/restaurants"><v-btn>Restaurants</v-btn></router-link>
        <router-link to="/reservations"><v-btn>Reservations</v-btn></router-link>
      </div>
    </v-app-bar-title>
    <div>

    </div>
  </v-app-bar>
</template>

<script setup>
  //
</script>
