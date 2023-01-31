import request from '@/utils/request'

// 查询招投标列表
export function listInvite_tenders(query) {
  return request({
    url: '/bid/invite_tenders/list',
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

//发售标书
export function lssue_of_tender(row) {
  return request({
    url:'/bid/invite_tenders/lssue',
    method:'put',
    data:row
  })
}
