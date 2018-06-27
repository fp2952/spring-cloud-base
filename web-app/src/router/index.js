import Vue from 'vue'
import Router from 'vue-router'
import store from '../store'
import { UPDATE_MAIN_LOADING } from '../store/constant'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

Vue.use(Router)

var router = new Router({
  routes: [
    {
      path: '/',
      name: 'Main',
      component: resolve => require(['@/components/Main.vue'], resolve),
      children: [
        {
          path: '/user',
          name: 'UserManaged',
          component: resolve => require(['@/components/user/UserManaged.vue'], resolve)
        }, {
          path: '/role',
          name: 'RoleManaged',
          component: resolve => require(['@/components/role/RoleManaged.vue'], resolve)
        }, {
          path: '/module',
          name: 'ModuleManaged',
          component: resolve => require(['@/components/module/ModuleManaged.vue'], resolve)
        }, {
          path: '/system',
          name: 'SystemManaged',
          component: resolve => require(['@/components/system/SystemManaged.vue'], resolve)
        }, {
          path: '/app',
          name: 'AppManaged',
          component: resolve => require(['@/components/app/AppManaged.vue'], resolve)
        }, {
          path: '/iframe',
          name: 'Iframe',
          component: resolve => require(['@/components/iframe/Iframe.vue'], resolve)
        }, {
          path: '*',
          name: 'notFound',
          component: resolve => require(['@/components/error/NotFound.vue'], resolve)
        }
      ]
    }
  ]
})

// 设置全局钩子
router.beforeEach(function (to, from, next) {
  store.commit(UPDATE_MAIN_LOADING, {isLoading: true})
  NProgress.start()
  next()
})

router.afterEach(function (to) {
  setTimeout(function () {
    store.commit(UPDATE_MAIN_LOADING, {isLoading: false})
  }, 500)
  NProgress.done()
})

export default router
