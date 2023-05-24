<template>
  <v-data-table
    v-model:items-per-page="itemsPerPage"
    :headers="headers"
    :items="users"
    class="elevation-1"
  >
  </v-data-table>
</template>

<script>
import { VDataTable } from 'vuetify/labs/VDataTable'

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
        {title: 'User ID', align: 'start', key: 'userId'},
        {title: 'Username', align: 'start', key: 'username'},
        {title: 'Email', align: 'start', key: 'email'},
        {title: 'Is active', align: 'start', key: 'isActive'},
        {title: 'Phone number', align: 'start', key: 'phoneNumber'},
        {title: 'role', align: 'start', key: 'role'},
        {title: 'Actions', value: 'actions', sortable: false},
      ],
      users: [
        {
          userId: 1,
          username: 'fake-username',
          email: '123',
          phoneNumber: '456',
          isActive: true,
          role: 'Admin'
        },
      ]
    }
  },
  methods: {
    fetchUsers() {
      fetch('http://localhost:8080/users')
        .then(response => response.json())
        .then(data => this.users = data)
    }
  }
};
</script>

<style>
/* Add custom styles for the component */
</style>
