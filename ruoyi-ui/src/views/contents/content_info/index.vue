<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="140px">
      <el-form-item label="类型" prop="contentType">
        <el-select v-model="queryParams.contentType" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_content_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="教师" prop="techer">
        <el-input
          v-model="queryParams.techer"
          placeholder="请输入教师"
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
          v-hasPermi="['contents:content_info:add']"
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
          v-hasPermi="['contents:content_info:edit']"
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
          v-hasPermi="['contents:content_info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['contents:content_info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="content_infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="类型" align="center" prop="contentType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_content_type" :value="scope.row.contentType"/>
        </template>
      </el-table-column>
      <el-table-column label="文本" align="center" prop="textContent" />
      <el-table-column label="文件" align="center" prop="fileContent" />
      <el-table-column label="图片" align="center" prop="pictureContent" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.pictureContent" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="富文本" align="center" prop="allContent" />
      <el-table-column label="教师" align="center" prop="techer" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['contents:content_info:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['contents:content_info:remove']"
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

    <!-- 添加或修改认知与学习对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="类型" prop="contentType">
          <el-select v-model="form.contentType" placeholder="请选择类型">
            <el-option
              v-for="dict in dict.type.sys_content_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文本" prop="textContent">
          <el-input v-model="form.textContent" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="文件" prop="fileContent">
          <file-upload v-model="form.fileContent"/>
        </el-form-item>
        <el-form-item label="图片" prop="pictureContent">
          <image-upload v-model="form.pictureContent"/>
        </el-form-item>
        <el-form-item label="富文本">
          <editor v-model="form.allContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="教师" prop="techer">
          <el-input v-model="form.techer" placeholder="请输入教师" />
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
import { listContent_info, getContent_info, delContent_info, addContent_info, updateContent_info } from "@/api/contents/content_info";

export default {
  name: "Content_info",
  dicts: ['sys_content_type'],
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
      // 认知与学习表格数据
      content_infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contentType: null,
        textContent: null,
        fileContent: null,
        pictureContent: null,
        allContent: null,
        techer: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      //测试
      test:null,
      //定义空数组存储封装好的数据，用于展示
      contentDataList:[]
    };
  },
  created() {
    this.getList();
    //获取数据看看
    this.getContentList()
  },
  methods: {
    /** 查询认知与学习列表 */
    getList() {
      this.loading = true;
      listContent_info(this.queryParams).then(response => {
        this.content_infoList = response.rows;
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
        contentType: null,
        textContent: null,
        fileContent: null,
        pictureContent: null,
        allContent: null,
        techer: null
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
      this.reset();
      this.open = true;
      this.title = "添加";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getContent_info(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "编辑";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateContent_info(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addContent_info(this.form).then(response => {
              this.$modal.msgSuccess("添加成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
      //添加成功后立即刷新数据库列表
      this.getContentList();
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除编号为"' + ids + '"的数据项？').then(function() {
        return delContent_info(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
      // //删除成功后立即刷新数据库列表
      // this.getContentList();
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contents/content_info/export', {
        ...this.queryParams
      }, `content_info_${new Date().getTime()}.xlsx`)
    },
    //获取数据、处理并封装数据
    getContentList(){
      listContent_info().then(res=>{
        let temp = [];
        temp = res.rows;
        console.log("获取处理后的数据列表",temp);    //contentType=3;图文 2--》文件  1==>文本  4-->富文本
        let pictureUrl = '';
        let fileUrl = '';
        let richUrl = '';
        //文件、图片、富文本中的视频、图片的路径获取与处理
        for(let i =0;i<temp.length;i++){
          var fileStr = "'"+ temp[i].fileContent + "'";
          var pictureStr = "'"+ temp[i].pictureContent + "'";
          var richStr = "'"+ temp[i].allContent + "'";
          console.log("fileStr,pictureStr,richStr",fileStr,pictureStr,richStr);
          if((fileStr.charAt(1) == 'h') || (pictureStr.charAt(1) == 'h') || (richStr.charAt(1) == 'h')){
            console.log("第一个字符是h开头，那么它是处理好的数据，无需再处理！");
            continue ;  //跳出当次循环，进行下次循环
          }else {
            console.log("这需要处理了！");
            //处理文件、图片类型的路径补全（判断是否是其中三种，是则处理，不是不作处理）
            if ((temp[i].contentType == 2) || (temp[i].contentType == 3)) {
              if (temp[i].contentType == 2) {
                fileUrl = 'http://localhost/dev-api' + temp[i].fileContent;
                temp[i].fileContent = fileUrl;
                console.log("文件地址", fileUrl);
              } else {
                pictureUrl = 'http://localhost/dev-api' + temp[i].pictureContent;
                temp[i].pictureContent = pictureUrl;
                console.log("图片地址", pictureUrl);
              }
            }
            //对富文本控件地址的截取与处理
            if (temp[i].contentType == 4) {
              var a = temp[i].allContent
              let start_index = a.indexOf('src');
              let last_index = a.indexOf('">');
              richUrl = a.substring(start_index + 5, last_index);
              temp[i].allContent = richUrl;
              console.log("richUrl", richUrl);
            }
          }
        }
        //封装再处理数据(此时地址均为字符串了)
        for(let j=0;j<temp.length;j++){
          let item = {
            id:temp[j].id,
            contentType:temp[j].contentType,
            textContent:temp[j].textContent,
            pictureContent:temp[j].pictureContent,
            fileContent:temp[j].fileContent,
            allContent:temp[j].allContent,
            techer:temp[j].techer,
          }
          this.contentDataList.push(item);
          // 在将处理好的数据压入到数据库中去，使得数据库存储的是带https的全地址。
          updateContent_info(this.contentDataList[j]).then(response => {
            console.log("修改成功否，见知晓");
            this.getList();
          });
          console.log("this.contentDataList",this.contentDataList);

        }
        console.log("封装的数据",this.contentDataList);
      })
    }

  }
};
</script>
