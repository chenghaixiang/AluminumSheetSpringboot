<template>
  <el-table :data="tableData" border stripe id="table1" highlight-current-row >
    <el-table-column prop="class_name" label="缺陷名" >
    </el-table-column>
    <el-table-column prop="num" label="数量">
    </el-table-column>
    <!--        <el-table-column prop="num1" label="平均缴费次数" width="80">-->
    <!--        </el-table-column>-->
    <!--        <el-table-column prop="type" label="类型" width="250">-->
    <!--        </el-table-column>-->
  </el-table>
</template>

<script>
export default {
  name: "table_type"
}
</script>

<style scoped>

</style>