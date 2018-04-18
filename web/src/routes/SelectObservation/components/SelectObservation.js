import React, { Component } from 'react'
import PropTypes from 'prop-types'
import SelectObservation from '../../../components/SelectObservation'

class SelectObservationComponent extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getObservations: PropTypes.func,
    listOfObservations: PropTypes.any,
    params: PropTypes.any
  }

  render () {
    return (
      <div className='background-home'>
        <SelectObservation
          getObservations={this.props.getObservations}
          listOfObservations={this.props.listOfObservations}
          params={this.props.params} />
      </div>
    )
  }
}

export default SelectObservationComponent
