import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

export default new Router({
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
        }
      ]
    }
  ]
})
