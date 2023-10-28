<template>
  <div class="dashboard-editor-container">

    <panel-group :mylistDatas = "mydata" />

<!--折线图表-->
    <el-row style="background:#FFFFFF;padding:0px 0px 0;margin-bottom:0px;">
      <div class="chart">
        <div class="chart1">
          <p>学习进度统计:</p>
          <dv-scroll-ranking-board :config="config" style="width:350px;height:300px;margin-left:10px"  />
        </div>
        <div class="chart2">
          <p>班级作答分析:</p>
          <PieChart></PieChart>
        </div>
      </div>
    </el-row>
    <!--折线图表-->
    <el-row style="background:#FFFFFF;padding:0px 0px 0;margin-bottom:0px;">
      <div class="chart">
        <div class="chart3">
          <p>每小题得分分析:</p>
          <LineChart></LineChart>
        </div>
        <div class="chart4">
          <p>本周学习人数分析:</p>
          <BarChart></BarChart>
        </div>
      </div>
    </el-row>
  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'
import { multiply_questionsCount } from '../api/questions/multiply_questions'
import { tf_count } from '../api/questions/tf_questions'
import{ single_count } from '../api/questions/single_questions'
import Progress from './dashboard/Progress'
import RankingBoard from './dashboard/RankingBoard'

const PanelGroupData = {
  start: {
    //总数
    allCount:19,
    //单选题总数
    singleItem_count:6,
    //多选题总数
    multiplyItems_count:6,
    //判断题总数
    trueorfalseItem_count:6
  }
}

// const lineChartData = {
//   newVisitis: {
//     expectedData: [100, 120, 161, 134, 105, 160, 165],
//     actualData: [120, 82, 91, 154, 162, 140, 145]
//   },
//   messages: {
//     expectedData: [200, 192, 120, 144, 160, 130, 140],
//     actualData: [180, 160, 151, 106, 145, 150, 130]
//   },
//   purchases: {
//     expectedData: [80, 100, 121, 104, 105, 90, 100],
//     actualData: [120, 90, 100, 138, 142, 130, 130]
//   },
//   shoppings: {
//     expectedData: [130, 140, 141, 142, 145, 150, 160],
//     actualData: [120, 82, 91, 154, 162, 140, 130]
//   }
// }

export default {
  name: 'Index',
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart,
    Progress,
    RankingBoard
  },
  data() {
    return {
      mydata: PanelGroupData.start,
      config:{
        data: [
          {
            name: '周兰',
            value: 55
          },
          {
            name: '王松',
            value: 100
          },
          {
            name: '茅台',
            value: 78
          },
          {
            name: '闰土',
            value: 66
          },
          {
            name: '张三',
            value: 80
          },
          {
            name: '李四',
            value: 45
          },
          {
            name: '王二',
            value: 10
          }
        ],
        unit:'%',
        rowNum:4
      }
    }
  },
  mounted() {
    this.getDatas();
  },
  methods: {
    getDatas() {
        let singledata = 0;
        let multiplydata = 0;
        let tfdata = 0;
        single_count().then(res => {
            console.log("打印单选题题数：", res.total);
            this.mydata.singleItem_count = null;
            singledata = res.total;
            this.mydata.singleItem_count = singledata;
          this.mydata.allCount = 0;
            this.mydata.allCount = singledata + this.mydata.allCount;
            console.log("此时的数据1******", this.mydata.singleItem_count);
          });
        multiply_questionsCount().then(res => {
          // console.log("打印多选题题数返回值",res.total);
          this.mydata.multiplyItems_count = null;
          multiplydata = res.total;
          this.mydata.multiplyItems_count =  multiplydata;
          this.mydata.allCount = multiplydata + this.mydata.allCount;
          console.log("此时的数据2*****", this.mydata.multiplyItems_count);
        });
        tf_count().then(res => {
          //赋值之前先清空
          this.mydata.trueorfalseItem_count = null;
          tfdata = res.total;
          this.mydata.trueorfalseItem_count = tfdata;
          this.mydata.allCount = tfdata + this.mydata.allCount;
          console.log("此时的数据3*****", this.mydata.trueorfalseItem_count);
        });
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #FFFFFF;
    padding: 16px 16px 0;
    margin-bottom: 32px;
    margin-top: 10px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}

.chart{
  height: 380px;
  width: 100%;
  margin-top: 0px;
  /*border: #7a6df0 solid 5px;*/
}
.chart1{
  float: left;
  width: 40%;
  height: 380px;
  margin-right: 5%;
  text-align: left;
  color: #FFFFFF;
  font-size: 25px;
  //border: greenyellow dotted 5px;
  margin-bottom: 0px;
  background:#27408B;
  //margin-left: 10px;
}
.chart2{
  float: left;
  width: 50%;
  height: 380px;
  margin-left: 2px;
  //border: 3px solid red;
  text-align: left;
  /*color: #1c84c6;*/
  font-size: 25px;

}
p{
  line-height: 30px;
  margin-left: 5px;
  //border: #ed5565 solid 3px;
  color:#7a6df0;
}
.chart3{
  float: left;
  width: 100%;
  height: 400px;
  margin-right: 5%;
  text-align: left;
  color: #000;
  font-size: 25px;
  //border: greenyellow dotted 5px;
  margin-bottom: 0px;
  background:#FFFFFF;
  margin-left: 10px;
}
.chart4{
  float: left;
  width: 100%;
  height: 400px;
  margin-left: 10px;
  //border: 3px solid red;
  text-align: left;
  /*color: #1c84c6;*/
  font-size: 25px;
  margin-top: 30px;

}
</style>
