import request from '@/utils/request'
import log from "../../views/monitor/job/log";

// 查询文件夹结构信息列表
export function listEvaluation(query) {
  return request({
    url: '/system/fileInfomation/listEvaluation',
    method: 'get',
    params: query
  })
}

// 查询文件夹结构信息详细
export function getFile(folderId) {
  return request({
    url: '/system/fileInfomation' + folderId,
    method: 'get'
  })
}

// 新增文件夹结构信息
export function addFile(data) {
  return request({
    url: '/system/fileInfomation',
    method: 'post',
    data: data
  })
}

// 修改文件夹结构信息
export function updateFile(data) {
  return request({
    url: '/system/fileInfomation',
    method: 'put',
    data: data
  })
}

// 删除文件夹结构信息
export function delFile(folderId) {
  return request({
    url: '/system/fileInfomation' + folderId,
    method: 'delete'
  })
}
//查询文件问价下拉树结构
export function folderTreeSelect(query) {
  return request({
    url: '/system/fileInfomation/deptTree',
    method: 'get',
    params: query
  })
}
