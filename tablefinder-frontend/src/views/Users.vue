<template>
  <v-data-table
    v-model:items-per-page="itemsPerPage"
    :headers="headers"
    :items="users"
    class="elevation-1"
  >
    <template v-slot:[`item.actions`]="{ item }">
      <v-btn color="secondary">
        Edit
      </v-btn>
      <v-btn @click="deleteUser(item.columns.username)" color="red" class="ml-2">
        Delete
      </v-btn>
    </template>
  </v-data-table>
</template>

<script>
import { VDataTable } from 'vuetify/labs/VDataTable'
import axios from "axios";

export default {
  components: {
    VDataTable,
  },
  mounted() {
    this.fetchUsers()
  },
  data() {
    return {
      itemsPerPage: 10,
      headers: [
        {title: 'Username', align: 'start', key: 'username'},
        {title: 'Email', align: 'start', key: 'email'},
        {title: 'Is active', align: 'start', key: 'isActive'},
        {title: 'Phone number', align: 'start', key: 'phoneNumber'},
        {title: 'role', align: 'start', key: 'role'},
        {title: 'Actions', value: 'actions', key: 'actions', sortable: false},
      ],
      users: []
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

