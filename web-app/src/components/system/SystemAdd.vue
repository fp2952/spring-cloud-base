<template lang="html">
  <el-dialog :title="$t('constant.system.ADD_SYSTEM')" :visible.sync="addSystemShow">
    <el-form ref="systemAddForm" :model="form" label-width="180px" :rules="formRules" :inline="true">
      <el-row>
        <el-col :span="12">
          <el-form-item :label="$t('constant.system.PROJECT_NAME')" prop="projectName">
            <el-tooltip class="item" effect="light" :content="$t('constant.system.CONTENT')" placement="top-start">
              <el-input v-model="form.projectName" :placeholder="$t('constant.system.PROJECT_NAME_PLACEHOLDER')"></el-input>
            </el-tooltip>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item :label="$t('constant.system.SYSTEM_NAME')" prop="systemName">
            <el-input v-model="form.systemName" :placeholder="$t('constant.system.SYSTEM_NAME_PLACEHOLDER')"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
       <el-col :span="12">
          <el-form-item :label="$t('constant.system.ACTIVE')">
            <el-switch
              v-model="form.active"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-text="$t('constant.TRUE')"
              :inactive-text="$t('constant.FALSE')"
              :active-value="1"
              :inactive-value="0">
            </el-switch>
          </el-form-item>
        </el-col>
      </el-row>
   </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="addSystemShow = false">{{$t('button.CANCEL')}}</el-button>
      <el-button type="primary" @click="saveSystem" :loading="addSystemLoading">{{$t('button.SURE')}}</el-button>
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
            callback(new Error(self.$t('constant.system.PROJECT_NAME_EXIST_NOTIFY')))
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
          { required: true, message: self.$t('constant.system.PROJECT_NAME_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 16, message: self.$t('constant.system.THREE_TO_16'), trigger: 'blur' },
          { validator: validateSystemCode, trigger: 'blur' }
        ],
        systemName: [
          { required: true, message: self.$t('constant.system.SYSTEM_NAME_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 16, message: self.$t('constant.system.THREE_TO_16'), trigger: 'blur' }
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
                self.$notify.success(self.$t('constant.system.SAVE_SYSTEM_SUCCESS_NOTIFY'))
                self.addSystemShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error(self.$t('constant.system.SAVE_SYSTEM_FAILED_NOTIFY'))
              }
              self.addSystemLoading = false
            })
            .catch(() => {
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
