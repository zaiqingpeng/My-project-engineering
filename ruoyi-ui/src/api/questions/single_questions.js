import request from '@/utils/request'

// 查询单选题题库列表
export function listSingle_questions(query) {
  return request({
    url: '/questions/single_questions/list',
    method: 'get',
    params: query
  })
}

// 查询单选题题库详细
export function getSingle_questions(id) {
  return request({
    url: '/questions/single_questions/' + id,
    method: 'get'
  })
}

// 新增单选题题库
export function addSingle_questions(data) {
  return request({
    url: '/questions/single_questions',
    method: 'post',
    data: data
  })
}

// 修改单选题题库
export function updateSingle_questions(data) {
  return request({
    url: '/questions/single_questions',
    method: 'put',
    data: data
  })
}

// 删除单选题题库
export function delSingle_questions(id) {
  return request({
    url: '/questions/single_questions/' + id,
    method: 'delete'
  })
}

//单选题个数
export function single_count(){
  return request({
    url:'/questions/single_questions/list',
    method:'get'
  })
}
