<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  margin: 0;
  width: 100%;
  min-height: 100%;
}

html,body {
  margin: 0;
  width: 100%;
  min-height: 100%;
  display: flex;
}
</style>
