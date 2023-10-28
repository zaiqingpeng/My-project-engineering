<template>
  <div class="exam">
    <div class="main">
      <div class="mian-body">
        <!--        v-if 和 v-show 会根据接收 true/false 信息使得页面上元素达到显示或隐藏的效果   重点：true>>>显示；false>>>隐藏  -->
        <div class="main-side" v-if="!yingcang">
          <div class="title">
            <div class="title_border"></div>
            <div class="title_content">
              <el-row :gutter="20">
                <el-col :span="15">答题卡</el-col>
                <el-col :span="9">
                  <el-button icon="el-icon-caret-bottom" circle @click="hidden"></el-button>
                </el-col>
              </el-row>
            </div>
          </div>
          <div class="card-content-list">
            <div class="card-content">
              <div class="card-content-title">单选题(共{{ singleAllCount }}题，合计{{ singleAllScores }}分)</div>
              <div class="box-list">
                <div class="box normal-box question_cbox" v-for="(item,index) in singleList" :key="index">
                  <div class="checki" :class="{'checked':radio[index]}">{{ index + 1 }}</div>
                </div>
              </div>
              <!--多选题-->
              <div class="card-content-title">多选题(共{{ multiplyAllCount }}题，合计{{ multiplyAllScores }}分)</div>
              <div class="box-list">
                <div class="box normal-box question_cbox" v-for="(test,index) in multiplyList" :key="index">
                  <div v-if="test.choosed.length == '0'" class="checki">{{ index + 1 + singleList.length }}</div>
                  <div v-else class="checki checked">{{ index + 1 + singleList.length }}</div>
                </div>
              </div>
              <!--判断题 -->
              <div class="card-content-title">判断题(共{{ tfAllCount }}题，合计{{ tfAllScores }}分)</div>
              <div class="box-list">
                <div class="box normal-box question_cbox" v-for="(item,tf_index) in tfList" :key="tf_index">
                  <div class="checki" :class="{'checked':radio[tf_index + singleList.length + multiplyList.length]}"
                       v-show="!checkResult">{{ tf_index + 1 + singleList.length + multiplyList.length }}
                  </div>
                  <div class="checki checked"
                       :class="{'check-error':radio[tf_index + singleList.length + multiplyList.length]}"
                       v-show="checkResult">{{ tf_index + 1 + singleList.length + multiplyList.length }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 问题在这-->
        <div class="main-center" style="background: #FFFFFF">
          <!-- 这儿没问题-->
          <div class="body-wrapper">
            <div class="questions-title">单选题(共{{ singleAllCount }}题，合计{{ singleAllScores }}分)</div>
            <div class="questions-content">
              <div v-for="(item,i) in singleList" :key="i">
                <div class="exam-question">
                  <span class="question-index ellipsis">{{ i + 1 }}.</span>
                  {{ item.question }}
                </div>
                <div v-if="!checking">
                  <el-radio-group v-model="radio[i]">
                    <el-radio label="A">A. {{ item.optionA }}</el-radio>
                    <el-radio label="B">B. {{ item.optionB }}</el-radio>
                    <el-radio label="C">C. {{ item.optionC }}</el-radio>
                    <el-radio label="D">D. {{ item.optionD }}</el-radio>
                  </el-radio-group>
                </div>
              </div>
            </div>
            <div class="questions-title">多选题(共{{ multiplyAllCount }}题，合计{{ multiplyAllScores }}分)</div>
            <div class="questions-content">
              <div v-for="(test,index) in multiplyList" :key="index">
                <div class="exam-question">
                  <span class="question-index ellipsis"
                        style="line-height: 40px">{{ 1 + index + singleList.length }}.</span>
                  <!--题目-->
                  {{ test.question }}
                </div>
                <div>
                  <el-checkbox-group v-model="test.choosed">
                    <p style="line-height: 25px">
                      <el-checkbox v-for="(option,index) in test.options" :label="index" :key="index">
                        {{ option }}
                      </el-checkbox>
                    </p>
                  </el-checkbox-group>
                </div>
              </div>
            </div>
            <div class="questions-title">判断题(共{{ tfAllCount }}题，合计{{ tfAllScores }}分)</div>
            <div class="questions-content">
              <div v-for="(item,i) in tfList" :key="i">
                <div class="exam-question">
                  <span class="question-index ellipsis">{{ multiplyList.length + singleList.length + 1 + i }}.</span>
                  {{ item.question }}
                </div>
                <div v-if="!checking">
                  <el-radio-group v-model="radio[multiplyList.length + singleList.length+i]">
                    <el-radio label="正确">正确{{ item.optionA }}</el-radio>
                    <el-radio label="错误">错误{{ item.optionB }}</el-radio>
                  </el-radio-group>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="main-right">
          <div class="nav">
            <ul v-show="!checkResult">
              <li class="menu-item">
                <div class="item-label">剩余时间</div>
                <div class="item-data">
                  {{ this.restTime }}
                </div>
              </li>
              <!--当前进度(OK)-->
              <li class="menu-item">
                <div class="item-label">当前进度</div>
                <div class="item-press">
                  <span>{{ radio.filter(v => v).length }}</span>
                  <span>{{ examinationData.length }}</span>
                </div>
              </li>
            </ul>
          </div>
          <div class="btn" v-show="!checkResult" @click="submit">提交</div>
          <div class="esc" @click="esc">退出</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {axios} from 'axios';
import {mapGetters} from 'vuex';
import {getInfo} from '../../../api/login'
import {SmartList} from '../../../api/paper/smart_info'
import { listStudent_info,listStudent_info1, getStudent_info, addStudent_info, updateStudent_info } from "@/api/student/student_info";

export default {
  name: "Exam",
  data() {
    return {
      //单选、判断、多选题最终得分
      FinallyScores:0,
      //存储试卷里的所有问题
      allQuestions:[],
      //单选+判断
      allScores:0,
      //获取学生账号
      student_account:'',
      // 表单参数
      form: {},
      // 总条数
      total: 0,
      // 遮罩层
      loading: true,
      // 学生基本信息表格数据
      student_infoList: [],
      //存储所有的正确答案
      allAnswer: [],
      checking: false,
      name: "宰清鹏",
      customColor: "#55b6da",
      checkResult: false, // 左侧栏、右侧栏、答题结果栏
      examinationData: "",
      singleAllCount: 1,
      singleAllScores: 1,
      examQuestionsList: [],
      BeginAndEndTime: [],
      multiplyAllCount: 1,
      multiplyAllScores: 1,
      tfAllCount: 1,
      tfAllScores: 1,
      radio: [], //单选与判断题答案数组,
      restTime: "", // 剩余时间
      timer: null,
      timeDiff: " ",
      singleList: [],
      multiplyList: [],
      tfList: [],
      singleLength: '',
      multiplyLength: '',
      yingcang: false,//默认为不隐藏
      getMultiplyScores: 0,
      indexAnswer: [], //存答案索引值，需要处理
      mulIndexList: []
    };
  },
  created() {
    this.getQuestions();
    //将学生基本信息先获取过来
    this.getList();
  },
  mounted() {
    // console.log("姓名；打印一下");
    this.getUserName();
    this.TimeCount();  //获取时间差
    //定时先暂停，任务完成后继续
    // 倒计时,每一秒执行一次
    if(this.timer)clearInterval(this.timer)
    this.timer = setInterval(this.getRestTime,1000)

  },
  updated() {
    console.log("更新多选题数据", this.multiplyList);
    this.TestScores();  //索引转换并计算得分

  },
  // 退出页面清除定时器
  beforeDestroy() {
    clearInterval(this.timer);
  },
  methods: {
    //计算考试得分
    TestScores() {
      //计算多选题得分
      this.mulIndexList = []  //每次刷新需清空，不然会堆叠
      for (let ma = 0; ma < this.multiplyList.length; ma++) {
        //将多选题的索引值转换为ABCD
        let indexAnswerItem = this.multiplyList[ma].answer.split('');    //结果是 ["A", "B", "C", "D"]
        if (indexAnswerItem.indexOf("A") != -1) {    //找到了A了
          let A_index = indexAnswerItem.indexOf("A");
          indexAnswerItem[A_index] = 0;
        }
        if (indexAnswerItem.indexOf("B") != -1) {   //找到了B了
          let B_index = indexAnswerItem.indexOf("B");
          indexAnswerItem[B_index] = 1;
        }
        if (indexAnswerItem.indexOf("C") != -1) {   //找到了C了
          let C_index = indexAnswerItem.indexOf("C");
          indexAnswerItem[C_index] = 2;
        }
        if (indexAnswerItem.indexOf("D") != -1) {   //找到了D了
          let D_index = indexAnswerItem.indexOf("D");
          indexAnswerItem[D_index] = 3;
        }
        this.mulIndexList.push(indexAnswerItem);
        //循环答题，比对答案
        this.getMultiplyScores = 0; //每次刷新清空，避免堆叠
        for (let d = 0; d < this.mulIndexList.length; d++) {
          if ((this.mulIndexList[d]).toString() == (this.multiplyList[d].choosed).toString()) {
            this.getMultiplyScores = Number(this.multiplyList[d].perScore) + this.getMultiplyScores; //答题得分
            console.log("答对了");
          }
        }
        console.log("多选题总得分", this.getMultiplyScores);
        this.FinallyScores  = Number(this.allScores) + Number(this.getMultiplyScores);  //单选、多选，判断
        console.log("*******彩灯***********",this.allScores,this.FinallyScores);
      }
    },
    //隐藏与收起答题卡面板
    hidden() {
      this.yingcang = !this.yingcang;
      console.log("是否隐藏0000000000000000000", this.yingcang);
    },
    //点击提交试卷后将学生答案与正确答案进行比对，计算分数的函数
    getTestScore(){
      // this.getQuestions();
      // 按下一次后禁用
      //  this.submitBan = true;
      console.log("打印查看是不是拉到了所有的单选、多选和判断++++++++++++++++++++++++++++++++++++++");
      console.log("打印查看所有的题目",this.singleList);
      console.log("打印查看所有的题目",this.multiplyList);
      console.log("打印查看所有的题目",this.tfList);

      console.log("打印查看所有的题目",this.singleList.length);
      console.log("打印查看所有的题目",this.multiplyList.length);
      console.log("打印查看所有的题目",this.tfList.length);
      //将所有的数组拼接到一起
      // this.allQuestions = this.allQuestions.concat(this.singleList);
      this.allQuestions = this.allQuestions.concat(this.singleList);
      // this.allQuestions = this.allQuestions.concat(this.multiplyList);
      // this.allQuestions = this.allQuestions.concat(this.tfList);
      //获得了所有的问题，存到一个数组里面
      console.log("3、查看所有问题集合到一起的数组",this.allQuestions);
      //拿到所有的答案
      for (let i = 0; i < this.allQuestions.length; i++) {
        // console.log("查看是不是真的进来了");
        this.allAnswer.push(this.allQuestions[i].answer);
      }
      //这里拿到了所有的答案
      console.log(this.allAnswer);
      console.log(this.allAnswer.length);

      //  下面处理学生选择的所有答案
      console.log("查看单选题和多选题的选择",this.radio);     // 单选头和多选题都存在这数组里面了，正确的从0开始的，但判断题需要进行修改
      console.log("查看所有的多选题答案",this.checkbox);
      //   现将单选题和判断题存到总的选项组里面去
      this.allStudentChoice = this.radio;
      //这两个用来将多选题的答案存在总的数组里面去
      let len_1 = this.singleList.length;
      let len_2 = this.singleList.length + this.multiplyList.length;
      //这个是所有题目的长度,用来处理后面没有选择的答案
      let len_3 = this.singleList.length + this.multiplyList.length + this.tfList.length;
      //将多选题的答案处理好以后往这里面扔
      for (let i = len_1; i < len_2; i++) {
        this.allStudentChoice[i] = "";
        //在这里处理checkbox哦！！！
        // this.allStudentChoice[i] = this.checkbox[i];
      }
      if(this.allStudentChoice.length < len_3){
        for (let i = this.allStudentChoice.length; i < len_3; i++) {
          this.allStudentChoice[i] = "";
        }
      }
      console.log("[[[[[[[[[[[[[[",this.allStudentChoice);

      //   在这里比对选的答案和正确答案是否一致
      for (let i = 0; i < len_3; i++) {
        if(this.allStudentChoice[i] == this.allAnswer[i]){
          this.allScores = this.allScores + 2;
          console.log("查看选择是哪一题:",i+1);
          console.log("学生选择的答案",this.allStudentChoice[i]);
          console.log("正确的答案",this.allAnswer[i]);
        }
      }
      console.log("查看最终得分是多少999：",this.allScores);
      this.FinallyScores  = this.allScores + this.getMultiplyScores;
      console.log("88888888888888888888888888",this.FinallyScores);

    },
    // 初始化获取时间
    getRestTime() {
      // 赋值转时分秒
      this.restTime = this.toHHmmss(this.timeDiff);
      console.log("返回时间拦截并展示一下(格式化之后)", this.restTime);
      // 所以不是大于0秒
      if (this.timeDiff >= 1000) {
        // 1000为一秒
        // 如果大于1秒
        this.timeDiff = this.timeDiff - 1000;
      } else {
        // 倒计时最后一秒将剩余时间修改
        this.restTime = "00:00:00";
        // 清除定时器
        clearInterval(this.timer)
        // 在这里编写考试交卷或自动取消订单的功能
        console.log("结束定时器!");
      }
    },
    //获取登录用户名
    getUserName() {
      getInfo().then(res => {
        console.log("当前用户获取的用户名是****************:", res.user.userName);
        this.student_account = res.user.userName;


      })
    },
    // 退出考试系统
    esc() {
      this.$router.push({
        path: "/"
      });
    },
    // 主动交卷
    submit() {
      this.$confirm("确定交卷吗?", "提示", {
        distinguishCancelAndClose: true,
        confirmButtonText: "交卷",
        cancelButtonText: "不交卷",
        type: "warning",
      })
      this.getTestScore();//彩灯
      this.submitScores(); //提交得分到数据库
      this.esc();   //退出
    },
    // 获取考试题
    getQuestions() {
      SmartList().then(res => {
        let data = res.rows;
        console.log("看看获取到没？", data);
        this.singleAllCount = 0;
        this.singleAllScores = 0;
        this.multiplyAllCount = 0;
        this.multiplyAllScores = 0;
        this.tfAllCount = 0;
        this.tfAllScores = 0;
        for (let i = 0; i < data.length; i++) {
          let smtf_item = {
            id: data[i].id,
            question: data[i].question,
            optionA: data[i].optionA,
            optionB: data[i].optionB,
            optionC: data[i].optionC,
            optionD: data[i].optionD,
            answer: data[i].answer,
            perScore: data[i].perScore
          }
          this.examQuestionsList.push(smtf_item);
          this.examinationData = this.examQuestionsList;
          // console.log("清洗并封装后的数据", this.examQuestionsList);
          if (data[i].questionType == 1) { //是单选题
            this.singleAllCount = this.singleAllCount + 1;
            this.singleAllScores = this.singleAllScores + Number(data[i].perScore);
            let single = {
              id: data[i].id,
              question: data[i].question,
              optionA: data[i].optionA,
              optionB: data[i].optionB,
              optionC: data[i].optionC,
              optionD: data[i].optionD,
              answer: data[i].answer,
              perScore: data[i].perScore
            }
            this.singleList.push(single);
          }
          this.singleLength = this.singleList.length;
          // console.log("打印单选题长度进行校验,///25///", this.singleLength);

          //判断是否为多选题
          if (data[i].questionType == 2) {
            this.multiplyAllCount = this.multiplyAllCount + 1;
            this.multiplyAllScores = this.multiplyAllScores + Number(data[i].perScore);
            let multiply = {
              id: data[i].id,
              question: data[i].question,
              options: [data[i].optionA, data[i].optionB, data[i].optionC, data[i].optionD],
              answer: data[i].answer,
              perScore: data[i].perScore,
              choosed: []
            }
            this.multiplyList.push(multiply);
          }
          //获取单选题加多选题之后的序号值
          this.multiplyLength = this.multiplyList.length + this.singleLength;
          if (data[i].questionType == 3) {
            this.tfAllCount = this.tfAllCount + 1;
            this.tfAllScores = this.tfAllScores + Number(data[i].perScore);
            let tf = {
              id: data[i].id,
              question: data[i].question,
              optionA: data[i].optionA,
              optionB: data[i].optionB,
              answer: data[i].answer,
              perScore: data[i].perScore
            }
            this.tfList.push(tf);
          }

        }
      })
    },
    // 倒计时处理
    madeTime(serverTime1, begin1, duration1) {
      var serverTime = new Date(serverTime1); // 系统时间
      var duration = duration1; //考试时间
      if (begin1 != null) {
        var begin = new Date(begin1); //开始时间
        var residue = begin.getTime() + duration * 1000 - serverTime.getTime(); // 倒计时
      } else {
        // eslint-disable-next-line no-redeclare
        var residue = duration * 1000 - serverTime.getTime(); // 倒计时
      }
      return residue;
    },
    // 时间戳时分秒
    toHHmmss(data) {
      let hours = parseInt((data % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      let minutes = parseInt((data % (1000 * 60 * 60)) / (1000 * 60));
      let seconds = (data % (1000 * 60)) / 1000;
      //时
      let hh = hours < 10 ? "0" + hours : hours;
      //分
      let mm = minutes < 10 ? "0" + minutes : minutes;
      //秒
      let ss = seconds < 10 ? "0" + seconds : seconds;
      //组合时间
      let testTime = hh + ":" + mm + ":" + ss;
      return testTime;
    },
    //时间的处理（宰）
    TimeCount() {
      SmartList().then(res => {
        // console.log("处理时间了", res.rows);
        // 将指定日期转换为时间戳
        let begintimeStr = res.rows[0].beginTime;
        let begintime = new Date(begintimeStr);
        let BT = begintime.getTime();  // 1512734730000
        // 将指定日期转换为时间戳
        let endtimeStr = res.rows[0].endTime;
        let endtime = new Date(endtimeStr);
        let ET = endtime.getTime();  // 1512734730000
        let time_item = {
          beginTime: BT,
          endTime: ET
        }
        this.BeginAndEndTime.push(time_item);
        //时间差=结束时间-开始时间
        this.timeDiff = parseInt((ET - BT))   //getTime默认是毫秒，除1000转换为秒（不用转）
        // console.log("时间戳差值", this.timeDiff);
        this.getRestTime();
      })
    },
    //以下是学生信息获取与处理
    /** 查询学生基本信息列表 */
    getList() {
      this.loading = true;
      listStudent_info1().then(response => {
        console.log("////////////打印学生数据/////////////",response);
        this.student_infoList = response.rows;
        this.total = response.total;
        this.loading = false;
        // console.log("先拿第一个数据来试试看");
        //遍历学生基本信息，将当前用户与数据库的学生账号匹配，并获取该学生的所有信息，并将其分数修改为考试得分
        for(let s = 0;s < this.student_infoList.length;s++){
          if(this.student_account == this.student_infoList[s].learningId){
               this.form = this.student_infoList[s];   //如果是同一账户 ，将此账户的学生信息全部赋值给表单，等待分数修改（这里只是多选题的得分哟）
              console.log("数据库比较有吗？",this.form);
          }
        }
        // this.form = response.rows[0];
      });
    },
    /** 提交按钮获取得分 */
    submitScores() {
            this.form.studentScore = this.FinallyScores;  //将得分赋值给当前账号的学生成绩，等待传递给学生信息页
            updateStudent_info(this.form).then(response => {
              this.getList();
            });
    }
  },
  beforeRouteEnter(to, from, next) {
    next(() => {
      // 改变html背景
      document.querySelector("html").style.cssText = `
        background: #ecf1f6;
      `;
    });
  },
  beforeRouteLeave(to, from, next) {
    // 消除html背景
    document.querySelector("html").style.cssText = `background: #fff;`;
    // 中途退出提示
    this.$confirm("确定中途退出吗, 是否继续?", "提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning"
    })
      .then(() => {
        next();
      })
      .catch();
  },
};
</script>
<style scoped lang="less">

/deep/ .el-progress__text {
  display: none;
}

/deep/ .el-select .el-input__inner:focus {
  border-color: #55b6da;
}

/deep/ .el-select {
  border: none;
}

/deep/ .el-select-dropdown__item.selected {
  color: #55b6da !important;
  font-weight: bold;
}

/deep/ .el-radio__input.is-checked + .el-radio__label {
  color: #55b6da;
}

/deep/ .el-radio__input.is-checked .el-radio__inner {
  border-color: #55b6da;
  background: #55b6da;
}

/deep/ .el-radio__inner:hover {
  border-color: #55b6da;
}

/deep/ .el-radio {
  display: block;
  margin: 20px 0;
}

.inner {
  padding: 0px 90px;
}

.exam {
  background: #ecf1f6;
  height: 100%;
  min-height: 100%;
}

.main {
  .header-wrapper {
    height: 80px;
    width: 100%;
    line-height: 80px;
    background: #FFFFFF;
    box-shadow: 0 2px 4px 0 rgba(153, 153, 153, 0.1);
    position: fixed;
    margin-left: 120px;
    top: 0;
    left: 0;

    .left {
      display: flex;
      align-items: center;
      font-size: 18px;

      .logo {
        width: 150px;
        margin-right: 120px;

        img {
          width: 50%;
        }
      }

      .name_title { //设置试卷标题的名称，例如：电解铝知识考试
        margin-right: 55px;
        width: 150px;
        height: 65px;
        line-height: 38px;
        text-align: center;
        //font-weight: bold;   //font_weight表示加粗字体
      }
    }

    .right {
      width: 400px;

      div {
        float: right;
        font-size: 16px;

      }

      .esc {
        width: 120px;
        height: 38px;
        line-height: 38px;
        margin: 21px 0;
        color: #fff;
        background: #55b6da;
        border-radius: 30px;
        text-align: center;
        cursor: pointer;
        user-select: none;

        &:hover {
          filter: brightness(80%);
        }

        &:active {
          filter: brightness(60%);
        }
      }

      .name {
        margin-right: 55px;
        font-weight: bold;
      }
    }
  }

  .mian-body {
    .main-side {
      display: inline-block;
      height: calc(100% - 140px);
      position: fixed;
      top: 130px;
      width: 240px;
      left: 90px;
      margin-left: 120px;
      background: #fff;
      box-shadow: 0 1px 4px 0 rgba(58, 62, 81, 0.1);

      .title {
        position: relative;
        text-align: left;
        margin: 23px 0px 0px 12px;

        .title_border {
          display: inline-block;
          width: 4px;
          height: 26px;
          background: #33394b;
          border-radius: 15px;
          position: absolute;
          top: 0;
          bottom: 0;
          margin: auto;
        }

        .title_content {
          margin-left: 14px;
          font-size: 18px;
          font-weight: 600;
          color: #27274a;
        }
      }

      .card-content-list {
        height: calc(100% - 38px);
        overflow: auto;

        .card-content {
          padding: 0 12px 0 12px;
          position: relative;

          .card-content-title {
            font-size: 14px;
            color: #27274a;
            font-weight: 600;
            padding-bottom: 40px; //12px
            padding-top: 20px;
          }

          .box-list {
            padding-bottom: 0;
            position: relative;
            left: -5px;
            font-size: 0;
            margin-right: -15px;

            .box {
              height: auto;
              line-height: unset;
              position: relative;
              margin-bottom: 15px;
              font-size: 14px;
              width: 35px;
              margin-top: unset;
              margin-right: unset;
              display: inline-block;

              .checki {
                border: 1px solid #dcdfe6;
                color: #dcdfe6;
                width: 27px;
                height: 27px;
                text-align: center;
                display: inline-block;
                line-height: 27px;
                background: #fff;
                border-radius: 50%;
                cursor: pointer;
              }

              .checked {
                color: #fff;
                background: #55b6da;
              }

              .check-error {
                color: #fff;
                background: #ec6941;
              }
            }
          }
        }
      }
    }

    .main-center {
      //这部分是调整中间那部分的CSS的，试卷面板
      //margin:10px 5px 15px 20px;上，右，下，左
      margin: 50px 230px 0px 270px;
      height: 100%;

      .body-wrapper {
        color: #27274a;
        width: 100%;
        background: #fff;
        border: 1px solid #dedede;
        border-radius: 4px;

        .questions-title { //单选题统计面板
          font-size: 18px;
          line-height: 25px;
          padding: 18px 20px;
          background: #fafafa;
          border-bottom: 1px solid #dedede;
        }

        .questions-content {
          height: 100%;
          padding-left: 30px;
          padding-right: 75px;
        }

        .question-content { //试题面板
          border-bottom: 1px solid #dedede;
          padding: 30px 0;
          position: relative;

          &:last-child {
            border: none;
          }

          .exam-question { //题目
            font-size: 16px;
            line-height: 22px;
            margin-bottom: 10px;
            padding-left: 20px;
            position: relative;

            .question-index { //题目索引值
              color: #55b6da;
              position: absolute;
              left: -25px;
              top: 0;
              display: inline-block;
              width: 40px;
              line-height: 22px;
              text-align: right;
            }
          }

          .analysis {
            overflow: auto;
            background: rgba(222, 222, 222, 0.2);
            border-radius: 4px;
            padding: 15px 20px;
            line-height: 24px;
            margin-top: 10px;
            position: relative;

            .question-icon-wrapper {
              position: absolute;
              right: 10px;
              top: 14px;

              .sign {
                width: 48px;
                height: 28px;
                position: absolute;
                color: #fff;
                top: 10px;
                right: 0;
                font-size: 14px;
                border-radius: 2px;
                line-height: 28px;
                text-align: center;
                font-style: normal;
              }

              .icon-error {
                background: #ec6941;
              }

              .icon-right {
                background: #55b6da;
              }
            }

            .analysis-row {
              font-size: 14px;
              margin-top: 10px;
              min-height: 24px;
              padding-left: 80px;
              padding-right: 60px;
              position: relative;

              .analysis-title {
                position: absolute;
                width: 80px;
                left: 0;
                top: 0;
              }

              .question-analysis {
                text-align: justify;
              }

              .error {
                color: #f72a3a;
                font-weight: bold;
              }
            }
          }
        }
      }
    }

    .main-right {
      right: 90px;
      bottom: 20px;
      position: fixed;
      top: 120px;
      width: 120px;

      .nav {
        color: #27274a;
        line-height: 20px;
        padding: 0 10px;
        background: #FFFFFF;
        box-shadow: 0 1px 4px 0 rgba(0, 0, 0, 0.1);
        border-radius: 4px;
        margin-bottom: 10px;
        text-align: center;
        width: 180px;

        .menu-item {
          padding: 14px 0;
          border-bottom: 1px solid #dedede;

        }

        .menu-item:last-child {
          border: none;
        }

        .pass {
          color: #55b6da;
        }

        .item-result,
        .unpass {
          color: rgb(236, 105, 65);
        }

        .item-label {
          margin-bottom: 6px;
          width: 80px;
        }

        .item-data {
          font-size: 18px;
          line-height: 22px;
          color: #ff0000;
          font-weight: 400;
          width: 80px;
        }

        .item-press {
          line-height: 22px;
          margin-bottom: 7px;
          color: #27274a;
          font-size: 18px;
          width: 80px;

          & span:nth-child(1)::after {
            content: "/";
            margin: 0 10px;
          }
        }
      }

      .btn {
        bottom: -10px;
        position: absolute;
        width: 100%;
        margin-bottom: 10px;
        cursor: pointer;
        text-align: center;
        height: 38px;
        line-height: 38px;
        border-radius: 20px;
        color: #fff;
        background: #33394b;
        user-select: none;

        &:hover {
          filter: brightness(120%);
        }

        &:active {
          filter: brightness(60%);
        }
      }

      .esc {
        width: 100%;
        height: 38px;
        line-height: 38px;
        margin: 21px 0;
        color: #fff;
        background: #55b6da;
        border-radius: 30px;
        text-align: center;
        cursor: pointer;
        bottom: 40px;
        position: absolute;
        user-select: none;

        &:hover {
          filter: brightness(80%);
        }

        &:active {
          filter: brightness(60%);
        }
      }
    }
  }
}
</style>



