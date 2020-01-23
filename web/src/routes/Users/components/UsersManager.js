import React, { Component } from 'react'
import { connect } from 'react-redux'
import Users from '../../../components/Users/components/Users'
import PropTypes, { bool } from 'prop-types'
import { getAllUsersList } from '../modules/users'

class UsersManager extends Component {
  static propTypes = {
    getAllUsersList: PropTypes.func,
    allUsersList: PropTypes.object,
    allUsersListLoading: bool
  }
  render () {
    return (
      <div>
        <Users
          allUsersList={this.props.allUsersList}
          allUsersListLoading={this.props.allUsersListLoading}
          getAllUsersList={this.props.getAllUsersList} />
      </div>
    )
  }
}

const mapDispatchToProps = {
  getAllUsersList
}

const mapStateToProps = (state) => ({
  allUsersList: state.usersManager.allUsersList,
  allUsersListLoading: state.usersManager.allUsersListLoading
})

export default connect(mapStateToProps, mapDispatchToProps)(UsersManager)
