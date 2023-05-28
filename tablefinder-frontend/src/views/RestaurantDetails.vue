<template>
  <v-container>
    <v-btn variant="text" @click="$router.back()">
      <v-icon start icon="mdi-arrow-left"></v-icon>
      Back
    </v-btn>
    <div class="wrapper">
      <div class="d-flex gap-12">
        <v-img src="https://lh3.googleusercontent.com/p/AF1QipNrrwiOJpDrs_Rf3HpSxvyBlOqt4Kyfd_Q6Bb9a=s680-w680-h510" width="800"></v-img>
        <div class="restaurant-description">
          <span class="text-h4">{{ restaurant.name }}</span>
          <span>{{ restaurant.description }}</span>

          <div class="reserve-now-btn">
            <ReservePopup></ReservePopup>
          </div>
        </div>
      </div>
    </div>
    <v-card>
      <v-card-title class="headline">User Reviews</v-card-title>
      <v-card-text>
        <!-- Display user reviews here -->
        <v-list>
          <v-list-item v-for="review in reviews" :key="review.id">
            <v-list-item-title>{{ review.user }}</v-list-item-title>
            <v-list-item-subtitle>{{ review.date }}</v-list-item-subtitle>
            <v-list-item-content>
              <p>{{ review.comment }}</p>
              <!-- Add more review details as needed -->
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-card-text>
    </v-card>
  </v-container>
</template>
<script>
import ReservePopup from "@/components/ReservePopup";

export default {
  components: {
    ReservePopup,
  },
  name: "RestaurantDetails",
  props: {
    id: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      restaurant: {},
      reviews: [
        { id: 1, user: 'John', date: '2023-05-17', comment: 'Great food and service!' },
        { id: 2, user: 'Jane', date: '2023-05-16', comment: 'Highly recommend this place.' },
        { id: 3, user: 'Mike', date: '2023-05-15', comment: 'Average experience, could be better.' }
      ]
    }
  },
  mounted() {
    this.fetchRestaurantById()
  },
  methods: {
    fetchRestaurantById() {
      fetch(`http://localhost:8080/restaurants/${this.id}`)
        .then(response => response.json())
        .then(data => this.restaurant = data)
      this.restaurant.image = "https://media-cdn.tripadvisor.com/media/photo-s/07/35/14/37/kod-bore.jpg"
    }
  }
}
</script>


<style scoped>
.wrapper {
  display: flex;
  align-items: center;
  min-height: 660px;
  height: calc(100vh - 8.5em);
}
.restaurant-description {
  display: flex;
  flex-direction: column;
  gap: 3rem;
}
.gap-12 {
  gap: 3rem;
}
.reserve-now-btn {
  margin-top: auto;
  display: flex;
  justify-content: flex-end;
  gap: 2em;
}
</style>
