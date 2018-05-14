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
    getStages: PropTypes.func,
    stagesList: PropTypes.object,
    setStage: PropTypes.func,
    stageActive: PropTypes.object,
    params: PropTypes.any,
    observationForm: PropTypes.any,
    getSchemaView: PropTypes.func
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
          getStages={this.props.getStages}
          stagesList={this.props.stagesList}
          setStage={this.props.setStage}
          stageActive={this.props.stageActive}
          params={this.props.params}
          observationForm={this.props.observationForm}
          getSchemaView={this.props.getSchemaView}
        />
      </div>
    )
  }
}

export default AdminTrialsView
