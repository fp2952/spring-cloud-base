import Vue from 'vue'
import * as Constant from './constant'

export default {
  geRoleInfo ({ commit }) {
    Vue.axios.get(`${Constant.DataMainApi}/menu`)
      .then(res => {
        if (res.data.code === Constant.Status.success) {
          commit(Constant.UPDATE_MENU_INFO, { menuInfo: res.data.data })
        }
      })
  }
}
