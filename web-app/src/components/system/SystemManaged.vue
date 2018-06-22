<template lang="html">
  <div>
  <!--查尋條件-->
    <el-row>
    <el-form :inline="true" :model="form" class="demo-form-inline">
    <el-form-item :label="$t('constant.system.PROJECT_NAME')">
      <el-input v-model="form.projectName" :placeholder="$t('constant.system.PROJECT_NAME')"></el-input>
    </el-form-item>
    <el-form-item :label="$t('constant.system.SYSTEM_NAME')">
      <el-input v-model="form.systemName" :placeholder="$t('constant.system.SYSTEM_NAME')"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="query">{{$t('button.SEARCH')}}</el-button>
    </el-form-item>
    </el-form>
    </el-row>
    <el-row style="margin-bottom: 20px">
    <el-button type="primary" icon="el-icon-add" @click="showAddDialog">{{$t('button.ADD')}}</el-button>
    <el-button type="danger" icon="el-icon-delete" @click="showDeleteDialog">{{$t('button.DELETE')}}</el-button>
    </el-row>

    <el-table
      :data="tableData"
      v-loading="tableLoading"
      :element-loading-text="$t('message.LOADING')"
      @selection-change="handleSelectionChange"
      style="width: 100%; margin-top: 10px">
       <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column
        prop="projectName"
        :label="$t('constant.system.PROJECT_NAME')"
        >
      </el-table-column>
      <el-table-column
        prop="systemName"
        :label="$t('constant.system.SYSTEM_NAME')"
        >
      </el-table-column>
      <el-table-column
        prop="active"
        :label="$t('constant.system.ACTIVE')">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.active === 0" type="danger">{{$t('constant.FALSE')}}</el-tag>
          <el-tag v-else type="primary">{{$t('constant.TRUE')}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        :label="$t('constant.OPERATE')"
        width="300">
        <template slot-scope="scope" >
          <el-button size="small" @click="showEditDialog(scope.row)">{{$t('button.EDIT')}}</el-button>
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
      :title="$t('constant.HINT')"
      :visible.sync="deleteDialogShow"
      width="30%">
      <span>{{$t('constant.system.DELETE_SYSTEM_HINT')}}</span>
      <span slot="footer" class="dialog-footer">
            <el-button @click="deleteDialogShow = false">{{$t('button.CANCEL')}}</el-button>
            <el-button type="danger" :loading="deleteDialogLoading" @click="deleteDialogClick">{{$t('button.SURE')}}</el-button>
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
              self.$notify.success(self.$t('constant.system.DELETE_SYSTEM_SUCCESS_NOTIFY'))
              self.loadTable()
              self.deleteDialogShow = false
            } else {
              self.$notify.error(self.$t('constant.system.DELETE_SYSTEM_FAILED_NOTIFY'))
            }
            self.deleteDialogLoading = false
          })
          .catch(() => {
            self.deleteDialogLoading = false
          })
      } else {
        self.$notify.warning(self.$t('constant.system.DELETE_SYSTEM_WARN_NOTIFY'))
        self.deleteDialogShow = false
      }
    }
  }
}
</script>
