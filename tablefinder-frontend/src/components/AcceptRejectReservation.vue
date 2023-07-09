<template>
  <v-btn @click.stop="showPopup=true" color="secondary">{{statusForButton}}</v-btn>
  <v-dialog v-model="showPopup" max-width="500px">
    <v-card>
      <v-card-title>Confirm your choice</v-card-title>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12" md="12">
              <v-textarea color="secondary" v-model="noteFromRestaurant"  label="Note (optional)" variant="outlined"></v-textarea>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-btn color="secondary" @click="confirm" variant="outlined">Confirm</v-btn>
        <v-btn color="error" @click="cancel" variant="outlined">Cancel</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from "axios";

export default {

  props: ['status', 'reservation', 'statusForButton'],
  data() {
    return {
      showPopup: false,
      noteFromRestaurant: '',
    };
  },
  methods: {
    confirm() {
      console.log(this.reservation.id)
      const changeReservationStatusData = {
        noteFromRestaurant: this.noteFromRestaurant,
        status: this.status
      };
      axios.put(`http://localhost:8080/reservations/${this.reservation.id}`, changeReservationStatusData)
        .then(response => {
          console.log('Reservation status changed:', response.status);
        })
        .catch(error => {
          console.error('Error changing reservation status:', error);
        });
      this.showPopup = false
      location.reload()
    },
    cancel() {
      this.showPopup = false
      console.log('Cancel button clicked');
    },
  },
};
</script>
