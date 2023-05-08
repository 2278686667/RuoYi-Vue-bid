import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listIndex(query) {
  return request({
    url: '/system/index/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getIndex(ggcode) {
  return request({
    url: '/system/index/' + ggcode,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addIndex(data) {
  return request({
    url: '/system/index',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateIndex(data) {
  return request({
    url: '/system/index',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delIndex(ggcode) {
  return request({
    url: '/system/index/' + ggcode,
    method: 'delete'
  })
}
