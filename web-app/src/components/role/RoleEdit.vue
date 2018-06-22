<template lang="html">
  <el-dialog :title="$t('constant.role.EDIT_ROLE')" :visible.sync="editRoleShow">
    <el-form ref="roleEditForm" :model="form" label-width="100px" :rules="formRules" :inline="true">
      <el-row>
        <el-col :span="12">
          <el-form-item :label="$t('constant.role.ROLE_CODE')">
            <el-input v-model="form.roleCode" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item :label="$t('constant.role.ROLE_NAME')" prop="roleName">
            <el-input v-model="form.roleName" :placeholder="$t('constant.role.ROLE_NAME_PLACEHOLDER')"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
   </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="editRoleShow = false">{{$t('button.CANCEL')}}</el-button>
      <el-button type="primary" @click="saveRole" :loading="editRoleLoading">{{$t('button.SURE')}}</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
export default {
  data () {
    var self = this
    return {
      form: {
        id: null,
        roleCode: null,
        roleName: null
      },
      // 表单验证
      formRules: {
        roleName: [
          { required: true, message: self.$t('constant.role.ROLE_NAME_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 16, message: self.$t('constant.role.THREE_TO_16'), trigger: 'blur' }
        ]
      },
      editRoleShow: false,
      editRoleLoading: false
    }
  },
  methods: {
    show (row) {
      var self = this
      if (self.$refs.roleEditForm) {
        // 重置表单
        self.$refs.roleEditForm.resetFields()
      }
      self.form.id = row.id
      self.form.roleCode = row.roleCode
      self.form.roleName = row.roleName
      this.editRoleShow = true
    },
    saveRole () {
      var self = this
      // 校验表单
      self.$refs.roleEditForm.validate(result => {
        self.editRoleLoading = true
        if (result) {
          self.$http.put(`${DataMainApi}/role`, self.form)
            .then(res => {
              if (res.data.code === Status.success) {
                self.$notify.success(self.$t('constant.role.SAVE_ROLE_SUCCESS_NOTIFY'))
                self.editRoleShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error(self.$t('constant.role.SAVE_ROLE_FAILED_NOTIFY'))
              }
              self.editRoleLoading = false
            })
            .catch(() => {
              self.editRoleLoading = false
            })
        } else {
          self.editRoleLoading = false
        }
      })
    }

  }
}
</script>
