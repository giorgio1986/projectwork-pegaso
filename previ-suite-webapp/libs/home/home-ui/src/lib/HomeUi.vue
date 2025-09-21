<script setup lang="ts">
import {Card} from "primevue";
import {HomeChartUi} from "../index";
</script>

<template>
  <div class="flex flex-col gap-4 h-full pb-5">
    <div class="flex-[2] flex gap-4 w-full gap-4">
      <div class="card flex-1 flex flex-col relative">
        <span class="font-bold text-2xl">La mia posizione previdenziale</span>
        <div class="flex flex-col gap-4 pt-4">
          <div class="flex items-center justify-between">
            <b>TFR</b>
            <span>23567,00 €</span>
          </div>
          <div class="flex items-center justify-between">
            <b>Aderente</b>
            <span>54256,00 €</span>
          </div>
          <div class="flex items-center justify-between">
            <b>Azienda</b>
            <span>12987,00 €</span>
          </div>
          <div class="flex items-center justify-between">
            <b>Trasferimenti</b>
            <span>34825,00 €</span>
          </div>
          <div class="flex items-center justify-between">
            <b>Anticipi</b>
            <span class="text-red-500">-44278,00 €</span>
          </div>
          <div class="flex items-center justify-between">
            <b>Riscatti</b>
            <span class="text-red-500">-12043,00 €</span>
          </div>
          <div class="flex absolute bottom-4 left-4 right-4 items-end justify-between text-3xl">
            <b>TOTALE</b>
            <span>69314,00 €</span>
          </div>
        </div>
      </div>
      <HomeChartUi />
    </div>
    <div class="card flex-1">
      <span class="font-bold text-2xl">Ultime operazioni</span>
      <div class="flex items-center justify-around gap-4 pt-4 h-full">
        <div v-for="n in 5" class="flex flex-col gap-10 shadow-lg bg-white border rounded-xl p-4">
          <span class="flex items-center gap-4">
            <i class="w-10 h-10 bg-gray-200 flex items-center justify-center rounded-md pi pi-euro"></i>
            <span class="flex flex-col gap-1">
              Contributo
              <span class="text-sms">12/10/2017</span>
            </span>
          </span>
          <span class="font-bold text-xl text-center">{{345*n}},00 €</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
