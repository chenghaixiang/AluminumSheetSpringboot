<template>

  <el-container>
    <el-main>

      <div id="main" :style="{width: '1200px', height: '500px'}"> </div>
      <div style="padding: 10px 0">
        <el-button type="warning"  @click="zhans">每日缺陷统计</el-button>
        <el-button type="success" @click="table_zhangxian">整体缺陷统计</el-button>
        <el-button type="success" @click="echarts_1">缺陷占比</el-button>
      </div>
      <el-table :data="tableData" border stripe id="table1" highlight-current-row >
        <el-table-column prop="class_name" label="name" >
        </el-table-column>
        <el-table-column prop="num" label="缺陷数">
        </el-table-column>
<!--        <el-table-column prop="num1" label="平均缴费次数" width="80">-->
<!--        </el-table-column>-->
<!--        <el-table-column prop="type" label="类型" width="250">-->
<!--        </el-table-column>-->
      </el-table>
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
    </el-main>
  </el-container>
</template>

<script>
import * as echarts from 'echarts';
import request from "@/utils/request";
import $ from 'jquery';
var myChart;
var index
export default {
  name: "Index",
  data() {
    return {
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:5,
      class_name:"",
      form:{},
      dialogFormVisible:false,

    }
  },
  created(){
    // this.load()
  },
  mounted() {
    // this.zhans()
  },

  methods:{
    // dianji(num1){
    //    index=Math.floor(Math.random()*4)
    //   console.log(Math.floor(Math.random()*4))
    //   myChart.dispatchAction({
    //     type: "highlight",
    //     seriesIndex: 0,
    //     dataIndex:index
    //   });
    // },
    zhans() {

      var chartDom = document.getElementById('main');
      var myChart = echarts.init(chartDom);
      request.get("/user/ehcart_day").then(res=>{
        var i=0;
        for (i;i<res.data.length;i++){
          option.series[0].data[i]=res.data[i].num
          option.series[1].data[i]=res.data[i].num
          option.xAxis.data[i]=res.data[i].class_name
          console.log(option.xAxis.data[i])
          console.log(option.series[0].data[i])
        }
        myChart.setOption(option);
      })

      var option = {
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value'
        },
        tooltip:{
        },
        series: [
          {
            data: [],
            type: 'bar'
          },
          {
            data: [],
            type: 'line'
          }
        ]
      };
      this.load1()

    },
    table_zhangxian(){
      var chartDom = document.getElementById('main');
      var myChart = echarts.init(chartDom);
      request.get("/user/ehcart_lp").then(res=>{
        var i=0;
        for (i;i<4;i++){
          option.series[0].data[i]=res.data[i].num
          option.series[1].data[i]=res.data[i].num
          option.xAxis.data[i]=res.data[i].class_name
          console.log(option.xAxis.data[i])
          console.log(option.series[0].data[i])
        }
        myChart.setOption(option);
      })

      var option = {
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value'
        },
        tooltip:{
        },
        series: [
          {
            showBackground: true,
            data: [],
            type: 'bar'
          },
          {
            data: [],
            type: 'line'
          }
        ]
      };
      this.load()
      myChart.on('click', function(params) {
        // 控制台打印数据的名称
        console.log(params.name);

        this.setCurrent(row)
      });

    },
    load(){
      //请求分页查询数据,接口请求,使用axios
      request.get("/user/ehcart_lp",{
        params:{
          pagNum:this.pageNum,
          pagSize:this.pageSize,
        }
      }).then(res=>{
        //从后台取的数据给前台赋值
        console.log(res)
        this.total=res.total
        console.log(res.data)
        this.tableData=res.data
      })

    },
    load1(){
      //请求分页查询数据,接口请求,使用axios
      request.get("/user/ehcart_day",{
        params:{
          pagNum:this.pageNum,
          pagSize:this.pageSize,
        }
      }).then(res=>{
        //从后台取的数据给前台赋值
        console.log(res)
        this.total=res.total
        console.log(res.data)
        this.tableData=res.data
      })
    },
    echarts_1(){
      var chartDom = document.getElementById('main');
      if (myChart != null && myChart != "" && myChart != undefined) {
        myChart.dispose(document.getElementById("main"));
      }
      myChart = echarts.init(chartDom);
      request.get("/user/ehcart_lp").then(res=>{
        option.series[0].data=[
          {name:res.data[0].class_name,value:res.data[0].num},
          {name:res.data[1].class_name,value:res.data[1].num},
          {name:res.data[2].class_name,value:res.data[2].num},
          {name:res.data[3].class_name,value:res.data[3].num},
        ]
        console.log(res);
        myChart.setOption(option);
      })
      var option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '40',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
            ]
          }
        ]
      };
      this.load()

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
    setCurrent(row) { // 给操作行 高亮
      if (row) {
        this.$refs.singleTable.setCurrentRow(row, false);
      } else {
        this.$refs.singleTable.setCurrentRow();
      }
    }

  },

}
</script>


<style >
.el-table__body tr.current-row>td{
  background-color: #69A8EA !important;
  color: #fff;
}
</style>