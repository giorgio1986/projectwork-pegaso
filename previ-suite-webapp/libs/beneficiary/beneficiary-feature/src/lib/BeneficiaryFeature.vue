<script setup lang="ts">
import {BeneficiaryUi} from "@previ-suite-webapp/beneficiary-ui";
import {onMounted} from "vue";
import {useBeneficiaryStore} from "@previ-suite-webapp/beneficiary-data-access";

const beneficiaryStore = useBeneficiaryStore();

onMounted(() => {
  beneficiaryStore.getBeneficiaries();
  beneficiaryStore.getDesignedSubjects();
})
</script>

<template>
  <BeneficiaryUi
    :beneficiaries="beneficiaryStore.beneficiary"
    :designed-subjects="beneficiaryStore.designedSubject"/>
</template>

<style scoped>

</style>
