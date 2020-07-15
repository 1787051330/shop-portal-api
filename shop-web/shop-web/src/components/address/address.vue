<template>
  <div>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>请选择收货地址</span>
      <el-button style="float: right; padding: 3px 0" @click="toAddress()" type="text">添加地址</el-button>
    </div>
    <el-table
      :data="addressList" @row-click="openDetails">
      <el-table-column>
        <template slot-scope="scope">
          <el-radio v-model="checkAddree" :label="scope.row.id" border size="medium">选择</el-radio>
        </template>
      </el-table-column>
      <el-table-column
        label="姓名">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>姓名: {{ scope.row.consignee }}</p>
            <p>住址: {{ scope.row.address }}</p>
            <p>手机号码: {{ scope.row.phone }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.consignee }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        label="地址类型">
        <template slot-scope="scope">
          <i class="el-icon-location-information"></i>
          <span style="margin-left: 10px">{{ scope.row.alias }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
    <div>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>购买商品详情</span>
        </div>
      <el-table
        :data="productList">
        <el-table-column
          prop="productName"
          label="商品名称">
        </el-table-column>
        <el-table-column
          prop="price"
          label="商品单价">
        </el-table-column>
        <el-table-column
          prop="num"
          label="购买数量">
        </el-table-column>
        <el-table-column
          label="是否有货">
          <template slot-scope="scope">
            <span>{{(scope.row.stock>scope.row.num)?"有货":"无货"}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="subtotal"
          label="小计金额">
        </el-table-column>
      </el-table>
        <div style="float: right">
          <el-button type="primary" :disabled="btnDisabled" @click="submitOrder()">生成订单</el-button>
          <font size="5px" color="red">总价：<span> {{ "¥ " + totalMoney.toFixed(2) +" 元" }}</span></font>
        </div>
        <div>
          <el-drawer
            :visible.sync="dialogAdd"
            direction="rtl"
            custom-class="demo-drawer"
          >
            <div class="demo-drawer__content">

              <el-form :model="addressForm">
                <el-form-item label="收货人" :label-width="formLabelWidth" >
                  <el-input v-model="addressForm.consignee" autocomplete="off" class="input_class"></el-input>
                </el-form-item>
                <el-form-item label="收货地址" :label-width="formLabelWidth">
                  <el-input v-model="addressForm.address" autocomplete="off" class="input_class"></el-input>
                </el-form-item>
                <el-form-item label="收货人的手机号" :label-width="formLabelWidth">
                  <el-input v-model="addressForm.phone" autocomplete="off" class="input_class"></el-input>
                </el-form-item>
                <el-form-item label="收货邮箱" :label-width="formLabelWidth">
                  <el-input v-model="addressForm.email" class="input_class" ></el-input>
                </el-form-item>
                <el-form-item label="例如：家，公司" :label-width="formLabelWidth">
                  <el-input v-model="addressForm.alias" autocomplete="off" class="input_class"></el-input>
                </el-form-item>
              </el-form>
              <div class="demo-drawer__footer">
                <el-button type="primary" @click="address()">保存</el-button>
              </div>
            </div>
          </el-drawer>
          <el-drawer
            :visible.sync="dialogUpdate"
            direction="rtl"
            custom-class="demo-drawer"
          >
            <div class="demo-drawer__content">

              <el-form :model="addressUpdateForm">
                <el-form-item label="收货人" :label-width="formLabelWidth" >
                  <el-input v-model="addressUpdateForm.consignee" autocomplete="off" class="input_class"></el-input>
                </el-form-item>
                <el-form-item label="收货地址" :label-width="formLabelWidth">
                  <el-input v-model="addressUpdateForm.address" autocomplete="off" class="input_class"></el-input>
                </el-form-item>
                <el-form-item label="收货人的手机号" :label-width="formLabelWidth">
                  <el-input v-model="addressUpdateForm.phone" autocomplete="off" class="input_class"></el-input>
                </el-form-item>
                <el-form-item label="收货邮箱" :label-width="formLabelWidth">
                  <el-input v-model="addressUpdateForm.email" class="input_class" ></el-input>
                </el-form-item>
                <el-form-item label="例如：家，公司" :label-width="formLabelWidth">
                  <el-input v-model="addressUpdateForm.alias" autocomplete="off" class="input_class"></el-input>
                </el-form-item>
                <el-input v-model="addressUpdateForm.id" type="hidden" autocomplete="off" class="input_class"></el-input>
              </el-form>
              <div class="demo-drawer__footer">
                <el-button type="primary" @click="addUpdateress()">保存</el-button>
              </div>
            </div>
          </el-drawer>
        </div>
      </el-card>

    </div>
  </div>
</template>
<script>
    export default ({
        data() {
            return {
              addressList:[],
              addressUpdateForm:{
                consignee:"",
                address:"",
                phone:"",
                email:"",
                alias:"",
                id:0,
              },
              addressForm:{
                consignee:"",
                address:"",
                phone:"",
                email:"",
                alias:"",
              },
              checkAddree:0,
              productList:[],
              totalMoney:0.00,
              dialogAdd:false,
              dialogUpdate:false,
              formLabelWidth:"130px",
              btnDisabled:false
            };
        },
      filters:{ // 过滤器 对数据实现转换 可以定义全局的 也可以定义局部的 这个是局部的 只有vue的实例才可以使用
        formatMoney:function (value) { // 默认接收一个参数
          return "¥ " + value.toFixed(2) +" 元"; // 返回一个¥ 加上2位小数
        }
      },
        created() {
            this.queryAddress();
            this.queryProductChecked();
        },

        methods: {
          queryAddress(){
            this.$http({
              url:"/userService/address",
              method: "get",
            }).then(result=>{
              this.addressList = result.data.data;
            })
          },
          openDetails(row){
            this.checkAddree = row.id
          },
          queryProductChecked(){
            this.$http({
              url:"/cartService/carts/checkedCarts",
              method:"get",
            }).then(result=>{
              this.productList = result.data.data;
              var _this = this;
              this.productList.forEach(function (item,index) {
                if(item.checked){
                  _this.totalMoney += item.subtotal;
                }
              })
            })
          },
          //删除当前地址
          handleDelete(index,row){
           //提示是否删除该地址
            this.$confirm('是否删除该地址, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(()=>{
              this.$http({
                url:"/userService/address",
                method:"delete",
                params:{
                  addressId:row.id
                }
              }).then(result=>{

                if(result.data.code == 200){
                  this.$message({
                    message:"删除地址成功",
                    type: 'success'
                  });
                  //删除完成之后再次加载地址信息
                  this.queryAddress();
                }else{
                  this.$message({
                    message:"删除地址失败",
                    type: 'warning'
                  });
                }
              })
            })
          },
          toAddress(){
            this.dialogAdd = true;
          },
          submitOrder(){
            this.btnDisabled = true;
            //获取选中地址的ID
            var addressId = this.checkAddree;
            if(addressId == false){
              this.$message({
                message:"请选择配送地址",
                type:"error"
              });
              this.btnDisabled = false;
              return false;
            };
            var orderOnlyFlag = this.$store.getters.orderOnlyFlag
            //发起请求生成订单
            this.$http({
              url:"/orderService/orders",
              method:"post",
              params: {
                addressId:addressId,
                orderOnlyFlag:orderOnlyFlag
              }
            }).then(result=>{
              if(result.data.code == 20002){
                this.$message({
                  message:result.data.msg,
                  type:"error",
                });
              }else if(result.data.code == 500){
                var data = result.data.data;
                if(data != null){
                  var messages = "商品名称为";
                  data.forEach(function (item,index) {
                    messages+=item.productName+",";
                  })
                  messages += "库存不足";
                  this.$message({
                    message:messages,
                    type:"error",
                  });
                }
              }else if(result.data.code == 20005){
                this.$message({
                  message:result.data.msg,
                  type:"error",
                });
              }else if(result.data.code == 200){
                this.$message({
                  message:"订单生成成功,请尽快支付",
                  type:"success",
                });
                //成功之后将返回的订单号、流水号、支付金额放到vuex中去
                this.$store.dispatch("add_orderId",result.data.data.orderId);
                this.$store.dispatch("add_totalMoney",result.data.data.totalMoney);
                this.$store.dispatch("add_outTradeNo",result.data.data.outTradeNo);
                //跳转到支付页面
                this.$router.push("payOrder");
              }else{
                this.$message({
                  message:"未知错误",
                  type:"error",
                });
              }
            })
          },
          address(){
            this.$http({
              url:"/userService/address",
              method:"post",
              params:{
                consignee:this.addressForm.consignee,
                address:this.addressForm.address,
                phone:this.addressForm.phone,
                email:this.addressForm.email,
                alias:this.addressForm.alias,
              }
            }).then(result=>{
              if(result.data.code == 200){
                this.dialogAdd = false;
                this.addressForm = "";
                this.$message({
                  message:"添加地址成功",
                  type: 'success'
                });
                this.queryAddress();
              }else{
                this.$message({
                  message:"添加地址失败",
                  type: 'warning'
                })
              }
            })
          },
          handleEdit(index,row){
            this.dialogUpdate = true;
            this.addressUpdateForm.consignee = row.consignee
            this.addressUpdateForm.address = row.address
            this.addressUpdateForm.phone = row.phone
            this.addressUpdateForm.email = row.email
            this.addressUpdateForm.alias = row.alias
            this.addressUpdateForm.creatorId = row.creatorId
            this.addressUpdateForm.createTime = row.createTime
            this.addressUpdateForm.id = row.id
          },
          addUpdateress(){
            //发起请求修改
            this.$http({
              url:"/userService/address",
              method:"put",
              params:{
                consignee:this.addressUpdateForm.consignee,
                address:this.addressUpdateForm.address,
                phone:this.addressUpdateForm.phone,
                email:this.addressUpdateForm.email,
                alias:this.addressUpdateForm.alias,
                id:this.addressUpdateForm.id,
              },
            }).then(result=>{
              if(result.data.code == 200){
                this.dialogUpdate = false;
                this.addressUpdateForm = "";
                this.$message({
                  message:"修改地址成功",
                  type: 'success'
                });
                this.queryAddress();
              }else{
                this.$message({
                  message:"修改地址失败",
                  type: 'error'
                });
              }
            })
          }
        }
    })
</script>
<style>
  .input_class{
    width: 200px;
  }
</style>
