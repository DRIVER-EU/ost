import React, { Component } from 'react'
import PropTypes from 'prop-types'
import StageDetail from '../../../components/StageDetail'

class StageDetailView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    questions: PropTypes.array,
    stageName: PropTypes.string,
    getStageById: PropTypes.func,
    updateStage: PropTypes.func,
    removeStage: PropTypes.func,
    params: PropTypes.object,
    id_stage: PropTypes.any,
    id_trial: PropTypes.any,
    trialName: PropTypes.string,
    getTrialDetail: PropTypes.func
  }

  render () {
    return (
      <div className='background-home'>
        <StageDetail
          new={false}
          loadData
          stageId={this.props.params.id_stage}
          questions={this.props.questions}
          stageName={this.props.stageName}
          getStage={this.props.getStageById}
          updateStage={this.props.updateStage}
          removeStage={this.props.removeStage}
          trialId={this.props.params.id_trial}
          trialName={this.props.trialName}
          getTrialDetail={this.props.getTrialDetail}
        />
      </div>
    )
  }
}

export default StageDetailView
