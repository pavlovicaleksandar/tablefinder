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
              <v-text-field v-model="name" label="Name" required></v-text-field>
              <v-text-field v-model="imageUrl" label="Image url" required></v-text-field>
              <v-select
                v-model="selectedTags"
                :items="tags"
                item-value="id"
                item-title="name"
                chips
                label="Tags"
                return-object
                multiple
              ></v-select>
              <v-textarea color="secondary" v-model="description"  label="Description" variant="outlined" base-color="primary"></v-textarea>
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
      loggedInUser: null
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
  },
  methods: {
    fetchTags() {
      axios.get('http://localhost:8080/tags')
        .then(response => response.data)
        .then(data => this.tags = data)
    },
    addNewRestaurant() {

      const restaurantData = {
        name: this.name,
        description: this.description,
        imageUrl: this.imageUrl,
        tags: this.selectedTags
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
