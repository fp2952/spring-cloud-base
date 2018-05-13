<template lang="html">
  <el-dialog title="新增系统" :visible.sync="addSystemShow">
    <el-form ref="systemAddForm" :model="form" label-width="100px" :rules="formRules" :inline="true">
      <el-row>
        <el-col :span="12">
          <el-form-item label="系统项目名" prop="projectName">
            <el-input v-model="form.projectName" placeholder="请输入系统项目名"></el-input>
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
      <el-button @click="addSystemShow = false">取 消</el-button>
      <el-button type="primary" @click="saveSystem" :loading="addSystemLoading">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
export default {
  data () {
    var self = this
    var validateSystemCode = (rule, value, callback) => {
      self.$http.get(`${DataMainApi}/system/validate/${self.form.projectName}`)
        .then(res => {
          if (res.data.code === Status.success) {
            callback()
          } else {
            callback(new Error('系统项目名已存在!'))
          }
        })
    }
    return {
      form: {
        projectName: null,
        systemName: null,
        active: 1
      },
      // 表单验证
      formRules: {
        projectName: [
          { required: true, message: '请输入系统项目名', trigger: 'blur' },
          { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' },
          { validator: validateSystemCode, trigger: 'blur' }
        ],
        systemName: [
          { required: true, message: '请输入系统名称', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ]
      },
      addSystemShow: false,
      addSystemLoading: false
    }
  },
  methods: {
    show () {
      var self = this
      if (this.$refs.systemAddForm) {
        // 重置表单
        self.$refs.systemAddForm.resetFields()
      }
      this.addSystemShow = true
    },
    saveSystem () {
      var self = this
      // 校验表单
      self.$refs.systemAddForm.validate(result => {
        self.addSystemLoading = true
        if (result) {
          self.$http.post(`${DataMainApi}/system`, self.form)
            .then(res => {
              if (res.data.code === Status.success) {
                self.$notify.success('保存系统成功！')
                self.addSystemShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error('保存系统失败！')
              }
              self.addSystemLoading = false
            })
        } else {
          self.addSystemLoading = false
        }
      })
    }

  }
}
</script>
