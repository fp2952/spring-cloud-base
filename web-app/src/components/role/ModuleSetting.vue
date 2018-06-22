<template lang="html">
  <el-dialog :title="$t('constant.role.AUTHORITY_SET')" :visible.sync="roleSettingShow">
   <el-tree
      :data="modules"
      show-checkbox
      default-expand-all
      node-key="id"
      ref="tree"
      highlight-current
      :props="treeProps">
    </el-tree>
    <div slot="footer" class="dialog-footer">
      <el-button @click="roleSettingShow = false">{{$t('button.CANCEL')}}</el-button>
      <el-button type="primary" @click="saveRoleSetting" :loading="roleSettingLoading">{{$t('button.SURE')}}</el-button>
    </div>
  </el-dialog>
</template>
<script>
import {DataMainApi, Status} from '../ApiConstant'
export default {
  data () {
    return {
      modules: [],
      systemIds: [],
      roleSettingShow: false,
      roleSettingLoading: false,
      roleId: '',
      treeProps: {
        label: 'moduleName',
        children: 'subModules'
      }
    }
  },
  created () {
    this.loadTree()
  },
  methods: {
    show (row) {
      this.roleSettingShow = true
      this.roleId = row.id
      this.loadRoleAuthData()
    },
    loadTree () {
      var self = this
      self.$http.get(`${DataMainApi}/system/module`)
        .then(res => {
          if (res.data.code === Status.success) {
            self.modules = res.data.data
            self.systemIds = res.data.data.map(item => item.id)
          }
        })
    },
    // 获取已授权的数据
    loadRoleAuthData () {
      var self = this
      self.$http.get(`${DataMainApi}/role/auth/${self.roleId}`)
        .then(res => {
          if (res.data.code === Status.success) {
            self.$refs.tree.setCheckedKeys(res.data.data.map(item => item.moduleId))
          }
        })
    },
    saveRoleSetting () {
      var self = this
      self.roleSettingLoading = true
      // 获取全部选中节点key
      var keys = self.$refs.tree.getCheckedKeys().concat(self.$refs.tree.getHalfCheckedKeys())
      const difference = (a, b) => { const s = new Set(b); return a.filter(x => !s.has(x)) }
      // difference([1,2,3], [1,2]) -> [3]
      // 提交过滤掉系统节点的key
      self.$http.post(`${DataMainApi}/module/role`,
        difference(keys, self.systemIds).map(item => ({
          roleId: self.roleId,
          moduleId: item
        })))
        .then(res => {
          if (res.data.code === Status.success) {
            self.$notify.success(self.$t('constant.role.SAVE_MODULE_AUTH_SUCCESS_NOTIFY'))
            self.roleSettingShow = false
          } else {
            self.$notify.error(self.$t('constant.role.SAVE_MODULE_AUTH_FAILED_NOTIFY'))
          }
          self.roleSettingLoading = false
        })
        .catch(() => {
          self.roleSettingLoading = false
        })
    }
  }
}
</script>
