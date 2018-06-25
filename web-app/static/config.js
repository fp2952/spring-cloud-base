/*
 在 index.html 引入
 2018-04-17
 */

window.serverconf = {
  appId: 'test',
  appSecret: 'test',
  production: {
    // nginx 代理
    baseUrl: 'http://www.mxclass.cn',
    authUrl: 'http://www.mxclass.cn/auth',
    baseURL: 'http://www.mxclass.cn'
  },
  development: {
    baseUrl: 'http://127.0.0.1:8080',
    authUrl: 'http://127.0.0.1:18001/auth',
    baseURL: 'http://127.0.0.1:18000'
  }
}
