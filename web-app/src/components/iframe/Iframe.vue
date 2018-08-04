<template lang="html">
  <iframe v-if="$route.query.src" :src='$route.query.src' class="iframe" ref="iframe"></iframe>
  <iframe v-else :src="urlPath" class="iframe" ref="iframe"></iframe>
</template>

<script>
export default {
  data () {
    return {
      urlPath: this.getUrlPath() // iframe src 路径
    }
  },
  created () {},
  mounted () {
    this.load()
    this.resize()
  },
  watch: {
  },
  methods: {
    // 加载浏览器窗口变化自适应
    resize () {
      window.onresize = () => {
        this.iframeInit()
      }
    },
    // 加载组件
    load () {
      var flag = true // URL是否包含问号
      if (this.$route.query.src.indexOf('?') === -1) {
        flag = false
      }
      var list = []
      for (var key in this.$route.query) {
        if (key !== 'src' && key !== 'name') {
          var v = this.$route.query[key]
          list.push(`${key}=${v}`)
        }
      }
      list = list.join('&').toString()
      if (flag) {
        this.$route.query.src = `${this.$route.query.src}${
          list.length > 0 ? `&${list}` : ''}`
      } else {
        this.$route.query.src = `${this.$route.query.src}${
          list.length > 0 ? `?${list}` : ''}`
      }
      this.iframeInit()
    },
    // iframe窗口初始化
    iframeInit () {
      const iframe = this.$refs.iframe
      const clientHeight = document.documentElement.clientHeight - 200
      iframe.style.height = `${clientHeight}px`
      if (iframe.attachEvent) {
        iframe.attachEvent('onload', () => {
          // this.hide()
        })
      } else {
        iframe.onload = () => {
          // this.hide()
        }
      }
    },
    getUrlPath: function () {
      // 获取 iframe src 路径
      let url = window.location.href
      url = url.replace('/iframe', '')
      return url
    }
  }
}
</script>

<style>
  .iframe {
    width: 100%;
    height: 100%;
    border: 0;
    overflow: hidden;
    box-sizing: border-box;
  }
</style>
