<template>
  <div>
    <div style="padding: 10px 0">
      <div style="padding: 10px 0;margin-top: 10px" >
      <el-upload
          class="upload-demo"
          drag
          action="http://101.42.234.173:9090/file/upload"
          multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传jpg/png文件,不超出50MB，可批量上传</div>
      </el-upload>
    </div>
      <el-input style="width: 200px" placeholder="请输入文件名" suffix-icon="el-icon-search" v-model="class_name">
      </el-input><el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning"  @click="clear1">重置</el-button>

    </div>

    <el-table :data="tableData" border stripe>
      <el-table-column prop="id" label="id" width="80">
      </el-table-column>
      <el-table-column prop="filename" label="文件名">
      </el-table-column>
      <el-table-column prop="type" label="文件类型" width="50">
      </el-table-column>
      <el-table-column prop="size" label="文件大小(kb)" width="180">
      </el-table-column>
      <el-table-column prop="is_delete" label="是否可下载" width="80" >
        <template slot-scope="scope">
          <el-switch
              v-model="scope.row.is_delete"
              disabled
          >
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="times" label="上传时间" width="180">
      </el-table-column>
      <el-table-column  label="操作" >
        <template slot-scope="scope">
          <!--    scope获取这一行的数据            <-->
          <el-button type="primary" @click="export1(scope.row.url,scope.row.is_delete)">下载</el-button>
          <el-button type="primary" @click="edit(scope.row)">检测预览</el-button>
          <el-button type="primary" @click="edit1(scope.row)">缺陷信息</el-button>
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
    <el-dialog title="检测图片展示" :visible.sync="dialogFormVisible" width="40%">
      <template>
      <div class="demo-fit" >
        <div class="block"  :key="fill">
<!--          <span class="title">7578</span>-->
          <el-avatar shape="square" :size="565" :fit="fill" :src="url"></el-avatar>
        </div>
      </div>
      </template>
      <div slot="footer" class="dialog-footer">
<!--        <el-button @click="dialogFormVisible = false">取 消</el-button>-->
        <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>

    <!--          弹窗-->
    <el-dialog title="编辑" :visible.sync="dialogtableVisible" width="80%">
      <el-table :data="tableData1" border stripe>
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
            :total=total1>
        </el-pagination>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogtableVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "File",
  data() {
    return {
      tableData: [],
      tableData1:[],
      total:0,total1:0,
      pageNum:1,
      pageSize:5,
      pageNum1:1,
      pageSize1:5,
      class_name:"",
      only_file:"",
      form:{},
      dialogFormVisible:false,
      dialogtableVisible:false,
      url: ''

    }
  },
  //初始化分页查询,获取数据
  created(){
    this.load()
  },
  methods: {

    load(){
      //请求分页查询数据,接口请求,使用axios
      request.get("/file/pagenow",{
        params:{
          pagNum:this.pageNum,
          pagSize:this.pageSize,
          filename:this.class_name
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
    // save(){
    //   request.post("/user",this.form).then(res =>{
    //     if(res){
    //       this.$message.success("保存成功")
    //       this.dialogFormVisible=false
    //       this.load()
    //     }else{
    //       this.$message.success("保存失败")
    //       this.dialogFormVisible=false
    //       // this.$router.push("/")跳转路由
    //     }
    //   })
    // },
    //点击页数实现翻页，使用的element的组件
    handleSizeChange(pageSize){

      this.pageSize=pageSize
      this.load()
    },

    handleCurrentChange(pageNum){

      this.pageNum=pageNum
      this.load()
    },
    export1(url,is_delete){
      if(is_delete==true){
        window.open(url)
      }else {
        alert("无法下载")
      }
    },
    //弹窗
    edit(row){
      // this.form=row
      this.dialogFormVisible=true;
      this.url=row.url;
    },
    edit1(row){
      // this.form=row;
      this.dialogtableVisible=true;
      request.get("/user/page_flaw",{
        params:{
          pagNum:this.pageNum1,
          pagSize:this.pageSize1,
          only_file:row.only_file,
        }
      }).then(res=>{
        //从后台取的数据给前台赋值
        console.log(res)
        this.total1=res.total
        console.log(res.data)
        this.tableData1=res.data
      })
    }
  },
}
</script>

<style scoped>

</style>