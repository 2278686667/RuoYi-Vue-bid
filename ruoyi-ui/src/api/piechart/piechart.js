import request from '@/utils/request'

// 查询投标列表
export function returnStatus() {
  return request({
    url: '/piechart/returnstatus',
    method: 'get',
  })
}

