<template>
  <div class="home">
    <div class="title">
      欢迎使用题库管理系统
    </div>
    <div class="chart">
      <div class="chart1">
        题目数量:
        <PieChart :chartData="mychartData"></PieChart>
      </div>
      <div class="chart2">
        错误率:
        <LineChart></LineChart>
      </div>
    </div>
  </div>
</template>

<script>
  import PieChart from './dashboard/PieChart'
  import LineChart from './dashboard/LineChart'
  import {single_count} from '@/api/questions/single_questions'
  import { multiply_questionsCount } from '../api/questions/multiply_questions'
  import{ tf_count} from '../api/questions/tf_questions'

  const PieChartData = {
    start: {
      //总题量
      allCount:6,
      //单选题总数
      singleItem_count:6,
      //多选题总数
      multiplyItems_count:6,
      //判断题总数
      trueorfalseItem_count:6
    }
  }

  export default {
    name:"Index",
    components:{
       PieChart,
       LineChart
    },
    data(){
      return{
        mychartData:PieChartData.start,
      }

    },
    created() {

      // // console.log("看看数据的样子");
      // // console.log("单选",this.singleItem_count);
      // // console.log("多选",this.mutiplyItems_count);\

    },
    mounted() {
      this.getSingleCount();
      this.getMutiplyCount();
      this.getTrueOrFalseCount();
      this.getAllCount();//获取总的题量数
      console.log("---------------");
    },
    methods:{
      getSingleCount(){
        single_count().then(res => {
          // console.log("打印单选题题数：",res.total);
          this.mychartData.singleItem_count = null;
          let singledata = res.total;
          this.mychartData.singleItem_count = singledata;
          console.log("此时的数据1******", this.mychartData.singleItem_count);

          }
        )
      },
      getMutiplyCount(){
        multiply_questionsCount().then(res =>{
          // console.log("打印多选题题数返回值",res.total);
          this.mychartData.mutiplyItems_count = null;
         this.mychartData.mutiplyItems_count = res.total;
          console.log("此时的数据2*****",this.mychartData.mutiplyItems_count);
        })
      },
     getTrueOrFalseCount(){
        tf_count().then(res =>{
          //赋值之前先清空
          this.mychartData.trueorfalseItem_count = null;
          this.mychartData.trueorfalseItem_count = res.total;
          console.log("此时的数据3*****",this.mychartData.trueorfalseItem_count);
        })
     },
      getAllCount(){
        this.mychartData.allCount = this.mychartData.singleItem_count + this.mychartData.multiplyItems_count + this.mychartData.trueorfalseItem_count;
        console.log("总题量",this.mychartData.allCount);
      }
    }
  }
</script>

<style>
  .home{
    height: 100%;
    width: 100%;
  }
  .title {
    color: #1c84c6;
    font-size: 60px;
    text-align: center;
    margin-top: 80px;
  }
  .chart{
    height: 500px;
    width: 100%;
    margin-top: 60px;
    /*border: #7a6df0 solid 5px;*/
  }
  .chart1{
    float: left;
    width: 40%;
    height: 450px;
    margin-left: 5%;
    margin-right: 5%;
    text-align: left;
    /*color: #1c84c6;*/
    font-size: 25px;
    border: #7a6df0 dotted 5px;
    margin-bottom: 10px;
  }
  .chart2{
    float: left;
    width: 48%;
    height: 450px;
    margin-left: 2px;
    border: 3px solid #7a6df0;
    text-align: left;
    /*color: #1c84c6;*/
    font-size: 25px;
    margin-bottom: 10px;
  }

</style>
