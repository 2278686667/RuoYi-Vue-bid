<template>
  <div>
    <el-steps :active="number" simple>
      <el-step title="待发售" icon="el-icon-edit">111</el-step>

      <el-step title="发售中" icon="el-icon-upload"></el-step>
      <el-step title="开标中" icon="el-icon-s-flag"></el-step>
      <br/>
      <el-step title="待评标" icon="el-icon-s-flag"></el-step>
      <el-step title="评审中" icon="el-icon-s-flag"></el-step>
      <el-step title="评审完成" icon="el-icon-s-flag"></el-step>
      <el-step title="已完成" icon="el-icon-s-flag"></el-step>
      <el-step title="已终止" icon="el-icon-s-flag"></el-step>
    </el-steps>
  </div>
</template>

<script>

export default {
  name: "index",
  data() {
    return {
      id: null,
      number: "0"
    }
  },
  created() {
    console.log(this.$route.query.id)
    this.id = this.$route.query.id;
    this.number = parseInt(this.id);
  }
}
</script>

<style scoped>
.el-steps {
  margin-top: 100px;
}
</style>
