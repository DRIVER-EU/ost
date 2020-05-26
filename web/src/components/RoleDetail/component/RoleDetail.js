import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './RoleDetail.scss'
import TextField from 'material-ui/TextField'
import SaveBtn from './SaveBtn'
import RemoveBtn from './RemoveBtn'
// import SelectField from 'material-ui/SelectField'
// import MenuItem from 'material-ui/MenuItem'
import ReactTable from 'react-table'
// import RaisedButton from 'material-ui/RaisedButton'
import Checkbox from 'material-ui/Checkbox'
import { browserHistory } from 'react-router'
import UserRole from './UserRole'

class RoleDetail extends Component {
  constructor (props) {
    super(props)
    this.state = {
      roleId: '',
      name: this.props.roleName || '',
      // roleTypes: [
      //   { id: 'PARTICIPANT', name: 'PARTICIPANT' },
      //   { id: 'OBSERVER', name: 'OBSERVER' }
      // ],
      // selectedCurrentRoleType: this.props.roleType || 'OBSERVER',
      selectedCurrentRoleType: 'OBSERVER',
      questionsList: this.props.questions,
      selectedQuestion: null,
      roleSet: this.props.roleSet,
      userRoles: this.props.userRoles,
      trialName: this.props.trialName,
      unassignedQuestions: this.props.unassignedQuestions,
      selectedUnassignedQuestion: null
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
    // roleType: PropTypes.string,
    questions: PropTypes.array,
    userRoles: PropTypes.array,
    roleSet: PropTypes.array,
    usersList: PropTypes.array,
    getUsersList: PropTypes.func,
    addUser: PropTypes.func,
    removeUser: PropTypes.func,
    unassignedQuestions: PropTypes.array,
    assignQuestion: PropTypes.func
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
      // selectedCurrentRoleType: nextProps.roleType,
      questionsList: nextProps.questions,
      userRoles: nextProps.userRoles,
      roleSet: nextProps.roleSet,
      trialName: nextProps.trialName,
      unassignedQuestions: nextProps.unassignedQuestions
    })
  }
  // handleChangeCurrentRoleType = (event, index, value) => {
  //   this.setState({ selectedCurrentRoleType: value })
  // };
  componentDidMount () {
    if (this.props.getTrialDetail) {
      this.props.getTrialDetail(this.props.trialId)
    }
    if (this.props.getRoleById) {
      this.props.getRoleById(this.props.roleId)
    }
    if (this.props.getUsersList) {
      this.props.getUsersList()
    }
  }
  viewQuestion () {
    if (this.state.selectedQuestion) {
      let trialId = this.props.trialId
      let roleId = this.props.roleId
      let questionId = this.state.selectedQuestion.id
      browserHistory.push(
        `/trial-manager/trial-detail/${trialId}/role/${roleId}/question/${questionId}`
      )
    }
  }
  newQuestion () {
    browserHistory.push(
      `/trial-manager/trial-detail/${this.props.trialId}/role/${this.state.roleId}/new-question`
    )
  }
  async assignQuestion (questionId) {
    let question = {
      observationTypeId: questionId,
      trialRoleId: this.state.roleId
    }
    if (this.props.assignQuestion) {
      await this.props.assignQuestion(question)
      if (this.props.getRoleById) {
        this.props.getRoleById(this.state.roleId)
      }
      this.setState({ selectedUnassignedQuestion: null })
    }
  }
  async unassignQuestion (questionId) {
    if (this.props.unassignQuestion) {
      await this.props.unassignQuestion(this.state.roleId, questionId)
      if (this.props.getRoleById) {
        this.props.getRoleById(this.state.roleId)
      }
      this.setState({ selectedQuestion: null })
    }
  }

  render () {
    const columns = [
      {
        // Header: 'Assigned Question Set',
        Header: 'Question Set',
        columns: [
          {
            Header: 'Id',
            accessor: 'id',
            width: 100,
            style: { textAlign: 'right' }
          },
          {
            Header: 'Name',
            accessor: 'name',
            style: { textAlign: 'left' }
          },
          // {
          //   Header: 'Position',
          //   accessor: 'position',
          //   style: { textAlign: 'left' }
          // },
          // {
          //   Header: 'Unassign',
          //   Cell: props => (
          //     <RaisedButton
          //       backgroundColor='#FCB636'
          //       labelColor='#fff'
          //       label='Unassign'
          //       type='Button'
          //       onClick={this.unassignQuestion.bind(this, props.original.id)}
          //     />
          //   ),
          //   width: 200
          // }
          {
            Header: 'Assigned',
            Cell: props => (
              <Checkbox
                checked={props.original.assign}
                onCheck={props.original.assign
                  ? this.unassignQuestion.bind(this, props.original.id)
                  : this.assignQuestion.bind(this, props.original.id)}
                />
              // props.original.assign
              // ? <RaisedButton
              //   backgroundColor='#FCB636'
              //   labelColor='#fff'
              //   label='Unassign'
              //   type='Button'
              //   onClick={this.unassignQuestion.bind(this, props.original.id)}
              // />
              // : <RaisedButton
              //   backgroundColor='#FCB636'
              //   labelColor='#fff'
              //   label='Assign'
              //   type='Button'
              //   onClick={this.assignQuestion.bind(this, props.original.id)}
              // />
            ),
            width: 100
          }
        ]
      }
    ]
    // const columnsUnassignedQuestions = [
    //   {
    //     Header: 'Unassigned Question Set',
    //     columns: [
    //       {
    //         Header: 'Id',
    //         accessor: 'id',
    //         width: 100,
    //         style: { textAlign: 'right' }
    //       },
    //       {
    //         Header: 'Name',
    //         accessor: 'name',
    //         style: { textAlign: 'left' }
    //       },
    //       // {
    //       //   Header: 'Position',
    //       //   accessor: 'position',
    //       //   style: { textAlign: 'left' }
    //       // },
    //       {
    //         Header: 'Assign',
    //         Cell: props => (
    //           props.original.assign
    //           ? <RaisedButton
    //             backgroundColor='#FCB636'
    //             labelColor='#fff'
    //             label='Assign'
    //             type='Button'
    //             onClick={this.assignQuestion.bind(this, props.original.id)}
    //           />
    //           : <RaisedButton
    //             backgroundColor='#FCB636'
    //             labelColor='#fff'
    //             label='Unassign'
    //             type='Button'
    //             onClick={this.unassignQuestion.bind(this, props.original.id)}
    //           />
    //         ),
    //         width: 200
    //       }
    //     ]
    //   }
    // ]
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='stage-container'>
            <div className='stage__header'>
              <h1 className='header__text'>Role</h1>
              <a
                className='header__link'
                style={{ cursor: 'pointer' }}
                onClick={() => browserHistory.push(`/trial-manager/trial-detail/${this.props.trialId}`)}
              >
                {this.props.trialName}
              </a>
            </div>
            <div className='stageDetail__info'>
              <div className='info__container'>
                <TextField
                  type='id'
                  value={this.state.roleId}
                  floatingLabelText='Id'
                  fullWidth
                  underlineShow={false}
                  disabled
                />
                <TextField
                  type='name'
                  onChange={this.handleChangeInput.bind(this, 'name')}
                  value={this.state.name}
                  floatingLabelText='Name'
                  fullWidth
                />
                <div>
                  {/* <SelectField
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
                  </SelectField> */}
                </div>
              </div>
              <div className='btns__wrapper'>
                <SaveBtn
                  new={this.props.new}
                  trialId={this.props.trialId}
                  roleName={this.state.name}
                  roleId={this.state.roleId}
                  updateRole={this.props.updateRole}
                  addNewRole={this.props.addNewRole}
                  roleType={this.state.selectedCurrentRoleType}
                  inputsValue={[this.state.name]}
                  getRoleById={this.props.getRoleById}
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
            <UserRole
              userRoles={this.props.userRoles}
              roleSet={this.props.roleSet}
              getRoleById={this.props.getRoleById}
              roleId={this.props.roleId}
              usersList={this.props.usersList}
              addUser={this.props.addUser}
              removeUser={this.props.removeUser}
              new={this.props.new}
            />
            <div className='table__wrapper'>
              <ReactTable
                data={this.state.questionsList}
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
                          selectedQuestion: rowInfo.original
                        })
                      },
                      // onDoubleClick: e => {
                      //   this.viewQuestion()
                      // },
                      style: {
                        background: this.state.selectedQuestion
                          ? rowInfo.original.id ===
                            this.state.selectedQuestion.id
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
                  <RaisedButton
                    buttonStyle={{ width: '200px' }}
                    backgroundColor='#244C7B'
                    labelColor='#FCB636'
                    label='+ New'
                    type='Button'
                    disabled
                    onClick={this.newQuestion.bind(this)}
                  />
                  <RaisedButton
                    buttonStyle={{ width: '200px' }}
                    backgroundColor={
                      this.state.selectedQuestion ? '#FCB636' : '#ccc'
                    }
                    labelColor='#fff'
                    label='Edit'
                    type='Button'
                    disabled={!this.state.selectedQuestion}
                    onClick={this.viewQuestion.bind(this)}
                  />
                </div>
              )} */}
            </div>
            {/* <div className='table__wrapper'>
              <ReactTable
                data={this.state.unassignedQuestions}
                columns={columnsUnassignedQuestions}
                multiSort
                showPagination={false}
                defaultPageSize={500}
                minRows={0}
                getTdProps={(state, rowInfo) => {
                  if (rowInfo && rowInfo.row) {
                    return {
                      onClick: e => {
                        this.setState({
                          selectedUnassignedQuestion: rowInfo.original
                        })
                      },
                      // onDoubleClick: e => {
                      //   this.viewQuestion()
                      // },
                      style: {
                        background: this.state.selectedUnassignedQuestion
                          ? rowInfo.original.id ===
                            this.state.selectedUnassignedQuestion.id
                            ? '#e5e5e5'
                            : ''
                          : '',
                        cursor: 'pointer'
                      }
                    }
                  }
                }}
              />
            </div> */}
          </div>
        </div>
      </div>
    )
  }
}

export default RoleDetail
