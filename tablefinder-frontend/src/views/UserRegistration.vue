<template>
  <v-container fluid>
    <v-row justify="center">
      <v-col cols="12" md="6" lg="4">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark>
            <v-toolbar-title>Register</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form @submit.prevent="registerUser">
              <v-text-field v-model="username" label="*Username" required></v-text-field>
              <v-text-field v-model="email" label="*Email" required></v-text-field>
              <v-text-field v-model="password" label="*Password" type="password" required></v-text-field>
              <v-text-field v-model="confirmPassword" label="*Confirm Password" type="password" required></v-text-field>
              <v-text-field v-model="phoneNumber" label="Phone Number"></v-text-field>
              <v-card-text v-if="message != null" class="mt-n6">
                <v-alert color="error">
                  {{message}}
                </v-alert>
              </v-card-text>
              <v-row>
                <v-col class="text-center">
                  <v-btn type="submit" color="primary" style="min-width: 120px;">Register</v-btn>
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
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
      phoneNumber: '',
      message: null
    };
  },
  mounted() {
    getCurrentlyLoggedInUser().then(userInfo => {
      if (userInfo != null) {
        window.location.href = '/restaurants'
      }
    })
    this.message = null
  },
  methods: {
    registerUser() {
      // Validate form inputs
      if (!this.username || !this.email || !this.password || !this.confirmPassword) {
        this.message = "All fields marked with * are required"
        return;
      }

      if (this.password !== this.confirmPassword) {
        this.message = "Passwords are not matching"
        return;
      }

      if (!this.isValidEmail(this.email)) {
        this.message = "Email is in wrong format"
        return
      }

      // Make API call to register the user with the provided data
      const userData = {
        username: this.username,
        email: this.email,
        password: this.password,
        phoneNumber: this.phoneNumber
      };


      axios.post('http://localhost:8080/users/register', userData)
        .then(response => {
          console.log('Successfully registered');
          this.$router.push('/')
        })
        .catch(error => {
          console.error('Error registration:', error);
        });

    },
    isValidEmail(email) {
      return /^[^@]+@\w+(\.\w+)+\w$/.test(email);
    }
  }
};
</script>
