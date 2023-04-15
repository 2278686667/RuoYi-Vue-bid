<template>
  <div style="margin: 20px;width: 500px">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="价格因素">
        <el-input-number v-model="num" @change="handleChange" :min="1" :max="100" label="描述文字"></el-input-number>
        <el-rate v-model="value1"></el-rate>
      </el-form-item>
      <el-form-item label="商务因素">
        <el-input-number v-model="num" @change="handleChange" :min="1" :max="100" label="描述文字"></el-input-number>
        <el-rate
          v-model="value2"
          :colors="colors">
        </el-rate>

      </el-form-item>
      <el-form-item label="技术因素">
        <el-input-number v-model="num" @change="handleChange" :min="1" :max="100" label="描述文字"></el-input-number>

      </el-form-item>
      <el-form-item label="售后服务因素">
        <el-input-number v-model="num" @change="handleChange" :min="1" :max="100" label="描述文字"></el-input-number>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>

    <div class="block">
      <span class="demonstration">默认不区分颜色</span>

    </div>
    <div class="block">
      <span class="demonstration">区分颜色</span>

    </div>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      form: {},
      value1: null,
      value2: null,
      colors: ['#99A9BF', '#F7BA2A', '#FF9900']  // 等同于 { 2: '#99A9BF', 4: { value: '#F7BA2A', excluded: true }, 5: '#FF9900' }
    }
  }
}
</script>

<style scoped>
.el-rate {
  float: right;
  margin-top: 6px;
}
</style>
