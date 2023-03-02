import request from '@/utils/request'

// 查询投标用户信息列表
export function listInfomation(query) {
  return request({
    url: '/system/infomation/list',
    method: 'get',
    params: query
  })
}

// 查询投标用户信息详细
export function getInfomation(bidderId) {
  return request({
    url: '/system/infomation/' + bidderId,
    method: 'get'
  })
}

// 新增投标用户信息
export function addInfomation(data) {
  return request({
    url: '/system/infomation',
    method: 'post',
    data: data
  })
}

// 修改投标用户信息
export function updateInfomation(data) {
  return request({
    url: '/system/infomation',
    method: 'put',
    data: data
  })
}

// 删除投标用户信息
export function delInfomation(bidderId) {
  return request({
    url: '/system/infomation/' + bidderId,
    method: 'delete'
  })
}
