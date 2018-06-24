import Vue from 'vue'
import VueI18n from 'vue-i18n'
import {Ch} from '@/assets/translate/Chinese'
import {En} from '@/assets/translate/English'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(VueI18n)

var lang = navigator.language || navigator.userLanguage
lang = lang.substr(0, 2)

export default new VueI18n({
  locale: lang === 'zh' ? 'Chinese' : 'English', // 语言标识
  messages: {
    Chinese: Ch,
    English: En
  }
})
