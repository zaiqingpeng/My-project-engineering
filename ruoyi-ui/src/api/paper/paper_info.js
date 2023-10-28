import request from '@/utils/request'

// 查询组卷信息列表
export function listPaper_info(query) {
  return request({
    url: '/paper/paper_info/list',
    method: 'get',
    params: query
  })
}

// 查询组卷信息详细
export function getPaper_info(id) {
  return request({
    url: '/paper/paper_info/' + id,
    method: 'get'
  })
}

// 添加组卷信息
export function addPaper_info(data) {
  return request({
    url: '/paper/paper_info',
    method: 'post',
    data: data
  })
}

// 修改组卷信息
export function updatePaper_info(data) {
  return request({
    url: '/paper/paper_info',
    method: 'put',
    data: data
  })
}

// 删除组卷信息
export function delPaper_info(id) {
  return request({
    url: '/paper/paper_info/' + id,
    method: 'delete'
  })
}

//获取单选题列表数据
export function singleDataList(){
  return request({
    url:'/questions/single_questions/list',
    method:'get'
  })
}

//获取多选题列表数据
export function multiplyDataList(){
  return request({
    url:'/questions/multiply_questions/list',
    method:'get'
  })
}

//获取判断题列表数据
export function tfDataList(){
  return request({
    url:'/questions/tf_questions/list',
    method:'get'
  })
}

// 查询组卷列表
export function Paperlist() {
  return request({
    url: '/paper/paper_info/list1',
    method: 'get',
  })
}
