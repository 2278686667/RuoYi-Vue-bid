<template>
<div>
  <div>
    <p v-html="data.ggcontent" style="margin-left: 20px"></p>

  </div>
</div>
</template>

<script>
import {listIndex, getIndex, delIndex, addIndex, updateIndex} from "@/api/zbgg/index.js"
export default {
  name: "detail",
  data() {
    return {
      form:{
        ggcode:'',
      },
      data:{

      }
    }
  },
  created() {
    console.log(this.$route.query.ggcode)
    this.form.ggcode=this.$route.query.ggcode
    this.getList()

  },
  methods:{
    getList(){
      listIndex(this.form).then(res=>{
        this.data=res.rows[0]
        console.log(this.data[0].ggcontent)
      })
    }
  }
}
</script>

<style scoped>

</style>
