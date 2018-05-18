import Url from '../utils/url'
import Token from '../token/token'
import Config from '../utils/config'

export default {
  init: function (Router) {
    Router.beforeEach((to, from, next) => {
      // 授权获取token
      if (Url.getParam('code')) {
        Token.accessoken(Url.getParam('code'), next)
      } else if (Url.getParam('logout') === 'yes') {
        // 登出
        Token.deleteToken()
      } else {
        // 判断是否登陆
        if (Token.isLogin()) {
          // 是否过期
          if (Token.isExpires()) {
            Token.refreshoken()
          } else {
            next()
          }
        } else {
          window.location.href = Config.authUrl + '/oauth/authorize?response_type=code&client_id=' + Config.appId + '&redirect_uri=' +
            Config.baseUrl
        }
      }
    })
  }
}
