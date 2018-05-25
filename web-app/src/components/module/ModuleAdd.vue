<template lang="html">
  <el-dialog title="新增模块" :visible.sync="addModuleShow">
    <el-form ref="moduleAddForm" :model="form" label-width="80px" :rules="formRules" :inline="true">
      <el-row>
          <el-col :span="12">
            <el-form-item label="系统名称">
              {{ systemName }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上级模块">
              {{ moduleId ? moduleName : ''}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item prop="moduleCode" label="模块编码">
              <el-input v-model="form.moduleCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="moduleName" label="模块名称">
              <el-input v-model="form.moduleName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item prop="modulePath" label="模块URL">
              <el-input v-model="form.modulePath"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="图标">
              <el-input v-model="form.moduleIcon"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
           <el-col :span="12">
            <el-form-item label="状态">
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
          <el-col :span="12">
            <el-form-item label="排序">
              <el-input-number v-model="form.sort" :min="0" :max="200"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否菜单">
              <el-switch
                v-model="form.isOperating"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="是"
                inactive-text="否"
                :active-value="0"
                :inactive-value="1">
              </el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-show="form.isOperating">
            <el-form-item label="http方法">
              <el-select v-model="form.httpMethod" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>
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
        moduleName: null,
        modulePath: null,
        parentId: null,
        moduleIcon: null,
        httpMethod: null,
        isOperating: 0,
        sort: 0,
        systemId: null,
        active: 1
      },
      // 表单验证
      formRules: {
        moduleCode: [
          { required: true, message: '请输入模块编码', trigger: 'blur' },
          { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' },
          { validator: validateModuleCode, trigger: 'blur' }
        ],
        moduleName: [
          { required: true, message: '请输入模块名称', trigger: 'blur' },
          { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' }
        ],
        modulePath: [
          { required: true, message: '请输入模块路径', trigger: 'blur' },
          { min: 3, max: 100, message: '长度在 3 到 100 个字符', trigger: 'blur' }
        ]
      },
      addModuleShow: false,
      addModuleLoading: false,
      options: ['GET', 'POST', 'PUT', 'DELETE'],
      systemId: null,
      systemName: null,
      moduleId: null,
      moduleName: null
    }
  },
  methods: {
    show (data) {
      var self = this
      if (this.$refs.moduleAddForm) {
        // 重置表单
        self.$refs.moduleAddForm.resetFields()
      }
      this.form.moduleCode = null
      this.form.moduleName = null
      this.form.modulePath = null
      this.form.parentId = data.moduleId
      this.form.moduleIcon = null
      this.form.httpMethod = null
      this.form.isOperating = 0
      this.form.sort = 0
      this.form.systemId = data.systemId
      this.form.active = 1
      this.addModuleShow = true

      this.systemId = data.systemId
      this.systemName = data.systemName
      this.moduleId = data.moduleId
      this.moduleName = data.moduleName
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
            .catch(() => {
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
