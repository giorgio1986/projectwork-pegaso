<script setup lang="ts">
import {ProfileUi} from "@previ-suite-webapp/profile-ui";
import {useAuthStore} from "@previ-suite-webapp/auth-data-access";
import {onMounted} from "vue";
import {useContactStore} from "@previ-suite-webapp/contact-data-access";
import {useAddressStore} from "@previ-suite-webapp/address-data-access";

const authStore = useAuthStore();
const contactStore = useContactStore();
const addressStore = useAddressStore();

onMounted(() => {
 contactStore.getContacts();
 addressStore.getAddresses();
})
</script>

<template>
  <ProfileUi
      v-if="authStore.user && contactStore.contacts"
      :user="authStore.user"
      :addresses="addressStore.addresses"
      :contacts="contactStore.contacts" />
</template>

<style scoped>

</style>
