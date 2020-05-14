import axios from 'axios'
import { logIn } from '../layouts/CoreLayout/layout-action'

export const getHeaders = () => {
  let token = localStorage.getItem('drivertoken')
  let globalHeaders = { headers: { Authorization: `Bearer ${token}` } }
  return globalHeaders
}

export const getHeadersASCI = () => {
  let token = localStorage.getItem('drivertoken')
  let globalHeaders = { headers: { Authorization: `Bearer ${token}` }, responseType: 'arraybuffer' }
  return globalHeaders
}

export const getHeadersFileDownload = () => {
  let token = localStorage.getItem('drivertoken')
  let globalHeaders = { headers: { Authorization: `Bearer ${token}` }, responseType: 'arraybuffer' }
  return globalHeaders
}

export const getHeadersReferences = () => {
  let token = localStorage.getItem('drivertoken')
  let globalHeaders = { headers: { Authorization: `Bearer ${token}`, 'content-Type': 'multipart/form-data; ' } }
  return globalHeaders
}

export const errorHandle = (error) => {
  if (error && error.response && error.response.status === 401) {
    logIn()
    localStorage.clear()
    window.location.replace(window.location.origin)
  } else if (error.message === 'Network Error' && localStorage.getItem('online')) {
    localStorage.removeItem('online')
  }
}

export const freeQueue = () => {
  if (!localStorage.getItem('online')) {
    window.indexedDB.open('driver', 1).onsuccess = (event) => {
      let queue = event.target.result.transaction(['sendQueue'], 'readwrite').objectStore('sendQueue')
      queue.getAll().onsuccess = (e) => {
        for (let i = 0; i < e.target.result.length; i++) {
          if (e.target.result[i].data) {
            if (e.target.result[i].headerType) {
              const data = new FormData()
              let tempData = {}
              for (let key in e.target.result[i].data) {
                if (key !== 'attachments') {
                  tempData[key] = e.target.result[i].data[key]
                }
              }
              let json = JSON.stringify(tempData)
              let blob = new Blob([json], { type: 'application/json' })
              for (let i = 0; i < e.target.result[i].data.attachments.length; i++) {
                data.append('attachments', e.target.result[i].data.attachments[i])
              }
              data.append('data', blob)
              axios[e.target.result[i].type](e.target.result[i].address, data, getHeadersReferences())
            } else {
              axios[e.target.result[i].type](e.target.result[i].address, e.target.result[i].data, getHeaders())
            }
          } else {
            axios[e.target.result[i].type](e.target.result[i].address, getHeaders())
          }
        }
        queue.clear()
      }
      localStorage.setItem('online', true)
    }
  }
}

export const setPathname = (value) => {
  localStorage.setItem(value, window.location.pathname)
}
