import React, { Component } from 'react'
import TrialDetail from '../../../components/TrialDetail'
import PropTypes from 'prop-types'

class TrialDetailWrapper extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getTrialDetail: PropTypes.func,
    updateTrial: PropTypes.func,
    removeTrial: PropTypes.func,
    trialId: PropTypes.any,
    trialName: PropTypes.string,
    trialDescription: PropTypes.string,
    lastTrialStage: PropTypes.any,
    archived: PropTypes.bool,
    stageSet: PropTypes.array,
    sessionSet: PropTypes.array,
    roleSet: PropTypes.array,
    params: PropTypes.object
  };
  render () {
    const trialDetail = {
      trialId: this.props.trialId,
      trialName: this.props.trialName,
      trialDescription: this.props.trialDescription,
      lastTrialStage: this.props.lastTrialStage,
      archived: this.props.archived,
      stageSet: this.props.stageSet,
      sessionSet: this.props.sessionSet,
      roleSet: this.props.roleSet
    }
    return (
      <TrialDetail
        removeBtn
        tabs
        getTrialDetail={this.props.getTrialDetail}
        updateTrial={this.props.updateTrial}
        trialDetail={trialDetail}
        removeTrial={this.props.removeTrial}
        params={this.props.params}
      />
    )
  }
}
export default TrialDetailWrapper
