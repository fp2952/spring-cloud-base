<template lang="html">
  <el-dialog title="新增应用" :visible.sync="addAppShow">
    <el-form ref="appAddForm" :model="form" label-width="100px" :rules="formRules" :inline="true">
      <el-row>
        <el-col :span="12">
          <el-form-item label="应用id" prop="clientId">
            <el-input v-model="form.clientId" placeholder="请输入应用id"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="应用名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入应用名称"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="应用secret" prop="clientSecret">
            <el-input v-model="form.clientSecret" placeholder="请输入应用secret"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="token有效期">
              <el-input-number v-model="form.accessTokenValidity" :min="10000" ></el-input-number>
            </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
            <el-form-item label="静默模式">
              <el-switch
                v-model="form.autoapprove"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="启用"
                inactive-text="禁用"
                active-value="true"
                inactive-value="false">
              </el-switch>
            </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="授权模式">
              <el-select v-model="authorizedGrantTypes" multiple placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
        </el-col>
      </el-row>
   </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="addAppShow = false">取 消</el-button>
      <el-button type="primary" @click="saveApp" :loading="addAppLoading">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
export default {
  data () {
    var self = this
    var validateAppCode = (rule, value, callback) => {
      self.$http.get(`${DataMainApi}/client/validate/${self.form.clientId}`)
        .then(res => {
          if (res.data.code === Status.success) {
            callback()
          } else {
            callback(new Error('应用id已存在!'))
          }
        })
    }
    return {
      form: {
        clientId: null,
        name: null,
        clientSecret: null,
        accessTokenValidity: 43200,
        autoapprove: 'false',
        authorizedGrantTypes: null
      },
      // 表单验证
      formRules: {
        clientId: [
          { required: true, message: '请输入应用id', trigger: 'blur' },
          { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' },
          { validator: validateAppCode, trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入应用名称', trigger: 'blur' },
          { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }
        ],
        clientSecret: [
          { required: true, message: '请输入应用secret', trigger: 'blur' },
          { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }
        ]
      },
      addAppShow: false,
      addAppLoading: false,
      authorizedGrantTypes: ['authorization_code'],
      options: [
        {
          label: '授权码',
          value: 'authorization_code'
        },
        {
          label: '刷新',
          value: 'refresh_token'
        },
        {
          label: '密码',
          value: 'password'
        },
        {
          label: '简化',
          value: 'implicit'
        },
        {
          label: '客户端',
          value: 'client_credentials'
        }
      ]
    }
  },
  computed: {
    computedGrantTypes () {
      return this.authorizedGrantTypes.toString()
    }
  },
  methods: {
    show () {
      var self = this
      if (this.$refs.appAddForm) {
        // 重置表单
        self.$refs.appAddForm.resetFields()
      }
      this.addAppShow = true
    },
    saveApp () {
      var self = this
      // 校验表单
      self.$refs.appAddForm.validate(result => {
        self.addAppLoading = true
        if (result) {
          self.form.authorizedGrantTypes = self.computedGrantTypes
          self.$http.post(`${DataMainApi}/client`, self.form)
            .then(res => {
              if (res.data.code === Status.success) {
                self.$notify.success('保存应用成功！')
                self.addAppShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error('保存应用失败！')
              }
              self.addAppLoading = false
            })
            .catch(() => {
              self.addAppLoading = false
            })
        } else {
          self.addAppLoading = false
        }
      })
    }
  }
}
</script>
