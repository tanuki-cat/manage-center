<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="520px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">

          <el-form-item label="公司名称" prop="name">
            <el-input v-model="form.name" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="联系人">
            <el-input v-model="form.userName" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="联系人手机">
            <el-input v-model="form.userMobile" style="width: 350px;" maxlength="11" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <el-dialog :close-on-click-modal="false" :visible.sync="visitVisible"  width="520px">
        4444
      </el-dialog>
      <!--表格渲染-->
      <el-table
       ref="table"
       v-loading="crud.loading"
       :data="crud.data"
       size="small"
       style="width: 100%;"
       @select="crud.selectChange"
       @select-all="crud.selectAllChange"
       @selection-change="crud.selectionChangeHandler">
        <el-table-column :selectable="checkboxT" type="selection" width="55" />
        <el-table-column prop="name" label="公司名称" />
        <el-table-column prop="userName" label="联系人" />
        <el-table-column prop="userMobile" label="联系人手机" />
        <el-table-column v-if="checkPer(['admin','company:edit','company:del'])" label="操作" width="550px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
              @addVisit="getVisit"
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
import crudCompany from '@/api/merchant/company/company'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.visit'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, name: null, userName: null, userMobile: null, createBy: null, updateBy: null, createTime: null, updateTime: null }
export default {
  name: 'Company',
  components: { pagination, crudOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '公司管理', url: 'api/merchant/company', sort: 'id,desc', crudMethod: { ...crudCompany }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'company:add'],
        edit: ['admin', 'company:edit'],
        del: ['admin', 'company:del']
      },
      visitVisible: false,
      visitFrom: {
        companyId: 0,
        companyTitle: ''
        

      },
      rules: {
        name: [
          { required: true, message: '公司名称不能为空', trigger: 'blur' }
        ]
      }}
  },
  methods: {
    getVisit(data) {
      console.log(data);
      this.visitVisible = true
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
