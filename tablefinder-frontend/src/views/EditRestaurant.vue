<template>
  <v-container fluid>
    <v-row justify="center">
      <v-col cols="12" md="6" lg="4">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark>
            <v-toolbar-title>Edit restaurant</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-text-field v-model="restaurant.name" label="Name" color="primary"></v-text-field>
            <v-text-field v-model="restaurant.imageUrl" label="Image url" color="primary"></v-text-field>
            <v-select
              v-model="restaurant.tags"
              :items="tags"
              item-value="id"
              item-title="name"
              chips
              label="Tags"
              return-object
              multiple
              color="primary"
            ></v-select>
            <v-select
              v-model="restaurant.moderatorUsername"
              :items="moderators"
              item-value="username"
              item-title="username"
              label="Moderator"
              color="primary"
            ></v-select>
            <v-textarea v-model="restaurant.description" label="Description" color="primary"></v-textarea>
            <v-card-text v-if="errorMessage != null" class="mt-n6">
              <v-alert color="error">
                {{errorMessage}}
              </v-alert>
            </v-card-text>
            <v-card-text v-if="successMessage != null" class="mt-n6">
              <v-alert color="success">
                {{successMessage}}
              </v-alert>
            </v-card-text>
            <v-row>
              <v-col class="text-center">
                <v-btn color="primary" @click="editRestaurant">Edit</v-btn>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import {getCurrentlyLoggedInUser} from "@/helpers";

export default {
  props: {
    id: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      restaurant: {},
      tags: [],
      errorMessage: null,
      successMessage: null,
      loggedInUser: {},
      moderators: [],
    };
  },
  mounted() {
    getCurrentlyLoggedInUser().then(userInfo => {
      this.loggedInUser = userInfo
      if (userInfo == null) {
        window.location.href = '/'
      }
    })
    this.errorMessage = null
    this.successMessage = null
    this.fetchTags()
    this.fetchRestaurantById()
    this.fetchModerators()
  },
  methods: {
    fetchRestaurantById() {
      axios.get(`http://localhost:8080/restaurants/${this.id}`)
        .then(response => response.data)
        .then(data => this.restaurant = data)
        .then(data => this.restaurant.tags = this.restaurant.tags.map(item => {
          return {
            id: item.tagId,
            name: item.tagName
          }
        }))
    },
    fetchModerators() {
      axios.get('http://localhost:8080/users/moderators')
        .then(response => response.data)
        .then(data => this.moderators = data)
    },
    fetchTags() {
      axios.get('http://localhost:8080/tags')
        .then(response => response.data)
        .then(data => this.tags = data)
    },
    editRestaurant() {
      if (!this.restaurant.name || !this.restaurant.description || !this.restaurant.imageUrl || !this.restaurant.moderatorUsername) {
        this.errorMessage = "All fields marked with * are required"
        return
      }

      const restaurantData = {
        name: this.restaurant.name,
        description: this.restaurant.description,
        imageUrl: this.restaurant.imageUrl,
        tags: this.restaurant.tags,
        moderatorUsername: this.restaurant.moderatorUsername
      };

      axios.put(`http://localhost:8080/restaurants/${this.restaurant.id}`, restaurantData)
        .then(response => {
          this.successMessage = "Successfully edited restaurant"
          console.log('Successfully created new restaurant');
        })
        .catch(error => {
          console.error('Error creating new restaurant:', error);
        });
    }
  }
};
</script>
