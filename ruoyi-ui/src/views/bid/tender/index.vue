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
      <el-form-item label="采购代理机构" prop="purchaserOrg">
        <el-input
          v-model="queryParams.purchaserOrg"
          placeholder="请输入采购代理机构"
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

      <el-form-item label="投标截止时间" prop="projEnd">
        <el-date-picker clearable
                        v-model="queryParams.projEnd"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择投标截止时间">
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
          v-hasPermi="['bid:invite_tenders:add']"
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
          v-hasPermi="['bid:invite_tenders:edit']"
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
          v-hasPermi="['bid:invite_tenders:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['bid:invite_tenders:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="invite_tendersList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="项目id" align="center" prop="projId" />
      <el-table-column label="项目名称" align="center" prop="projName" />
      <el-table-column label="招标编号" align="center" prop="projNumber" />
      <el-table-column label="采购代理机构" align="center" prop="purchaserOrg" />
      <el-table-column label="采购人" align="center" prop="purchaser" />
      <el-table-column label="投标截止时间" align="center" prop="projEnd" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.projEnd, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="项目状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" v-if="scope.row.status==0">待发售</el-button>
          <el-button type="success" size="mini" v-if="scope.row.status==1">发售中</el-button>
          <el-button type="success" size="mini" v-if="scope.row.status==2">开标中</el-button>
          <el-button type="success" size="mini" v-if="scope.row.status==3">待评标</el-button>
          <el-button type="success" size="mini" v-if="scope.row.status==4">评审中</el-button>
          <el-button type="success" size="mini" v-if="scope.row.status==5">评审完成</el-button>
          <el-button type="success" size="mini" v-if="scope.row.status==6">已完成</el-button>
          <el-button type="success" size="mini" v-if="scope.row.status>=7">已终止</el-button>
        </template>

      </el-table-column>

      <el-table-column label="备注" align="center" >
        <template slot-scope="scope">
          <p v-html="scope.row.remark"></p>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status==1"
                     size="mini"
                     type="danger"
                     icon="el-icon-position"
                     @click="toubiao(scope.row)"
          >我要投标
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

    <!-- 添加或修改招投标对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" prop="projName">
          <el-input v-model="form.projName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="招标编号" prop="projNumber">
          <el-input v-model="form.projNumber" placeholder="请输入招标编号" />
        </el-form-item>
        <el-form-item label="采购代理机构" prop="purchaserOrg">
          <el-input v-model="form.purchaserOrg" placeholder="请输入采购代理机构" />
        </el-form-item>
        <el-form-item label="采购人" prop="purchaser">
          <el-input v-model="form.purchaser" placeholder="请输入采购人" />
        </el-form-item>
        <el-form-item label="招标文件">
          <file-upload v-model="form.projTender"/>
        </el-form-item>
        <el-form-item label="投标文件模板">
          <file-upload v-model="form.tenderTemp"/>
        </el-form-item>
        <el-form-item label="提取码" prop="projPwd">
          <el-input v-model="form.projPwd" placeholder="请输入提取码" />
        </el-form-item>
        <el-form-item label="投标截止时间" prop="projEnd">
          <el-date-picker clearable
                          v-model="form.projEnd"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择投标截止时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注">
          <editor v-model="form.remark" :min-height="192"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--    投标对话框-->
    <el-dialog :title="title" :visible.sync="toubiaoduihuakuang" width="500px">
      <el-form ref="form" :model="form" :rules="rules">

        <el-form-item label="项目名称" label-width="120px">
          <el-input v-model="form.projName" ></el-input>
        </el-form-item>

        <el-form-item label="项目编号" label-width="120px">

          <el-input v-model="form.projNumber" ></el-input>
        </el-form-item>
        <el-form-item label="投标公司名称" label-width="120px" >
          <el-input v-model.nmber="form.bidderCompany" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="招标文件提取码" label-width="120px" >
          <el-input v-model="form.projPwd" type="password" autocomplete="off"></el-input>
          <el-button  @click="tqwj($event)" style="margin-top: 10px">提取文件</el-button>
        </el-form-item>
        <el-form-item label="招标文件" label-width="120px" >
            <el-button
              size="mini"
              type="text"
              icon="el-icon-download"
              @click="downloadFile($event)"
            >下载投标文件
            </el-button>
        </el-form-item>
        <el-form-item label="招标文件模板" label-width="120px" >
          <template slot-scope="scope">
            {{scope.row}}
            <el-button
              size="mini"
              type="text"
              icon="el-icon-download"
              @click="downloadFile($event)"

            >下载投标文件模板
            </el-button>
          </template>
        </el-form-item>
        <p  style="color: #ff4949;font-size: 16px">
          提取文件后请点击"确定"按钮，进行公司投标登记，填写好标书后到"查看我的投标项自中进行文件上传!
        </p>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="toubiaotj">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProject, getProject, delProject, addProject, updateProject } from "@/api/tender/project";
import {
  listInvite_tenders,

  delInvite_tenders,
  addInvite_tenders,
  updateInvite_tenders,
  lssue_of_tender
} from "@/api/bid/invite_tenders";

import {
  listTender,
  getInvite_tenders,
  deliver,
} from "@/api/bid/tender.js";
import download from "../../../plugins/download";

/**
 * 浏览投标信息
 */
export default {
  name: "Invite_tenders",
  data() {
    return {
      tqwjdata:false,
      updateUrl:process.env.VUE_APP_BASE_API+"/bid/invite_tenders/uploadFile",
      fileList: [],
      number: 0,
      uploadList: [],
      baseUrl: process.env.VUE_APP_BASE_API,
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传文件服务器地址

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
      // 招投标表格数据
      invite_tendersList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      toubiaoduihuakuang:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projName: null,
        projNumber: null,
        purchaserOrg: null,
        purchaser: null,
        projTender: null,
        tenderTemp: null,
        projPwd: null,
        projEnd: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        // createTime: [
        //   { required: true, message: "创建时间不能为空", trigger: "blur" }
        // ],
        count:[
          { required: true, message: "请输入投标公司名称", trigger: 'blur' }
        ],
        jobName:[
          { required: true, message: "请输入提取码", trigger: "blur" }
        ]
      },
      rulesping:{

      }
    };
  },
  created() {
    this.getList();
    console.log(this.invite_tendersList.projId)

  },
  methods: {

    tqwj(data){
      // if (data.target.innerText===this.form.projPwd){
      //   this.tqwjdata=true
      // }else {
        this.tqwjdata=true
        this.$message.success('提取成功');
        console.log(this.form)
      // }
    },
    //下載投標文件
    downloadFile(data){
      if (data.target.innerText==="下载投标文件"&&this.tqwjdata===true){
        console.log("下载投标文件")
        download.name(this.form.projTender);
      }else if (data.target.innerText==="下载投标文件模板"&&this.tqwjdata===true){
        download.name(this.form.tendeTemp);
      }else {
      }

    },
    /**
     * 投标按钮操作
     * @param row
     */
    toubiao(row){
      this.reset();

      const projId = row.projId || this.ids
      getInvite_tenders(projId).then(response => {
        this.form = response.data;
        this.toubiaoduihuakuang = true;
        this.title = "项目投标";

      });
    },
    fileClick(){
      console.log("文件柜")
    },
    sss(){
      this.fileList.push({name:this.invite_tendersList[0].projTender.substring(this.invite_tendersList[0].projTender.lastIndexOf('/')+1),url:"http://localhost:8080"+this.invite_tendersList[0].projTender})

    },
    handlePreview(){
    },
    /** 查询招投标列表 */
    getList() {
      this.loading = true;
      console.log(this.queryParams)
      listTender(this.queryParams).then(response => {
        this.invite_tendersList = response.rows;
        this.total = response.total;
        this.loading = false;
        this.sss();

      });
    },
    // 取消按钮
    cancel() {
      this.toubiaoduihuakuang = false;
      console.log("取消按钮")
    },
    //确定按钮
    toubiaotj(){
      addProject(this.form).then(res=>{
        this.$modal.msgSuccess("投标成功");
        this.toubiaoduihuakuang = false;
        this.getList();
      })
    },
    // 表单重置
    reset() {
      this.form = {
        projId: null,
        projName: null,
        projNumber: null,
        purchaserOrg: null,
        purchaser: null,
        projTender: null,
        tenderTemp: null,
        projPwd: null,
        projEnd: null,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
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
      this.ids = selection.map(item => item.projId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加招投标";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.reset
      const projId = row.projId || this.ids
      getInvite_tenders(projId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改招投标";

      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.projId != null) {
            updateInvite_tenders(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInvite_tenders(this.form).then(response => {
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
      const projIds = row.projId || this.ids;
      this.$modal.confirm('是否确认删除招投标编号为"' + projIds + '"的数据项？').then(function() {
        return delInvite_tenders(projIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    pingbiao(row){
      this.reset();
      const projId = row.projId || this.ids
      getInvite_tenders(projId).then(response => {
        this.form = response.data;
        this.pingbiaoopen = true;
        this.title = "评标";

      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('bid/invite_tenders/export', {
        ...this.queryParams
      }, `invite_tenders_${new Date().getTime()}.xlsx`)
    },

  }
};
</script>
