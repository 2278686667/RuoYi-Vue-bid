<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目id" prop="projId">
        <el-input
          v-model="queryParams.projId"
          placeholder="请输入项目id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评审专家Id" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入评审专家Id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否签到" prop="signIn">
        <el-input
          v-model="queryParams.signIn"
          placeholder="请输入是否签到"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件id" prop="fileId">
        <el-input
          v-model="queryParams.fileId"
          placeholder="请输入文件id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否删除(0-未删, 1-已删)" prop="isDeleted">
        <el-input
          v-model="queryParams.isDeleted"
          placeholder="请输入是否删除(0-未删, 1-已删)"
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
          v-hasPermi="['system:evaluation_experts_information:add']"
        >新增
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
          v-hasPermi="['system:evaluation_experts_information:edit']"
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
          v-hasPermi="['system:evaluation_experts_information:remove']"
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
          v-hasPermi="['system:evaluation_experts_information:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="evaluation_experts_informationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" min-width="8%"/>
      <el-table-column label="是否签到" align="center" prop="signIn" min-width="8%">
        <template slot-scope="scope">
          <el-button type="success" size="mini" icon="el-icon-check" v-if="scope.row.signIn==1">签到成功</el-button>
          <el-button type="danger" size="mini" v-else-if="scope.row.signIn==0" @click="signIn(scope.row)">点击签到
          </el-button>
        </template>

      </el-table-column>
      <el-table-column label="评审id" align="center" prop="reviewId" min-width="8%"/>
      <el-table-column label="项目名称" align="center" prop="projName" min-width="8%"/>
      <el-table-column label="项目编号" align="center" prop="projNumber" min-width="8%"/>
      <el-table-column label="采购人" align="center" prop="purchaser" min-width="8%"/>
      <el-table-column label="采购代理机构" align="center" prop="bidderCompany" min-width="8%"/>
      <!--      <el-table-column label="评审专家Id" align="center" prop="userId" min-width="8%"/>-->
      <!--      <el-table-column label="文件id" align="center" prop="fileId" />-->
      <!--      <el-table-column label="项目id" align="center" prop="projId" />-->
      <el-table-column label="评审状态" align="center" prop="status" min-width="8%">
        <template slot-scope="scope">
          <el-button type="success" size="mini" v-if="scope.row.status==2" round>已提交</el-button>
          <el-button type="primary" size="mini" v-else round>未提交</el-button>
        </template>
      </el-table-column>
      <!--      <el-table-column label="是否删除(0-未删, 1-已删)" align="center" prop="isDeleted" min-width="8%">-->
      <!--        <template slot-scope="scope">-->
      <!--          <el-button type="success" size="mini" v-if="scope.isDeleted==1">已删除</el-button>-->
      <!--          <el-button type="danger" size="mini" v-else>未删除</el-button>-->
      <!--        </template>-->
      <!--      </el-table-column>-->

      <el-table-column label="备注" align="center" prop="remark" min-width="8%"/>
      <el-table-column label="操作" align="center" min-width="20%">
        <template slot-scope="scope">
          <!--          {{scope.row}}-->
          <el-button type="success" size="mini" icon="el-icon-upload" v-if="scope.row.signIn==1&&scope.row.status==0"
                     @click="zaixian(scope.row)">
            在线评分
          </el-button>
          <el-button type="success" size="mini" icon="el-icon-upload"
                     v-if="scope.row.signIn==1&&(scope.row.status==1||scope.row.status==0)"
                     @click="downloadReview(scope.row)">下载评审资料
          </el-button>
          <el-button type="primary" size="mini" icon="el-icon-upload2"
                     v-if="scope.row.signIn==1&&(scope.row.status==1||scope.row.status==0)"
                     @click="submitReview(scope.row)">提交评审资料
          </el-button>
          <el-button type="success" size="mini" icon="el-icon-upload"
                     v-if="scope.row.signIn==1&&scope.row.status==2&&scope.row.bidStatus!=5">上传成功
          </el-button>
          <!--          <el-button type="danger" size="mini" icon="el-icon-upload2"-->
          <!--                     v-if="scope.row.signIn==1&&scope.row.status==1&&scope.row.isGroupLeaders==1&&scope.row.bidStatus!=5"-->
          <!--                     @click="submitReview(scope.row)">上传最终评审资料-->
          <!--          </el-button>-->
          <el-button type="success" size="mini" icon="el-icon-upload" v-if="scope.row.bidStatus==5">评标完成</el-button>
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

    <!-- 添加或修改评审专家信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目id" prop="projId">
          <el-input v-model="form.projId" placeholder="请输入项目id"/>
        </el-form-item>
        <el-form-item label="评审专家Id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入评审专家Id"/>
        </el-form-item>
        <el-form-item label="是否签到" prop="signIn">
          <el-input v-model="form.signIn" placeholder="请输入是否签到"/>
        </el-form-item>
        <el-form-item label="文件id" prop="fileId">
          <el-input v-model="form.fileId" placeholder="请输入文件id"/>
        </el-form-item>
        <el-form-item label="是否删除(0-未删, 1-已删)" prop="isDeleted">
          <el-input v-model="form.isDeleted" placeholder="请输入是否删除(0-未删, 1-已删)"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <!-- 提交评审资料 -->
    <el-dialog :title="title" :visible.sync="openfile" width="500px" append-to-body>
      <el-form ref="form" :model="formEv" :rules="rules" label-width="80px">
        <el-form-item label="评审资料">
          <file-upload v-model="formEv.tenderTemp"/>
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
import {listEvaluation} from "../../../api/file/file";
import {
  updateSignIn,
  lssue_of_tenderE,
  listEvaluation_experts_information,
  listEvaluation_experts_informationBYUserId,
  getEvaluation_experts_information,
  delEvaluation_experts_information,
  addEvaluation_experts_information,
  updateEvaluation_experts_information,
  fileUpload
} from "@/api/system/evaluation_experts_information";

import {
  lssue_of_tender,
} from "@/api/bid/invite_tenders";
import download from "../../../plugins/download";

export default {
  name: "Evaluation_experts_information",
  data() {
    return {
      formEv: {},
      openfile: false,
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
      // 评审专家信息表格数据
      evaluation_experts_informationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projId: null,
        userId: null,
        signIn: null,
        fileId: null,
        status: null,
        isDeleted: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "更新时间不能为空", trigger: "blur"}
        ],
        isDeleted: [
          {required: true, message: "是否删除(0-未删, 1-已删)不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    zaixian(row) {
      this.$router.push({
        path: 'system/mark_sheet/index.vue',
        query: {
          projId: row.projId,
          reviewId: row.reviewId
        }
      })
    },
    //提交评审资料
    submitFormFile(row) {
      console.log("---------")
      console.log(this.formEv)
      if (this.formEv.isGroupLeaders == 1 && row.status == 1) {
        lssue_of_tenderE(this.formEv).then(res => {
          console.log(res)
        })
      }
      console.log("---------")
      fileUpload(this.formEv).then(res => {
        console.log(res)
        this.openfile = false;
        this.getList();
        this.$message({
          message: '提交成功',
          type: 'success'
        });
      })
    },

    /**
     * downloadReview 下载评审资料
     */
    downloadReview(row) {
      listEvaluation(row).then(res => {
        console.log(res)
        download.resource(res.filePath)
      })
    },
    submitReview(row) {
      if (row.isGroupLeaders == 1 && row.status == 1) {
        console.log("提交最终评审报告")
        this.openfile = true;
        this.formEv = row;
        console.log("this.formEv")
        console.log(this.formEv)
        this.title = "请提交最终评审报告"
      } else {
        console.log("提交评审资料")
        this.openfile = true;
        this.formEv = row;
        console.log("this.formEv")
        console.log(this.formEv)
        console.log(row)

        this.title = "请提交评审资料"
      }


    },

    /**
     *签到
     */
    signIn(row) {
      console.log(row)
      if (row.signIn < 2) {
        row.signIn++;
      }
      updateSignIn(row).then(res => {
        this.$message.success("签到成功")

      })
    },
    /** 查询评审专家信息列表 */
    getList() {
      this.loading = true;
      listEvaluation_experts_informationBYUserId(this.queryParams).then(response => {
        this.evaluation_experts_informationList = response.rows;
        console.log(this.evaluation_experts_informationList)
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.openfile = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        reviewId: null,
        projId: null,
        userId: null,
        signIn: null,
        fileId: null,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        isDeleted: null,
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
      this.ids = selection.map(item => item.reviewId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加评审专家信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const reviewId = row.reviewId || this.ids
      getEvaluation_experts_information(reviewId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改评审专家信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.reviewId != null) {
            updateEvaluation_experts_information(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEvaluation_experts_information(this.form).then(response => {
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
      const reviewIds = row.reviewId || this.ids;
      this.$modal.confirm('是否确认删除评审专家信息编号为"' + reviewIds + '"的数据项？').then(function () {
        return delEvaluation_experts_information(reviewIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/evaluation_experts_information/export', {
        ...this.queryParams
      }, `evaluation_experts_information_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
