import React, { Component } from 'react'
import PropTypes from 'prop-types'
import 'react-table/react-table.css'
import ReactTable from 'react-table'
// import SelectField from 'material-ui/SelectField'
// import MenuItem from 'material-ui/MenuItem'
// import RaisedButton from 'material-ui/RaisedButton'
// import Dialog from 'material-ui/Dialog'
// import FlatButton from 'material-ui/FlatButton'

class UserRole extends Component {
  constructor (props) {
    super(props)
    this.state = {
      roleId: '',
      statusType: [
        { value: 'ACTIVE', text: 'Active' },
        { value: 'SUSPENDED', text: 'Suspended' },
        { value: 'ENDED', text: 'Ended' }
      ],
      selectedRole: null,
      openUserRoleDialog: false,
      selectedCurrentRole: '',
      selectedCurrentUser: '',
      openRemoveDialog: false,
      openAddUserInfoDialog: false,
      usersList: this.props.usersList || []
    }
  }
  static propTypes = {
    userRoles: PropTypes.array,
    // new: PropTypes.bool,
    // roleSet: PropTypes.array,
    usersList: PropTypes.array,
    roleId: PropTypes.any
  };
  newUserRole () {
    this.handleOpen()
  }
  handleOpen = () => {
    this.setState({ openUserRoleDialog: true })
  };

  handleClose = () => {
    this.setState({ openUserRoleDialog: false })
  };

  async addUser (user) {
    if (!this.checkIfExist(user, this.props.userRoles)) {
      await this.props.addUser(user)
      this.handleClose()
      if (this.props.getRoleById) {
        this.props.getRoleById(this.props.roleId)
      }
    } else {
      this.handleOpenDialog('openAddUserInfoDialog')
    }
  }
  async removeUser (user) {
    await this.props.removeUser(
      user.id.trialRoleId,
      user.id.trialSessionId,
      user.id.trialUserId
    )
    this.handleCloseDialog('openRemoveDialog')
    this.setState({ selectedRole: null })
    if (this.props.getRoleById) {
      this.props.getRoleById(this.props.roleId)
    }
  }
  checkIfExist (user, usersList) {
    let isExist = false
    for (let i = 0; i < usersList.length; i++) {
      const userItem = usersList[i]
      if (user.trialRoleId === userItem.id.trialRoleId && user.trialUserId === userItem.id.trialUserId) {
        isExist = true
      }
    }
    return isExist
  }
  handleChangeCurrentRole = (event, index, value) => {
    this.setState({ selectedCurrentRole: value })
  };
  handleChangeCurrentUser = (event, index, value) => {
    this.setState({ selectedCurrentUser: value })
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
  render () {
    const columns = [
      {
        Header: 'User Assignment',
        columns: [
          {
            Header: 'Id',
            accessor: 'id.trialUserId',
            width: 100,
            style: { textAlign: 'right' }
          },
          {
            Header: 'User name',
            accessor: 'trialUserName',
            style: { textAlign: 'left' }
          },
          {
            Header: 'Role name',
            accessor: 'trialRoleName',
            style: { textAlign: 'left' }
          }
        ]
      }
    ]
    // const user = {
    //   trialRoleId: this.state.selectedCurrentRole,
    //   trialSessionId: 1,
    //   trialUserId: this.state.selectedCurrentUser
    // }
    // const actions = [
    //   <FlatButton label='Cancel' primary onClick={this.handleClose} />,
    //   <RaisedButton
    //     backgroundColor='#FCB636'
    //     labelColor='#fff'
    //     label='Add'
    //     type='Button'
    //     onClick={this.addUser.bind(this, user)}
    //   />
    // ]
    // const actionsAddUser = [
    //   <RaisedButton
    //     backgroundColor='#FCB636'
    //     labelColor='#fff'
    //     label='Ok'
    //     type='Button'
    //     onClick={this.handleCloseDialog.bind(this, 'openAddUserInfoDialog')}
    //   />
    // ]
    // const actionsRemoveDialog = [
    //   <FlatButton
    //     label='No'
    //     primary
    //     onClick={this.handleCloseDialog.bind(this, 'openRemoveDialog')}
    //   />,
    //   <RaisedButton
    //     backgroundColor='#b71c1c'
    //     labelColor='#fff'
    //     label='Yes'
    //     type='Button'
    //     onClick={this.removeUser.bind(this, this.state.selectedRole)}
    //   />
    // ]
    return (
      <div className='table__wrapper'>
        <ReactTable
          data={this.props.userRoles}
          columns={columns}
          multiSort
          showPagination={false}
          defaultPageSize={500}
          minRows={0}
          getTdProps={(state, rowInfo) => {
            if (rowInfo && rowInfo.row) {
              return {
                onClick: e => {
                  this.setState({
                    selectedRole: rowInfo.original
                  })
                },
                // onDoubleClick: e => {
                //   this.viewUserRole()
                // },
                style: {
                  background: this.state.selectedRole
                    ? rowInfo.original.id === this.state.selectedRole.id
                      ? '#e5e5e5'
                      : ''
                    : '',
                  cursor: 'pointer'
                }
              }
            }
          }}
        />
        {/* {!this.props.new && (
          <div className='action-btns'>
            <div>
              <RaisedButton
                buttonStyle={{ width: '200px' }}
                backgroundColor='#244C7B'
                labelColor='#FCB636'
                label='+ New'
                type='Button'
                disabled
                onClick={this.newUserRole.bind(this)}
              />

              <Dialog
                title='Add User'
                actions={actions}
                contentClassName='custom__dialog'
                modal={false}
                open={this.state.openUserRoleDialog}
                onRequestClose={this.handleClose}
              >
                <div>
                  <SelectField
                    floatingLabelText='User'
                    value={this.state.selectedCurrentUser}
                    onChange={this.handleChangeCurrentUser}
                  >
                    {this.props.usersList.map(user => (
                      <MenuItem
                        key={user.id}
                        value={user.id}
                        primaryText={`${user.firstName} ${user.lastName}`}
                      />
                    ))}
                  </SelectField>
                </div>
                <div>
                  <SelectField
                    floatingLabelText='Role'
                    value={this.state.selectedCurrentRole}
                    onChange={this.handleChangeCurrentRole}
                  >
                    {this.props.roleSet.map(role => (
                      <MenuItem
                        key={role.id}
                        value={role.id}
                        primaryText={role.name}
                      />
                    ))}
                  </SelectField>
                </div>
              </Dialog>
              <Dialog
                title='This user role already exists.'
                actions={actionsAddUser}
                modal={false}
                contentClassName='custom__dialog'
                open={this.state.openAddUserInfoDialog}
                onRequestClose={this.handleCloseDialog.bind(
                  this,
                  'openAddUserInfoDialog'
                )}
              />
            </div>
            <RaisedButton
              buttonStyle={{ width: '200px' }}
              labelColor='#fff'
              label='Remove'
              type='Button'
              backgroundColor={this.state.selectedRole ? '#b71c1c' : '#ccc'}
              disabled={!this.state.selectedRole}
              onClick={this.handleOpenDialog.bind(this, 'openRemoveDialog')}
            />
            <Dialog
              title='Do you want to remove user role?'
              actions={actionsRemoveDialog}
              modal={false}
              contentClassName='custom__dialog'
              open={this.state.openRemoveDialog}
              onRequestClose={this.handleCloseDialog.bind(
                this,
                'openRemoveDialog'
              )}
            />
          </div>
        )} */}
      </div>
    )
  }
}
export default UserRole
