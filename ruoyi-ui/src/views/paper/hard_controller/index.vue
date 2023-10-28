<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          @click="handleAdd"
          v-hasPermi="['paper:hard_controller:add']"
        >新增难度比例</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['paper:hard_controller:remove']"
        >批量删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="hard_controllerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="难度水平" align="center" prop="hardName">
      <template slot-scope="scope">
        <dict-tag :options="dict.type.sys_questions_level" :value="scope.row.hardName"/>
      </template>
      </el-table-column>
      <el-table-column label="单选题占比" align="center" prop="singleCount" />
      <el-table-column label="多选题占比" align="center" prop="multiplyCount" />
      <el-table-column label="判断题占比" align="center" prop="tfCount" />
      <el-table-column label="单选题简单占比" align="center" prop="sSimple" />
      <el-table-column label="单选题中等占比" align="center" prop="sAverage" />
      <el-table-column label="单选题较难占比" align="center" prop="sHarder" />
      <el-table-column label="多选题简单占比" align="center" prop="mSimple" />
      <el-table-column label="多选题中等占比" align="center" prop="mAverage" />
      <el-table-column label="多选题较难占比" align="center" prop="mHarder" />
      <el-table-column label="判断题简单占比" align="center" prop="tSimple" />
      <el-table-column label="判断题中等占比" align="center" prop="tAverage" />
      <el-table-column label="判判断较难占比" align="center" prop="tHarder" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['paper:hard_controller:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['paper:hard_controller:remove']"
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


    <!-- 修改出题难度设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1400px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="单选题占比" prop="singleCount">
              <el-input-number v-model="form.singleCount" controls-position="right" @change="singleCountChange" :min="0" :max="300"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="简单百分比" prop="sSimple">
              <el-input-number v-model="form.sSimple" controls-position="right" @change="sSimpleChange" :min="0" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="中等百分比" prop="sAverage">
              <el-input-number v-model="form.sAverage" controls-position="right" @change="sAverageChange" :min="0" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="较难百分比" prop="sHarder">
              <el-input-number v-model="form.sHarder" controls-position="right" @change="sHardChange" :min="0" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="多选题占比" prop="multiplyCount">
              <el-input-number v-model="form.multiplyCount" controls-position="right" @change="multiplyCountChange" :min="0" :max="300"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="简单百分比" prop="mSimple">
              <el-input-number v-model="form.mSimple" controls-position="right" @change="mSimpleChange" :min="0" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="中等百分比" prop="mAverage">
              <el-input-number v-model="form.mAverage" controls-position="right" @change="mAverageChange" :min="0" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="较难百分比" prop="mHarder">
              <el-input-number v-model="form.mHarder" controls-position="right" @change="mHardChange" :min="0" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="判断题占比" prop="tfCount">
              <el-input-number v-model="form.tfCount" controls-position="right" @change="tfCountChange" :min="0" :max="300"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="简单百分比" prop="tSimple">
              <el-input-number v-model="form.tSimple" controls-position="right" @change="tSimpleChange" :min="0" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="中等百分比" prop="tAverage">
              <el-input-number v-model="form.tAverage" controls-position="right" @change="tAverageChange" :min="0" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="较难百分比" prop="tHarder">
              <el-input-number v-model="form.tHarder" controls-position="right" @change="tHardChange" :min="0" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-form-item label="难度水平" prop="hardName">
          <el-select v-model="form.hardName" placeholder="请选择难度" clearable>
            <el-option
              v-for="dict in dict.type.sys_questions_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="success" @click="submitForm">提 交</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listHard_controller, getHard_controller, delHard_controller, addHard_controller, updateHard_controller } from "@/api/paper/hard_controller";

export default {
  name: "Hard_controller",
  dicts:['sys_questions_level'],
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
      // 出题难度控制表格数据
      hard_controllerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        hardName:null,
        singleCount: null,
        multiplyCount: null,
        tfCount: null,
        sSimple: null,
        sAverage: null,
        sHarder: null,
        mSimple: null,
        mAverage: null,
        mHarder: null,
        tSimple: null,
        tAverage: null,
        tHarder: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
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
        }],
        hardName:[{
          required:true,
          message:"请选择难度水平",
          trigger:'blur'
        }]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    //获取单选题总数
    singleCountChange(singleCount) {
      this.paperform.singleCount = singleCount;
      console.log('单选题总数：', singleCount);
      console.log('选择的总数：', this.paperform.singleCount);
    },
    //获取多选题总数
    multiplyCountChange(multiplyCount) {
      this.paperform.multiplyCount = multiplyCount;
      console.log('多选题总数：', multiplyCount);
    },
    //获取判断题总数
    tfCountChange(tfCount) {
      this.paperform.tfCount = tfCount;
      console.log('判断题总数：',  tfCount);
    },
    //获取单选题简单比例值
    sSimpleChange(sSimple) {
      this.paperform.sSimple = sSimple;
      console.log('单选题简单比例：', sSimple);
    },
    //获取单选题中等比例值
    sAverageChange(sAverage) {
      this.paperform.sAverage = sAverage;
      console.log('单选题中等比例：', sAverage);
    },
    //获取单选题较难比例值
    sHardChange(sHard) {
      this.paperform.sHarder = sHard;
      console.log('单选题较难比例：', sHard);
    },
    //获取多选题简单比例值
    mSimpleChange(mSimple) {
      this.paperform.mSimple = mSimple;
      console.log('多选题简单比例：', mSimple);
    },
    //获取多选题中等比例值
    mAverageChange(mAverage) {
      this.paperform.mAverage = mAverage;
      console.log('多选题中等比例：', mAverage);
    },
    //获取多选题较难比例值
    mHardChange( mHard) {
      this.paperform.mHarder = mHard;
      console.log('多选题较难比例：',  mHard);
    },
    //获取判断题简单比例值
    tSimpleChange(tSimple) {
      this.paperform.tSimple = tSimple;
      console.log('判断题简单比例：', tSimple);
    },
    //获取判断题中等比例值
    tAverageChange(tAverage) {
      this.paperform.tAverage = tAverage;
      console.log('判断题中等比例：', tAverage);
    },
    //获取判断题较难比例值
    tHardChange(tHard) {
      this.paperform.tHarder = tHard;
      console.log('判断题较难比例：', tHard);
    },
    /** 查询出题难度控制列表 */
    getList() {
      this.loading = true;
      listHard_controller(this.queryParams).then(response => {
        this.hard_controllerList = response.rows;
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
        singleCount: 60,    //单选题题量
        hardName:1,
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
      this.title = "添加出题难度控制";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getHard_controller(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改出题难度控制";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateHard_controller(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHard_controller(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除出题难度控制编号为"' + ids + '"的数据项？').then(function() {
        return delHard_controller(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('paper/hard_controller/export', {
        ...this.queryParams
      }, `hard_controller_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
