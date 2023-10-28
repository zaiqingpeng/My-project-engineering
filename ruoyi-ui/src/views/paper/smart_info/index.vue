<template>
  <div class="app-container">
    <el-row :gutter="20">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="70px">
      <el-col :span="5" >
        <el-form-item label="类型" prop="questionType">
          <el-select v-model="queryParams.questionType" placeholder="请选择试题类型" clearable>
            <el-option
              v-for="dict in dict.type.sys_questions_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="5" >
        <el-form-item label="难度" prop="questionLevel">
          <el-select v-model="queryParams.questionLevel" placeholder="请选择难度" clearable>
            <el-option
              v-for="dict in dict.type.sys_questions_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="5" >
        <el-form-item label="开始时间" prop="beginTime">
          <el-date-picker clearable
                          v-model="queryParams.beginTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择开始时间"
          >
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="5" >
        <el-form-item label="截止时间" prop="endTime">
          <el-date-picker clearable
                          v-model="queryParams.beginTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择截止时间"
          >
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="2.5" >
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-col>
    </el-form>
    </el-row>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-document"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['paper:smart_info:add']"
        >出卷
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-setting"
          size="mini"
          @click="handleUpdate"
        >出题难度控制
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['paper:smart_info:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['paper:smart_info:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="smart_infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="试题类型" align="center" prop="questionType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_questions_type" :value="scope.row.questionType"/>
        </template>
      </el-table-column>
      <el-table-column label="试题" align="center" prop="question"/>
      <el-table-column label="选项1" align="center" prop="optionA"/>
      <el-table-column label="选项2" align="center" prop="optionB"/>
      <el-table-column label="选项3" align="center" prop="optionC"/>
      <el-table-column label="选项4" align="center" prop="optionD"/>
      <el-table-column label="答案" align="center" prop="answer"/>
      <el-table-column label="分值" align="center" prop="perScore"/>
      <el-table-column label="难度" align="center" prop="questionLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_questions_level" :value="scope.row.questionLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="beginTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.beginTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="截止时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['paper:smart_info:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['paper:smart_info:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 添加或修改智能出卷对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="总分推荐" prop="totalScore">
              <el-input-number v-model="form.totalScores" controls-position="right" @change="totalScoresChange" :min="100"
                               :max="100"
              ></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="题量推荐" prop="totalNumber">
              <el-input-number v-model="form.totalNumber" controls-position="right" @change="totalNumberChange" :min="50"
                               :max="100" :step="50"
              ></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="开始时间" prop="beginTime">
              <el-date-picker clearable
                              v-model="form.beginTime"
                              type="datetime"
                              @change="beginDateAndTimeChange"
                              value-format="yyyy-MM-dd HH:MM:SS"
                              placeholder="请选择开始时间"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="截止时间" prop="endTime">
              <el-date-picker clearable
                              v-model="form.endTime"
                              type="datetime"
                              @change="endDateAndTimeChange"
                              value-format="yyyy-MM-dd HH:MM:SS "
                              placeholder="请选择截止时间"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="出题难度推荐" prop="paperLevel">
              <el-select v-model="form.paperLevel" placeholder="请选择出题难度">
                <el-option
                  v-for="dict in dict.type.sys_questions_level"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import {listSmart_info, getSmart_info, delSmart_info, addSmart_info, updateSmart_info} from '@/api/paper/smart_info';
  import  {getHardExtend} from '@/api/paper/hard_controller';
  import { multiply_questionsCount } from '@/api/questions/multiply_questions';
  import { tf_count } from '@/api/questions/tf_questions';
  import { single_count } from '@/api/questions/single_questions';

  export default {
    name: 'Smart_info',
    dicts: ['sys_questions_level', 'sys_questions_type'],
    data() {
      return {
        //指定默认起始时间
        beginDateAndTime:null,
        //截止日期和时间
        endDateAndTime:null,
        //单选题、多选题、判断题每小题得分
        singlePerScores:null,
        multiplyPerScores:null,
        tfPerScores:null,
        //每种题型的简单、中等、较难题目个数的获取
        single_simple:null,
        multiply_simple:null,
        tf_simple:null,
        single_average:null,
        multiply_average:null,
        tf_average:null,
        single_hard:null,
        multiply_hard:null,
        tf_hard:null,
        //难度水平接收值
        hardLevel:1,
        //试题总数
        allNum:50,
        //试题总分
        allScores:100,
        //单选题题数
        single_count:null,
        //多选题题数
        multiply_count:null,
        //判断题题数
        tf_count:null,
        //难度控制列表数据
        hardList:[],
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 智能出卷表格数据
        smart_infoList: [],
        // 弹出层标题
        title: '',
        // 是否显示弹出层
        open: false,
        // 初始为不显示修改对话框
        paperOpen: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          questionType: null,
          question: null,
          optionA: null,
          optionB: null,
          optionC: null,
          optionD: null,
          answer: null,
          perScore: null,
          questionLevel: null,
          managers: null,
          beginTime: null,
          endTime: null,
        },
        // 表单参数
        form: {},
        /////////paperform未使用开始///////////
        // 出题难度设置表
        paperform : {
          singleCount: 60,    //单选题题量
          multiplyCount: 30,    //多选题题量
          tfCount: 10,        //判断题题量
          sSimple:60,     //单选题简单比例
          sAverage:30,  //单选题中等比例
          sHarder:10,   //单选题较难比例
          mSimple:80,   //多选题简单比例
          mAverage:10,  //多选题中等比例
          mHarder:10,   //多选题较难比例
          tSimple:70,   //判断题简单比例
          tAverage: 20, //判断题中等比例
          tHarder:10,   //判断题较难比例
        },
        /////////paperform未使用结束///////////
        // 表单校验
        rules: {
          paperLevel: [{
            required: true,
            message: '请选择试卷难度',
            trigger: 'blur'
          }],
          totalNumber: [{
            required: true,
            message: '您还未确定本次题量',
            trigger: 'blur'
          }],
          totalScores: [{
            required: true,
            message: '您还未确定本次总分',
            trigger: 'blur'
          }],
          beginTime: [{
            required: true,
            message: '请选择开始时间',
            trigger: 'blur'
          }],
          endTime: [{
            required: true,
            message: '请选择截止时间',
            trigger: 'blur'
          }],
          singleCount: [{
            required: true,
            message: '请选择单选题数量',
            trigger: 'blur'
          }],
          multiplyCount: [{
            required: true,
            message: '请选择多选题数量',
            trigger: 'blur'
          }],
          tfCount: [{
            required: true,
            message: '请选择判断题数量',
            trigger: 'blur'
          }]
        }
      }
    },
    created() {
      this.getList();
      //获取当前时间
      this.getNowTime();

    },
    methods: {
      //获取难度控制的列表数据data
      getHardData(){
        getHardExtend().then(res=>{
          this.hardList = [];
          let temp = res.rows;
          for(let i = 0;i<temp.length;i++){
            let item = {
              hardName:temp[i].hardName,
              singleCount:temp[i].singleCount,
              multiplyCount:temp[i].multiplyCount,
              tfCount:temp[i].tfCount,
              sSimple:temp[i].sSimple,
              sAverage:temp[i].sAverage,
              sHarder:temp[i].sHarder,
              mSimple:temp[i].mSimple,
              mAverage:temp[i].mAverage,
              mHarder:temp[i].mHarder,
              tSimple:temp[i].tSimple,
              tAverage:temp[i].tAverage,
              tHarder:temp[i].tHarder,
            };
            this.hardList.push(item);
          }
          console.log("看看封装后的数据hardList:",this.hardList);
          this.getDataListByLogic();
        })

      },
      getDataListByLogic() {
        console.log("总分", this.allScores);
        console.log("总题", this.allNum);
        console.log("hardlist数据********", this.hardList);
        //试题总数与试题百分比相乘得到具体的题目数量
       for(let p = 0;p<this.hardList.length;p++){
         console.log("this.************",this.form.paperLevel);
        if(this.form.paperLevel == this.hardList[p].hardName) {
          this.single_count = this.hardList[p].singleCount * this.allNum * 0.01;
          this.multiply_count = this.hardList[p].multiplyCount * this.allNum * 0.01;
          this.tf_count = this.hardList[p].tfCount * this.allNum * 0.01;
          console.log("单、多、判", this.single_count, this.multiply_count, this.tf_count, this.allNum);

          //单选题
          let singleIsDot = [];
          let single_simple_temp = this.hardList[p].sSimple * this.single_count * 0.01;
          singleIsDot.push(single_simple_temp);
          let single_average_temp = this.hardList[p].sAverage * this.single_count * 0.01;
          singleIsDot.push(single_average_temp);
          let single_hard_temp= this.hardList[p].sHarder * this.single_count * 0.01;
          singleIsDot.push(single_hard_temp);
          let flag = 0;
          for(let i = 0;i<singleIsDot.length;i++){
            let rep = /[.]/;
            let value = null;
            if(rep.test(singleIsDot[i])){  //如果是小数
              if(flag == 0){
                flag++;
                value = Math.ceil(singleIsDot[i]);  //向上取整
                if(single_simple_temp == singleIsDot[i]){
                  this.single_simple = value;
                }
                if(single_average_temp == singleIsDot[i]){
                  this.single_average = value;
                }
                if(single_hard_temp == singleIsDot[i]){
                  this.single_hard = value;
                }
              }
              else{  //如果是小数
                value = Math.floor(singleIsDot[i]);  //向上取整
                if(single_simple_temp == singleIsDot[i]){
                  this.single_simple = value;
                }
                if(single_average_temp == singleIsDot[i]){
                  this.single_average = value;
                }
                if(single_hard_temp == singleIsDot[i]){
                  this.single_hard = value;
                }
              }
            }else{
               value = singleIsDot[i];
              if(single_simple_temp == singleIsDot[i]){
                this.single_simple = value;
              }
              if(single_average_temp == singleIsDot[i]){
                this.single_average = value;
              }
              if(single_hard_temp == singleIsDot[i]){
                this.single_hard = value;
              }
            }
          }
          console.log("单选题简单个数,单选题中等个数,单选题较难个数", this.single_simple, this.single_average, this.single_hard);
          //多选题
          let multiplyIsdot = [];
          let multiply_simple_temp = this.hardList[p].mSimple * this.multiply_count * 0.01;
          multiplyIsdot.push(multiply_simple_temp);
          let multiply_average_temp = this.hardList[p].mAverage * this.multiply_count * 0.01;
          multiplyIsdot.push(multiply_average_temp);
          let multiply_hard_temp = this.hardList[p].mHarder * this.multiply_count * 0.01;
          multiplyIsdot.push(multiply_hard_temp);
          let multiply_flag = 0;
          for(let i = 0;i<multiplyIsdot.length;i++){
            let rep = /[.]/;
            let value = null;
            if(rep.test(multiplyIsdot[i])){  //如果是小数
              if(multiply_flag == 0){
                multiply_flag++;
                value = Math.ceil(multiplyIsdot[i]);  //向上取整
                if(multiply_simple_temp == multiplyIsdot[i]){
                  this.multiply_simple = value;
                }
                if(multiply_average_temp == multiplyIsdot[i]){
                  this.multiply_average = value;
                }
                if(multiply_hard_temp == multiplyIsdot[i]){
                  this.multiply_hard = value;
                }
              }
              else{  //如果是小数
                value = Math.floor(multiplyIsdot[i]);  //向上取整
                if(multiply_simple_temp == multiplyIsdot[i]){
                  this.multiply_simple = value;
                }
                if(multiply_average_temp == multiplyIsdot[i]){
                  this.multiply_average = value;
                }
                if(multiply_hard_temp == multiplyIsdot[i]){
                  this.multiply_hard = value;
                }
              }
            }else{
              value = multiplyIsdot[i];
              if(multiply_simple_temp == multiplyIsdot[i]){
                this.multiply_simple = value;
              }
              if(multiply_average_temp == multiplyIsdot[i]){
                this.multiply_average = value;
              }
              if(multiply_hard_temp == multiplyIsdot[i]){
                this.multiply_hard = value;
              }
            }
          }
          console.log("多选题简单个数,多选题中等个数,多选题较难个数", this.multiply_simple, this.multiply_average, this.multiply_hard);
          //判断题
          let tfIsdot = [];
          let tf_simple_temp = this.hardList[p].tSimple * this.tf_count * 0.01;
          tfIsdot.push(tf_simple_temp);
          let tf_average_temp = this.hardList[p].tAverage * this.tf_count * 0.01;
          tfIsdot.push(tf_average_temp);
          let tf_hard_temp = this.hardList[p].tHarder * this.tf_count * 0.01;
          tfIsdot.push(tf_hard_temp);
          let tf_flag = 0;
          for(let i = 0;i<tfIsdot.length;i++){
            let rep = /[.]/;
            let value = null;
            if(rep.test(tfIsdot[i])){  //如果是小数
              if(tf_flag == 0){
                tf_flag++;
                value = Math.ceil(tfIsdot[i]);  //向上取整
                if(tf_simple_temp == tfIsdot[i]){
                  this.tf_simple = value;
                }
                if(tf_average_temp == tfIsdot[i]){
                  this.tf_average = value;
                }
                if(tf_hard_temp == tfIsdot[i]){
                  this.tf_hard = value;
                }
              }
              else{  //如果是小数
                value = Math.floor(tfIsdot[i]);  //向上取整
                if(tf_simple_temp == tfIsdot[i]){
                  this.tf_simple = value;
                }
                if(tf_average_temp == tfIsdot[i]){
                  this.tf_average = value;
                }
                if(tf_hard_temp == tfIsdot[i]){
                  this.tf_hard = value;
                }
              }
            }else{
              value = tfIsdot[i];
              if(tf_simple_temp == tfIsdot[i]){
                this.tf_simple = value;
              }
              if(tf_average_temp == tfIsdot[i]){
                this.tf_average = value;
              }
              if(tf_hard_temp == tfIsdot[i]){
                this.tf_hard = value;
              }
            }
          }
          console.log("判断题简单个数,判断题中等个数,判断题较难个数", this.tf_simple, this.tf_average, this.tf_hard);
          //单选题、多选题、判断题分值计算
          let singlePartScores = (this.hardList[p].singleCount * this.allScores * 0.01);
          let multiplyPartScores = (this.hardList[p].multiplyCount * this.allScores * 0.01);
          let tfPartScores = (this.hardList[p].tfCount * this.allScores * 0.01);
          console.log("单选题部分、多选题部分、判断题部分分值", singlePartScores, multiplyPartScores, tfPartScores);
          //单选题、多选题、判断题等小题分值计算
          this.singlePerScores = (this.hardList[p].singleCount * this.allScores * 0.01) / this.single_count;
          this.multiplyPerScores = (this.hardList[p].multiplyCount * this.allScores * 0.01) / this.multiply_count;
          this.tfPerScores = (this.hardList[p].tfCount * this.allScores * 0.01) / this.tf_count;
          console.log("单选题小题分值、多选题小题分值、判断题小题分值",  this.singlePerScores,  this.multiplyPerScores,  this.tfPerScores);
        }
      }
        single_count().then(res => {
          let s_temp = [];
          let singleDataByDealWith = [];
          s_temp = res.rows;
          console.log("单选题数据列表：", s_temp);
          for (let s = 0; s < s_temp.length; s++) {
            let s_item = {
              questionType: 1,
              question: s_temp[s].question,
              optionA: s_temp[s].optionA,
              optionB: s_temp[s].optionB,
              optionC: s_temp[s].optionC,
              optionD: s_temp[s].optionD,
              answer: s_temp[s].answer,
              perScore:this.singlePerScores ,
              questionLevel: s_temp[s].level,
              beginTime:this.beginDateAndTime,
              endTime:this.endDateAndTime,
            }
            singleDataByDealWith.push(s_item);
          }
          let i = 0;
          let a = 0;
          let b = 0;
          for (let bS = 0; bS < singleDataByDealWith.length; bS++) {
            //简单题
            if (singleDataByDealWith[bS].questionLevel == 1){
              if(i < this.single_simple) {
                i++;
                console.log("single_simple", this.single_simple);
                this.addTestquestions(singleDataByDealWith[bS]);
              }
            }
            //中等题
            if (singleDataByDealWith[bS].questionLevel == 2){
              if(a < this.single_average){
                a++;
                console.log("single_average",this.single_average);
                this.addTestquestions(singleDataByDealWith[bS]);
              }
            }
            //较难题
            if (singleDataByDealWith[bS].questionLevel == 3){
              if(b < this.single_hard){
                b++;
                console.log("single_hard",this.single_hard);
                this.addTestquestions(singleDataByDealWith[bS]);
              }
            }
          }
          this.getList();
        });
        // 多选题简单、中等、较难处理
        multiply_questionsCount().then(res => {
          console.log("打印多选题题数返回值", res.total);
          let m_temp = [];
          let multiplyDataByDealWith = [];
          m_temp = res.rows;
          console.log("多选题数据列表：", m_temp);
          for (let m = 0; m < m_temp.length; m++) {
            let m_item = {
              questionType: 2,
              question: m_temp[m].question,
              optionA: m_temp[m].questionA,
              optionB: m_temp[m].questionB,
              optionC: m_temp[m].questionC,
              optionD: m_temp[m].questionD,
              answer: m_temp[m].mulAnswer,
              perScore: this.multiplyPerScores,
              questionLevel: m_temp[m].questionLevel,
              beginTime:this.beginDateAndTime,
              endTime:this.endDateAndTime,
            }
            multiplyDataByDealWith.push(m_item);
          }
          console.log("--------------000--------------------",multiplyDataByDealWith.length);
          let m = 0;
          let c = 0;
          let d = 0;
          for (let bM = 0; bM < multiplyDataByDealWith.length; bM++) {
            //简单题
            if (multiplyDataByDealWith[bM].questionLevel == 1) {
              if ( m < this.multiply_simple) {
                 m++;
                console.log("multiply_simple",this.multiply_simple);
                this.addTestquestions(multiplyDataByDealWith[bM]);
              }
            }
            //中等题
            if (multiplyDataByDealWith[bM].questionLevel == 2) {
              if( c < this.multiply_average) {
                c++
                console.log("multiply_average",this.multiply_average);
                this.addTestquestions(multiplyDataByDealWith[bM]);
              }
            }
            //较难题
            if (multiplyDataByDealWith[bM].questionLevel == 3) {
              if(d < this.multiply_hard ) {
                d++
                console.log("multiply_hard",this.multiply_hard);
                this.addTestquestions(multiplyDataByDealWith[bM]);
              }
            }
          }
          this.getList();
        });
        // 判断题简单、中等、较难处理
        tf_count().then(res => {
          let t_temp = [];
          let tfDataByDealWith = [];
          t_temp = res.rows;
          console.log("判断题数据列表：", t_temp);
          for (let t = 0; t < t_temp.length; t++) {
            let t_item = {
              questionType: 3,
              question: t_temp[t].question,
              answer: t_temp[t].answer,
              perScore: this.tfPerScores,
              questionLevel: t_temp[t].tfLevel,
              beginTime:this.beginDateAndTime,
              endTime:this.endDateAndTime,
            }
            tfDataByDealWith.push(t_item);
          }
          let h = 0;
          let e = 0;
          let t = 0;
          for (let bT = 0; bT < tfDataByDealWith.length; bT++) {
            //简单题
            if (tfDataByDealWith[bT].questionLevel == 1) {
              if(h < this.tf_simple) {
                 h++;
                console.log("tf_simple",this.tf_simple);
                this.addTestquestions(tfDataByDealWith[bT]);
              }
            }
            //中等题
            if (tfDataByDealWith[bT].questionLevel == 2) {
              if(e < this.tf_average){
                e++;
                console.log("tf_average",this.tf_average);
                this.addTestquestions(tfDataByDealWith[bT]);
              }
            }
            //较难题
            if (tfDataByDealWith[bT].questionLevel == 3) {
              if(t < this.tf_hard) {
                t++;
                console.log("tf_hard",this.tf_hard);
                this.addTestquestions(tfDataByDealWith[bT]);
              }
            }
          }
          this.getList();
        })
      },
      //添加试题（封装的，便于使用）
      addTestquestions(datalist){
        //添加数据到数据库
        this.open = false;
        addSmart_info(datalist).then(response => {
          // this.$modal.msgSuccess("添加成功");
        })
      },
      //获取试卷总分
      totalScoresChange(totalScores){
        console.log("赋值前的总分值：",this.allScores);
        console.log('试卷总分：', totalScores);
        this.allScores = totalScores;
        console.log("赋值成功后的总分值？",this.allScores);
      },
      //获取试题总数
      totalNumberChange(totalNumber){
        console.log("赋值前的值：",this.allNum);
        console.log('试题总数：', totalNumber);
        this.allNum = totalNumber;
        console.log("赋值成功？",this.allNum);
      },
      endDateAndTimeChange(end){
        console.log("截止时间未选择之前：",this.endDateAndTime);
        this.endDateAndTime= end;
        console.log("截止时间选择后的值",this.endDateAndTime);
      },
      beginDateAndTimeChange(start){
        console.log("未选择之前是默认时间：",this.beginDateAndTime);
        this.beginDateAndTime= start;
        console.log("开始时间选择后：",this.beginDateAndTime);
      },
   /** 获取当前时间以及日期 */
      getNowTime() {
        let now = new Date();
        let year = now.getFullYear(); //获取年
        let month = now.getMonth();//获取月
        let date = now.getDate();//获取日
        let hours = now.getHours();//获取小时
        let minutes = now.getMinutes();//获取分钟
        let seconds = now.getSeconds();//获取秒
        month = month + 1;
        month = month.toString().padStart(2, "0");
        date = date.toString().padStart(2, "0");
        this.beginDateAndTime = `${year}-${month}-${date} ${hours}:${minutes}:${seconds}`;
      },
      /** 查询智能出卷列表 */
      getList() {
        this.loading = true
        listSmart_info(this.queryParams).then(response => {
          this.smart_infoList = response.rows
          this.total = response.total
          this.loading = false
        })
      },
      // 取消按钮
      cancel() {
        this.open = false
        //关闭修改出题比例对话框。
        this.paperOpen = false
        this.reset()
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          totalScores: this.allScores,
          totalNumber: this.allNum,
          paperLevel: this.dict.type.sys_questions_level[0].value,
          beginTime: this.beginDateAndTime,
          endTime: this.endDateAndTime,
        }
        this.resetForm('form')
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1
        this.getList()
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm('queryForm')
        this.handleQuery()
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset()
        this.open = true
        this.title = '发布试卷'
      },
      /** 编辑出题难度 */
      handleUpdate(row) {
        //初始默认行为
        this.paperOpen = true;
        this.title = '编辑出题难度'
      },
      /** 提交按钮 */
      submitForm() {
        console.log("总分与总题数",this.allScores,this.allNum);
        console.log("难度水平值获取",this.form.paperLevel);
        console.log("起始时间",this.beginDateAndTime);
        console.log("截止时间",this.endDateAndTime);
        this.$refs['form'].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateSmart_info(this.form).then(response => {
                this.$modal.msgSuccess('修改成功')
                this.open = false
                this.getList()
              })
            } else {
              //获取难度列表并进行数据处理
                this.getHardData();
                this.open = false;
                this.getList();
            }
          }
        })
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids
        this.$modal.confirm('是否确认删除出卷编号为"' + ids + '"的数据项？').then(function() {
          return delSmart_info(ids)
        }).then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        }).catch(() => {
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('paper/smart_info/export', {
          ...this.queryParams
        }, `smart_info_${new Date().getTime()}.xlsx`)
      }
    }
  }
</script>
