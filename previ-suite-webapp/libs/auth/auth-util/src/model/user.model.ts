export interface MemberModel {
  name: string;
  surname: string;
  fiscalCode: string;
  birthDate: string;
  gender: "MALE" | "FEMALE";
  creationTimestamp: string;
  pensionRegistrationDate: string;
  firstPensionRegistrationDate: string;
}

export interface UserModel {
  id: number;
  username: string;
  passwordValidityDays: number;
  state: "ACTIVE" | "INACTIVE" | "BLOCKED";
  creationTimestamp: string;
  lastAccessTimestamp: string;
  member: MemberModel;
}
