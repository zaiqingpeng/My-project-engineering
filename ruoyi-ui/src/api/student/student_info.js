import request from '@/utils/request'

// 查询学生基本信息列表
export function listStudent_info(query) {
  return request({
    url: '/student/student_info/list',
    method: 'get',
    params: query
  })
}

// 查询学生基本信息详细
export function getStudent_info(id) {
  return request({
    url: '/student/student_info/' + id,
    method: 'get'
  })
}

// 添加学生基本信息
export function addStudent_info(data) {
  return request({
    url: '/student/student_info',
    method: 'post',
    data: data
  })
}

// 修改学生基本信息
export function updateStudent_info(data) {
  return request({
    url: '/student/student_info',
    method: 'put',
    data: data
  })
}

// 删除学生基本信息
export function delStudent_info(id) {
  return request({
    url: '/student/student_info/' + id,
    method: 'delete'
  })
}

// 查询学生基本信息列表
export function listStudent_info1() {
  return request({
    url: '/student/student_info/list',
    method: 'get',
  })
}
