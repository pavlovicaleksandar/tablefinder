<template>
  <v-btn v-if="statusForButton === 'Accept'" @click.stop="showPopup=true" color="success" style="min-width: 120px;" class="mr-2">{{statusForButton}}</v-btn>
  <v-btn v-if="statusForButton === 'Reject'" @click.stop="showPopup=true" color="red" style="min-width: 120px;">{{statusForButton}}</v-btn>
  <v-btn v-if="statusForButton === 'Cancel'" @click.stop="confirm" color="red" style="min-width: 120px;">{{statusForButton}}</v-btn>
  <v-dialog v-model="showPopup" max-width="500px">
    <v-card>
      <v-toolbar color="primary" dark>
        <v-card-title>Confirm your choice</v-card-title>
      </v-toolbar>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12" md="12">
              <v-textarea color="primary" v-model="noteFromRestaurant"  label="Note (optional)" variant="outlined"></v-textarea>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions style="display: flex; justify-content: center;" class="mb-3">
        <v-btn color="primary" @click="confirm" variant="elevated" style="min-width: 120px;">Confirm</v-btn>
        <v-btn color="error" @click="cancel" variant="elevated" style="min-width: 120px;">Cancel</v-btn>
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
