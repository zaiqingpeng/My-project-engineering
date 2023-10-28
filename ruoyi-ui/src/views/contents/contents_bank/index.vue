<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="120px">
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
      <el-form-item label="管理" prop="contentManager">
        <el-input
          v-model="queryParams.contentManager"
          placeholder="请输入管理"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="时间" prop="contentTime">
        <el-date-picker clearable
          v-model="queryParams.contentTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择时间">
        </el-date-picker>
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
          v-hasPermi="['contents:contents_bank:add']"
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
          v-hasPermi="['contents:contents_bank:edit']"
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
          v-hasPermi="['contents:contents_bank:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['contents:contents_bank:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contents_bankList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="类型" align="center" prop="contentType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_content_type" :value="scope.row.contentType"/>
        </template>
      </el-table-column>
      <el-table-column label="文本" align="center" prop="contentText" width="180" />
      <el-table-column label="图片/视频" align="center" prop="contentPictureVideo">
        <template slot-scope="scope">
          <image-preview :src="scope.row.contentPictureVideo" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="文档" align="center" prop="contentFile" />
      <el-table-column label="管理" align="center" prop="contentManager" />
      <el-table-column label="时间" align="center" prop="contentTime" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.contentTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['contents:contents_bank:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['contents:contents_bank:remove']"
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

    <!-- 添加或修改知识仓库对话框 -->
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
        <el-form-item label="文本">
          <editor v-model="form.contentText" :min-height="192"/>
        </el-form-item>
        <el-form-item label="图片/视频" prop="contentPictureVideo">
          <image-upload v-model="form.contentPictureVideo"/>
        </el-form-item>
        <el-form-item label="文档" prop="contentFile">
          <file-upload v-model="form.contentFile"/>
        </el-form-item>
        <el-form-item label="管理" prop="contentManager">
          <el-input v-model="form.contentManager" placeholder="请输入管理" />
        </el-form-item>
        <el-form-item label="时间" prop="contentTime">
          <el-date-picker clearable
            v-model="form.contentTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择时间">
          </el-date-picker>
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
import { listContents_bank, getContents_bank, delContents_bank, addContents_bank, updateContents_bank } from "@/api/contents/contents_bank";

export default {
  name: "Contents_bank",
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
      // 知识仓库表格数据
      contents_bankList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contentType: null,
        contentText: null,
        contentPictureVideo: null,
        contentFile: null,
        contentManager: null,
        contentTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      //定义空数组存储封装好的数据，用于展示
      contentDataList:[]
    };
  },
  created() {
    this.getList();
    //获取数据看看
    this.getContentList();
  },
  methods: {
    //获取数据、处理并封装数据
    getContentList(){
      listContents_bank().then(res=>{
        let temp = [];
        temp = res.rows;
        console.log("获取处理后的数据列表",temp);    //contentType=3;图文 2--》文档  1==>文本  4-->富文本
        let pictureUrl = '';
        let fileUrl = '';
        let dealText = ' ';
        //文档、图片、富文本中的视频、图片的路径获取与处理
        for(let i =0;i<temp.length;i++){
          let fileUrl = "'"+ temp[i].contentFile + "'";
          let picturevideoUrl = "'"+ temp[i].contentPictureVideo + "'";
          //对富文本去除段落标记
          let a = temp[i].contentText;
          temp[i].contentText = a.replace(/<[^>]+>/g, '');
          console.log("test222",temp[i].contentText);
          //富文本处理到此结束
          console.log("fileStr,picturevideoUrl",fileUrl,picturevideoUrl);
          if((fileUrl.charAt(1) == 'h') || (picturevideoUrl.charAt(1) == 'h')){
            console.log("第一个字符是h开头，那么它是处理好的数据，无需再处理！");
            continue ;  //跳出当次循环，进行下次循环
          }else {
            console.log("这需要处理了！");
            //处理文档、图片类型的路径补全（判断是否是其中三种，是则处理，不是不作处理）
            if ((temp[i].contentType == 2) || (temp[i].contentType == 3) ||(temp[i].contentType == 4)) {
              if(temp[i].contentType == 2){
                fileUrl = 'http://localhost/dev-api' + temp[i].contentFile;
                temp[i].contentFile = fileUrl;
                console.log("文档地址", fileUrl);
              }
              else{
                picturevideoUrl = 'http://localhost/dev-api' + temp[i].contentPictureVideo;
                temp[i].contentPictureVideo = picturevideoUrl;
                console.log("图片/视频地址", picturevideoUrl);
              }
            }

            //对富文本控件文本进行截取与处理
              // let a = temp[i].contentText;
              // temp[i].contentText = a.replace(/<[^>]+>/g, '')
              // // let start_index = a.indexOf('<');
              // // let last_index = a.indexOf('>');
              // // dealText = a.substring(start_index,last_index);
              // // temp[i].contentText = dealText ;
              // console.log("处理好的文字为啥没有",temp[i].contentText);


          }
        }
        //封装再处理数据(此时地址均为字符串了)
        for(let j=0;j<temp.length;j++){
          let item = {
            id:temp[j].id,
            contentType:temp[j].contentType,
            contentText:temp[j].contentText,
            contentPictureVideo:temp[j].contentPictureVideo,
            contentFile:temp[j].contentFile,
            contentManager:temp[j].contentManager,
            contentTime:temp[j].contentTime
          }
          this.contentDataList.push(item);
          // 在将处理好的数据压入到数据库中去，使得数据库存储的是带https的全地址。
          updateContents_bank(this.contentDataList[j]).then(response => {
            console.log("修改成功否，见知晓");
            this.getList();
          });
          console.log("this.contentDataList",this.contentDataList);

        }
        console.log("封装的数据",this.contentDataList);
      })
    },
    /** 查询知识仓库列表 */
    getList() {
      this.loading = true;
      listContents_bank(this.queryParams).then(response => {
        this.contents_bankList = response.rows;
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
        contentText: null,
        contentPictureVideo: null,
        contentFile: null,
        contentManager: null,
        contentTime: null
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
      this.title = "添加知识";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getContents_bank(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改知识";
      });

    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateContents_bank(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();

            });
          } else {
            addContents_bank(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除知识仓库编号为"' + ids + '"的数据项？').then(function() {
        return delContents_bank(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contents/contents_bank/export', {
        ...this.queryParams
      }, `contents_bank_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
