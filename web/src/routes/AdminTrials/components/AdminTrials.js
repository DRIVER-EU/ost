import React, { Component } from 'react'
import PropTypes from 'prop-types'
import AdminTrials from '../../../components/AdminTrials'

class AdminTrialsView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getMessages: PropTypes.func,
    messages: PropTypes.object,
    isSendMessage: PropTypes.any,
    sendMessage: PropTypes.func,
    getObservation: PropTypes.func,
    observation: PropTypes.array,
    getUsers: PropTypes.func,
    usersList: PropTypes.object,
    getRoles: PropTypes.func,
    rolesList: PropTypes.object,
    params: PropTypes.any
  }

  render () {
    return (
      <div className='background-home'>
        <AdminTrials
          messages={this.props.messages}
          getMessages={this.props.getMessages}
          isSendMessage={this.props.isSendMessage}
          sendMessage={this.props.sendMessage}
          getObservation={this.props.getObservation}
          observation={this.props.observation}
          getUsers={this.props.getUsers}
          usersList={this.props.usersList}
          getRoles={this.props.getRoles}
          rolesList={this.props.rolesList}
          params={this.props.params}
        />
      </div>
    )
  }
}

export default AdminTrialsView
