import './styles.scss';
import { createApp } from 'vue';
import App from './app/App.vue';
import {router} from "@previ-suite-webapp/common-data-access";
import PrimeVue from 'primevue/config';
import {createPinia} from "pinia";
import {PreviTheme} from "./previ-theme";

const app = createApp(App);
const pinia = createPinia();

app.use(PrimeVue, {
  theme: {
    preset: PreviTheme,
    options: {
      darkModeSelector: '.dark'
    }
  }
});
app.use(pinia);
app.use(router);
app.mount('#root');
