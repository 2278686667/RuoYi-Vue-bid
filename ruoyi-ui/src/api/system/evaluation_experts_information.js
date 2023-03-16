import request from '@/utils/request'

// 查询评审专家信息列表
export function listEvaluation_experts_information(query) {
  return request({
    url: '/system/evaluation_experts_information/list',
    method: 'get',
    params: query
  })
}

// 查询评审专家信息列表
export function listEvaluation_experts_informationBYUserId(query) {
  return request({
    url: '/system/evaluation_experts_information/listByUserId',
    method: 'get',
    params: query
  })
}
// 查询评审专家信息详细
export function getEvaluation_experts_information(reviewId) {
  return request({
    url: '/system/evaluation_experts_information/' + reviewId,
    method: 'get'
  })
}

// 新增评审专家信息
export function addEvaluation_experts_information(data) {
  return request({
    url: '/system/evaluation_experts_information',
    method: 'post',
    data: data
  })
}

// 修改评审专家信息
export function updateEvaluation_experts_information(data) {
  return request({
    url: '/system/evaluation_experts_information',
    method: 'put',
    data: data
  })
}

// 删除评审专家信息
export function delEvaluation_experts_information(reviewId) {
  return request({
    url: '/system/evaluation_experts_information/' + reviewId,
    method: 'delete'
  })
}
// 签到
export function updateSignIn(query) {
  return request({
    url: '/system/evaluation_experts_information/signIn',
    method: 'get',
    params: query
  })
}
// 修改评审专家信息
export function fileUpload(data) {
  return request({
    url: '/system/evaluation_experts_information/fileUpload',
    method: 'put',
    data: data
  })
}

//发售标书
export function lssue_of_tenderE(row) {
  return request({
    url:'/system/evaluation_experts_information/lssue',
    method:'put',
    data:row
  })
}
