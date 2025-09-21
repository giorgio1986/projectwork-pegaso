<script setup lang="ts">
import {QuestionnaireListUi, QuestionnaireSummaryUi} from "@previ-suite-webapp/questionnaire-ui";
import {useQuestionnaireStore} from "@previ-suite-webapp/questionnaire-data-access";
import {onMounted, ref} from "vue";

const questionnaireStore = useQuestionnaireStore()

onMounted(() => {
  questionnaireStore.getQuestions();
})

const compileQuestionnaire = ref<Record<string, string>>({})
</script>

<template>
  <div class="flex items-center h-full w-full">
    <div class="h-full w-3/4">
      <QuestionnaireListUi @update:questions="compileQuestionnaire = $event" :questions="questionnaireStore.questions" />
    </div>
    <div class="h-full w-1/4 pl-4">
      <QuestionnaireSummaryUi :questions="questionnaireStore.questionsList" :compile-questionnaire="compileQuestionnaire" />
    </div>
  </div>
</template>

<style scoped>

</style>
