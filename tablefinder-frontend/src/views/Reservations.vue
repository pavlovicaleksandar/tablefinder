<template>
  <v-data-table
    v-model:items-per-page="itemsPerPage"
    :headers="headers"
    :items="reservations"
    class="elevation-1"
  >
    <template v-slot:item.createdOn="{ item }">
      <span>{{ new Date(item.dateAndTime).toLocaleString() }}</span>
    </template>
    <template v-slot:[`item.actions`]="{ item }">
      <v-btn>
        mdi-pencil
      </v-btn>
    </template>
  </v-data-table>
</template>

<script>
import { VDataTable } from 'vuetify/labs/VDataTable'

export default {
  components: {
    VDataTable,
  },
  mounted() {
    this.fetchReservations()
  },
  data() {
    return {
      itemsPerPage: 10,
      headers: [
        {title: 'Date & time', align: 'start', sortable: false, key: 'dateAndTime'},
        {title: 'Restaurant ID', align: 'start', key: 'restaurantId'},
        {title: 'User ID', align: 'start', key: 'userId'},
        {title: 'Status', align: 'start', key: 'status'},
        {title: 'Number of People', align: 'start', key: 'numberOfPeople'},
        {title: 'Note for restaurant', align: 'start', key: 'noteForRestaurant'},
        {title: 'Restaurant Note', align: 'start', key: 'restaurantNote'},
        {title: 'Actions', value: 'actions', key: 'actions', sortable: false},
      ],
      reservations: []
    }
  },
  methods: {
    fetchReservations() {
      fetch('http://localhost:8080/reservations')
        .then(response => response.json())
        .then(data => this.reservations = data)
    },
    acceptReservation(res) {
      console.log(res)
    },
    rejectReservation(res) {
      console.log(res)
    }
  }
};
</script>

<style>
/* Add custom styles for the component */
</style>
