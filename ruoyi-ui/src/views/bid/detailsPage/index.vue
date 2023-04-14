<template>
  <div>
    <p v-html="data.remark" style="margin-left: 20px"></p>

  </div>
</template>

<script>
import {

  getInvite_tenders,

} from "@/api/bid/invite_tenders";

export default {
  name: "index",
  data() {
    return {
      data: ""
    }
  },
  created() {
    getInvite_tenders(this.$route.query.id).then(res => {
      this.data = res.data;
      console.log(this.data)

    })
  }

}
</script>

<style scoped>

</style>
