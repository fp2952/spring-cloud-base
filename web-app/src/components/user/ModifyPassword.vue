<template lang="html">
  <el-dialog title="修改密码" :visible.sync="modifyPasswordShow" width="700px">
    <el-form ref="modifyPasswordForm" :model="form" label-width="80px" :rules="formRules" >
      <el-row>
        <el-col :span="24">
          <el-form-item label="密码" prop="newPassword">
            <el-input v-model="form.newPassword" placeholder="请输入新密码"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="确认密码" prop="checkPassword">
            <el-input v-model="form.checkPassword" placeholder="请再次输入密码"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="modifyPasswordShow = false">取 消</el-button>
      <el-button type="primary" @click="modifyPassword" :loading="modifyPasswordLoading">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
export default {
  data () {
    var self = this
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (self.form.checkPassword !== '') {
          self.$refs.modifyPasswordForm.validateField('checkPassword')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== self.form.newPassword) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }

    return {
      form: {
        newPassword: null,
        checkPassword: null
      },
      // 表单验证
      formRules: {
        newPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
          { validator: validatePass2, trigger: 'blur' }
        ]
      },
      modifyPasswordShow: false,
      modifyPasswordLoading: false,
      user: null
    }
  },
  methods: {
    show (data) {
      var self = this
      if (this.$refs.modifyPasswordForm) {
        // 重置表单
        self.$refs.modifyPasswordForm.resetFields()
      }
      this.user = data
      this.modifyPasswordShow = true
    },
    modifyPassword () {
      var self = this
      // 校验表单
      self.$refs.modifyPasswordForm.validate(result => {
        self.modifyPasswordLoading = true
        if (result) {
          self.$http.post(`${DataMainApi}/user/password/${self.form.checkPassword}`, [self.user])
            .then(res => {
              if (res.data.code === Status.success) {
                self.$notify.success('修改密码成功！')
                self.modifyPasswordShow = false
              } else {
                self.$notify.error('修改密码失败！')
              }
              self.modifyPasswordLoading = false
            })
            .catch(() => {
              self.modifyPasswordLoading = false
            })
        } else {
          self.modifyPasswordLoading = false
        }
      })
    }
  }
}
</script>
