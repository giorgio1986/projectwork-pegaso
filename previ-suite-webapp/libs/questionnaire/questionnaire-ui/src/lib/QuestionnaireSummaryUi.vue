<script setup lang="ts">
import {QuestionnaireModel} from "@previ-suite-webapp/questionnaire-util";
import {computed} from "vue";
import {ProgressBar, Button} from "primevue";

const props = defineProps<{
  questions: QuestionnaireModel[],
  compileQuestionnaire: Record<string, string>,
}>()

const compileProgress = computed(() => {
  return {
    nCompiled: Object.values(props.compileQuestionnaire).filter(v => v !== '').length,
    percCompiled: (Object.values(props.compileQuestionnaire).filter(v => v !== '').length * 100) / 9,
    nQuestions: props.questions.length
  }
})
</script>

<template>
  <div class="flex flex-col gap-4">
    <div class="flex flex-col gap-4 bg-white border-2 shadow-lg p-4 rounded-xl">
      <span class="text-2xl text-primary font-bold">Completamento questionario</span>
      <ProgressBar :value="compileProgress.percCompiled"> {{ compileProgress.nCompiled }}/{{ compileProgress.nQuestions }} </ProgressBar>
    </div>
    <div class="flex flex-col gap-4 bg-white border-2 shadow-lg p-4 rounded-xl">
      <span class="text-xl text-gray-500">In caso di mancato completamento</span>
      <p>L'aderente è consapevole che la mancata compilazione della sezione "<b>Congruità della scelta providenziale</b>" non consente di ottenere un punteggio come ausilio nella scelta fra le diverse opzioni di investimento offerte dal fondo</p>
      <Button label="Visualizza griglia di valutazione" />
    </div>
  </div>
</template>

<style scoped>

</style>
