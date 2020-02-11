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
    updateSession: PropTypes.func,
    addNewSession: PropTypes.func,
    trialId: PropTypes.any,
    sessionId: PropTypes.any,
    new: PropTypes.bool,
    status: PropTypes.string,
    stageId: PropTypes.any,
    manual: PropTypes.bool
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

  async dialogAccepted (session) {
    if (!this.state.new) {
      this.props.updateSession(session)
      this.handleCloseDialog('openSaveDialog')
    } else {
      await this.props.addNewSession(session)
      browserHistory.push(`/trial-manager/trial-detail/${this.props.trialId}/session/${this.props.sessionId}`)
    }
  };

  render () {
    let session = {
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
