<template>
  <div id="ranking-board" style="width:400px;height:300px">
    <div class="ranking-board-title">学习进度统计</div>
    <dv-scroll-ranking-board :config="config" style="textColor:#0000" />
  </div>
</template>

<script>
  export default {
    name: 'RankingBoard',
    data () {
      return {
        config: {
          data: [
            {
              name: '日常养护',
              value: 55
            },
            {
              name: '交通事故',
              value: 120
            },
            {
              name: '路面',
              value: 78
            },
            {
              name: '桥通',
              value: 66
            },
            {
              name: '计日工',
              value: 80
            },
            {
              name: '路基',
              value: 45
            },
            {
              name: '交安设施',
              value: 29
            },
            {
              name: '除雪',
              value: 29
            },
            {
              name: '绿化',
              value: 29
            }
          ],
          rowNum: 9
        }
      }
    }
  }
</script>

<style lang="less">
  #ranking-board {
    width: 20%;
    box-shadow: 0 0 3px blue;
    display: flex;
    flex-direction: column;
    background-color: #FFFFFF;
    border-top: 2px solid rgba(1, 153, 209, .5);
    box-sizing: border-box;
    padding: 0px 30px;
    .ranking-board-title {
      font-weight: bold;
      height: 50px;
      display: flex;
      align-items: center;
      font-size: 20px;
      color:#000000;
    }
    .dv-scroll-ranking-board {
      flex: 1;
    }
  }
</style>
