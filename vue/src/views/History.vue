<template>
<div>
  <div style="padding: 10px 0">
    <el-input style="width: 200px" placeholder="请输入缺陷名或图片名" suffix-icon="el-icon-search" v-model="class_name">
    </el-input><el-button class="ml-5" type="primary" @click="load" id="find" >搜索</el-button>
    <el-button type="warning"  @click="clear1">重置</el-button>
    <el-button type="success" @click="export1">导出</el-button>
  </div>

  <el-table :data="tableData" border stripe>
    <el-table-column prop="u_id" label="图片名" width="120">
    </el-table-column>
    <el-table-column prop="class_name" label="缺陷名" width="120">
    </el-table-column>
    <el-table-column prop="score" label="准确率">
    </el-table-column>
    <el-table-column prop="xmin" label="xmin" >
    </el-table-column>
    <el-table-column prop="ymin" label="ymin" >
    </el-table-column>
    <el-table-column prop="xmax" label="xmax" >
    </el-table-column>
    <el-table-column prop="ymax" label="ymax" >
    </el-table-column>
    <el-table-column prop="len" label="len" width="80">
    </el-table-column>
    <el-table-column prop="times" label="检测时间" width="200">
    </el-table-column>
    <el-table-column  label="操作" width="100">
      <template slot-scope="scope">
        <!--    scope获取这一行的数据            <-->
        <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!--          页码组件-->
  <div style="padding: 10px 0">
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[2,5,10,30]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total=total>
    </el-pagination>
  </div>

  <!--          弹窗-->
  <el-dialog title="编辑" :visible.sync="dialogFormVisible" width="30%">
    <el-form label-width="80px" size="small">
      <el-form-item label="缺陷名" >
        <el-input v-model="form.class_name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="个数" >
        <el-input v-model="form.len" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="save" >确 定</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "History",
  data() {
    return {
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:5,
      class_name:"",
      form:{},
      dialogFormVisible:false

    }
  },
  //初始化分页查询,获取数据
  created(){
    this.load()
  },
  methods: {
    load(){
      //请求分页查询数据,接口请求,使用axios
      request.get("/user/page",{
        params:{
          pagNum:this.pageNum,
          pagSize:this.pageSize,
          class_name:this.class_name
        }
      }).then(res=>{
        //从后台取的数据给前台赋值
        console.log(res)
        this.total=res.total
        console.log(res.data)
        this.tableData=res.data
      })

    },
    //重置
    clear1(){
      this.class_name=""
      this.load()
    },
    //保存向后台插入数据，现在目前页面没用到
    save(){
      request.post("/user",this.form).then(res =>{
        if(res){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.success("保存失败")
          this.dialogFormVisible=false
          // this.$router.push("/")跳转路由
        }
      })
    },
    //弹窗
    edit(row){
      this.form=row
      this.dialogFormVisible=true;

    },
    //点击页数实现翻页，使用的element的组件
    handleSizeChange(pageSize){

      this.pageSize=pageSize
      this.load()
    },

    handleCurrentChange(pageNum){

      this.pageNum=pageNum
      this.load()
    },
    export1(){
      window.open("http://101.42.234.173:9090/user/export")
    }
  },
}
</script>

<style scoped>
.el-aside {
  color: #333;
}
</style>