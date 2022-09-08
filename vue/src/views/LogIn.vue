<template>
<div class="warpper">
  <div style="margin: 200px auto;background-color: #ffffff;width: 350px;height: 300px;padding: 20px;border-radius: 10px">
    <div style="margin: 20px 0;text-align: center;font-size: 26px"><b>登录</b>
    </div>
    <el-form :model="user" :rules="rules" ref="ruleForm">
      <el-form-item prop="username">
        <el-input size="medium" style="margin: 10px 0 " prefix-icon="el-icon-user-solid" v-model="user.username"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input size="medium" style="margin: 10px 0 " prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
      </el-form-item>
    </el-form>
    <div style="margin: 10px 0; text-align: right">
      <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
      <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/Register')">注册</el-button>
    </div>
  </div>
</div>
</template>

<script>
import request from "@/utils/request";
export default {
  name: "LogIn",
  data() {
    return {
      user:{},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
      }
    };

  },
  methods:{
    login(){
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          this.request.get("/user/login",{
            params:{
              username:this.user.username,
              password:this.user.password,
            }
          }).then(res=>{
            if(!res){
              this.$message.error("用户名或密码错误")
            }else {
              this.$router.push("/File")
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
}
</script>

<style >
 .warpper{
   height: 100vh;
   background-image: linear-gradient(to bottom right,#ffffff,#46b8da);
   overflow: hidden;
 }
</style>