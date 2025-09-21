<script setup lang="ts">
import {CommonNavbarUi} from "@previ-suite-webapp/common-ui";
import {useRoute} from "vue-router";
import {computed} from "vue";

const route = useRoute();

const haveNavbar = computed(() => {
  return route.path !== "/login";
})
</script>

<template>
  <CommonNavbarUi v-if="haveNavbar" />
  <div class="h-dvh" :class="{'px-36 pt-[7rem]': haveNavbar}">
    <RouterView />
  </div>
</template>
