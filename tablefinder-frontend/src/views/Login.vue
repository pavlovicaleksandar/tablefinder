<template>
  <v-container fluid>
    <v-row justify="center">
      <v-col cols="12" md="6" lg="4">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark>
            <v-toolbar-title>Login</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form>
              <v-text-field label="Username" v-model="username"></v-text-field>
              <v-text-field label="Password" v-model="password" type="password"></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="login">Login</v-btn>
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
      username: '',
      password: ''
    }
  },
  methods: {
    login() {
      const loginData = {
        email: this.username,
        password: this.password
      };

      axios.post(`http://localhost:8080/users/login`, loginData)
        .then(response => {
          console.log('Successfully signed in', response.data);
          alert('Successfully signed in')
          this.$router.push('/restaurants')
        })
        .catch(error => {
          console.error('Error signing in:', error);
        });
    }
  }
}
</script>
