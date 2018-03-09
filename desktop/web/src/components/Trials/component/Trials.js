import React, { Component } from 'react'
import PropTypes from 'prop-types'
import DateComponent from '../../DateComponent/DateComponent'
import './Trials.scss'
import { Accordion, AccordionItem } from 'react-sanfona'
import RaisedButton from 'material-ui/RaisedButton'
import { browserHistory } from 'react-router'

class Trials extends Component {
  constructor (props) {
    super(props)
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
      dataTable: [],
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
    getTrials: PropTypes.func,
    listOfTrials: PropTypes.array
  }

  componentWillMount () {
    this.props.getTrials()
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.listOfTrials.data &&
      nextProps.listOfTrials.data !== this.state.listOfTrials &&
      nextProps.listOfTrials.data !== this.props.listOfTrials) {
      this.setState({ listOfTrials: nextProps.listOfTrials.data })
    }
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
      observations: order,
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
              <div>Select trial</div>
              <DateComponent />
            </div>
            <Accordion>
              {this.state.listOfTrials.map((object) => {
                return (
                  <AccordionItem title={object.title} expanded={false}>
                    <div>
                      <p>{object.description}</p>
                      <div style={{ display: 'table', margin: '0 auto' }}>
                        <RaisedButton
                          buttonStyle={{ width: '200px' }}
                          backgroundColor='#244C7B'
                          labelColor='#FCB636'
                          onClick={this.viewTrial.bind(this, object.id)}
                          label='Ok' />
                      </div>
                    </div>
                  </AccordionItem>
                )
              })}
            </Accordion>
          </div>
        </div>
      </div>
    )
  }
}

export default Trials
