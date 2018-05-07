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
        }
      ]
    }
  ]
})
