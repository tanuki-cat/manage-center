

<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input v-model="query.companyName"
          clearable size="small"
          placeholder="输入公司名称进行搜索"
          style="width: 230px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <el-input v-model="query.projectName"
          clearable size="small"
          placeholder="输入项目名称进行搜索"
          style="width: 200px;"
          class="filter-item" @keyup.enter.native="crud.toQuery"
        />
        <el-input v-model="query.projectTag"
          clearable size="small"
          placeholder="输入项目类型进行搜索"
          style="width: 200px;"
          class="filter-item" @keyup.enter.native="crud.toQuery"
        />
        <el-input v-model="query.areas"
          clearable size="small"
          placeholder="输入地区进行搜索"
          style="width: 200px;"
          class="filter-item" @keyup.enter.native="crud.toQuery"
        />
        <el-select
              v-model="query.projectStatus"
              clearable
              size="small"
              placeholder="状态"
              class="filter-item"
              style="width: 90px"
              @change="crud.toQuery"
            >
              <el-option
                v-for="item in enabledTypeOptions"
                :key="item.key"
                :label="item.display_name"
                :value="item.key"
              />
            </el-select>
        <!--年份-->
        <el-date-picker
          v-model="query.year"
          type="year"
          style="width: 115px;position: relative; top: -4px "
          placeholder="选择年份"
          value-format="yyyy"
          format="yyyy 年"
          @change="crud.toQuery"
        >
        </el-date-picker>
        <rrOperation />
      </div>
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
          <el-form-item label="项目类型">
            <el-input v-model="form.projectTag" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="地区">
            <el-input v-model="form.areas" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
 <!--变更业务员-->
 <el-dialog :close-on-click-modal="false" :visible.sync="editUserVisible" width="520px" :title="变更业务员">
        <el-form ref="form" :model="editUserFrom"  size="small" label-width="100px">
          <el-form-item label="公司名称">
            <el-input v-model="editUserFrom.companyName" style="width: 350px;" disabled />
          </el-form-item>
          <el-form-item label="项目名称">
            <el-input v-model="editUserFrom.projectName" style="width: 350px;" disabled />
          </el-form-item>

          <el-form-item label="变更业务员">
              <el-select
                v-model="editUserFrom.assignUser"
                style="width: 178px"
                placeholder="请选择"
                prop="content"
              >
                <el-option
                  v-for="item in userList"
                  :key="item.nickName"
                  :label="item.nickName"
                  :value="item.id"
                  @click.native="selectEditUser(item)"
                />
              </el-select>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="editUserClose()">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="$event => editUserForm()">确认</el-button>
        </div>
      </el-dialog>
      <!--派发任务-->
      <el-dialog :close-on-click-modal="false" :visible.sync="userVisible" width="520px" :title="派发任务">
        <el-form ref="form" :model="userProjectFrom" :rules="visitRules" size="small" label-width="100px">
          <el-form-item label="公司名称">
            <el-input v-model="userProjectFrom.companyName" style="width: 350px;" disabled />
          </el-form-item>
          <el-form-item label="项目名称">
            <el-input v-model="userProjectFrom.projectName" style="width: 350px;" disabled />
          </el-form-item>

          <el-form-item label="指派人员">
              <el-select
                v-model="userProjectFrom.assignUser"
                style="width: 178px"
                placeholder="请选择"
                prop="content"
              >
                <el-option
                  v-for="item in userList"
                  :key="item.nickName"
                  :label="item.nickName"
                  :value="item.id"
                  @click.native="selectUser(item)"
                />
              </el-select>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="visitClose()">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="$event => addUserProject()">确认</el-button>
        </div>
      </el-dialog>
         <!--完成任务-->
         <el-dialog :close-on-click-modal="false" :visible.sync="finishVisible" width="520px" :title="完成任务">
          <el-form ref="form" :model="finishFrom" :rules="visitRules" size="small" label-width="100px">
            <el-form-item label="公司名称">
              <el-input v-model="finishFrom.companyName" style="width: 350px;" disabled />
            </el-form-item>
            <el-form-item label="项目名称">
            <el-input v-model="finishFrom.projectName" style="width: 350px;" disabled />
          </el-form-item>
          <el-form-item label="金额百分比">
            <el-input v-model="finishFrom.amountPercent" style="width: 350px;" disabled />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="finishClose()">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="$event => addProjectComplete()">确认</el-button>
        </div>
      </el-dialog>
        <!--项目经理填写-->
        <el-dialog :close-on-click-modal="false" :visible.sync="projectVisible" width="520px" :title="填写项目">
        <el-form ref="form" :model="projectFrom" :rules="rules" size="small" label-width="100px">
          <el-form-item label="公司名称">
            <el-input v-model="projectFrom.companyName" style="width: 350px;" disabled />
          </el-form-item>
          <el-form-item label="项目名称">
            <el-input v-model="projectFrom.projectName" style="width: 350px;" disabled />
          </el-form-item>
          <el-form-item label="项目进度" prop="scheduleDesc">
            <el-input v-model="projectFrom.scheduleDesc" style="width: 350px;" />
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="projectClose()">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="$event => addProjectManager()">填写</el-button>
          <el-button type="danger" @click="goTransfer()">转交</el-button>
        </div>
      </el-dialog>
         <!--项目经理填写-->
         <el-dialog :close-on-click-modal="false" :visible.sync="projectsVisible" width="520px" :title="填写项目">
        <el-form ref="form" :model="projectsFrom" :rules="rules" size="small" label-width="100px">
          <el-form-item label="公司名称">
            <el-input v-model="projectsFrom.companyName" style="width: 350px;" disabled />
          </el-form-item>
          <el-form-item label="项目名称">
            <el-input v-model="projectsFrom.projectName" style="width: 350px;" disabled />
          </el-form-item>
          <el-form-item label="项目进度" prop="scheduleDesc">
            <el-input v-model="projectsFrom.scheduleDesc" style="width: 350px;" />
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="projectsClose()">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="$event => addProjectManagers()">填写</el-button>
          <el-button type="danger" @click="goTransfers()">转交</el-button>
        </div>
      </el-dialog>
         <!--财务填写-->
         <el-dialog :close-on-click-modal="false" :visible.sync="financeVisible" width="520px" :title="财务填写">
        <el-form ref="form" :model="financeFrom" :rules="rules" size="small" label-width="100px">
          <el-form-item label="公司名称">
            <el-input v-model="financeFrom.companyName" style="width: 350px;" disabled />
          </el-form-item>
          <el-form-item label="项目名称">
            <el-input v-model="financeFrom.projectName" style="width: 350px;" disabled />
          </el-form-item>
          <el-form-item label="金额百分比" prop="amountPercent">
            <el-input v-model="financeFrom.amountPercent" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="汇款时间" prop="">
            <el-date-picker v-model="financeFrom.remittanceTime" style="width: 350px;" type="datetime" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="financeClose()">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="$event => addProjectFinance()">填写</el-button>
          <el-button type="danger" @click="goFinish()">转交项目经理</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="companyName" label="公司名称" />
        <el-table-column prop="projectName" label="项目名称" />
        <el-table-column prop="projectDesc" label="项目描述" />
        <el-table-column prop="areas" label="地区" />
        <el-table-column prop="userName" label="联系人" />
        <el-table-column prop="userMobile" label="联系人电话" />
        <el-table-column prop="projectAmount" label="项目金额" />
        <el-table-column prop="amountPercent" label="金额百分比" />
        <el-table-column prop="projectTag" label="项目类型" />
        <el-table-column prop="statusName" label="项目状态" />
        <el-table-column prop="createTime" label="签单日期" />
        <el-table-column prop="nickName" label="创建者" />
        <el-table-column v-if="checkPer(['admin','project:detail','project:del'])" label="操作" width="450px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
              @addProject="getUserProject"
              @upProject="getUserProject"
              @projectManager="getProjectManager"
              @projectFinance="getProjectFinance"
              @editUser="getEditUser"
              @projectManagers="getProjectManagers"
              @projectComplete="getProjectComplete"
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
import crudOperation from '@crud/CRUD.project'
import udOperation from '@crud/UD.project'
import pagination from '@crud/Pagination'
import crudUser from '@/api/system/user'
import projectSchedule from '@/api/merchant/projectSchedule'

const defaultForm = { id: null, companyId: null, companyName: null, projectName: null, projectDesc: null, projectAmount: null, projectStatus: null, nickName: null, createBy: null, updateBy: null, createTime: null, updateTime: null,areas:null }
export default {
  name: 'Project',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '项目', url: 'api/merchant/project', idField: 'id', sort: 'id,desc', crudMethod: { ...crudProject }})
  },
  data() {
    return {
      userList:[],
      userVisible:false,
      projectVisible:false,
      projectsVisible:false,
      //财务
      financeVisible:false,
      editUserVisible:false,
      finishVisible:false,
      enabledTypeOptions: [
        { key: '0', display_name: '创建' },
        { key: '1', display_name: '进行中' },
        { key: '2', display_name: '完结' }
      ],
      editUserFrom: {
        companyId: 0,
        companyName: '',
        projectId:'',
        projectName:'',
        assignUser:[],
        assignUserId:0
      },
      userProjectFrom: {
        companyId: 0,
        companyName: '',
        projectId:'',
        projectName:'',
        assignUser:[],
        assignUserId:0,
        amountPercent:0
      },
      finishFrom:{
        companyId: 0,
        companyName: '',
        projectId:'',
        projectName:'',
        amountPercent:0
      },
      projectFrom: {
        companyId: 0,
        companyName: '',
        projectId:'',
        projectName:'',
        scheduleDesc:'',
        amountPercent:0
      },
      projectsFrom: {
        companyId: 0,
        companyName: '',
        projectId:'',
        projectName:'',
        scheduleDesc:'',
        amountPercent:0
      },
      financeFrom: {
        companyId: 0,
        companyName: '',
        projectId:'',
        projectName:'',
        amountPercent:0,
        remittanceTime:''
      },
      permission: {
        add: ['admin', 'project:add'],
        edit: ['admin', 'project:edit'],
        del: ['admin', 'project:del'],
        leader: ['admin', 'project:leader'],
        //项目经理
        manager: ['admin', 'project:manager'],
        //财务
        finance: ['admin', 'project:finance'],
        //详情
        detail: ['admin', 'project:detail'],
        //变更业务
        editUser: ['admin', 'project:editUser']
      },
      rules: {
        scheduleDesc: [
          { required: true, message: '项目进度不能为空', trigger: 'blur' }
        ],
        amountPercent: [
          { required: true, message: '金额百分比不能为空', trigger: 'blur' }
        ]
      },
      visitRules: {
        content: [
          { required: true, message: '指派人员不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    },
    visitClose() {
      this.userVisible = false
    },
    projectClose() {
      this.projectVisible = false
    },
    financeClose() {
      this.financeVisible = false
    },
    editUserClose() {
      this.editUserVisible = false
    },
    projectsClose(){
      this.projectsVisible = false
    },
    getEditUser(data){
      this.editUserFrom.companyId = data.companyId
      this.editUserFrom.companyName = data.companyName
      this.editUserFrom.projectId = data.id
      this.editUserFrom.projectName =data.projectName
      this.editUserFrom.assignUser=''
      this.editUserFrom.assignUserId=0
      this.editUserVisible = true
      crudUser.businessList().then(res => {
        console.log(res)
        this.userList=res
      })

    },
    getUserProject(data) {
      console.log(data)
      this.userProjectFrom.companyId = data.companyId
      this.userProjectFrom.companyName = data.companyName
      this.userProjectFrom.projectId = data.id
      this.userProjectFrom.projectName =data.projectName
      this.userProjectFrom.assignUser=''
      this.userProjectFrom.assignUserId=0
      this.userProjectFrom.amountPercent=data.amountPercent
      this.userVisible = true
      crudUser.roleList().then(res => {
        console.log(res)
        this.userList=res
      })
    },
    selectUser(data) {
      this.userProjectFrom.assignUserId =data.id
      this.userProjectFrom.assignUser =data.nickName

    },
    selectEditUser(data) {
      this.editUserFrom.assignUserId =data.id
      this.editUserFrom.assignUser =data.nickName
    },
    editUserForm(){
      if(this.editUserFrom.assignUserId==0){
        this.$message({
          message: '请选择业务员',
          type: 'warning'
        })
        return false
      }

      this.$refs['form'].validate((valid) => {
        if (valid) {
          projectSchedule.editUser(this.editUserFrom).then(() => {
            this.crud.notify('添加成功', 'success')
            this.editUserVisible = false
            //刷新表格
            this.crud.refresh()
          })
        }
      })

    },
    deleteTag(){
      this.userProjectFrom.assignUser = ''
      this.userProjectFrom.assignUserId =0
    },
    addUserProject(){
      if(this.userProjectFrom.assignUserId==0){
        this.$message({
          message: '请选择指派人员',
          type: 'warning'
        })
        return false
      }

      this.$refs['form'].validate((valid) => {
        if (valid) {
          projectSchedule.assign(this.userProjectFrom).then(() => {
            this.crud.notify('添加成功', 'success')
            this.userVisible = false
            //刷新表格
            this.crud.refresh()
          })
        }
      })
    },
    //项目经理
    getProjectManager(data){
      //清空当前值
      this.projectFrom.scheduleDesc=''
      this.projectFrom.companyId = data.companyId
      this.projectFrom.companyName = data.companyName
      this.projectFrom.projectId = data.id
      this.projectFrom.projectName =data.projectName
      this.projectFrom.amountPercent=data.amountPercent
      this.projectVisible = true
    },
    addProjectManager(){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          projectSchedule.manager(this.projectFrom).then(() => {
            this.crud.notify('提交成功', 'success')
            this.projectVisible = false
            //刷新表格
            this.crud.refresh()
          })
        }
      })
    },
    //转交
    goTransfer(){
      projectSchedule.transfer(this.projectFrom).then(() => {
            this.crud.notify('转交成功', 'success')
            this.projectVisible = false
            //刷新表格
            this.crud.refresh()
      })
    },

/********************再次填写****************************/
getProjectManagers(data){
      //清空当前值
      this.projectsFrom.scheduleDesc=''
      this.projectsFrom.companyId = data.companyId
      this.projectsFrom.companyName = data.companyName
      this.projectsFrom.projectId = data.id
      this.projectsFrom.projectName =data.projectName
      this.projectsFrom.amountPercent=data.amountPercent
      this.projectsVisible = true
    },
    addProjectManagers(){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          projectSchedule.managers(this.projectsFrom).then(() => {
            this.crud.notify('提交成功', 'success')
            this.projectsVisible = false
            //刷新表格
            this.crud.refresh()
          })
        }
      })
    },
    //转交
    goTransfers(){
      projectSchedule.transfers(this.projectsFrom).then(() => {
            this.crud.notify('提交', 'success')
            this.projectsVisible = false
            //刷新表格
            this.crud.refresh()
      })
    },

    /*******************end******************************/
     /********************组长****************************/
     getProjectComplete(data){
      console.log(data)
      this.finishFrom.companyId = data.companyId
      this.finishFrom.companyName = data.companyName
      this.finishFrom.projectId = data.id
      this.finishFrom.projectName =data.projectName
      this.finishFrom.amountPercent=data.amountPercent
      this.finishVisible = true
    },
    addProjectComplete(){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          projectSchedule.finish(this.finishFrom).then(() => {
            this.crud.notify('提交成功', 'success')
            this.finishVisible = false
            //刷新表格
            this.crud.refresh()
          })
        }
      })
    },
    finishClose(){
      this.finishVisible = false
    },
    /*******************end******************************/


    //财务
    getProjectFinance(data){
      //清空当前值
      this.financeFrom.companyId = data.companyId
      this.financeFrom.companyName = data.companyName
      this.financeFrom.projectId = data.id
      this.financeFrom.projectName =data.projectName
      this.financeFrom.amountPercent=data.amountPercent
      this.financeFrom.remittanceTime=''
      this.financeVisible = true
    },
    addProjectFinance(){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          projectSchedule.finance(this.financeFrom).then(() => {
            this.crud.notify('提交成功', 'success')
            this.financeVisible = false
            //刷新表格
            this.crud.refresh()
          })
        }
      })
    },
    //完结

    goFinish(){
      projectSchedule.complete(this.financeFrom).then(() => {
            this.crud.notify('提交成功', 'success')
            this.financeVisible = false
            //刷新表格
            this.crud.refresh()
      })
    }

  }
}
</script>

<style scoped>

</style>
