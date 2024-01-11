<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <jdProject :dataList="dataList" ref="jdProject"/>
      <!-- <crudOperation :permission="permission" /> -->
      <!--表单组件-->

      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        
        <el-table-column prop="statusName" label="进度状态" />
        <el-table-column prop="assignUser" label="指派人" />
        <el-table-column prop="amountPercent" label="金额百分比(%)" />
        <el-table-column prop="scheduleDesc" label="项目进度" />
        <el-table-column prop="remittanceTime" label="汇款时间" />
        <el-table-column prop="nickName" label="最后操作人" />
        <el-table-column prop="createTime" label="日期" />
        <!-- <el-table-column v-if="checkPer(['admin','projectSchedule:edit','projectSchedule:del'])" label="操作" width="150px" align="center">
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
import crudProjectSchedule from '@/api/merchant/projectSchedule'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import jdProject from '@crud/JD.project.vue'

const defaultForm = { id: null, projectId: null, scheduleStatus: null, assignUser: null, nickName: null, createBy: null, updateBy: null, createTime: null, updateTime: null }
export default {
  name: 'ProjectSchedule',
  components: { pagination, crudOperation, rrOperation, udOperation ,jdProject},
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({queryOnPresenterCreated:false, title: 'projectschedule', url: 'api/merchant/projectSchedule', idField: 'id', sort: 'id,desc', crudMethod: { ...crudProjectSchedule }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'projectSchedule:add'],
        edit: ['admin', 'projectSchedule:edit'],
        del: ['admin', 'projectSchedule:del']
      },
      dataList:{},
      rules: {
      }    }
  },
  created() {
    this.query.projectId=this.$route.query.projectId;
    this.crud.toQuery()
    this.getDeatils(this.query.projectId);
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    },
    getDeatils(data){
      console.log("data:"+data)
      crudProjectSchedule.details(data).then(res=>{
        console.log(res)
        this.dataList=res;
        const status = res.status>1?res.status:res.status+1
        this.$refs.jdProject.getSchedule(status==4?5:status)
      })      
    }
  }
}
</script>

<style scoped>

</style>
