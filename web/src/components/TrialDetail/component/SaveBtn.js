import React, { Component } from 'react'
import Dialog from 'material-ui/Dialog'
import RaisedButton from 'material-ui/RaisedButton'
import FlatButton from 'material-ui/FlatButton'
import browserHistory from 'react-router/lib/browserHistory'
import PropTypes from 'prop-types'
import WarningModal, { inputValidationRegex, checkInputs } from '../../NewObservationComponent/component/WarningModal'

class SaveBtn extends Component {
  constructor (props) {
    super(props)
    this.state = {
      openSaveDialog: false,
      commentModal: false,
      isWarningOpen: false,
      suggestedText: '',
      name: this.props.name,
      description: this.props.description
    }
  }
  static propTypes = {
    name: PropTypes.string,
    description: PropTypes.string,
    updateTrial: PropTypes.func,
    id: PropTypes.any,
    inputsValue: PropTypes.array,
    getTrialDetail: PropTypes.func
  }
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
    let name = this.props.name.replace(regex, '')
    let description = this.props.description.replace(regex, '')
    this.setState({
      isWarningOpen: false,
      openSaveDialog: true,
      name: name,
      description: description
    })
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
  async saveTrial (trial) {
    if (this.props.addNewTrial) {
      await this.props.addNewTrial(trial)
      browserHistory.push(`/trial-manager/trial-detail/${this.props.newTrialDetail.trialId}`)
    } else {
      await this.props.updateTrial(trial)
      this.handleCloseDialog('openSaveDialog')
      this.props.getTrialDetail(this.props.id)
    }
  }
  render () {
    let trial = {
      trialId: this.props.id,
      trialName: this.state.name,
      trialDescription: this.state.description
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
        onClick={this.saveTrial.bind(this, trial)}
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
