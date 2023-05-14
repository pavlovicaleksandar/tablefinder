<template>
  <v-btn
    prepend-icon="mdi-vuetify"
    append-icon="mdi-vuetify"
    variant="tonal"
  >
    Button
  </v-btn>
</template>

<script lang="js">
async function check() {
  await fetch(`https://tapping4.uber.space/match/simple/`)
    .then((res) => res.json())
    .then((json) => (this.matches = json))
    .catch((err) => (console.log(err)))
}
</script>
