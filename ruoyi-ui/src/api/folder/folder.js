import request from '@/utils/request'
import log from "../../views/monitor/job/log";

// 查询文件夹结构信息列表
export function listFolder(query) {
  return request({
    url: '/folder/folder/list',
    method: 'get',
    params: query
  })
}

// 查询文件夹结构信息详细
export function getFolder(folderId) {
  return request({
    url: '/folder/folder/' + folderId,
    method: 'get'
  })
}

// 新增文件夹结构信息
export function addFolder(data) {
  return request({
    url: '/folder/folder',
    method: 'post',
    data: data
  })
}

// 修改文件夹结构信息
export function updateFolder(data) {
  return request({
    url: '/folder/folder',
    method: 'put',
    data: data
  })
}

// 删除文件夹结构信息
export function delFolder(folderId) {
  return request({
    url: '/folder/folder/' + folderId,
    method: 'delete'
  })
}
//查询文件问价下拉树结构
export function folderTreeSelect(query) {
  return request({
    url: '/folder/folder/deptTree',
    method: 'get',
    params: query
  })
}
