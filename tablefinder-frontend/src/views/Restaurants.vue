<template>
  <v-container fluid>
    <v-row>
      <v-col v-for="(restaurant, index) in restaurants" :key="index" cols="12" sm="6" md="4" lg="3" xl="2">
          <v-card>
            <v-card-title>{{ restaurant.name }}</v-card-title>
            <v-img src="https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510" class="restaurant-img"></v-img>
            <v-card-actions>
              <v-menu>
                <template v-slot:activator="{ props }">
                  <v-btn icon="mdi-dots-vertical" v-bind="props"></v-btn>
                </template>

                <v-list>
                  <v-list-item>
                    <v-list-item-title>Edit</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="deleteRestaurant(restaurant.id)">
                    <v-list-item-title>Delete</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
              <router-link :to="{ name: 'RestaurantDetails', params: { id: restaurant.id } }" style="text-decoration: none">
                <v-btn color="secondary">View</v-btn>
              </router-link>
              <v-btn color="secondary" @click="showPopup=true">Reserve now</v-btn>
            </v-card-actions>
          </v-card>
      </v-col>
    </v-row>
  </v-container>
  <ReservePopup v-model="showPopup"></ReservePopup>
</template>

<script>
import axios from "axios";
import ReservePopup from "@/components/ReservePopup";

export default {
  components: {
    ReservePopup,
  },
  data() {
    return {
      restaurants: [
      ],
      showPopup: false
    }
  },
  mounted() {
    this.fetchRestaurants()
  },
  methods: {
    fetchRestaurants() {
      fetch('http://localhost:8080/restaurants')
        .then(response => response.json())
        .then(data => this.restaurants = data)
    },
    deleteRestaurant(restaurantId) {
      axios.delete(`http://localhost:8080/restaurants/${restaurantId}`)
        .then(response => {
          console.log('Restaurant deleted:', response.data);
        })
        .catch(error => {
          console.error('Error deleting restaurant:', error);
        });
      location.reload()
    }
  }
}
</script>

<style>
.restaurant-img {
  width: 300px; /* Set the desired width for the card */
  height: 300px; /* Set the desired height for the card */
}
</style>
