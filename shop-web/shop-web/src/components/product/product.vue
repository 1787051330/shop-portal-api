<template>

  <div>
    <el-menu
      :default-active="activeIndex2"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b">
      <el-submenu index="2">
        <template slot="title">{{userName}}<el-avatar :size="50" :src="userUrl"></el-avatar></template>

        <el-menu-item index="2-1" @click="toQueryUserLogin()">个人资料</el-menu-item>
        <el-menu-item index="2-2">修改密码</el-menu-item>
        <el-menu-item index="2-3">会员中心</el-menu-item>
      </el-submenu>
    </el-menu>

    <el-input
      placeholder="请输入内容"
      v-model="inputName" style="width: 200px" @keyup.enter.native="submit()">
      <i slot="prefix" class="el-input__icon el-icon-search"></i>
    </el-input>

    <el-table :data="shopDate" style="width: 100%">
      <el-table-column prop="id" label="序号" >
      </el-table-column>
      <el-table-column prop="name" label="商品名称" >
      </el-table-column>
      <el-table-column prop="price" label="价格" >
      </el-table-column>
      <el-table-column prop="stock" label="库存" >
      </el-table-column>
      <el-table-column prop="status" label="状态">
      </el-table-column>
      <el-table-column prop="detail" label="商品描述">
      </el-table-column>
      <el-table-column prop="createtime" label="日期" >
      </el-table-column>
      <el-table-column prop="subtitle" label="宣传标题" >
      </el-table-column>
      <el-table-column  label="操作">
        <template slot-scope="scope">
        <el-button type="success" plain @click="toAddCard(scope.$index,scope.row)"><i class="el-icon-shopping-bag-2"></i>加入购物车</el-button>
        </template>
      </el-table-column>


    </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="pageSizes"
        :page-size="100"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalCount">
      </el-pagination>
    <div>
    <el-badge :value="cartNum" :max="99" style="float: left" class="item">
      <el-button type="primary" @click="mycart()" plain><i class="el-icon-shopping-cart-2"></i>我的购物车</el-button>
    </el-badge>
    </div>
    <el-drawer
      :visible.sync="dialog"
      direction="rtl"
      custom-class="demo-drawer"
    >
      <div class="demo-drawer__content">
        <el-form :model="carForm">
          <el-form-item label="商品名称" :label-width="formLabelWidth" >
            <el-input v-model="carForm.name" autocomplete="off" class="input_class" readonly></el-input>
          </el-form-item>
          <el-form-item label="商品名称" :label-width="formLabelWidth">
            <el-input v-model="carForm.price" autocomplete="off" class="input_class" readonly></el-input>
          </el-form-item>
          <el-form-item label="商品库存" :label-width="formLabelWidth">
            <el-input v-model="carForm.stock" autocomplete="off" class="input_class" readonly></el-input>
          </el-form-item>
          <el-form-item label="购买数量" :label-width="formLabelWidth">
            <el-input-number v-model="carForm.num" @change="handleChange" class="input_class" :min="1" :max="carForm.stock" label="描述文字" ></el-input-number>
          </el-form-item>
          <el-form-item label="小计金额" :label-width="formLabelWidth">
            <el-input v-model="carForm.amount" autocomplete="off" class="input_class" readonly></el-input>
          </el-form-item>
        </el-form>
        <div class="demo-drawer__footer">
          <el-button type="primary" @click="addCard()">加入购物车</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>
<script>
  export default {
    data(){
      return {
        shopDate:[],
        currentPage:1,
        pageSizes:[2,5,8,10],
        pageSize:2,
        totalCount:0,
        inputName:"",
        carForm:{
          name:"",
          price:0.00,
          stock:0,
          num:1,
          amount:0.00
        },
        userUrl:"",
        dialog:false,
        cartNum:0,
        userName:"",
        formLabelWidth:"130px"
      }
    },
    created(){
      this.queryProduct();
      this.queryUserAdmin();
    },
    methods:{
      queryUserAdmin(){
        //查询当前用户数据进行展示
        this.$http({
          url:"/userService/users/getUser",
          method:"get",
        }).then(result=>{
          this.userName = result.data.data.loginName;
          this.userUrl = result.data.data.images;
        })
      },
      submit(){
        this.queryProduct();
      },
      handleSizeChange(val){
        this.pageSize = val,
        this.currentPage = 1,
        this.queryProduct();
      },
      handleCurrentChange(val){
        this.currentPage = val,
        this.queryProduct();
      },
      toQueryUserLogin(){
        var user = this.$router.resolve({
          "path":"/userMain"
        });
        window.open(user.href,"_blank");
      },
      addCard(){
        //将商品加入到购物车
        this.$http({
          url:"/cartService/carts",
          method:"post",
          params:{
            productId:this.carForm.productId,
            num:this.carForm.num
          }
        }).then(result=>{
          if(result.data.code == 200){
            this.$message("添加购物车成功");
            this.queryProduct();
            this.dialog = false;
            this.cartNum = result.data.data;
          }else{
            this.$message("添加购物车失败");
          }
        })

      },
      mycart(){
        //查看我的购物车
        var router = this.$router.resolve({
          "path":"/cart"
        });
        window.open(router.href,"_blank")
      },
      toAddCard(index,row){
        //弹出加入购物车页面
        if(row.stock > 0){
          this.carForm.num=1;
          this.dialog = true;
          this.carForm.name = row.name;
          this.carForm.price = row.price;
          this.carForm.stock = row.stock;
          this.carForm.productId = row.id;
          this.carForm.amount = row.price*this.carForm.num+".00";
        }else {
          this.$message("你选择的商品库存不足")
        }
      },
      handleChange(val){
        //加减商品
        this.carForm.amount = this.carForm.price*val+".00";
      },
      queryProduct(){
        this.$http({
          url:"/searchService/searchs",
          method: "post",
          params:{
            name:this.inputName,
            current:this.currentPage,
            size:this.pageSize
          }
        }).then(result=>{
          var data = result.data.data.data;
          this.cartNum = result.data.size;
          if(data == null || data.length == 0){
            this.$message({
              message:'没有你要查询的数据',
              type: 'warning'
            });
            return false;
          }
          this.shopDate = result.data.data.data;
          this.totalCount = result.data.data.total;
        })
      },
    }
  }
</script>
<style>
  .input_class{
    width: 200px;
  }
</style>
