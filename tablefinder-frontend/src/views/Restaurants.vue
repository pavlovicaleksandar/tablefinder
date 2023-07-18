<template>
  <v-container fluid>
    <v-row justify="start">
      <v-col cols="3">
        <v-text-field v-model="search" label="Search by name" outlined></v-text-field>
      </v-col>
      <v-col cols="2">
        <v-select color="secondary"
                  v-model="selectedOptionForRating"
                  label="Rating"
                  variant="outlined"
                  :items="ratingOptions"
                  item-value="value"
                  item-title="displayText"
                  return-object
                  single-line
        >
        </v-select>
      </v-col>
      <v-col cols="2">
        <v-select color="secondary"
                  v-model="selectedOptionForPrice"
                  label="Price"
                  variant="outlined"
                  :items="priceOptions"
                  item-value="value"
                  item-title="displayText"
                  return-object
                  single-line
        >
        </v-select>
      </v-col>
      <v-col cols="2" >
        <v-btn color="secondary" @click="filterRestaurants()">Filter</v-btn>
      </v-col>
      <v-col cols="2" >
        <router-link to="/restaurants/add"><v-btn color="secondary">Add new</v-btn></router-link>
      </v-col>
    </v-row>
    <v-row v-if="filteredRestaurants.length == 0" style="align-items: center;justify-content: center">
        No restaurants found
    </v-row>
    <v-row  >
      <v-col v-for="(restaurant, index) in filteredRestaurants" :key="index" cols="12" sm="6" md="4" lg="3" xl="2">
          <v-card>
            <v-card-title>{{ restaurant.name }}</v-card-title>
            <v-card-text>
              <v-row>
                <v-rating readonly v-model="restaurant.rating" color="secondary"></v-rating>
              </v-row>
              <v-row>
                  Number of reviews: ({{restaurant.numberOfRatings}})
              </v-row>
            </v-card-text>
            <v-card-text>Price: {{['N/A', '$', '$$', '$$$'].at(restaurant.price)}}</v-card-text>
            <v-img :src="restaurant.imageUrl" class="restaurant-img"></v-img>
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
              <ReservePopup :restaurant="{id: restaurant.id, name: restaurant.name}">
              </ReservePopup>
            </v-card-actions>
          </v-card>
      </v-col>
    </v-row>
  </v-container>
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
      restaurants: [],
      showPopup: false,
      search: '',
      priceOptions: [
        { displayText: 'All prices', value: 0 },
        { displayText: '$', value: 1 },
        { displayText: '$$', value: 2 },
        { displayText: '$$$', value: 3 }
      ],
      selectedOptionForPrice: { displayText: 'All prices', value: 0 },
      ratingOptions: [
        { displayText: 'All ratings', value: 0 },
        { displayText: 'above 2 stars', value: 2 },
        { displayText: 'above 3 stars', value: 3 },
        { displayText: 'above 4 stars', value: 4 },
      ],
      selectedOptionForRating: { displayText: 'All ratings', value: 0 },
    }
  },
  mounted() {
    this.filterRestaurants()
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
    },
    filterRestaurants() {
      fetch(`http://localhost:8080/restaurants?priceFilter=${this.selectedOptionForPrice.value}&ratingFilter=${this.selectedOptionForRating.value}`)
        .then(response => response.json())
        .then(data => this.restaurants = data)
    }
  },
  computed: {
    filteredRestaurants() {
      const searchTerm = this.search.toLowerCase();
      return this.restaurants.filter((restaurant) => {
        return restaurant.name.toLowerCase().includes(searchTerm);
      });
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
