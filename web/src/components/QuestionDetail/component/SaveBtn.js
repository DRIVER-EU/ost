import React, { Component } from 'react'
import Dialog from 'material-ui/Dialog'
import PropTypes from 'prop-types'
import RaisedButton from 'material-ui/RaisedButton'
import FlatButton from 'material-ui/FlatButton'
import browserHistory from 'react-router/lib/browserHistory'
import WarningModal, { checkInputs, inputValidationRegex } from '../../NewObservationComponent/component/WarningModal'
import _ from 'lodash'

class SaveBtn extends Component {
  constructor (props) {
    super(props)
    this.state = {
      openSaveDialog: false,
      new: this.props.new,
      isWarningOpen: false,
      suggestedText: '',
      questionName: this.props.questionName,
      description: this.props.description
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
    questionId: PropTypes.any,
    inputsValue: PropTypes.array
  }

  componentWillReceiveProps (nextProps) {
    if (!_.isEqual(nextProps.questionName, this.props.questionName)) {
      this.setState({
        questionName: nextProps.questionName
      })
    }
    if (!_.isEqual(nextProps.description, this.props.description)) {
      this.setState({
        description: nextProps.description
      })
    }
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
      this.props.getQuestion(this.props.questionDetailId)
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
      id: this.props.questionDetailId || 0,
      name: this.state.questionName === '' ? this.props.questionName : this.state.questionName,
      description: this.state.description === '' ? this.props.description : this.state.description,
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
