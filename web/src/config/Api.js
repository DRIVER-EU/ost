export let origin = window.location.origin

if (origin.includes('localhost') || origin.includes('dev.itti.com.pl')) {
  origin = 'https://testbed-ost.itti.com.pl'
  // origin = '192.168.1.174:8080'
} else {
  origin = 'https://' + window.location.host
}
