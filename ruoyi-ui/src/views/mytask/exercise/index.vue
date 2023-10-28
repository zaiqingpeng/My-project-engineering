<template>
  <div class="app-container">
    <el-container>
      <el-header>欢迎进入数字电解槽练习系统</el-header>
      <el-container>
        <el-main>
          <el-row>
            <el-col :span="24"><div style="text-align: left">{{question_i}}、{{question_stem}}</div></el-col>
          </el-row>
          <br>
          <el-row>
            <el-col :span="24"><div style="text-align: left">
              <!--单选框-->
              <el-radio name="single_button" v-model="single_radio" label="A" v-show="ui_state.single_ui" :disabled="button_state">A、{{options.optionA}}</el-radio>
              <!--多选框-->
              <el-checkbox label="A"  v-model="multiply_checkList" v-show="ui_state.multiply_ui" :disabled="button_state">A、{{options.optionA}}</el-checkbox>
              <!--判断题-->
              <el-radio v-model="tf_radio" label="0" v-show="ui_state.tf_ui" :disabled="button_state">正确</el-radio>
            </div></el-col>
          </el-row>
          <br>
          <el-row>
            <el-col :span="24"><div style="text-align: left">
              <!--单选框-->
              <el-radio name="single_button" v-model="single_radio" label="B" v-show="ui_state.single_ui" :disabled="button_state">B、{{options.optionB}}</el-radio>
              <!--多选框-->
              <el-checkbox label="B" v-model="multiply_checkList" v-show="ui_state.multiply_ui" :disabled="button_state">B、{{options.optionB}}</el-checkbox>
              <!--判断题-->
              <el-radio v-model="tf_radio" label="1" v-show="ui_state.tf_ui" :disabled="button_state">错误</el-radio>
            </div></el-col>
          </el-row>
          <br>
          <el-row>
            <el-col :span="24"><div style="text-align: left">
              <!--单选框-->
              <el-radio name="single_button" v-model="single_radio" label="C" v-show="ui_state.single_ui" :disabled="button_state">C、{{options.optionC}}</el-radio>
              <!--多选框-->
              <el-checkbox label="C" v-model="multiply_checkList" v-show="ui_state.multiply_ui" :disabled="button_state">C、{{options.optionC}}</el-checkbox>
            </div></el-col>
          </el-row>
          <br>
          <el-row>
            <el-col :span="24"><div style="text-align: left">
              <!--单选框-->
              <el-radio name="single_button" v-model="single_radio" label="D" v-show="ui_state.single_ui" :disabled="button_state">D、{{options.optionD}}</el-radio>
              <!--多选框-->
              <el-checkbox label="D" v-model="multiply_checkList" v-show="ui_state.multiply_ui" :disabled="button_state">D、{{options.optionD}}</el-checkbox>
            </div></el-col>
          </el-row>
          <br>
<!--          <el-row>-->
<!--            <el-col :span="24">-->
<!--              <div style="text-align: left">-->
<!--                <el-col :span="24"><div style="text-align: left">回答错误！</div></el-col>-->
<!--              </div>-->
<!--            </el-col>-->
<!--          </el-row>-->
          <el-row>
            <el-col :span="24">
              <div style="text-align: center">
                <el-button type="primary" style="margin: auto;width: 300px;" @click="look_answer">查看答案</el-button>
              </div>
            </el-col>
          </el-row>
          <div style="border-color: #3A71A8;border-style: dotted;" v-show=answer_show>
            <el-row>
              <el-col :span="24"><div style="text-align: left">结果：{{answer_state}}</div></el-col>
            </el-row>
            <el-row>
              <el-col :span="24"><div style="text-align: left">答案：{{answer}}</div></el-col>
            </el-row>
            <el-row>
<!--              <el-col :span="24"><div style="text-align: left">解析：{{description}}</div></el-col>-->
              <el-col :span="24"><div style="text-align: left">解析：暂无解析</div></el-col>
            </el-row>
          </div>
        </el-main>
      </el-container>
      <el-footer>
        <el-button-group>
          <el-button type="primary" icon="el-icon-arrow-left" @click="get_last_question">上一题</el-button>
          <el-button type="primary" @click="get_next_question">下一题<i class="el-icon-arrow-right el-icon--right"></i></el-button>
        </el-button-group>
      </el-footer>
    </el-container>

  </div>
</template>

<script>
// <!--  导入单选题 多选题 判断题的接口-->
import {single_count} from "../../../api/questions/single_questions";
import {multiply_questionsCount} from "../../../api/questions/multiply_questions";
import {tf_count} from "../../../api/questions/tf_questions";

export default {
  name: "Exercise",
  data() {
    return {
      // 按钮禁用
      button_state:false,
      //绑定的数据
      multiply_checkList: [],
      single_radio: '',
      tf_radio:'',
      //用哪一组UI
      ui_state:{
        single_ui:true,
        multiply_ui:false,
        tf_ui:false,
      },
      options:{
        optionA:"",
        optionB:"",
        optionC:"",
        optionD:""
      },
      //题目序号
      question_i:1,
      //题号
      question_count:0,
      //题干部分
      question_stem:"",
      // 是否显示答案,默认不显示
      answer_show:false,
      //答案
      answer:"",
      //回答是否正确
      answer_state:"",
      //解析
      description:"",
      //用来存储所有的题
      all_questions:[],
    //  存储单选题的问题
      single_question:[],
    //  存储多选题的问题
      multiple_question:[],
    //  存储判断题的问题
      tf_questions:[],
    //  处理选项是否禁用
      is_on:{
        single_option_is_on:false,
        multiply_option_is_on:false,
        tf_option_is_on:false,
      },
    }
  },
  //这部分是开始时就拉取数据，页面一开始就拉取了
  created() {
    this.combine_all_questions();
    // this.getSingleQuestion();
    // this.getMultipleQuestion();
  },
  methods: {
    //查看答案并判断用户是否答对
    look_answer(){
      console.log(this.answer_show);
      this.answer_show = true;
      console.log(this.answer_show);
      console.log(this.all_questions);
      if(this.all_questions[this.question_count].question_type == "单选题"){
        // 判断用户选择的答案与正确答案是否一致
        console.log("查看选择的是哪个答案",this.single_radio);
        console.log("查看以下正确选项吧!",this.all_questions[this.question_count].answer);
        if(this.single_radio == this.all_questions[this.question_count].answer){
          console.log("回答正确");
          this.answer_state = "回答正确";
        //  修改选项的样式
        //   let selection = document.getElementsByName("single_button");
        //   console.log(selection);

        }
        else {
          console.log("回答错误");
          this.answer_state = "回答错误";
        }
      //  查看答案以后将按钮禁用
        this.button_state = true;
      }
      else if (this.all_questions[this.question_count].question_type == "多选题"){
        // 判断用户选择的答案与正确答案是否一致
        console.log("查看选择的是哪个答案",this.multiply_checkList);
        console.log("查看以下正确选项吧!",this.all_questions[this.question_count].mulanswer);
        let pause_multiply_answer = "";
        for (let i = 0; i < this.multiply_checkList.length; i++) {
          pause_multiply_answer = pause_multiply_answer + this.multiply_checkList[i];
        }
        console.log("打印查看多选题选择的选项组成的数组",pause_multiply_answer);
        if(pause_multiply_answer==this.all_questions[this.question_count].mulanswer){
          console.log("回答正确");
          this.answer_state = "回答正确";

        }
        else {
          console.log("回答错误");
          this.answer_state = "回答错误";
        }
        //  查看答案以后将按钮禁用
        this.button_state = true;
      }
      else if(this.all_questions[this.question_count].question_type == "判断题"){
        console.log("查看选择的是哪个答案",this.tf_radio);
        console.log("查看以下正确选项吧!",this.all_questions[this.question_count].answer);
        if(this.tf_radio == this.all_questions[this.question_count].answer){
          console.log("回答正确");
          this.answer_state = "回答正确";

        }
        else {
          console.log("回答错误");
          this.answer_state = "回答错误";
        }
        //  查看答案以后将按钮禁用
        this.button_state = true;
      }
    },
    //获取单选题题库里的题目
    getSingleQuestion(){
      single_count().then(res=>{
        for (let i = 0; i < res.rows.length; i++) {
          let pause_single_question = {
            question:res.rows[i].question,
            optionA:res.rows[i].optionA,
            optionB:res.rows[i].optionB,
            optionC:res.rows[i].optionC,
            optionD:res.rows[i].optionD,
            answer:res.rows[i].answer,
            question_type:"单选题",
            describe:"xxxxxxxxxxxxx",
          };
          this.single_question.push(pause_single_question);
        }
        // console.log("看看单选题数组里面有什么东西",this.single_question);
        this.all_questions = [].concat(this.single_question);
        this.getMultipleQuestion();
      })
    },
  // 获取多选题题库里的题目
    getMultipleQuestion(){
      multiply_questionsCount().then(res=>{
        console.log(res.rows.length);
        for (let i = 0; i <res.rows.length ; i++) {
          let pause_multiply_question={
            question:res.rows[i].question,
            optionA:res.rows[i].questionA,
            optionB:res.rows[i].questionB,
            optionC:res.rows[i].questionC,
            optionD:res.rows[i].questionD,
            mulanswer:res.rows[i].mulAnswer,
            question_type:"多选题",
            describe:"ggggggggggggggggggggg",
          };
          this.multiple_question.push(pause_multiply_question);
         // console.log("--------------------",this.multiple_question);
        }
        // console.log("查看多选题数组里面有什么东西",this.multiple_question);
        this.all_questions = this.all_questions.concat(this.multiple_question);
        this.getTfQuestion();
      })
    },
    // 获取判断题题库里的题目
    getTfQuestion(){
      // console.log("看单选题数组里面定义了什么",pause_tf_question);
      tf_count().then(res=>{
        for (let i = 0; i < res.rows.length; i++) {
          let pause_tf_question={
            question:res.rows[i].question,
            answer:res.rows[i].answer,
            question_type:"判断题",
            describe:"",
          };
          this.tf_questions.push(pause_tf_question);
        }
        // console.log("查看获得的判断题数组",this.tf_questions);
        this.all_questions = this.all_questions.concat(this.tf_questions);
        //在这里获取到了所有的题目
        console.log("获取到所有的题目",this.all_questions);
        console.log(this.all_questions.length);
      //  在这里初始化题目
        this.question_stem = this.all_questions[0].question;
        this.options.optionA = this.all_questions[0].optionA;
        this.options.optionB = this.all_questions[0].optionB;
        this.options.optionC = this.all_questions[0].optionC;
        this.options.optionD = this.all_questions[0].optionD;
        this.answer = this.all_questions[0].answer;
        this.description = this.all_questions[0].remark;
      })
    },
  //  将所有的题目全部放到一个数组里
    combine_all_questions(){
      this.getSingleQuestion();
    },

  //  上一题
    get_last_question(){
      //  查看答案以后将按钮解禁用
      this.button_state = false;
      console.log("上一题-----------");
      console.log("打印选项里是哪个",this.single_radio);
      if(this.question_count>0){
        console.log("第一次打印次数",this.question_count);
        //点击一次上一题就加一次
        --this.question_count;
        // 题号减一
        --this.question_i;
        this.question_stem = this.all_questions[this.question_count].question;
        this.options.optionA = this.all_questions[this.question_count].optionA;
        this.options.optionB = this.all_questions[this.question_count].optionB;
        this.options.optionC = this.all_questions[this.question_count].optionC;
        this.options.optionD = this.all_questions[this.question_count].optionD;
        this.description = this.all_questions[this.question_count].remark;
        //收起解析板
        this.answer_show = false;
        //  打开相应的UI
        if(this.all_questions[this.question_count].question_type == "单选题"){
          // 将下一题的选项置位空
          this.single_radio = "";
          // 设置答案
          this.answer = this.all_questions[this.question_count].answer;
          // 打开相应的UI
          this.ui_state.multiply_ui = false;
          this.ui_state.single_ui = true;
          this.ui_state.tf_ui = false;
          // 判断用户选择的答案与正确答案是否一致
          // console.log("查看选择的是哪个答案",this.single_radio);
        }
        else if (this.all_questions[this.question_count].question_type == "多选题"){
          //将选项置为空
          this.multiply_checkList = [];
          // 设置答案
          this.answer = this.all_questions[this.question_count].mulanswer;
          // 打开相应的UI
          this.ui_state.multiply_ui = true;
          this.ui_state.single_ui = false;
          this.ui_state.tf_ui = false;
        }
        else if(this.all_questions[this.question_count].question_type == "判断题"){
          //将选项设置为空
          this.tf_radio = "";
          // 判断答案是正确还是错误
          if(this.all_questions[this.question_count].answer=="0"){
            this.answer = "正确";
          }
          else if(this.all_questions[this.question_count].answer=="1"){
            this.answer = "错误";
          }
          // 打开相应的UI
          this.ui_state.multiply_ui = false;
          this.ui_state.single_ui = false;
          this.ui_state.tf_ui = true;
        }
      }
      else {
        console.log("已经是第一题了，无法再上一题了！");
      }
    },

    //  下一题
    get_next_question(){
      //  查看答案以后将按钮解禁用
      this.button_state = false;
      console.log("下一题-----------");
      // 打印题号
      console.log("打印选项里是哪个",this.single_radio);
      if(this.question_count < this.all_questions.length){
        console.log("第一次打印次数",this.question_count);
        //点击一次上一题就加一次
        ++this.question_count;
        // 题号加一
        ++this.question_i;
        this.question_stem = this.all_questions[this.question_count].question;
        this.options.optionA = this.all_questions[this.question_count].optionA;
        this.options.optionB = this.all_questions[this.question_count].optionB;
        this.options.optionC = this.all_questions[this.question_count].optionC;
        this.options.optionD = this.all_questions[this.question_count].optionD;
        this.answer = this.all_questions[this.question_count].answer;
        this.description = this.all_questions[this.question_count].remark;
        // 将下一题的选项置位空
        this.single_radio = "";
        //收起解析板
        this.answer_show = false;
        //  打开相应的UI
        if(this.all_questions[this.question_count].question_type == "单选题"){
          // 将下一题的选项置位空
          this.single_radio = "";
          // 设置答案
          this.answer = this.all_questions[this.question_count].answer;
          // 打开相应的UI
          this.ui_state.multiply_ui = false;
          this.ui_state.single_ui = true;
          this.ui_state.tf_ui = false;
          // 判断用户选择的答案与正确答案是否一致
          // console.log("查看选择的是哪个答案",this.single_radio);
        }
        else if (this.all_questions[this.question_count].question_type == "多选题"){
          //将选项置为空
          this.multiply_checkList = [];
          // 设置答案
          this.answer = this.all_questions[this.question_count].mulanswer;
          // 打开相应的UI
          this.ui_state.multiply_ui = true;
          this.ui_state.single_ui = false;
          this.ui_state.tf_ui = false;
        }
        else if(this.all_questions[this.question_count].question_type == "判断题"){
          //将选项设置为空
          this.tf_radio = "";
          // 判断答案是正确还是错误
          if(this.all_questions[this.question_count].answer=="0"){
            this.answer = "正确";
          }
          else if(this.all_questions[this.question_count].answer=="1"){
            this.answer = "错误";
          }
          // 打开相应的UI
          this.ui_state.multiply_ui = false;
          this.ui_state.single_ui = false;
          this.ui_state.tf_ui = true;
        }
      }
      else {
        console.log("已经是最后一题了，无法再下一题了");
      }
    }
  }
};
</script>
<style>
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
    /*text-align: center;*/
    line-height: 100%;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 100%;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
  .el-row {
    margin-bottom: 20px;
  &:last-child {
     margin-bottom: 0;
   }
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
    text-align: left;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
/*  修改按钮选中正确为红色*/
      /*选中后字体的颜色*/
  /*   .el-radio__input.is-checked + .el-radio__label {*/
  /*     color: red !important;*/
  /*   }*/
  /*!* 选中后圆圈的背景颜色 *!*/
  /*.el-radio__input.is-checked .el-radio__inner {*/
  /*  background: red !important;*/
  /*  border-color: red !important;*/
  /*}*/


  /*  修改按钮选中正确为绿色*/
  /*.el-radio__input.is-checked + .el-radio__label {*/
  /*  color: greenyellow !important;*/
  /*}*/
  /*!* 选中后圆圈的背景颜色 *!*/
  /*.el-radio__input.is-checked .el-radio__inner {*/
  /*  background: greenyellow !important;*/
  /*  border-color: greenyellow !important;*/
  /*}*/


  /*checkBox自定义禁用样式*/
    .el-radio__input.is-disabled + .el-radio__label {
      color: #808080 !important;
    }

  .el-radio__input.is-disabled.is-checked + .el-radio__label {
    color: #1890ff !important;
  }


  .el-radio__input.is-disabled.is-checked .el-radio__inner {
    background-color: #1890ff !important;
    border-color: #1890ff !important;
  }
  /*checkBox自定义禁用样式*/
  .el-checkbox__input.is-disabled + .el-checkbox__label {
    color: #808080 !important;
  }

  .el-checkbox__input.is-disabled.is-checked + .el-checkbox__label {
    color: #1890ff !important;
  }


  .el-checkbox__input.is-disabled.is-checked .el-checkbox__inner {
    background-color: #1890ff !important;
    border-color: #1890ff !important;
  }


</style>
