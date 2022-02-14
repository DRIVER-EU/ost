import React, { Component } from 'react'
import PropTypes from 'prop-types'
import SessionDetail from '../../../components/SessionDetail/component/SessionDetail'

class SessionDetailView extends Component {
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
    getSessionById: PropTypes.func,
    status: PropTypes.string,
    stages: PropTypes.array,
    userRoles: PropTypes.array,
    updateSession: PropTypes.func,
    removeSession: PropTypes.func,
    getObservations: PropTypes.func,
    openRemoveInfoDialog: PropTypes.bool,
    roleSet: PropTypes.array,
    usersList: PropTypes.array,
    getUsersList: PropTypes.func,
    addUser: PropTypes.func,
    removeUser: PropTypes.func,
//    manual: PropTypes.bool
    manual: PropTypes.bool,
    sessionName: PropTypes.string

  }

  render () {
    return (
      <div className='background-home'>
        <SessionDetail
          new={false}
          loadData
          sessionId={this.props.params.id_session}
          stageId={this.props.stageId}
          stageName={this.props.stageName}
          trialId={this.props.params.id_trial}
          trialName={this.props.trialName}
          roleSet={this.props.roleSet}
          getTrialDetail={this.props.getTrialDetail}
          getSessionDetail={this.props.getSessionById}
          status={this.props.status}
          stages={this.props.stages}
          userRoles={this.props.userRoles}
          updateSession={this.props.updateSession}
          removeSession={this.props.removeSession}
          getObservations={this.props.getObservations}
          openRemoveInfoDialog={this.props.openRemoveInfoDialog}
          usersList={this.props.usersList}
          getUsersList={this.props.getUsersList}
          addUser={this.props.addUser}
          removeUser={this.props.removeUser}
          manual={this.props.manual}
          sessionName={this.props.sessionName}
        />
      </div>
    )
  }
}

export default SessionDetailView
