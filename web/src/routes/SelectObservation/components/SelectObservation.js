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
    params: PropTypes.any,
    getViewTrials: PropTypes.func,
    clearTrialList: PropTypes.func
  }

  render () {
    return (
      <div className='background-home'>
        <SelectObservation
          getObservations={this.props.getObservations}
          listOfObservations={this.props.listOfObservations}
          getViewTrials={this.props.getViewTrials}
          clearTrialList={this.props.clearTrialList}
          params={this.props.params} />
      </div>
    )
  }
}

export default SelectObservationComponent
