import React, { Component } from 'react'
import Dialog from 'material-ui/Dialog'
import PropTypes from 'prop-types'
import RaisedButton from 'material-ui/RaisedButton'
import FlatButton from 'material-ui/FlatButton'
import browserHistory from 'react-router/lib/browserHistory'
import WarningModal, { checkInputs, inputValidationRegex } from '../../NewObservationComponent/component/WarningModal'

class SaveBtn extends Component {
  constructor (props) {
    super(props)
    this.state = {
      openSaveDialog: false,
      new: this.props.new,
      isWarningOpen: false,
      suggestedText: '',
      questionName: this.props.questionName,
      description: this.props.description || ''
    }
  }
  static propTypes = {
    updateQuestion: PropTypes.func,
    addNewQuestion: PropTypes.func,
    questionName: PropTypes.string,
    trialId: PropTypes.any,
    stageId: PropTypes.any,
    questionId: PropTypes.any,
    new: PropTypes.bool,
    description: PropTypes.string,
    position: PropTypes.any,
    withUsers: PropTypes.bool,
    multiplicity: PropTypes.bool,
    questionForRole: PropTypes.bool,
    inputsValue: PropTypes.array
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
//      this.props.updateQuestion(question)
        await this.props.updateQuestion(question)
      this.handleCloseDialog('openSaveDialog')
//      this.props.getQuestion(this.props.questionId)
      await this.props.getQuestion(this.props.questionId)

    } else {
      await this.props.addNewQuestion(question)
      let questionId = this.props.questionId
      let stageId = this.props.stageId
      let roleId = this.props.roleId
      let trialId = this.props.trialId
      if (this.props.questionForRole) {
        browserHistory.push(`/trial-manager/trial-detail/${trialId}/role/${roleId}/question/${questionId}`)
      } else {
        browserHistory.push(`/trial-manager/trial-detail/${trialId}/stage/${stageId}/question/${questionId}`)
      }
    }
  };
  validateInputs (nameOfDialog, inputsValue) {
    let isValid = checkInputs(inputsValue).isValid
    if (isValid) {
      this.handleOpenDialog(nameOfDialog)
    } else {
      this.setState({
        isWarningOpen: true,
        suggestedText: checkInputs(inputsValue).suggestedText
      })
    }
  }
  closeWarningModal = () => {
    this.setState({
      isWarningOpen: false
    })
  }
  acceptSuggestedText = () => {
    const regex = inputValidationRegex
    let name = this.props.questionName.replace(regex, '')
    let description = this.props.description.replace(regex, '')
    this.setState({
      isWarningOpen: false,
      openSaveDialog: true,
      questionName: name,
      description: description
    })
  }

  render () {
    let question = {
      id: this.props.questionId,
      name: this.state.questionName === '' ? this.props.questionName : this.state.questionName,
      description: this.state.description === '' ? this.props.description : this.state.description,
      trailStageId: parseInt(this.props.stageId),
      trailId: parseInt(this.props.trialId),
      multiplicity: this.props.multiplicity,
      withUsers: this.props.withUsers,
      position: parseInt(this.props.position)

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
      <div>
        <WarningModal
          isWarningOpen={this.state.isWarningOpen}
          suggestedText={this.state.suggestedText}
          closeWarningModal={this.closeWarningModal}
          acceptSuggestedText={this.acceptSuggestedText}
          />
        <div className='info__btn'>
          <RaisedButton
            buttonStyle={{ width: '200px' }}
            backgroundColor='#FCB636'
            labelColor='#fff'
            label='Save'
            type='Button'
            onClick={this.validateInputs.bind(this, 'openSaveDialog', this.props.inputsValue)}
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
      </div>
    )
  }
}
export default SaveBtn
