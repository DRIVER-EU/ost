import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './SelectObservation.scss'

import { RaisedButton, FontIcon } from 'material-ui'
import { List, ListItem } from 'material-ui/List'
import _ from 'lodash'
import { toastr } from 'react-redux-toastr'
import Spinner from 'react-spinkit'

const toastrOptions = {
  timeOut: 3000
}

class SelectObservation extends Component {
  constructor (props) {
    super(props)
    this.interval
    this.state = {
      listOfObservations: null,
      viewTrials: null,
      isLoading: true
    }
  }

  static propTypes = {
    getObservations: PropTypes.func,
    listOfObservations: PropTypes.any,
    params: PropTypes.any,
    getViewTrials: PropTypes.func,
    viewTrials: PropTypes.array,
    clearTrialList: PropTypes.func
  }

  componentWillMount () {
    this.props.getObservations(this.props.params.id)
    this.interval = setInterval(() => {
      this.props.getViewTrials(this.props.params.id)
    }, 3000)
  }

  componentWillUnmount () {
    this.props.clearTrialList()
    clearInterval(this.interval)
  }

  componentWillReceiveProps (nextProps) {
    let listOfObsevationProps = [...nextProps.listOfObservations]
    let listOfObsevation = this.state.listOfObservations ? [...this.state.listOfObservations] : []
    if (!_.isEqual(nextProps.listOfObservations, this.state.listOfObservations) &&
      !_.isEqual(listOfObsevation.sort(), listOfObsevationProps.sort())) {
      this.setState({ listOfObservations: nextProps.listOfObservations })
    }
    if (!_.isEqual(nextProps.viewTrials, this.state.viewTrials)) {
      let newItem = _.differenceWith(nextProps.viewTrials, this.state.viewTrials, _.isEqual)
      if (this.state.viewTrials && this.state.viewTrials.length !== 0 &&
        newItem.length !== 0 && newItem[0].type === 'EVENT') {
        toastr.success('New Event', 'New Event received.', toastrOptions)
      }
      this.setState({ viewTrials: nextProps.viewTrials })
    }
    if (this.props.viewTrials && this.props.listOfObservations) {
      // eslint
    }
    if (this.state.viewTrials && this.state.listOfObservations) {
      this.setState({ isLoading: false })
    }
  }

  checkAnswers (answersList) {
    const { viewTrials } = this.state
    let isCheck = false
    let listOfIds = []
    if (answersList.length !== 0 && viewTrials.length !== 0) {
      if (viewTrials.length !== 0) {
        viewTrials.map((obj) => {
          listOfIds.push(obj.id)
        })
      }
      if (listOfIds && listOfIds.length !== 0 && answersList && answersList.length !== 0) {
        for (let i = 0; i < answersList.length; i++) {
          for (let j = 0; j < listOfIds.length; j++) {
            if (listOfIds[j] === answersList[i]) {
              isCheck = true
            }
          }
        }
      }
    }
    return isCheck
  }

  newObservation (id) {
    window.location = `/#/trials/${this.props.params.id}/new-observation/${id}`
  }

  back () {
    window.location = `/#/trials/${this.props.params.id}`
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='view-trials-observation-container'>
            <RaisedButton
              style={{ float: 'right' }}
              buttonStyle={{ width: '240px' }}
              backgroundColor='#244C7B'
              labelColor='#FCB636'
              label='Back to list of events'
              secondary
              icon={<FontIcon className='material-icons' style={{ margin: 0 }}>
                <i className='material-icons'>keyboard_arrow_left</i></FontIcon>}
              onClick={this.back.bind(this)}
          /><div style={{ clear: 'both' }} />
            <div className='trial-title'>
              New entry
            </div>
            {this.state.isLoading ? <div className='spinner-box'>
              <div className={'spinner'}>
                <Spinner fadeIn='none' className={'spin-item'} color={'#fdb913'} name='ball-spin-fade-loader' />
              </div>
            </div>
              : <div className='trials-header'>
                <List style={{ width: '100%' }}>
                  {this.state.listOfObservations && this.state.listOfObservations.map((object) => (
                    <ListItem
                      key={object.id}
                      style={this.checkAnswers(object.answersId)
                    ? { border: '1px solid #feb912', backgroundColor: '#1f497e12' }
                      : { border: '1px solid #feb912', backgroundColor: '#feb91221' }}
                      primaryText={object.name}
                      secondaryText={object.description}
                      onClick={() => this.newObservation(object.id)}
                    />
                ))}
                </List>
              </div>
            }
          </div>
        </div>
      </div>
    )
  }
}

export default SelectObservation
