import React, { Component } from 'react'
import PropTypes from 'prop-types'
import NewSessionComponent from '../../../components/NewSessionComponent'

class NewSession extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getRoles: PropTypes.func,
    rolesList: PropTypes.object,
    getStages: PropTypes.func,
    stagesList: PropTypes.object,
    params: PropTypes.any
  }

  render () {
    return (
      <NewSessionComponent
        getRoles={this.props.getRoles}
        rolesList={this.props.rolesList}
        getStages={this.props.getStages}
        stagesList={this.props.stagesList}
        params={this.props.params}
      />
    )
  }
}

export default NewSession
