<template>
  <div class="app-container">
    <el-row :gutter="20">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="60px">
      <el-col :span="7">
          <el-form-item label="试题" prop="question">
            <el-input
              v-model="queryParams.question"
              placeholder="请输入题目"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="7" >
          <el-form-item label="答案" prop="answer">
            <el-select v-model="queryParams.answer" placeholder="请选择答案" clearable>
              <el-option
                v-for="dict in dict.type.sys_questions_tf"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6" >
          <el-form-item label="难度" prop="tfLevel">
            <el-select v-model="queryParams.tfLevel" placeholder="请选择难度" clearable>
              <el-option
                v-for="dict in dict.type.sys_questions_level"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="4" >
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['questions:tf_questions:add']"
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
          v-hasPermi="['questions:tf_questions:edit']"
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
          v-hasPermi="['questions:tf_questions:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['questions:tf_questions:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['questions:tf_questions:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tf_questionsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="试题" align="center" prop="question" />
      <el-table-column label="答案【1:正确 0:错误】" align="center" prop="answer">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_questions_tf" :value="scope.row.answer"/>
        </template>
      </el-table-column>
      <el-table-column label="难度【1:简单 2:中等 3:较难】" align="center" prop="tfLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_questions_level" :value="scope.row.tfLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['questions:tf_questions:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['questions:tf_questions:remove']"
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

    <!-- 添加或修改判断题题库对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="试题" prop="question">
          <el-input v-model="form.question" type="textarea" placeholder="请输入题目" :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}" />
        </el-form-item>

        <el-form-item label="答案" prop="answer">
          <el-radio-group v-model="form.answer" size="medium">
            <el-radio :label="1">正确</el-radio>
            <el-radio :label="0">错误</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="难度" prop="tfLevel">
          <el-select v-model="form.tfLevel" placeholder="请选择难度" style="width: 120px">
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
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 判断题导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listTf_questions, getTf_questions, delTf_questions, addTf_questions, updateTf_questions } from "@/api/questions/tf_questions";
import { getToken } from "@/utils/auth";
export default {
  name: "Tf_questions",
  dicts: ['sys_questions_tf', 'sys_questions_level', 'sys_questions_type'],
  data() {
    return {
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/questions/tf_questions/importData" // todo
      },
      //选项默认值
      radio: 1,
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
      // 判断题题库表格数据
      tf_questionsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        questionType: null,
        question: null,
        answer: null,
        tfLevel: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        question: [{
          required: true,
          message: '请输入试题',
          trigger: 'blur'
        }],
        answer: [{
          required: true,
          message: '答案不能为空',
          trigger: 'change'
        }],
        tfLevel: [{
          required: true,
          message: '请选择难度',
          trigger: 'change'
        }],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询判断题题库列表 */
    getList() {
      this.loading = true;
      listTf_questions(this.queryParams).then(response => {
        this.tf_questionsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        questionType: null,
        question: null,
        trueOption: null,
        falseOption: null,
        answer: 1,
        tfLevel: this.dict.type.sys_questions_level[0].value
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加试题";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTf_questions(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改试题";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTf_questions(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTf_questions(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除试题编号为"' + ids + '"的数据项？').then(function() {
        return delTf_questions(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('questions/tf_questions/export', {
        ...this.queryParams
      }, `tf_questions_${new Date().getTime()}.xlsx`)
    },
    ///////以下为判断题如题库的方法//////////
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "判断题导入"; // todo
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('questions/tf_questions/importTemplate', {
      }, `stu_base_template_${new Date().getTime()}.xlsx`)  // todo
    },
// 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
// 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
// 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }

  }
};
</script>
<style>
  .el-radio{
    /*border: #ed5565 solid 5px;*/
    margin-left: 150px;
  }


</style>
