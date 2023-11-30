import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/merchant/projectSchedule',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/merchant/projectSchedule/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/merchant/projectSchedule',
    method: 'put',
    data
  })
}
export function details(id) {
  return request({
    url: 'api/merchant/projectSchedule/details/' + id,
    method: 'get'
  })
}

export default { add, edit, del,details }
