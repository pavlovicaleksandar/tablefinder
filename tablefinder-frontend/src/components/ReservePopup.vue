<template>
  <v-btn @click.stop="showPopup=true" color="secondary">Reserve now</v-btn>
  <v-dialog v-model="showPopup" max-width="500px">
    <v-card>
      <v-card-title>Reservation</v-card-title>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12" md="6">
              <v-text-field v-model.number="numberOfPeople" label="Number of People" type="number"></v-text-field>
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

export default {
  data() {
    return {
      showPopup: false,
      numberOfPeople: 0,
      noteForRestaurant: '',
    };
  },
  methods: {
    reserve() {
      // Handle the reservation logic here
      // You can access the selected using this.numberOfPeople
      console.log('Reserve button clicked');
      const reservationData = {
        restaurantId: '11e20fd9-a530-40cb-8890-628561c85021',
        userId: '11111111-a530-40cb-8890-628561c85021',
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
      // Handle the cancel logic here
      this.showPopup = false
      console.log('Cancel button clicked');
    },
  },
};
</script>
