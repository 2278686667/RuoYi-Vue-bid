<template>
  <div class="app-container">
    <el-row>
      <el-col :span="4" :xs="24">
      <el-input
        placeholder="输入关键字进行过滤"
        v-model="filterText">
      </el-input>

      <el-tree
        class="filter-tree"
        :data="deptOptions"
        :props="defaultProps"
        :expand-on-click-node="false"
        :filter-node-method="filterNode"
        highlight-current
        expand-on-click-node="false"
        accordion="true"
        node-key="id"
        @node-click="handleNodeClick"
        icon-class="el-icon-folder-opened"
        ref="tree">
      </el-tree>
      </el-col>
      <el-col :span="20" :xs="24">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="父级文件夹id" prop="parentId">
          <el-input
            v-model="queryParams.parentId"
            placeholder="请输入父级文件夹id"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="项目id" prop="projId">
          <el-input
            v-model="queryParams.projId"
            placeholder="请输入项目id"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="文件夹名称" prop="folderName">
          <el-input
            v-model="queryParams.folderName"
            placeholder="请输入文件夹名称"
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
              v-hasPermi="['folder:folder:add']"
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
              v-hasPermi="['folder:folder:edit']"
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
              v-hasPermi="['folder:folder:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['folder:folder:export']"
            >导出</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="pigeonholeFile"
              v-hasPermi="['folder:folder:export']"
            >归档</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="folderList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="文件id" align="center" prop="fileId" />
          <el-table-column label="项目id" align="center" prop="projId" />
          <el-table-column label="文件夹id" align="center" prop="folderId" />
          <el-table-column label="文件夹名称" align="center" prop="fileName"  show-overflow-tooltip/>
          <el-table-column label="下载文件" align="center">
            <template slot-scope="scope">
              <a><p @click="downloadfile(scope.row)">下载文件</p></a>
            </template>
            </el-table-column>
          <el-table-column label="文件密码" align="center" prop="filePwd" />
          <el-table-column label="文件状态(0-未加密 1-已加密)" align="center" prop="status" />
          <el-table-column label="创建者" align="center" prop="createBy" />
          <el-table-column label="创建时间" align="center" prop="breateTime" />
          <el-table-column label="更新者" align="center" prop="updateBy" />
          <el-table-column label="更新时间" align="center" prop="updateTime" />
          <el-table-column label="是否删除(0-未删, 1-已删)" align="center" prop="isDeleted" >
            <template slot-scope="scope">
              <el-button type="success" size="mini" v-if="scope.row.isDeleted==0">
                未删除
              </el-button>
              <el-button type="warning" size="mini" v-if="scope.row.isDeleted==1">
                已删除
              </el-button>
            </template>

          </el-table-column>
          <el-table-column label="备注" align="center" prop="remark" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['folder:folder:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['folder:folder:remove']"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>

      </el-col>
    </el-row>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改文件夹结构信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="父级文件夹id" prop="parentId">
          <el-input v-model="form.parentId" placeholder="请输入父级文件夹id" />
        </el-form-item>
        <el-form-item label="项目id" prop="projId">
          <el-input v-model="form.projId" placeholder="请输入项目id" />
        </el-form-item>
        <el-form-item label="文件夹名称" prop="folderName">
          <el-input v-model="form.folderName" placeholder="请输入文件夹名称" />
        </el-form-item>
        <el-form-item label="文件存储路径" prop="folderPath">
          <el-input v-model="form.folderPath" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="是否删除(0-未删, 1-已删)" prop="isDeleted">
          <el-input v-model="form.isDeleted" placeholder="请输入是否删除(0-未删, 1-已删)" />
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
  </div>
</template>

<script>
import { listFolder, getFolder, delFolder, addFolder, updateFolder,folderTreeSelect } from "@/api/folder/folder";
import download from "../../../plugins/download";
export default {
  name: "Folder",
  data() {
    return {
      routeProjId:'',
      // 文件树选项
      deptOptions: undefined,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      nodeKey:[],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 文件夹结构信息表格数据
      folderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        parentId: null,
        projId: 0,
        folderName: null,
        folderPath: null,
        status: null,
        isDeleted: null,
        folderId:null

      },
      treeParams:{
        folderId: null,
        parentId:null,
        projId:null,
        folderName:null,
        folderPath:null,
        status:null,
        isDeleted:null
      },

      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ],
        isDeleted: [
          { required: true, message: "是否删除(0-未删, 1-已删)不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.queryParams.projId=this.$route.query.id
    this.treeParams.projId=this.$route.query.id
    this.getList();
    //树形结构
    this.getDeptTree(this.treeParams);
    console.log(this.treeParams)


  },
  methods: {
    /**
     * 归档
     */
    pigeonholeFile(){
      console.log("归档")
      console.log(this.queryParams)
    },

    /** 查询文件夹结构信息列表 */
    getList() {
      this.loading = true;


      listFolder(this.queryParams).then(response => {
        this.folderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    downloadfile(data){
      console.log(data.filePath)
      download.resource(data.filePath)
      console(data)
    },
    /**
     * 查询文件下拉树结构
     */

    getDeptTree(data){
      console.log(data)
      console.log("tree")
      folderTreeSelect(data).then(response=>{
        this.deptOptions=response.data;

        this.nodeKey.push(this.deptOptions[0].id)
      })
    },
    /**
     * 节点单击事件
     */
    handleNodeClick(data){
      this.queryParams.folderId=data.id
      this.handleQuery();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        folderId: null,
        parentId: null,
        projId: null,
        folderName: null,
        folderPath: null,
        status: "0",
        updateBy: null,
        createTime: null,
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
      this.ids = selection.map(item => item.folderId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加文件夹结构信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const folderId = row.folderId || this.ids
      getFolder(folderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改文件夹结构信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.folderId != null) {
            updateFolder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFolder(this.form).then(response => {
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
      const folderIds = row.folderId || this.ids;
      this.$modal.confirm('是否确认删除文件夹结构信息编号为"' + folderIds + '"的数据项？').then(function() {
        return delFolder(folderIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('folder/folder/export', {
        ...this.queryParams
      }, `folder_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
