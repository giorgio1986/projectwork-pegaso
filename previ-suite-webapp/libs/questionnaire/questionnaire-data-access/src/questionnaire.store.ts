import {defineStore} from "pinia";
import {httpClient} from "@previ-suite-webapp/common-data-access";
import {QuestionnaireModel} from "@previ-suite-webapp/questionnaire-util";

export interface QuestionnaireState {
  questions: Record<string, QuestionnaireModel[]>;
  questionsList: QuestionnaireModel[];
}

export const initialQuestionnaireState: QuestionnaireState = {
  questions: {},
  questionsList: []
}

export const useQuestionnaireStore = defineStore("questionnaireStore", {
  state: () => initialQuestionnaireState,
  actions: {
    async getQuestions() {
      const response = await httpClient.get("/members/questionnaires");
      this.questionsList = response.data.data.questions
      let quests: Record<string, QuestionnaireModel[]> = {};
      response.data.data.questions.forEach((question: QuestionnaireModel) => {
        if(quests[question.paragraph]) {
          quests[question.paragraph].push(question);
        } else {
          quests[question.paragraph] = [question];
        }
      })
      this.questions = quests;
    }
  }
})
