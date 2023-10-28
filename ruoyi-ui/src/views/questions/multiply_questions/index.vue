<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
        <el-col :span="10" >
          <el-form-item label="试题" prop="question">
            <el-input
              v-model="queryParams.question"
              placeholder="请输入题目"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="10" >
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
          v-hasPermi="['questions:multiply_questions:add']"
        >添加
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['questions:multiply_questions:edit']"
        >修改
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
          v-hasPermi="['questions:multiply_questions:remove']"
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
          v-hasPermi="['questions:multiply_questions:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['questions:multiply_questions:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="multiply_questionsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="试题" align="center" prop="question"/>
      <el-table-column label="A选项" align="center" prop="questionA"/>
      <el-table-column label="B选项" align="center" prop="questionB"/>
      <el-table-column label="C选项" align="center" prop="questionC"/>
      <el-table-column label="D选项" align="center" prop="questionD"/>
      <el-table-column label="答案" align="center" prop="mulAnswer"/>
      <el-table-column label="难度【1:简单;2:中等;3:较难】" align="center" prop="questionLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_questions_level" :value="scope.row.questionLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['questions:multiply_questions:edit']"
          >编辑
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['questions:multiply_questions:remove']"
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

    <!-- 添加或修改多选题题库对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="试题" prop="question">
          <el-input v-model="form.question" type="textarea" placeholder="请输入试题"
                    :autosize="{minRows: 5, maxRows: 5}" :style="{width: '100%'}"/>
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12" >
            <el-form-item label="A." prop="questionA">
              <el-input v-model="form.questionA" placeholder="请输入选项A" type="textarea"
                        :autosize="{minRows: 3, maxRows: 3}" :style="{width: '100%'}"/>
            </el-form-item>
          </el-col>
          <el-col :span="12" >
            <el-form-item label="B." prop="questionB">
              <el-input v-model="form.questionB" placeholder="请输入选项B" type="textarea"
                        :autosize="{minRows: 3, maxRows: 3}" :style="{width: '100%'}"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12" >
            <el-form-item label="C." prop="questionC">
              <el-input v-model="form.questionC" placeholder="请输入选项C" type="textarea"
                        :autosize="{minRows: 3, maxRows: 3}" :style="{width: '100%'}"/>
            </el-form-item>
          </el-col>
          <el-col :span="12" >
            <el-form-item label="D." prop="questionD">
              <el-input v-model="form.questionD" placeholder="请输入选项D" type="textarea"
                        :autosize="{minRows: 3, maxRows: 3}" :style="{width: '100%'}"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="答案" prop="mulAnswer">
          <el-input v-model="form.mulAnswer" placeholder="请输入答案"/>
        </el-form-item>
        <el-form-item label="难度" prop="questionLevel">
          <el-select v-model="form.questionLevel" placeholder="请选择难度水平" size="small">
            <el-option
              v-for="dict in dict.type.sys_questions_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
         <!--答案和难度水平放在一行-->
<!--        <el-row :gutter="20">-->
<!--          <el-col :span="18" >-->
<!--            <el-form-item label="答案" prop="mulAnswer">-->
<!--              <el-input v-model="form.mulAnswer" placeholder="请输入答案"/>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--          <el-col :span="12" >-->
<!--            <el-form-item label="难度" prop="questionLevel">-->
<!--              <el-select v-model="form.questionLevel" placeholder="请选择难度水平" size="small">-->
<!--                <el-option-->
<!--                  v-for="dict in dict.type.sys_questions_level"-->
<!--                  :key="dict.value"-->
<!--                  :label="dict.label"-->
<!--                  :value="dict.value"-->
<!--                ></el-option>-->
<!--              </el-select>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--        </el-row>-->
<!-- 添加栏底部的取消与确定按钮-->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  <!--************导入数据对话框**************-->
    <!-- 用户导入对话框 -->
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
  import {
    listMultiply_questions,
    getMultiply_questions,
    delMultiply_questions,
    addMultiply_questions,
    updateMultiply_questions
  } from '@/api/questions/multiply_questions'
  import { getToken } from "@/utils/auth";
  export default {
    name: 'Multiply_questions',
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
        // 多选题题库表格数据
        multiply_questionsList: [],
        // 弹出层标题
        title: '',
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          questionType: null,
          question: null,
          mulAnswer: null,
          questionLevel: null
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
          mulAnswer:[{
            require:true,
            message: '请输入答案',
            trigger: 'blur'
          }],
          questionLevel:[{
            require:true,
            message: '请选择难度',
            trigger: 'blur'
          }]
        },
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
          url: process.env.VUE_APP_BASE_API + "/questions/multiply_questions/importData" // 模块名+业务功能名
        }
      }
    },
    created() {
      this.getList()
    },
    methods: {
      /** 查询多选题题库列表 */
      getList() {
        this.loading = true
        listMultiply_questions(this.queryParams).then(response => {
          this.multiply_questionsList = response.rows
          this.total = response.total
          this.loading = false
        })
      },
      // 取消按钮
      cancel() {
        this.open = false
        this.reset()
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          questionType: null,
          question: null,
          questionA: null,
          questionB: null,
          questionC: null,
          questionD: null,
          mulAnswer: 'ABCD',
          questionLevel: this.dict.type.sys_questions_level[0].value
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
      /** 添加按钮操作 */
      handleAdd() {
        this.reset()
        this.open = true
        this.title = '添加试题'
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset()
        const id = row.id || this.ids
        getMultiply_questions(id).then(response => {
          this.form = response.data
          this.open = true
          this.title = '修改试题'
        })
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateMultiply_questions(this.form).then(response => {
                this.$modal.msgSuccess('修改成功')
                this.open = false
                this.getList()
              })
            } else {
              addMultiply_questions(this.form).then(response => {
                this.$modal.msgSuccess('添加成功')
                this.open = false
                this.getList()
              })
            }
          }
        })
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids
        this.$modal.confirm('是否确认删除试题编号为"' + ids + '"的数据项？').then(function() {
          return delMultiply_questions(ids)
        }).then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        }).catch(() => {
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('questions/multiply_questions/export', {
          ...this.queryParams
        }, `multiply_questions_${new Date().getTime()}.xlsx`)
      },
      /////////*****以下为导入方法******/////////
      /** 导入按钮操作 */
      handleImport() {
        this.upload.title = "多选题数据导入"; // todo
        this.upload.open = true;
      },
      /** 下载模板操作 */
      importTemplate() {
        this.download('questions/multiply_questions/importTemplate', {
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
  }
</script>
