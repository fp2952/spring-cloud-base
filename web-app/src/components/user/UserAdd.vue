<template lang="html">
  <el-dialog :title="$t('constant.user.ADD_USER')" :visible.sync="addUserShow">
    <el-form ref="userAddForm" :model="form" label-width="100px" :rules="formRules" :inline="true">
      <el-row>
        <el-col :span="12">
          <el-form-item :label="$t('constant.user.USERNAME')" prop="userName">
            <el-input v-model="form.userName" :placeholder="$t('constant.user.USERNAME_PLACEHOLDER')"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item :label="$t('constant.user.PASSWORD')" prop="password">
            <el-input v-model="form.password" type="password" :placeholder="$t('constant.user.PASSWORD_PLACEHOLDER')"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item :label="$t('constant.user.PHONE_NUMBER')" prop="phone">
            <el-input v-model="form.phone" :placeholder="$t('constant.user.PHONE_NUMBER_PLACEHOLDER')"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item :label="$t('constant.user.AGE')">
            <el-input v-model="form.age" :placeholder="$t('constant.user.AGE_PLACEHOLDER')"></el-input>
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
      <el-button @click="addUserShow = false">{{$t('button.CANCEL')}}</el-button>
      <el-button type="primary" @click="saveUser" :loading="addUserLoading">{{$t('button.SURE')}}</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
export default {
  data () {
    var self = this
    var validateUserName = (rule, value, callback) => {
      self.$http.get(`${DataMainApi}/user/validate/${self.form.userName}`)
        .then(res => {
          if (res.data.code === Status.success) {
            callback()
          } else {
            callback(new Error(self.$t('constant.user.USERNAME_EXIST_NOTIFY')))
          }
        })
    }
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
        userName: null,
        password: null,
        phone: null,
        age: 0,
        gender: 'male',
        active: 1
      },
      // 表单验证
      formRules: {
        userName: [
          { required: true, message: self.$t('constant.user.USERNAME_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 16, message: self.$t('constant.user.THREE_TO_16'), trigger: 'blur' },
          { validator: validateUserName, trigger: 'blur' }
        ],
        password: [
          { required: true, message: self.$t('constant.user.PASSWORD_PLACEHOLDER'), trigger: 'blur' },
          { min: 6, max: 16, message: self.$t('constant.user.SEX_TO_16'), trigger: 'blur' }
        ],
        phone: [
          { len: 11, message: self.$t('constant.user.PHONE_NUMBER_FORMAT_WARN_NOTIFY'), trigger: 'blur' },
          { validator: validatePhone, trigger: 'blur' }
        ]
      },
      addUserShow: false,
      addUserLoading: false
    }
  },
  methods: {
    show () {
      var self = this
      if (this.$refs.userAddForm) {
        // 重置表单
        self.$refs.userAddForm.resetFields()
      }
      this.addUserShow = true
    },
    saveUser () {
      var self = this
      // 校验表单
      self.$refs.userAddForm.validate(result => {
        self.addUserLoading = true
        if (result) {
          self.$http.post(`${DataMainApi}/user`, self.form)
            .then(res => {
              if (res.data.code === Status.success) {
                self.$notify.success(self.$t('constant.user.SAVE_USER_SUCCESS_NOTIFY'))
                self.addUserShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error(self.$t('constant.user.SAVE_USER_FAILED_NOTIFY'))
              }
              self.addUserLoading = false
            })
            .catch(() => {
              self.addUserLoading = false
            })
        } else {
          self.addUserLoading = false
        }
      })
    }

  }
}
</script>
