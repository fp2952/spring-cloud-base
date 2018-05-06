import AuthRouter from './router/router'
import Config from './utils/config'
import Token from './token/token'

export default {
  install: function (Vue, Router) {
    // 初始化router
    AuthRouter.init(Router)
    // 设置vue原型链
    Vue.prototype.$auth = {
      config: Config,
      token () {
        return Token.token()
      },
      logout () {
        Token.deleteToken()
      }
    }
  }
}
