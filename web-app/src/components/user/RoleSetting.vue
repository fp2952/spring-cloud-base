<template lang="html">
  <el-dialog :title="$t('constant.user.ROLE_SET')" :visible.sync="roleSettingShow">
    <el-transfer v-model="selected" :data="roles" :titles="[$t('constant.user.ROLE_LIST'), $t('constant.user.AUTH_ROLE')]"></el-transfer>
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
      roles: [],
      selected: [],
      roleSettingShow: false,
      roleSettingLoading: false,
      userId: ''
    }
  },
  created () {
    this.getAllRole()
  },
  methods: {
    show (row) {
      this.roleSettingShow = true
      this.userId = row.id
      this.getUserRole()
    },
    // 获取所有角色
    getAllRole () {
      var self = this
      self.$http.get(`${DataMainApi}/role`, self.form)
        .then(res => {
          if (res.data.code === Status.success) {
            self.roles = res.data.data.map(
              item => ({
                key: item.id,
                label: item.roleName
              })
            )
          }
        })
    },
    // 获取已授权角色
    getUserRole () {
      var self = this
      self.$http.get(`${DataMainApi}/user/role/${self.userId}`)
        .then(res => {
          if (res.data.code === Status.success) {
            self.selected = res.data.data.map(item => item.roleId)
          }
        })
    },
    saveRoleSetting () {
      var self = this
      // 校验表单
      self.roleSettingLoading = true
      var userRole = self.selected.map(item => ({
        userId: self.userId,
        roleId: item
      }))
      self.$http.post(`${DataMainApi}/user/role`, userRole)
        .then(res => {
          if (res.data.code === Status.success) {
            self.$notify.success(self.$t('constant.user.SAVE_USER_ROLE_SUCCESS_NOTIFY'))
            self.roleSettingShow = false
            // 触发事件
            self.$emit('success')
          } else {
            self.$notify.error(self.$t('constant.user.SAVE_USER_ROLE_FAILED_NOTIFY'))
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
