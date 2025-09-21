<script setup lang="ts">
import { ContactModel } from '@previ-suite-webapp/contact-util';
import { Button, InputText, Checkbox } from 'primevue';
import { ref } from 'vue';

const props = defineProps<{
  contacts: ContactModel
}>()

const contactsEdit = ref<ContactModel>(props.contacts)
</script>

<template>
  <div class="flex flex-col gap-4 h-full pb-4">
    <span class="text-4xl text-primary font-bold">Modifica i tuoi recapiti</span>
    <div class="flex-1 flex flex-col justify-between gap-4">
      <div class="h-fit grid grid-cols-2 gap-4">
        <div class="card flex flex-col h-fit justify-center gap-4">
          <span class="text-secondary font-bold text-2xl">Email</span>
          <InputText class="w-full" v-model="contactsEdit.email" type="text" size="large" />
        </div>
        <div class="card flex flex-col h-fit justify-center gap-4">
          <span class="text-secondary font-bold text-2xl">Email PEC</span>
          <InputText class="w-full" v-model="contactsEdit.emailPec" type="text" size="large" />
        </div>
        <div class="card flex flex-col h-fit justify-center gap-4">
          <span class="text-secondary font-bold text-2xl">Telefono</span>
          <InputText class="w-full" v-model="contactsEdit.phone" type="text" size="large" />
        </div>
        <div class="card flex flex-col h-fit justify-center gap-4">
          <span class="text-secondary font-bold text-2xl">Cellulare</span>
          <InputText class="w-full" v-model="contactsEdit.mobilePhone" type="text" size="large" />
        </div>
      </div>
      <div class="flex justify-between items-center w-full card">
        <div class="flex items-center gap-2">
          <Checkbox v-model="contactsEdit.electronicCommunications" binary inputId="electronicCommunications" name="electronicCommunications" />
          <label for="electronicCommunications"> L'aderente esprime il consenso a ricevere tutte le comunicazioni in formato elettronico </label>
        </div>
        <Button label="Salva" icon="pi pi-save" />
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
