<template lang="html">
  <el-dialog :title="$t('constant.user.EDIT_USER')" :visible.sync="editUserShow">
    <el-form ref="editUserForm" :model="form" label-width="100px" :rules="formRules" :inline="true">
      <el-row>
        <el-col :span="12">
          <el-form-item :label="$t('constant.user.USERNAME')">
            <el-input v-model="form.userName" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item :label="$t('constant.user.PASSWORD')">
            <el-input v-model="form.password" type="password" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item :label="$t('constant.user.PHONE_NUMBER')" prop="phone">
            <el-input v-model="form.phone"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item :label="$t('constant.user.AGE')">
            <el-input v-model="form.age"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item :label="$t('constant.user.GENDER')">
            <el-radio v-model="form.gender" label="male">{{$t('constant.user.MALE')}}</el-radio>
            <el-radio v-model="form.gender" label="female">{{$t('constant.user.FEMALE')}}</el-radio>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item :label="$t('constant.user.ACTIVE')">
            <el-switch
              v-model="form.active"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-text="$t('constant.TRUE')"
              :inactive-text="$t('constant.FALSE')"
              :active-value="1"
              :inactive-value="0">
            </el-switch>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="editUserShow = false">{{$t('button.CANCEL')}}</el-button>
      <el-button type="primary" @click="saveUser" :loading="editUserLoading">{{$t('button.SURE')}}</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
export default {
  data () {
    var self = this
    var validatePhone = (rule, value, callback) => {
      self.$http.get(`${DataMainApi}/user/validate/phone/${self.form.phone}`)
        .then(res => {
          if (res.data.code === Status.success) {
            callback()
          } else {
            callback(new Error(self.$t('constant.user.PHONE_EXIST_NOTIFY')))
          }
        })
    }
    return {
      form: {
        id: null,
        userName: null,
        password: null,
        phone: null,
        age: 0,
        gender: null,
        active: null
      },
      // 表单验证
      formRules: {
        phone: [
          { len: 11, message: self.$t('constant.user.PHONE_NUMBER_FORMAT_WARN_NOTIFY'), trigger: 'blur' },
          { validator: validatePhone, trigger: 'blur' }
        ]
      },
      editUserShow: false,
      editUserLoading: false
    }
  },
  methods: {
    show (row) {
      var self = this
      if (self.$refs.editUserForm) {
        // 重置表单
        self.$refs.editUserForm.resetFields()
      }
      self.form.id = row.id
      self.form.userName = row.userName
      self.form.password = row.password
      self.form.phone = row.phone
      self.form.age = row.age
      self.form.gender = row.gender
      self.form.active = row.active
      self.editUserShow = true
    },
    saveUser () {
      var self = this
      // 校验表单
      self.$refs.editUserForm.validate(result => {
        self.editUserLoading = true
        if (result) {
          self.$http.put(`${DataMainApi}/user`, self.form)
            .then(res => {
              if (res.data.code === Status.success) {
                self.$notify.success(self.$t('constant.user.EDIT_USER_SUCCESS_NOTIFY'))
                self.editUserShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error(self.$t('constant.user.EDIT_USER_FAILED_NOTIFY'))
              }
              self.editUserLoading = false
            })
            .catch(() => {
              self.editUserLoading = false
            })
        } else {
          self.editUserLoading = false
        }
      })
    }

  }
}
</script>
