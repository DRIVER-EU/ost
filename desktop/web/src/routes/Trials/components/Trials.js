import React, { Component } from 'react'
import Trials from '../../../components/Trials'
import PropTypes from 'prop-types'

class TrialsView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getMessages: PropTypes.func,
    messages: PropTypes.any,
    isSendMessage: PropTypes.any,
    sendMessage: PropTypes.func
  }

  render () {
    return (
      <div className='background-home'>
        <Trials
        messages={this.props.messages}
        getMessages={this.props.getMessages}
        isSendMessage={this.props.isSendMessage}
        sendMessage={this.props.sendMessage}
        />
      </div>
    )
  }
}

export default TrialsView
