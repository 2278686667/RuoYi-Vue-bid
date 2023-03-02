import request from '@/utils/request'

// 查询投标项目列表
export function listProject(query) {
  return request({
    url: '/tender/project/list',
    method: 'get',
    params: query
  })
}

// 查询投标项目详细
export function getProject(tpId) {
  return request({
    url: '/tender/project/' + tpId,
    method: 'get'
  })
}

// 新增投标项目
export function addProject(data) {
  return request({
    url: '/tender/project',
    method: 'post',
    data: data
  })
}

// 修改投标项目
export function updateProject(data) {
  return request({
    url: '/tender/project',
    method: 'put',
    data: data
  })
}

// 删除投标项目
export function delProject(tpId) {
  return request({
    url: '/tender/project/' + tpId,
    method: 'delete'
  })
}

// 修改投标项目
export function updateProjectFile(data) {
  return request({
    url: '/tender/project/file',
    method: 'put',
    data: data
  })
}
