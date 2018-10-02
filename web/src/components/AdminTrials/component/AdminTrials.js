import React, { Component } from 'react'
import PropTypes from 'prop-types'
import moment from 'moment'
import { Table, TableBody,
  TableHeader, TableHeaderColumn,
  TableRow, TableRowColumn } from 'material-ui/Table'
import { Card } from 'material-ui/Card'
import DropDownMenu from 'material-ui/DropDownMenu'
import MenuItem from 'material-ui/MenuItem'
import TextField from 'material-ui/TextField'
import SelectField from 'material-ui/SelectField'
import RaisedButton from 'material-ui/RaisedButton'
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts'
import DateComponent from '../../DateComponent/DateComponent'
import SummaryOfObservationModal from '../../SummaryOfObservationModal/SummaryOfObservationModal'
import { Tabs, Tab } from 'material-ui/Tabs'
import ReactTooltip from 'react-tooltip'
import './AdminTrials.scss'
import _ from 'lodash'
import { toastr } from 'react-redux-toastr'

const toastrOptions = {
  timeOut: 3000
}

const statusList = [
  { id: 0, name: 'ACTIVE' },
  { id: 1, name: 'SUSPENDED' }
]

const rangeList = [
  { id: 0, name: '5 minutes', value: 5 * 60 * 1000 },
  { id: 1, name: '10 minutes', value: 10 * 60 * 1000 },
  { id: 2, name: '15 minutes', value: 15 * 60 * 1000 },
  { id: 3, name: '30 minutes', value: 30 * 60 * 1000 },
  { id: 4, name: '60 minutes', value: 60 * 60 * 1000 },
  { id: 5, name: '1 day', value: 24 * 60 * 60 * 1000 }
]

const styles = {
  searchPanelContainer: {
    width: '100%',
    justifyContent: 'flex-start',
    display: 'flex',
    alignItems: 'center',
    flexWrap: 'wrap'
  },
  default: {
    color: '#064C7B',
    backgroundColor: '#fff'
  },
  active: {
    color: '#fff',
    backgroundColor: '#064C7B'
  }
}

class AdminTrials extends Component {
  constructor (props) {
    super(props)
    const t = moment(new Date().getTime()).format('DD/MM/YYYY hh:mm')
    this.state = {
      observationForm: {},
      time: t,
      userValue: null,
      roleValue: null,
      title: '',
      titleErrorText: '',
      messageValue: '',
      messageValueErrorText: '',
      sortObservation: true,
      sourceValue: '',
      changeDataTable: [],
      observations: [],
      chartData: [],
      changeDataTableSorted: [],
      messages: [],
      messageTime: '',
      value: 'a',
      sort: {
        type: 'eventTime',
        order: 'asc'
      },
      errorUserRole: true,
      showModal: false,
      selectedObj: {},
      trialStage: '',
      trialStatus: '',
      usersList: [],
      rolesList: [],
      stagesList: [],
      interval: '',
      searchText: '',
      timeRange: '',
      isOneDay: false
    }
  }

  static propTypes = {
    getMessages: PropTypes.func,
    messages: PropTypes.object,
    isSendMessage: PropTypes.any,
    sendMessage: PropTypes.func,
    getObservation: PropTypes.func,
    observation: PropTypes.array,
    getUsers: PropTypes.func,
    usersList: PropTypes.object,
    getRoles: PropTypes.func,
    rolesList: PropTypes.object,
    getStages: PropTypes.func,
    stagesList: PropTypes.object,
    setStage: PropTypes.func,
    params: PropTypes.any,
    observationForm: PropTypes.any,
    getSchemaView: PropTypes.func,
    downloadFile: PropTypes.func,
    exportToCSV: PropTypes.func,
    setStatus: PropTypes.func
  }

  componentWillUnmount () {
    clearInterval(this.state.interval)
  }

  componentWillMount () {
    this.props.getMessages(this.props.params.id, this.state.sort)
    this.props.getObservation(this.props.params.id, this.state.searchText)
    this.props.getUsers(this.props.params.id)
    this.props.getRoles(this.props.params.trial_id)
    this.props.getStages(this.props.params.id)
    this.handleChangeRange(null, 0)

    let interval = setInterval(() => {
      this.props.getMessages(this.props.params.id, this.state.sort)
      this.props.getObservation(this.props.params.id, this.state.searchText)
    }, 3000)
    this.setState({ interval: interval })
  }

  shouldComponentUpdate (nextProps, nextState) {
    for (var key in this.state) {
      for (var nextKey in nextState) {
        if (nextKey === key && this.state[key] !== nextState[nextKey]) {
          return true
        }
        if (key === nextKey && typeof (this.state[key]) === 'object' &&
        !_.isEqual(this.state[key], nextState[nextKey])) {
          return true
        }
      }
    }
    return false
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.usersList && nextProps.usersList !== this.props.usersList) {
      this.setState({ usersList: nextProps.usersList.data })
    }
    if (nextProps.rolesList && nextProps.rolesList !== this.props.rolesList) {
      this.setState({ rolesList: nextProps.rolesList.data })
    }
    if (nextProps.stagesList && nextProps.stagesList !== this.props.stagesList) {
      this.setState({ stagesList: nextProps.stagesList.data })
    }
    if (nextProps.observation && !_.isEqual(nextProps.observation, this.state.changeDataTable)) {
      let sortedList = _.orderBy(nextProps.observation, ['sentSimulationTime'], ['asc'])
      let dateMax = (new Date(_.maxBy(sortedList, 'sentSimulationTime').sentSimulationTime).getTime())
      let dateMin = new Date(_.minBy(sortedList, 'sentSimulationTime').sentSimulationTime).getTime()
      if (dateMax - dateMin > 2 * 3600 * 1000) {
        let change = {}
        change['timeRange'] = 5
        change['isOneDay'] = true
        this.setState(change, () => this.setState({ changeDataTable: nextProps.observation }, () => {
          this.handleChangeChart(this.state.changeDataTable)
        }))
      } else {
        this.setState({ changeDataTable: nextProps.observation }, () => {
          this.handleChangeChart(this.state.changeDataTable)
        })
      }
    }
    if (nextProps.observationForm && !_.isEqual(nextProps.observationForm, this.state.observationForm)) {
      this.setState({ observationForm: nextProps.observationForm })
    }
    if (nextProps.messages.data &&
      !_.isEqual(nextProps.messages.data, this.state.messages)) {
      this.setState({ messages: nextProps.messages.data })
    }
    if (nextProps.isSendMessage.time && this.state.messageTime !== nextProps.isSendMessage.time) {
      this.setState({ messageTime: nextProps.isSendMessage.time }, () => {
        this.props.getMessages()
      })
    }
    if (this.props.messages && this.props.observation && this.props.isSendMessage) {
      // eslint
    }
  }

  handleChangeChart (list) {
    if (list.length !== 0) {
      let sortedList = _.orderBy(list, ['sentSimulationTime'], ['asc'])
      let dateMax = (new Date(_.maxBy(sortedList, 'sentSimulationTime').sentSimulationTime).getTime())
      let dateMin = new Date(_.minBy(sortedList, 'sentSimulationTime').sentSimulationTime).getTime()
      let data = []
      let chartData = []
      data[0] = { date: dateMin, count: 0 }
      let i = 0
      while (true) {
        if (data[i].date + rangeList[this.state.timeRange].value > dateMax) {
          break
        }
        data[i + 1] = { date: data[i].date + rangeList[this.state.timeRange].value, count: 0 }
        i++
      }
      for (let y = 0; y < data.length; y++) {
        for (let j = 0; j < sortedList.length; j++) {
          if (data[y + 1] !== undefined &&
            data[y].date <= (new Date(sortedList[j].sentSimulationTime).getTime()) &&
            (new Date(sortedList[j].sentSimulationTime).getTime()) <= data[y + 1].date) {
            data[y].count++
          } else if (data[y + 1] === undefined &&
            data[y].date <= (new Date(sortedList[j].sentSimulationTime).getTime())) {
            data[y].count++
          }
        }
      }
      data.map((obj) => (
        chartData.push({ date: moment(obj.date).format('DD/MM/YYYY HH:mm'), count: obj.count })
      ))
      this.setState({ chartData: chartData })
    } else {
      this.setState({ chartData: [] })
    }
  }

  handleChangeRange (event, value) {
    let change = { ...this.state }
    change['timeRange'] = value
    this.setState(change, () => this.handleChangeChart(this.state.changeDataTable))
  }

  handleChangeDropDown (stateName, event, index, value) {
    let change = {}
    change[stateName] = value
    if (change.roleValue === null && change.userValue === null) {
      change['errorUserRole'] = false
    } else {
      change['errorUserRole'] = true
    }
    this.setState(change)
  }

  handleChangeTextField (name, e) {
    let change = { ...this.state }
    change[name] = e.target.value
    if (name === 'title') {
      if (e.target.value === '') {
        change['titleErrorText'] = 'Please, enter your title'
      } else {
        change['titleErrorText'] = ''
      }
    } else if (name === 'messageValue') {
      if (e.target.value === '') {
        change['messageValueErrorText'] = 'Please, enter your message'
      } else {
        change['messageValueErrorText'] = ''
      }
    }
    this.setState(change)
  }

  checkValid () {
    let valid = true
    let change = { ...this.state }

    if (change.title === '') {
      change['titleErrorText'] = 'Please, enter your title'
      valid = false
    } else {
      change['titleErrorText'] = ''
    }

    if (change.messageValue === '') {
      change['messageValueErrorText'] = 'Please, enter your message'
      valid = false
    } else if (change.messageValue.length > 30) {
      change['messageValueErrorText'] = 'Your message is too long'
      valid = false
    } else {
      change['messageValueErrorText'] = ''
    }

    if (change.roleValue === null && change.userValue === null) {
      valid = false
      change['errorUserRole'] = false
    } else {
      valid = true
      change['errorUserRole'] = true
    }
    this.setState(change)

    return valid
  }

  handleChangeState (type, order) {
    let change = {
      ...this.state.sort
    }
    change['type'] = type
    change['order'] = order
    this.setState(
      {
        sort: change
      }
    )
  }

  sendMessage () {
    let send = {}
    send.trialSessionId = parseInt(this.props.params.id)
    if (this.state.userValue === -1) {
      send.trialUserId = null
    }
    if (this.state.roleValue === -1) {
      send.trialRoleId = null
    }
    if (this.state.userValue !== null && this.state.userValue !== -1) {
      send.trialUserId = this.state.userValue
      send.trialRoleId = ''
    }
    if (this.state.roleValue !== null && this.state.roleValue !== -1) {
      send.trialRoleId = this.state.roleValue
      send.trialUserId = ''
    }
    send.name = this.state.title
    send.languageVersion = 'POLISH'
    send.description = this.state.messageValue
    // send.eventTime = moment(new Date().getTime()).format('YYYY-MM-DDThh:mm:ss')
    if (this.checkValid()) {
      this.props.sendMessage(send)
    }
  }

  handleSort (type) {
    let change = { ...this.state.sort }
    if (this.state.sort.type !== type) {
      change.order = 'asc'
      change.type = type
    } else if (this.state.sort.type === type && this.state.sort.order === 'asc') {
      change.order = 'desc'
      change.type = type
    } else {
      change.order = 'asc'
      change.type = type
    }
    this.setState({ sort: change }, () => { this.props.getMessages(this.state.sort) })
  }

  handleChange = (value) => {
    this.setState({
      value: value
    })
  }

  handleShowModal () {
    this.setState({ showModal: !this.state.showModal })
  }

  handleSelectedObject (obj) {
    this.setState({ selectedObj: obj })
    this.props.getSchemaView(obj.id, this.props.params.id)
    this.handleShowModal()
  }

  handleChangeStatus () {
    if (this.state.trialStatus !== '') {
      this.props.setStatus(this.props.params.id, this.state.trialStatus)
    }
  }

  handleChangeStage () {
    if (this.state.trialStage !== '') {
      this.props.setStage(this.props.params.id, { id: this.state.trialStage })
    }
  }

  search () {
    this.props.getObservation(this.props.params.id, this.state.searchText)
  }

  handleDownloadSummary () {
    if (this.state.changeDataTable.length !== 0) {
      this.props.exportToCSV(this.props.params.id)
    } else {
      toastr.error('Export to CSV', `There isn't data to export!`, toastrOptions)
    }
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='admin-trials-container'>
            <div className='trials-set-header'>
              <div>Session settings</div>
            </div>
            <div style={{ marginBottom: '10px' }}>
              <SelectField
                value={this.state.trialStatus}
                floatingLabelText='Change Session Status'
                onChange={this.handleChangeDropDown.bind(this, 'trialStatus')} >
                {statusList.map((index) => (
                  <MenuItem
                    key={index.id}
                    value={index.name}
                    style={{ color: 'grey' }}
                    primaryText={index.name} />
                ))}
              </SelectField>
              <RaisedButton
                style={{ marginLeft: '20px', marginRight: '20px', marginBottom: '10px', verticalAlign: 'bottom' }}
                label='Set status'
                primary
                onClick={this.handleChangeStatus.bind(this)}
                labelStyle={{ color: '#FDB913' }} />
            </div>
            <div style={{ marginBottom: '20px' }}>
              <SelectField
                value={this.state.trialStage}
                floatingLabelText='Change Trial Stage'
                onChange={this.handleChangeDropDown.bind(this, 'trialStage')} >
                {this.state.stagesList !== undefined && this.state.stagesList.map((index) => (
                  <MenuItem
                    key={index.id}
                    value={index.id}
                    style={{ color: 'grey' }}
                    primaryText={index.name} />
                ))}
              </SelectField>
              <RaisedButton
                style={{ marginLeft: '20px', marginRight: '20px', marginBottom: '10px', verticalAlign: 'bottom' }}
                label='Set stage'
                primary
                onClick={this.handleChangeStage.bind(this)}
                labelStyle={{ color: '#FDB913' }} />
            </div>
            <Tabs
              value={this.state.value}
              onChange={this.handleChange}
              tabItemContainerStyle={{ whiteSpace: 'inherit' }}
              >
              <Tab
                style={this.state.value === 'a' ? styles.active : styles.default}
                label='Summary of observations'
                value='a'>
                <div>
                  <div className='trials-header'>
                    <div>Summary of observations
                      <RaisedButton
                        buttonStyle={{ width: 150 }}
                        style={{ marginLeft: 10 }}
                        backgroundColor='#244C7B'
                        labelColor='#FCB636'
                        label='Download'
                        secondary
                        onClick={() => this.handleDownloadSummary()} />
                    </div>
                    <DateComponent />
                  </div>
                  {false &&
                  <Card style={{ padding: '20px', margin: '20px 30px' }}>
                    <div style={styles.searchPanelContainer}>
                      <div style={{ width: 'calc(100% - 108px)' }}>
                        <div className='dropdown-title'>Search</div>
                        <TextField
                          value={this.state.searchText}
                          hintText='enter the search text'
                          fullWidth
                          onChange={this.handleChangeTextField.bind(this, 'searchText')} />
                      </div>
                      <RaisedButton
                        style={{ marginLeft: '20px' }}
                        label='Search'
                        primary
                        onClick={this.search.bind(this)}
                        labelStyle={{ color: '#FDB913' }} />
                    </div>
                  </Card>
                  }
                  <Card style={{ margin: '20px 30px' }}>
                    <Table
                      bodyStyle={{ overflowX: 'auto', overflowY: undefined }}
                      fixedFooter={false} >
                      <TableHeader
                        displaySelectAll={false}
                        adjustForCheckbox={false} >
                        <TableRow>
                          <TableHeaderColumn>
                            <div className='sort-style'>
                              <div className='sort-style-icon sort-cursor'>Time</div>
                            </div>
                          </TableHeaderColumn>
                          <TableHeaderColumn >
                      User
                    </TableHeaderColumn>
                          <TableHeaderColumn >
                      Observation Type
                    </TableHeaderColumn>
                          <TableHeaderColumn>
                      Show Details
                    </TableHeaderColumn>
                        </TableRow>
                      </TableHeader>
                      <TableBody
                        displayRowCheckbox={false} showRowHover>
                        {this.state.changeDataTable.map((row, index) => (
                          <TableRow key={index} selectable={false} style={{ whiteSpace: 'inherit' }}>
                            <TableRowColumn>
                              {moment(row.sentSimulationTime, 'YYYY-MM-DDTHH:mm Z').format('DD/MM/YYYY HH:mm')}
                            </TableRowColumn>
                            <TableRowColumn>
                              {`${row.user.firstName} ${row.user.lastName}`}
                            </TableRowColumn>
                            <TableRowColumn>
                              {row.observationTypeName}
                            </TableRowColumn>
                            <TableRowColumn>
                              <i className='cursor-pointer material-icons'
                                style={{ verticalAlign: 'middle' }}
                                onClick={() => this.handleSelectedObject(row)}>
                                open_in_new
                              </i>
                              <ReactTooltip />
                            </TableRowColumn>
                          </TableRow>
                          ))}
                      </TableBody>
                    </Table>
                  </Card>
                  {this.state.chartData.length === 0 &&
                  <Card style={{ margin: '20px 30px', padding: '20px' }}>
                    <div style={{ textAlign: 'center' }}>No chart data to show!</div>
                  </Card>
                  }
                  {this.state.chartData.length > 0 && <Card style={{ margin: '20px 30px' }}>
                    <div style={{ padding: '20px' }}>
                      <div className='dropdown-title'>select range of time</div>
                      <DropDownMenu
                        style={{ width: '220px' }}
                        disabled={this.state.isOneDay}
                        value={this.state.timeRange}
                        underlineStyle={{ marginLeft: '0' }}
                        labelStyle={{ color: '#282829', paddingLeft: '0' }}
                        onChange={this.handleChangeRange.bind(this)}>
                        {rangeList.map((index) => (
                          <MenuItem
                            key={index.id}
                            value={index.id}
                            style={{ color: 'grey' }}
                            primaryText={index.name} />
                ))}
                      </DropDownMenu>
                    </div>

                    <ResponsiveContainer width='100%' height={400}>
                      <BarChart data={this.state.chartData}
                        margin={{ top: 30, right: 30, left: 20, bottom: 60 }}>
                        <XAxis dataKey='date' hide />
                        <YAxis />
                        <CartesianGrid strokeDasharray='3 3' />
                        <Tooltip />
                        <Bar dataKey='count' fill='#00497E' background={{ fill: '#eee' }} />
                      </BarChart>
                    </ResponsiveContainer>
                  </Card>}
                </div>
              </Tab>
              <Tab
                style={this.state.value === 'b' ? styles.active : styles.default}
                label='Events / messages send to observers'
                value='b'
                >
                <div>
                  <div className='trials-header'>
                    <div>Events / messages send to observers</div>
                    <DateComponent />
                  </div>
                  <Card style={{ padding: '20px', margin: '20px 30px' }}>
                    <div style={styles.searchPanelContainer}>
                      <div>
                        <div className='dropdown-title'>Select User</div>
                        <DropDownMenu
                          style={{ width: '220px' }}
                          value={this.state.userValue}
                          underlineStyle={!this.state.errorUserRole
                            ? { borderTop: '1px solid red', marginLeft: '0' }
                            : { borderTop: '1px solid rgb(224, 224, 224)', marginLeft: '0' }}
                          labelStyle={{ color: '#282829', paddingLeft: '0' }}
                          disabled={this.state.roleValue !== null}
                          onChange={this.handleChangeDropDown.bind(this, 'userValue')}>
                          <MenuItem
                            value={null}
                            style={{ color: 'grey' }}
                            primaryText={' '} />
                          <MenuItem
                            value={-1}
                            style={{ color: 'grey' }}
                            primaryText={'All'} />
                          {this.state.usersList !== undefined && this.state.usersList.map((index) => (
                            <MenuItem
                              key={index.id}
                              value={index.id}
                              style={{ color: 'grey' }}
                              primaryText={index.firstName + ' ' + index.lastName} />
                ))}
                        </DropDownMenu>
                      </div>
                      <div>
                        <div className='dropdown-title'>Select Role</div>
                        <DropDownMenu
                          style={{ width: '220px' }}
                          value={this.state.roleValue}
                          underlineStyle={!this.state.errorUserRole
                            ? { borderTop: '1px solid red', marginLeft: '0' }
                            : { borderTop: '1px solid rgb(224, 224, 224)', marginLeft: '0' }}
                          labelStyle={{ color: '#282829', paddingLeft: '0' }}
                          disabled={this.state.userValue !== null}
                          onChange={this.handleChangeDropDown.bind(this, 'roleValue')}>
                          <MenuItem
                            value={null}
                            style={{ color: 'grey' }}
                            primaryText={' '} />
                          <MenuItem
                            value={-1}
                            style={{ color: 'grey' }}
                            primaryText={'All'} />
                          {this.state.rolesList !== undefined && this.state.rolesList.map((index) => (
                            <MenuItem
                              key={index.id}
                              value={index.id}
                              style={{ color: 'grey' }}
                              primaryText={index.name} />
                ))}
                        </DropDownMenu>
                      </div>
                      <div style={{ marginRight: '20px' }}>
                        <div className='dropdown-title'>Title</div>
                        <TextField
                          style={{ width: '250px' }}
                          value={this.state.title}
                          hintText='enter the title'
                          errorText={this.state.titleErrorText}
                          onChange={this.handleChangeTextField.bind(this, 'title')}
                  />
                      </div>
                      <div>
                        <div className='dropdown-title'>Message</div>
                        <TextField
                          style={{ width: '300px' }}
                          multiLine
                          value={this.state.messageValue}
                          hintText='enter the message'
                          errorText={this.state.messageValueErrorText}
                          onChange={this.handleChangeTextField.bind(this, 'messageValue')}
                  />
                      </div>
                      <RaisedButton
                        style={{ marginLeft: '20px', marginRight: '20px' }}
                        label='Send'
                        primary
                        labelStyle={{ color: '#FDB913' }}
                        onClick={this.sendMessage.bind(this)} />
                    </div>
                  </Card>
                  <Card style={{ margin: '20px 30px' }}>
                    <Table
                      bodyStyle={{ overflowX: undefined, overflowY: undefined }}
                      fixedFooter={false} >
                      <TableHeader
                        displaySelectAll={false}
                        adjustForCheckbox={false} >
                        <TableRow>
                          <TableHeaderColumn onTouchTap={this.handleSort.bind(this, 'eventTime')}>
                            <div className='sort-style'>
                              <div className='sort-style-icon sort-cursor'>Time</div>
                              {this.state.sort.type === 'eventTime' && <div className='sort-cursor'>
                                {
                                this.state.sort.order === 'asc' ? <i className='material-icons'>keyboard_arrow_up</i>
                      : <i className='material-icons'>keyboard_arrow_down</i>}
                              </div>
                              }
                            </div>
                          </TableHeaderColumn>
                          <TableHeaderColumn onTouchTap={this.handleSort.bind(this, 'trialRoleName')}>
                            <div className='sort-style'>
                              <div className='sort-style-icon sort-cursor'>User</div>
                              {this.state.sort.type === 'trialRoleName' &&
                              <div className='sort-cursor'>
                                {
                                this.state.sort.order === 'asc' ? <i className='material-icons'>keyboard_arrow_up</i>
                      : <i className='material-icons'>keyboard_arrow_down</i>}
                              </div>
                             }
                            </div>
                          </TableHeaderColumn>
                          <TableHeaderColumn onTouchTap={this.handleSort.bind(this, 'trialRoleName')}>
                            <div className='sort-style'>
                              <div className='sort-style-icon sort-cursor'>Role</div>
                              {this.state.sort.type === 'trialRoleName' &&
                              <div className='sort-cursor'>
                                {
                                this.state.sort.order === 'asc' ? <i className='material-icons'>keyboard_arrow_up</i>
                      : <i className='material-icons'>keyboard_arrow_down</i>}
                              </div>
                             }
                            </div>
                          </TableHeaderColumn>
                          <TableHeaderColumn onTouchTap={this.handleSort.bind(this, 'name')}>
                            <div className='sort-style'>
                              <div className='sort-style-icon sort-cursor'>Name</div>
                              {this.state.sort.type === 'name' &&
                              <div className='sort-cursor'>
                                {
                                this.state.sort.order === 'asc' ? <i className='material-icons'>keyboard_arrow_up</i>
                      : <i className='material-icons'>keyboard_arrow_down</i>}
                              </div>
                             }
                            </div>
                          </TableHeaderColumn>
                          <TableHeaderColumn onTouchTap={this.handleSort.bind(this, 'description')}>
                            <div className='sort-style'>
                              <div className='sort-style-icon sort-cursor'>Description</div>
                              {this.state.sort.type === 'description' &&
                              <div className='sort-cursor'>
                                {
                                this.state.sort.order === 'asc' ? <i className='material-icons'>keyboard_arrow_up</i>
                      : <i className='material-icons'>keyboard_arrow_down</i>}
                              </div>
                             }
                            </div>
                          </TableHeaderColumn>
                        </TableRow>
                      </TableHeader>
                      <TableBody displayRowCheckbox={false} showRowHover>
                        {this.state.messages.map((row, index) => (
                          <TableRow key={index} selectable={false}>
                            <TableRowColumn>
                              {moment(row.eventTime, 'YYYY-MM-DDTHH:mm').format('DD/MM/YYYY hh:mm')}
                            </TableRowColumn>
                            <TableRowColumn>
                              {row.trialUserId !== null &&
                                (row.firstName + ' ' + (row.lastName ? row.lastName : '')) }
                              {row.trialUserId === null && row.trialRoleId === null && 'All' }
                              {row.trialUserId === null && row.trialRoleId !== null && 'N/A'}
                            </TableRowColumn>
                            <TableRowColumn>
                              {row.trialRoleId !== null && row.trialRoleName}
                              {row.trialUserId === null && row.trialRoleId === null && 'All'}
                              {row.trialUserId !== null && row.trialRoleId === null && 'N/A'}
                            </TableRowColumn>
                            <TableRowColumn>
                              {row.name}
                            </TableRowColumn>
                            <TableRowColumn>
                              {row.description}
                            </TableRowColumn>
                          </TableRow>
                ))}
                      </TableBody>
                    </Table>
                  </Card>
                </div>
              </Tab>
            </Tabs>
            <SummaryOfObservationModal
              mode={'adminmodal'}
              show={this.state.showModal}
              object={this.state.selectedObj}
              handleShowModal={this.handleShowModal.bind(this)}
              observationForm={this.props.observationForm}
              params={this.props.params.id}
              downloadFile={this.props.downloadFile} />
          </div>
        </div>
      </div>
    )
  }
}

export default AdminTrials
