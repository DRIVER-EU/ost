import React, { Component } from 'react'
import PropTypes from 'prop-types'
import moment from 'moment'
class DateComponent extends Component {
  constructor (props) {
    super(props)
    this.state = {
      time: moment(props.trialTime ? props.trialTime : new Date().getTime()).format('DD/MM/YYYY HH:mm:ss')
    }
    this.interval = null
  }

  static propTypes = {
    trialTime: PropTypes.any,
    mode: PropTypes.any,
    desc: PropTypes.any,
    handleChangeTrialTime: PropTypes.func
  }

  componentDidMount () {
    if (this.props.mode && this.props.mode !== 'new') {
      let time = moment(this.props.trialTime, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
      this.setState({
        time
      })
    } else {
      this.interval = setInterval(() => {
        let time = this.state.time
        if (!this.props.trialTime) {
          time = moment(new Date().getTime()).format('DD/MM/YYYY HH:mm:ss')
        } else {
          time = moment(time, 'DD/MM/YYYY HH:mm:ss').add(1000, 'milliseconds').format('DD/MM/YYYY HH:mm:ss')
          this.props.handleChangeTrialTime(time)
        }
        this.setState({
          time })
      }, 1000)
    }
  }

  componentWillUnmount () {
    clearInterval(this.interval)
  }

  render () {
    return (
      <div>
        <div className='data-time' style={{ textAlign: 'right' }}>
          {this.props.desc ? this.props.desc + this.state.time : this.state.time}
        </div>
      </div>
    )
  }
}

export default DateComponent
