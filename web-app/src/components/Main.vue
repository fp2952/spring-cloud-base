<template @keyup.esc="requestFullScreen">
  <el-container class="main">
    <!--header -->
    <el-header class="main-top">
      <!-- 顶栏 -->
      <el-row  type="flex" justify="space-between" style="top: 50%;margin-top: -13px;">
        <el-col :span="2" style="text-align: center">{{$t('message.title')}}</el-col>
        <el-col :span="6" class="main-dropdown">
          <el-tooltip class="item" effect="dark" content="全屏切换" placement="bottom">
            <img src="../assets/img/full-screen.png" class="main-avatar" style="margin-right: 20px" @click="fullScreen"/>
          </el-tooltip>
          <el-dropdown @command="handleTranslate" style="margin-right: 30px">
            <span class="el-dropdown-link">
              {{lang}}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="Chinese">中文</el-dropdown-item>
              <el-dropdown-item command="English">English</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <img src="../assets/img/account.png" class="main-avatar circel" />
          <el-dropdown @command="handleCommand">
            <span class="el-dropdown-link">
              {{userInfo.userName}}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="modify">{{$t('message.MODIFY_PASSWORD')}}</el-dropdown-item>
              <el-dropdown-item command="logout">{{$t('message.LOGOUT')}}</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-col>
      </el-row>
    </el-header>
    <el-container>
      <!--左侧菜单 -->
      <el-aside class="main-left">
        <el-menu class="el-menu-vertical-demo" :collapse="isCollapse" :unique-opened="true" :router="true" text-color="#fff" background-color="#545c64" active-text-color="#ffd04b">
          <!--collapse-->
          <el-menu-item style="text-align: center;" @click="collapse" index="">
            <i :class="isCollapse ? 'el-icon-d-arrow-right' : 'el-icon-d-arrow-left'"></i>
          </el-menu-item>
          <main-menu v-for="item in menuInfo" :module="item" :key="item.id" @click="clickMenu"></main-menu>
        </el-menu>
      </el-aside>
      <el-container class="main-right">
        <!--面包屑、标签-->
        <el-header class="main-breadcrumxxb">
          <el-row>
            <el-col :span="24">
              <el-breadcrumb separator="/" class="main-breadcrumb">
                <el-breadcrumb-item v-for="item in breadcrumb" :to="item.path ? { path: item.path } : null" :key="item.path">
                  {{$t(item.code)}}
                </el-breadcrumb-item>
              </el-breadcrumb>
              <div class="main-tags">
                <el-tag size="medium" :key="index" v-for="(tag, index) in tags" @close="handleClose(index,tag)" closable :type="tag.active == false ? 'info' : null" style="margin-right: 10px;">
                  <span @click="clickTag(tag)">{{$t(tag.code)}}</span>
                </el-tag>
              </div>
            </el-col>
          </el-row>
        </el-header>
        <!--content -->
        <el-main class="main-content" v-loading="mainLoading"
                 :element-loading-text="$t('message.LOADING')">
          <router-view></router-view>
        </el-main>
        <!--footer-->
        <el-footer height="40px">
          <el-col :span="24" class="main-foot">
            2018 - 2019 © fp2952
          </el-col>
        </el-footer>
      </el-container>
    </el-container>
    <modify-password ref="modifyPassword"></modify-password>
  </el-container>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import ModifyPassword from './user/ModifyPassword.vue'

var Menu = {
  template: '<el-menu-item v-if="module.subModules.length === 0" :index="module.modulePath" ' +
  ':title="module.moduleName" :parent="module.parentId" :key="module.id" :disabled="module.active !== 1" :code="module.moduleCode" @click="click">' +
  '<i :class="module.moduleIcon ? module.moduleIcon : \'el-icon-star-off\'"></i>' +
  '<span slot="title">{{$t(module.moduleCode)}}</span>' +
  '</el-menu-item>' +
  '<el-submenu v-else-if="module.subModules.length > 0" :title="module.moduleName" :parent="module.parentId" :code="module.moduleCode"' +
  ':index="module.modulePath" :key="module.id">' +
  '<template slot="title">' +
  '<i :class="module.moduleIcon ? module.moduleIcon : \'el-icon-star-off\'"></i>' +
  '<span slot="title">{{$t(module.moduleCode)}}</span>' +
  '</template>' +
  '<main-menu v-for="item in module.subModules" :module="item" :key="item.id" @click="click"></main-menu>' +
  '</el-submenu>',
  name: 'main-menu',
  props: ['module'],
  methods: {
    click (menu) {
      this.$emit('click', menu)
    }
  }
}

export default {
  components: {
    'main-menu': Menu,
    'modify-password': ModifyPassword
  },
  data () {
    return {
      tags: [],
      tagLenght: 15,
      breadcrumb: [{ title: '首页', code: 'HOME_PAGE', path: '/' }],
      isCollapse: false,
      lang: '中文'
    }
  },
  created () {
    this.geRoleInfo()
  },
  methods: {
    ...mapActions([
      'geRoleInfo'
    ]),
    clickMenu (menu, parent) {
      // 点击事件调用
      if (!parent) {
        // 清空面包屑
        this.breadcrumb = []
        this.breadcrumb.push({ title: menu.$attrs.title, code: menu.$attrs.code, path: menu.index })
        this.resetTags(menu.index)
        // 添加标签
        if (!this.checkTags(menu.index)) {
          // 如果标签总数已到达最大值，则删除第一个标签再进行添加
          if (this.tags.length === this.tagLenght) {
            this.tags.splice(0, 1)
          }
          this.tags.push({ title: menu.$attrs.title, code: menu.$attrs.code, path: menu.index, active: true, menu: menu })
        }
      } else {
        this.breadcrumb.push({ title: menu.$attrs.title, code: menu.$attrs.code, path: null })
      }
      if (menu.$attrs.parent) {
        this.clickMenu(menu.$parent.$parent, true)
      } else {
        // 递归完毕
        this.breadcrumb.push({ title: '首页', code: 'HOME_PAGE', path: '/' })
        // 反向数组
        this.breadcrumb.reverse()
      }
    },
    resetTags (path) {
      this.tags.forEach(item => {
        if (item.path === path) {
          item.active = true
        } else {
          item.active = false
        }
      })
    },
    checkTags (path) {
      // 检查是否已存在
      for (var i = 0; i < this.tags.length; i++) {
        if (this.tags[i].path === path) {
          return true
        }
      }
      return false
    },
    handleCommand (command) {
      var self = this
      if (command === 'logout') {
        self.$auth.logout()
      } else if (command === 'modify') {
        self.$refs.modifyPassword.show(self.userInfo, self.$i18n.locale)
      }
    },
    handleTranslate (command) {
      var self = this
      if (command === 'Chinese') {
        this.lang = '中文'
      } else if (command === 'English') {
        this.lang = 'English'
      }
      self.$i18n.locale = command
    },
    clickTag (tag) {
      // 点击跳转到指定页面
      this.$router.push({ path: tag.path })
      this.clickMenu(tag.menu)
    },
    handleClose (index, tag) {
      // 如果已经没有标签页
      if (this.tags.length === 1) {
        this.$router.push({ path: '/' })
        this.breadcrumb = [{ title: '首页', code: 'HOME_PAGE', path: '/' }]
        this.tags.splice(index, 1)
        return
      }
      // 如果不是当前选中的，直接删除
      if (!tag.active) {
        this.tags.splice(index, 1)
        return
      }
      // 如果不是最后一个，关闭后则选中下一个标签页
      if (this.tags.length - 1 !== index) {
        this.$router.push({ path: this.tags[index + 1].path })
        this.clickMenu(this.tags[index + 1].menu)
      } else if (this.tags.length - 1 === index) {
        // 如果是最后一个，则选中上一个
        this.$router.push({ path: this.tags[index - 1].path })
        this.clickMenu(this.tags[index - 1].menu)
      }
      this.tags.splice(index, 1)
    },
    collapse () {
      this.isCollapse = !this.isCollapse
    },
    fullScreen () {
      if (document.fullscreenElement ||
          document.msFullscreenElement ||
          document.mozFullScreenElement ||
          document.webkitFullscreenElement || false) {
        this.exitFullscreen()
      } else {
        this.requestFullScreen()
      }
    },
    // 进入全屏
    requestFullScreen () {
      var de = document.documentElement
      if (de.requestFullscreen) {
        de.requestFullscreen()
      } else if (de.mozRequestFullScreen) {
        de.mozRequestFullScreen()
      } else if (de.webkitRequestFullScreen) {
        de.webkitRequestFullScreen()
      }
    },
    // 退出全屏
    exitFullscreen () {
      var de = document
      if (de.exitFullscreen) {
        de.exitFullscreen()
      } else if (de.mozCancelFullScreen) {
        de.mozCancelFullScreen()
      } else if (de.webkitCancelFullScreen) {
        de.webkitCancelFullScreen()
      }
    }
  },
  computed: {
    ...mapGetters([
      'userInfo',
      'menuInfo',
      'mainLoading'
    ])
  }
}
</script>

<style>
  .main-top {
    padding: 15px;
    color: #fff;
    background-color: #414a52;
  }
  .main {
    height:100%;
    display: flex;
    background: #f5f7f9;
  }
  .main-left {
    display: inline-block;
    width: auto !important;
  }
  .main-right {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  .main-content {
    background-color: #fff;
    margin: 15px;
    border-radius: 2px;
    flex-grow: 1;
    padding: 10px;
  }
  .main-foot {
    display: flex;
    justify-content: center;
    align-items: center;
    color: #9ea7b4;
    font-size: 12px;
    line-height: 1.5;
    margin-bottom: 15px;
  }
  .main-dropdown {
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }
  .main-avatar {
    width: 25px;
    height: 25px;
    margin-right: 5px;
  }
  .circel {
    border-radius: 25px;
    border: 1px solid #ccc;
  }
  .main-breadcrumxxb {
    height: 40px !important;
  }
  .main-breadcrumb {
    padding-top: 20px;
    float: left;
  }
  .main-tags {
    padding-top: 12px;
    margin-left: 20px;
    float: left;
  }
  .el-menu-vertical-demo {
    height: 100%;
  }
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
  }
  .el-dropdown-link {
    cursor: pointer;
    color: #fff;
  }
</style>
