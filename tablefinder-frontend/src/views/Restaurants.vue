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
            <v-btn color="primary">Delete</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      restaurants: [
        // { name: 'Restaurant 1', description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.' },
        // { name: 'Restaurant 2', description: 'Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.' },
        // { name: 'Restaurant 3', description: 'Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.' },
        // { name: 'Restaurant 4', description: 'Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.' },
        // { name: 'Restaurant 5', description: 'Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.' },
        // { name: 'Restaurant 6', description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.' },
        // { name: 'Restaurant 7', description: 'Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.' },
        // { name: 'Restaurant 8', description: 'Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.' },
        // { name: 'Restaurant 9', description: 'Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.' },
        // { name: 'Restaurant 10', description: 'Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.' }
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
    }
  }
}
</script>
