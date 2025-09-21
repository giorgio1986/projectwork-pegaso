import {AddressModel} from "@previ-suite-webapp/address-util";
import {defineStore} from "pinia";
import {httpClient} from "@previ-suite-webapp/common-data-access";

export interface AddressState {
  addresses: AddressModel[]
}

export const initialAddressState: AddressState = {
  addresses: [],
}

export const useAddressStore = defineStore("addressStore", {
  state: () => initialAddressState,
  actions: {
    async getAddresses() {
      const response = await httpClient.get("/members/addresses")
      this.addresses = response.data.data.map((r: AddressModel) => ({...r, addressType: this.getTypeLabel(r.addressType)}))
    },
    async addAddress(address: Partial<AddressModel>) {
      await httpClient.post("/members/addresses", address)
      await this.getAddresses();
    },
    async deleteAddress(id: string) {
      await httpClient.delete("/members/addresses/"+id)
      await this.getAddresses();
    },
    getTypeLabel(t: string) {
      switch (t) {
        case "RESIDENTIAL_ADDRESS":
          return "Residenza"
        case "POSTAL_ADDRESS":
          return "Corrispondenza"
        case "TAX_DOMICILE":
          return "Domicilio fiscale"
        default:
          return "Sconosciuto"
      }
    }
  }
})
