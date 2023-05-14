<template>
  <div>
    <h1>Restaurant Detail</h1>
    <p>Name: {{ restaurant.name }}</p>
    <p>Description: {{ restaurant.description }}</p>
  </div>
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
    }
  }
}
</script>

