import {BeneficiaryModel, DesignedSubjectModel} from "@previ-suite-webapp/beneficiary-util";
import {defineStore} from "pinia";
import {httpClient} from "@previ-suite-webapp/common-data-access";

export interface BeneficiaryState {
  designedSubject: DesignedSubjectModel[];
  beneficiary: BeneficiaryModel[];
}

export const initialBeneficiaryState: BeneficiaryState = {
  designedSubject: [],
  beneficiary: []
}

export const useBeneficiaryStore = defineStore("beneficiaryStore", {
  state: () => initialBeneficiaryState,
  actions: {
    async getBeneficiaries() {
      const response = await httpClient.get("members/beneficiaries");
      this.beneficiary = await response.data.data.map((b: any) => ({...b, beneficiaryType: b.beneficiaryType === "NATURAL_ENTITY" ? "Persona fisica" : "Persona giuridica"}));
    },
    async getDesignedSubjects() {
      const response = await httpClient.get("members/designatedsubjects");
      this.designedSubject = await response.data.data.map((d: any) => ({
        ...d,
        distribution: d.distribution+'%',
        beneficiary: {...d.beneficiary, beneficiaryType: (d.beneficiary.beneficiaryType === "NATURAL_ENTITY" ? "Persona fisica" : "Persona giuridica") }
      }));
    }
  }
})
