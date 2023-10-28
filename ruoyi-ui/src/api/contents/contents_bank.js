import request from '@/utils/request'

// 查询知识仓库列表
export function listContents_bank(query) {
  return request({
    url: '/contents/contents_bank/list',
    method: 'get',
    params: query
  })
}

// 查询知识仓库详细
export function getContents_bank(id) {
  return request({
    url: '/contents/contents_bank/' + id,
    method: 'get'
  })
}

// 新增知识仓库
export function addContents_bank(data) {
  return request({
    url: '/contents/contents_bank',
    method: 'post',
    data: data
  })
}

// 修改知识仓库
export function updateContents_bank(data) {
  return request({
    url: '/contents/contents_bank',
    method: 'put',
    data: data
  })
}

// 删除知识仓库
export function delContents_bank(id) {
  return request({
    url: '/contents/contents_bank/' + id,
    method: 'delete'
  })
}
