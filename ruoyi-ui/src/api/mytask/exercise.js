import request from '@/utils/request'

// 查询开始练习列表
export function listExercise(query) {
  return request({
    url: '/mytask/exercise/list',
    method: 'get',
    params: query
  })
}

// 查询开始练习详细
export function getExercise(id) {
  return request({
    url: '/mytask/exercise/' + id,
    method: 'get'
  })
}

// 新增开始练习
export function addExercise(data) {
  return request({
    url: '/mytask/exercise',
    method: 'post',
    data: data
  })
}

// 修改开始练习
export function updateExercise(data) {
  return request({
    url: '/mytask/exercise',
    method: 'put',
    data: data
  })
}

// 删除开始练习
export function delExercise(id) {
  return request({
    url: '/mytask/exercise/' + id,
    method: 'delete'
  })
}
