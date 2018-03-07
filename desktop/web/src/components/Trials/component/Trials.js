import React, { Component } from 'react'
import DateComponent from '../../DateComponent/DateComponent'
import './Trials.scss'
import Accordion from 'react-responsive-accordion'

class Trials extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='trials-container'>
            <div className='trials-header'>
              <div>Summary of observations</div>
              <DateComponent />
            </div>

            <Accordion>
              <div data-trigger='A nifty React accordion component'>
                <p>
                  So this is an Accordion component that used the
                  <a href='https://github.com/glennflanagan/react-collapsible'>react-collapsible</a>
                  component. How handy.
                </p>
              </div>

              <div data-trigger='What the difference?'>
                <p>
                  An Accordion is different to a Collapsible in the sense that only one
                  "tray" will be open at any one time.
                </p>
              </div>

              <div data-trigger="I'm responsive and I have a little secret. Look inside.">
                <p>
                  And this Accordion component is also completely repsonsive. Hurrah
                  for mobile users!
                </p>
              </div>
            </Accordion>

          </div>
        </div>
      </div>
    )
  }
}

export default Trials
