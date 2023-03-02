<template>
  <div>
<!--    <button @click="getList">点击了</button>-->
  </div>
</template>

<script>

import { listFolder, getFolder, delFolder, addFolder, updateFolder,folderTreeSelect } from "@/api/folder/folder";
export default {
  props:['testto'],
  created() {

  },
  name: "test",
  data(){
    return{
        queryParams: {
        pageNum: 1,
          pageSize: 20,
          parentId: null,
          projId: 16,
          folderName: null,
          folderPath: null,
          status: null,
          isDeleted: null,
          folderId:26

      },
    }
  },
  methods:{
    getList() {
      this.loading = true;
      console.log(this.queryParams)

      listFolder(this.queryParams).then(response => {
      });
    },
  }

}
</script>

<style scoped>

</style>
