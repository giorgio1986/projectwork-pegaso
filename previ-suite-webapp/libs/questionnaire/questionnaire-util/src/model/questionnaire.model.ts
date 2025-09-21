export interface QuestionnaireModel {
  idQuestion: number;
  question: string;
  paragraph: string;
  type: 'MULTIPLE_CHOICE' | 'TEXT';
  answers: {
    idAnswer: number;
    answer?: string;
    score: number;
  }[];
}
