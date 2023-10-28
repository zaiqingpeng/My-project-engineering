<template>
  <div :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts';
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '60%'
    },
    chartData:{
      type:Object,
      require:true
    },
  },
  data() {
    return {
      chart: null,
    }
  },

  //添加监事属性
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        //this.setOptions(val)
        this.initChart()
      }
    }
  },
  mounted() {
    this.$nextTick(() => {   //本身属于一种优化策略
      this.initChart()
    });
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons');

      // console.log("*******piechart开始工作了********",this.chartData.singleItem_count,this.chartData.mutiplyItems_count,this.chartData.trueorfalseItem_count);
      this.chart.setOption({
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'      //这里是设置显示格式而已
        },
        legend: {
          // left: 'center',   //设置图例对齐方式
          // bottom: '25',     //设置图例之间的间隔
          data: ['90-100分', '80-89分', '70-79分','60-69分','0-59分'],      //设置图例的内容显示
          right: 50,
          bottom: 'center',
          orient: 'vertical',
          itemGap:8,
        },
        grid:{
          top:'2%',//距上边距
          left:'40%',//距离左边距
          right:'2%',//距离右边距
          bottom:'2%',//距离下边距

        },
        color: ['#ff3333', '#33ccff','#7B68EE', '#FFCC33', '#66FF33'],   //这里设置饼图的默认颜色显示先后顺序
        series: [
          {
            name: '人数',
            type: 'pie',
            radius: '90%',
            data: [
              { value:6, name: '90-100分' },
              { value: 15, name: '80-89分' },
              { value: 24, name: '70-79分' },
              { value:6, name: '60-69分' },
              { value: 2, name: '0-59分' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },
            animationEasing: 'cubicInOut',
            animationDuration: 2600
          }
        ]
      })
    }
  }
}
</script>
