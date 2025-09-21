<script setup lang="ts">
import {DataTable, Column, Button, Dialog, InputText, Select, Message} from "primevue";
import {AddressModel} from "@previ-suite-webapp/address-util";
import {computed, ref} from "vue";
import {useAddressStore} from "@previ-suite-webapp/address-data-access";

const props = defineProps<{
  addresses: AddressModel[];
}>()

const addressStore = useAddressStore();

const addressModel = ref({
  addressType: "",
  nation: "",
  province: "",
  location: "",
  address: ""
})

function resetModel() {
  addressModel.value = {
    addressType: "",
    nation: "",
    province: "",
    location: "",
    address: ""
  }
}

function addAddress() {
  addressStore.addAddress(addressModel.value)
  resetModel()
}

const columns = [
  { field: 'addressType', header: 'Tipo' },
  { field: 'nation', header: 'Nazione' },
  { field: 'province', header: 'Provincia' },
  { field: 'location', header: 'Città' },
  { field: 'address', header: 'Indirizzo' }
];

const options = computed(() => {
  return optsType.filter(ot => !props.addresses.some(ad => ad.addressType === ot.name) )
})

const optsType = [
  { name: "Residenza", code: "RESIDENTIAL_ADDRESS" },
  { name: "Corrispondenza", code: "POSTAL_ADDRESS" },
  { name: "Domicilio fiscale", code: "TAX_DOMICILE" }
]

const disabledBtn = computed(() => {
  let disable = false;
  Object.values(addressModel.value).forEach((val) => {
    if(!val.length) {
      disable = true;
    }
  })
  return disable;
})

const openAddModal = ref(false)
</script>

<template>
  <div class="flex items-center justify-end mb-4 w-full">
    <Button v-if="options.length" label="Aggiungi indirizzo" icon="pi pi-plus" @click="openAddModal = true"/>
  </div>
  <DataTable v-if="addresses.length" :value="addresses" showGridlines stripedRows>
    <Column v-for="col of columns" :key="col.field" :field="col.field" :header="col.header" sortable></Column>
    <Column header="Azioni">
      <template #body="{data}">
        <div class="flex items-center gap-2">
          <Button icon="pi pi-pen-to-square" severity="secondary"/>
          <Button @click="addressStore.deleteAddress(data.id)" icon="pi pi-trash" severity="danger"/>
        </div>
      </template>
    </Column>
  </DataTable>
  <Message v-else>Nessun indirizzo aggiunto</Message>
  <Dialog v-model:visible="openAddModal" modal header="Aggiungi indirizzo" :style="{ width: '50dvw' }">
    <div class="flex flex-col gap-1 mb-4">
      <label for="type" class="font-semibold w-24">Tipo</label>
      <Select v-model="addressModel.addressType" option-value="code" :options="options" optionLabel="name" class="w-full" />
    </div>
    <div class="flex items-center gap-4">
      <div class="flex-1 flex flex-col gap-1 mb-8">
        <label for="nation" class="font-semibold w-24">Nazione</label>
        <InputText v-model="addressModel.nation" id="nation" class="flex-auto" autocomplete="off" />
      </div>
      <div class="flex-1 flex flex-col gap-1 mb-8">
        <label for="province" class="font-semibold w-24">Provincia</label>
        <InputText v-model="addressModel.province" id="province" class="flex-auto" autocomplete="off" />
      </div>
      <div class="flex-1 flex flex-col gap-1 mb-8">
        <label for="location" class="font-semibold w-24">Città</label>
        <InputText v-model="addressModel.location" id="location" class="flex-auto" autocomplete="off" />
      </div>
    </div>
    <div class="flex flex-col gap-1 mb-8">
      <label for="address" class="font-semibold w-24">Indirizzo</label>
      <InputText v-model="addressModel.address" id="address" class="flex-auto" autocomplete="off" />
    </div>
    <div class="flex justify-end gap-2">
      <Button type="button" label="Annulla" severity="secondary" @click="resetModel(); openAddModal = false"></Button>
      <Button :disabled="disabledBtn" type="button" label="Salva" @click="addAddress(); openAddModal = false"></Button>
    </div>
  </Dialog>
</template>

<style scoped>

</style>
