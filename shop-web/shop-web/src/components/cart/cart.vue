<template xmlns:v-on="http://www.w3.org/1999/xhtml" xmlns:v-bind="http://www.w3.org/1999/xhtml">
<div>
  <svg style="position: absolute; width: 0; height: 0; overflow: hidden;" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
    <defs>
      <symbol id="icon-add" viewBox="0 0 32 32">
        <title>add2</title>
        <path class="path1" d="M15 17h-13.664c-0.554 0-1.002-0.446-1.002-1 0-0.552 0.452-1 1.002-1h13.664v-13.664c0-0.554 0.446-1.002 1-1.002 0.552 0 1 0.452 1 1.002v13.664h13.664c0.554 0 1.002 0.446 1.002 1 0 0.552-0.452 1-1.002 1h-13.664v13.664c0 0.554-0.446 1.002-1 1.002-0.552 0-1-0.452-1-1.002v-13.664z"></path>
      </symbol>
      <symbol id="icon-ok" viewBox="0 0 39 32">
        <title>ok</title>
        <path class="path1" d="M14.084 20.656l-7.845-9.282c-1.288-1.482-3.534-1.639-5.016-0.351s-1.639 3.534-0.351 5.016l10.697 12.306c1.451 1.669 4.057 1.623 5.448-0.096l18.168-22.456c1.235-1.527 0.999-3.765-0.528-5.001s-3.765-0.999-5.001 0.528l-15.573 19.337z"></path>
      </symbol>
      <symbol id="icon-edit" viewBox="0 0 32 32">
        <title>edit</title>
        <path class="path1" d="M25.599 11.292l-4.892-4.892 3.825-3.825 4.892 4.892-3.825 3.825zM4.732 23.308l3.959 3.959-5.939 1.98 1.98-5.939zM10.666 26.225l-4.892-4.892 13.425-13.425 4.892 4.892-13.425 13.425zM31.687 6.713l-6.4-6.4c-0.417-0.417-1.091-0.417-1.508 0l-20.267 20.267c-0.114 0.115-0.191 0.25-0.242 0.393-0.003 0.009-0.012 0.015-0.015 0.025l-3.2 9.6c-0.128 0.383-0.029 0.806 0.257 1.091 0.203 0.204 0.476 0.313 0.754 0.313 0.112 0 0.227-0.017 0.337-0.054l9.6-3.2c0.011-0.003 0.017-0.013 0.027-0.016 0.142-0.052 0.276-0.128 0.39-0.242l20.267-20.267c0.417-0.416 0.417-1.091 0-1.508v0z"></path>
      </symbol>
      <symbol id="icon-del" viewBox="0 0 26 32">
        <title>delete</title>
        <path class="path1" d="M17.723 28c0.543 0 0.984-0.448 0.984-1v-12c0-0.552-0.441-1-0.984-1s-0.985 0.448-0.985 1v12c0 0.552 0.441 1 0.985 1v0zM7.877 28c0.543 0 0.984-0.448 0.984-1v-12c0-0.552-0.441-1-0.984-1s-0.985 0.448-0.985 1v12c0 0.552 0.441 1 0.985 1v0zM12.8 28c0.543 0 0.985-0.448 0.985-1v-12c0-0.552-0.441-1-0.985-1s-0.984 0.448-0.984 1v12c0 0.552 0.441 1 0.984 1v0zM23.631 4h-5.908v-2c0-1.104-0.882-2-1.969-2h-5.908c-1.087 0-1.969 0.896-1.969 2v2h-5.908c-1.087 0-1.969 0.896-1.969 2v2c0 1.104 0.882 2 1.969 2v18c0 2.208 1.765 4 3.939 4h13.784c2.174 0 3.938-1.792 3.938-4v-18c1.087 0 1.969-0.896 1.969-2v-2c0-1.104-0.882-2-1.969-2v0zM9.846 3c0-0.552 0.441-1 0.984-1h3.938c0.544 0 0.985 0.448 0.985 1v1h-5.908v-1zM21.662 28c0 1.104-0.882 2-1.969 2h-13.784c-1.087 0-1.97-0.896-1.97-2v-18h17.723v18zM22.646 8h-19.692c-0.543 0-0.985-0.448-0.985-1s0.441-1 0.985-1h19.692c0.543 0 0.984 0.448 0.984 1s-0.441 1-0.984 1v0z"></path>
      </symbol>
      <symbol id="icon-clock" viewBox="0 0 32 32">
        <title>clock</title>
        <path class="path1" d="M29.333 16c0-7.364-5.97-13.333-13.333-13.333s-13.333 5.97-13.333 13.333c0 7.364 5.97 13.333 13.333 13.333s13.333-5.97 13.333-13.333v0 0 0 0 0 0zM0 16c0-8.837 7.163-16 16-16s16 7.163 16 16c0 8.837-7.163 16-16 16s-16-7.163-16-16zM14.667 14.667v1.333h2.667v-10.667h-2.667v9.333zM24 18.667h1.333v-2.667h-10.667v2.667h9.333z"></path>
      </symbol>
    </defs>
  </svg>
  <div class="container">
    <div class="cart">
      <div class="checkout-title">
        <span>Vue购物车实例</span>

      </div>
      <div>
        <el-button type="danger" style="float: right" @click="delAllCart()"><i class="el-icon-delete-solid"></i>清空购物车</el-button>
      </div>

      <!--商品信息-->
      <div class="item-list-wrap">
        <div class="cart-item">
          <div class="cart-item-head">
            <ul>
              <li>商品信息</li>
              <li>商品金额</li>
              <li>商品数量</li>
              <li>总金额</li>
              <li>编辑</li>
            </ul>
          </div>
          <!--商品列表信息-->

          <ul class="cart-item-list">
            <li v-for="(item,index) in productList"> <!--item of productList 这两个是一样的-->
              <!--
              这块的 index 是让自己知道
              这块的index 是表示索引的值 和 js原声的 forEach(function(value,index){ }) 是一样的
              jq的 $.each(function(index ,value ){ }) 和它们是相反的
              -->
              <div class="cart-tab-1">
                <div class="cart-item-check">
                  <!--v-bind:class="" 里面一定是 一个对象或者是数组的形式
                   v-bind:class="{ }"  v-bind:class="[ ]"
                   -->
                  <a href="javascript:;" class="item-check-btn" v-bind:class="{'check':item.checked} " @click="selectedProduct(item)">
                    <svg class="icon icon-ok"><use xlink:href="#icon-ok"></use></svg>
                  </a>
                </div>
                <!--图片后面的文字-->
                <div class="cart-item-title">
                  <div class="item-name">
                    {{ item.productName}}
                  </div>
                </div>
              </div>
              <!--第二列-->
              <div class="cart-tab-2">
                <!-- 过滤器的使用方法 | 加方法 -->
                <div class="item-price"> {{ item.price | formatMoney }}</div>
              </div>
              <!--第三列-->
              <div class="cart-tab-3">
                <div class="item-quantity">
                  <div class="select-self select-self-open">
                    <div class="quentity">
                        <el-input-number v-model="item.num" @change="handleChange(item)" class="input_class" :min="1" label="描述文字" ></el-input-number>
                    </div>
                  </div>
                  <div class="item-stock"> {{item.stock-item.num>0?"有货":"库存不足"}} </div>
                </div>
              </div>
              <!--第四列-->
              <div class="cart-tab-4">
                <!-- 总金额是当前的单价乘以商品总数-->
                <div class="item-price-total">{{item.subtotal | formatMoney}}</div>

              </div>
              <!--第五列-->
              <div class="cart-tab-5">
                <div class="cart-item-opration">
                  <a href="javascript:;" class="item-edit-btn" @click="delConfirm(item)">
                    <svg class="icon icon-del"><use xlink:href="#icon-del"></use></svg>
                  </a>
                </div>
              </div>

            </li>
          </ul>
        </div>
      </div>

      <!--footer-->
      <div class="cart-foot-wrap">
        <!--footer 左边的-->
        <div class="cart-foot-l">
          <div class="item-all-check">
            <a href="javascript:;">
              <!--v-bind:class 的简写  :class-->
              <span class="item-check-btn" :class="{'check': checkAllFlag}" @click="checkAll(true)">
                                <svg class="icon icon-ok"><use xlink:href="#icon-ok"></use></svg>
                            </span>
              <span @click="checkAll(true)"> 全选 </span>
            </a>
          </div>
        </div>
        <!--footer 右边的-->
        <div class="cart-foot-r">
          <div class="item-total">
            总价：<span class="total-price"> {{ "¥ " + totalMoney.toFixed(2) +" 元" }}</span>
          </div>
          <div  class="next-btn-wrap">
            <a href="javascript:;" @click="statement()" class="btn btn--red" > 结账 </a>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!--模态框-->
  <div class="md-modal modal-msg md-modal-transtion " id="showModal" :class="{'md-show': delFlag}">
    <div class="md-modal-inner">
      <div class="md-top">
        <button class="md-close" @click="delFlag= false"> 关闭 </button>
      </div>
      <div class="md-content">
        <div class="confirm-tips">
          <p id="cusLanInfo" lan="Cart.Del.Confirm">你确认删除此订单信息吗?</p>
        </div>
        <div class="btn-wrap col-2">
          <button class="btn btn--m" id="btnModalConfirm" @click=" delProduct()"> YES</button>
          <button class="btn btn--m  btn--red" id="btnModalCancel" @click=" delFlag = false" > NO</button>
        </div>
      </div>
    </div>
  </div>
  <!--遮罩层-->
  <div class="md-overlay" id="showOverLay" v-if=" delFlag"></div>
</div>
</template>
<script>
  import "./css/checkout.css"
  import "./css/base2.css"
  import "./css/modal.css"
  export default {
    data(){
      return {
        formatMoney:0,
        totalMoney:0,         // 总金额
        productList:[],       // 定义一个数组
        checkAllFlag:false,   // 定义是否全选
        curProduct:'',        // 保存删除的商品信息
        delFlag:false,         // 默认弹出框为false
        checked:false
      };
    },
    filters:{ // 过滤器 对数据实现转换 可以定义全局的 也可以定义局部的 这个是局部的 只有vue的实例才可以使用
      formatMoney:function (value) { // 默认接收一个参数
        return "¥ " + value.toFixed(2) +" 元"; // 返回一个¥ 加上2位小数
      }
    },
    created() {
      this.queryCartList();
    },
    methods:{
      queryCartList(){
        this.$http({
          url:"/cartService/carts",
          method:"get",
        }).then(result=>{
          this.productList = result.data.data;
          this.queryTotalMoney();
        })
      },
      delAllCart(){
        this.$confirm('确定删除购物车中所有的商品吗, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          if(this.productList == null || this.productList == ""){
            this.$message({
              message:"购物车中没有商品",
              type: 'warning'
            });
            return false;
          }
          //发起请求删除当前用户购物车中所有的数据
          this.$http({
            url:"/cartService/carts/delAllCarts",
            method:"delete",
          }).then(result=>{
            if(result.data.code == 200){
              this.$message({
                message:"清空购物车成功",
                type: 'success'
              });
              this.queryCartList();
            }else{
              this.$message({
                message:"清空购物车失败",
                type: 'warning'
              });
            }

          })
        })
      },
      selectedProduct(item){
        this.totalMoney = 0;
        item.checked = !item.checked;
        this.$http({
          url:"/cartService/carts",
          method: "put",
          params:{
            productId:item.productId,
            num:item.num,
            flag:2
          }
        }).then(result=>{
          if(result.data.code == 200){
            this.queryCartList();
            this.queryTotalMoney();
          }else{
            this.$message("增加/删除数量失败")
          }
        })
      },
      statement(){
        var flag = 1;
        this.productList.forEach(function (item,index) {
          if(item.checked){
            flag = 2;
          }
        })
        if(flag == 1){
          this.$message({
            message:"没有选择要结算的商品",
            type:"warning"
          })
          return false;
        }
        this.$http({
          url:"/orderService/orders",
          method:"get",
        }).then(result=>{
          //将生成的唯一标识放入vuex中去
          this.$store.dispatch("add_orderOnlyFlag",result.data.data);
          this.$router.push("address");
        })

      },
      delConfirm(item){
        this.$confirm('从购物车移除这个商品, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url:"/cartService/carts",
            method: "delete",
            params:{
              productId:item.productId
            }
          }).then(result=>{
            if(result.data.code == 200){
              this.queryCartList();
              this.queryTotalMoney();
            }else{
              this.$message("删除购物车失败")
            }
          })
        }).catch(() => {
          //几点取消的提示
        });

      },
      queryTotalMoney(){
        var _this = this;
        this.totalMoney = 0;
        this.productList.forEach(function (item,index) {
          if(item.checked){
            _this.totalMoney += item.subtotal;
          }
        })
      },
      checkAll(){
        this.checkAllFlag = !this.checkAllFlag;
        this.$http({
          url:"/cartService/carts/checkedAll",
          method: "put",
          params:{
            checkAllFlag:this.checkAllFlag
          }
        }).then(result=>{
          if(result.data.code == 200){
            this.queryCartList();
            this.queryTotalMoney();
          }else{
            this.$message("增加/删除数量失败")
          }
        })
      },
      handleChange(item){
        this.$http({
          url:"/cartService/carts",
          method: "put",
          params:{
            productId:item.productId,
            num:item.num,
            flag: 1
          }
        }).then(result=>{
          if(result.data.code == 200){
            this.queryCartList();

          }else{
            this.$message("增加/删除数量失败")
          }
        })
      }
    }
  }

</script>
<style>

</style>
