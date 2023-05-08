import request from '@/utils/request'

// 查询评分表列表
export function listSheet(query) {
  return request({
    url: '/sheet/sheet/list',
    method: 'get',
    params: query
  })
}

// 查询评分表详细
export function getSheet(id) {
  return request({
    url: '/sheet/sheet/' + id,
    method: 'get'
  })
}

// 新增评分表
export function addSheet(data) {
  return request({
    url: '/sheet/sheet',
    method: 'post',
    data: data
  })
}

// 修改评分表
export function updateSheet(data) {
  return request({
    url: '/sheet/sheet',
    method: 'put',
    data: data
  })
}

// 删除评分表
export function delSheet(id) {
  return request({
    url: '/sheet/sheet/' + id,
    method: 'delete'
  })
}

// 根据projid查询评分表列表
export function listprojId(id) {
  return request({
    url: '/sheet/sheet/listprojId/'+id,
    method: 'get',

  })
}
// 根据projid查询评分表列表
export function isEvaluationOfBid(query) {
  return request({
    url: '/sheet/sheet/isEvaluationOfBid',
    method: 'get',
    params: query
  })
}
