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
    observationForm: PropTypes.any,
    sendObservation: PropTypes.func,
    observation: PropTypes.any,
    params: PropTypes.any,
    downloadFile: PropTypes.func
  }

  render () {
    return (
      <div className='background-home'>
        <NewObservationComponent
          getSchema={this.props.getSchema}
          observationForm={this.props.observationForm}
          sendObservation={this.props.sendObservation}
          observation={this.props.observation}
          mode={'new'}
          params={this.props.params}
          downloadFile={this.props.downloadFile} />
      </div>
    )
  }
}

export default NewObservation
