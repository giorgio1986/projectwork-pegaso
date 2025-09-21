<script setup lang="ts">
import {ref} from "vue";
import {InputText, FloatLabel, Button} from "primevue";
import {useAuthStore} from "@previ-suite-webapp/auth-data-access";
import {useRouter} from "vue-router";

const authStore = useAuthStore();
const router = useRouter();

const username = ref("");
const password = ref("");

function login() {
  authStore.login({username: username.value, password: password.value}, router)
}
</script>

<template>
  <div class="login-container">
    <div class="login">
      <div class="login__header">
        <span class="login__header-title">Accedi a <b>Previgiorgio</b></span>
        <span class="login__header-subtitle">Inserisci le tue credenziali per accedere alla tua area personale</span>
      </div>
      <div class="login__form">
        <FloatLabel variant="on">
          <InputText type="text" v-model="username" variant="filled" class="w-full" />
          <label for="on_label">Codice fiscale</label>
        </FloatLabel>
        <FloatLabel variant="on">
          <InputText type="password" v-model="password" variant="filled" class="w-full" />
          <label for="on_label">Password</label>
        </FloatLabel>
      </div>
      <Button @click="login()" label="Accedi" />
    </div>
  </div>
</template>

<style lang="scss" scoped>
  .login {
    @apply fixed  flex-col gap-4 right-0 top-0 bottom-0 z-10 bg-white/75 backdrop-blur border-2 shadow-xl flex px-52 justify-center w-[50dvw] rounded-l-3xl;
    &-container {
      @apply relative bg-[url(images/bgLogin.png)] bg-no-repeat bg-cover bg-center h-[100dvh] w-[100dvw];
      &:after {
        content: '';
        @apply bg-green-700/25 absolute top-0 bottom-0 right-0 left-0;
      }
    }
    &__form {
      @apply flex flex-col gap-4;
    }
    &__header {
      @apply flex flex-col;
      &-title {
        @apply text-4xl font-thin;
      }
      &-subtitle {
        @apply text-lg font-thin text-gray-500;
      }
    }
  }
</style>
