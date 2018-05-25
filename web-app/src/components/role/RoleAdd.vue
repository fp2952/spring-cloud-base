<template lang="html">
  <el-dialog title="新增角色" :visible.sync="addRoleShow">
    <el-form ref="roleAddForm" :model="form" label-width="80px" :rules="formRules" :inline="true">
      <el-row>
        <el-col :span="12">
          <el-form-item label="角色编码" prop="roleCode">
            <el-input v-model="form.roleCode" placeholder="请输入角色编码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="角色名称" prop="roleName">
            <el-input v-model="form.roleName" placeholder="请输入角色名称"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
   </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="addRoleShow = false">取 消</el-button>
      <el-button type="primary" @click="saveRole" :loading="addRoleLoading">确 定</el-button>
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
            callback(new Error('角色编码已存在!'))
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
          { required: true, message: '请输入角色编码', trigger: 'blur' },
          { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' },
          { validator: validateRoleCode, trigger: 'blur' }
        ],
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
          { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }
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
                self.$notify.success('保存角色成功！')
                self.addRoleShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error('保存角色失败！')
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
