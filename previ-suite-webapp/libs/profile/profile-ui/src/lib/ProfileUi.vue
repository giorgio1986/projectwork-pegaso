<script setup lang="ts">
import {UserModel} from "@previ-suite-webapp/auth-util";
import {computed} from "vue";
import {ContactModel} from "@previ-suite-webapp/contact-util";
import {AddressModel} from "@previ-suite-webapp/address-util";
import {Message, Button} from "primevue";
import {MessageCircleQuestionMark} from "lucide-vue-next";
import {useRouter} from "vue-router";

const props = defineProps<{
  user: UserModel,
  contacts: ContactModel,
  addresses: AddressModel[]
}>()

const router = useRouter()

function getStateLabel(label: string) {
  return props.user.state === 'ACTIVE' ? "Attivo" : props.user.state === "BLOCKED" ? "Bloccato" : "inattivo"
}

const dy = computed(() => {
  if (!props.user.creationTimestamp) return null
  const cDate = new Date(props.user.creationTimestamp);
  const today = new Date();
  let years = today.getFullYear() - cDate.getFullYear()
  let months = today.getMonth() - cDate.getMonth()
  let days = today.getDate() - cDate.getDate()
  if (months < 0) {
    years--
    months += 12
  }
  return { years, months, days }
})

const dataIscrizione = computed(() => {
  const date = new Date(props.user.creationTimestamp);
  return date.toLocaleDateString('it-IT', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
  })
})
</script>

<template>
  <div class="h-full pb-4 flex flex-col gap-4">
    <div class="flex-1 flex !bg-primary/75 text-white gap-4 card">
      <div class="flex-[2] flex flex-col justify-between gap-2">
        <div class="flex flex-col gap-4">
          <span class="font-bold text-4xl">{{user.member.name}} {{user.member.surname}}</span>
          <span class="text-base bg-white rounded-full text-primary w-fit px-4 font-bold">{{getStateLabel(user.state)}}</span>
        </div>
        <div class="flex flex-col gap-4">
          <span class="font-bold text-3xl">{{user.member.fiscalCode}}</span>
          <span class="text-xl"><b>Nato il</b> {{user.member.birthDate}}</span>
        </div>
      </div>
      <div class="flex-1 bg-white rounded-2xl flex flex-col gap-10 p-4">
        <span class="text-secondary font-bold text-3xl">Adesione</span>
        <div class="flex items-center justify-between">
          <div class="flex flex-col gap-2 text-black">
            <span class="font-bold">Anzianità</span>
            <span>{{dy?.years}} anni {{dy?.months}} mesi {{dy?.days}} giorni</span>
          </div>
          <div class="flex flex-col gap-2 text-black">
            <span class="font-bold">Data di iscrizione</span>
            <span>{{dataIscrizione}}</span>
          </div>
        </div>
      </div>
    </div>
    <div class="flex-1 flex items-center gap-4">
      <div class="flex-1 h-full flex flex-col !bg-white/75 gap-4 card">
        <div class="flex w-full justify-between text-primary font-bold">
          <span class="text-xl">Recapiti</span>
          <RouterLink to="/contact" class="text-xl underline">Mostra tutti</RouterLink>
        </div>
        <div class="grid grid-cols-2 gap-4">
          <div class="flex flex-col">
            <span class="font-bold">Email</span>
            <span>{{contacts.email}}</span>
          </div>
          <div class="flex flex-col">
            <span class="font-bold">Email PEC</span>
            <span>{{contacts.emailPec}}</span>
          </div>
          <div class="flex flex-col">
            <span class="font-bold">Telefono</span>
            <span>{{contacts.phone}}</span>
          </div>
          <div class="flex flex-col">
            <span class="font-bold">Cellulare</span>
            <span>{{contacts.mobilePhone}}</span>
          </div>
        </div>
      </div>
      <div class="flex-1 h-full flex flex-col !bg-white/75 gap-4 card">
        <div class="flex w-full justify-between text-primary font-bold">
          <span class="text-xl">Indirizzi</span>
          <RouterLink to="/address" class="text-xl underline">Mostra tutti</RouterLink>
        </div>
        <div class="flex flex-col gap-4">
          <template v-if="addresses.length">
            <div v-for="(a, i) in addresses" :key="i" class="flex justify-between">
              <span class="font-bold">Indirizzo {{a.addressType}}</span>
              <span>{{a.address}}, {{a.location}} {{a.province}} {{a.nation}}</span>
            </div>
          </template>
          <Message v-else>Nessun indirizzo aggiunto</Message>
        </div>
      </div>
    </div>
    <div class="flex-1 flex items-center justify-around !bg-white/75 gap-4 card">
      <MessageCircleQuestionMark :size="200" class="text-primary" />
      <div class="flex flex-col gap-4">
        <span class="text-primary text-4xl font-bold">Questionario di autovalutazione</span>
        <span><b>Ultimo aggiornamento</b>: 14/09/2025 ( Punteggio: 7 )</span>
        <Button @click="router.push('/questionnaire')" label="Compila" />
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
