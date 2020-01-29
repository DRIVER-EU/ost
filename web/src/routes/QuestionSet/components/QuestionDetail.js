import React, { Component } from 'react'
import PropTypes from 'prop-types'
import QuestionDetail from '../../../components/QuestionSet/component/QuestionDetail'

class QuestionDetailView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getQuestion: PropTypes.func,
    getStageById: PropTypes.func,
    getTrialDetail: PropTypes.func,
    trialName: PropTypes.string,
    stageName: PropTypes.string,
    params: PropTypes.object,
    questionName: PropTypes.string,
    questionsDetailList: PropTypes.array,
    description: PropTypes.string,
    position: PropTypes.any,
    multiplicity: PropTypes.bool,
    withUsers: PropTypes.bool,
    updateQuestion: PropTypes.func,
    removeQuestion: PropTypes.func
  }

  render () {
    return (
      <div className='background-home'>
        <QuestionDetail
          new={false}
          getQuestion={this.props.getQuestion}
          updateQuestion={this.props.updateQuestion}
          removeQuestion={this.props.removeQuestion}
          getStageById={this.props.getStageById}
          getTrialDetail={this.props.getTrialDetail}
          trialName={this.props.trialName}
          stageName={this.props.stageName}
          trialId={this.props.params.id_trial}
          stageId={this.props.params.id_stage}
          questionName={this.props.questionName}
          questionsDetailList={this.props.questionsDetailList}
          questionId={this.props.params.id_question}
          description={this.props.description}
          position={this.props.position}
          multiplicity={this.props.multiplicity}
          withUsers={this.props.withUsers}
        />
      </div>
    )
  }
}

export default QuestionDetailView
