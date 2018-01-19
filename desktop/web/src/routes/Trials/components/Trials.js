import React, { Component } from 'react'
import Trials from '../../../components/Trials'

class TrialsView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  render () {
    return (
      <div className='background-home'>
        <Trials />
      </div>
    )
  }
}

export default TrialsView
