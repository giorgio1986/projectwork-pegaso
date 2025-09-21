<script setup lang="ts">
import {ref} from 'vue';
import {Button, Menu} from "primevue";
import {useAuthStore} from "@previ-suite-webapp/auth-data-access";

const authStore = useAuthStore();

const menuProfile = ref();
const menuPosition = ref();
const menuTools = ref();
const itemsProfile = ref([
  { label: 'Visualizza profilo', route: "/profile" },
  { label: 'Questionario', route: "/questionnaire" },
  { label: 'Modifica indirizzi', route: "/address" },
  { label: 'Modifica contatti', route: "/contact" },
  { label: 'Soggetti designati', route: "/beneficiaries" },
]);
const itemsPosition = ref([
  { label: 'Visualizza posizione', route: "/" },
  { label: 'Importi versati', route: "/" },
  { label: 'Prestazioni godute', route: "/" },
  { label: 'Operazioni', route: "/" },
  { label: 'Contributi non dedotti', route: "/" },
]);
const itemsTools = ref([
  { label: 'Le Mie Richieste', route: "/" },
  { label: 'Info in fase di accumulo', route: "/" },
]);

const toggle = (event: PointerEvent, menu: string): void => {
  switch (menu) {
    case "profile":
      menuProfile.value.toggle(event);
      break;
    case "position":
      menuPosition.value.toggle(event);
      break;
    case "tools":
      menuTools.value.toggle(event);
      break;
  }
};
</script>

<template>
  <div class="fixed z-40 flex items-center justify-between gap-4 top-2 left-4 right-4 h-20 rounded-2xl bg-white backdrop-blur border shadow-lg p-2">
    <img src="/images/logo.png" alt="PreviGiorgio" class="h-full">
    <div class="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2">
      <RouterLink v-slot="{ href, navigate }" to="/home" class="mr-2" custom>
        <a v-ripple :href="href" @click="navigate">
          <span class="pi pi-home" />
          <span class="ml-2">Home</span>
        </a>
      </RouterLink>
      <Button severity="contrast" variant="text" type="button" label="Profilo" icon="pi pi-user" @click="toggle($event, 'profile')" aria-haspopup="true" aria-controls="overlay_menu" />
      <Menu ref="menuProfile" id="overlay_menu" :model="itemsProfile" :popup="true">
        <template #item="{ item, props }">
          <RouterLink v-if="item.route" v-slot="{ href, navigate }" :to="item.route" custom>
            <a v-ripple :href="href" v-bind="props.action" @click="navigate">{{ item.label }}</a>
          </RouterLink>
        </template>
      </Menu>
      <Button severity="contrast" variant="text" type="button" label="Posizione" icon="pi pi-chart-pie" @click="toggle($event, 'position')" aria-haspopup="true" aria-controls="overlay_menu_position" />
      <Menu ref="menuPosition" id="overlay_menu_position" :model="itemsPosition" :popup="true">
        <template #item="{ item, props }">
          <RouterLink v-if="item.route" v-slot="{ href, navigate }" :to="item.route" custom>
            <a v-ripple :href="href" v-bind="props.action" @click="navigate">{{ item.label }}</a>
          </RouterLink>
        </template>
      </Menu>
      <Button severity="contrast" variant="text" type="button" label="Strumenti" icon="pi pi-wrench" @click="toggle($event, 'tools')" aria-haspopup="true" aria-controls="overlay_menu_tools" />
      <Menu ref="menuTools" id="overlay_menu_tools" :model="itemsTools" :popup="true">
        <template #item="{ item, props }">
          <RouterLink v-if="item.route" v-slot="{ href, navigate }" :to="item.route" custom>
            <a v-ripple :href="href" v-bind="props.action" @click="navigate">{{ item.label }}</a>
          </RouterLink>
        </template>
      </Menu>
    </div>
    <div class="flex gap-1 items-center">
      <Button label="Nuova richiesta" variant="outlined" class="mr-4" />
      <span class="pi pi-user h-10 w-10 mr-3 bg-gray-200 flex items-center justify-center rounded-full"></span>
      <div class="flex flex-col mr-2">
        <span>{{authStore.user?.member.name}} {{authStore.user?.member.surname}}</span>
        <span class="text-gray-600 text-sm">{{authStore.user?.member.fiscalCode}}</span>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
