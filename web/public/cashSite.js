const cacheName = 'v1'

var prefetchedURLs = [
  '/',
  '/vendor.js',
  '/app.js',
  '/version.txt',
  '/images/driver-logo.png',
  '/images/driver-mini-logo.png'
]
self.addEventListener('install', function (event) {
  console.log('install sw')
  event.waitUntil(
        caches.open(cacheName).then((cache) => {
          return Promise.all(prefetchedURLs.map((url) => {
            return fetch(url).then(res => {
              if (res.status >= 400) throw Error('request failed')
              return cache.put(url, res)
            })
          }))
        }).catch((err) => {
          console.log(err)
        })
    )
  self.addEventListener('fetch', (e) => {
    if (e.request.url.indexOf('/api/') === -1 && e.request.url.indexOf('webpack') === -1) {
      e.respondWith(
        caches.match(e.request)
            .then(function (responseData) {
              if (responseData && e.request.url.indexOf('version.txt') === -1) {
                console.log('[ServiceWorker] Found in Cache', e.request.url, responseData)
                return responseData
              }
              var requestClone = e.request.clone()
              return fetch(requestClone)
                    .then(function (response) {
                      if (!response) {
                        console.log('[ServiceWorker] No response from fetch ')
                        return response
                      }
                      var responseClone = response.clone()
                      caches.open(cacheName).then(function (cache) {
                        cache.put(e.request, responseClone)
                        console.log('[ServiceWorker] New Data Cached', e.request.url)
                        return response
                      })
                      return response
                    })
                    .catch(function (err) {
                      if (e.request.url.indexOf('version.txt') > -1) {
                        console.log('[ServiceWorker] Found in Cache', e.request.url)
                        return caches.match(e.request.url)
                      }
                      console.log('[ServiceWorker] Error Fetching & Caching New Data', err)
                      return caches.match('/')
                    })
            })
            .catch(function (e) {
              console.log('[ServiceWorker] ERROR WITH THIS MATCH !!!', e, arguments)
            })
      )
    }
  })
})

self.addEventListener('activate', event => {
  event.waitUntil(self.clients.claim())
  event.waitUntil(
    caches.keys().then(cacheNames => {
      return Promise.all(
        cacheNames.map(cache => {
          if (cache !== cacheName) {
            return caches.delete(cache)
          }
        })
      )
    })
  )
})
