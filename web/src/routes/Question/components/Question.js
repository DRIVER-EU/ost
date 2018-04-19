import React, { Component } from 'react'
import PropTypes from 'prop-types'
import NewObservationComponent from '../../../components/NewObservationComponent'

class QuestionView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getSchema: PropTypes.func,
    observationForm: PropTypes.any,
    params: PropTypes.any
  }
  componentWillReceiveProps (nextProps) {
    console.log(nextProps)
  }

  render () {
    return (
      <div className='background-home'>
        <NewObservationComponent
          getSchema={this.props.getSchema}
          observationForm={this.props.observationForm}
          mode
          params={this.props.params} />
      </div>
    )
  }
}

export default QuestionView
