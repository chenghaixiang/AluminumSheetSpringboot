<template>
  <div id="main" :style="{width: '1200px', height: '500px'}"> </div>
</template>

<script>
import * as echarts from 'echarts';
import request from "@/utils/request";
export default {
  name: "echarts",
  data() {
    return {
      timmerOneAnim: null
    }
  },
  mounted() {
    this.echarts_1()
  },
  methods:{
    zhanshi(){
      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('main'));
      var checkName = ''
      var obj = {
        "xAxis":["潍坊","临沂","泰安","运河","青岛","东营","未管所","济宁","德州","潍北","鲁北","济南","北墅","滕州","鲁西","鲁南","湖西","微湖","淄博","鲁中","渝北区戒毒所","齐州","专属","威海","第二女子","聊城","济南第二","菏泽","省","鲁宁","烟台","新康","历山","枣庄","任城"],
        "datas":[86,50,25,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
      }
      // 指定图表的配置项和数据
      var option = {
        grid: {
          x: 40,
          y: 20,
          x2: 30,
          y2: 20,
          borderWidth: 0,
          bottom: "25%"
        },
        xAxis: {
          type: "category",
          data: obj.xAxis,
          axisLabel: {
            textStyle: {
              color: "#515A6E"
            },
            margin: 21,
            interval: 0,
            fontSize: 12,
            rotate: 0,
            formatter: function (params) {
              var newParamsName = "";
              var paramsNameNumber = params.length;
              var provideNumber = 6;
              var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
              if (paramsNameNumber > provideNumber) {
                for (var p = 0; p < rowNumber; p++) {
                  var tempStr = "";
                  var start = p * provideNumber;
                  var end = start + provideNumber;
                  if (p == rowNumber - 1) {
                    tempStr = params.substring(start, paramsNameNumber);
                  } else {
                    tempStr = params.substring(start, end) + "\n";
                  }
                  newParamsName += tempStr;
                }
              } else {
                newParamsName = params;
              }
              return newParamsName;
            },
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: "#E0E7EF",
              width: 0,
              type: "solid",
            },
          },
          axisTick: {
            show: false,
          },
        },
        yAxis: {
          type: "value",
          scale: true,
          max: 100,
          min: 0,
          splitNumber:5,
          boundaryGap: [0.2, 0.2],
          axisLabel: {
            textStyle: {
              color: "#999",
            },
            show: true,
            interval: 'auto',
            formatter: "{value}%",
          },
          axisLine: {
            show: false,
          },
          axisTick: {
            show: false,
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: 'rgb(240, 240, 240)',
              width: 1,
              type: 'solid'
            }
          }
        },
        dataZoom: [
          // {
          // type: 'slider',
          // show: true, //flase直接隐藏图形
          // xAxisIndex: [0],
          // left: '0%', //滚动条靠左侧的百分比
          // bottom: -5,
          // height: 15,
          // start: 0,//滚动条的起始位置
          // end: 40, //滚动条的截止位置（按比例分割你的柱状图x轴长度）
          // backgroundColor:"rgba(255,255,255,0)",
          // // fillerColor:"rgba(255,255,255,0.4)",
          // textStyle:false
          // }
          {
            type:'inside',
            filterMode: 'empty',
            maxValueSpan: 13,
            minValueSpan:10,
            start:0,
            end:40
          }
        ],
        series: [
          {
            type: "bar",
            // barCategoryGap:'40%',
            barWidth: 18,
            itemStyle: {
              normal: {
                // color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                //     { offset: 0, color: "#EAB2F6" },
                //     { offset: 0.5, color: "#8D8BFF" },
                // ]),
                color: function(params) {
                  let color1 = new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    { offset: 0, color: "#EAB2F6" },
                    { offset: 0.5, color: "#8D8BFF" },
                  ])
                  let color2 = '#000'
                  if (params.name === checkName) {
                    return color1
                  } else {
                    return color2
                  }
                },
                barBorderRadius: [30, 30, 0, 0],
                label:{
                  show:true,
                  position:'top',
                  textStyle:{
                    color:'#999',
                    fontSize:10
                  }
                }
              },
            },
            label:{normal:{
                formatter:"{c}%"
              }},
            data: obj.datas,
            showBackground: true,
            backgroundStyle: {
              color: "#f9fafc",
              barBorderRadius: [30, 30, 0, 0],
            },
          },
        ]
      }

      /*
          主要用在有高亮的数据部分可以点击  其他数据为0的点击不了
      */
      var drawChatOne = function() {
        myChart.clear()
        myChart.setOption(option);
        // 防止点击时触发多次事件
        myChart.off('click')
        // 添加柱状图点击事件
        myChart.on('click',function(params){
          console.log(params)
          //点击的柱子的名称
          checkName = params.name;
          //柱形图重构
          myChart.setOption(option);
          document.getElementById('text').innerHTML = checkName
        })
      }
      drawChatOne()

      /*
          主要用在所以显示的数据结构包括为0的都可以点击
      */
      var drawChatTwo = function() {
        myChart.clear()
        myChart.setOption(option);
        // 防止点击时触发多次事件
        myChart.getZr().off('click')
        // 添加柱状图点击事件
        myChart.getZr().on('click',function(params){
          console.log(params)
          let pointInPixel = [params.offsetX, params.offsetY]
          if (myChart.containPixel('grid', pointInPixel)) {
            // 拿到点击当前图表对应的下标
            let xIndex = myChart.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[0]
            //点击的柱子的名称
            checkName = obj.xAxis[xIndex]
          }
          //柱形图重构
          myChart.setOption(option);
          document.getElementById('text').innerHTML = checkName
        })
      }
    },
    echarts_1(){
      var chartDom = document.getElementById('main');
      var myChart = echarts.init(chartDom);
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
    },

  }
}
</script>

<style scoped>

</style>