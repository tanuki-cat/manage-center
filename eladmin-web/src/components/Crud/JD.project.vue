<template>
    <el-steps :active="activeStep" align-center finish-status="success" style="margin: 20px;">
      <el-step v-for="(step, index) in steps" :key="index" :title="step.title" :description="getStepDescription(index)"></el-step>
</el-steps>
  </template>

  <script>
export default {
    props:{
        dataList: {
            type: Object
        }
    },
    data(){
      return {
        activeStep:0,
      stepDescriptions:['已完成', '正在进行中', '未开始'],
      steps: [
        { title: '业务', description: '已完成' },
        { title: '项目组', description: '已完成' },
        { title: '项目经理', description: '正在进行中' },
        { title: '财务', description: '未开始' },
        { title: '项目完成', description: '未开始' }
      ]
      }
   
    },
    computed: {
    // 计算属性，根据当前激活步骤动态设置后续步骤状态
    dynamicDescriptions() {
      return this.steps.map((step, index) => this.getStepDescription(index));
    }
  },
    methods:{
      getSchedule(num){      
        this.activeStep=num

      },
      // 根据索引获取步骤的描述
    getStepDescription(index) {
      if (index < this.activeStep) {
        return '已完成';
      } else if (index === this.activeStep) {
        return '正在进行中';
      } else {
        return '未开始';
      }
    }
    }
}
</script>