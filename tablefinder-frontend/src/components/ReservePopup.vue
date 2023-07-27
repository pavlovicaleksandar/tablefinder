<template>
  <v-btn @click.stop="showPopup=true" color="primary">Reserve now</v-btn>
  <v-dialog v-model="showPopup" max-width="500px">
    <v-card v-if="!isReservationCreated">
      <v-toolbar color="primary" dark>
        <v-card-title>Reservation</v-card-title>
      </v-toolbar>
      <v-card-text>
        <v-container>
          <v-row cols="12" md="12">
            <v-text-field type="date" label="Date" v-model="pickedDate"></v-text-field>
            <v-text-field v-model="pickedTime" label="Time" type="time"></v-text-field>
          </v-row>
          <v-row>
            <v-col cols="12" md="12">
              <v-text-field v-model.number="numberOfPeople" label="Number of People" type="number" min="1" oninput="if (this.value < 1) this.value = 1;"></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12" md="12">
              <v-textarea color="primary" v-model="noteForRestaurant"  label="Note" variant="outlined"></v-textarea>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-text v-if="errorMessage != null" class="mb-3">
        <v-alert color="error">
          {{errorMessage}}
        </v-alert>
      </v-card-text>
      <v-card-actions  style="display: flex; justify-content: center;" class="mb-3">
        <v-btn color="primary" @click="reserve" variant="elevated" style="min-width: 120px;">Reserve</v-btn>
        <v-btn color="error" @click="cancel" variant="elevated" style="min-width: 120px;">Cancel</v-btn>
      </v-card-actions>
    </v-card>
    <v-card v-if="isReservationCreated">
      <v-toolbar color="primary" dark>
        <v-card-title>Reservation created</v-card-title>
      </v-toolbar>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12" md="12">
              <div>Reservation successfully created!</div>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions style="display: flex; justify-content: center;" class="mb-3">
        <v-btn color="primary" @click="closeModal" variant="elevated" style="min-width: 120px;">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from "axios";
import {getCurrentlyLoggedInUser} from "@/helpers";

export default {
  props: ['restaurant'],
  data() {
    return {
      showPopup: false,
      pickedDate: (new Date(Date.now())).toISOString().substr(0, 10),
      pickedTime: (new Date(Date.now())).toLocaleTimeString().substr(0, 5),
      numberOfPeople: 1,
      noteForRestaurant: '',
      isReservationCreated: false,
      loggedInUser: null,
      errorMessage: null
    };
  },
  mounted() {
    this.numberOfPeople = 1
    this.noteForRestaurant = ''
    this.errorMessage = null
    this.pickedDate = (new Date(Date.now())).toISOString().substr(0, 10)
    this.pickedTime = (new Date(Date.now())).toLocaleTimeString().substr(0, 5)
    getCurrentlyLoggedInUser().then(userInfo => {
      this.loggedInUser = userInfo
    })
    this.isReservationCreated = false
  },
  methods: {
    reserve() {
      console.log('Reserve button clicked');
      // Split the date and time parts
      const [year, month, day] = this.pickedDate.split("-");
      const [hours, minutes] = this.pickedTime.split(":");

      // Create a new Date object with the parsed values
      const parsedDate = new Date(year, month - 1, day, hours, minutes);

      const timestampNow = (new Date()).getTime()
      if (parsedDate < timestampNow) {
        this.errorMessage = "You cannot make reservation for date & time in past"
        return
      }

      // Get the timestamp in milliseconds
      const timestamp = parsedDate.getTime();
      console.log(timestamp)
      console.log(new Date(timestamp))
      const reservationData = {
        restaurantId: this.restaurant.id,
        userId: this.loggedInUser.userId,
        dateAndTime: timestamp,
        numberOfPeople: this.numberOfPeople,
        noteForRestaurant: this.noteForRestaurant
      };
      axios.post(`http://localhost:8080/reservations`, reservationData)
        .then(response => {
          console.log('Reservation created:', response.data);
        })
        .catch(error => {
          console.error('Error creating reservation:', error);
        });
        this.isReservationCreated = true
    },
    cancel() {
      this.showPopup = false
      console.log('Cancel button clicked');
    },
    closeModal() {
      this.showPopup = false
      location.reload()
    }
  },
};
</script>
