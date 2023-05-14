<template>
  <v-container fluid>
    <v-row>
      <v-col v-for="(restaurant, index) in restaurants" :key="index" cols="12" sm="6" md="4" lg="3" xl="2">
        <v-card>
          <v-card-title>{{ restaurant.name }}</v-card-title>
          <v-card-text>{{ restaurant.description }}</v-card-text>
          <v-card-actions>
            <v-btn color="primary">Edit</v-btn>
            <v-btn color="primary">Add</v-btn>
            <v-btn @click="deleteRestaurant(restaurant.id)" color="primary">Delete</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      restaurants: [
      ]
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
