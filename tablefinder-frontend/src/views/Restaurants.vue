<template>
  <v-container fluid>
    <v-row justify="start">
      <v-col cols="12" sm="6" md="4" lg="3" xl="2">
        <v-text-field v-model="search" label="Search by name" outlined color="primary"></v-text-field>
      </v-col>
      <v-col cols="6" sm="4" md="3" lg="2" xl="1">
        <v-select
          color="primary"
          v-model="selectedOptionForRating"
          label="Rating"
          variant="outlined"
          :items="ratingOptions"
          item-value="value"
          item-title="displayText"
          return-object
          single-line
        ></v-select>
      </v-col>
      <v-col cols="6" sm="4" md="3" lg="2" xl="1">
        <v-select
          color="primary"
          v-model="selectedOptionForPrice"
          label="Price"
          variant="outlined"
          :items="priceOptions"
          item-value="value"
          item-title="displayText"
          return-object
          single-line
        ></v-select>
      </v-col>
      <v-col cols="6" sm="4" md="3" lg="2" xl="1">
        <v-btn color="primary" @click="filterRestaurants()" style="min-width: 120px;">Filter</v-btn>
      </v-col>
      <v-col cols="6" sm="4" md="3" lg="2" xl="1">
        <router-link v-if="loggedInUser != null && loggedInUser.role === 'Admin'" to="/restaurants/add">
          <v-btn color="primary" style="min-width: 120px;">Add new</v-btn>
        </router-link>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-select
          v-model="selectedTags"
          :items="tags"
          item-value="id"
          item-title="name"
          chips
          label="Tags"
          multiple
        ></v-select>
      </v-col>
    </v-row>
    <v-row v-if="filteredRestaurants.length === 0" style="align-items: center; justify-content: center">
      No restaurants found
    </v-row>
    <v-row>
      <v-col v-for="(restaurant, index) in filteredRestaurants" :key="index" cols="12" sm="6" md="4" lg="3">
        <v-card class="restaurant-card">
          <v-card-title>{{ restaurant.name }}</v-card-title>
          <v-card-text>
            <v-rating readonly v-model="restaurant.rating" color="primary" half-increments></v-rating>
          </v-card-text>
          <v-card-text>
            Number of reviews: ({{ restaurant.numberOfRatings }})
          </v-card-text>
          <v-card-text>Price: {{ ['N/A', '$', '$$', '$$$'].at(restaurant.price) }}</v-card-text>
          <v-img :src="restaurant.imageUrl" class="restaurant-img" contain></v-img>
          <v-card-actions>
            <v-menu v-if="loggedInUser != null && loggedInUser.role !== 'Guest'">
              <template v-slot:activator="{ props }">
                <v-btn icon="mdi-dots-vertical" v-bind="props"></v-btn>
              </template>
              <v-list>
                <v-list-item>
                  <router-link :to="{ name: 'EditRestaurant', params: { id: restaurant.id } }" style="text-decoration: none; color: black">
                    <v-list-item-title>Edit</v-list-item-title>
                  </router-link>
                </v-list-item>
                <v-list-item @click="deleteRestaurant(restaurant.id)">
                  <v-list-item-title>Delete</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
            <router-link :to="{ name: 'RestaurantDetails', params: { id: restaurant.id } }" style="text-decoration: none">
              <v-btn color="primary">View</v-btn>
            </router-link>
            <ReservePopup v-if="loggedInUser != null" :restaurant="{ id: restaurant.id, name: restaurant.name }"></ReservePopup>
            <router-link  to="/login"><v-btn v-if="loggedInUser == null" color="primary">Reserve now</v-btn></router-link>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>


<script>
import axios from "axios";
import ReservePopup from "@/components/ReservePopup";
import {getCurrentlyLoggedInUser} from "@/helpers";

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
      tags: [],
      selectedTags: [],
      loggedInUser: null
    }
  },
  mounted() {
    if (localStorage.getItem( 'user') != null) {
      getCurrentlyLoggedInUser().then(userInfo => {
        this.loggedInUser = userInfo
      })
    }
    this.filterRestaurants()
    this.fetchTags()
  },
  methods: {
    fetchTags() {
      axios.get('http://localhost:8080/tags')
        .then(response => response.data)
        .then(data => this.tags = data)
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
      axios.get(`http://localhost:8080/restaurants?priceFilter=${this.selectedOptionForPrice.value}&ratingFilter=${this.selectedOptionForRating.value}`)
        .then(response => response.data)
        .then(data => this.restaurants = data)
    },
    areRestaurantTagsInFilteredTags(restaurantTags, filteredTags) {
      const set1 = new Set(restaurantTags.map(item => item['tagId']));
      const set2 = new Set(filteredTags);

      for (const item of set1) {
        if (set2.has(item)) {
          return true
        }
      }

      return false;
    },
  },
  computed: {
    filteredRestaurants() {
      const searchTerm = this.search.toLowerCase();
      return this.restaurants
        .filter((restaurant) => {
          return restaurant.name.toLowerCase().includes(searchTerm);
        })
        .filter((restaurant) => {
          console.log(JSON.stringify(restaurant.tags))
          console.log(JSON.stringify(this.selectedTags))
          return this.selectedTags.length == 0 || this.areRestaurantTagsInFilteredTags(restaurant.tags, this.selectedTags)
        })
    }
  }
}
</script>

