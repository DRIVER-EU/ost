import React, { Component } from 'react'
import PropTypes from 'prop-types'
import StageDetail from '../../../components/StageDetail'

class NewStageWrapper extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    addNewStage: PropTypes.func,
    newStageDetail: PropTypes.object,
    params: PropTypes.object,
    trialName: PropTypes.string,
    getTrialDetail: PropTypes.func
  };
  render () {
    return (
      <StageDetail
        new
        trialId={this.props.params.id_trial}
        addNewStage={this.props.addNewStage}
        stageId={this.props.newStageDetail.id}
        stageName={this.props.newStageDetail.name}
        trialName={this.props.trialName}
        getTrialDetail={this.props.getTrialDetail}
      />
    )
  }
}
export default NewStageWrapper
