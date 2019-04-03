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
          time: this.props.data 
            ? moment(this.props.data.getTime().add(moment.duration(this.state.seconds, 'seconds'))).format('DD/MM/YYYY HH:mm:ss') 
            : moment(new Date().getTime()).format('DD/MM/YYYY HH:mm:ss'),
            seconds: this.state.seconds +1
        }), 1000)
  }

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
