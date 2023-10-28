import request from '@/utils/request'

// 查询出题难度控制列表
export function listHard_controller(query) {
  return request({
    url: '/paper/hard_controller/list',
    method: 'get',
    params: query
  })
}

// 查询出题难度控制详细
export function getHard_controller(id) {
  return request({
    url: '/paper/hard_controller/' + id,
    method: 'get'
  })
}

// 新增出题难度控制
export function addHard_controller(data) {
  return request({
    url: '/paper/hard_controller',
    method: 'post',
    data: data
  })
}

// 修改出题难度控制
export function updateHard_controller(data) {
  return request({
    url: '/paper/hard_controller',
    method: 'put',
    data: data
  })
}

// 删除出题难度控制
export function delHard_controller(id) {
  return request({
    url: '/paper/hard_controller/' + id,
    method: 'delete'
  })
}

//获取难度控制页面的数据列表
export function getHardExtend(){
  return request({
    url:'/paper/hard_controller/list',
    method:'get'
  })
}
