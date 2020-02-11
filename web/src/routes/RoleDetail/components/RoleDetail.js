import React, { Component } from 'react'
import PropTypes from 'prop-types'
import RoleDetail from '../../../components/RoleDetail/component/RoleDetail'

class RoleDetailView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    params: PropTypes.object,
    id_trial: PropTypes.any,
    trialName: PropTypes.string,
    roleName: PropTypes.string,
    getRoleById: PropTypes.func,
    removeRole: PropTypes.func,
    updateRole: PropTypes.func,
    roleType: PropTypes.string,
    questions: PropTypes.array,
    userRoles: PropTypes.array,
    roleSet: PropTypes.array,
    getTrialDetail: PropTypes.func,
    usersList: PropTypes.array,
    getUsersList: PropTypes.func,
    addUser: PropTypes.func,
    removeUser: PropTypes.func
  };

  render () {
    return (
      <div className='background-home'>
        <RoleDetail
          new={false}
          loadData
          trialId={this.props.params.id_trial}
          trialName={this.props.trialName}
          roleId={this.props.params.id_role}
          roleName={this.props.roleName}
          getRoleById={this.props.getRoleById}
          removeRole={this.props.removeRole}
          updateRole={this.props.updateRole}
          roleType={this.props.roleType}
          questions={this.props.questions}
          userRoles={this.props.userRoles}
          roleSet={this.props.roleSet}
          getTrialDetail={this.props.getTrialDetail}
          usersList={this.props.usersList}
          getUsersList={this.props.getUsersList}
          addUser={this.props.addUser}
          removeUser={this.props.removeUser}
        />
      </div>
    )
  }
}

export default RoleDetailView
