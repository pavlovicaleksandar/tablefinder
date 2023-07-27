<template>
  <v-container fluid>
    <v-row justify="center">
      <v-col cols="12" md="6" lg="4">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark>
            <v-toolbar-title>My profile</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
              <v-text-field readonly v-model="user.username" label="Username" required color="primary"></v-text-field>
              <v-text-field readonly v-model="user.role" label="Role" color="primary"></v-text-field>
              <v-text-field v-model="user.email" label="Email" color="primary"></v-text-field>
              <v-text-field v-model="user.phoneNumber" label="Phone Number" color="primary"></v-text-field>
              <v-text-field v-model="newPassword" label="New password" type="password" color="primary"></v-text-field>
              <v-text-field v-model="confirmNewPassword" label="Confirm new password" type="password" color="primary"></v-text-field>
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
                  <v-btn color="primary" @click="editUser" style="min-width: 120px;">Confirm</v-btn>
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
  data() {
    return {
      user: {},
      newPassword: null,
      confirmNewPassword: null,
      errorMessage: null,
      successMessage: null,
      loggedInUser: {}
    };
  },
  mounted() {
    getCurrentlyLoggedInUser().then(userInfo => {
      this.loggedInUser = userInfo
      this.user = this.getUser(userInfo.userId)
    })
    this.errorMessage = null
    this.successMessage = null
  },
  methods: {
    editUser() {
      // Validate form inputs
      if (!this.user.email) {
        this.errorMessage = "All fields marked with * are required"
        return;
      }

      if (!this.isValidEmail(this.user.email)) {
        this.errorMessage = "Email is in wrong format"
        return
      }
      if (this.newPassword && this.newPassword != this.confirmNewPassword) {
        this.errorMessage = "Passwords are not matching"
        return
      }

      const userData = {
        email: this.user.email,
        password: this.newPassword,
        phoneNumber: this.user.phoneNumber,
        role: this.role
      };


      axios.put(`http://localhost:8080/users/${this.user.id}`, userData)
        .then(response => {
          console.log('Successfully updated user');
          this.successMessage = "Successfully updated profile"
          this.errorMessage = null
          // setTimeout(function(){
          //   window.location.reload();
          // }, 2000);
        })
        .catch(error => {
          console.error('Error updating user:', error);
        });

    },
    isValidEmail(email) {
      return /^[^@]+@\w+(\.\w+)+\w$/.test(email);
    },
    async getUser(id) {
      await axios.get(`http://localhost:8080/users/${id}`)
        .then(response => {
          console.log('Successfully updated user');
          this.user = response.data
        })
        .catch(error => {
          console.error('Error updating user:', error);
        });
    }
  }
};
</script>
