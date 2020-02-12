import PropTypes from 'prop-types'
import React, { Component } from 'react'
import ActiveSessions from '../../../components/ActiveSessions/component/ActiveSessions'

class ActiveSessionsView extends Component {
  static propTypes = {
    activeSessionsList: PropTypes.array,
    sessionDetail: PropTypes.array,
    getSessionDetail: PropTypes.func,
    getActiveSessions: PropTypes.func
  }
  render () {
    return (
      <div className='background-home'>
        <ActiveSessions
          activeSessionsList={this.props.activeSessionsList}
          sessionDetail={this.props.sessionDetail}
          getSessionDetail={this.props.getSessionDetail}
          getActiveSessions={this.props.getActiveSessions}
        />
      </div>
    )
  }
}
export default ActiveSessionsView
