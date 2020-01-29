import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import Users from '../../../components/Users/components/Users'
import {
  getAllUsersList,
  putUser,
  getSelectedUser,
  addUser,
  putUserPassword,
  passwordUpdateFailAction
} from '../modules/users'

class UsersManager extends Component {
  static propTypes = {
    getAllUsersList: PropTypes.func,
    getSelectedUser: PropTypes.func,
    putUser: PropTypes.func,
    addUser: PropTypes.func,
    passwordUpdateFailAction: PropTypes.func,
    putUserPassword: PropTypes.func,
    allUsersList: PropTypes.object,
    selectedUser: PropTypes.object,
    allUsersListLoading: PropTypes.bool,
    isUserLoading: PropTypes.bool,
    isPasswordUpdated: PropTypes.bool
  }
  render () {
    return (
      <div>
        <Users
          allUsersList={this.props.allUsersList}
          selectedUser={this.props.selectedUser}
          allUsersListLoading={this.props.allUsersListLoading}
          isUserLoading={this.props.isUserLoading}
          getAllUsersList={this.props.getAllUsersList}
          getSelectedUser={this.props.getSelectedUser}
          putUser={this.props.putUser}
          addUser={this.props.addUser}
          putUserPassword={this.props.putUserPassword}
          isPasswordUpdated={this.props.isPasswordUpdated}
          passwordUpdateFailAction={this.props.passwordUpdateFailAction}
        />
      </div>
    )
  }
}

const mapDispatchToProps = {
  getAllUsersList,
  putUser,
  getSelectedUser,
  addUser,
  putUserPassword,
  passwordUpdateFailAction
}

const mapStateToProps = (state) => ({
  allUsersList: state.usersManager.allUsersList,
  selectedUser: state.usersManager.selectedUser,
  allUsersListLoading: state.usersManager.allUsersListLoading,
  isUserLoading: state.usersManager.isUserLoading,
  isPasswordUpdated: state.usersManager.isPasswordUpdated
})

export default connect(mapStateToProps, mapDispatchToProps)(UsersManager)
