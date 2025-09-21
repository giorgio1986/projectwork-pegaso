<script setup lang="ts">
import {BeneficiaryModel, DesignedSubjectModel} from "@previ-suite-webapp/beneficiary-util";
import {Column, DataTable, Message} from "primevue";

defineProps<{
  beneficiaries: BeneficiaryModel[],
  designedSubjects: DesignedSubjectModel[]
}>()

const columns = [
  { field: 'beneficiary.beneficiaryType', header: 'Tipo beneficiario' },
  { field: 'beneficiary.name', header: 'Nome' },
  { field: 'beneficiary.surname', header: 'Cognome' },
  { field: 'beneficiary.fiscalCode', header: 'Codice fiscale' },
  { field: 'beneficiary.companyName', header: 'Nome azienda' },
  { field: 'beneficiary.vatNumber', header: 'Partita IVA' },
  { field: 'distribution', header: 'Percentuale beneficio' },
  { field: 'ordering', header: 'Ordine designazione' }
];
</script>

<template>
  <div class="flex gap-10 h-full w-full">
    <div class="flex flex-col gap-4">
      <span class="text-4xl text-primary font-bold">Soggetti designati</span>
      <DataTable v-if="designedSubjects.length" :value="designedSubjects" showGridlines stripedRows>
        <Column v-for="col of columns" :key="col.field" :field="col.field" :header="col.header"></Column>
      </DataTable>
      <Message v-else>Nessun soggetto designato, la sua posizione previdenziale sarà riscattata dagli eredi legittimi</Message>
    </div>
    <div class="flex flex-col gap-4 w-full">
      <span class="text-4xl text-primary font-bold">Beneficiari registrati</span>
      <div v-for="ben in beneficiaries" :key="ben.id" class="bg-white flex flex-col gap-1 border rounded-xl p-4 w-full">
        <span class="flex gap-1 items-center text-xl font-bold">
          <span class="pi pi-user h-10 w-10 mr-3 bg-gray-200 flex items-center justify-center rounded-full"></span>
          {{ben.name ?? ben.companyName}}
          <span v-if="ben.surname">{{ben.surname}}</span>
        </span>
        <span class="pl-14 text-gray-500">{{ben.beneficiaryType}}</span>
        <span class="pl-14 text-gray-500">{{ben.fiscalCode && ben.fiscalCode.length > 1 ? ben.fiscalCode : ben.vatNumber}}</span>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
