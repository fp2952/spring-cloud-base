<template lang="html">
  <el-dialog title="新增模块" :visible.sync="addModuleShow">
    <el-form ref="moduleAddForm" :model="form" label-width="80px" :rules="formRules" :inline="true">
      <el-row>
        <el-col :span="12">
          <el-form-item label="模块编码" prop="moduleCode">
            <el-input v-model="form.moduleCode" placeholder="请输入模块编码"></el-input>
          </el-form-item>
        </el-col>Module
        <el-col :span="12">
          <el-form-item label="模块名称" prop="moduleName">
            <el-input v-model="form.moduleName" placeholder="请输入模块名称"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="模块编码" prop="moduleCode">
            <el-input v-model="form.moduleCode" placeholder="请输入模块编码"></el-input>
          </el-form-item>
        </el-col>Module
        <el-col :span="12">
          <el-form-item label="模块名称" prop="moduleName">
            <el-input v-model="form.moduleName" placeholder="请输入模块名称"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="模块编码" prop="moduleCode">
            <el-input v-model="form.moduleCode" placeholder="请输入模块编码"></el-input>
          </el-form-item>
        </el-col>Module
        <el-col :span="12">
          <el-form-item label="模块名称" prop="moduleName">
            <el-input v-model="form.moduleName" placeholder="请输入模块名称"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
   </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="addModuleShow = false">取 消</el-button>
      <el-button type="primary" @click="saveModule" :loading="addModuleLoading">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
export default {
  data () {
    var self = this
    var validateModuleCode = (rule, value, callback) => {
      self.$http.get(`${DataMainApi}/module/validate/${self.form.moduleCode}`)
        .then(res => {
          if (res.data.code === Status.success) {
            callback()
          } else {
            callback(new Error('模块编码已存在!'))
          }
        })
    }
    return {
      form: {
        moduleCode: null,
        moduleName: null
      },
      // 表单验证
      formRules: {
        moduleCode: [
          { required: true, message: '请输入模块编码', trigger: 'blur' },
          { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' },
          { validator: validateModuleCode, trigger: 'blur' }
        ],
        moduleName: [
          { required: true, message: '请输入模块名称', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ]
      },
      addModuleShow: false,
      addModuleLoading: false
    }
  },
  methods: {
    show () {
      var self = this
      if (this.$refs.moduleAddForm) {
        // 重置表单
        self.$refs.moduleAddForm.resetFields()
      }
      this.addModuleShow = true
    },
    saveModule () {
      var self = this
      // 校验表单
      self.$refs.moduleAddForm.validate(result => {
        self.addModuleLoading = true
        if (result) {
          self.$http.post(`${DataMainApi}/module`, self.form)
            .then(res => {
              if (res.data.code === Status.success) {
                self.$notify.success('保存模块成功！')
                self.addModuleShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error('保存模块失败！')
              }
              self.addModuleLoading = false
            })
        } else {
          self.addModuleLoading = false
        }
      })
    }

  }
}
</script>
