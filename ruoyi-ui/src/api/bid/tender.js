import request from '@/utils/request'

// 查询投标列表
export function listTender(query) {
  return request({
    url: '/tender/info/list',
    method: 'get',
    params: query
  })
}

// 查询招投标详细
export function getInvite_tenders(projId) {
  return request({
    url: '/bid/invite_tenders/' + projId,
    method: 'get'
  })
}

// 新增招投标
export function addInvite_tenders(data) {
  return request({
    url: '/bid/invite_tenders',
    method: 'post',
    data: data
  })
}

// 修改招投标
export function updateInvite_tenders(data) {
  return request({
    url: '/bid/invite_tenders',
    method: 'put',
    data: data
  })
}

// 删除招投标
export function delInvite_tenders(projId) {
  return request({
    url: '/bid/invite_tenders/' + projId,
    method: 'delete'
  })
}

//我要投标
export function deliver(data) {
  return request({
    url: '/tender/info/deliver',
    method: 'post',
    data: data
  })
}
export function downloadFile(data){
  return request({
    url:'/'
  })
}
