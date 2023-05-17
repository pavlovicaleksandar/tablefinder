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
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" @click="reserve">Reserve</v-btn>
        <v-btn color="error" @click="cancel">Cancel</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  data() {
    return {
      showPopup: false,
      numberOfPeople: 0,
    };
  },
  methods: {
    reserve() {
      // Handle the reservation logic here
      // You can access the selected using this.numberOfPeople
      console.log('Reserve button clicked');
    },
    cancel() {
      // Handle the cancel logic here
      this.showPopup = false
      console.log('Cancel button clicked');
    },
  },
};
</script>
