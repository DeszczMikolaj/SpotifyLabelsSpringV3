import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080", // Spring Boot dev server
  withCredentials: true,            // send session cookie (important!)
});

export default api;