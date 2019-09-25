import React, { Component } from 'react'
import PropTypes from 'prop-types'
import NewObservationComponent from '../../../components/NewObservationComponent'

class QuestionView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getSchemaView: PropTypes.func,
    observationForm: PropTypes.any,
    params: PropTypes.any,
    downloadFile: PropTypes.func,
    getTrialTime: PropTypes.func,
    resetObservation: PropTypes.func
  }

  render () {
    return (
      <div className='background-home'>
        <NewObservationComponent
          getSchema={this.props.getSchemaView}
          getTrialTime={this.props.getTrialTime}
          observationForm={this.props.observationForm}
          mode={'view'}
          params={this.props.params}
          downloadFile={this.props.downloadFile}
          resetObservation={this.props.resetObservation} />
      </div>
    )
  }
}

export default QuestionView
