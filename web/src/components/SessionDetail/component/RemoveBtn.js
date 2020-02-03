import React, { Component } from 'react'
import Dialog from 'material-ui/Dialog'
import RaisedButton from 'material-ui/RaisedButton'
import FlatButton from 'material-ui/FlatButton'
import PropTypes from 'prop-types'
import browserHistory from 'react-router/lib/browserHistory'

class RemoveBtn extends Component {
  constructor (props) {
    super(props)
    this.state = {
      openRemoveDialog: false,
      openRemoveInfoDialog: false,
      sessionId: this.props.sessionId,
      removeSession: this.props.openRemoveInfoDialog
    }
  }

  static propTypes={
    sessionId: PropTypes.any,
    removeSession: PropTypes.func,
    userRoles: PropTypes.array,
    trialId: PropTypes.any,
    openRemoveInfoDialog: PropTypes.bool
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
  async removeQuestion () {
    if (
      this.props.userRoles.length === 0
    ) {
      await this.props.removeSession(this.props.sessionId)
      if (this.state.removeSession) {
        this.handleCloseDialog('openRemoveDialog')
        this.handleOpenDialog('openRemoveInfoDialog')
      } else {
        browserHistory.push(`/trial-manager/trial-detail/${this.props.trialId}`)
      }
    } else {
      this.handleCloseDialog('openRemoveDialog')
      this.handleOpenDialog('openRemoveInfoDialog')
    }
  }
  render () {
    const actionsRemoveDialog = [
      <FlatButton
        label='No'
        primary
        onClick={this.handleCloseDialog.bind(this, 'openRemoveDialog')}
      />,
      <RaisedButton
        backgroundColor='#b71c1c'
        labelColor='#fff'
        label='Yes'
        type='Button'
        onClick={this.removeQuestion.bind(this)}
      />
    ]
    const actionsRemoveInfoDialog = [
      <RaisedButton
        backgroundColor='#FCB636'
        labelColor='#fff'
        label='Ok'
        type='Button'
        onClick={this.handleCloseDialog.bind(this, 'openRemoveInfoDialog')}
      />
    ]
    return (
      <div className='info__btn'>
        <RaisedButton
          buttonStyle={{ width: '200px' }}
          backgroundColor='#b71c1c'
          labelColor='#fff'
          label='Remove'
          type='Button'
          onClick={this.handleOpenDialog.bind(this, 'openRemoveDialog')}
        />
        <Dialog
          title='Do you want to remove session?'
          actions={actionsRemoveDialog}
          modal={false}
          contentClassName='custom__dialog'
          open={this.state.openRemoveDialog}
          onRequestClose={this.handleCloseDialog.bind(this, 'openRemoveDialog')}
        />
        <Dialog
          title='System cannot remove this session due to existing dependent objects to this session.'
          actions={actionsRemoveInfoDialog}
          modal={false}
          contentClassName='custom__dialog'
          open={this.state.openRemoveInfoDialog}
          onRequestClose={this.handleCloseDialog.bind(
            this,
            'openRemoveInfoDialog'
          )}
        />
      </div>
    )
  }
}
export default RemoveBtn
