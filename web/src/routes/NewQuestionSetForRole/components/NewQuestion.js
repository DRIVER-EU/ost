import React, { Component } from 'react'
import PropTypes from 'prop-types'
import QuestionDetail from '../../../components/QuestionSet/component/QuestionDetail'

class NewQuestionView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    addNewQuestion: PropTypes.func,
    getRoleById: PropTypes.func,
    getTrialDetail: PropTypes.func,
    newQuestionDetail: PropTypes.object,
    trialName: PropTypes.string,
    stageName: PropTypes.string,
    params: PropTypes.object
  }

  render () {
    return (
      <div className='background-home'>
        <QuestionDetail
          new
          addNewQuestion={this.props.addNewQuestion}
          getStageById={this.props.getRoleById}
          getTrialDetail={this.props.getTrialDetail}
          trialName={this.props.trialName}
          stageName={this.props.stageName}
          trialId={this.props.params.id_trial}
          stageId={this.props.params.id_role}
          roleId={this.props.params.id_role}
          questionName={this.props.newQuestionDetail.name}
          questionsDetailList={this.props.newQuestionDetail.questions}
          questionId={this.props.newQuestionDetail.id}
          description={this.props.newQuestionDetail.description}
          position={this.props.newQuestionDetail.position}
          multiplicity={this.props.newQuestionDetail.multiplicity}
          withUsers={this.props.newQuestionDetail.withUsers}
          questionForRole
        />
      </div>
    )
  }
}

export default NewQuestionView
