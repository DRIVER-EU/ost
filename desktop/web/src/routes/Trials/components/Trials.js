import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Trials from '../../../components/Trials'

class TrialsView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getObservation: PropTypes.func,
    observation: PropTypes.array
  }

  render () {
    return (
      <div className='background-home'>
        <Trials
          getObservation={this.props.getObservation}
          observation={this.props.observation}
        />
      </div>
    )
  }
}

export default TrialsView
