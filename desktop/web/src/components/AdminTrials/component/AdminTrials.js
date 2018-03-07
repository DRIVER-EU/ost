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
import RaisedButton from 'material-ui/RaisedButton'
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, LabelList } from 'recharts'
import DateComponent from '../../DateComponent/DateComponent'
import ReactTooltip from 'react-tooltip'
import './AdminTrials.scss'
import _ from 'lodash'

const userList = [
  { id: 0, name: 'All' },
  { id: 1, name: 'Test User 1' },
  { id: 2, name: 'Test User 2' }
]

const roleList = [
  { id: 0, name: 'All' },
  { id: 1, name: 'Team 1' }
]

const rangeList = [
  { id: 0, name: '15 minutes' },
  { id: 1, name: '30 minutes' },
  { id: 2, name: '1 hour' },
  { id: 3, name: '1 day' },
  { id: 4, name: '7 days' },
  { id: 5, name: '1 month' }
]

const styles = {
  searchPanelContainer: {
    width: '100%',
    justifyContent: 'flex-start',
    display: 'flex',
    alignItems: 'center',
    flexWrap: 'wrap'
  }
}

class AdminTrials extends Component {
  constructor (props) {
    super(props)
    const t = moment(new Date().getTime()).format('DD/MM/YYYY hh:mm')
    this.state = {
      time: t,
      userValue: 0,
      roleValue: 0,
      messageValue: '',
      whatValue: '',
      whoValue: '',
      observationValue: '',
      sortObservation: true,
      sourceValue: '',
      changeDataTable: [],
      sort: { type: 'dateTime', order: 'asc' },
      observations: [],
      chartData: [],
      changeDataTableSorted: [],
      messages: [],
      messageTime: ''
    }
  }

  static propTypes = {
    getMessages: PropTypes.func,
    messages: PropTypes.array,
    isSendMessage: PropTypes.any,
    sendMessage: PropTypes.func,
    getObservation: PropTypes.func,
    observation: PropTypes.array
  }

  componentWillMount () {
    this.props.getMessages()
    this.props.getObservation()

    setInterval(function () {
      this.props.getMessages()
      this.props.getObservation()
    }.bind(this), 3000)
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
    if (nextProps.observation && nextProps.observation.length !== this.state.changeDataTable.length) {
      let change = nextProps.observation
      this.setState({ changeDataTable: change }, () => {
        this.sortFunction()
        this.getData()
      })
    }
    if (nextProps.messages &&
      nextProps.messages.length !== this.state.messages.length) {
      let change = nextProps.messages
      change = this.getMessagesSorted(change)
      this.setState({ messages:  change })
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

  handleChangeDropDown (stateName, event, index, value) {
    let change = {}
    change[stateName] = value
    this.setState(change)
  }

  handleChangeTextField (name, e) {
    let change = {}
    change[name] = e.target.value
    this.setState(change)
  }

  getMessagesSorted (messages) {
    for (let i = 0; i < messages.length; i++) {
      messages[i].dateTime = moment(messages[i].dateTime, 'DD/MM/YYYY hh:mm').unix()
    }

    let order = _.orderBy(messages, ['dateTime'], ['desc'])
    for (let i = 0; i < order.length; i++) {
      order[i].dateTime = moment.unix(order[i].dateTime).format('DD/MM/YYYY hh:mm')
    }
    return order
  }

  sortFunction () {
    let observations = [ ...this.state.changeDataTable ]
    let sort = { ...this.state.sort }
    let orderBy = ''
    for (let i = 0; i < observations.length; i++) {
      observations[i].dateTime = moment(observations[i].dateTime, 'DD/MM/YYYY hh:mm').unix()
    }
    if (this.state.sort.order === 'asc') {
      orderBy = 'desc'
    } else {
      orderBy = 'asc'
    }
    let order = _.orderBy(observations, ['dateTime'], [orderBy])
    for (let i = 0; i < order.length; i++) {
      order[i].dateTime = moment.unix(order[i].dateTime).format('DD/MM/YYYY hh:mm')
    }

    sort['order'] = orderBy
    this.setState({
      sortObservation: !this.state.sortObservation,
      changeDataTableSorted: order,
      sort: sort
    })
  }

  getSelectedUser () {
    if (this.state.userValue === 0) {
      return 'All'
    } else if (this.state.userValue === 1) {
      return 'Test User 1'
    } else if (this.state.userValue === 2) {
      return 'Test User 2'
    }
  }
  getSelectedRole () {
    if (this.state.roleValue === 0) {
      return 'All'
    } else if (this.state.roleValue === 1) {
      return 'Team 1'
    }
  }

  sendMessage () {
    this.props.sendMessage({
      selectUser: this.getSelectedUser(),
      role: this.getSelectedRole(),
      message: this.state.messageValue,
      time: moment(new Date().getTime()).format('DD/MM/YYYY hh:mm')
    })
  }

  getData () {
    let observations = [ ...this.state.changeDataTable ]
    for (let i = 0; i < observations.length; i++) {
      observations[i].dateTime = moment(observations[i].dateTime, 'DD/MM/YYYY hh:mm').unix()
    }

    let order = _.orderBy(observations, ['dateTime'], ['asc'])
    for (let i = 0; i < order.length; i++) {
      order[i].dateTime = moment.unix(order[i].dateTime).format('DD/MM/YYYY hh:mm')
    }

    let data = []
    if (order.length) {
      let range = moment(order[0].dateTime, 'DD/MM/YYYY hh:mm').unix() * 1000 + 60 * 5 * 1000

      data.push({
        name: order[0]['what'],
        answers: 1,
        date: moment(order[0].dateTime, 'DD/MM/YYYY hh:mm').format('DD/MM/YYYY hh:mm')
      })
      for (let i = 1; i < order.length; i++) {
        if (order[i].dateTime === 'Invalid date') {
          order[i].dateTime = moment(new Date().getTime()).format('DD/MM/YYYY hh:mm')
        }
        if (moment(order[i].dateTime, 'DD/MM/YYYY hh:mm').unix() * 1000 < range) {
          data[data.length - 1].answers++
        } else {
          range = moment(order[i].dateTime, 'DD/MM/YYYY hh:mm').unix() * 1000 + 60 * 5 * 1000
          data.push({
            name: order[i]['what'],
            answers: 1,
            date: order[i].dateTime
          })
        }
      }
    }

    this.setState({ chartData: data })
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='trials-container'>
            <div className='trials-header'>
              <div>Summary of observations</div>
              <DateComponent />
            </div>

            <Card style={{ padding: '20px', margin: '20px 30px' }}>
              <div style={styles.searchPanelContainer}>
                <div style={{ marginRight: '20px' }}>
                  <div className='dropdown-title'>Source</div>
                  <TextField
                    style={{ width: '250px' }}
                    value={this.state.sourceValue}
                    hintText='enter the message'
                    onChange={this.handleChangeTextField.bind(this, 'sourceValue')}
                 />
                </div>
                <div style={{ marginRight: '20px' }}>
                  <div className='dropdown-title'>Observation type</div>
                  <TextField
                    style={{ width: '250px' }}
                    value={this.state.observationValue}
                    hintText='enter the message'
                    onChange={this.handleChangeTextField.bind(this, 'observationValue')}
                 />
                </div>
                <div style={{ marginRight: '20px' }}>
                  <div className='dropdown-title'>Who</div>
                  <TextField
                    style={{ width: '250px' }}
                    value={this.state.whoValue}
                    hintText='enter the message'
                    onChange={this.handleChangeTextField.bind(this, 'whoValue')}
                 />
                </div>
                <div>
                  <div className='dropdown-title'>What</div>
                  <TextField
                    style={{ width: '250px' }}
                    value={this.state.whatValue}
                    hintText='enter the message'
                    onChange={this.handleChangeTextField.bind(this, 'whatValue')}
                 />
                </div>
                <RaisedButton
                  style={{ marginLeft: '20px', marginRight: '20px' }}
                  label='Search'
                  primary
                  labelStyle={{ color: '#FDB913' }} />
              </div>
            </Card>

            <Card style={{ margin: '20px 30px' }}>
              <Table
                bodyStyle={{ overflowX: undefined, overflowY: undefined }}
                fixedFooter={false} >
                <TableHeader
                  displaySelectAll={false}
                  adjustForCheckbox={false}
                >
                  <TableRow>
                    <TableHeaderColumn onTouchTap={() => this.sortFunction()}>
                      <div className='sort-style'>
                        <div className='sort-style-icon sort-cursor'>Time</div>
                        <div className='sort-cursor'>
                          {this.state.sortObservation ? <i className='material-icons'>keyboard_arrow_up</i>
                      : <i className='material-icons'>keyboard_arrow_down</i>}
                        </div>
                      </div>
                    </TableHeaderColumn>
                    <TableHeaderColumn >
                      User
                    </TableHeaderColumn>
                    <TableHeaderColumn >
                      Role
                    </TableHeaderColumn>
                    <TableHeaderColumn >
                      Observation Type
                    </TableHeaderColumn>
                    <TableHeaderColumn >
                      Who
                    </TableHeaderColumn>
                    <TableHeaderColumn >
                      What
                    </TableHeaderColumn>
                    <TableHeaderColumn>
                      Attachment
                    </TableHeaderColumn>
                  </TableRow>
                </TableHeader>
                <TableBody displayRowCheckbox={false} showRowHover>
                  {this.state.changeDataTableSorted.map((row, index) => (
                    <TableRow key={index} selectable={false}>
                      <TableRowColumn >
                        {row.dateTime}
                      </TableRowColumn>
                      <TableRowColumn >
                        {row.selectUser}
                      </TableRowColumn>
                      <TableRowColumn>
                        {row.role}
                      </TableRowColumn>
                      <TableRowColumn >
                        {row.observationType}
                      </TableRowColumn>
                      <TableRowColumn >
                        {row.who}
                      </TableRowColumn>
                      <TableRowColumn >
                        {row.what}
                      </TableRowColumn>
                      <TableRowColumn>
                        <p data-tip={row.attachment}>
                          <i className='material-icons'>announcement</i>
                        </p>
                        <ReactTooltip />
                      </TableRowColumn>
                    </TableRow>
                ))}
                </TableBody>
              </Table>
            </Card>
            {this.state.chartData.length > 0 && <Card style={{ margin: '20px 30px' }}>
              <div style={{ padding: '20px' }}>
                <div className='dropdown-title'>select range of time</div>
                <DropDownMenu
                  style={{ width: '220px' }}
                  value={this.state.timeRange}
                  underlineStyle={{ marginLeft: '0' }}
                  labelStyle={{ color: '#282829', paddingLeft: '0' }}
                  onChange={this.handleChangeDropDown.bind(this, 'timeRange')}>
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
                  <XAxis dataKey='name' hide='true' />
                  <YAxis />
                  <CartesianGrid strokeDasharray='3 3' />
                  <Tooltip />
                  <Bar dataKey='answers' fill='#00497E' background={{ fill: '#eee' }} >
                    <LabelList dataKey='date' position='bottom' offset='10' />
                  </Bar>
                </BarChart>
              </ResponsiveContainer>

            </Card>}
            <div className='trials-title'>
              <div>Events / messages send to observers</div>
            </div>
            <Card style={{ padding: '20px', margin: '20px 30px' }}>
              <div style={styles.searchPanelContainer}>
                <div>
                  <div className='dropdown-title'>Select User</div>
                  <DropDownMenu
                    style={{ width: '220px' }}
                    value={this.state.userValue}
                    underlineStyle={{ marginLeft: '0' }}
                    labelStyle={{ color: '#282829', paddingLeft: '0' }}
                    onChange={this.handleChangeDropDown.bind(this, 'userValue')}>
                    {userList.map((index) => (
                      <MenuItem
                        key={index.id}
                        value={index.id}
                        style={{ color: 'grey' }}
                        primaryText={index.name} />
                ))}
                  </DropDownMenu>
                </div>
                <div>
                  <div className='dropdown-title'>Select Role</div>
                  <DropDownMenu
                    style={{ width: '220px' }}
                    value={this.state.roleValue}
                    underlineStyle={{ marginLeft: '0' }}
                    labelStyle={{ color: '#282829', paddingLeft: '0' }}
                    onChange={this.handleChangeDropDown.bind(this, 'roleValue')}>
                    {roleList.map((index) => (
                      <MenuItem
                        key={index.id}
                        value={index.id}
                        style={{ color: 'grey' }}
                        primaryText={index.name} />
                ))}
                  </DropDownMenu>
                </div>
                <div>
                  <div className='dropdown-title'>Message</div>
                  <TextField
                    style={{ width: '300px' }}
                    value={this.state.messageValue}
                    hintText='enter the message'
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
                <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
                  <TableRow>
                    <TableHeaderColumn>
                      Time
                    </TableHeaderColumn>
                    <TableHeaderColumn>
                      User
                    </TableHeaderColumn>
                    <TableHeaderColumn>
                      Role
                    </TableHeaderColumn>
                    <TableHeaderColumn>
                      Message
                    </TableHeaderColumn>
                  </TableRow>
                </TableHeader>
                <TableBody displayRowCheckbox={false} showRowHover>
                  {this.state.messages.map((row, index) => (
                    <TableRow key={index} selectable={false}>
                      <TableRowColumn>
                        {row.dateTime}
                      </TableRowColumn>
                      <TableRowColumn>
                        {row.selectUser}
                      </TableRowColumn>
                      <TableRowColumn>
                        {row.role}
                      </TableRowColumn>
                      <TableRowColumn>
                        {row.message}
                      </TableRowColumn>
                    </TableRow>
                ))}
                </TableBody>
              </Table>
            </Card>
          </div>
        </div>
      </div>
    )
  }
}

export default AdminTrials
