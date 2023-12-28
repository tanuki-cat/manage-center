<template>
  <div style="padding: 50px 50px 200px 50px" width="100%">
    <!--表单 -->
    <el-form
      :ref="form"
      :model="form"
      :rules="rules"
      :title="title"
      style="width: 960px;"
    >
      <!-- tableW-->
      <table style="width: 100%" class="table">
        <!-- 企业名称-->
        <tr>
          <td colspan="4">企业名称</td>
          <td colspan="8">
            <el-input style="color: #000;" v-model="form.companyName" disabled  />
          </td>
        </tr>
        <!-- 联系人-->
        <tr>
          <td colspan="4">联系人</td>
          <td colspan="2">
            <el-input v-model="form.userName" disabled style="color: #000;;" />
          </td>
          <td colspan="2">联系方式</td>
          <td colspan="4">
            <el-input v-model="form.userMobile" disabled style="color: #000;;" />
          </td>
        </tr>
        <!-- 所属行业-->
        <tr>
            <td colspan="4">所属行业</td>
            <td colspan="2">
              <el-input v-model="form.industry"/>
            </td>
            <td rowspan="2" colspan="2">主营产品</td>
            <td colspan="4">
              <el-input v-model="form.mainProducts"/>
            </td>
        </tr>
        <!-- 主要市场-->
        <tr>
            <td colspan="4">主要市场</td>
            <td colspan="2">
              <el-input v-model="form.mainMarket"/>
            </td>
            <td colspan="4">
                <el-form-item label="技术指标">
                    <el-select v-model="form.technicalIndicator">
                        <el-option label="高于国家标准 " value="1"></el-option>
                        <el-option label="参照国家标准" value="2"></el-option>
                    </el-select>
                </el-form-item>
            </td>
        </tr>
        <!-- 公司性质-->
        <tr>
          <td colspan="4">公司性质</td>
          <td colspan="8">
            <el-select v-model="form.companyNature">
                <!-- 1、国有企业；2、外方独资；3.合资企业；4、合资企业；5、民营企业；6、其他 -->
                <el-option label="国有企业" value="1"></el-option>
                <el-option label="外方独资" value="2"></el-option>
                <el-option label="合资企业" value="3"></el-option>
                <el-option label="民营企业" value="4"></el-option>
                <el-option label="其他" value="5"></el-option>
            </el-select>
          </td>
        </tr>
        <!-- 注册信息-->
        <tr>
          <td colspan="2">注册时间</td>
          <td colspan="2">
            <el-input v-model="form.registrationTime"/>
          </td>
          <td colspan="1">注册资本</td>
          <td colspan="2">
            <el-input v-model="form.registeredCapital"/>
          </td>
          <td colspan="3">注册地</td>
          <td colspan="2">
            <el-input v-model="form.registryAddr"/>
          </td>
        </tr>
        <!-- 人员情况-->
        <tr>
          <td colspan="4">职工总数（人）</td>
          <td colspan="2">
            <el-input v-model="form.workersNum"/>
          </td>
          <td colspan="3">大专及以上科技人员（人）</td>
          <td colspan="3">
            <el-input v-model="form.tertiaryEducationTechNum"/>
          </td>       
        </tr>
        <!-- 人员占比-->
        <tr>
          <td colspan="4">直接从事研发的人员（人）</td>
          <td colspan="2">
            <el-input v-model="form.developersNum"/>
          </td>
          <td colspan="3">研发人员占比（%）</td>
          <td colspan="3">
            <el-input v-model="form.developersPercentage"/>
          </td>
       
        </tr>
        <!--公司员工职称情况-->
        <tr>
          <td colspan="5" rowspan="2">公司员工职称情况</td>
          <td colspan="2">高级</td>
          <td colspan="2">中级</td>
          <td colspan="2">初级</td>
          <td >其他</td>
       
        </tr>
        <tr>          
          <td colspan="2">
            <el-input v-model="form.workersDesignation.senior"/>
          </td>
          <td colspan="2">
            <el-input v-model="form.workersDesignation.middle"/>
          </td>
          <td colspan="2">
            <el-input v-model="form.workersDesignation.junior"/>
          </td>
          <td >
            <el-input v-model="form.workersDesignation.other"/>
          </td>       
        </tr>
        <!-- 财务状况（近三年）（万元）-->
        <tr>
          <td colspan="4" rowspan="4">财务状况（近三年）<br>（万元）</td>
          <td colspan="2">年份</td>
          <td colspan="2">
           <el-input v-model="form.threeYearsFinance.data[3].year"/>
          </td>
          <td colspan="2">   <el-input v-model="form.threeYearsFinance.data[2].year"/></td>
          <td colspan="2">   <el-input v-model="form.threeYearsFinance.data[1].year"/></td>
        </tr>
        <tr>
          <td colspan="2">销售收入</td>
          <td colspan="2">
            <el-input v-model="form.threeYearsFinance.data[3].salesIncome"/>
          </td>
          <td colspan="2">
            <el-input v-model="form.threeYearsFinance.data[2].salesIncome"/>
          </td>
          <td colspan="2">
            <el-input v-model="form.threeYearsFinance.data[1].salesIncome"/>
          </td>
        </tr>
        <tr>
          <td colspan="2">研发费用</td>
          <td colspan="2">
            <el-input v-model="form.threeYearsFinance.data[3].developmentCost"/>
          </td >
          <td colspan="2">
            <el-input v-model="form.threeYearsFinance.data[2].developmentCost"/>
          </td>
          <td colspan="2">
            <el-input v-model="form.threeYearsFinance.data[1].developmentCost"/>
          </td>
        </tr>
        <tr>
            <td colspan="8">
                <el-row>
                    <el-col :span="12">
                      <el-form-item label="主营产品市场占有率">
                        <el-input v-model="form.threeYearsFinance.marketShare" style="width: 150px;"/>
                      </el-form-item>

                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="主营产品国内排名">
                          <el-input v-model="form.threeYearsFinance.ranking" style="width: 150px;"/>
                        </el-form-item>
                    </el-col>
                  </el-row>
            </td>
        </tr>
        <!-- 历年来获得的政府部门立项的项目、取得资质和荣誉-->
        <tr>
          <td colspan="2" rowspan="5" >历年来获得的政府部门立项的项目、取得资质和荣誉</td>
          <td colspan="2" rowspan="5">立项</td>
          <td colspan="3" >项目名称</td>
          <td colspan="2" >立项时间</td>
          <td colspan="3" >政府资助额度（万元）</td>
        </tr>
        
        <tr>
          <td colspan="3">
            <el-input v-model="form.yearsHonors[1].projectName"/>
          </td>
          <td colspan="2">
            <el-input v-model="form.yearsHonors[1].projectTime"/>
          </td>
          <td colspan="3">
            <el-input v-model="form.yearsHonors[1].projectAmount"/>
          </td>
        </tr>
        <tr>
          <td colspan="3">
            <el-input v-model="form.yearsHonors[2].projectName"/>
          </td>
          <td colspan="2">
            <el-input v-model="form.yearsHonors[2].projectTime"/>
          </td>
          <td colspan="3">
            <el-input v-model="form.yearsHonors[2].projectAmount"/>
          </td>
        </tr>
        <tr>
          <td colspan="3">
            <el-input v-model="form.yearsHonors[3].projectName"/>
          </td>
          <td colspan="2">
            <el-input v-model="form.yearsHonors[3].projectTime"/>
          </td>
          <td colspan="3">
            <el-input v-model="form.yearsHonors[3].projectAmount"/>
          </td>
        </tr>
        <tr>
          <td colspan="3">
            <el-input v-model="form.yearsHonors[4].projectName"/>
          </td>
          <td colspan="2">
            <el-input v-model="form.yearsHonors[4].projectTime"/>
          </td>
          <td colspan="3">
            <el-input v-model="form.yearsHonors[4].projectAmount"/>
          </td>
        </tr>
        <!--正在申报中或拟申报的项目-->
        <tr style="height: 100px;">
            <td colspan="4">
                正在申报中或拟申报的项目
            </td>
            <td colspan="8">
                <el-input v-model="form.projectsUnderDeclaration" type="textarea" style="height: auto;"/>
            </td>

        </tr>
        <!--校企合作情况-->
        <tr style="height: 100px;">
            <td colspan="4">
                校企合作情况
            </td>
            <td colspan="8">
                <el-input v-model="form.schoolEnterpriseCooperation" type="textarea" style="height: auto;"/>
            </td>

        </tr>
        <!--知识产权情况 （近三年）-->
        <tr>
            <td colspan="4" rowspan="3">知识产权情况 （近三年）</td>
            <td>类型</td>
            <td>发明专利</td>
            <td>实用新型专利</td>
            <td>软件著作权</td>
            <td colspan="3">农业新品种、各类标准和各类认证</td>
            <td>商标</td>
        </tr>
        <tr>          
            <td>自主研发</td>
            <td>
                <el-input v-model="form.thirdYearsPropertyRights[1].selfDevelopment"/>
            </td>
            <td>
                <el-input v-model="form.thirdYearsPropertyRights[2].selfDevelopment"/>
            </td>
            <td>
                <el-input v-model="form.thirdYearsPropertyRights[3].selfDevelopment"/>
            </td>
            <td colspan="3">
                <el-input v-model="form.thirdYearsPropertyRights[4].selfDevelopment"/>
            </td>
            <td>
                <el-input v-model="form.thirdYearsPropertyRights[5].selfDevelopment"/>
            </td>
        </tr>
        <tr>          
            <td>转让所得</td>
            <td>
                <el-input v-model="form.thirdYearsPropertyRights[1].transfer"/> 
            </td>
            <td>
                <el-input v-model="form.thirdYearsPropertyRights[2].transfer"/>
            </td>
            <td>
                <el-input v-model="form.thirdYearsPropertyRights[3].transfer"/>
            </td>
            <td colspan="3">
                <el-input v-model="form.thirdYearsPropertyRights[4].transfer"/>
            </td>
            <td>
                <el-input v-model="form.thirdYearsPropertyRights[5].transfer"/>
            </td>
        </tr>
        <!--体系情况-->
        <tr>
            <td colspan="4">
                体系情况
            </td>
            <td colspan="8">
              <el-row>
                <el-col :span="8">
                  <el-select v-model="form.systematicSituation" style="width: 200px;" @change="selectChange()">
                    <el-option label="质量体系" value="1"></el-option>
                    <el-option label="环境体系" value="2"></el-option>
                    <el-option label="职业健康体系" value="3"></el-option>
                    <el-option label="其他" value="4"></el-option>
                  </el-select>
                </el-col>
                <el-col :span="8">
                  <div v-if="dialogFormVisible">（
                  <el-input v-model="form.remark" style="width: 150px;" />）
                  </div>
                </el-col>
              </el-row>
              
              
              

            </td>
        </tr>
        <!--近三年是否有环保事故、税务处罚-->
        <tr style="height: 100px;">
            <td colspan="4">
                近三年是否有环保事故、税务处罚
            </td>
            <td colspan="8">
                <el-input v-model="form.incidentThirdYears" type="textarea" style="height: auto;"/>
            </td>

        </tr>
      </table>
      <!-- table-->
      <el-form-item style="float: right; margin: 20px;">
        <el-button type="primary" @click="$event => onSubmit()">确认</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import crudCompany from '@/api/merchant/company/company'
import  { presenter, header, form, crud } from '@crud/crud'

export default {
  name: "edits",
  props: {
    data: {
      type: Object,
      required: true,
    },
    title: {
      type: String,
      required: true,
    },
  },
  mixins: [ crud()],
  data() {
    return {
      //显示隐藏
      dialogFormVisible: false,
      form: {
        companyName: "",
        userName: "",
        userMobile: "",
        industry: "",
        mainMarket: "",
        mainProducts: "",
        technicalIndicator: "2",
        companyNature: "1",
        registrationTime: "",
        registeredCapital: "",
        registryAddr: "",
        workersNum: "",
        tertiaryEducationTechNum: "",
        developersNum: "",
        developersPercentage: "",
        workersDesignation: {
            //高级
            senior: "",
            //中级
            middle: "",
            //初级
            junior: "",
            //其他
            other: ""
        },
        threeYearsFinance: {
            data: [
                {            
                    year: "",
                    //销售收入
                    salesIncome: "",
                    //研发费用
                    developmentCost: "",
                }
            ],
            //主营产品市场占有率
            marketShare: "",
            //主营产品国内排名
            ranking: ""
        },
        yearsHonors: [{          
            //项目名称
            projectName: "",
            //立项时间
            projectTime: "",
            //政府资助额度（万元）
            projectAmount: ""
         
      }],
      thirdYearsPropertyRights: [
        {
          selfDevelopment:"",
          transferIncome:""
        }
      ],
      projectsUnderDeclaration: "",
      schoolEnterpriseCooperation: "",
      incidentThirdYears: "",
      systematicSituation:"1",
      remark:""      
    },
    froms:{
      id: 0,
      companyId: 0,
      infoExt:{

      },
    },
      rules: {
        companyName: [
          { required: true, message: "请输入公司名称", trigger: "blur" },
        ],
        userName: [
          { required: true, message: "请输入联系人", trigger: "blur" },
        ],
        userMobile: [
          { required: true, message: "请输入联系人手机", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.froms.companyId = this.$route.query.companyId;
    
    this.getExinfo(this.froms.companyId)
  },
  methods: {
      //初始化
      getConfig(){
          let year = new Date().getFullYear();
          const yearArray = [year,year-1,year-2];
          yearArray.forEach(item=>{
              this.form.threeYearsFinance.data.push({
                  year: item,
                  salesIncome: "",
                  developmentCost: "",
              })
          })
          const  yearsHonors= [1,2,3,4];
          yearsHonors.forEach(item=>{
              this.form.yearsHonors.push({
                  projectName: "",
                  projectTime: "",
                  projectAmount: ""
              })
          })  
          const   thirdYearsPropertyRights= [1,2,3,4,5];
          thirdYearsPropertyRights.forEach(item=>{
              this.form.thirdYearsPropertyRights.push({
                  selfDevelopment:"",
                  transferIncome:""
              })
          })    
         
      },
      selectChange(){
          if(this.form.systematicSituation=="4"){
              this.dialogFormVisible=true            
          }else{
              this.dialogFormVisible=false
              this.form.remark=""
          }
      },
      //提交
      onSubmit(){
         this.froms.infoExt = this.form
         crudCompany.addExt(this.froms).then(() => {
            //弹窗成公
          this.$message({
          message: '修改成功',
          type: 'success'
        });
          
          }) 
      },
      getCompany(id){
        if(id>0){
            crudCompany.getCompany(id).then(res=>{
                this.form.companyName = res.name
                this.form.userName = res.userName
                this.form.userMobile = res.userMobile                    
            })
        }
      },
      getExinfo(id){
        if(id>0){
            crudCompany.getExtInfo(id).then(res=>{
              this.froms.id=res.id
              if(res.infoExt){
                this.form = res.infoExt
                if(this.form.systematicSituation=="4"){
                    this.dialogFormVisible=true            
                  }else{
                    this.dialogFormVisible=false
                    this.form.remark=""
                  }
              }else{
                this.getConfig()
              }
            })
            this.getCompany(id)
        }
      }
  }
};
</script>
<style scoped>
/* 表单居中 */
.el-form {
  margin: 0 auto;
}

.table {
  width: 100%;
  border-collapse: collapse;

}

tr,
td {
  border: 1pt solid #000;
}
.el-input.is-disabled .el-input__inner {
    background-color: #f5f7fa;
    border-color: #dfe4ed;
    color: #000;
    cursor: not-allowed;
}
</style>