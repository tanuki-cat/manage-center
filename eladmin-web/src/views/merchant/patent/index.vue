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
        <el-input v-model="query.patentTag" 
          clearable size="small" 
          placeholder="输入专利类型进行搜索" 
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
              v-model="query.patentStatus"
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
        <rrOperation />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <!--派发任务-->
      <el-dialog :close-on-click-modal="false" :visible.sync="userVisible" width="520px" :title="派发任务">
          <el-form ref="form" :model="userPatentFrom" :rules="visitRules" size="small" label-width="100px">
            <el-form-item label="公司名称">
              <el-input v-model="userPatentFrom.companyName" style="width: 350px;" disabled />
            </el-form-item>
          
            <el-form-item label="指派人员">
                <el-select
                  v-model="userPatentFrom.assignUser"
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
            <el-button :loading="crud.status.cu === 2" type="primary" @click="$event => addUserPatent()">确认</el-button>
          </div>
        </el-dialog>
        <!--变更业务员-->
     <el-dialog :close-on-click-modal="false" :visible.sync="editUserVisible" width="520px" :title="变更业务员">
        <el-form ref="form" :model="editUserFrom"  size="small" label-width="100px">
          <el-form-item label="公司名称">
            <el-input v-model="editUserFrom.companyName" style="width: 350px;" disabled />
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
      <!--专利经理-->
      <el-dialog :close-on-click-modal="false" :visible.sync="patentVisible" width="520px" :title="填写专利">
        <el-form ref="form" :model="patentFrom" :rules="rules" size="small" label-width="100px">
          <el-form-item label="公司名称">
            <el-input v-model="patentFrom.companyName" style="width: 350px;" disabled />
          </el-form-item>      
          <el-form-item label="专利数">
            <el-input v-model="patentFrom.patentNum" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="年费预警时间">
            <el-input v-model="patentFrom.forewarnTime" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="专利进度" >
            <el-input v-model="patentFrom.progress" style="width: 350px;" />
          </el-form-item>   
          <el-form-item label="专利申报时间" >
            <el-input v-model="patentFrom.filingTime" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="专利写好时间" >
            <el-input v-model="patentFrom.writeTime" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="授权下证时间" >
            <el-input v-model="patentFrom.authorizationTime" style="width: 350px;" />
          </el-form-item>   
          
        </el-form>
        
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="patentClose()">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="$event => addPatentManager()">填写</el-button>
          <el-button type="danger" @click="goTransfer()">转交</el-button>       
        </div>
      </el-dialog>
      <!--财务-->
      <el-dialog :close-on-click-modal="false" :visible.sync="financeVisible" width="520px" :title="财务">
        <el-form ref="form" :model="financeFrom" :rules="rules" size="small" label-width="100px">
          <el-form-item label="公司名称">
            <el-input v-model="financeFrom.companyName" style="width: 350px;" disabled />
          </el-form-item>      
          <el-form-item label="专利数">
            <el-input v-model="financeFrom.patentNum" style="width: 350px;" disabled/>
          </el-form-item>
          <el-form-item label="年费预警时间">
            <el-input v-model="financeFrom.forewarnTime" style="width: 350px;" disabled/>
          </el-form-item>
          <el-form-item label="专利进度" >
            <el-input v-model="financeFrom.progress" style="width: 350px;" disabled/>
          </el-form-item>   
          <el-form-item label="专利申报时间" >
            <el-input v-model="financeFrom.filingTime" style="width: 350px;" disabled/>
          </el-form-item>
          <el-form-item label="专利写好时间" >
            <el-input v-model="financeFrom.writeTime" style="width: 350px;" disabled/>
          </el-form-item>
          <el-form-item label="授权下证时间" >
            <el-input v-model="financeFrom.authorizationTime" style="width: 350px;" disabled/>
          </el-form-item>   
          <el-form-item label="金额百分比" prop="amountPercent">
            <el-input v-model="financeFrom.amountPercent" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="汇款时间">
            <el-date-picker v-model="financeFrom.remittanceTime" type="datetime"  style="width: 350px;"  />
          </el-form-item>
        </el-form>
        
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="financeClose()">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="$event => addPatentFinance()">填写</el-button>
          <el-button :loading="crud.status.cu === 2" type="danger" @click="$event => goFinish()">完结</el-button>
        </div>
      </el-dialog>
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">   
          <el-form-item label="公司名称">
            <el-input v-model="form.companyName" style="width: 370px;" disabled/>
          </el-form-item>
          <el-form-item label="发明专利">
            <el-input v-model="form.invention" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="地区">
            <el-input v-model="form.areas" style="width: 370px;" />
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
          <el-form-item label="专利类型">
            <el-input v-model="form.patentTag" style="width: 370px;" />
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
        <el-table-column prop="areas" label="地区" />
        <el-table-column prop="utilityModel" label="实用新型专利" />
        <el-table-column prop="appearance" label="外观专利" />
        <el-table-column prop="softwareWorks" label="软件著作" />
        <el-table-column prop="copyright" label="版权" />
        <el-table-column prop="patentTag" label="专利类型" />
        <el-table-column prop="projectAmount" label="金额" />
        <el-table-column prop="amountPercent" label="金额百分比" />
        <el-table-column prop="progress" label="专利进度" />
        <el-table-column prop="filingTime" label="专利申报时间" />
        <el-table-column prop="writeTime" label="专利写好时间" />
        <el-table-column prop="authorizationTime" label="授权下证时间" />
        <el-table-column prop="patentNum" label="专利数" />
        <el-table-column prop="forewarnTime" label="年费预警时间" />
        <el-table-column prop="statusName" label="状态" />
        <el-table-column prop="nickName" label="创建者" />
        <el-table-column prop="createTime" label="签单日期" />

        <el-table-column v-if="checkPer(['admin','patent:detail','patent:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
              @addPatent="getUserPatent"
              @upPatent="getUserPatent"
              @patentManager="getPatentManager"
              @patentFinance="getPatentFinance"
              @editUser="getEditUser"
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
import udOperation from '@crud/UD.patent'
import pagination from '@crud/Pagination'
import crudUser from '@/api/system/user'
import patentSchedule from '@/api/merchant/patentSchedule'
const defaultForm = { patentId: null, companyId: null, companyName: null, invention: null, utilityModel: null, appearance: null, softwareWorks: null, copyright: null, progress: null, filingTime: null, writeTime: null, authorizationTime: null, patentNum: null, forewarnTime: null, patentStatus: null, nickName: null, createBy: null, updateBy: null, createTime: null, updateTime: null,patentTag:null,areas:null }
export default {
  name: 'Patent',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '专利', url: 'api/merchant/patent', idField: 'id', sort: 'id,desc', crudMethod: { ...crudPatent }})
  },
  data() {
    return {
      userList:[],
      userVisible:false,
      patentVisible:false,
      //财务
      financeVisible:false,      
      editUserVisible:false,
      enabledTypeOptions: [
        { key: '0', display_name: '创建' },
        { key: '1', display_name: '进行中' },
        { key: '2', display_name: '完结' }
      ],
      userPatentFrom: {
        companyId: 0,
        companyName: '',
        patentId: 0,
        assignUser:[],
        assignUserId:0
      },
      editUserFrom: {
        companyId: 0,
        companyName: '',
        patentId: 0,
        assignUser:[],
        assignUserId:0
      },
      patentFrom: {
        companyId: 0,
        companyName: '',
        patentId: 0,
        progress: '',
        filingTime: '',
        writeTime: '',
        authorizationTime: '',
        patentNum: '',
        forewarnTime: ''
      },
      financeFrom: {
        companyId: 0,
        companyName: '',
        patentId: 0,
        progress: '',
        filingTime: '',
        writeTime: '',
        authorizationTime: '',
        patentNum: '',
        forewarnTime: '',
        amountPercent:  0,
        remittanceTime: ''
      },
      permission: {
        add: ['admin', 'patent:add'],
        edit: ['admin', 'patent:edit'],
        del: ['admin', 'patent:del'],
        leader: ['admin', 'patent:leader'],
        //项目经理
        manager: ['admin', 'patent:manager'],
        //财务
        finance: ['admin', 'patent:finance'],
        //详情
        detail: ['admin', 'patent:detail'],
        //变更业务
        editUser: ['admin', 'patent:editUser']
      },
      rules: {
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
    patentClose() {
      this.patentVisible = false
    },
    financeClose() {
      this.financeVisible = false
    },
    editUserClose() {
      this.editUserVisible = false
    },
    getEditUser(data){
      this.editUserFrom.companyId = data.companyId
      this.editUserFrom.companyName = data.companyName
      this.editUserFrom.patentId = data.id
      this.editUserFrom.assignUser=''
      this.editUserFrom.assignUserId=0
      this.editUserVisible = true
      crudUser.businessList().then(res => {
        console.log(res)
        this.userList=res
      })
      
    },
    getUserPatent(data) {
      console.log(data)
      this.userPatentFrom.companyId = data.companyId
      this.userPatentFrom.companyName = data.companyName
      this.userPatentFrom.patentId = data.id
      this.userPatentFrom.assignUser=''
      this.userPatentFrom.assignUserId=0
      this.userVisible = true    
      crudUser.roleLists().then(res => {
        console.log(res)
        this.userList=res
      })
    },
    selectUser(data) {
      this.userPatentFrom.assignUserId =data.id
      this.userPatentFrom.assignUser =data.nickName
   
    },
    selectEditUser(data) {
      this.editUserFrom.assignUserId =data.id
      this.editUserFrom.assignUser =data.nickName   
    },
    deleteTag(){
      this.userPatentFrom.assignUser = ''
      this.userPatentFrom.assignUserId =0
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
          patentSchedule.editUser(this.editUserFrom).then(() => {
            this.crud.notify('添加成功', 'success')
            this.editUserVisible = false
            //刷新表格
            this.crud.refresh()
          })
        }
      })
        
    },
    addUserPatent(){
      if(this.userPatentFrom.assignUserId==0){
        this.$message({
          message: '请选择指派人员',
          type: 'warning'
        })
        return false
      }
      this.$refs['form'].validate((valid) => {
        if (valid) {
          patentSchedule.assign(this.userPatentFrom).then(res => {
            this.crud.notify('提交成功', 'success')
            this.userVisible = false
              //刷新表格
              this.crud.refresh()
          })
        }
      })      
    },
    //专利经理
    getPatentManager(data){
      //清空当前值
      this.patentFrom.companyId = data.companyId
      this.patentFrom.companyName = data.companyName
      this.patentFrom.patentId = data.id
      this.patentFrom.progress = ''
      this.patentFrom.filingTime = ''
      this.patentFrom.writeTime = ''
      this.patentFrom.authorizationTime = ''
      this.patentFrom.patentNum = ''
      this.patentFrom.forewarnTime = ''
      this.patentVisible = true
    },
    addPatentManager(){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          patentSchedule.manager(this.patentFrom).then(() => {
            this.crud.notify('提交成功', 'success')
            this.patentVisible = false
            //刷新表格
            this.crud.refresh()
          })
        }
      })
    },
    //转交   
    goTransfer(){
      patentSchedule.transfer(this.patentFrom).then(() => {
            this.crud.notify('转交成功', 'success')
            this.patentVisible = false
            //刷新表格
            this.crud.refresh()
      })
    },
    //财务
    getPatentFinance(data){
      this.financeFrom.companyId = data.companyId
      this.financeFrom.companyName = data.companyName
      this.financeFrom.patentId = data.id
      this.financeFrom.progress = data.progress
      this.financeFrom.filingTime = data.filingTime
      this.financeFrom.writeTime = data.writeTime
      this.financeFrom.authorizationTime = data.authorizationTime
      this.financeFrom.patentNum = data.patentNum
      this.financeFrom.forewarnTime = data.forewarnTime
      this.financeFrom.amountPercent= data.amountPercent
      this.financeFrom.remittanceTime=''
      this.financeVisible = true

    },
    addPatentFinance(){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          patentSchedule.finance(this.financeFrom).then(() => {
            this.crud.notify('提交成功', 'success')
            this.financeVisible = false
            //刷新表格
            this.crud.refresh()
          })
        }
      })
    },
    goFinish(){
      patentSchedule.complete(this.financeFrom).then(() => {
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
