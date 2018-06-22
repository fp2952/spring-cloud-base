<template lang="html">
  <el-dialog :title="$t('constant.client.ADD_CLIENT')" :visible.sync="addAppShow">
    <el-form ref="appAddForm" :model="form" label-width="200px" :rules="formRules" :inline="true">
      <el-row>
        <el-col :span="12">
          <el-form-item :label="$t('constant.client.CLIENT_ID')" prop="clientId">
            <el-input v-model="form.clientId" :placeholder="$t('constant.client.CLIENT_ID_PLACEHOLDER')"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item :label="$t('constant.client.CLIENT_NAME')" prop="name">
            <el-input v-model="form.name" :placeholder="$t('constant.client.CLIENT_NAME_PLACEHOLDER')"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item :label="$t('constant.client.CLIENT_SECRET')" prop="clientSecret">
            <el-input v-model="form.clientSecret" :placeholder="$t('constant.client.CLIENT_SECRET_PLACEHOLDER')"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item :label="$t('constant.client.TOKEN_VALIDITY')">
              <el-input-number v-model="form.accessTokenValidity" :min="10000" ></el-input-number>
            </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
            <el-form-item :label="$t('constant.client.AUTO_APPROVE')">
              <el-switch
                v-model="form.autoapprove"
                active-color="#13ce66"
                inactive-color="#ff4949"
                :active-text="$t('constant.TRUE')"
                :inactive-text="$t('constant.FALSE')"
                active-value="true"
                inactive-value="false">
              </el-switch>
            </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item :label="$t('constant.client.AUTHORIZED_GRANT_TYPE')">
              <el-select v-model="authorizedGrantTypes" multiple :placeholder="$t('constant.client.PLACEHOLDER')">
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
      <el-button @click="addAppShow = false">{{$t('button.CANCEL')}}</el-button>
      <el-button type="primary" @click="saveApp" :loading="addAppLoading">{{$t('button.SURE')}}</el-button>
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
            callback(new Error(self.$t('constant.client.CLIENT_ID_EXIST_NOTIFY')))
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
          { required: true, message: self.$t('constant.client.CLIENT_ID_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 16, message: self.$t('constant.client.THREE_TO_16'), trigger: 'blur' },
          { validator: validateAppCode, trigger: 'blur' }
        ],
        name: [
          { required: true, message: self.$t('constant.client.CLIENT_NAME_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 16, message: self.$t('constant.client.THREE_TO_16'), trigger: 'blur' }
        ],
        clientSecret: [
          { required: true, message: self.$t('constant.client.CLIENT_SECRET_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 16, message: self.$t('constant.client.THREE_TO_16'), trigger: 'blur' }
        ]
      },
      addAppShow: false,
      addAppLoading: false,
      authorizedGrantTypes: ['authorization_code'],
      options: [
        {
          label: self.$t('constant.client.AUTHORIZED_CODE'),
          value: 'authorization_code'
        },
        {
          label: self.$t('constant.client.REFRESH_TOKEN'),
          value: 'refresh_token'
        },
        {
          label: self.$t('constant.client.PASSWORD'),
          value: 'password'
        },
        {
          label: self.$t('constant.client.IMPLICIT'),
          value: 'implicit'
        },
        {
          label: self.$t('constant.client.CLIENT_CREDENTIALS'),
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
                self.$notify.success(self.$t('constant.client.SAVE_CLIENT_SUCCESS_NOTIFY'))
                self.addAppShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error(self.$t('constant.client.SAVE_CLIENT_FAILED_NOTIFY'))
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
