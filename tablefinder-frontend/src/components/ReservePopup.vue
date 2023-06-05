<template>
  <v-btn @click.stop="showPopup=true" color="secondary">Reserve now</v-btn>
  <v-dialog v-model="showPopup" max-width="500px">
    <v-card>
      <v-card-title>Reservation</v-card-title>
      <v-card-text>
        <v-container>
          <v-row cols="12" md="12">
            <v-text-field type="date" label="Date" v-model="pickedDate"></v-text-field>
            <v-text-field v-model="pickedTime" label="Time" type="time"></v-text-field>
          </v-row>
          <v-row>
            <v-col cols="12" md="12">
              <v-text-field v-model.number="numberOfPeople" label="Number of People" type="number" min="1"></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12" md="12">
              <v-textarea color="secondary" v-model="noteForRestaurant"  label="Note" variant="outlined"></v-textarea>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-btn color="secondary" @click="reserve" variant="outlined">Reserve</v-btn>
        <v-btn color="error" @click="cancel" variant="outlined">Cancel</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from "axios";
import { toRaw } from 'vue';
// pickedDate: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
// pickedTime: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toLocaleTimeString().substr(0, 5),

export default {
  // props: {
  //   restaurant: {
  //     type: Object,
  //     required: true
  //   }
  // },
  props: ['restaurant'],
  data() {
    return {
      showPopup: false,
      pickedDate: (new Date(Date.now())).toISOString().substr(0, 10),
      pickedTime: (new Date(Date.now())).toLocaleTimeString().substr(0, 5),
      numberOfPeople: 1,
      noteForRestaurant: '',
    };
  },
  methods: {
    reserve() {
      // Handle the reservation logic here
      // You can access the selected using this.numberOfPeople
      console.log('Reserve button clicked');
      // Split the date and time parts
      const [year, month, day] = this.pickedDate.split("-");
      const [hours, minutes] = this.pickedTime.split(":");

      // Create a new Date object with the parsed values
      const parsedDate = new Date(year, month - 1, day, hours, minutes);

      // Get the timestamp in milliseconds
      const timestamp = parsedDate.getTime();
      console.log(timestamp)
      console.log(new Date(timestamp))
      const reservationData = {
        restaurantId: this.restaurant.id,
        userId: '11111111-a530-40cb-8890-628561c85021',
        dateAndTime: timestamp,
        numberOfPeople: this.numberOfPeople,
        noteForRestaurant: this.noteForRestaurant
      };
      axios.post(`http://localhost:8080/reservations`, reservationData)
        .then(response => {
          console.log('Reservation created:', response.data);
          alert('Reservation successfully created')
        })
        .catch(error => {
          console.error('Error creating reservation:', error);
        });
        this.showPopup = false
    },
    cancel() {
      console.log(this.currRestaurant)
      // Handle the cancel logic here
      this.showPopup = false
      console.log('Cancel button clicked');
    },
  },
};
</script>
