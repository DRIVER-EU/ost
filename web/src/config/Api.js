export let origin = window.location.origin

if (origin.includes('localhost') || origin.includes('dev.itti.com.pl')) {
  origin = 'https://testbed-ost.itti.com.pl'
} else {
  origin = 'https://'+window.location.host
}
