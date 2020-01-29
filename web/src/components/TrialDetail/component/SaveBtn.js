import React, { Component } from 'react'
import Dialog from 'material-ui/Dialog'
import RaisedButton from 'material-ui/RaisedButton'
import FlatButton from 'material-ui/FlatButton'
import browserHistory from 'react-router/lib/browserHistory'
import PropTypes from 'prop-types'

class SaveBtn extends Component {
  constructor (props) {
    super(props)
    this.state = {
      openSaveDialog: false
    }
  }
  static propTypes = {
    name: PropTypes.string,
    description: PropTypes.string,
    updateTrial: PropTypes.func,
    id: PropTypes.any
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
    }
  }
  render () {
    let trial = {
      trialId: this.props.id,
      trialName: this.props.name,
      trialDescription: this.props.description
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
