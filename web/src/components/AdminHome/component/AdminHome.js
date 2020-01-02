import React, { Component } from 'react'
import './AdminHome.scss'
import RaisedButton from 'material-ui/RaisedButton'

class AdminHome extends Component {

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box' style={{ 'background': 'white' }}>
          <div className='home-container'>
            <div className='buttons-container'>
              <RaisedButton
                buttonStyle={{ width: '200px' }}
                backgroundColor='#244C7B'
                labelColor='#FCB636'
                label='Users'
                type='button' />
              <a href='trial-manager'>
                <RaisedButton
                  buttonStyle={{ width: '200px' }}
                  backgroundColor='#244C7B'
                  labelColor='#FCB636'
                  label='Trials'
                  type='button' />
              </a>
              <RaisedButton
                buttonStyle={{ width: '200px' }}
                backgroundColor='#244C7B'
                labelColor='#FCB636'
                label='Session'
                type='button' />
            </div>
            <div className='image-container'>
              <img src='https://fakeimg.pl/800x400/?text=PNG' />
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default AdminHome
