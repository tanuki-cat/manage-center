<template>
  <div>
    <!-- <el-button v-permission="permission.edit" :loading="crud.status.cu === 2" :disabled="disabledEdit" size="mini" type="primary" icon="el-icon-edit" @click.stop="crud.toEdit(data)" />
    <el-popover v-model="pop" v-permission="permission.del" placement="top" width="180" trigger="manual" @show="onPopoverShow" @hide="onPopoverHide">
      <p>{{ msg }}</p>
      <div style="text-align: right; margin: 0">
        <el-button size="mini" type="text" @click="doCancel">取消</el-button>
        <el-button :loading="crud.dataStatus[crud.getDataId(data)].delete === 2" type="primary" size="mini" @click="crud.doDelete(data)">确定</el-button>
      </div>
      <el-button slot="reference" :disabled="disabledDle" type="danger" icon="el-icon-delete" size="mini" @click.stop="toDelete" />
    </el-popover> -->

   
    <router-link v-permission="permission.detail"  :to="{ path: '/merchant/projectSchedule', query: { projectId: data.id } }">
      <el-button icon="el-icon-search" type="warning" size="mini">查看详情</el-button>
    </router-link>
    <el-button v-permission="permission.leader" icon="el-icon-edit" type="primary" size="mini" @click="projectAdd(data)" v-if="data.scheduleStatus==0">派发项目</el-button>
    <el-button v-permission="permission.leader" icon="el-icon-edit" type="primary" size="mini" @click="projectUpdate(data)" v-if="data.scheduleStatus==1">重新派发</el-button>
    <el-button v-permission="permission.manager" icon="el-icon-edit" type="primary" size="mini" @click="projectManager(data)" v-if="data.scheduleStatus==1||data.scheduleStatus==2">填写或提交财务</el-button>
    <el-button v-permission="permission.finance" icon="el-icon-edit" type="primary" size="mini" @click="projectFinance(data)" v-if="data.scheduleStatus==3">填写或完结</el-button>
    <el-button v-permission="permission.editUser" icon="el-icon-edit" type="danger" size="mini" @click="editUser(data)" >变更业务</el-button>
  </div>
</template>
<script>
import CRUD, { crud } from '@crud/crud'
export default {
  mixins: [crud()],
  props: {
    data: {
      type: Object,
      required: true
    },
    permission: {
      type: Object,
      required: true
    },
    disabledEdit: {
      type: Boolean,
      default: false
    },
    disabledDle: {
      type: Boolean,
      default: false
    },
    msg: {
      type: String,
      default: '确定删除本条数据吗？'
    }
  },
  data() {
    return {
      pop: false
    }
  },
  methods: {
    doCancel() {
      this.pop = false
      this.crud.cancelDelete(this.data)
    },
    toDelete() {
      this.pop = true
    },
    [CRUD.HOOK.afterDelete](crud, data) {
      if (data === this.data) {
        this.pop = false
      }
    },
    onPopoverShow() {
      setTimeout(() => {
        document.addEventListener('click', this.handleDocumentClick)
      }, 0)
    },
    onPopoverHide() {
      document.removeEventListener('click', this.handleDocumentClick)
    },
    handleDocumentClick(event) {
      this.pop = false
    },
    projectAdd(data){
      this.$emit('addProject' , data)
    },
    projectUpdate(data){
      this.$emit('upProject' , data)
    },
    projectManager(data){
      this.$emit('projectManager' , data)
    },
    //财务
    projectFinance(data){
      this.$emit('projectFinance' , data)
    },
    editUser(data){
      this.$emit('editUser' , data)
    }
  }
}
</script>
