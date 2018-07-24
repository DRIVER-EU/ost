import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './SelectObservation.scss'
import { browserHistory } from 'react-router'
import { RaisedButton, FontIcon } from 'material-ui'
import { List, ListItem } from 'material-ui/List'
import _ from 'lodash'
import { toastr } from 'react-redux-toastr'

const toastrOptions = {
  timeOut: 3000
}

class SelectObservation extends Component {
  constructor (props) {
    super(props)
    this.state = {
      listOfObservations: []
    }
  }

  static propTypes = {
    getObservations: PropTypes.func,
    listOfObservations: PropTypes.any,
    params: PropTypes.any
  }

  componentWillMount () {
    this.props.getObservations(this.props.params.id)
  }

  componentWillReceiveProps (nextProps) {
    let listOfObsevationProps = [...nextProps.listOfObservations]
    let listOfObsevation = [...this.state.listOfObservations]
    if (nextProps.listOfObservations && this.props.listOfObservations &&
      !_.isEqual(listOfObsevation.sort(), listOfObsevationProps.sort())) {
      let newObs = _.differenceWith(listOfObsevationProps, this.props.listOfObservations, _.isEqual)
      if (this.props.listOfObservations.length !== 0 && newObs.length !== 0) {
        toastr.success('New Event', 'New Event received.', toastrOptions)
      }
      this.setState({ listOfObservations: nextProps.listOfObservations })
    }
  }

  newObservation (id) {
    browserHistory.push(`/trials/${this.props.params.id}/new-observation/${id}`)
  }

  back () {
    browserHistory.push(`/trials/${this.props.params.id}`)
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='view-trials-container'>
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
              New observation
            </div>
            <div className='trials-header'>
              <List style={{ width: '100%' }}>
                { this.state.listOfObservations.map((object, key) => (
                  <ListItem
                    key={object.id}
                    style={key % 2 === 0 ? { border: '1px solid #feb912', backgroundColor: '#1f497e12' }
                    : { border: '1px solid #feb912', backgroundColor: '#feb91221' }}
                    primaryText={object.name}
                    secondaryText={object.description}
                    onClick={() => this.newObservation(object.id)}
                  />
              ))}
              </List>
            </div>
          </div>
        </div>
      </div>

    )
  }
}

export default SelectObservation
