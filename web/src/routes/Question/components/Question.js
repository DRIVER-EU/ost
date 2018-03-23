import React, { Component } from 'react'
import Question from '../../../components/Question'

class QuestionView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  render () {
    return (
      <div className='background-home'>
        <Question />
      </div>
    )
  }
}

export default QuestionView
