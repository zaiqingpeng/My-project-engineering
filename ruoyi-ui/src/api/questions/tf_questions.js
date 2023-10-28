import request from '@/utils/request'

// 查询判断题题库列表
export function listTf_questions(query) {
  return request({
    url: '/questions/tf_questions/list',
    method: 'get',
    params: query
  })
}

// 查询判断题题库详细
export function getTf_questions(id) {
  return request({
    url: '/questions/tf_questions/' + id,
    method: 'get'
  })
}

// 添加判断题题库
export function addTf_questions(data) {
  return request({
    url: '/questions/tf_questions',
    method: 'post',
    data: data
  })
}

// 修改判断题题库
export function updateTf_questions(data) {
  return request({
    url: '/questions/tf_questions',
    method: 'put',
    data: data
  })
}

// 删除判断题题库
export function delTf_questions(id) {
  return request({
    url: '/questions/tf_questions/' + id,
    method: 'delete'
  })
}

//判断题个数
export function tf_count(){
  return request({
    url:'/questions/tf_questions/list',
    method:'get'
  })
}
