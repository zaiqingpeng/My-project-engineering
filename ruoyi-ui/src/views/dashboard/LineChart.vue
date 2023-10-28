<template>
  <div :class="className" :style="{height:height,width:width}" />
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
      default: '400px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    // chartData: {
    //   type: Object,
    //   required: true
    // }
  },
  data() {
    return {
      chart: null,
      mydata:['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    }
  },
  watch: {
    chartData: {

      deep: true,
      handler(val) {
        this.setOptions(val)
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
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
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions(this.chartData)
    },
    setOptions({ expectedData, actualData } = {}) {
      // console.log('---------检查我的数字--------');
      // console.log(this.chartData);
      this.mydata = [];
      this.mydata  = this.chartData;

      this.chart.setOption({
        xAxis: {
          type:'category',
          data: ['1', '2', '3', '4', '5', '6', '7','8', '9', '10', '11', '12', '13', '14','15', '16', '17', '18', '19', '20', '21','22', '23', '24', '25', '26', '27', '28','29', '30'],
          boundaryGap: false,
          axisTick: {
            show: false
          }
        },
        grid: {
          left: 0,
          right: 18,
          bottom: '15%',
          top: 40,
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          padding: [10, 15]
        },
        yAxis: {
          axisTick: {
            show: false
          }
        },
        legend: {
          data: ['满分','平均得分'],
          height:'100%'
        },
        dataZoom: { // 放大和缩放
          type: 'inside'
        },
        series: [{
          name: '平均得分', itemStyle: {
            normal: {
              color: '#FF005A',
              lineStyle: {
                color: '#FF005A',
                width: 2
              }
            }
          },
          smooth: true,
          type: 'line',
          data: [5,5,4.8,5,5,5,4.8,4.7,5,5,4,4.6,3.8,4,8,7,6,8,8,5,4,4,3.9,3.8,4,4,4,3,4,0],
          animationDuration: 2800,
          animationEasing: 'quadraticInOut'
        },
          {
            name: '满分',
            smooth: true,   //折线和曲线的转换开关，true为曲线（默认为true）,false为曲线
            type: 'line',
            data: [5,5,5,5,5,5,5,5,5,5,5,5,5,5,8,8,8,8,8,8,4,4,4,4,4,4,4,4,4,4],
            animationDuration: 2800,
            animationEasing: 'linear',  //进入动画
            symbol:'rect'   //设置
          },
        ]
      })
    }
  }
}
</script>
