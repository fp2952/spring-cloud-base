<template lang="html">
  <el-dialog :title="$t('constant.role.ADD_ROLE')" :visible.sync="addRoleShow">
    <el-form ref="roleAddForm" :model="form" label-width="100px" :rules="formRules" :inline="true">
      <el-row>
        <el-col :span="12">
          <el-form-item :label="$t('constant.role.ROLE_CODE')" prop="roleCode" :span="6">
            <el-input v-model="form.roleCode" :placeholder="$t('constant.role.ROLE_CODE_PLACEHOLDER')"></el-input>
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
      <el-button @click="addRoleShow = false">{{$t('button.CANCEL')}}</el-button>
      <el-button type="primary" @click="saveRole" :loading="addRoleLoading">{{$t('button.SURE')}}</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
export default {
  data () {
    var self = this
    var validateRoleCode = (rule, value, callback) => {
      self.$http.get(`${DataMainApi}/role/validate/${self.form.roleCode}`)
        .then(res => {
          if (res.data.code === Status.success) {
            callback()
          } else {
            callback(new Error(self.$t('constant.role.ROLE_CODE_EXIST_NOTIFY')))
          }
        })
    }
    return {
      form: {
        roleCode: null,
        roleName: null
      },
      // 表单验证
      formRules: {
        roleCode: [
          { required: true, message: self.$t('constant.role.ROLE_CODE_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 16, message: self.$t('constant.role.THREE_TO_16'), trigger: 'blur' },
          { validator: validateRoleCode, trigger: 'blur' }
        ],
        roleName: [
          { required: true, message: self.$t('constant.role.ROLE_NAME_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 16, message: self.$t('constant.role.THREE_TO_16'), trigger: 'blur' }
        ]
      },
      addRoleShow: false,
      addRoleLoading: false
    }
  },
  methods: {
    show () {
      var self = this
      if (this.$refs.roleAddForm) {
        // 重置表单
        self.$refs.roleAddForm.resetFields()
      }
      this.addRoleShow = true
    },
    saveRole () {
      var self = this
      // 校验表单
      self.$refs.roleAddForm.validate(result => {
        self.addRoleLoading = true
        if (result) {
          self.$http.post(`${DataMainApi}/role`, self.form)
            .then(res => {
              if (res.data.code === Status.success) {
                self.$notify.success(self.$t('constant.role.SAVE_ROLE_SUCCESS_NOTIFY'))
                self.addRoleShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error(self.$t('constant.role.SAVE_ROLE_FAILED_NOTIFY'))
              }
              self.addRoleLoading = false
            })
            .catch(() => {
              self.addRoleLoading = false
            })
        } else {
          self.addRoleLoading = false
        }
      })
    }

  }
}
</script>
