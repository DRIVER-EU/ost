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
    updateStage: PropTypes.func,
    addNewStage: PropTypes.func,
    stageName: PropTypes.string,
    trialId: PropTypes.any,
    stageId: PropTypes.any,
    new: PropTypes.bool
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
    } else {
      await this.props.addNewStage(stage)
      browserHistory.push(`/trial-manager/trial-detail/${this.props.trialId}/stage/${this.props.stageId}`)
    }
  };

  render () {
    let stage = {
      id: this.props.stageId,
      name: this.props.stageName,
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
