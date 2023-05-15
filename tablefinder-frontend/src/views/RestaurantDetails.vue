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
            <v-btn color="secondary">Reserve now </v-btn>
          </div>
        </div>
      </div>
    </div>
  </v-container>
</template>
<script>
export default {
  name: "RestaurantDetails",
  props: {
    id: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      restaurant: {}
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
