import Vue from 'vue'
import App from './App.vue'
import router from './router';
import Axios from 'axios';
import store from './store';



Vue.config.productionTip = false

new Vue({
  Axios,
  router,
  store,
  render: h => h(App),
}).$mount('#app')
