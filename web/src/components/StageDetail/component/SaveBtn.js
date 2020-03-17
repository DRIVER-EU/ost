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
      stageName:''
    }
  }
  static propTypes = {
    updateStage: PropTypes.func,
    addNewStage: PropTypes.func,
    stageName: PropTypes.string,
    trialId: PropTypes.any,
    stageId: PropTypes.any,
    new: PropTypes.bool,
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
  async dialogAccepted (stage) {
    if (!this.state.new) {
      this.props.updateStage(stage)
      this.handleCloseDialog('openSaveDialog')
      this.props.getStage(parseInt(this.props.stageId))
    } else {
      await this.props.addNewStage(stage)
      browserHistory.push(`/trial-manager/trial-detail/${this.props.trialId}/stage/${this.props.stageId}`)
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
    let stageName = this.props.stageName.replace(regex, '')
    this.setState({
      isWarningOpen: false,
      openSaveDialog: true,
      stageName: stageName
    })
  }

  render () {
    let stage = {
      id: this.props.stageId,
      name: this.state.stageName === '' ? this.props.stageName : this.state.stageName,
      trialId: this.props.trialId
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
        onClick={this.dialogAccepted.bind(this, stage)}
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
