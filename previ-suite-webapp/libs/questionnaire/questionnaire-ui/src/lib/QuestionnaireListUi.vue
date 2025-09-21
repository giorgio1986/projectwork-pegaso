<script setup lang="ts">
import {QuestionnaireModel} from "@previ-suite-webapp/questionnaire-util";
import {RadioButton, InputText} from "primevue";
import {Ref, ref, watch, watchEffect} from "vue";

const props = defineProps<{
  questions: Record<string, QuestionnaireModel[]>
}>()

const emits = defineEmits(["update:questions"]);

const values = ref<Record<string, string>>({})

watchEffect(() => {
  console.log("edit")
  emits("update:questions", values);
})

watch(() => props.questions, (value) => {
  Object.keys(value).forEach((key) => {
    value[key].forEach((item) => {
      values.value['question'+item.idQuestion] = ""
    })
  })
})
</script>

<template>
  <div class="flex flex-col gap-4 h-full pr-4 overflow-auto">
    <span class="text-4xl text-primary font-bold">Questionario di autovalutazione</span>
    <p class="mb-4">Il Questionario di autovalutazione è uno strumento che aiuta l'aderente a verificare il proprio <b>livello di conoscenza</b> in materia previdenziale e ad orientarsi tra le diverse opzioni di <b>investimento</b></p>
    <div v-for="parag in Object.keys(questions)" :key="parag" class="flex flex-col gap-4">
      <span class="text-3xl font-bold">{{parag}}</span>
      <div v-for="q in questions[parag]" :key="q.idQuestion" class="flex flex-col gap-4 bg-white shadow-lg border-2 rounded-xl p-4">
        <span class="text-xl text-secondary font-bold">{{q.question}}</span>
        <div v-if="q.type === 'MULTIPLE_CHOICE'" class="flex flex-col gap-2">
          <div v-for="a in q.answers" class="flex items-center gap-2">
            <RadioButton v-model="values['question'+q.idQuestion]" :inputId="'question-'+q.idQuestion" :name="'question-'+q.idQuestion" :value="a.idAnswer" />
            <label :for="'question-'+q.idQuestion">{{a.answer}}</label>
          </div>
        </div>
        <div v-if="q.type === 'TEXT'" class="flex flex-col gap-2">
          <div class="flex items-center gap-2">
            <InputText type="text" v-model="values['question'+q.idQuestion]" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
