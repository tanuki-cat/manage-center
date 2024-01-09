<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <!-- <crudOperation :permission="permission" /> -->
      <jdPatent :dataList="dataList" ref="jdPatent" />
      <!--表单组件-->
 
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="statusName" label="进度状态" />
        <el-table-column prop="assignUser" label="指派人" />
        <el-table-column prop="progress" label="专利进度" />
        <el-table-column prop="filingTime" label="专利申报时间" />
        <el-table-column prop="writeTime" label="专利写好时间" />
        <el-table-column prop="authorizationTime" label="授权下证时间" />
        <el-table-column prop="patentNum" label="专利数" />
        <el-table-column prop="forewarnTime" label="年费预警时间" />
        <el-table-column prop="amountPercent" label="金额百分比(%)" />
        <el-table-column prop="nickName" label="最后操作人" />
        <!-- <el-table-column v-if="checkPer(['admin','patentSchedule:edit','patentSchedule:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column> -->
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudPatentSchedule from '@/api/merchant/patentSchedule'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import jdPatent from '@crud/JD.patent.vue'
const defaultForm = { scheduleId: null, patentId: null, scheduleStatus: null, assignUser: null, assignUserId: null, assignStatus: null, progress: null, filingTime: null, writeTime: null, authorizationTime: null, patentNum: null, forewarnTime: null, nickName: null, createBy: null, updateBy: null, createTime: null, updateTime: null }
export default {
  name: 'PatentSchedule',
  components: { pagination, crudOperation, rrOperation, udOperation,jdPatent },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'patentSchedule', url: 'api/merchant/patentSchedule', idField: 'id', sort: 'id,desc', crudMethod: { ...crudPatentSchedule }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'patentSchedule:add'],
        edit: ['admin', 'patentSchedule:edit'],
        del: ['admin', 'patentSchedule:del']
      },
      rules: {
        assignStatus: [
          { required: true, message: '指派状态 1- 作废 0- 指派不能为空', trigger: 'blur' }
        ]
      }    }
  },
  created() {
    this.query.patentId=this.$route.query.patentId;
    this.crud.toQuery()
    this.getDeatils(this.query.patentId);
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    },
    getDeatils(data){
      console.log("data:"+data)
      crudPatentSchedule.details(data).then(res=>{
        console.log(res)
        this.dataList=res;
        const status = res.status>1?res.status:res.status+1
        this.$refs.jdPatent.getSchedule(status==4?5:status)
      })      
    }
  }
}
</script>

<style scoped>

</style>
