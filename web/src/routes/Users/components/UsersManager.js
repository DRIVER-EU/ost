import React, { Component } from 'react'
import { connect } from 'react-redux'
import Users from '../../../components/Users/components/Users'
import PropTypes, { bool } from 'prop-types'
import { getAllUsersList, putUser, getSelectedUser, addUser } from '../modules/users'

class UsersManager extends Component {
  static propTypes = {
    getAllUsersList: PropTypes.func,
    getSelectedUser: PropTypes.func,
    putUser: PropTypes.func,
    addUser: PropTypes.func,
    allUsersList: PropTypes.object,
    selectedUser: PropTypes.object,
    allUsersListLoading: bool,
    isUserLoading: bool
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
        />
      </div>
    )
  }
}

const mapDispatchToProps = {
  getAllUsersList,
  putUser,
  getSelectedUser,
  addUser
}

const mapStateToProps = (state) => ({
  allUsersList: state.usersManager.allUsersList,
  selectedUser: state.usersManager.selectedUser,
  allUsersListLoading: state.usersManager.allUsersListLoading,
  isUserLoading: state.usersManager.isUserLoading
})

export default connect(mapStateToProps, mapDispatchToProps)(UsersManager)
