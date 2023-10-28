import request from '@/utils/request'

// 查询简答题题库列表
export function listFree_questions(query) {
  return request({
    url: '/questions/free_questions/list',
    method: 'get',
    params: query
  })
}

// 查询简答题题库详细
export function getFree_questions(id) {
  return request({
    url: '/questions/free_questions/' + id,
    method: 'get'
  })
}

// 新增简答题题库
export function addFree_questions(data) {
  return request({
    url: '/questions/free_questions',
    method: 'post',
    data: data
  })
}

// 修改简答题题库
export function updateFree_questions(data) {
  return request({
    url: '/questions/free_questions',
    method: 'put',
    data: data
  })
}

// 删除简答题题库
export function delFree_questions(id) {
  return request({
    url: '/questions/free_questions/' + id,
    method: 'delete'
  })
}
