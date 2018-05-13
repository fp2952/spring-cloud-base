<template lang="html">
  <el-dialog title="编辑用户" :visible.sync="editUserShow">
    <el-form ref="editUserForm" :model="form" label-width="80px" :rules="formRules" :inline="true">
      <el-row>
        <el-col :span="12">
          <el-form-item label="用户名">
            <el-input v-model="form.userName" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="密码">
            <el-input v-model="form.password" type="password" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="手机号码" prop="phone">
            <el-input v-model="form.phone"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="年龄">
            <el-input v-model="form.age"></el-input>
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
      <el-button @click="editUserShow = false">取 消</el-button>
      <el-button type="primary" @click="saveUser" :loading="editUserLoading">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
export default {
  data () {
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
          { len: 11, message: '手机号码格式错误', trigger: 'blur' }
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
                self.$notify.success('编辑用户成功！')
                self.editUserShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error('编辑用户失败！')
              }
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
