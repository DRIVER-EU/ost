import React from 'react'
import ReactDOM from 'react-dom'
import createStore from './store/createStore'
import AppContainer from './containers/AppContainer'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider'
import injectTapEventPlugin from 'react-tap-event-plugin'
import { green500, green300, orange500, grey900 } from 'material-ui/styles/colors'
import getMuiTheme from 'material-ui/styles/getMuiTheme'
require('es6-promise').polyfill()
injectTapEventPlugin()
if (!Array.prototype.find) {
  Object.defineProperty(Array.prototype, 'find', {
    value: function (predicate) {
      if (this == null) {
        throw new TypeError('"this" is null or not defined')
      }
      var o = Object(this)
      var len = o.length >>> 0
      if (typeof predicate !== 'function') {
        throw new TypeError('predicate must be a function')
      }
      var thisArg = arguments[1]
      var k = 0
      while (k < len) {
        var kValue = o[k]
        if (predicate.call(thisArg, kValue, k, o)) {
          return kValue
        }
        k++
      }
      return undefined
    }
  })
}

if (!String.prototype.includes) {
  String.prototype.includes = function (search, start) {
    if (typeof start !== 'number') {
      start = 0
    }

    if (start + search.length > this.length) {
      return false
    } else {
      return this.indexOf(search, start) !== -1
    }
  }
}

if (!Array.prototype.includes) {
  Array.prototype.includes = function (searchElement /*, fromIndex */) {
    'use strict'
    var O = Object(this)
    var len = parseInt(O.length) || 0
    if (len === 0) {
      return false
    }
    var n = parseInt(arguments[1]) || 0
    var k
    if (n >= 0) {
      k = n
    } else {
      k = len + n
      if (k < 0) { k = 0 }
    }
    var currentElement
    while (k < len) {
      currentElement = O[k]
      if (searchElement === currentElement ||
         (searchElement !== searchElement && currentElement !== currentElement)) { // NaN !== NaN
        return true
      }
      k++
    }
    return false
  }
}
// ========================================================
// Store Instantiation
// ========================================================
const initialState = window.__INITIAL_STATE__
const store = createStore(initialState)

// ========================================================
// IndexedDB Instantiation
// ========================================================
if (!('indexedDB' in window)) {
  console.warn('This browser doesn\'t support IndexedDB - offline app version won\'t be enabled.')
} else {
  let DBOpenRequest = window.indexedDB.open('driver', 1)
  DBOpenRequest.onupgradeneeded = (event) => {
    let idb = event.target.result
    if (!idb.objectStoreNames.contains('attachment')) {
      idb.createObjectStore('attachment', { keyPath: 'id' })
    }
    if (!idb.objectStoreNames.contains('answer')) {
      idb.createObjectStore('answear', { keyPath: 'id' })
    }
    if (!idb.objectStoreNames.contains('observation_type')) {
      idb.createObjectStore('observation_type', { keyPath: 'id' })
    }
    if (!idb.objectStoreNames.contains('question')) {
      idb.createObjectStore('question', { keyPath: 'id' })
    }
    if (!idb.objectStoreNames.contains('trial_stage')) {
      idb.createObjectStore('trial_stage', { keyPath: 'id' })
    }
    if (!idb.objectStoreNames.contains('trial')) {
      idb.createObjectStore('trial', { keyPath: 'id' })
    }
    if (!idb.objectStoreNames.contains('answer_trial_role')) {
      idb.createObjectStore('answer_trial_role', { keyPath: 'answer_id' })
    }
    if (!idb.objectStoreNames.contains('observation_type_trial_role')) {
      idb.createObjectStore('observation_type_trial_role', { keyPath: 'observation_type_id' })
    }
    if (!idb.objectStoreNames.contains('trial_role_m2m')) {
      idb.createObjectStore('trial_role_m2m', { keyPath: 'trial_observer_id' })
    }
    if (!idb.objectStoreNames.contains('user_role_session')) {
      idb.createObjectStore('user_role_session', { keyPath: 'trial_user_id' })
    }
    if (!idb.objectStoreNames.contains('trial_role')) {
      idb.createObjectStore('trial_role', { keyPath: 'id' })
    }
    if (!idb.objectStoreNames.contains('trial_user')) {
      idb.createObjectStore('trial_user', { keyPath: 'id' })
    }
    if (!idb.objectStoreNames.contains('trial_manager')) {
      idb.createObjectStore('trial_manager', { keyPath: 'trial_user_id' })
    }
    if (!idb.objectStoreNames.contains('trial_session')) {
      let session = idb.createObjectStore('trial_session', { keyPath: 'id' })
      session.createIndex("status", "status", { unique: false })
    }
    if (!idb.objectStoreNames.contains('event')) {
      idb.createObjectStore('event', { keyPath: 'id' })
    }
    if (!idb.objectStoreNames.contains('trial_session_manager')) {
      idb.createObjectStore('trial_session_manager', { keyPath: 'trial_user_id' })
    }
  }
}

// ========================================================
// Render Setup
// ========================================================
const MOUNT_NODE = document.getElementById('root')

const muiTheme = getMuiTheme({
  palette: {
    primary1Color: '#00497E',
    primary2Color: '#053451',
    accent1Color: '#FDB913',
    textColor: '#282829',
    pickerHeaderColor: green300
  }
})

let render = () => {
  const routes = require('./routes/index').default(store)

  ReactDOM.render(
    <MuiThemeProvider muiTheme={muiTheme}>
      <AppContainer store={store} routes={routes} />
    </MuiThemeProvider>,
    MOUNT_NODE
  )
}

// This code is excluded from production bundle
if (__DEV__) {
  if (module.hot) {
    // Development render functions
    const renderApp = render
    const renderError = (error) => {
      const RedBox = require('redbox-react').default

      ReactDOM.render(<RedBox error={error} />, MOUNT_NODE)
    }

    // Wrap render in try/catch
    render = () => {
      try {
        renderApp()
      } catch (error) {
        console.error(error)
        renderError(error)
      }
    }

    // Setup hot module replacement
    module.hot.accept('./routes/index', () =>
      setImmediate(() => {
        ReactDOM.unmountComponentAtNode(MOUNT_NODE)
        render()
      })
    )
  }
}

// ========================================================
// Go!
// ========================================================
render()