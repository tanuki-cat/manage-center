<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
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
            <el-input v-model="financeFrom.patentNum" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="年费预警时间">
            <el-input v-model="financeFrom.forewarnTime" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="专利进度" >
            <el-input v-model="financeFrom.progress" style="width: 350px;" />
          </el-form-item>   
          <el-form-item label="专利申报时间" >
            <el-input v-model="financeFrom.filingTime" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="专利写好时间" >
            <el-input v-model="financeFrom.writeTime" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="授权下证时间" >
            <el-input v-model="financeFrom.authorizationTime" style="width: 350px;" />
          </el-form-item>   
          
        </el-form>
        
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="financeClose()">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="$event => goFinish()">完结</el-button>
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
        <el-table-column prop="statusName" label="状态" />
        <el-table-column prop="nickName" label="创建者" />
        <el-table-column prop="createTime" label="添加时间" />

        <el-table-column v-if="checkPer(['admin','patent:edit','patent:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
              @addPatent="getUserPatent"
              @upPatent="getUserPatent"
              @patentManager="getPatentManager"
              @patentFinance="getPatentFinance"
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
const defaultForm = { patentId: null, companyId: null, companyName: null, invention: null, utilityModel: null, appearance: null, softwareWorks: null, copyright: null, progress: null, filingTime: null, writeTime: null, authorizationTime: null, patentNum: null, forewarnTime: null, patentStatus: null, nickName: null, createBy: null, updateBy: null, createTime: null, updateTime: null }
export default {
  name: 'Patent',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'patent', url: 'api/merchant/patent', idField: 'id', sort: 'id,desc', crudMethod: { ...crudPatent }})
  },
  data() {
    return {
      userList:[],
      userVisible:false,
      patentVisible:false,
      //财务
      financeVisible:false,
      userPatentFrom: {
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
        forewarnTime: ''
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
        detail: ['admin', 'patent:detail']
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
    deleteTag(){
      this.userPatentFrom.assignUser = ''
      this.userPatentFrom.assignUserId =0
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
      this.financeVisible = true

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
