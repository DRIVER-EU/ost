import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './SelectObservation.scss'
import { browserHistory } from 'react-router'
import { List, ListItem } from 'material-ui/List'
import _ from 'lodash'

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
    if (nextProps.listOfObservations.data && this.props.listOfObservations &&
      !_.isEqual(this.state.listOfObservations.sort(), nextProps.listOfObservations.data.sort())) {
      this.setState({ listOfObservations: nextProps.listOfObservations.data })
    }
  }

  newObservation (id) {
    browserHistory.push(`/trials/${this.props.params.id}/new-observation/${id}`)
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='view-trials-container'>
            <div className='trial-title' />
            <div>New observation</div>
            <div className='trials-header'>
              <List style={{ width: '100%' }}>
                { this.state.listOfObservations.map((object, key) => (
                  <ListItem
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
