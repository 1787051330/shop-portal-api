// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios'
import qs from 'qs'

Vue.prototype.qs = qs
Vue.prototype.$http = axios

axios.defaults.headers.common['Authorization-token'] = store.getters.token;

Vue.use(ElementUI);

Vue.config.productionTip = false
//发起axios请求拦截器
axios.interceptors.request.use(config=>{
  var url = config.url;
  //判断url放开登录所访问的url
  if(url != "/userService/users"){
      //刷新token
    var token = store.getters.token;
    if(token){
      var expTime = Number(store.getters.expTime);
      var refreshTime = Number(store.getters.refreshTime);
      var nowTime = Math.round(new Date().getTime());
      if(nowTime>refreshTime){
        if(nowTime<expTime){
          //刷新token
          axios({
            url:"/userService/users"+token,
            method:"get",
          }).then(result=>{
            if(result.data.code == 200){
              store.dispatch("add_token",result.data.data.token);
              store.dispatch("add_expTime",result.data.data.expTime);
              store.dispatch("add_refreshTime",result.data.data.refreshTime);
              config.headers.common['Authorization-token']=store.getters.token;
            }
          })
        }else{
          router.replace({
            path:"/"
          })
          return config;
        }
      }else{
        config.headers.common['Authorization-token']=store.getters.token;
      }
    }else{
      router.replace({
        path:"/"
      })
      return config;
    }
  }
  return config;
})

//axios回调函数
axios.interceptors.response.use(response=>{
  var code = response.data.code;
  if(typeof(code)!="undefined"){
    if(code == 5005 || code == 5006 ){
      router.replace({
        path:"/"
      })
    }
  }
  return response;
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
