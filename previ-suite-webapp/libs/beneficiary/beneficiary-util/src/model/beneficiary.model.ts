export interface DesignedSubjectModel {
  distribution: number;
  ordering: number;
  beneficiary: BeneficiaryModel
}

export interface BeneficiaryModel {
  id: number,
  beneficiaryType: "NATURAL_ENTITY" | "LEGAL_ENTITY"
  name: string,
  surname: string,
  fiscalCode: string,
  birthDate: string,
  gender: string,
  nation: string,
  province: string,
  location: string,
  address: string,
  birthNation: string,
  birthProvince: string,
  birthLocation: string,
  companyName: string,
  vatNumber: string,
  email: string,
  emailPec: string,
  mobilePhone: string,
  phone: string,
}
