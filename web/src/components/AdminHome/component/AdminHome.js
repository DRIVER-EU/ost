import React, { Component } from 'react'
import './AdminHome.scss'
import RaisedButton from 'material-ui/RaisedButton'

class AdminHome extends Component {
  render () {
    return (
      <div className='main-container'>
        <div className='pages-box' style={{ background: 'white' }}>
          <div className='home-container'>
            <div className='buttons-container'>
              <a href='users'>
                <RaisedButton
                  buttonStyle={{ width: '200px' }}
                  backgroundColor='#244C7B'
                  labelColor='#FCB636'
                  label='Users'
                  type='button'
                />
              </a>
              <a href='trial-manager'>
                <RaisedButton
                  buttonStyle={{ width: '200px' }}
                  backgroundColor='#244C7B'
                  labelColor='#FCB636'
                  label='Trials'
                  type='button'
                />
              </a>
              <a href='active-sessions'>
                <RaisedButton
                  buttonStyle={{ width: '200px' }}
                  backgroundColor='#244C7B'
                  labelColor='#FCB636'
                  label='Session'
                  type='button'
                />
              </a>
            </div>
            <div className='image-container'>
              <img src='/images/ost.png' />
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default AdminHome
