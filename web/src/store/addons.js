export const getHeaders = () => {
  let token = localStorage.getItem('drivertoken')
  let globalHeaders = { headers: { 'x-auth-token': token } }
  return globalHeaders
}
export const getHeadersReferences = () => {
  let token = localStorage.getItem('drivertoken')
  let globalHeaders = { headers: { 'x-auth-token': token, 'content-Type': 'multipart/form-data; ' } }
  return globalHeaders
}
export const errorHandle = (error) => {
  if (error === 401) {
    localStorage.removeItem('drivertoken')
    window.location.replace(window.location.origin)
  } else if (error === 403) {
  }
}
export const getTypeOfResources = (items) => {
  let types = ''
  for (let key in items) {
    if (items[key] === '0') {
      types += 'RESOURCE,'
    } else if (items[key] === '1') {
      types += 'REQUEST,'
    } else if (items[key] === '2') {
      types += 'OBSERVATION,'
    } else if (items[key] === '3') {
      types += 'CAMPAIGN,'
    } else if (items[key] === '4') {
      types += 'ANNOUNCEMENT,'
    }
  }
  return types
}
export const setPathname = (value) => {
  localStorage.setItem(value, window.location.pathname)
}
