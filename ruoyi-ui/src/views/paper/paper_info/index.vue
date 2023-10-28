<template>
  <div class="app-container">
    <!--查询列表-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="75px">
      <el-form-item label="试题类型" prop="questionsType">
        <el-select v-model="queryParams.questionsType" placeholder="请选择试题类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_questions_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="试题" prop="questions">
        <el-input
          v-model="queryParams.questions"
          placeholder="请输入试题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="难度" prop="questionsLevel">
        <el-select v-model="queryParams.questionsLevel" placeholder="请选择难度" clearable>
          <el-option
            v-for="dict in dict.type.sys_questions_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="分值" prop="perScore">
        <el-input
          v-model="queryParams.perScore"
          placeholder="请输入分值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
      <el-form-item class="fenshu">总分： {{this.currentScore}} 分</el-form-item>
    </el-form>
   <!--四个操作选项，添加、编辑、删除、导出-->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['paper:paper_info:add']"
        >添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['paper:paper_info:edit']"
        >编辑</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['paper:paper_info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['paper:paper_info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--列表数据-->
    <el-table v-loading="loading" :data="paper_infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="试题类型" align="center" prop="questionsType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_questions_type" :value="scope.row.questionsType"/>
        </template>
      </el-table-column>
      <el-table-column label="试题" align="center" prop="questions" />
      <el-table-column label="选项A" align="center" prop="optionA" />
      <el-table-column label="选项B" align="center" prop="optionB" />
      <el-table-column label="选项C" align="center" prop="optionC" />
      <el-table-column label="选项D" align="center" prop="optionD" />
      <el-table-column label="答案" align="center" prop="answer" />
      <el-table-column label="难度" align="center" prop="questionsLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_questions_level" :value="scope.row.questionsLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="分值" align="center" prop="perScore" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!--每行数据中的修改、删除-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['paper:paper_info:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['paper:paper_info:remove']"
          >删除</el-button>
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

    <!-- 添加试题对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="328px" append-to-body>
      <el-form ref="addform" :model="addform" :rules="rules" label-width="70px">
        <el-form-item label="试题类型" prop="questionsType">
          <el-select v-model="addform.questionsType" placeholder="请选择试题类型">
            <el-option
              v-for="dict in dict.type.sys_questions_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="testAmount">
          <el-input v-model="addform.testAmount" placeholder="请输入试题数量" />
        </el-form-item>
        <el-form-item label="分数/题" prop="partScore">
          <el-input v-model="addform.partScore" placeholder="请输入试题分数" />
        </el-form-item>
        <el-form-item label="难度" prop="testLevel">
          <el-select v-model="addform.testLevel" placeholder="请选择难度">
            <el-option
              v-for="dict in dict.type.sys_questions_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <!--   修改前：submitForm    修改后：add-->
        <el-button type="primary" @click="add_confirm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 修改组卷信息对话框 -->
    <el-dialog :title="title" :visible.sync="open_alter" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="试题类型" prop="questionsType">
          <el-select v-model="form.questionsType" placeholder="请选择试题类型">
            <el-option
              v-for="dict in dict.type.sys_questions_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="试题" prop="questions">
          <el-input v-model="form.questions" placeholder="请输入试题" />
        </el-form-item>
        <el-form-item label="选项A" prop="optionA">
          <el-input v-model="form.optionA" placeholder="请输入选项A" />
        </el-form-item>
        <el-form-item label="选项B" prop="optionB">
          <el-input v-model="form.optionB" placeholder="请输入选项B" />
        </el-form-item>
        <el-form-item label="选项C" prop="optionC">
          <el-input v-model="form.optionC" placeholder="请输入选项C" />
        </el-form-item>
        <el-form-item label="选项D" prop="optionD">
          <el-input v-model="form.optionD" placeholder="请输入选项D" />
        </el-form-item>
        <el-form-item label="答案" prop="answer">
          <el-input v-model="form.answer" placeholder="请输入答案" />
        </el-form-item>
        <el-form-item label="难度" prop="questionsLevel">
          <el-select v-model="form.questionsLevel" placeholder="请选择难度">
            <el-option
              v-for="dict in dict.type.sys_questions_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分值" prop="perScore">
          <el-input v-model="form.perScore" placeholder="请输入分值" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPaper_info, getPaper_info, delPaper_info, addPaper_info, updatePaper_info } from "@/api/paper/paper_info";
import { singleDataList, multiplyDataList, tfDataList,Paperlist } from '../../../api/paper/paper_info'
export default {
  name: "Paper_info",
  dicts: ['sys_questions_level', 'sys_questions_type'],
  data() {
    return {
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
      // 组卷信息表格数据
      paper_infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      //两个弹出层的控制 open是添加考试题的 ; open_alter是修改试题的
      open: false,
      open_alter:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        questionsType: null,
        questions: null,
        optionA: null,
        optionB: null,
        optionC: null,
        optionD: null,
        answer: null,
        questionsLevel: null,
        perScore: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      //单选题数据（暂存）
      singleList:[],
      //处理后的数据
      singleData:[],
      //多选题数据
      multiplyData:[],
      //判断题数据
      tfData:[],
      //所有题目数据
      allData:[],
      //总分
      totalScore:100,
      //当前得分
      currentScore:0,
      //添加表单
      addform:{
        //试题类型
        questionsType:null,
        //试题个数
        testAmount:null,
        //试题分值
        partScore:0,
        //试题难度
        testLevel:null,
      },
    };
  },
  created() {
    this.getList();
    this.getDatas();
    this.ScoreCount(); //计算初始列表总分
  },
  methods: {
    /** 查询组卷信息列表 */
    getList() {
      this.loading = true;
      listPaper_info(this.queryParams).then(response => {
        this.paper_infoList = response.rows;
        this.total = response.total;
        this.loading = false;
        console.log("当前题库中的总分是",this.currentScore);
      });
    },
    // 取消按钮
    cancel() {
      //添加试题的对话框不显示，关闭
      this.open = false;
      //修改的对话框也关闭
      this.open_alter =false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        questionsType: null,
        questions: null,
        optionA: null,
        optionB: null,
        optionC: null,
        optionD: null,
        answer: null,
        questionsLevel: null,
        perScore: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 添加按钮操作 */
    handleAdd() {
      if(this.currentScore < this.totalScore){
        this.reset();
        this.open = true;
        this.title = "添加试题";
        this.addform = {
          //试题个数
          testAmount:null,
          //试题分值
          partScore:null,
          //试题难度
          testLevel:null,
          //试题类型
          questionsType:null
        };
        //当前总分，您只有几分
        let scores = this.totalScore - this.currentScore;
        let tips = '当前总分：'+ this.currentScore + "分" + " " +"您还可添加："+scores +"分";
        if(scores < 10){
          this.$alert(tips, '接近满分提示', {
            confirmButtonText: '确定',
          });
        }
      }else{
        this.$message({
          message: '对不起，你已超出总分范围，禁止再添加任何试题！',
          type: 'warning'
        });
      }
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPaper_info(id).then(response => {
        this.form = response.data;
        this.open_alter = true;
        this.title = "修改试题";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePaper_info(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPaper_info(this.form).then(response => {
              this.$modal.msgSuccess("添加成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    //抽取的数据处理
    dataDealWith(){
      console.log("详情：",this.addform.questionsType,this.addform.testLevel,this.addform.testAmount,this.addform.partScore);
      let tA = this.addform.testAmount;   //选中的题目数
      console.log("*****dealwith中目前已获得的分数******",this.currentScore);
      let cycles = 0;
      if(this.currentScore < this.totalScore) {  //列表中的总分还小于100

          for (let s = 0; s < this.singleData.length; s++) {
            if (tA == cycles) {
              break;
            } else {
            if ((this.addform.questionsType == this.singleData[s].questionsType) && (this.addform.testLevel == this.singleData[s].questionsLevel)) {
              cycles ++; //循环加1
              console.log("此次选择的单选题数量：", this.addform.testAmount);
              //添加数据到数据库
              this.singleData[s].perScore = this.addform.partScore;
              this.addTestquestions(this.singleData[s]);
              this.getList();  //刷新列表
              this.ScoreCount();  //刷新分数

            }
          }
        }
          for (let m = 0; m < this.multiplyData.length; m++) {
            console.log("多选的长度",this.multiplyData.length);
              if(tA == cycles){
                break;
              }else{
                if ((this.addform.questionsType == this.multiplyData[m].questionsType) && (this.addform.testLevel == this.multiplyData[m].questionsLevel)) {
                  cycles ++; //循环加1
                  console.log("此次选择的多选题数量：", this.addform.testAmount);
                  //添加数据到数据库
                  this.multiplyData[m].perScore = this.addform.partScore;
                  this.addTestquestions(this.multiplyData[m]);
                  this.getList();  //刷新列表
                  this.ScoreCount();  //刷新分数
                }
              }
        }
          for (let t = 0; t < this.tfData.length; t++) {
            if (tA == cycles) {
              break;
            } else {
              if ((this.addform.questionsType == this.tfData[t].questionsType) && (this.addform.testLevel == this.tfData[t].questionsLevel)) {
                cycles ++; //循环加1
                console.log("此次选择的判断题数量：", this.addform.testAmount);
                //添加数据到数据库
                this.tfData[t].perScore = this.addform.partScore;
                this.addTestquestions(this.tfData[t]);
                this.getList();  //刷新列表
                this.ScoreCount();  //刷新分数
              }
            }
          }
      }else{
         this.open = false;  //添加试题的对话框不显示，关闭
        this.$message({
          message: '对不起，你已超出总分范围，禁止再添加任何试题！',
          type: 'warning'
        });
      }
    },
    //添加试题（封装的，便于使用）
    addTestquestions(datalist){
      //添加数据到数据库
      this.open = false;
        addPaper_info(datalist).then(response => {
          this.$modal.msgSuccess("添加成功");
          //刷新分數列表
          this.ScoreCount();
        })
    },
    //计算当前试题中的总分数
    ScoreCount(){
      Paperlist().then(res=>{
        let templist = [];
        this.currentScore = 0;
        templist = res.rows;
        console.log("数据展示",templist);
        for(let m=0;m<templist.length;m++){
          this.currentScore = templist[m].perScore + this.currentScore;
        };
        console.log("当前题库中的总分是",this.currentScore);
      })
    },
    //确定添加后
    add_confirm(){
      console.log("读取添加的表单数据",this.addform);
      this.dataDealWith();
      console.log("******添加至列表了*******");
      //刷新分數列表
      this.ScoreCount();
    },
    //获取题库列表
    getDatas(){
      //获取单选题列表数据
      singleDataList().then(single_res =>{
        this.singleList = single_res.rows;
        this.singleData =[];
        for(let i=0;i<this.singleList.length;i++){    //序号不从0开始，读取不到数据
          const item = {
            id:this.singleList[i].id,
            questionsType:this.singleList[i].questionType,
            questions:this.singleList[i].question,
            optionA:this.singleList[i].optionA,
            optionB:this.singleList[i].optionB,
            optionC:this.singleList[i].optionC,
            optionD:this.singleList[i].optionD,
            answer:this.singleList[i].answer,
            questionsLevel:this.singleList[i].level,
            perScore: 0
          };
          this.singleData.push(item);
        }
        console.log("已处理好的单选题列表数据",this.singleData);
      });
      //获取多选题列表数据
      multiplyDataList().then(multiply_res =>{
        let m_templist = multiply_res.rows;
        this.multiplyData = [];
        for(let i=0;i<m_templist.length;i++){    //序号不从0开始，读取不到数据
          const item = {
            id:m_templist[i].id,
            questionsType:m_templist[i].questionType,
            questions:m_templist[i].question,
            optionA:m_templist[i].questionA,
            optionB:m_templist[i].questionB,
            optionC:m_templist[i].questionC,
            optionD:m_templist[i].questionD,
            answer:m_templist[i].mulAnswer,
            questionsLevel:m_templist[i].questionLevel,
            perScore: 0
          };
          this.multiplyData.push(item);
        }
        console.log("已处理好的多选题列表数据",this.multiplyData);
      });
      //获取判断题列表数据
      tfDataList().then(tf_res =>{
        let tf_templist = tf_res.rows;
        console.log("打印一个原始数据瞧瞧",tf_templist);
        this.tfData = [];
        for(let i=0;i<tf_templist.length;i++){    //序号不从0开始，读取不到数据
          const item = {
            id:tf_templist[i].id,
            questionsType:tf_templist[i].questionType,
            questions:tf_templist[i].question,
            optionA:tf_templist[i].trueOption,
            optionB:tf_templist[i].falseOption,
            answer:tf_templist[i].answer,
            questionsLevel:tf_templist[i].tfLevel,
            perScore: 0
          };
          this.tfData.push(item);
        }
        console.log("已处理好的判断题列表数据",this.tfData);
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除组卷信息编号为"' + ids + '"的数据项？').then(function() {
        return delPaper_info(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
        //刷新分數列表
        this.ScoreCount();
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('paper/paper_info/export', {
        ...this.queryParams
      }, `paper_info_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style>
  .fenshu{
    /*border: #ed5565 3px solid;*/
    width: 13%;
    text-align: center;
    font-size: large;
    color: #ed5565;
  }
</style>

