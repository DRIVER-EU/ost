import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './SessionDetail.scss'
import 'react-table/react-table.css'
import TextField from 'material-ui/TextField'
import SaveBtn from './SaveBtn'
import RemoveBtn from './RemoveBtn'
import ReactTable from 'react-table'
import SelectField from 'material-ui/SelectField'
import MenuItem from 'material-ui/MenuItem'
import RaisedButton from 'material-ui/RaisedButton'
import Dialog from 'material-ui/Dialog'
import Checkbox from 'material-ui/Checkbox'
import FlatButton from 'material-ui/FlatButton'

class SessionDetail extends Component {
  constructor (props) {
    super(props)
    this.state = {
      sessionId: '',
      sessionName: this.props.sessionName || '',
      userRoles: this.props.userRoles || [],
      selectedStatus: this.props.status || '',
      selectedCurrentStage: this.props.stageId || 1,
      statusType: [
        { value: 'ACTIVE', text: 'Active' },
        { value: 'SUSPENDED', text: 'Suspended' },
        { value: 'ENDED', text: 'Ended' }
      ],
      stageList: this.props.stages,
      selectedRole: null,
      openUserRoleDialog: false,
      roleSet: this.props.roleSet || [],
      selectedCurrentRole: '',
      usersList: this.props.usersList || [],
      selectedCurrentUser: '',
      openRemoveDialog: false,
      openAddUserInfoDialog: false,
      manual: this.props.manual
    }
  }

  static propTypes = {
    trialId: PropTypes.any,
    stageId: PropTypes.any,
    sessionId: PropTypes.any,
    sessionName: PropTypes.string,
    userRoles: PropTypes.array,
    updateSession: PropTypes.func,
    new: PropTypes.bool,
    addNewSession: PropTypes.func,
    removeSession: PropTypes.func,
    trialName: PropTypes.string,
    getTrialDetail: PropTypes.func,
    getSessionDetail: PropTypes.func,
    stages: PropTypes.array,
    status: PropTypes.string,
    getObservations: PropTypes.func,
    openRemoveInfoDialog: PropTypes.bool,
    roleSet: PropTypes.array,
    usersList: PropTypes.array,
    getUsersList: PropTypes.func,
    addUser: PropTypes.func,
    removeUser: PropTypes.func,
    manual: PropTypes.bool
  };
  handleChangeInput (name, e) {
    let change = {}
    change[name] = e.target.value
    this.setState(change)
  }
  componentWillReceiveProps (nextProps) {
    this.setState({
      sessionName: nextProps.sessionName,
      sessionId: nextProps.sessionId,
      userRoles: nextProps.userRoles,
      stageList: nextProps.stages,
      selectedStatus: nextProps.status,
      selectedCurrentStage: nextProps.stageId,
      roleSet: nextProps.roleSet,
      usersList: nextProps.usersList,
      manual: nextProps.manual
    })
  }
  handleChangeStatus = (event, index, value) => {
    this.setState({ selectedStatus: value })
  };
  handleChangeCurrentStage = (event, index, value) => {
    this.setState({ selectedCurrentStage: value })
  };
  handleChangeCurrentRole = (event, index, value) => {
    this.setState({ selectedCurrentRole: value })
  };
  handleChangeCurrentUser = (event, index, value) => {
    this.setState({ selectedCurrentUser: value })
  };
  updateCheck (name) {
    this.setState({
      [name]: !this.state[name]
    })
  }
  getObservations (sessionId) {
    this.props.getObservations(sessionId)
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
  componentDidMount () {
    if (this.props.getTrialDetail) {
      this.props.getTrialDetail(this.props.trialId)
    }
    if (this.props.getSessionDetail) {
      this.props.getSessionDetail(this.props.sessionId)
    }
    if (this.props.getUsersList) {
      this.props.getUsersList()
    }
  }
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
    if (!this.checkIfExist(user, this.state.userRoles)) {
      await this.props.addUser(user)
      this.handleClose()
      if (this.props.getSessionDetail) {
        this.props.getSessionDetail(this.props.sessionId)
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
    if (this.props.getSessionDetail) {
      this.props.getSessionDetail(this.props.sessionId)
    }
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
  render () {
    const columns = [
      {
        Header: 'User Role',
        columns: [
          {
            Header: 'Id',
            accessor: 'id.trialUserId',
            width: 100
          },
          {
            Header: 'User name',
            accessor: 'trialUserName'
          },
          {
            Header: 'Role name',
            accessor: 'trialRoleName'
          }
        ]
      }
    ]
    const actionsRemoveDialog = [
      <FlatButton
        label='No'
        primary
        onClick={this.handleCloseDialog.bind(this, 'openRemoveDialog')}
      />,
      <RaisedButton
        backgroundColor='#b71c1c'
        labelColor='#fff'
        label='Yes'
        type='Button'
        onClick={this.removeUser.bind(this, this.state.selectedRole)}
      />
    ]
    const user = {
      trialRoleId: this.state.selectedCurrentRole,
      trialSessionId: parseInt(this.state.sessionId),
      trialUserId: this.state.selectedCurrentUser
    }
    const actions = [
      <FlatButton label='Cancel' primary onClick={this.handleClose} />,
      <RaisedButton
        backgroundColor='#FCB636'
        labelColor='#fff'
        label='Add'
        type='Button'
        onClick={this.addUser.bind(this, user)}
      />
    ]
    const actionsAddUser = [
      <RaisedButton
        backgroundColor='#FCB636'
        labelColor='#fff'
        label='Ok'
        type='Button'
        onClick={this.handleCloseDialog.bind(this, 'openAddUserInfoDialog')}
      />
    ]
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='stage-container'>
            <div className='stage__header'>
              <h1 className='header__text'>Session</h1>
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
                  value={this.state.sessionId}
                  floatingLabelText='Id'
                  fullWidth
                  underlineShow={false}
                />
                <TextField
                  type='name'
                  onChange={this.handleChangeInput.bind(this, 'sessionName')}
                  value={this.props.trialName}
                  floatingLabelText='Name'
                  fullWidth
                />
              </div>
              <div className='btns__wrapper'>
                <SaveBtn
                  new={this.props.new}
                  trialId={this.props.trialId}
                  sessionName={this.state.sessionName}
                  sessionId={this.state.sessionId}
                  updateSession={this.props.updateSession}
                  addNewSession={this.props.addNewSession}
                  stageId={this.state.selectedCurrentStage}
                  status={this.state.selectedStatus}
                  manual={this.state.manual}
                />
                {!this.props.new && (
                  <RemoveBtn
                    userRoles={this.state.userRoles}
                    removeSession={this.props.removeSession}
                    sessionId={this.props.sessionId}
                    trialId={this.props.trialId}
                    openRemoveInfoDialog={this.props.openRemoveInfoDialog}
                  />
                )}
              </div>
            </div>
            <div>
              <SelectField
                floatingLabelText='Status'
                value={this.state.selectedStatus}
                onChange={this.handleChangeStatus}
              >
                <MenuItem
                  value={this.state.statusType[0].value}
                  primaryText={this.state.statusType[0].text}
                />
                <MenuItem
                  value={this.state.statusType[1].value}
                  primaryText={this.state.statusType[1].text}
                />
                <MenuItem
                  value={this.state.statusType[2].value}
                  primaryText={this.state.statusType[2].text}
                />
              </SelectField>
            </div>
            {!this.props.new && (
              <div>
                <SelectField
                  floatingLabelText='Current Stage'
                  value={this.state.selectedCurrentStage}
                  onChange={this.handleChangeCurrentStage}
                >
                  {this.state.stageList.map(stage => (
                    <MenuItem
                      key={stage.id}
                      value={stage.id}
                      primaryText={stage.name}
                    />
                  ))}
                </SelectField>
              </div>
            )}
            <div>
              <Checkbox
                label='Manual'
                checked={this.state.manual}
                onCheck={this.updateCheck.bind(this, 'manual')}
              />
            </div>
            {!this.props.new && (
              <div className='download__btn'>
                <RaisedButton
                  backgroundColor='#FCB636'
                  labelColor='#fff'
                  label='Download observations'
                  type='Button'
                  onClick={this.getObservations.bind(
                    this,
                    this.props.sessionId
                  )}
                />
              </div>
            )}
            <div className='table__wrapper'>
              <ReactTable
                data={this.state.userRoles}
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
                      onDoubleClick: e => {
                        this.viewUserRole()
                      },
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
              {!this.props.new && (
                <div className='action-btns'>
                  <div>
                    <RaisedButton
                      buttonStyle={{ width: '200px' }}
                      backgroundColor='#244C7B'
                      labelColor='#FCB636'
                      label='+ New'
                      type='Button'
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
                          {this.state.usersList.map(user => (
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
                          {this.state.roleSet.map(role => (
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
                      onRequestClose={this.handleCloseDialog.bind(this, 'openAddUserInfoDialog')}
                   />
                  </div>
                  <RaisedButton
                    buttonStyle={{ width: '200px' }}
                    labelColor='#fff'
                    label='Remove'
                    type='Button'
                    backgroundColor={
                      this.state.selectedRole ? '#b71c1c' : '#ccc'
                    }
                    disabled={!this.state.selectedRole}

                    onClick={this.handleOpenDialog.bind(this, 'openRemoveDialog')}
                  />
                  <Dialog
                    title='Do you want to remove user role?'
                    actions={actionsRemoveDialog}
                    modal={false}
                    contentClassName='custom__dialog'
                    open={this.state.openRemoveDialog}
                    onRequestClose={this.handleCloseDialog.bind(this, 'openRemoveDialog')}
                   />
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default SessionDetail
