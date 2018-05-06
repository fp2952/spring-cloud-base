import Axios from 'axios'
import VueAxios from 'vue-axios'

export default {
  init (Vue) {
    // 配置 axios
    Vue.use(VueAxios, Axios)

    // 配置 OAuth2.0
    Vue.prototype.$auth.config.appId = 'test'
    Vue.prototype.$auth.config.appSecret = 'test'

    // 设置后台请求地址前缀
    Vue.axios.defaults.baseURL = window.serverconf[process.env.NODE_ENV]['baseURL']
    Vue.prototype.$auth.config.baseUrl = window.serverconf[process.env.NODE_ENV]['baseUrl']
    Vue.prototype.$auth.config.authUrl = window.serverconf[process.env.NODE_ENV]['authUrl']

    // 配置认证头
    Vue.axios.defaults.headers.common['Authorization'] = 'Bearer ' + Vue.prototype.$auth.token()
    // http 拦截器
    Vue.axios.interceptors.response.use(function (response) {
      // Do something with response data
      return response
    }, function (error) {
      Vue.prototype.$notify.error('访问服务器错误')
      console.error(error)
      // Do something with response error
      return Promise.reject(error)
    })
  }
}
