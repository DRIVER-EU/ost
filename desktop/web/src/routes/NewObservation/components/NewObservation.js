import React, { Component } from 'react'
import PropTypes from 'prop-types'
import NewObservationComponent from '../../../components/NewObservationComponent'

class NewObservation extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getSchema: PropTypes.func,
    questionSchema: PropTypes.any,
    sendObservation: PropTypes.func,
    observation: PropTypes.any
  }

  render () {
    return (
      <div className='background-home'>
        <NewObservationComponent
          getSchema={this.props.getSchema}
          questionSchema={this.props.questionSchema}
          sendObservation={this.props.sendObservation}
          observation={this.props.observation} />
      </div>
    )
  }
}

export default NewObservation
