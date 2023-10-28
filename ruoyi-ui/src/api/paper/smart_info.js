import request from '@/utils/request'

// 查询智能出卷列表
export function listSmart_info(query) {
  return request({
    url: '/paper/smart_info/list',
    method: 'get',
    params: query
  })
}

// 查询智能出卷详细
export function getSmart_info(id) {
  return request({
    url: '/paper/smart_info/' + id,
    method: 'get'
  })
}

// 新增智能出卷
export function addSmart_info(data) {
  return request({
    url: '/paper/smart_info',
    method: 'post',
    data: data
  })
}

// 修改智能出卷
export function updateSmart_info(data) {
  return request({
    url: '/paper/smart_info',
    method: 'put',
    data: data
  })
}

// 删除智能出卷
export function delSmart_info(id) {
  return request({
    url: '/paper/smart_info/' + id,
    method: 'delete'
  })
}

// 获取所有智能出题的题目列表
export function SmartList() {
  return request({
    url: '/paper/smart_info/list',
    method: 'get'
  })
}


