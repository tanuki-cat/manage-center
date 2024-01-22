import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/merchant/patentSchedule',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/merchant/patentSchedule/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/merchant/patentSchedule',
    method: 'put',
    data
  })
}

export function details(id) {
  return request({
    url: 'api/merchant/patentSchedule/details/' + id,
    method: 'get'
  })
}
export function assign(data){
  return request({
    url: 'api/merchant/patentSchedule/assign',
    method: 'post',
    data
  })
}
export function manager(data){
  return request({
    url: 'api/merchant/patentSchedule/manager',
    method: 'post',
    data
  })
}
export function transfer(data){
  return request({
    url: 'api/merchant/patentSchedule/transfer',
    method: 'post',
    data
  })
}
export function finance(data){
  return request({
    url: 'api/merchant/patentSchedule/finance',
    method: 'post',
    data
  })
}
export function complete(data){
  return request({
    url: 'api/merchant/patentSchedule/complete',
    method: 'post',
    data
  })
}
export function editUser(data){
  return request({
    url: 'api/merchant/patentSchedule/changeUser',
    method: 'post',
    data
  })
}
export default { add, edit, del,details,assign,manager,transfer,finance,complete ,editUser}
