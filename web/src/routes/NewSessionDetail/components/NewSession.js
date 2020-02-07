import React, { Component } from 'react'
import PropTypes from 'prop-types'
import SessionDetail from '../../../components/SessionDetail/component/SessionDetail'

class NewSessionWrapper extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    params: PropTypes.object,
    id_trial: PropTypes.any,
    trialName: PropTypes.string,
    getTrialDetail: PropTypes.func,
    stageId: PropTypes.any,
    stageName: PropTypes.string,
    status: PropTypes.string,
    stages: PropTypes.array,
    userRoles: PropTypes.array,
    addNewSession: PropTypes.func,
    sessionId: PropTypes.any,
    manual: PropTypes.bool
  };
  render () {
    return (
      <SessionDetail
        new
        sessionId={this.props.sessionId}
        stageId={this.props.stageId}
        stageName={this.props.stageName}
        trialId={this.props.params.id_trial}
        trialName={this.props.trialName}
        getTrialDetail={this.props.getTrialDetail}
        status={this.props.status}
        stages={this.props.stages}
        userRoles={this.props.userRoles}
        addNewSession={this.props.addNewSession}
        manual={this.props.manual}
      />
    )
  }
}
export default NewSessionWrapper
