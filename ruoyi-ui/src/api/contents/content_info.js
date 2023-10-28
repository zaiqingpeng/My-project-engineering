import request from '@/utils/request'

// 查询认知与学习列表
export function listContent_info(query) {
  return request({
    url: '/contents/content_info/list',
    method: 'get',
    params: query
  })
}

// 查询认知与学习详细
export function getContent_info(id) {
  return request({
    url: '/contents/content_info/' + id,
    method: 'get'
  })
}

// 添加认知与学习
export function addContent_info(data) {
  return request({
    url: '/contents/content_info',
    method: 'post',
    data: data
  })
}

// 修改认知与学习
export function updateContent_info(data) {
  return request({
    url: '/contents/content_info',
    method: 'put',
    data: data
  })
}

// 删除认知与学习
export function delContent_info(id) {
  return request({
    url: '/contents/content_info/' + id,
    method: 'delete'
  })
}
