<template>
  <div style="width:382px;height: calc(100vh - 502px);overflow-y: auto;overflow-x: hidden;margin:20px;">
    <div>
      <div class="rank-title">班级学习进度统计</div>
      <div>
        <div v-show="Cumulative_Rank.length>0" style="width: 382px;height:386px;">
          <div v-for="(item,index) in Cumulative_Rank"
               :key="index"
               style="width:350px;padding-left:10px;"
          >
            <el-row style="font-size:14px;color:#606266;">{{ index + 1 + '  ' + item.name }}</el-row>
            <el-row style="height:18px;">
              <el-col :span="20">
                <el-progress :percentage="item.percent" :stroke-width="10" color="#50c097"></el-progress>
              </el-col>
              <el-col :span="4">
                <span style="color:#50c097;font-weight:bold;margin-left:-35px;"
                >{{ item.value ? item.value : '' }}</span>
              </el-col>
            </el-row>
          </div>
        </div>
        <div v-show="Cumulative_Rank == null || Cumulative_Rank.length == 0"
             style="width: 382px;height:260px;line-height:260px;text-align:center;color:#909399;"
        >
          暂无数据
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    name:'Prosses',
    data() {
      return {
        // 排名的列表
        Cumulative_Rank: [
          { name: '张三', value: 100, percent: 100 },
          { name: '李四', value: 90, percent: 90 },
          { name: '王五', value: 80, percent: 80 },
          { name: '小刘', value: 70, percent: 70 },
          { name: '嘎子', value: 60, percent: 60 },
          { name: '二狗', value: 50, percent: 50 },
          { name: '铁蛋', value: 40, percent: 40 },
          { name: '大牛', value: 0, percent: 0 }
        ]
      }
    },
    methods: {},
    mounted() {
    },
    created() {
    }
  }
</script>
<style lang="scss" scoped>
  .rank-title {
    font-size: 18px;
    color: #FFFFFF;
    font-weight: bold;
    margin-top: 10px;
    margin-bottom: 10px;
  }

  ::v-deep .el-progress__text {
    display: none;
  }
  ::v-deep .el-progress-bar__outer {
    background-color: rgba(235, 238, 245, 0);
  }
  .progress-txt {
    .progress-title {
      width: 68px;
      display: inline-block;
      position: relative;
    }
    .progress-percen {
      display: inline-block;
      position: relative;
      left: 0px;
    }
  }
  .progress-custom {
    position: relative;
    // width: 364px;
    width: 100%;
    height: 20px;

    .progress-content {
      position: absolute;
      width: 100%;
      height: 12px;
      background-color: #ebeef5;
      margin: 1px;
      border-radius: 6px;
    }

    .progress-value {
      position: absolute;
      width: 20%;
      height: 12px;
      background-color: #FFFFFF;
      margin: 1px;
      border-radius: 6px;
      left: 0px; // 经测试，left 最多到70% 就已经最右边了
    }
  }
</style>
