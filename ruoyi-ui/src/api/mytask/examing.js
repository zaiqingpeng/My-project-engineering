import request from '@/utils/request'

// 查询开始考试列表
export function listExaming(query) {
  return request({
    url: '/mytask/examing/list',
    method: 'get',
    params: query
  })
}

// 查询开始考试详细
export function getExaming(questionId) {
  return request({
    url: '/mytask/examing/' + questionId,
    method: 'get'
  })
}

// 新增开始考试
export function addExaming(data) {
  return request({
    url: '/mytask/examing',
    method: 'post',
    data: data
  })
}

// 修改开始考试
export function updateExaming(data) {
  return request({
    url: '/mytask/examing',
    method: 'put',
    data: data
  })
}

// 删除开始考试
export function delExaming(questionId) {
  return request({
    url: '/mytask/examing/' + questionId,
    method: 'delete'
  })
}

