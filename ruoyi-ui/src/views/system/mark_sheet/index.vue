<template>
  <div style="margin: 20px;width: 500px">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="价格因素">
        <el-input-number v-model="form.price" @change="handleChange" :min="1" :max="100" label="描述文字"></el-input-number>
        <el-rate v-model="form.price"></el-rate>
      </el-form-item>
      <el-form-item label="商务因素">
        <el-input-number v-model="form.business" @change="handleChange" :min="1" :max="100"
                         label="描述文字"></el-input-number>
        <el-rate
          v-model="form.business"
          :colors="colors">
        </el-rate>

      </el-form-item>
      <el-form-item label="技术因素">
        <el-input-number v-model="form.techology" @change="handleChange" :min="1" :max="100"
                         label="描述文字"></el-input-number>
        <el-rate v-model="form.techology"></el-rate>
      </el-form-item>
      <el-form-item label="售后服务因素">
        <el-input-number v-model="form.after" @change="handleChange" :min="1" :max="100" label="描述文字"></el-input-number>
        <el-rate v-model="form.after"></el-rate>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>

    <div class="block">
      <el-button @click="readExcelFromRemoteFile">预览</el-button>
    </div>
  </div>
</template>

<script>
import mammoth from "mammoth";
import {listSheet, getSheet, delSheet, addSheet, updateSheet} from "@/api/sheet/sheet";

export default {
  name: "index",
  data() {
    return {
      form: {},

      wordURL: '/profile/upload/2023/03/14/关于高校招投标管理系统的设计与实现的文献综述_20230314190646A001.doc',  //文件地址，看你对应怎末获取、赋值
      value1: null,
      value2: null,
      colors: ['#99A9BF', '#F7BA2A', '#FF9900']  // 等同于 { 2: '#99A9BF', 4: { value: '#F7BA2A', excluded: true }, 5: '#FF9900' }
    }
  },
  methods: {
    onSubmit() {
      this.form.projId = this.$route.query.projId;
      this.form.reviewId = this.$route.query.reviewId;

      addSheet(this.form).then(res => {
        this.$modal.msgSuccess("提交成功");
        console.log(res)
      })
    },
    // 在线查看word文件
    readExcelFromRemoteFile: function () {
      console.log("测试")
      var url = this.wordURL
      var vm = this;
      var xhr = new XMLHttpRequest();
      xhr.open("get", url, true);
      xhr.responseType = "arraybuffer";
      xhr.onload = function () {
        if (xhr.status == 200) {
          mammoth
            .convertToHtml({arrayBuffer: new Uint8Array(xhr.response)})
            .then(function (resultObject) {
              vm.$nextTick(() => {
                // document.querySelector("#wordView").innerHTML =
                //   resultObject.value;
                vm.vHtml = resultObject.value;
              });
            });
        }
      };
      xhr.send();
    },

  },

}
</script>

<style scoped>
.el-rate {
  float: right;
  margin-top: 6px;
}
</style>
