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
      roleId: this.props.roleId
    }
  }

  static propTypes={
    roleId: PropTypes.any,
    removeRole: PropTypes.func,
    trialId: PropTypes.any
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
  async removeRole () {
    if (
      this.props.removeRole
    ) {
      await this.props.removeRole(this.props.roleId)
      browserHistory.push(`/trial-manager/trial-detail/${this.props.trialId}`)
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
        onClick={this.removeRole.bind(this)}
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
          title='Are you sure to remove role?'
          actions={actionsRemoveDialog}
          modal={false}
          contentClassName='custom__dialog'
          open={this.state.openRemoveDialog}
          onRequestClose={this.handleCloseDialog.bind(this, 'openRemoveDialog')}
        />
      </div>
    )
  }
}
export default RemoveBtn
