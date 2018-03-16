import React, { Component } from 'react'
import SelectObservation from '../../../components/SelectObservation'

class SelectObservationComponent extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  render () {
    return (
      <div className='background-home'>
        <SelectObservation
        />
      </div>
    )
  }
}

export default SelectObservationComponent
