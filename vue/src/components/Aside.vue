<template>
  <el-menu :default-openeds="['1', '3']"
           style="min-height: 100%;overflow-x: hidden;min-width: 100%"
           background-color="#303133"
           text-color="#FFFFFF"
           active-text-color="#F56C6C"
           router
  >
    <div style="height: 60px;line-height: 60px;text-align: center">
      <b style="color: aliceblue">铝片检测系统</b>

    </div>
    <el-submenu index="1">
      <template slot="title"><i class="el-icon-s-tools"></i>缺陷检测</template>

      <!--              <template slot="title">分组一</template>-->
      <el-menu-item index="/File">图片上传</el-menu-item>
      <el-menu-item index="/History">历史数据</el-menu-item>

    </el-submenu>
    <el-submenu index="2">
      <template slot="title"><i class="el-icon-office-building"></i>数据分析</template>
      <el-menu-item-group>
        <el-menu-item index="/Index">数据可视化展示</el-menu-item>
        <el-menu-item index="/pingtai">工厂检测平台</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside"
}
</script>

<style scoped>

</style>