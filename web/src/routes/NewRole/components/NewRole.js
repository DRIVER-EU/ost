import React, { Component } from 'react'
import PropTypes from 'prop-types'
import RoleDetail from '../../../components/RoleDetail/component/RoleDetail'

class NewRoleView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    params: PropTypes.object,
    id_trial: PropTypes.any,
    trialName: PropTypes.string,
    roleId: PropTypes.any,
    roleName: PropTypes.string,
    addNewRole: PropTypes.func,
    roleType: PropTypes.string
  }

  render () {
    return (
      <div className='background-home'>
        <RoleDetail
          new
          loadData
          trialId={this.props.params.id_trial}
          trialName={this.props.trialName}
          roleId={this.props.roleId}
          roleName={this.props.roleName}
          roleType={this.props.roleType}
          addNewRole={this.props.addNewRole}
        />
      </div>
    )
  }
}

export default NewRoleView
