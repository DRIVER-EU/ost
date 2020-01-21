import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Question from '../../../components/QuestionDetail/component/Question'

class QuestionView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getQuestionDetail: PropTypes.func,
    getTrialDetail: PropTypes.func,
    getStageById: PropTypes.func,
    trialName: PropTypes.string,
    stageName: PropTypes.string,
    params: PropTypes.object,
    questionName: PropTypes.string,
    option: PropTypes.array,
    description: PropTypes.string,
    position: PropTypes.any,
    required: PropTypes.bool,
    commented: PropTypes.bool,
    answerType: PropTypes.string,
    updateQuestion: PropTypes.func,
    removeQuestionDetail: PropTypes.func,
    questionSetName: PropTypes.any,
    getQuestion: PropTypes.func
  }

  render () {
    return (
      <div className='background-home'>
        <Question
          new={false}
          getQuestion={this.props.getQuestionDetail}
          updateQuestion={this.props.updateQuestion}
          removeQuestion={this.props.removeQuestionDetail}
          getStageById={this.props.getStageById}
          getTrialDetail={this.props.getTrialDetail}
          trialName={this.props.trialName}
          stageName={this.props.stageName}
          trialId={this.props.params.id_trial}
          stageId={this.props.params.id_stage}
          questionName={this.props.questionName}
          option={this.props.option}
          questionId={this.props.params.id_question}
          questionDetailId={this.props.params.id_questdetail}
          description={this.props.description}
          position={this.props.position}
          required={this.props.required}
          commented={this.props.commented}
          answerType={this.props.answerType}
          questionSetName={this.props.questionSetName}
          getQuestionSet={this.props.getQuestion}
        />
      </div>
    )
  }
}

export default QuestionView
