<template>
  <div class="warpper1">
    <div style="margin: 200px auto;background-color: #ffffff;width: 350px;height: 400px;padding: 20px;border-radius: 10px">
      <div style="margin: 20px 0;text-align: center;font-size: 26px"><b>注册</b>
      </div>
      <el-form :model="user" :rules="rules" ref="ruleForm">
        <el-form-item prop="username">
          <el-input placeholder="请输入用户名" size="medium" style="margin: 10px 0 " prefix-icon="el-icon-user-solid" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0 " prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input placeholder="请输入手机号" size="medium" style="margin: 10px 0 " prefix-icon="el-icon-phone" v-model="user.phone"></el-input>
        </el-form-item>
      </el-form>
      <div style="margin: 10px 0; text-align: right">
        <el-button type="primary" size="small" autocomplete="off" @click="Reg()">确定</el-button>
        <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/LogIn')">返回登录界面</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
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
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { min: 8, max: 11, message: '长度在 8 到 11 个数字', trigger: 'blur' }
        ],

      }
    };

  },
  methods:{
    Reg(){
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          this.request.get("/user/Regis",{
            params:{
              username:this.user.username,
              password:this.user.password,
              phone: this.user.phone,
            }
          }).then(res=>{
            if(!res){
              this.$message.error("注册信息有误")
            }else {
              this.$router.push("/LogIn")
            }
          })
        } else {
          this.$message.error("注册信息有误")
          return false;
        }
      });
    }
  }
}
</script>

<style >
.warpper1{
  height: 100vh;
  background-image: linear-gradient(to bottom right,#080808,#46b8da);
  overflow: hidden;
}
</style>