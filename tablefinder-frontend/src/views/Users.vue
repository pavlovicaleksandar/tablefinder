<template>
  <v-data-table
    v-model:items-per-page="itemsPerPage"
    :headers="headers"
    :items="users"
    class="elevation-1"
  >
    <template v-slot:[`item.actions`]="{ item }">
      <router-link :to="{ name: 'EditUser', params: { id: item.raw.id } }" style="text-decoration: none">
        <v-btn color="primary" style="min-width: 120px;">
          Edit
        </v-btn>
      </router-link>
      <v-btn @click="deleteUser(item.columns.username)" color="red" class="ml-2" style="min-width: 120px;">
        Delete
      </v-btn>
    </template>
  </v-data-table>
</template>

<script>
import { VDataTable } from 'vuetify/labs/VDataTable'
import axios from "axios";
import {getCurrentlyLoggedInUser} from "@/helpers";

export default {
  components: {
    VDataTable,
  },
  mounted() {
    getCurrentlyLoggedInUser().then(userInfo => {
      this.loggedInUser = userInfo
    })
    this.fetchUsers()
  },
  data() {
    return {
      itemsPerPage: 10,
      headers: [
        {title: 'Username', align: 'start', key: 'username'},
        {title: 'Email', align: 'start', key: 'email'},
        {title: 'Phone number', align: 'start', key: 'phoneNumber'},
        {title: 'Role', align: 'start', key: 'role'},
        {title: 'Actions', value: 'actions', key: 'actions', sortable: false},
      ],
      users: [],
      loggedInUser: {}
    }
  },
  methods: {
    fetchUsers() {
      axios.get('http://localhost:8080/users')
        .then(response => response.data)
        .then(data => this.users = data)
    },
    deleteUser(username) {
      axios.delete(`http://localhost:8080/users/${username}`)
        .then(response => {
          console.log('User deleted:', response.data);
        })
        .catch(error => {
          console.error('Error deleting user:', error);
        });
      location.reload()
    }
  }
};
</script>

<style scoped>

</style>
