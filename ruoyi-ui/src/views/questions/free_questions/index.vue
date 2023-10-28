<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关键词1" prop="keyword1">
        <el-input
          v-model="queryParams.keyword1"
          placeholder="请输入关键词1"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关键词2" prop="keyword2">
        <el-input
          v-model="queryParams.keyword2"
          placeholder="请输入关键词2"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关键词3" prop="keyword3">
        <el-input
          v-model="queryParams.keyword3"
          placeholder="请输入关键词3"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关键词4" prop="keyword4">
        <el-input
          v-model="queryParams.keyword4"
          placeholder="请输入关键词4"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关键词5" prop="keyword5">
        <el-input
          v-model="queryParams.keyword5"
          placeholder="请输入关键词5"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="难度等级" prop="questionLevel">
        <el-input
          v-model="queryParams.questionLevel"
          placeholder="请输入难度等级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['questions:free_questions:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['questions:free_questions:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['questions:free_questions:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['questions:free_questions:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="free_questionsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="题干" align="center" prop="question" />
      <el-table-column label="答案" align="center" prop="answer" />
      <el-table-column label="解析" align="center" prop="questionDescribe" />
      <el-table-column label="关键词1" align="center" prop="keyword1" />
      <el-table-column label="关键词2" align="center" prop="keyword2" />
      <el-table-column label="关键词3" align="center" prop="keyword3" />
      <el-table-column label="关键词4" align="center" prop="keyword4" />
      <el-table-column label="关键词5" align="center" prop="keyword5" />
      <el-table-column label="难度等级" align="center" prop="questionLevel" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['questions:free_questions:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['questions:free_questions:remove']"
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

    <!-- 添加或修改简答题题库对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="题干" prop="question">
          <el-input v-model="form.question" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="答案" prop="answer">
          <el-input v-model="form.answer" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="解析" prop="questionDescribe">
          <el-input v-model="form.questionDescribe" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="关键词1" prop="keyword1">
          <el-input v-model="form.keyword1" placeholder="请输入关键词1" />
        </el-form-item>
        <el-form-item label="关键词2" prop="keyword2">
          <el-input v-model="form.keyword2" placeholder="请输入关键词2" />
        </el-form-item>
        <el-form-item label="关键词3" prop="keyword3">
          <el-input v-model="form.keyword3" placeholder="请输入关键词3" />
        </el-form-item>
        <el-form-item label="关键词4" prop="keyword4">
          <el-input v-model="form.keyword4" placeholder="请输入关键词4" />
        </el-form-item>
        <el-form-item label="关键词5" prop="keyword5">
          <el-input v-model="form.keyword5" placeholder="请输入关键词5" />
        </el-form-item>
        <el-form-item label="难度等级" prop="questionLevel">
          <el-input v-model="form.questionLevel" placeholder="请输入难度等级" />
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
import { listFree_questions, getFree_questions, delFree_questions, addFree_questions, updateFree_questions } from "@/api/questions/free_questions";

export default {
  name: "Free_questions",
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
      // 简答题题库表格数据
      free_questionsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        question: null,
        answer: null,
        questionDescribe: null,
        keyword1: null,
        keyword2: null,
        keyword3: null,
        keyword4: null,
        keyword5: null,
        questionLevel: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询简答题题库列表 */
    getList() {
      this.loading = true;
      listFree_questions(this.queryParams).then(response => {
        this.free_questionsList = response.rows;
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
        question: null,
        answer: null,
        questionDescribe: null,
        keyword1: null,
        keyword2: null,
        keyword3: null,
        keyword4: null,
        keyword5: null,
        questionLevel: null
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
      this.title = "添加简答题题库";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFree_questions(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改简答题题库";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateFree_questions(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFree_questions(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除简答题题库编号为"' + ids + '"的数据项？').then(function() {
        return delFree_questions(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('questions/free_questions/export', {
        ...this.queryParams
      }, `free_questions_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
