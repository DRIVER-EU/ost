import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './RoleDetail.scss'
import TextField from 'material-ui/TextField'
import SaveBtn from './SaveBtn'
import RemoveBtn from './RemoveBtn'
import SelectField from 'material-ui/SelectField'
import MenuItem from 'material-ui/MenuItem'

class RoleDetail extends Component {
  constructor (props) {
    super(props)
    this.state = {
      roleId: '',
      name: this.props.roleName || '',
      roleTypes: [
        { id: 'PARTICIPANT', name: 'PARTICIPANT' },
        { id: 'OBSERVER', name: 'OBSERVER' }
      ],
      selectedCurrentRoleType: this.props.roleType || ''
    }
  }

  static propTypes = {
    trialId: PropTypes.any,
    new: PropTypes.bool,
    trialName: PropTypes.string,
    getTrialDetail: PropTypes.func,
    roleId: PropTypes.any,
    roleName: PropTypes.string,
    updateRole: PropTypes.func,
    removeRole: PropTypes.func,
    getRoleById: PropTypes.func,
    addNewRole: PropTypes.func,
    roleType: PropTypes.string
  };
  handleChangeInput (name, e) {
    let change = {}
    change[name] = e.target.value
    this.setState(change)
  }

  componentWillReceiveProps (nextProps) {
    this.setState({
      name: nextProps.roleName,
      roleId: nextProps.roleId,
      selectedCurrentRoleType: nextProps.roleType
    })
  }
  handleChangeCurrentRoleType = (event, index, value) => {
    this.setState({ selectedCurrentRoleType: value })
  };
  componentDidMount () {
    if (this.props.getTrialDetail) {
      this.props.getTrialDetail(this.props.trialId)
    }
    if (this.props.getRoleById) {
      this.props.getRoleById(this.props.roleId)
    }
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='stage-container'>
            <div className='stage__header'>
              <h1 className='header__text'>Role</h1>
              <a
                className='header__link'
                href={`/trial-manager/trial-detail/${this.props.trialId}`}
              >
                {this.props.trialName}
              </a>
            </div>
            <div className='stageDetail__info'>
              <div className='info__container'>
                <TextField
                  type='id'
                  value={this.props.roleId}
                  floatingLabelText='Id'
                  fullWidth
                  underlineShow={false}
                />
                <TextField
                  type='name'
                  onChange={this.handleChangeInput.bind(this, 'name')}
                  value={this.state.name}
                  floatingLabelText='Name'
                  fullWidth
                />
                <div>
                  <SelectField
                    floatingLabelText='Role Type'
                    value={this.state.selectedCurrentRoleType}
                    onChange={this.handleChangeCurrentRoleType}
                  >
                    {this.state.roleTypes.map(role => (
                      <MenuItem
                        key={role.id}
                        value={role.id}
                        primaryText={role.name}
                      />
                    ))}
                  </SelectField>
                </div>
              </div>
              <div className='btns__wrapper'>
                <SaveBtn
                  new={this.props.new}
                  trialId={this.props.trialId}
                  roleName={this.state.name}
                  roleId={this.props.roleId}
                  updateRole={this.props.updateRole}
                  addNewRole={this.props.addNewRole}
                  roleType={this.state.selectedCurrentRoleType}
                />
                {!this.props.new && (
                  <RemoveBtn
                    trialId={this.props.trialId}
                    roleId={this.props.roleId}
                    removeRole={this.props.removeRole}
                  />
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default RoleDetail
