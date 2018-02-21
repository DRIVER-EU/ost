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
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts'
import DateComponent from '../../DateComponent/DateComponent'
import './Trials.scss'

const tableOne = [
  { time: '10:00:10',
    user: 'Mike001',
    role: 'Team 1',
    type: 'Behaviour of team 1',
    who: 'participant 1',
    what: '40%',
    attachment: 'attachment 1' },
  { time: '10:01:20',
    user: 'John001',
    role: 'Team 1',
    type: 'Behaviour of team 1',
    who: 'participant 1',
    what: '80%',
    attachment: 'attachment 2' },
  { time: '10:05:20',
    user: 'Mary11',
    role: 'Team 2',
    type: 'Observation ZZ',
    who: 'participant 2',
    what: 'option 1, option2',
    attachment: 'attachment 3' }
]

const tableThree = [
  { time: '10:05:20',
    user: 'Mary11',
    role: 'Team 2',
    type: 'Observation ZZ',
    who: 'participant 2',
    what: 'option 1, option2',
    attachment: 'attachment 3' },
  { time: '10:01:20',
    user: 'John001',
    role: 'Team 1',
    type: 'Behaviour of team 1',
    who: 'participant 1',
    what: '80%',
    attachment: 'attachment 2' },
  { time: '10:00:10',
    user: 'Mike001',
    role: 'Team 1',
    type: 'Behaviour of team 1',
    who: 'participant 1',
    what: '40%',
    attachment: 'attachment 1' }
]

const tableTwo = [
  { time: '10:00:10', user: 'All', role: 'All', message: 'Trial started' },
  { time: '10:01:20', user: 'John001', role: 'Team 1', message: 'Focus on Participant 1' },
  { time: '10:05:20', user: 'All', role: 'Team 2', message: 'Some message' }
]

const userList = [
  { id: 0, name: 'All' },
  { id: 1, name: 'User 1' },
  { id: 2, name: 'user 2' },
  { id: 3, name: 'user 3' }
]

const roleList = [
  { id: 0, name: 'All' },
  { id: 1, name: 'Role 1' },
  { id: 2, name: 'Role 2' },
  { id: 3, name: 'Role 3' }
]

const rangeList = [
  { id: 0, name: '15 minutes' },
  { id: 1, name: '30 minutes' },
  { id: 2, name: '1 hour' },
  { id: 3, name: '1 day' },
  { id: 4, name: '7 days' },
  { id: 5, name: '1 month' }
]

const dataOne = [
  { name: 'Observation 1', answer: 240, date: '01/01/2018' },
  { name: 'Observation 2', answer: 139, date: '02/01/2018' },
  { name: 'Observation 3', answer: 980, date: '03/01/2018' },
  { name: 'Observation 4', answer: 390, date: '04/01/2018' },
  { name: 'Observation 5', answer: 480, date: '05/01/2018' },
  { name: 'Observation 6', answer: 380, date: '06/01/2018' },
  { name: 'Observation 7', answer: 230, date: '07/01/2018' },
  { name: 'Observation 8', answer: 430, date: '08/01/2018' },
  { name: 'Observation 9', answer: 11, date: '09/01/2018' },
  { name: 'Observation 10', answer: 430, date: '10/01/2018' },
  { name: 'Observation 11', answer: 180, date: '11/01/2018' },
  { name: 'Observation 12', answer: 100, date: '12/01/2018' },
  { name: 'Observation 13', answer: 200, date: '13/01/2018' },
  { name: 'Observation 14', answer: 145, date: '14/01/2018' },
  { name: 'Observation 15', answer: 13, date: '15/01/2018' },
  { name: 'Observation 16', answer: 888, date: '16/01/2018' },
  { name: 'Observation 17', answer: 321, date: '17/01/2018' },
  { name: 'Observation 18', answer: 431, date: '18/01/2018' },
  { name: 'Observation 19', answer: 0, date: '19/01/2018' },
  { name: 'Observation 20', answer: 512, date: '20/01/2018' },
  { name: 'Observation 21', answer: 199, date: '21/01/2018' },
  { name: 'Observation 22', answer: 50, date: '22/01/2018' },
  { name: 'Observation 23', answer: 20, date: '23/01/2018' },
  { name: 'Observation 24', answer: 210, date: '24/01/2018' },
  { name: 'Observation 25', answer: 412, date: '25/01/2018' },
  { name: 'Observation 26', answer: 150, date: '26/01/2018' },
  { name: 'Observation 27', answer: 80, date: '27/01/2018' },
  { name: 'Observation 28', answer: 156, date: '28/01/2018' },
  { name: 'Observation 29', answer: 670, date: '29/01/2018' },
  { name: 'Observation 30', answer: 309, date: '30/01/2018' },
  { name: 'Observation 31', answer: 724, date: '31/01/2018' }
]

const dataTwo = [
  { name: 'Observation 1', answer: 240, date: '10:00:00' },
  { name: 'Observation 2', answer: 139, date: '10:15:00' },
  { name: 'Observation 3', answer: 980, date: '10:30:00' },
  { name: 'Observation 4', answer: 390, date: '10:45:00' },
  { name: 'Observation 5', answer: 480, date: '11:00:00' },
  { name: 'Observation 6', answer: 380, date: '11:15:00' },
  { name: 'Observation 7', answer: 230, date: '11:30:00' },
  { name: 'Observation 8', answer: 430, date: '11:45:00' },
  { name: 'Observation 9', answer: 11, date: '12:00:00' },
  { name: 'Observation 10', answer: 430, date: '12:15:00' },
  { name: 'Observation 11', answer: 180, date: '12:30:00' },
  { name: 'Observation 12', answer: 100, date: '12:45:00' },
  { name: 'Observation 13', answer: 300, date: '13:00:00' }
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

class Trials extends Component {
  constructor (props) {
    super(props)
    this.state = {
      time: moment(new Date().getTime()).format('DD/MM/YYYY h:mm:ss'),
      userValue: 0,
      roleValue: 0,
      messageValue: '',
      whatValue: '',
      whoValue: '',
      observationValue: '',
      sortObservation: true,
      sourceValue: '',
      changeDataTable: tableOne,
      timeRange: 5
    }
  }

  static propTypes = {
    getObservation: PropTypes.func,
    observation: PropTypes.array
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.observation) {
      console.log(nextProps)
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

  sortFunction () {
    this.setState({
      sortObservation: !this.state.sortObservation,
      changeDataTable: tableThree
    })
    if (this.state.changeDataTable === tableThree) {
      this.setState({ changeDataTable: tableOne })
    }
  }

  getData () {
    if (this.state.timeRange === 0) {
      return dataTwo
    }
    return dataOne
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
                  {this.state.changeDataTable.map((row, index) => (
                    <TableRow key={index} selectable={false}>
                      <TableRowColumn >
                        {row.time}
                      </TableRowColumn>
                      <TableRowColumn >
                        {row.user}
                      </TableRowColumn>
                      <TableRowColumn>
                        {row.role}
                      </TableRowColumn>
                      <TableRowColumn >
                        {row.type}
                      </TableRowColumn>
                      <TableRowColumn >
                        {row.who}
                      </TableRowColumn>
                      <TableRowColumn >
                        {row.what}
                      </TableRowColumn>
                      <TableRowColumn>
                        {row.attachment}
                      </TableRowColumn>
                    </TableRow>
                ))}
                </TableBody>
              </Table>
            </Card>
            <Card style={{ margin: '20px 30px' }}>
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
                <BarChart data={this.getData()}
                  margin={{ top: 30, right: 30, left: 20, bottom: 5 }}>
                  <XAxis dataKey='date' />
                  <YAxis />
                  <CartesianGrid strokeDasharray='3 3' />
                  <Tooltip />
                  <Bar dataKey='answer' fill='#00497E' background={{ fill: '#eee' }} />
                </BarChart>
              </ResponsiveContainer>
            </Card>
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
                  labelStyle={{ color: '#FDB913' }} />
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
                  {tableTwo.map((row, index) => (
                    <TableRow key={index} selectable={false}>
                      <TableRowColumn>
                        {row.time}
                      </TableRowColumn>
                      <TableRowColumn>
                        {row.user}
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

export default Trials
