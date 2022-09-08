<template>
    <el-container style="height: 100vh; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246); height:100%">

        <Aside></Aside>
      </el-aside>

      <el-container>
<!--        <el-header >-->
<!--        <Header></Header>-->
<!--        </el-header>-->
        <el-main>
          <router-view />
        </el-main>
      </el-container>

    </el-container>

</template>

<style>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>
<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import request from "@/utils/request";
import Aside from "@/components/Aside";
import Header from "@/components/Header";


export default {
  name: 'HomeView',
  components: {
    Header,
    Aside,
    HelloWorld
  },

}
</script>
