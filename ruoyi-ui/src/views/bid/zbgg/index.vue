<template>
  <div>
    <div class="top">

    </div>
    <div class="content">
      <div class="top" data-v-17f0b034="">
        <div class="left" data-v-17f0b034="">
          <div style="font-size:23px;color:#333" data-v-17f0b034="">
            招标项目
          </div>
          <div style="font-size:23px;font-weight:600;color:rgb(204 204 204)" data-v-17f0b034="">
            Bidding Project
          </div>
        </div>
        <div class="right" data-v-17f0b034="">
          <span @click="gonggaoSumbit('zbgg')">
                        招标公告
                </span>
          <span @click="gonggaoSumbit('biangeng')">
                        变更公告
                </span>
          <span @click="gonggaoSumbit('zhongbiao')">
                        中标公告
                </span>
          <span @click="gonggaoSumbit('yichang')">
                        异常公告
                </span></div>
      </div>
      <div class="list">
        <div v-for="(item,index) in indexList">
          <!--          <div @click="pariculars(item.ggcode)">-->
          <router-link :to="`/zbgg/detail?ggcode=${item.ggcode}`">
            <el-card class="box-card">
              <p>
                {{ item.ggname }}
              </p>
              <div>
                项目类型：{{ item.typename }}
              </div>
              <div>
                公告发布时间:{{ item.ggfbtime }}
              </div>
            </el-card>

          </router-link>
        </div>

      </div>
    </div>
    <div class="news">
      <div class="content" data-v-09d0b2eb="">
        <div class="top" data-v-09d0b2eb="">

          <div class="left" data-v-09d0b2eb="">

            <div style="font-size:23px;color:#333" data-v-09d0b2eb="">
              新闻公告
            </div>
            <div style="font-size:23px;font-weight:600;color:rgb(204 204 204)" data-v-09d0b2eb="">
              News Center
            </div>
          </div>

          <div class="right" data-v-09d0b2eb="">
          </div>

        </div>

        <div class="list" data-v-09d0b2eb="">
          <div class="left" data-v-09d0b2eb="">
            <img src="../../../assets/images/newslogo.png">
          </div>
          <div class="right" >
            <ul style="height:300px;overflow:hidden" ><!---->
              <li >
                <div  class="time">
                  <div style="height: 35px; font-size: 25px;">
                    08
                  </div>
                  <div>
                    2022-11
                  </div>
                </div>
                <div data-v-09d0b2eb="" class="text">
                  <div data-v-09d0b2eb="">
                    中华人民共和国财政部令第94号 --政府采购质疑和投诉办法
                  </div>
                </div>
              </li>
              <li data-v-09d0b2eb="">
                <div data-v-09d0b2eb="" class="time">
                  <div data-v-09d0b2eb="" style="height: 35px; font-size: 25px;">
                    08
                  </div>
                  <div data-v-09d0b2eb="">
                    2022-11
                  </div>
                </div>
                <div data-v-09d0b2eb="" class="text">
                  <div data-v-09d0b2eb="">
                    政府采购非招标采购方式管理办法
                  </div>
                </div>
              </li>
              <li data-v-09d0b2eb="">
                <div data-v-09d0b2eb="" class="time">
                  <div data-v-09d0b2eb="" style="height: 35px; font-size: 25px;">
                    08
                  </div>
                  <div data-v-09d0b2eb="">
                    2022-11
                  </div>
                </div>
                <div data-v-09d0b2eb="" class="text">
                  <div data-v-09d0b2eb="">
                    中华人民共和国民法典
                  </div>
                </div>
              </li>
              <li data-v-09d0b2eb="">
                <div data-v-09d0b2eb="" class="time">
                  <div data-v-09d0b2eb="" style="height: 35px; font-size: 25px;">
                    08
                  </div>
                  <div data-v-09d0b2eb="">
                    2022-11
                  </div>
                </div>
                <div data-v-09d0b2eb="" class="text">
                  <div data-v-09d0b2eb="">
                    中华人民共和国招标投标法（2017年修正）
                  </div>
                </div>
              </li>
              <li data-v-09d0b2eb="">
                <div data-v-09d0b2eb="" class="time">
                  <div data-v-09d0b2eb="" style="height: 35px; font-size: 25px;">
                    08
                  </div>
                  <div data-v-09d0b2eb="">
                    2022-11
                  </div>
                </div>
                <div data-v-09d0b2eb="" class="text">
                  <div data-v-09d0b2eb="">
                    必须招标的工程项目规定
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {listIndex, getIndex, delIndex, addIndex, updateIndex} from "@/api/zbgg/index.js"

export default {
  name: "Index",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 【请填写功能名称】表格数据
      indexList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ggcode: null,
        ggname: null,
        ggfbtime: null,
        hyname: null,
        zbr: null,
        zbdl: null,
        govchild: null,
        ggtype: null,
        typename: null,
        ggendtime: null,
        ggcontent: null,
        addtime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        ggcode: [
          {required: true, message: "公告编号必须以SD开头后面用guid即可保证在咱们的表里面不重复，必要不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.queryParams.ggtype = 'zbgg'
    this.getList();
  },
  methods: {
    pariculars(ggcode) {
      this.$router.push({
        path: '/bid/zbgg/detail',
        query: {
          ggcode: ggcode
        }
      })
    },
    gonggaoSumbit(data) {
      this.queryParams.ggtype = data
      this.getList()
    },
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listIndex(this.queryParams).then(response => {
        this.indexList = response.rows;
        this.total = response.total;
        this.loading = false;
        console.log(response)
      });

    },


  },

};
</script>
<style scoped lang="scss">
.content {
  overflow: hidden;

  .top {
    .left {
      float: left;
    }

    .right {
      float: right;
      padding-top: 20px;

      span:hover {
        cursor: pointer;
        color: aliceblue;
        background-color: #008cff;
      }
    }
  }

  .list {
    margin: -0px -20px;
    clear: both;
    padding: 25px 20px;
    background: #fff;


    .box-card {
      float: left;
      margin: 0 20px 10px 20px;

      p {
        height: 64px;
        color: #252e3a;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        overflow: hidden;
        border-bottom: 1px solid #ededed;
        margin-bottom: 10px;
        font-size: 16px;
        line-height: 28px;
      }

      div {
        height: 20px;
        overflow: hidden;
        font-size: 14px;
        color: #828282;
        margin-top: 5px;
      }

      width: 350px;
      height: 200px;
      padding: 25px 20px;
    }
  }



  max-width: 1200px;
  margin: 0 auto;
}
.news {
  margin-top: 50px;
  .list{
    .left {
      float: left;

    }
    .right{
      float: right;
      width: 560px;
    }
    li{
      list-style: none;
      height: 95px;
      border-bottom: 1px solid #ececec;
      padding: 5px 0;
      cursor: pointer;
    }
  }
}
</style>
