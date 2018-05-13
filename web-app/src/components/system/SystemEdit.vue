<template lang="html">
  <el-dialog title="编辑系统" :visible.sync="editSystemShow">
    <el-form ref="systemEditForm" :model="form" label-width="100px" :rules="formRules" :inline="true">
      <el-row>
        <el-col :span="12">
          <el-form-item label="系统项目名">
            <el-input v-model="form.projectName" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="系统名称" prop="systemName">
            <el-input v-model="form.systemName" placeholder="请输入系统名称"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
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
      <el-button @click="editSystemShow = false">取 消</el-button>
      <el-button type="primary" @click="saveSystem" :loading="editSystemLoading">确 定</el-button>
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
        projectName: null,
        systemName: null,
        active: null
      },
      // 表单验证
      formRules: {
        systemName: [
          { required: true, message: '请输入系统名称', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ]
      },
      editSystemShow: false,
      editSystemLoading: false
    }
  },
  methods: {
    show (row) {
      var self = this
      if (self.$refs.systemEditForm) {
        // 重置表单
        self.$refs.systemEditForm.resetFields()
      }
      self.form.id = row.id
      self.form.projectName = row.projectName
      self.form.systemName = row.systemName
      self.form.active = row.active
      this.editSystemShow = true
    },
    saveSystem () {
      var self = this
      // 校验表单
      self.$refs.systemEditForm.validate(result => {
        self.editSystemLoading = true
        if (result) {
          self.$http.put(`${DataMainApi}/system`, self.form)
            .then(res => {
              if (res.data.code === Status.success) {
                self.$notify.success('保存系统成功！')
                self.editSystemShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error('保存系统失败！')
              }
              self.editSystemLoading = false
            })
        } else {
          self.editSystemLoading = false
        }
      })
    }
  }
}
</script>
