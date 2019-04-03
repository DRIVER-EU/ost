import React, { Component } from 'react'
import moment from 'moment'
class DateComponent extends Component {
  constructor (props) {
    super(props)
    this.state = {
      time: moment(new Date().getTime()).format('DD/MM/YYYY HH:mm:ss')
    }
  }

  static propTypes = { }

  componentDidMount () {
    this.props.data 
      ? this.customClock(this.props.data) 
      : this.interval = setInterval(() => this.setState({
          time: moment(new Date().getTime()).format('DD/MM/YYYY HH:mm:ss'),
            seconds: this.state.seconds +1
        }), 1000)
  }

  customClock = (function() {
    console.log('dupa')
    var timeDiff;
    var timeout;
  
    function addZ(n) {
      return (n < 10? '0' : '') + n;
    }
  
    function formatTime(d) {
      return addZ(d.getHours()) + ':' +
             addZ(d.getMinutes()) + ':' +
             addZ(d.getSeconds());
    }
  
    return function (s) {
  
      var now = new Date();
      var then;
  
      // Set lag to just after next full second
      var lag = 1015 - now.getMilliseconds();
  
      // Get the time difference if first run
      if (s) {
        s = s.split(':');
        then = new Date(now);
        then.setHours(+s[0], +s[1], +s[2], 0);
        timeDiff = now - then;
      }
  
      now = new Date(now - timeDiff);
  
      this.setState({
        time: formatTime(now)
      })
      timeout = setTimeout(customClock, lag)
    }
  }())

  componentWillUnmount () {
    clearInterval(this.interval)
  }

  render () {
    return (
      <div className='data-time' style={{ textAlign: 'right' }}>
        {this.props.desc 
          ? this.props.desc + this.state.time 
          : this.state.time}
      </div>
    )
  }
}

export default DateComponent
