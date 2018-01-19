import React, { Component } from 'react'
import moment from 'moment'
import { Table, TableBody,
  TableHeader, TableHeaderColumn,
  TableRow, TableRowColumn } from 'material-ui/Table'
import { Card } from 'material-ui/Card'
import DropDownMenu from 'material-ui/DropDownMenu'
import MenuItem from 'material-ui/MenuItem'
import TextField from 'material-ui/TextField'
import RaisedButton from 'material-ui/RaisedButton'
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'Recharts'
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

const data = [
  { name: 'Observation one', uv: 4000, answer: 240, date: '19/01/2018' },
  { name: 'Observation two', uv: 3000, answer: 139, date: '20/01/2018' },
  { name: 'Observation three', uv: 2000, answer: 980, date: '21/01/2018' },
  { name: 'Observation four', uv: 2780, answer: 390, date: '22/01/2018' },
  { name: 'Observation five', uv: 1890, answer: 480, date: '23/01/2018' },
  { name: 'Observation six', uv: 2390, answer: 380, date: '24/01/2018' },
  { name: 'Observation seven', uv: 3490, answer: 430, date: '25/01/2018' }
]

const styles = {
  dropDownContainer: {
    width: '100%',
    justifyContent: 'center',
    display: 'flex'
  }
}

class Trials extends Component {
  constructor (props) {
    super(props)
    this.state = {
      time: moment(new Date().getTime()).format('DD/MM/YYYY h:mm:ss'),
      userValue: 0,
      roleValue: 0,
      messageValue: ''
    }
  }

  static propTypes = { }

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

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='trials-container'>
            <div className='trials-header'>
              <div>Summary of observations</div>
              <DateComponent />
            </div>
            <Card style={{ margin: '20px 30px' }}>
              <Table
                bodyStyle={{ overflowX: undefined, overflowY: undefined }}
                fixedFooter={false} >
                <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
                  <TableRow>
                    <TableHeaderColumn >
                      Time
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
                  {tableOne.map((row, index) => (
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
            <div style={styles.dropDownContainer}>
              <ResponsiveContainer width='60%' height={400}>
                <BarChart data={data}
                  margin={{ top: 30, right: 30, left: 20, bottom: 5 }}>
                  <XAxis dataKey='date' />
                  <YAxis />
                  <CartesianGrid strokeDasharray='3 3' />
                  <Tooltip />
                  <Bar dataKey='answer' fill='#00497E' background={{ fill: '#eee' }} />
                </BarChart>
              </ResponsiveContainer>
            </div>
            <div className='trials-title'>
              <div>Events / messages send to observers</div>
            </div>
            <Card style={{ padding: '20px', margin: '20px 30px' }}>
              <div style={{ display: 'flex', justifyContent: 'flex-start', alignItems: 'center' }}>
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
