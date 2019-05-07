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
    // localStorage.removeItem('drivertoken')
    window.location.replace(window.location.origin)
  } else if (error === 403) {
  }
}
export const setPathname = (value) => {
  localStorage.setItem(value, window.location.pathname)
}
