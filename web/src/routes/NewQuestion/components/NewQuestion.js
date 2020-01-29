import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Question from '../../../components/QuestionDetail/component/Question'

class NewQuestionView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    addNewQuestionDetail: PropTypes.func,
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
    questionDetailId: PropTypes.any,
    questionSetName: PropTypes.any,
    getQuestion: PropTypes.func
  }

  render () {
    return (
      <div className='background-home'>
        <Question
          new
          addNewQuestion={this.props.addNewQuestionDetail}
          getStageById={this.props.getStageById}
          getTrialDetail={this.props.getTrialDetail}
          trialName={this.props.trialName}
          stageName={this.props.stageName}
          trialId={this.props.params.id_trial}
          stageId={this.props.params.id_stage}
          questionName={this.props.questionName}
          option={this.props.option}
          questionId={this.props.params.id_question}
          questionDetailId={this.props.questionDetailId}
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

export default NewQuestionView
