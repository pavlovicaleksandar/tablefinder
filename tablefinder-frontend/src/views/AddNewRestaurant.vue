<template>
  <v-btn variant="text" @click="$router.back()">
    <v-icon start icon="mdi-arrow-left"></v-icon>
    Back
  </v-btn>
  <v-container fluid>
    <v-row justify="center">
      <v-col cols="12" md="8" lg="8">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark>
            <v-toolbar-title>Add new restaurant</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form @submit.prevent="addNewRestaurant">
              <v-text-field v-model="name" label="Name" required color="primary"></v-text-field>
              <v-text-field v-model="imageUrl" label="Image url" required color="primary"></v-text-field>
              <v-select
                v-model="selectedTags"
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
                v-model="selectedModerator"
                :items="moderators"
                item-value="username"
                item-title="username"
                label="Moderator"
                color="primary"
              ></v-select>
              <v-textarea color="primary" v-model="description"  label="Description" variant="outlined" base-color="primary"></v-textarea>
              <v-card-text v-if="errorMessage != null">
                <v-alert color="error">
                  {{errorMessage}}
                </v-alert>
              </v-card-text>
              <v-row>
                <v-col class="text-center">
                  <v-btn type="submit" color="primary">Confirm</v-btn>
                </v-col>
              </v-row>
            </v-form>
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
  data() {
    return {
      name: '',
      description: '',
      imageUrl: '',
      tags: [],
      selectedTags: [],
      moderators: [],
      selectedModerator: null,
      loggedInUser: {},
      errorMessage: null
    };
  },
  mounted() {
    getCurrentlyLoggedInUser().then(userInfo => {
      this.loggedInUser = userInfo
      if (userInfo == null) {
        window.location.href = '/'
      }
    })
    this.fetchTags()
    this.fetchModerators()
  },
  methods: {
    fetchTags() {
      axios.get('http://localhost:8080/tags')
        .then(response => response.data)
        .then(data => this.tags = data)
    },
    fetchModerators() {
      axios.get('http://localhost:8080/users/moderators')
        .then(response => response.data)
        .then(data => this.moderators = data)
    },
    addNewRestaurant() {
      if (!this.name || !this.description || !this.imageUrl || !this.selectedModerator) {
        this.errorMessage = "All fields marked with * are required"
        return
      }

      const restaurantData = {
        name: this.name,
        description: this.description,
        imageUrl: this.imageUrl,
        tags: this.selectedTags,
        moderatorUsername: this.selectedModerator
      };
      console.log(JSON.stringify(restaurantData))
      axios.post('http://localhost:8080/restaurants', restaurantData)
        .then(response => {
          console.log('Successfully created new restaurant');
          this.$router.push(`/restaurants/${response.data.id}`)
        })
        .catch(error => {
          console.error('Error creating new restaurant:', error);
        });

    },
  }
};
</script>
