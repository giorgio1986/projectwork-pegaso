import {createRouter, createWebHashHistory, RouteRecordRaw} from "vue-router";
import {LoginFeature} from "@previ-suite-webapp/auth-feature";
import {HomeFeature} from "@previ-suite-webapp/home-feature";
import {QuestionnaireFeature} from "@previ-suite-webapp/questionnaire-feature";
import {useAuthStore} from "@previ-suite-webapp/auth-data-access";
import {AddressFeature} from "@previ-suite-webapp/address-feature";
import {ContactFeature} from "@previ-suite-webapp/contact-feature"
import {ProfileFeature} from "@previ-suite-webapp/profile-feature"
import {BeneficiaryFeature} from "@previ-suite-webapp/beneficiary-feature"

const routes: RouteRecordRaw[] = [
  {
    path: "/login",
    component: LoginFeature
  },
  {
    path: "/home",
    component: HomeFeature
  },
  {
    path: "/questionnaire",
    component: QuestionnaireFeature
  },
  {
    path: "/address",
    component: AddressFeature
  },
  {
    path: "/contact",
    component: ContactFeature
  },
  {
    path: "/profile",
    component: ProfileFeature
  },
  {
    path: "/beneficiaries",
    component: BeneficiaryFeature
  }
]

export const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

router.beforeEach(async (to, from, next) => {
  const authStore = useAuthStore();

  if (!authStore.user) {
    await authStore.getUser();
  }

  if (to.path === "/login" && !!authStore.user) {
    return next("/home");
  } else if (to.path !== "/login" && !authStore.user) {
    return next("/login");
  }
  return next();
});
