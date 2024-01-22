import request from '@/utils/request'
import qs from 'qs'

export function initData(url, params) {
  return request({
    url: url + '?' + qs.stringify(params, { indices: false }),
    method: 'get'
  })
}

export function download(url, params) {
  return request({
    url: url + '?' + qs.stringify(params, { indices: false }),
    method: 'get',
    responseType: 'blob'
  })
}

export function getCountAll() {
  return request({
      url: 'api/merchant/statisticians/countAll',
      method: 'get'
  })
}

export function getDayCount() {
  return request({
    url: 'api/merchant/statisticians/countByCurrentDay',
    method: 'get'
  })
  
}

export function getWeekCount() {
  return request({
    url: 'api/merchant/statisticians/countByWeek',
    method: 'get'
  })
}
export function getYearCount(year){
  return request({
    url: 'api/merchant/statisticians/countByYear?year=' + year,
    method: 'get'
  })
}