import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/merchant/company',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/merchant/company/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/merchant/company',
    method: 'put',
    data
  })
}
export function addExt(data) {
  return request({
    url: 'api/merchant/company/addExt',
    method: 'post',
    data
  })  
}
export function getCompany(id) {
  return request({
    url: 'api/merchant/company/detail?companyId=' + id,
    method: 'get'
  })
}
export function getExtInfo(id) {
  return request({
    url: 'api/merchant/company/extInfo?companyId=' + id,
    method: 'get'
  })
}

export default { add, edit, del,addExt,getCompany,getExtInfo }
