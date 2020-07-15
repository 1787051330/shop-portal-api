const order={
  state:{
    "orderId":localStorage.getItem("orderId"),
    "totalMoney":localStorage.getItem("totalMoney"),
    "outTradeNo":localStorage.getItem("outTradeNo"),
    "orderOnlyFlag":localStorage.getItem("orderOnlyFlag")
  },
  mutations:{
    'set_orderId':(state,orderId)=>{
      state.orderId = orderId;
      localStorage.setItem("orderId",orderId);
    },
    'set_orderOnlyFlag':(state,orderOnlyFlag)=>{
      state.orderOnlyFlag = orderOnlyFlag;
      localStorage.setItem("orderOnlyFlag",orderOnlyFlag);
    },
    'set_totalMoney':(state,totalMoney)=>{
      state.totalMoney = totalMoney;
      localStorage.setItem("totalMoney",totalMoney);
    },
    'set_outTradeNo':(state,outTradeNo)=>{
      state.outTradeNo = outTradeNo;
      localStorage.setItem("outTradeNo",outTradeNo);
    },
  },
  actions:{
    add_orderId({commit},orderId){
      commit('set_orderId',orderId);
    },
    add_orderOnlyFlag({commit},orderOnlyFlag){
      commit('set_orderOnlyFlag',orderOnlyFlag);
    },
    add_totalMoney({commit},totalMoney){
      commit('set_totalMoney',totalMoney);
    },
    add_outTradeNo({commit},outTradeNo){
      commit('set_outTradeNo',outTradeNo);
    },
  }
}
export default order;
