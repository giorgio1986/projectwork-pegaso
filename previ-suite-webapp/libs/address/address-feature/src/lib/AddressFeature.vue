<script setup lang="ts">
import {AddressTableUi} from "@previ-suite-webapp/address-ui";
import {onMounted} from "vue";
import {useAddressStore} from "@previ-suite-webapp/address-data-access";

const addressStore = useAddressStore();

onMounted(() => {
  addressStore.getAddresses();
})
</script>

<template>
  <div class="w-full flex flex-col gap-4">
    <span class="text-4xl text-primary font-bold">Modifica Indirizzi</span>
    <div class="w-full">
      <AddressTableUi :addresses="addressStore.addresses" />
    </div>
  </div>
</template>

<style scoped>

</style>
