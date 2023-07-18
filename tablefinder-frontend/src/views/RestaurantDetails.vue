<template>
  <v-container>
    <v-btn variant="text" @click="$router.back()">
      <v-icon start icon="mdi-arrow-left"></v-icon>
      Back
    </v-btn>
    <div class="wrapper">
      <div class="d-flex gap-12">
        <v-img :src='restaurant.imageUrl' width="800"></v-img>
        <div class="restaurant-description">
          <span class="text-h4">{{ restaurant.name }}</span>
          <v-rating readonly v-model="restaurant.rating" color="secondary" half-increments></v-rating>
          <span>Number of reviews: ({{restaurant.numberOfRatings}})</span>
          <h3>Price: {{['N/A', '$', '$$', '$$$'].at(restaurant.price)}}</h3>
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
          <v-list-item v-if="isAddReviewEnabled === true">
            <v-card>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" md="12">
                      <v-rating
                        v-model="selectedRating"
                        bg-color="orange-lighten-1"
                        color="secondary"
                      ></v-rating>
                    </v-col>
                    <v-col cols="12" md="12">
                      <v-select color="secondary"
                                v-model="selectedOptionForPrice"
                                label="Price"
                                variant="outlined"
                                :items="priceOptions"
                                item-value="value"
                                item-title="displayText"
                                return-object
                                single-line
                                >
                      </v-select>
                    </v-col>
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
          <hr>
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
                      <h3> Price: {{['$', '$$', '$$$'].at(review.price - 1)}}</h3>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="12" md="12">
                      <v-rating
                        v-model="review.rating"
                        bg-color="orange-lighten-1"
                        color="secondary"
                        readonly
                      ></v-rating>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="12" md="12">
                      <p>{{ review.comment }}</p>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>
            </v-card>
            <hr>
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
      priceOptions: [
        { displayText: '$', value: 1 },
        { displayText: '$$', value: 2 },
        { displayText: '$$$', value: 3 }
      ],
      restaurant: {},
      reviews: [],
      selectedOptionForPrice: { displayText: '$', value: 1 },
      selectedRating: null,
      comment: null,
      user: null,
      isAddReviewEnabled: true
    }
  },
  mounted() {
    this.user = JSON.parse(localStorage.getItem('user'))
    this.fetchRestaurantById()
    this.fetchReviews()
  },
  methods: {
    fetchRestaurantById() {
      fetch(`http://localhost:8080/restaurants/${this.id}`)
        .then(response => response.json())
        .then(data => this.restaurant = data)
    },
    fetchReviews() {
      fetch(`http://localhost:8080/reviews/${this.id}`)
        .then(response => response.json())
        .then(data => this.reviews = data)
        .then(data => this.isAddReviewEnabled = data.find(review => review.username === this.user.username) === undefined)
    },
    addReview() {
      if (this.comment != null && this.comment.trim() !== '') {
        const reviewData = {
          username: this.user.username,
          comment: this.comment,
          rating: this.selectedRating,
          price: this.selectedOptionForPrice.value,
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
