<template lang="html">
  <el-dialog title="编辑角色" :visible.sync="editRoleShow">
    <el-form ref="roleEditForm" :model="form" label-width="80px" :rules="formRules" :inline="true">
      <el-row>
        <el-col :span="12">
          <el-form-item label="角色编码">
            <el-input v-model="form.roleCode" :disabled="true"></el-input>
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
      <el-button @click="editRoleShow = false">取 消</el-button>
      <el-button type="primary" @click="saveRole" :loading="editRoleLoading">确 定</el-button>
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
        roleCode: null,
        roleName: null
      },
      // 表单验证
      formRules: {
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
          { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }
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
                self.$notify.success('保存角色成功！')
                self.editRoleShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error('保存角色失败！')
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
