<template>
<div>
  <el-button @click="openTZGG">测试</el-button>
</div>
</template>

<script>
export default {
  name: "index",
  methods:{

    // 单纯打开新页面
    openTZGG() {
      this.$tab.openPage("打开的通知公告", "/system/notice");
    },
    // 打开页面之后，并且做一点其他事情。
    openTZGGAndWindows() {
      this.$tab.openPage("打开的通知公告并且弹窗", "/system/notice").then(() => {
        this.$modal.msg("我是弹窗")
      })
    }
  }
}
</script>

<style scoped>

</style>
