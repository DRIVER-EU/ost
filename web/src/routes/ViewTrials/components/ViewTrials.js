import React, { Component } from 'react'
import PropTypes from 'prop-types'
import ViewTrials from '../../../components/ViewTrials'

class ViewTrial extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getViewTrials: PropTypes.func,
    getTrialSession: PropTypes.func,
    viewTrials: PropTypes.array,
    params: PropTypes.any,
    trialSession: PropTypes.any,
    getTrials: PropTypes.func,
    listOfTrials: PropTypes.object,
    downloadFile: PropTypes.func
  }

  render () {
    return (
      <div className='background-home'>
        <ViewTrials
          getViewTrials={this.props.getViewTrials}
          viewTrials={this.props.viewTrials}
          getTrialSession={this.props.getTrialSession}
          trialSession={this.props.trialSession}
          getTrials={this.props.getTrials}
          listOfTrials={this.props.listOfTrials}
          params={this.props.params}
          downloadFile={this.props.downloadFile}
        />
      </div>
    )
  }
}

export default ViewTrial
