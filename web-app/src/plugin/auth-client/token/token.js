import axios from 'axios'
import querystring from 'querystring'
import Config from '../utils/config'
import Constant from '../utils/constant'
import moment from 'moment'

export default {

  /**
   * 请求授权服务器获取token
   * @param code
   */
  accestokenByCode (code) {
    axios.post(Config.authUrl + '/oauth/token', {
      grant_type: 'authorization_code',
      code: code,
      client: Config.appId,
      redirect_uri: Config.baseUrl
    }, {
      transformRequest: [function (data) {
        return querystring.stringify(data)
      }],
      auth: {
        username: Config.appId,
        password: Config.appSecret
      }
    })
      .then(res => {
        this.saveToken(res.data)
        window.location.href = Config.baseUrl
      })
      .catch(err => {
        console.error(err)
      })
  },

  accestokenByBasic (username, password) {
    axios.post(Config.authUrl + '/oauth/token', {
      grant_type: 'password',
      username: username,
      password: password
    }, {
      transformRequest: [function (data) {
        return querystring.stringify(data)
      }],
      auth: {
        username: Config.appId,
        password: Config.appSecret
      }
    })
      .then(res => {
        this.saveToken(res.data)
        window.location.href = Config.baseUrl
      })
      .catch(err => {
        console.error(err)
      })
  },

  /**
   * 请求授权服务器刷新token
   */
  refreshoken () {
    let refreshToken = localStorage.getItem(Constant.refresh_token)
    if (!refreshToken) {
      console.error('refresh_token not find.')
    } else {
      // 调用刷新 token
      axios.post(Config.authUrl + '/oauth/token', {
        grant_type: 'refresh_token',
        refresh_token: refreshToken
      }, {
        transformRequest: [function (data) {
          return querystring.stringify(data)
        }],
        auth: {
          username: Config.appId,
          password: Config.appSecret
        }
      })
        .then(res => {
          this.saveToken(res.data)
          window.location.href = Config.baseUrl
        })
        .catch(err => {
          console.error(err)
          window.location.href = Config.authUrl + '/oauth/authorize?response_type=code&client_id=' + Config.appId + '&redirect_uri=' +
            Config.baseUrl
        })
    }
  },

  /**
   * 是否登陆
   */
  isLogin () {
    if (localStorage.getItem(Constant.access_token)) {
      return true
    }
    return false
  },

  /**
   * 是否过期
   */
  isExpires () {
    let tokenExpiresTime = localStorage.getItem(Constant.token_expires_time)
    if (!tokenExpiresTime) {
      console.error('access_token_time not find.')
      return true
    }

    if (moment().unix() > tokenExpiresTime) {
      return true
    }
    return false
  },
  token () {
    return localStorage.getItem(Constant.access_token)
  },
  deleteToken () {
    localStorage.removeItem(Constant.access_token)
    localStorage.removeItem(Constant.refresh_token)
    localStorage.removeItem(Constant.expires_in)
    localStorage.removeItem(Constant.token_expires_time)
    localStorage.removeItem(Constant.user_info)
    window.location.href = Config.authUrl + '/logout'
  },
  saveToken (data) {
    localStorage.setItem(Constant.access_token, data.access_token)
    localStorage.setItem(Constant.refresh_token, data.refresh_token)
    localStorage.setItem(Constant.expires_in, data.expires_in)
    localStorage.setItem(Constant.token_expires_time, moment().add(data.expires_in, 's').unix())
    localStorage.setItem(Constant.user_info, JSON.stringify(data.user_info))
  }
}
