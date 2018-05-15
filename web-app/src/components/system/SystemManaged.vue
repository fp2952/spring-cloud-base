<template lang="html">
  <div>
  <!--查尋條件-->
    <el-row>
    <el-form :inline="true" :model="form" class="demo-form-inline">
    <el-form-item label="系统项目名">
      <el-input v-model="form.projectName" placeholder="系统项目名"></el-input>
    </el-form-item>
    <el-form-item label="系统名称">
      <el-input v-model="form.systemName" placeholder="系统名称"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="query">查询</el-button>
    </el-form-item>
    </el-form>
    </el-row>
    <el-row style="margin-bottom: 20px">
    <el-button type="primary" icon="el-icon-add" @click="showAddDialog">新增</el-button>
    <el-button type="danger" icon="el-icon-delete" @click="showDeleteDialog">删除</el-button>
    </el-row>

    <el-table
      :data="tableData"
      v-loading="tableLoading"
      element-loading-text="加载中"
      @selection-change="handleSelectionChange"
      style="width: 100%; margin-top: 10px">
       <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column
        prop="projectName"
        label="系统项目名"
        >
      </el-table-column>
      <el-table-column
        prop="systemName"
        label="系统名称"
        >
      </el-table-column>
      <el-table-column
        prop="active"
        label="是否启用">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.active === 0" type="danger">否</el-tag>
          <el-tag v-else type="primary">是</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="300">
        <template slot-scope="scope" >
          <el-button size="small" @click="showEditDialog(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-row style="text-align: center; margin-top: 20px">
    <el-col :span="24">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="form.pageNum"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="form.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableTotal">
      </el-pagination>
    </el-col>
    </el-row>
    <!--删除系统-->
    <el-dialog
      title="提示"
      :visible.sync="deleteDialogShow"
      width="30%">
      <span>确定删除选中系统？</span>
      <span slot="footer" class="dialog-footer">
            <el-button @click="deleteDialogShow = false">取 消</el-button>
            <el-button type="danger" :loading="deleteDialogLoading" @click="deleteDialogClick">确 定</el-button>
          </span>
    </el-dialog>
    <!--新增系统表单-->
    <add-system ref="addSystem" @success="loadTable"></add-system>
    <!--编辑系统表单-->
    <edit-system ref="editSystem" @success="loadTable"></edit-system>
  </div>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
import AddSystem from './SystemAdd.vue'
import EditSystem from './SystemEdit.vue'
export default {
  components: {
    'add-system': AddSystem,
    'edit-system': EditSystem
  },
  created () {
    // 加载表格数据
    this.loadTable()
  },
  data () {
    return {
      form: {
        projectName: null,
        systemName: null,
        pageNum: 1,
        pageSize: 10
      },
      // 表格数据
      tableData: [],
      tableTotal: 0,
      tableLoading: false,
      // 删除框
      deleteDialogShow: false,
      // 删除按钮loading
      deleteDialogLoading: false,
      // 选中行
      selectData: []
    }
  },
  methods: {
    // 查询数据
    query () {
      this.loadTable()
    },
    // 加载表格数据
    loadTable () {
      var self = this
      self.tableLoading = true
      this.$http.post(`${DataMainApi}/system/table`, self.form)
        .then(res => {
          if (res.data.code === Status.success) {
            self.tableData = res.data.data.rows
            self.tableTotal = res.data.data.total
          }
          setTimeout(function () {
            self.tableLoading = false
          }, 500)
        })
    },
    showDeleteDialog () {
      this.deleteDialogShow = true
    },
    // 表格多选
    handleSelectionChange (row) {
      this.selectData = row
    },
    showAddDialog () {
      this.$refs.addSystem.show()
    },
    handleSizeChange (val) {
      this.form.pageSize = val
      this.loadTable()
    },
    handleCurrentChange (val) {
      this.form.pageNum = val
      this.loadTable()
    },
    showEditDialog (row) {
      this.$refs.editSystem.show(row)
    },
    // 删除系统
    deleteDialogClick () {
      var self = this
      if (this.selectData.length > 0) {
        this.deleteDialogLoading = true
        this.$http.delete(`${DataMainApi}/system`, {data: self.selectData})
          .then(res => {
            if (res.data.code === Status.success) {
              self.$notify.success('删除系统成功')
              self.loadTable()
              self.deleteDialogShow = false
            } else {
              self.$notify.error('删除系统失败')
            }
            self.deleteDialogLoading = false
          })
          .catch(() => {
            self.deleteDialogLoading = false
          })
      } else {
        self.$notify.warning('请选择需要删除的系统')
        self.deleteDialogShow = false
      }
    }
  }
}
</script>
