<template>
  <div :class="className" :style="{height:height,width:width}" id="bar" />
</template>

<script>
import * as echarts from 'echarts';
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

const animationDuration = 4000

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
      default: '300px'
    }
  },
  data() {
    return {
      chart: null
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

      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'line' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
          top: 10,
          left: '2%',
          right: '2%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [{
          type: 'category',
          data: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'],
          axisTick: {
            alignWithLabel: true
          }
        }],
        yAxis: [{
          type: 'value',
          axisTick: {
            show: false
          }
        }],
        series: [{
          name: '学习次数低于1',
          type: 'bar',
          stack: 'vistors',
          barWidth: '60%',
          data: [2, 33, 8, 15, 0, 15, 20],
          animationDuration,
          itemStyle: {
            normal: { color: '#8B0000' }
          }
          },
          {
          name: '学习次数介于2-50',
          type: 'bar',
          stack: 'vistors',
          barWidth: '60%',
          data: [55, 20, 50, 35, 48, 39, 36],
          animationDuration,
            itemStyle: {
              normal: { color: '#008B8B' }
            }
        }, {
          name: '学习次数大于50',
          type: 'bar',
          stack: 'vistors',
          barWidth: '60%',
          data: [3, 7, 2, 10, 12, 6, 4],
          animationDuration,
            itemStyle: {
              normal: { color: '#8968CD' }
            }
        }]
      })
    }
  }
}
</script>
<style>
  /*#bar{*/
  /*  border: 10px solid red;*/
  /*}*/
</style>
