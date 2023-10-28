<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-qiuhe">
          <svg-icon icon-class="qiuhe" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            总题量
          </div>
          <count-to :start-val="0" :end-val="this.mylistDatas.allCount" :duration="6000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-danxuanti">
          <svg-icon icon-class="danxuanti" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            单选题
          </div>
          <count-to :start-val="0" :end-val="this.mylistDatas.singleItem_count" :duration="6000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-duoxuanti">
          <svg-icon icon-class="duoxuanti" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            多选题
          </div>
          <count-to :start-val="0" :end-val="this.mylistDatas.multiplyItems_count" :duration="6000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-panduan">
          <svg-icon icon-class="panduan" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            判断题
          </div>
          <count-to :start-val="0" :end-val="this.mylistDatas.trueorfalseItem_count" :duration="6000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'

export default {
  props: {
    mylistDatas:{
      type:Object,
      require:true
    }
  },
  // //添加监事属性
  // watch: {
  //   chartData: {
  //     deep: true,
  //     handler(val) {
  //       //this.setOptions(val)
  //       this.initChart()
  //     }
  //   }
  // },
  // data(){
  //   return{
  //     singleOption:this.mylistDatas.singleItem_count,
  //     multiplyOptions:this.mylistDatas.multiplyItems_count,
  //     tfOption:this.mylistDatas.trueorfalseItem_count
  //   }
  // },
  components: {
    CountTo
  },
  methods: {
//    initChart(){
//      let singleOptiontemp = this.mylistDatas.singleItem_count;
//      this.singleOption = singleOptiontemp;
//      let multiplyOptiontemp = this.mylistDatas.multiplyItems_count;
//      this.multiplyOptions = multiplyOptiontemp;
//      let tfOptiontemp = this.mylistDatas.trueorfalseItem_count;
//      this.tfOption = tfOptiontemp;
// }
  }
}
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-qiuhe {
        background: #40c9c6;
      }

      .icon-danxuanti {
        background: #36a3f7;
      }

      .icon-duoxuanti {
        background: #f4516c;
      }

      .icon-panduan {
        background: #efe30c
      }
    }

    .icon-qiuhe {
      color: #40c9c6;
    }

    .icon-danxuanti {
      color: #36a3f7;
    }

    .icon-duoxuanti {
      color: #f4516c;
    }

    .icon-panduan {
      color: #efe30c
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
