<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目名称" prop="projName">
        <el-input
          v-model="queryParams.projName"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="招标编号" prop="projNumber">
        <el-input
          v-model="queryParams.projNumber"
          placeholder="请输入招标编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购人" prop="purchaser">
        <el-input
          v-model="queryParams.purchaser"
          placeholder="请输入采购人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="投标公司名称" prop="bidderCompany">
        <el-input
          v-model="queryParams.bidderCompany"
          placeholder="请输入投标公司名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件名称" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入文件名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上传人" prop="uploadBy">
        <el-input
          v-model="queryParams.uploadBy"
          placeholder="请输入上传人"
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
          v-hasPermi="['tender:project:add']"
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
          v-hasPermi="['tender:project:edit']"
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
          v-hasPermi="['tender:project:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['tender:project:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="projectList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="tpId" />
<!--      <el-table-column label="投标用户id" align="center" prop="bidderId" />-->
<!--      <el-table-column label="招标项目id" align="center" prop="projId" />-->
<!--      <el-table-column label="文件id" align="center" prop="fielId" />-->
      <el-table-column label="项目名称" align="center" prop="projName" />
      <el-table-column label="招标编号" align="center" prop="projNumber" />
      <el-table-column label="采购人" align="center" prop="purchaser" />
      <el-table-column label="投标公司名称" align="center" prop="bidderCompany" />
      <el-table-column label="文件名称" align="center" prop="fileName" />
      <el-table-column label="上传人" align="center" prop="uploadBy" />
      <el-table-column label="上传时间" align="center" prop="uploadTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.uploadTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文件状态" align="center" prop="status" >
        <template slot-scope="scope">
          <el-button type="danger" size="mini" v-if="scope.row.status==0">未上传</el-button>
          <el-button type="success" size="mini" v-if="scope.row.status==1">已上传</el-button>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark">
        <template slot-scope="scope">
          <p v-html="scope.row.remark"></p>
        </template>

      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status==1"
            size="mini"
            type="warning"
            @click="handleUpdateFile(scope.row)"
            v-hasPermi="['tender:project:edit']"
          >重新上传</el-button>
          <el-button
            v-if="scope.row.status==0"
            size="mini"
            type="success"
            @click="handleUpdateFile(scope.row)"
            v-hasPermi="['tender:project:remove']"
          >文件上传</el-button>
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

    <!-- 添加或修改投标项目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="投标用户id" prop="bidderId">
          <el-input v-model="form.bidderId" placeholder="请输入投标用户id" />
        </el-form-item>
        <el-form-item label="招标项目id" prop="projId">
          <el-input v-model="form.projId" placeholder="请输入招标项目id" />
        </el-form-item>
        <el-form-item label="文件id" prop="fielId">
          <el-input v-model="form.fielId" placeholder="请输入文件id" />
        </el-form-item>
        <el-form-item label="项目名称" prop="projName">
          <el-input v-model="form.projName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="招标编号" prop="projNumber">
          <el-input v-model="form.projNumber" placeholder="请输入招标编号" />
        </el-form-item>
        <el-form-item label="采购人" prop="purchaser">
          <el-input v-model="form.purchaser" placeholder="请输入采购人" />
        </el-form-item>
        <el-form-item label="投标公司名称" prop="bidderCompany">
          <el-input v-model="form.bidderCompany" placeholder="请输入投标公司名称" />
        </el-form-item>
        <el-form-item label="文件名称" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入文件名称" />
        </el-form-item>
        <el-form-item label="上传人" prop="uploadBy">
          <el-input v-model="form.uploadBy" placeholder="请输入上传人" />
        </el-form-item>
        <el-form-item label="上传时间" prop="uploadTime">
          <el-date-picker clearable
            v-model="form.uploadTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择上传时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <!-- 上传投标文件对话框 -->
    <el-dialog :title="title" :visible.sync="openfile" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="投标文件" >
          <file-upload v-model="form.tenderTemp"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormFile">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProject, getProject, delProject, addProject, updateProject,updateProjectFile } from "@/api/tender/project";

export default {
  name: "Project",
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
      // 投标项目表格数据
      projectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openfile:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bidderId: null,
        projId: null,
        fielId: null,
        projName: null,
        projNumber: null,
        purchaser: null,
        bidderCompany: null,
        fileName: null,
        uploadBy: null,
        uploadTime: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bidderId: [
          { required: true, message: "投标用户id不能为空", trigger: "blur" }
        ],
        projId: [
          { required: true, message: "招标项目id不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    //文件上传确认按钮
    submitFormFile(){
      console.log(this.form)
      updateProjectFile(this.form).then(res=>{
        this.$message.success("上传成功")
        this.openfile=false
        this.getList();
        console.log("提交")
      })
    },

    //文件上传
    handleUpdateFile(row){
      console.log("投标上传")
      const tpId = row.tpId || this.ids
      getProject(tpId).then(response => {
        this.form = response.data;
        this.openfile=true
        console.log(this.form)
        this.title = "上传投标文件";
      });

    },
    /** 查询投标项目列表 */
    getList() {
      console.log("测试")
      this.loading = true;
      listProject(this.queryParams).then(response => {
        this.projectList = response.rows;
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
        tpId: null,
        bidderId: null,
        projId: null,
        fielId: null,
        projName: null,
        projNumber: null,
        purchaser: null,
        bidderCompany: null,
        fileName: null,
        uploadBy: null,
        uploadTime: null,
        status: "0",
        remark: null
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
      this.ids = selection.map(item => item.tpId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加投标项目";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const tpId = row.tpId || this.ids
      getProject(tpId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改投标项目";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.tpId != null) {
            updateProject(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProject(this.form).then(response => {
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
      const tpIds = row.tpId || this.ids;
      this.$modal.confirm('是否确认删除投标项目编号为"' + tpIds + '"的数据项？').then(function() {
        return delProject(tpIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('tender/project/export', {
        ...this.queryParams
      }, `project_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
