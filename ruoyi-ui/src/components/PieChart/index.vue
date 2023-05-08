<template>
  <div>
    <div class="chart" id="myChart" ></div>
  </div>
</template>

<script>
import {returnStatus} from '../../api/piechart/piechart'
import async from "async";
export default {
  name: "index",
  data() {
    return {
      chartData:{
        pending:1,
        onSale:1,
        bidOpening:1,
        bidEvaluated:1,
        underReview:1,
        reviewCompleted:1,
        terminated:1,
        completed:1,
      }
    };
  },
  methods: {
    async getStatus(){
      returnStatus().then(res=>{
        Object.assign(this.chartData,res.data)
        console.log(this.chartData)
        this.drawEcharts();
      })

    },
    drawEcharts() {
      console.log(this.chartData)
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById("myChart"));
      // 绘制图表
      myChart.setOption({
        series: [
          {
            type: 'pie',
            data: [
              {
                value: this.chartData.pending,
                name: '待发售'
              },
              {
                value: this.chartData.pending.onSale,
                name: '发售中'
              },
              {
                value: this.chartData.bidOpening,
                name: '开标中'
              },
              {
                value: this.chartData.bidEvaluated,
                name: '待评标'
              },
              {
                value: this.chartData.underReview,
                name: '评审中'
              },
              {
                value: this.chartData.reviewCompleted,
                name: '评审完成'
              },
              {
                value: this.chartData.terminated,
                name: '已终止'
              },
              {
                value: this.chartData.completed,
                name: '已完成'
              },
            ],
            radius:'50%'
          }
        ]
      })
    }
  },
  mounted() {

    //模板挂载完成后调用
    this.getStatus();

  }
}
</script>

<style scoped>
.chart{
  width: 500px;
  height: 400px;
  margin: 0 auto;
  background-color: #ffffff;
}
</style>
