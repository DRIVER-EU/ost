import React, { Component } from 'react'
import Dialog from 'material-ui/Dialog'
import PropTypes from 'prop-types'
import RaisedButton from 'material-ui/RaisedButton'
import FlatButton from 'material-ui/FlatButton'
import browserHistory from 'react-router/lib/browserHistory'

class SaveBtn extends Component {
  constructor (props) {
    super(props)
    this.state = {
      openSaveDialog: false,
      new: this.props.new
    }
  }
  static propTypes = {
    updateQuestion: PropTypes.func,
    addNewQuestion: PropTypes.func,
    questionName: PropTypes.string,
    trialId: PropTypes.any,
    stageId: PropTypes.any,
    questionDetailId: PropTypes.any,
    new: PropTypes.bool,
    description: PropTypes.string,
    position: PropTypes.any,
    commented: PropTypes.bool,
    answerType: PropTypes.string,
    questionId: PropTypes.any
  }
  handleOpenDialog (name) {
    let change = {}
    change[name] = true
    this.setState(change)
  }

  handleCloseDialog (name) {
    let change = {}
    change[name] = false
    this.setState(change)
  }

  async dialogAccepted (question) {
    if (!this.state.new) {
      this.props.updateQuestion(question)
      this.handleCloseDialog('openSaveDialog')
    } else {
      await this.props.addNewQuestion(question)
      let questionId = this.props.questionId
      let stageId = this.props.stageId
      let trialId = this.props.trialId
      let questionDetailId = this.props.questionDetailId
      let path = `/trial-manager/trial-detail/${trialId}/stage`
      browserHistory.push(`${path}/${stageId}/question/${questionId}/question-detail/${questionDetailId}`)
    }
  };

  render () {
    let question = {
      id: this.props.questionDetailId || 0,
      name: this.props.questionName,
      description: this.props.description,
      commented: this.props.commented,
      position: parseInt(this.props.position),
      answerType: this.props.answerType,
      observationTypeId: this.props.questionId

    }
    const actionsSaveDialog = [
      <FlatButton
        label='No'
        primary
        onClick={this.handleCloseDialog.bind(this, 'openSaveDialog')}
      />,
      <RaisedButton
        backgroundColor='#FCB636'
        labelColor='#fff'
        label='Yes'
        type='Button'
        onClick={this.dialogAccepted.bind(this, question)}
      />
    ]
    return (
      <div className='info__btn'>
        <RaisedButton
          buttonStyle={{ width: '200px' }}
          backgroundColor='#FCB636'
          labelColor='#fff'
          label='Save'
          type='Button'
          onClick={this.handleOpenDialog.bind(this, 'openSaveDialog')}
        />
        <Dialog
          title='Are you sure to save changes?'
          actions={actionsSaveDialog}
          contentClassName='custom__dialog'
          modal={false}
          open={this.state.openSaveDialog}
          onRequestClose={this.handleCloseDialog.bind(this, 'openSaveDialog')}
        />
      </div>
    )
  }
}
export default SaveBtn
