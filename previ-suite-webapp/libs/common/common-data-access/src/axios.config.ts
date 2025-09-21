import axios from "axios";
import {router} from "./router";
// eslint-disable-next-line @nx/enforce-module-boundaries
import {useAuthStore} from "@previ-suite-webapp/auth-data-access";

export const HOST = window.location.hostname;
export const PORT = window.location.port;
export const PROTOCOL = window.location.protocol;
export const SERVICE_BASE_URL = `${PROTOCOL}//${HOST}:${PORT}/`;

const httpClient = axios.create({
  baseURL: SERVICE_BASE_URL+"previ-suite-service-dev/api/v1/",
});

httpClient.interceptors.request.use((config) => {
  const token = localStorage.getItem("previgiorgio-jwt");
  if (token && config.url !== "/login") {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

httpClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const authStore = useAuthStore();
    const token = localStorage.getItem("previgiorgio-jwt");
    if (error.response?.status === 401 && !error.config.url.includes("/login") && !error.config.url.includes("/logout") && !!token) {
      localStorage.removeItem("previgiorgio-jwt");
      await authStore.logout();
      router.push("/login").then();
    }
    return Promise.reject(error);
  }
);
export { httpClient };
