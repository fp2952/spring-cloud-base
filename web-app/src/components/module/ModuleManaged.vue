<template lang="html">
<el-row>
  <el-col :span="6"
  v-loading="treeLoading"
  element-loading-text="加载中">
    <el-tree
    :data="systemData"
    node-key="id"
    ref="tree"
    lazy
    highlight-current
    :expand-on-click-node="false"
    :default-expanded-keys="expandedKeys"
    :props="treeProps"
    :load="loadSubModule"
    @node-click="selectNode">
    </el-tree>
  </el-col>
  <el-col :span="17" :offset="1">
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
        prop="moduleName"
        label="模块名称">
      </el-table-column>
      <el-table-column
         prop="moduleCode"
         label="模块编码">
      </el-table-column>
      <el-table-column
         prop="modulePath"
         label="模块URL">
      </el-table-column>
      <el-table-column
         prop="sort"
         label="排序">
      </el-table-column>
      <el-table-column label="操作">
        <template scope="scope">
          <el-button size="small" @click="showEditDialog(scope.row)">编辑</el-button>
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
</el-row>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
export default {
  components: {
  },
  created () {
    // 初始化模块数
    this.loadSystem()
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
      tableTotal: 0
    }
  },
  methods: {
    // 加载系统根节点
    loadSystem () {
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
            // 加载首个系统的表格
            self.queryForm.systemId = self.systemData[0].id
            self.loadTable()
            // 设置默认展开
            self.expandedKeys = ['d69060a3-914b-11e7-8c99-00ff6227aaa1', '113e9c94-8405-11e7-b35a-00ff6227aaa1', 'e69131c2-870d-11e7-ad1e-00ff6227aaa1']
          } else {
            self.$notify.error('获取模块树失败')
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
          self.$notify.error('获取模块树失败')
        }
      })
    },
    // 模块树节点点击事件
    selectNode (data, node) {
      var self = this
      // 如果点击的是系统节点
      if (node.level === 1) {
        self.queryForm.systemId = data.id
        self.queryForm.parentId = null
      } else {
        self.queryForm.systemId = null
        self.queryForm.parentId = data.id
      }
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
  }
}
</script>
