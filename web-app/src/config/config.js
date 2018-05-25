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
    Vue.axios.defaults.baseURL = process.env.SERVER_URL
    Vue.prototype.$auth.config.baseUrl = process.env.BASE_URL
    // Vue.prototype.$auth.config.authUrl = window.serverconf[process.env.NODE_ENV]['authUrl']
    Vue.prototype.$auth.config.authUrl = process.env.AUTH_URL

    // 配置认证头
    Vue.axios.defaults.headers.common['Authorization'] = 'Bearer ' + Vue.prototype.$auth.token()
    // http 拦截器
    Vue.axios.interceptors.response.use(function (response) {
      // Do something with response data
      return response
    }, function (error) {
      if (error && error.response) {
        switch (error.response.status) {
          case 403:
            Vue.prototype.$notify.error('无访问权限')
            break
          default:
            Vue.prototype.$notify.error('访问服务器错误')
        }
      } else {
        Vue.prototype.$notify.error('访问服务器错误')
      }
      console.error(error)
      // Do something with response error
      return Promise.reject(error)
    })
  }
}
