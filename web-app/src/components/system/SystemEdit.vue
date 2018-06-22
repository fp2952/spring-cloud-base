<template lang="html">
  <el-dialog :title="$t('constant.system.EDIT_SYSTEM')" :visible.sync="editSystemShow">
    <el-form ref="systemEditForm" :model="form" label-width="180px" :rules="formRules" :inline="true">
      <el-row>
        <el-col :span="12">
          <el-form-item :label="$t('constant.system.PROJECT_NAME')">
            <el-tooltip class="item" effect="light" :content="$t('constant.system.CONTENT')" placement="top-start">
              <el-input v-model="form.projectName"></el-input>
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
      <el-button @click="editSystemShow = false">{{$t('button.CANCEL')}}</el-button>
      <el-button type="primary" @click="saveSystem" :loading="editSystemLoading">{{$t('button.SURE')}}</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
export default {
  data () {
    var self = this
    return {
      form: {
        id: null,
        projectName: null,
        systemName: null,
        active: null
      },
      // 表单验证
      formRules: {
        projectName: [
          { required: true, message: self.$t('constant.system.PROJECT_NAME_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 16, message: self.$t('constant.system.THREE_TO_16'), trigger: 'blur' }
        ],
        systemName: [
          { required: true, message: self.$t('constant.system.SYSTEM_NAME_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 16, message: self.$t('constant.system.THREE_TO_16'), trigger: 'blur' }
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
                self.$notify.success(self.$t('constant.system.SAVE_SYSTEM_SUCCESS_NOTIFY'))
                self.editSystemShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error(self.$t('constant.system.SAVE_SYSTEM_FAILED_NOTIFY'))
              }
              self.editSystemLoading = false
            })
            .catch(() => {
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
