<script setup lang="ts">
import { ContactUi } from '@previ-suite-webapp/contact-ui';
import { onMounted } from 'vue';
import { useContactStore } from '@previ-suite-webapp/contact-data-access';

const contactStore = useContactStore();

onMounted(() => {
  contactStore.getContacts();
})
</script>

<template>
 <ContactUi v-if="contactStore.contacts" :contacts="contactStore.contacts" />
</template>

<style scoped>

</style>
