<template>
  <div>
    <el-container>
      <el-header>飞狐电商登录页面</el-header>
      <el-main>
        <el-form :model="loginForm" label-width="60px" class="demo-ruleForm">
          <el-form-item>
            手机号：
            <el-input type="text" v-model="loginForm.phone" style="width: 400px" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            验证码：
            <el-input type="text" v-model="loginForm.code" style="width: 285px" autocomplete="off"></el-input>
            <el-button type="primary" :disabled="!disabledCodeBtn"  @click="sendMessage()" >{{codeText}}</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="loginSubmit()">登录/注册</el-button>
            <el-button @click="resetForm('loginForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>
<script>

  export default {
    data(){
      return{
        codeText:'发送验证码',
        disabledCodeBtn: true,
        loginForm:{
          phone:"",
          code:"",
        }
      }
    },
    create(){

    },
    methods:{
      //重置
      resetForm(){
        location.reload();
      },
      //倒计时方法
      countDown(time){
        if (time === 0) {
          this.disabledCodeBtn = true
          this.codeText = "重新发送"
          return
        } else {
          this.disabledCodeBtn = false;
          this.codeText = '重新发送(' + time + ')'
          time--
        }
        setTimeout(()=> {
          this.countDown(time)
        }, 1000)
      },
      sendMessage(time){
        if(this.loginForm.phone == null || this.loginForm.phone.length == 0){
          this.$alert('请输入手机号获取验证码', '提示框', {
            confirmButtonText: '确定',
          });
          return false;
        }
        time = 60;
        this.countDown(time);
        this.$http({
          url:"/userService/users",
          method:"get",
          params:{
            phone:this.loginForm.phone,
          }
        }).then(result=>{
          this.$alert(result.data.msg, '提示框', {
            confirmButtonText: '确定',
          });
        })
      },
      loginSubmit: function () {
        if (this.loginForm.phone == null || this.loginForm.phone.length == 0) {
          this.$alert('请输入手机号获取验证码', '提示框', {
            confirmButtonText: '确定',
          });
          return false;
        }
        if (this.loginForm.code == null || this.loginForm.code.length == 0) {
          this.$alert("请输入验证码", "提示框", {
            confirmButtonText: '确定',
          });
          return false;
        }

        this.$http({
          url: "/userService/users",
          method: "post",
          params: {
            phone: this.loginForm.phone,
            code: this.loginForm.code
          }
        }).then(result => {
          if (result.data.code != 200) {
            this.$alert(result.data.msg, "提示框", {
              confirmButtonText: '确定',
            });
            return false;
          }
          /*this.$alert(result.data.data, "提示框", {
            confirmButtonText: '确定',
          });*/
          this.$store.dispatch("add_token",result.data.data.token);
          this.$store.dispatch("add_expTime",result.data.data.expTime);
          this.$store.dispatch("add_refreshTime",result.data.data.refreshTime);
          this.$router.push("product");
        })
      }
    }
  }

</script>
<style>
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 400px;
  }
  .el-header{
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
</style>
