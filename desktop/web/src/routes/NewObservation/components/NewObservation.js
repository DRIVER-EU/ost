import React, { Component } from 'react'
import NewObservationComponent from '../../../components/NewObservationComponent'

class NewObservation extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  render () {
    return (
      <div className='background-home'>
        <NewObservationComponent
        />
      </div>
    )
  }
}

export default NewObservation
