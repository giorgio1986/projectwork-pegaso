import {defineStore} from "pinia";
import {httpClient} from "@previ-suite-webapp/common-data-access";
import {UserModel} from "@previ-suite-webapp/auth-util";
import {Router} from "vue-router";

export interface AuthState {
  user: UserModel | null;
}

export const initialUserState: AuthState = {
  user: null,
}

export const useAuthStore = defineStore("authStore", {
  state: () => initialUserState,
  actions: {
    async login(data: { username: string, password: string }, router: Router) {
      const response = await httpClient.post("users/login", data);
      localStorage.setItem("previgiorgio-jwt", response.data.data.authToken);
      await this.getUser();
      router.push("/home").then();
    },
    async getUser() {
      try {
        const response = await httpClient.get("users");
        this.user = response.data.data;
      } catch (error) {
        this.user = null;
      }
    },
    async logout() {
      await httpClient.get("users/logout");
    }
  }
})
