<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="peoples" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            今日新增公司数
          </div>
          <count-to :start-val="0" :end-val="this.day_companyCount" :duration="2600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="message" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
              今日专利数  
          </div>
          <count-to :start-val="0" :end-val="this.day_projectCount" :duration="3000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="money" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            今日签单金额
          </div>
          <count-to :start-val="0" :end-val="this.day_projectAmount" :duration="3200" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="money" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            今日到账数
          </div>
          <count-to :start-val="0" :end-val="this.day_reallyAmount" :duration="3600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
  
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="peoples" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            2023年公司数
          </div>
          <count-to :start-val="0" :end-val="this.year_2023_companyCount" :duration="2600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="message" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            2023年专利数 
          </div>
          <count-to :start-val="0" :end-val="this.year_2023_projectCount" :duration="3000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="money" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            2023年签单金额
          </div>
          <count-to :start-val="0" :end-val="this.year_2023_projectAmount" :duration="3200" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="money" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            2023年到账数
          </div>
          <count-to :start-val="0" :end-val="this.year_2023_reallyAmount" :duration="3600" class="card-panel-num" />
        </div>
      </div>
    </el-col>

    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="peoples" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            2024年公司数
          </div>
          <count-to :start-val="0" :end-val="this.year_2024_companyCount" :duration="2600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="message" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            2024年专利数 
          </div>
          <count-to :start-val="0" :end-val="this.year_2024_projectCount" :duration="3000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="money" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            2024年签单金额
          </div>
          <count-to :start-val="0" :end-val="this.year_2024_projectAmount" :duration="3200" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="money" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            2024年到账数
          </div>
          <count-to :start-val="0" :end-val="this.year_2024_reallyAmount" :duration="3600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col" >
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="peoples" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            总公司数
          </div>
          <count-to :start-val="0" :end-val="this.companyCount" :duration="2600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col" >
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="message" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
              总签专利数
          </div>
          <count-to :start-val="0" :end-val="this.projectCount" :duration="3000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col" >
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="money" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            总签单金额
          </div>
          <count-to :start-val="0" :end-val="this.projectAmount" :duration="3200" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col" >
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="money" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
           总到账数
          </div>
          <count-to :start-val="0" :end-val="this.reallyAmount" :duration="3600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
  </el-row>
  
</template>

<script>
import CountTo from 'vue-count-to'
//统计
import {getCountAll,getDayCount,getYearCount} from '@/api/data'
export default {
  data() {
    return {
      companyCount: 0,
      projectCount: 0,
      projectAmount: 0,
      reallyAmount :0,
      day_projectAmount: 0,
      day_projectCount: 0,
      day_companyCount: 0,
      day_reallyAmount:0,
      year_2023_projectAmount: 0,
      year_2023_projectCount: 0,
      year_2023_companyCount: 0,
      year_2023_reallyAmount:0,
      year_2024_projectAmount: 0,
      year_2024_projectCount: 0,
      year_2024_companyCount: 0,
      year_2024_reallyAmount:0
    }
  },
  components: {
    CountTo
  },
  created(){
    this.getCountAll()
    this.getDayCount()
    this.get2023YearCount()
    this.get2024YearCount()
  },
  methods: {
    handleSetLineChartData(type) {
      this.$emit('handleSetLineChartData', type)
    },
    getCountAll(){
      getCountAll().then(res=>{
        this.companyCount = res.companyCount
        this.projectCount = res.projectCount
        this.projectAmount = res.projectAmount
        this.reallyAmount =res.reallyAmount
      })
    },
    getDayCount(){
      getDayCount().then(res=>{
        this.day_companyCount = res.companyCount
        this.day_projectCount = res.projectCount
        this.day_projectAmount = res.projectAmount
        this.day_reallyAmount =res.reallyAmount 
        
      })
    },
    get2023YearCount(){
      getYearCount(2023).then(res=>{
        this.year_2023_companyCount = res.companyCount
        this.year_2023_projectCount = res.projectCount
        this.year_2023_projectAmount = res.projectAmount
        this.year_2023_reallyAmount =res.reallyAmount
      })
    },
    get2024YearCount(){
      getYearCount(2024).then(res=>{
        this.year_2024_companyCount = res.companyCount
        this.year_2024_projectCount = res.projectCount
        this.year_2024_projectAmount = res.projectAmount
        this.year_2024_reallyAmount =res.reallyAmount
      })
    }
  }

}
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
        float: right  ;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
