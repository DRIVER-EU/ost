import React, { Component } from 'react'
import PropTypes from 'prop-types'
import NewObservationComponent from '../../../components/NewObservationComponent'

class QuestionView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getViewSchema: PropTypes.func,
    viewSchema: PropTypes.any,
    params: PropTypes.any
  }

  render () {
    return (
      <div className='background-home'>
        <NewObservationComponent
          getSchema={this.props.getViewSchema}
          questionSchema={this.props.viewSchema}
          mode
          params={this.props.params} />
      </div>
    )
  }
}

export default QuestionView