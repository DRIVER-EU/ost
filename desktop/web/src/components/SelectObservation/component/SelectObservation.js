import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './SelectObservation.scss'
import { browserHistory } from 'react-router'
import { List, ListItem } from 'material-ui/List'

class SelectObservation extends Component {
  constructor (props) {
    super(props)
    this.state = {
      listOfObservations: []
    }
  }

  static propTypes = {
    listOfObservations: PropTypes.any
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.listOfObservations.data &&
      nextProps.listOfObservations.data !== this.state.listOfObservations &&
      nextProps.listOfObservations.data !== this.props.listOfObservations) {
      this.setState({ listOfObservations: nextProps.listOfObservations.data })
    }
  }

  viewTrial (id) {
    browserHistory.push(`/trials/${id}`)
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='view-trials-container'>
            <div className='trial-title'>
              <div>New observation</div>
            </div>
            <div className='trials-header'>
              <List style={{ width: '100%' }}>
                {this.state.listOfObservations.map((object) => {
                  <ListItem
                    primaryText={object.title}
                    secondaryText={object.desc} />
                })
              }
              </List>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default SelectObservation
