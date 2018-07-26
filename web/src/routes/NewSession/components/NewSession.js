import React, { Component } from 'react'
import PropTypes from 'prop-types'
import NewSessionComponent from '../../../components/NewSessionComponent'

class NewSession extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getUsers: PropTypes.func,
    usersList: PropTypes.object,
    getRoles: PropTypes.func,
    rolesList: PropTypes.object,
    getStages: PropTypes.func,
    stagesList: PropTypes.object
  }

  render () {
    return (
      <NewSessionComponent
        getUsers={this.props.getUsers}
        usersList={this.props.usersList}
        getRoles={this.props.getRoles}
        rolesList={this.props.rolesList}
        getStages={this.props.getStages}
        stagesList={this.props.stagesList}
      />
    )
  }
}

export default NewSession
