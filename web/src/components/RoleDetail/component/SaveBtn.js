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
    updateRole: PropTypes.func,
    addNewRole: PropTypes.func,
    roleName: PropTypes.string,
    trialId: PropTypes.any,
    roleId: PropTypes.any,
    new: PropTypes.bool,
    roleType: PropTypes.string
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

  async dialogAccepted (role) {
    if (!this.state.new) {
      this.props.updateRole(role)
      this.handleCloseDialog('openSaveDialog')
    } else {
      await this.props.addNewRole(role)
      browserHistory.push(`/trial-manager/trial-detail/${this.props.trialId}/role/${this.props.roleId}`)
    }
  };

  render () {
    let role = {
      id: this.props.roleId,
      name: this.props.roleName || '',
      roleType: this.props.roleType,
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
        onClick={this.dialogAccepted.bind(this, role)}
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
