
const getters={
  token:state=>state.user.token,
  expTime:state=>state.user.expTime,
  refreshTime:state=>state.user.refreshTime,
  orderId:state=>state.order.orderId,
  totalMoney:state=>state.order.totalMoney,
  outTradeNo:state=>state.order.outTradeNo,
  orderOnlyFlag:state=>state.order.orderOnlyFlag,
}
export default getters;
