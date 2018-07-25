import React, { Component } from 'react'
import { RaisedButton, FloatingActionButton } from 'material-ui'
import FontIcon from 'material-ui/FontIcon'
import DatePicker from 'material-ui/DatePicker'
import SelectField from 'material-ui/SelectField'
import Checkbox from 'material-ui/Checkbox'
import './newSession.scss'
import ContentAdd from 'material-ui/svg-icons/content/add'

class NewSessionComponent extends Component {
  state = {
    value: null
  };

  handleChange = (event, index, value) => this.setState({ value });
  render () {
    return (
      <div className='main-container'>
        <div className='pages-box' style={{ height: '100%' }}>
          <div className='question-container'>
            <h2 style={{ display: 'inline-block' }}>New Session</h2>
            <div className={'buttons-obs'} style={{ float: 'right', display: 'inline-block' }}>
              <RaisedButton
                buttonStyle={{ width: '300px' }}
                backgroundColor='#244C7B'
                labelColor='#FCB636'
                label='Back to list of Observations'
                secondary
                icon={<FontIcon className='material-icons' style={{ margin: 0 }}>
                  <i className='material-icons'>keyboard_arrow_left</i></FontIcon>} />
            </div>
            <div style={{
              marginTop: 100 }} />
            <div className='col-md-5'
              style={{ display: 'flex', flexDirection: 'row', alignItems: 'center', paddingTop: 20 }}>
              <h3 style={{ paddingRight: 20 }}>Time:</h3>
              <DatePicker hintText='Time' textFieldStyle={{ width: 150 }} />
            </div>
            <div className='col-md-7' style={{ display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
              <h3 style={{ paddingRight: 20, paddingTop: 20 }}>Start Stage:</h3>
              <SelectField
                value={this.state.value}
                onChange={this.handleChange}
                floatingLabelText='Stage'
                  >
                {}
              </SelectField>
            </div>
            <h3 style={{ paddingTop: 180 }}>Users:</h3>
            <div>
              <div style={{
                display: 'flex',
                alignItems: 'center' }}>
                <SelectField
                  value={this.state.value}
                  onChange={this.handleChange}
                  floatingLabelText='Select User'
                  autoWidth
                  classname='col-xs-12 col-md-4'
                  style={{ marginRight: 50 }}
                >
                  {}
                </SelectField>
                <div className='col-xs-12 col-md-8' style={{ display: 'flex', flexDirection: 'row' }}>
                  <Checkbox />
                  <Checkbox />
                  <Checkbox />
                  <Checkbox />
                  <Checkbox />
                </div>
              </div>
              <div style={{ float: 'right' }}>
                <FloatingActionButton secondary>
                  <ContentAdd />
                </FloatingActionButton>
              </div>
            </div>
            <RaisedButton
              label='Submit'
              style={{ display: 'table', margin: '0 auto', width: 200, marginTop: 120 }}
              primary />
          </div>
        </div>
      </div>
    )
  }
}

export default NewSessionComponent
