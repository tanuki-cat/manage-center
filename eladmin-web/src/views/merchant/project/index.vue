

<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="公司名称">
            <el-input v-model="form.companyName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="项目名称">
            <el-input v-model="form.projectName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="项目描述">
            <el-input v-model="form.projectDesc" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="项目金额">
            <el-input v-model="form.projectAmount" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="项目状态">
            <el-input v-model="form.projectStatus" style="width: 370px;" />
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
        <el-table-column prop="companyName" label="公司名称" />
        <el-table-column prop="projectName" label="项目名称" />
        <el-table-column prop="projectDesc" label="项目描述" />
        <el-table-column prop="projectAmount" label="项目金额" />
        <el-table-column prop="projectStatus" label="项目状态" />
        <el-table-column prop="nickName" label="创建者" />
        <el-table-column v-if="checkPer(['admin','project:edit','project:del'])" label="操作" width="450px" align="center">
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
import crudProject from '@/api/merchant/project'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.project'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, companyId: null, companyName: null, projectName: null, projectDesc: null, projectAmount: null, projectStatus: null, nickName: null, createBy: null, updateBy: null, createTime: null, updateTime: null }
export default {
  name: 'Project',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'project', url: 'api/merchant/project', idField: 'id', sort: 'id,desc', crudMethod: { ...crudProject }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'project:add'],
        edit: ['admin', 'project:edit'],
        del: ['admin', 'project:del']
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
