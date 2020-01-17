import React, { Component } from 'react'
import Dialog from 'material-ui/Dialog'
import RaisedButton from 'material-ui/RaisedButton'
import FlatButton from 'material-ui/FlatButton'
import browserHistory from 'react-router/lib/browserHistory'
import PropTypes from 'prop-types'

class RemoveBtn extends Component {
  constructor (props) {
    super(props)
    this.state = {
      openRemoveDialog: false,
      openRemoveInfoDialog: false,
      id: this.props.id
    }
  }
  static propTypes = {
    sessionList: PropTypes.array,
    stageList: PropTypes.array,
    roleList: PropTypes.array,
    removeTrial: PropTypes.func,
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
  async removeTrial (id) {
    if (
      this.props.sessionList.length === 0 &&
      this.props.stageList.length === 0 &&
      this.props.roleList.length === 0
    ) {
      await this.props.removeTrial(id)
      browserHistory.push('/trial-manager')
    } else {
      this.handleCloseDialog('openRemoveDialog')
      this.handleOpenDialog('openRemoveInfoDialog')
    }
  }
  render () {
    const id = this.props.id
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
        onClick={this.removeTrial.bind(this, id)}
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
          title='Are you sure to remove trial?'
          actions={actionsRemoveDialog}
          modal={false}
          contentClassName='custom__dialog'
          open={this.state.openRemoveDialog}
          onRequestClose={this.handleCloseDialog.bind(this, 'openRemoveDialog')}
        />
        <Dialog
          title='System cannot remove this trial due to existing sessions, stages or roles belongs to this trial.'
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
