import request from '@/utils/request'

// 查询班级信息列表
export function listClasses_info(query) {
  return request({
    url: '/classes/classes_info/list',
    method: 'get',
    params: query
  })
}

// 查询班级信息详细
export function getClasses_info(id) {
  return request({
    url: '/classes/classes_info/' + id,
    method: 'get'
  })
}

// 添加班级信息
export function addClasses_info(data) {
  return request({
    url: '/classes/classes_info',
    method: 'post',
    data: data
  })
}

// 修改班级信息
export function updateClasses_info(data) {
  return request({
    url: '/classes/classes_info',
    method: 'put',
    data: data
  })
}

// 删除班级信息
export function delClasses_info(id) {
  return request({
    url: '/classes/classes_info/' + id,
    method: 'delete'
  })
}
