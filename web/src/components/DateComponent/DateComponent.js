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
    this.interval = setInterval(() => this.setState({
      time: moment(new Date().getTime()).format('DD/MM/YYYY HH:mm:ss')
    }), 1000)
  }
  componentWillUnmount () {
    clearInterval(this.interval)
  }

  render () {
    return (
      <div className='data-time' style={{ textAlign: 'right' }}>
        {this.state.time}
      </div>
    )
  }
}

export default DateComponent
