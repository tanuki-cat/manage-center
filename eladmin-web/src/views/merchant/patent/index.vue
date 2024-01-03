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
            <el-input v-model="form.patentId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="公司id">
            <el-input v-model="form.companyId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="公司名称">
            <el-input v-model="form.companyName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="发明专利">
            <el-input v-model="form.invention" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="实用新型专利">
            <el-input v-model="form.utilityModel" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="外观专利">
            <el-input v-model="form.appearance" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="软件著作">
            <el-input v-model="form.softwareWorks" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="版权">
            <el-input v-model="form.copyright" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="专利进度">
            <el-input v-model="form.progress" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="专利申报时间">
            <el-input v-model="form.filingTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="专利写好时间">
            <el-input v-model="form.writeTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="授权下证时间">
            <el-input v-model="form.authorizationTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="专利数">
            <el-input v-model="form.patentNum" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="年费预警时间">
            <el-input v-model="form.forewarnTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="状态">
            <el-input v-model="form.patentStatus" style="width: 370px;" />
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
        <el-table-column prop="invention" label="发明专利" />
        <el-table-column prop="utilityModel" label="实用新型专利" />
        <el-table-column prop="appearance" label="外观专利" />
        <el-table-column prop="softwareWorks" label="软件著作" />
        <el-table-column prop="copyright" label="版权" />
        <el-table-column prop="progress" label="专利进度" />
        <el-table-column prop="filingTime" label="专利申报时间" />
        <el-table-column prop="writeTime" label="专利写好时间" />
        <el-table-column prop="authorizationTime" label="授权下证时间" />
        <el-table-column prop="patentNum" label="专利数" />
        <el-table-column prop="forewarnTime" label="年费预警时间" />
        <el-table-column prop="patentStatus" label="状态" />
        <el-table-column prop="nickName" label="创建者名字" />
        <el-table-column prop="createTime" label="添加时间" />

        <el-table-column v-if="checkPer(['admin','patent:edit','patent:del'])" label="操作" width="150px" align="center">
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
import crudPatent from '@/api/merchant/patent'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.project'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { patentId: null, companyId: null, companyName: null, invention: null, utilityModel: null, appearance: null, softwareWorks: null, copyright: null, progress: null, filingTime: null, writeTime: null, authorizationTime: null, patentNum: null, forewarnTime: null, patentStatus: null, nickName: null, createBy: null, updateBy: null, createTime: null, updateTime: null }
export default {
  name: 'Patent',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'patent', url: 'api/merchant/patent', idField: 'patentId', sort: 'patentId,desc', crudMethod: { ...crudPatent }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'patent:add'],
        edit: ['admin', 'patent:edit'],
        del: ['admin', 'patent:del']
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
