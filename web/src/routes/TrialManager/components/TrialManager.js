import React, { Component } from 'react'
import PropTypes from 'prop-types'
import TrialManager from '../../../components/TrialManager'

class TrialManagerView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getTrialManager: PropTypes.func,
    listOfTrialsManager: PropTypes.array
  }

  render () {
    return (
      <div className='background-home'>
        <TrialManager
          getTrialManager={this.props.getTrialManager}
          listOfTrialsManager={this.props.listOfTrialsManager}
        />
      </div>
    )
  }
}

export default TrialManagerView
