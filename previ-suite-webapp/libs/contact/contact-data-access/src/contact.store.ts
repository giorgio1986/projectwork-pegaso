import { ContactModel } from '@previ-suite-webapp/contact-util';
import { defineStore } from 'pinia';
import { httpClient } from '@previ-suite-webapp/common-data-access';

export interface ContactState {
  contacts: ContactModel | null
}

export const initialContactState: ContactState = {
  contacts: null
}

export const useContactStore = defineStore("contactStore", {
  state: () => initialContactState,
  actions: {
    async getContacts() {
      const response = await httpClient.get("members/contacts");
      this.contacts = response.data.data
    }
  }
})
