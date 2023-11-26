<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="主键ID">
            <el-input v-model="form.scheduleId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="项目id">
            <el-input v-model="form.projectId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="进度状态">
            <el-input v-model="form.scheduleStatus" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="指派人">
            <el-input v-model="form.assignUser" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="创建者名字">
            <el-input v-model="form.nickName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="创建者">
            <el-input v-model="form.createBy" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="更新者">
            <el-input v-model="form.updateBy" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="创建日期">
            <el-input v-model="form.createTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="更新时间">
            <el-input v-model="form.updateTime" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
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

const defaultForm = { scheduleId: null, projectId: null, scheduleStatus: null, assignUser: null, nickName: null, createBy: null, updateBy: null, createTime: null, updateTime: null }
export default {
  name: 'ProjectSchedule',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'projectschedule', url: 'api/merchant/projectSchedule', idField: 'scheduleId', sort: 'scheduleId,desc', crudMethod: { ...crudProjectSchedule }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'projectSchedule:add'],
        edit: ['admin', 'projectSchedule:edit'],
        del: ['admin', 'projectSchedule:del']
      },
      rules: {
      }    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
