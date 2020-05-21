import React, { Component } from 'react'
import Dialog from 'material-ui/Dialog'
import PropTypes from 'prop-types'
import RaisedButton from 'material-ui/RaisedButton'
import FlatButton from 'material-ui/FlatButton'
import browserHistory from 'react-router/lib/browserHistory'
import WarningModal, {
  checkInputs,
  inputValidationRegex
} from '../../NewObservationComponent/component/WarningModal'

class SaveBtn extends Component {
  constructor (props) {
    super(props)
    this.state = {
      openSaveDialog: false,
      new: this.props.new,
      sessionName: this.props.sessionName,
      isWarningOpen: false,
      suggestedText: ''
    }
  }
  static propTypes = {
    updateSession: PropTypes.func,
    addNewSession: PropTypes.func,
    trialId: PropTypes.any,
    sessionId: PropTypes.any,
    new: PropTypes.bool,
    status: PropTypes.string,
    stageId: PropTypes.any,
    manual: PropTypes.bool,
    sessionName: PropTypes.string,
    inputsValue: PropTypes.array
  };
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

  async dialogAccepted (session) {
    if (!this.state.new) {
      this.props.updateSession(session)
      this.handleCloseDialog('openSaveDialog')
      this.props.getSessionDetail(this.props.sessionId)
    } else {
      await this.props.addNewSession(session)
      browserHistory.push(
        `/trial-manager/trial-detail/${this.props.trialId}/session/${this.props.sessionId}`
      )
    }
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
  };
  acceptSuggestedText = () => {
    const regex = inputValidationRegex
    let name = this.props.sessionName.replace(regex, '')
    this.setState({
      isWarningOpen: false,
      openSaveDialog: true,
      sessionName: name
    })
  };

  render () {
    let session = {
      trialSessionName: this.props.sessionName,
      id: this.props.sessionId,
      trialId: this.props.trialId,
      status: this.props.status,
      lastTrialStageId: this.props.stageId,
      manualStageChange: this.props.manual
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
        onClick={this.dialogAccepted.bind(this, session)}
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
