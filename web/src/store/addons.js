import axios from 'axios'

export const getHeaders = () => {
  let token = localStorage.getItem('drivertoken')
  let globalHeaders = { headers: { 'x-auth-token': token } }
  return globalHeaders
}

export const getHeadersASCI = () => {
  let token = localStorage.getItem('drivertoken')
  let globalHeaders = { headers: { 'x-auth-token': token }, responseType: 'arraybuffer' }
  return globalHeaders
}

export const getHeadersFileDownload = () => {
  let token = localStorage.getItem('drivertoken')
  let globalHeaders = { headers: { 'x-auth-token': token }, responseType: 'arraybuffer' }
  return globalHeaders
}

export const getHeadersReferences = () => {
  let token = localStorage.getItem('drivertoken')
  let globalHeaders = { headers: { 'x-auth-token': token, 'content-Type': 'multipart/form-data; ' } }
  return globalHeaders
}

export const errorHandle = (error) => {
  if (error === 401) {
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
          axios[e.target.result[i].type](e.target.result[i].address, e.target.result[i].data, getHeaders())
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
