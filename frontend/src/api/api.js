import axios from "axios";

const api = axios.create({
  baseURL: "http://127.0.0.1:8080", // Spring Boot dev server
  withCredentials: true,            // send session cookie (important!)
});

export default api;