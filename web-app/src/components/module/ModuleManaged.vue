<template lang="html">
  <el-row style="margin: 20px;">
    <el-col :span="6"
    v-loading="treeLoading"
    :element-loading-text="$t('message.LOADING')">
      <el-tree style="overflow: auto;height: 700px"
      :data="systemData"
      node-key="id"
      ref="tree"
      lazy
      highlight-current
      :default-expanded-keys="expandedKeys"
      :props="treeProps"
      :load="loadSubModule"
      @node-click="selectNode">
      </el-tree>
    </el-col>
    <el-col :span="17" :offset="1">
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
          prop="moduleName"
          :label="$t('constant.module.MODULE_NAME')">
        </el-table-column>
        <el-table-column
           prop="moduleCode"
           :label="$t('constant.module.MODULE_CODE')">
        </el-table-column>
        <el-table-column
           prop="modulePath"
           :label="$t('constant.module.MODULE_PATH')">
        </el-table-column>
        <el-table-column
           prop="sort"
           :label="$t('constant.module.SORT')">
        </el-table-column>
        <el-table-column :label="$t('constant.OPERATE')">
          <template slot-scope="scope">
            <el-button size="small" @click="showEditDialog(scope.row)">{{$t('button.EDIT')}}</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-row style="text-align: center; margin-top: 20px">
      <el-col :span="24">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryForm.pageNum"
          :page-sizes="[5, 10, 20, 50]"
          :page-size="queryForm.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="tableTotal">
        </el-pagination>
      </el-col>
      </el-row>
    </el-col>
    <!--删除模块-->
    <el-dialog
      :title="$t('constant.HINT')"
      :visible.sync="deleteDialogShow"
      width="30%">
      <span>{{$t('constant.module.DELETE_MODULE_HINT')}}</span>
      <span slot="footer" class="dialog-footer">
                <el-button @click="deleteDialogShow = false">{{$t('button.CANCEL')}}</el-button>
                <el-button type="danger" :loading="deleteDialogLoading" @click="deleteDialogClick">{{$t('button.SURE')}}</el-button>
              </span>
    </el-dialog>
    <!--新增模块表单-->
    <module-add ref="addModule" @success="reLoadTreeAndTable"></module-add>
    <!--编辑模块表单-->
    <module-edit ref="editModule" @success="reLoadTreeAndTable">
    </module-edit>
  </el-row>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
import ModuleAdd from './ModuleAdd.vue'
import ModuleEdit from './ModuleEdit.vue'
export default {
  components: {
    'module-add': ModuleAdd,
    'module-edit': ModuleEdit
  },
  data () {
    return {
      // 根节点系统
      systemData: [],
      // 默认展开
      expandedKeys: [],
      treeProps: {
        label: 'moduleName',
        children: 'subModule',
        isLeaf: 'leaf'
      },
      treeLoading: false,
      selectedNode: {},
      selectedRow: {},
      queryForm: {
        pageNum: 1,
        pageSize: 5,
        parentId: null,
        systemId: null
      },
      tableData: [],
      tableLoading: false,
      tableTotal: 0,
      // 选中行
      selectData: [],
      deleteDialogShow: false,
      deleteDialogLoading: false,
      currentModule: {
        moduleId: null,
        moduleName: null,
        systemId: null,
        systemName: null,
        key: null
      }
    }
  },
  created () {
    // 初始化模块数
    this.loadSystem(true)
  },
  methods: {
    // 加载系统根节点
    loadSystem (first) {
      var self = this
      self.treeLoading = true
      this.$http.get(`${DataMainApi}/system`)
        .then(res => {
          if (res.data.code === Status.success) {
            self.systemData = res.data.data.map(e => ({
              id: e.id,
              moduleName: e.systemName,
              leaf: false
            }))
            setTimeout(function () {
              self.treeLoading = false
            }, 500)
            if (first) {
              // 加载首个系统的表格
              self.queryForm.systemId = self.systemData[0].id
              self.loadTable()
              // 设置初始选中
              self.currentModule.key = self.systemData[0].id
            }
          } else {
            self.$notify.error(self.$t('constant.module.LOAD_MODULE_TREE_FAILED_NOTIFY'))
          }
        })
    },
    // 模块树的子节点加载方法
    loadSubModule (node, resolve) {
      // 根节点
      if (node.level === 0) {
        return
      }
      var self = this
      var query
      // 加载系统下节点
      if (node.level === 1) {
        query = {id: 'root', systemId: node.key}
      } else {
        query = {id: node.key}
      }
      self.treeLoading = true
      this.$http.post(`${DataMainApi}/module/tree`, query).then(res => {
        if (res.data.code === Status.success) {
          setTimeout(function () {
            self.treeLoading = false
          }, 500)
          return resolve(res.data.data.map(e => ({
            id: e.id,
            moduleName: e.moduleName,
            subModule: e.subModule,
            leaf: e.subModules.length === 0
          })))
        } else {
          self.$notify.error(self.$t('constant.module.LOAD_MODULE_TREE_FAILED_NOTIFY'))
        }
      })
    },
    // 模块树节点点击事件
    selectNode (data, node) {
      console.log(node)
      var self = this
      // 如果点击的是系统节点
      if (node.level === 1) {
        // 设置表格查询条件
        self.queryForm.systemId = data.id
        self.queryForm.parentId = null
      } else {
        // 设置表格查询条件
        self.queryForm.systemId = null
        self.queryForm.parentId = data.id
      }
      // 当前选中模块
      self.currentModule.key = data.id
      self.queryForm.pageNum = 1
      self.loadTable()
    },
    loadTable () {
      var self = this
      self.tableLoading = true
      this.$http.post(`${DataMainApi}/module/table`, self.queryForm)
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
      this.queryForm.pageSize = val
      this.loadTable()
    },
    handleCurrentChange (val) {
      this.queryForm.pageNum = val
      this.loadTable()
    },
    // 表格多选
    handleSelectionChange (row) {
      this.selectData = row
    },
    // 设置树展开
    getExpandedKeys (parent) {
      // 首次调用
      if (!parent) {
        this.expandedKeys = []
        this.expandedKeys.push(this.currentModule.key)
        parent = this.$refs.tree.getNode(this.currentModule.key)
      } else {
        this.expandedKeys.push(parent.key)
      }
      if (parent.parent.level !== 0) {
        this.getExpandedKeys(parent.parent)
      } else {
        // 递归完毕
        this.expandedKeys.reverse()
      }
    },
    // 获取选中节点信息
    getCurrentNode (parent) {
      // 首次调用
      if (!parent) {
        parent = this.$refs.tree.getNode(this.currentModule.key)
        this.currentModule.moduleId = parent.key
        this.currentModule.moduleName = parent.label
      }
      if (parent.parent.level !== 0) {
        this.getCurrentNode(parent.parent)
      } else {
        // 递归完毕
        this.currentModule.systemId = parent.key
        this.currentModule.systemName = parent.label
        // 判断模块id是否等于系统id
        if (parent.key === this.currentModule.moduleId) {
          this.currentModule.moduleId = null
          this.currentModule.moduleName = null
        }
      }
    },
    // 新增模块
    showAddDialog () {
      this.getCurrentNode()
      this.$refs.addModule.show(this.currentModule)
    },
    // 编辑模块
    showEditDialog (row) {
      this.getCurrentNode()
      this.$refs.editModule.show(row, this.currentModule)
    },
    showDeleteDialog () {
      this.deleteDialogShow = true
    },
    // 删除模块
    deleteDialogClick () {
      var self = this
      if (this.selectData.length > 0) {
        this.deleteDialogLoading = true
        this.$http.delete(`${DataMainApi}/module`, {data: self.selectData})
          .then(res => {
            if (res.data.code === Status.success) {
              self.$notify.success(self.$t('constant.module.DELETE_MODULE_SUCCESS_NOTIFY'))
              self.reLoadTreeAndTable()
              self.deleteDialogShow = false
            } else {
              self.$notify.error(self.$t('constant.module.DELETE_MODULE_FAILED_NOTIFY'))
            }
            self.deleteDialogLoading = false
          })
          .catch(() => {
            self.deleteDialogLoading = false
          })
      } else {
        self.$notify.warning(self.$t('constant.module.DELETE_MODULE_WARN_NOTIFY'))
        self.deleteDialogShow = false
      }
    },
    reLoadTreeAndTable () {
      this.loadSystem()
      this.getExpandedKeys()
      this.loadTable()
    }
  }
}
</script>
