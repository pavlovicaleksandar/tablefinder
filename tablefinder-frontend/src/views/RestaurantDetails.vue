<template>
  <v-container>
    <v-btn variant="text" @click="$router.back()">
      <v-icon start icon="mdi-arrow-left"></v-icon>
      Back
    </v-btn>
    <v-card>
      <v-card-title>{{restaurant.name}}</v-card-title>
      <v-card-text>
        <v-row>
          <v-col>
            <v-img :src='restaurant.imageUrl'></v-img>
          </v-col>
          <v-col>
            <v-row>
              <v-rating readonly v-model="restaurant.rating" color="secondary" half-increments></v-rating>
            </v-row>
            <v-chip v-for="tag in restaurant.tags" :key="tag.id">{{tag.tagName}}</v-chip>
            <span>Number of reviews: ({{restaurant.numberOfRatings}})</span>
            <h3>Price: {{['N/A', '$', '$$', '$$$'].at(restaurant.price)}}</h3>
            <span>{{ restaurant.description }}</span>
            <div class="reserve-now-btn">
              <ReservePopup></ReservePopup>
            </div>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
<!--    <div class="wrapper">-->
<!--      <div class="d-flex gap-12">-->
<!--        <v-img :src='restaurant.imageUrl' width="50%"></v-img>-->
<!--        <div class="restaurant-description">-->
<!--          <span class="text-h4">{{ restaurant.name }}</span>-->
<!--          <div>-->
<!--            <v-chip v-for="tag in restaurant.tags" :key="tag.id">{{tag.tagName}}</v-chip>-->
<!--          </div>-->
<!--          <v-rating readonly v-model="restaurant.rating" color="secondary" half-increments></v-rating>-->
<!--          <span>Number of reviews: ({{restaurant.numberOfRatings}})</span>-->
<!--          <h3>Price: {{['N/A', '$', '$$', '$$$'].at(restaurant.price)}}</h3>-->
<!--          <span>{{ restaurant.description }}</span>-->
<!--          <div class="reserve-now-btn">-->
<!--            <ReservePopup></ReservePopup>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
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
                <v-btn v-if="selectedRating!=null" color="secondary" @click="addReview" variant="outlined" class="mt-n8">Add</v-btn>
                <v-btn v-if="selectedRating == null" color="secondary" @click="addReview" variant="outlined" disabled class="mt-n8">Add</v-btn>
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
                  <v-row justify="end">
                    <v-btn v-if="user.role === 'Admin' || user.username === review.username" color="red" @click="removeReview(review.id)">Remove</v-btn>
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
import {getCurrentlyLoggedInUser} from "@/helpers";

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
      user: {},
    }
  },
  mounted() {
    getCurrentlyLoggedInUser().then(userInfo => {
      this.user = userInfo
      if (userInfo == null) {
        window.location.href = '/'
      }
    })
    this.selectedRating = null
    this.fetchRestaurantById()
    this.fetchReviews()
  },
  methods: {
    fetchRestaurantById() {
      axios.get(`http://localhost:8080/restaurants/${this.id}`)
        .then(response => response.data)
        .then(data => this.restaurant = data)
    },
    fetchReviews() {
      axios.get(`http://localhost:8080/reviews/${this.id}`)
        .then(response => response.data)
        .then(data => this.reviews = data)
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
    },
    removeReview(reviewId) {
      axios.delete(`http://localhost:8080/reviews/${reviewId}`)
        .then(response => {
          console.log('Successfully removed review');
          location.reload()
        })
        .catch(error => {
          console.error('Error removing review:', error);
        });
    }
  },
  computed: {
    isAddReviewEnabled() {
      return this.reviews.find(review => review.username === this.user.username) === undefined
    }
  }
}
</script>


<style scoped>
.reserve-now-btn {
  margin-top: auto;
  display: flex;
  justify-content: flex-end;
  gap: 2em;
}
</style>
