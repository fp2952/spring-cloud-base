<template lang="html">
  <div>
  <!--查尋條件-->
    <el-row>
    <el-form :inline="true" :model="form" class="demo-form-inline">
    <el-form-item label="用户名">
      <el-input v-model="form.userName" placeholder="用户名"></el-input>
    </el-form-item>
    <el-form-item label="手机号码">
      <el-input v-model="form.phone" placeholder="手机号码"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="query">查询</el-button>
    </el-form-item>
    </el-form>
    </el-row>
    <el-row style="margin-bottom: 20px">
    <el-button type="primary" icon="el-icon-add" @click="showAddDialog">新增</el-button>
    <el-button type="warning" icon="el-icon-setting" @click="showResetDialog">重置密码</el-button>
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
        prop="userName"
        label="用户名"
        >
      </el-table-column>
      <el-table-column
        prop="phone"
        label="手机号码"
        >
      </el-table-column>
      <el-table-column
        prop="age"
        label="年龄"
      >
      </el-table-column>
      <el-table-column
        prop="gender"
        label="性别">
        <template scope="scope">
            <el-tag v-if="scope.row.gender === 'female'" type="danger">女</el-tag>
            <el-tag v-else type="primary">男</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="active"
        label="是否启用">
        <template scope="scope">
          <el-tag v-if="scope.row.active === 0" type="danger">否</el-tag>
          <el-tag v-else type="primary">是</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="300">
        <template scope="scope" >
          <el-button size="small" @click="showEditDialog(scope.row)">编辑</el-button>
          <el-button type="warning" size="small" @click="showRoleSetDialog(scope.row)">角色配置</el-button>
          <el-button type="danger" size="small" @click="showResetDialogByCol(scope.row)">重置密码</el-button>
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
    <!--重置密码-->
    <el-dialog
      title="提示"
      :visible.sync="resetDialogShow"
      width="30%">
      <span>确定重置密码为 000000 ？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetDialogShow = false">取 消</el-button>
        <el-button type="danger" :loading="resetDialogLoading" @click="resetDialogClick">确 定</el-button>
      </span>
    </el-dialog>
    <!--删除用户-->
    <el-dialog
      title="提示"
      :visible.sync="deleteDialogShow"
      width="30%">
      <span>确定删除选中用户？</span>
      <span slot="footer" class="dialog-footer">
            <el-button @click="deleteDialogShow = false">取 消</el-button>
            <el-button type="danger" :loading="deleteDialogLoading" @click="deleteDialogClick">确 定</el-button>
          </span>
    </el-dialog>
    <!--新增用户表单-->
    <add-user ref="addUser" @success="loadTable"></add-user>
    <!--编辑用户表单-->
    <edit-user ref="editUser" @success="loadTable"></edit-user>
    <!--设置角色-->
    <role-set ref="roleSetting"></role-set>
  </div>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
import UserAddForm from './UserAdd.vue'
import UserEditFrom from './UserEdit.vue'
import RoleSetting from './RoleSetting.vue'
export default {
  components: {
    'add-user': UserAddForm,
    'edit-user': UserEditFrom,
    'role-set': RoleSetting
  },
  created () {
    // 加载表格数据
    this.loadTable()
  },
  data () {
    return {
      form: {
        userName: null,
        phone: null,
        pageNum: 1,
        pageSize: 10
      },
      // 表格数据
      tableData: [],
      tableTotal: 0,
      tableLoading: false,
      // 重置密码框
      resetDialogShow: false,
      // 重置按钮loading
      resetDialogLoading: false,
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
      this.$http.post(DataMainApi + '/user/table', self.form)
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
    handleSizeChange (val) {
      this.form.pageSize = val
      this.loadTable()
    },
    handleCurrentChange (val) {
      this.form.pageNum = val
      this.loadTable()
    },
    showResetDialog () {
      this.resetDialogShow = true
    },
    showDeleteDialog () {
      this.deleteDialogShow = true
    },
    showResetDialogByCol (row) {
      this.selectData = [row]
      this.resetDialogShow = true
    },
    showAddDialog () {
      this.$refs.addUser.show()
    },
    // 表格多选
    handleSelectionChange (row) {
      this.selectData = row
    },
    showEditDialog (row) {
      this.$refs.editUser.show(row)
    },
    showRoleSetDialog (row) {
      this.$refs.roleSetting.show(row)
    },
    // 重置密码
    resetDialogClick () {
      var self = this
      if (this.selectData.length > 0) {
        this.resetDialogLoading = true
        this.$http.post(DataMainApi + '/user/password/000000', this.selectData)
          .then(res => {
            if (res.data.code === Status.success) {
              self.$notify.success('重置密码成功')
              self.loadTable()
              self.resetDialogShow = false
            } else {
              self.$notify.error('重置密码失败')
            }
            self.resetDialogLoading = false
          })
          .catch(() => {
            self.resetDialogLoading = false
          })
      } else {
        self.$notify.warning('请选择需要重置密码的用户')
        self.resetDialogShow = false
      }
    },
    // 删除用户
    deleteDialogClick () {
      var self = this
      if (this.selectData.length > 0) {
        this.deleteDialogLoading = true
        this.$http.delete(DataMainApi + '/user', {data: self.selectData})
          .then(res => {
            if (res.data.code === Status.success) {
              self.$notify.success('删除用户成功')
              self.loadTable()
              self.deleteDialogShow = false
            } else {
              self.$notify.error('删除用户失败')
            }
            self.deleteDialogLoading = false
          })
          .catch(() => {
            self.deleteDialogLoading = false
          })
      } else {
        self.$notify.warning('请选择需要删除的用户')
        self.deleteDialogShow = false
      }
    }
  }
}
</script>
