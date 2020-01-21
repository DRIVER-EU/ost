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
      stageList: this.props.stages
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
    openRemoveInfoDialog: PropTypes.bool
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
      selectedCurrentStage: nextProps.stageId

    })
  }
  handleChangeStatus = (event, index, value) => {
    this.setState({ selectedStatus: value })
  };
  handleChangeCurrentStage = (event, index, value) => {
    this.setState({ selectedCurrentStage: value })
  };
  getObservations (sessionId) {
    this.props.getObservations(sessionId)
  }
  componentDidMount () {
    if (this.props.getTrialDetail) {
      this.props.getTrialDetail(this.props.trialId)
    }
    if (this.props.getSessionDetail) {
      this.props.getSessionDetail(this.props.sessionId)
    }
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
            {!this.props.new && <div>
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
            </div>}
            {!this.props.new && <div className='download__btn'>
              <RaisedButton
                backgroundColor='#FCB636'
                labelColor='#fff'
                label='Download observations'
                type='Button'
                onClick={this.getObservations.bind(this, this.props.sessionId)}
              />
            </div>}
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
                          selectedQuestion: rowInfo.original
                        })
                      },
                      onDoubleClick: e => {
                        this.viewQuestion()
                      },
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
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default SessionDetail
