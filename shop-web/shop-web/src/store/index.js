import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getter'
import user from './modules/user'
import order from './modules/order'

Vue.use(Vuex);
const store=new Vuex.Store({
  modules:{
    user,
    order
  }
  ,getters
})
export default store;
