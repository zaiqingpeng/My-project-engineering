import request from '@/utils/request'

// 查询多选题题库列表
export function listMultiply_questions(query) {
  return request({
    url: '/questions/multiply_questions/list',
    method: 'get',
    params: query
  })
}

// 查询多选题题库详细
export function getMultiply_questions(id) {
  return request({
    url: '/questions/multiply_questions/' + id,
    method: 'get'
  })
}

// 添加多选题题库
export function addMultiply_questions(data) {
  return request({
    url: '/questions/multiply_questions',
    method: 'post',
    data: data
  })
}

// 修改多选题题库
export function updateMultiply_questions(data) {
  return request({
    url: '/questions/multiply_questions',
    method: 'put',
    data: data
  })
}

// 删除多选题题库
export function delMultiply_questions(id) {
  return request({
    url: '/questions/multiply_questions/' + id,
    method: 'delete'
  })
}

//多选题个数
export function multiply_questionsCount(){
  return request({
    url:'/questions/multiply_questions/list',
    method:'get'
  })
}
