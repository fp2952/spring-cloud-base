<template lang="html">
  <el-dialog title="新增用户" :visible.sync="addUserShow">
    <el-form ref="userAddForm" :model="form" label-width="80px" :rules="formRules" :inline="true">
      <el-row>
        <el-col :span="12">
          <el-form-item label="用户名" prop="userName">
            <el-input v-model="form.userName" placeholder="请输入用户名"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" type="password" placeholder="请输入密码"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="手机号码" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入手机号码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="年龄">
            <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="性别">
            <el-radio v-model="form.gender" label="male">男</el-radio>
            <el-radio v-model="form.gender" label="female">女</el-radio>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否启用">
            <el-switch
              v-model="form.active"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="启用"
              inactive-text="禁用"
              :active-value="1"
              :inactive-value="0">
            </el-switch>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="addUserShow = false">取 消</el-button>
      <el-button type="primary" @click="saveUser" :loading="addUserLoading">确 定</el-button>
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
            callback(new Error('用户名已存在!'))
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
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' },
          { validator: validateUserName, trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ],
        phone: [
          { len: 11, message: '手机号码格式错误', trigger: 'blur' }
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
                self.$notify.success('保存用户成功！')
                self.addUserShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error('保存用户失败！')
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
