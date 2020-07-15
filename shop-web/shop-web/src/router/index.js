import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import product from '@/components/product/product'
import cart from '@/components/cart/cart'
import address from '@/components/address/address'
import payOrder from '@/components/order/payOrder'
import paySuccess from '@/components/order/paySuccess'
import userMain from '@/components/user/userMain'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/product',
      name: 'product',
      component: product
    },
    {
      path: '/cart',
      name: 'cart',
      component: cart
    },
    {
      path: '/address',
      name: 'address',
      component: address
    },
    {
      path: '/payOrder',
      name: 'payOrder',
      component: payOrder
    },
    {
      path: '/paySuccess',
      name: 'paySuccess',
      component: paySuccess
    },
    {
      path: '/userMain',
      name: 'userMain',
      component: userMain
    },
  ]
})
