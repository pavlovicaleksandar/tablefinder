import axios from "axios";

export async function getCurrentlyLoggedInUser() {

  return axios.get(`http://localhost:8080/users/userInfo`)
  .then(async response => {
      console.log('Successfully resolved token in', response.data);
      return response.data
    })
    .catch(error => {
      console.error('Error resolving token to user info in:', error);
      return null
    });
}
