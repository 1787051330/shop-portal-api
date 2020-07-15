<template>
  <div style="width: 600px;margin: 0px auto">
    <el-form ref="form" :model="userForm" label-width="80px">
      <el-form-item class="input_class">
        <el-avatar :size="50" :src="userForm.images"></el-avatar>
      </el-form-item>
      <el-form-item label="账户名称" class="input_class">
        <el-input v-model="userForm.name"></el-input>
      </el-form-item>
      <el-form-item label="手机号" class="input_class">
        <el-input v-model="userForm.phone" :disabled="btnDisable"></el-input>
      </el-form-item>
      <el-form-item label="账号状态" class="input_class">
        <el-input v-model="userForm.status"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">修改信息</el-button>
        <el-button @click="toShopProduct()">返回购买商品</el-button>
        <el-button type="hidden" @click="toUpdatePassword()">设置密码</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
    export default ({
        data() {
            return {
              userForm:{
                name:"",
                phone:"",
                status:"",
                images:"",
              },
              btnDisable:true

            };
        },
        created() {
          //查询出当前用户个人信息
          this.queryUser();
        },
        methods: {
          queryUser(){
            this.$http({
              url:"/userService/users/getUser",
              method:"get",
            }).then(result=>{
              if(result.data.code == 200){
                this.userForm.name = result.data.data.loginName;
                this.userForm.phone = result.data.data.phone;
                if(result.data.data.userStarts == 1){
                  this.userForm.status = "信誉良好";
                }else if(result.data.data.userStarts == 2){
                  this.userForm.status = "信誉正常";
                }else{
                  this.userForm.status = "信誉警告";
                }
                this.userForm.images = result.data.data.images;
                if(result.data.data.password == null || result.data.data.password == ""){
                    this.$message({
                      message:"还未设置密码请及时设置密码",
                      type:"error",
                    })

                }
              }
            })
          },
          onSubmit(){

          },
          toUpdatePassword(){
            this.$store.push("");
          },
          toShopProduct(){
            this.$router.push("/product")
          }
        }
    })
</script>
<style>
  .input_class{
    width: 600px;
  }
</style>
