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
        <v-list>
          <v-list-item>
            <v-card>
              <v-card-title>Write review...</v-card-title>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" md="12">
                      <v-textarea color="secondary" v-model="comment"  label="Review (optional)" variant="outlined"></v-textarea>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-btn color="secondary" @click="addReview" variant="outlined" class="mt-n8">Add</v-btn>
              </v-card-actions>
            </v-card>
          </v-list-item>
          <v-list-item v-for="review in reviews" :key="review.id">
            <v-card>
              <v-card-title>{{ review.username }}</v-card-title>
              <v-card-subtitle>
                {{ new Date(review.createdAt).toLocaleString() }}
              </v-card-subtitle>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" md="12">
                      <p>{{ review.comment }}</p>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>
            </v-card>
          </v-list-item>
        </v-list>
      </v-card-text>
    </v-card>
  </v-container>
</template>
<script>
import ReservePopup from "@/components/ReservePopup";
import axios from "axios";

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
        { id: 1, username: 'John', createdAt: '2023-05-17', comment: 'Great food and service!' },
        { id: 2, username: 'Jane', createdAt: '2023-05-16', comment: 'Highly recommend this place.' },
        { id: 3, username: 'Mike', createdAt: '2023-05-15', comment: 'Average experience, could be better.' }
      ],
      comment: null
    }
  },
  mounted() {
    this.fetchRestaurantById()
    this.fetchReviews()
  },
  methods: {
    fetchRestaurantById() {
      fetch(`http://localhost:8080/restaurants/${this.id}`)
        .then(response => response.json())
        .then(data => this.restaurant = data)
      this.restaurant.image = "https://media-cdn.tripadvisor.com/media/photo-s/07/35/14/37/kod-bore.jpg"
    },
    fetchReviews() {
      fetch(`http://localhost:8080/reviews/${this.id}`)
        .then(response => response.json())
        .then(data => this.reviews = data)
    },
    addReview() {
      if (this.comment != null && this.comment.trim() !== '') {
        const reviewData = {
          username: "user1",
          comment: this.comment,
          restaurantId: this.restaurant.id
        };

        axios.post('http://localhost:8080/reviews', reviewData)
          .then(response => {
            console.log('Successfully added new review');
            location.reload()
          })
          .catch(error => {
            console.error('Error adding new review:', error);
          });
      }
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
