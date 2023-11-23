<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->

      <!--表单组件-->

      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
 
        <el-table-column prop="companyId" label="公司id" />
        <el-table-column prop="companyName" label="公司名称" />
        <el-table-column prop="content" label="日志内容" />
        <el-table-column prop="nickName" label="创建者" />       
        <el-table-column prop="createTime" label="创建日期" />
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudVisitLog from '@/api/merchant/visit'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, companyId: null, companyName: null, content: null, nickName: null, createBy: null, updateBy: null, createTime: null, updateTime: null }
export default {
  name: 'VisitLog',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '拜访记录', url: 'api/merchant/visitLog', sort: 'id,desc',query: {companyId: getVisit()}, crudMethod: { ...crudVisitLog }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'visitLog:add'],
        edit: ['admin', 'visitLog:edit'],
        del: ['admin', 'visitLog:del']
      },
      companyId: 0,
      rules: {
      }    }
  },
  methods: {
    getVisit() {
      return $route.query.companyId
    },    
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
