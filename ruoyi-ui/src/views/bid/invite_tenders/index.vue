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
      <el-table-column label="文件柜" align="center" >
        <template slot-scope="scope">
          <router-link
            :to="`/bid/folder?id=${scope.row.projId}`">
          <i class="el-icon-folder-opened" @click="fileClick">

        </i></router-link>
        </template>
      </el-table-column>
      <el-table-column label="项目id" align="center" prop="projId" />
      <el-table-column label="项目名称" align="center" prop="projName" />
      <el-table-column label="招标编号" align="center" prop="projNumber" />
      <el-table-column label="采购代理机构" align="center" prop="purchaserOrg" />
      <el-table-column label="采购人" align="center" prop="purchaser" />
<!--      <el-table-column label="招标文件" prop="projTender">-->
<!--        <el-upload style="margin-top: -30px"-->
<!--          :on-preview="handlePreview"-->
<!--          multiple-->
<!--          :file-list="fileList">-->
<!--        </el-upload>-->
<!--        <template slot-scope="scope">-->
<!--          <p @click="downloadfile(scope.row.projTender)">{{invite_tendersList[0].projTender.substring(invite_tendersList[0].projTender.lastIndexOf('/')+1)}}</p>-->
<!--        </template>-->

<!--      </el-table-column>-->


<!--      <el-table-column label="投标文件模板" align="center" prop="tenderTemp" >-->
<!--        <template slot-scope="scope">-->
<!--          <p @click="downloadfile(scope.row.tenderTemp)">{{invite_tendersList[0].tenderTemp.substring(invite_tendersList[0].tenderTemp.lastIndexOf('/')+1)}}</p>-->
<!--        </template>-->
<!--        <a v-if="tenderTemp!=null" :href="'http://localhost:8080'+invite_tendersList[0].tenderTemp">{{ (invite_tendersList[0].tenderTemp.substring(invite_tendersList[0].tenderTemp.lastIndexOf('/')+1))}}</a>-->
<!--      </el-table-column>-->
      <el-table-column label="提取码" align="center" prop="projPwd">
        <template slot-scope="scope">
          <el-button size="mini" @click="tiquma(scope.row)">提取码</el-button>
        </template>

      </el-table-column>
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
          <el-button v-if="scope.row.status==0"
            size="mini"
            type="test"
            icon="el-icon-position"
            @click="lssue_of_tender(scope.row)"
          >发售
          </el-button>
          <el-button v-if="scope.row.status==0"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['bid:invite_tenders:edit']"
          >修改</el-button>
          <el-button v-if="scope.row.status<2"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bid:invite_tenders:remove']"
          >删除</el-button>
          <el-button v-if="scope.row.status==2"
                     size="mini"
                     type="text"
                     icon="el-icon-s-marketing"
                     @click="pingbiao(scope.row)"
                     v-hasPermi="['bid:invite_tenders:remove']"
          >评标</el-button>
          <el-button v-if="scope.row.status>=2"
                     size="mini"
                     type="text"
                     icon="el-icon-delete"
                     @click="zhongzhi(scope.row)"
                     v-hasPermi="['bid:invite_tenders:remove']"
          >终止</el-button>
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
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
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

<!--    评标对话框-->
    <el-dialog :title="title" :visible.sync="pingbiaoopen" width="500px">
      <el-form :model="form" :rules="rulesping">
        <el-form-item label="项目名称" label-width="120px">
          <el-input v-model="form.projName" ></el-input>
        </el-form-item>
        <el-form-item label="评标文件" label-width="120px">
          <file-upload v-model="form.pingbiaowj"/>
        </el-form-item>
        <el-form-item label="本次评标需要" label-width="120px" prop="count">
          <el-input v-model.nmber="form.count" autocomplete="off" style="width: 50px"></el-input>名专家
        </el-form-item>
        <el-form-item label="评审专家授权" label-width="120px">
          <el-select v-model="form.auth" multiple placeholder="请选择">
            <el-option
              v-for="item in randomFormData"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId">
            </el-option>
          </el-select>

          <el-button @click="random">随机生成</el-button>
        </el-form-item>
        <el-form-item label="评委组长授权" label-width="120px">
          <el-select v-model="form.groupLeaders" placeholder="请选择">
            <el-option
              v-for="item in randomFormData"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="tiqumaOpen" width="500px">
      <el-form :model="form">
        <el-form-item label="项目名称"  label-width="120px">
          <el-input v-model="form.projName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="项目编号"  label-width="120px">
          <el-input v-model="form.projNumber" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="提取码" label-width="120px">
          <el-input v-model="form.projPwd" ></el-input>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {
  listInvite_tenders,
  getInvite_tenders,
  delInvite_tenders,
  addInvite_tenders,
  updateInvite_tenders,
  lssue_of_tender,
  startEvaluation,
  randomExpert
} from "@/api/bid/invite_tenders";
import {getToken} from "@/utils/auth";

export default {
  name: "Invite_tenders",
  data() {
    return {
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
      tiqumaOpen:false,
      pingbiaoopen:false,
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
      randomData:{
        data:null
      },
      randomFormData:{

      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      },
      rulesping:{
        count:[
          { required: true, message: "评标专家数量不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {

    this.getList();

  },
  methods: {
  //提取码
    tiquma(row){
      this.form=row
      this.title='提取码';
      this.tiqumaOpen=true;
    },
    //终止评标
    zhongzhi(row){
      this.$confirm('此操作将终止该项目, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '终止成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消终止操作'
        });
      });
    },
    //评标
    dialogFormVisible(){
      console.log("评标")
      console.log(this.form);
      startEvaluation(this.form).then(res=>{

        var v={'projId':this.form.projId,'status':this.form.status,'projEnd':this.form.projEnd}
        lssue_of_tender(v).then(res=>{
          this.$message.success("评标成功")
          this.getList();
          this.pingbiaoopen=false
        })
      })
    },
    //随机生成
    random(randomData){
      randomData.data=this.form.count
      console.log(randomData)

      randomExpert(randomData).then(res=>{
        this.randomFormData=res.data;
      })
    },
    lssue_of_tender(row){
      lssue_of_tender(row).then(response=>{

        this.$modal.msgSuccess(response.msg);
        this.getList()


      })
    },
    fileClick(){
      console.log("文件柜")
    },
    downloadfile(s){

      window.location.href="http://localhost:8080"+s
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
      listInvite_tenders(this.queryParams).then(response => {
        this.invite_tendersList = response.rows;
        for (let i = 0; i <this.invite_tendersList; i++) {
          this.invite_tendersList.projTender[i]=(this.invite_tendersList[i].substring(this.invite_tendersList[i].lastIndexOf('/')+1));
        }
        this.total = response.total;
        this.loading = false;
        this.sss();

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
