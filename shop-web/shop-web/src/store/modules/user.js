const user={
  state:{
    token:localStorage.getItem("token"),
    name:'',
    expTime:localStorage.getItem("expTime"),
    refreshTime:localStorage.getItem("refreshTime"),
  }
  ,mutations:{
    'set_token':(state,token)=>{
      state.token = token;
      localStorage.setItem("token",token);
    },
    'set_expTime':(state,expTime)=>{
      state.expTime = expTime;
      localStorage.setItem("expTime",expTime);
    },
    'set_refreshTime':(state,refreshTime)=>{
      state.refreshTime = refreshTime;
      localStorage.setItem("refreshTime",refreshTime);
    },
    'set_name':(state,name)=>{
      state.name = name;
    },
    'delete_token':(state)=>{
      state.token = "";
    },
    'delete_name':(state)=>{
      state.name = "";
    }
  },
  actions:{
    add_token({commit},token){
      commit('set_token',token);
    },
    add_expTime({commit},expTime){
      commit('set_expTime',expTime);
    },
    add_refreshTime({commit},refreshTime){
      commit('set_refreshTime',refreshTime);
    }
  }
};
export default user;
