<template>
  <v-data-table
    v-model:items-per-page="itemsPerPage"
    :headers="headers"
    :items="reservations"
    class="elevation-1"
  >
    <template v-slot:[`item.status`]="{ item }">
      <v-chip
        class="ma-2"
        color="orange"
        text-color="white"
        v-if="item.columns.status === 'PENDING'"
      >
        Pending
      </v-chip>
      <v-chip
        class="ma-2"
        color="red"
        text-color="white"
        v-if="item.columns.status === 'REJECTED'"
      >
        Rejected
      </v-chip>
      <v-chip
        class="ma-2"
        color="green"
        text-color="white"
        v-if="item.columns.status === 'ACCEPTED'"
      >
        Accepted
      </v-chip>
    </template>
    <template v-slot:[`item.dateAndTime`]="{ item }">
      {{ new Date(item.columns.dateAndTime).toLocaleString() }}
    </template>
    <template v-slot:[`item.actions`]="{ item }" v-if="loggedInUser.role !== 'Guest'">
      <AcceptRejectReservation :statusForButton="'Accept'" :status="'ACCEPTED'" :reservation="{id: item.value}" v-if="item.columns.status === 'PENDING'"></AcceptRejectReservation>
      <AcceptRejectReservation :statusForButton="'Reject'" :status="'REJECTED'" :reservation="{id: item.value}" v-if="item.columns.status === 'PENDING'"></AcceptRejectReservation>
    </template>
  </v-data-table>
</template>

<script>
import { VDataTable } from 'vuetify/labs/VDataTable'
import AcceptRejectReservation from "@/components/AcceptRejectReservation";
import axios from "axios";
import {getCurrentlyLoggedInUser} from "@/helpers";

export default {
  components: {
    VDataTable,
    AcceptRejectReservation,
  },
  mounted() {
    getCurrentlyLoggedInUser().then(userInfo => {
      this.loggedInUser = userInfo
    })
    this.fetchReservations()
  },
  data() {
    return {
      itemsPerPage: 10,
      headers: [
        {title: 'Date & time', align: 'start', sortable: false, key: 'dateAndTime'},
        {title: 'Restaurant', align: 'start', key: 'restaurantName'},
        {title: 'Username', align: 'start', key: 'username'},
        {title: 'Status', align: 'start', key: 'status'},
        {title: 'Number of People', align: 'start', key: 'numberOfPeople'},
        {title: 'Note for restaurant', align: 'start', key: 'noteForRestaurant'},
        {title: 'Restaurant Note', align: 'start', key: 'noteFromRestaurant'},
        {title: 'Actions', value: 'actions', key: 'actions', sortable: false},
      ],
      reservations: [],
      loggedInUser: {}
    }
  },
  methods: {
    fetchReservations() {
      axios.get('http://localhost:8080/reservations')
        .then(response => response.data)
        .then(data => this.reservations = data)
    },
  }
};
</script>

<style>
/* Add custom styles for the component */
</style>
