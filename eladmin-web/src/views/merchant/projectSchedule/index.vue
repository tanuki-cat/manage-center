<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <jdProject :dataList="dataList" ref="jdProject"/>
      <crudOperation :permission="permission" />
      <!--表单组件-->

      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="scheduleId" label="主键ID" />
        <el-table-column prop="projectId" label="项目id" />
        <el-table-column prop="scheduleStatus" label="进度状态" />
        <el-table-column prop="assignUser" label="指派人" />
        <el-table-column prop="nickName" label="创建者名字" />
        <el-table-column prop="createBy" label="创建者" />
        <el-table-column prop="updateBy" label="更新者" />
        <el-table-column prop="createTime" label="创建日期" />
        <el-table-column prop="updateTime" label="更新时间" />
        <el-table-column v-if="checkPer(['admin','projectSchedule:edit','projectSchedule:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
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
        this.$refs.jdProject.getSchedule(res.status)
      })      
    }
  }
}
</script>

<style scoped>

</style>
