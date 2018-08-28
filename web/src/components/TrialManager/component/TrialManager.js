import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './TrialManager.scss'
import { Accordion, AccordionItem } from 'react-sanfona'
import RaisedButton from 'material-ui/RaisedButton'
import { browserHistory } from 'react-router'
import Spinner from 'react-spinkit'
import FloatingActionButton from 'material-ui/FloatingActionButton'
import ContentAdd from 'material-ui/svg-icons/content/add'
import FlatButton from 'material-ui/FlatButton'
import Dialog from 'material-ui/Dialog'
import SelectField from 'material-ui/SelectField'
import MenuItem from 'material-ui/MenuItem'

class TrialManager extends Component {
  constructor (props) {
    super(props)
    this.state = {
      listOfTrialsManager: [],
      listOfTrials: [],
      isLoading: false,
      openModal: false,
      trialId: null
    }
  }

  static propTypes = {
    getTrialManager: PropTypes.func,
    listOfTrialsManager: PropTypes.object,
    getListOfTrials: PropTypes.func,
    listOfTrials: PropTypes.array
  }

  componentWillMount () {
    this.props.getTrialManager()
    this.setState({ isLoading: true })
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.listOfTrialsManager.data &&
      nextProps.listOfTrialsManager.data !== this.state.listOfTrialsManager &&
      nextProps.listOfTrialsManager.data !== this.props.listOfTrialsManager.data) {
      this.setState({
        listOfTrialsManager: nextProps.listOfTrialsManager.data,
        isLoading: false
      })
    }
    if (nextProps.listOfTrials &&
      nextProps.listOfTrials !== this.props.listOfTrials) {
      let listOfTrials = []
      nextProps.listOfTrialsManager.data.map((object) => {
        listOfTrials.push({ id: object.id, name: object.trialName })
      })
      this.setState({ listOfTrials: listOfTrials })
    }
  }

  viewTrial (id) {
    browserHistory.push(`/admin-trials/${id}`)
  }

  getShortDesc (str) {
    let desc = str.split(/[.|!|?]\s/)
    if (desc[1]) {
      return desc[0] + '. ' + desc[1]
    } else {
      return desc[0]
    }
  }

  handleOpen = () => {
    this.props.getListOfTrials()
    this.setState({
      trialId: null,
      openModal: true
    })
  }

  handleClose = () => {
    this.setState({ openModal: false })
  }

  newSession = () => {
    if (this.state.trialId) {
      browserHistory.push(`trial-manager/${this.state.trialId}/newsession`)
    }
  }

  handleChangeDropDown (stateName, event, index, value) {
    let change = { ...this.state }
    change[stateName] = value
    this.setState(change)
  }

  render () {
    const actions = [
      <FlatButton
        label='Cancel'
        primary
        onTouchTap={this.handleClose}
      />,
      <FlatButton
        label='Next'
        secondary
        keyboardFocused
        onClick={this.newSession.bind(this)}
      />
    ]
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='trials-container'>
            <div className='trials-header'>
              <div className={'trial-select'}>Trial Sessions</div>
            </div>
            {this.state.isLoading && <div className='spinner-box'>
              <div className={'spinner'}>
                <Spinner fadeIn='none' className={'spin-item'} color={'#fdb913'} name='ball-spin-fade-loader' />
              </div>
              </div>
            }
            {(!this.state.isLoading && this.state.listOfTrialsManager.length === 0) &&
            <div className={'no-sessions'}> No trial sessions available</div>}
            <Accordion>
              {this.state.listOfTrialsManager.map((object) => {
                return (
                  <AccordionItem key={object.id}
                    title={<div className={'react-sanfona-item-title cursor-pointer'}><h3>
                      {object.trialName}</h3>
                      <h5 style={{ margin: '4px 0 10px' }}>
                        session: #{object.id} stage: {object.name} status: {object.status}
                      </h5>
                      <div className={'desc'}>{this.getShortDesc(object.trialDescription)}</div>
                    </div>} expanded={false} >
                    <div>
                      <p>{object.trialDescription}</p>
                      <div style={{ display: 'table', margin: '0 auto' }}>
                        <RaisedButton
                          buttonStyle={{ width: '200px' }}
                          backgroundColor='#244C7B'
                          labelColor='#FCB636'
                          onClick={this.viewTrial.bind(this, object.id)}
                          label='Enter' />
                      </div>
                    </div>
                  </AccordionItem>
                )
              })}
            </Accordion>
            <FloatingActionButton style={{ float: 'right' }} onTouchTap={this.handleOpen} secondary>
              <ContentAdd />
            </FloatingActionButton>
          </div>
        </div>
        <Dialog
          title='Select Trial'
          actions={actions}
          modal={false}
          open={this.state.openModal}
          onRequestClose={this.handleClose}>
          <div style={{
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'center',
            justifyContent: 'center',
            width: 600,
            marginBottom: 10 }}>
            <h2 style={{ display: 'inline-block', padding: '50px 40px 55px 50px' }}>Trial:</h2>
            <SelectField
              value={this.state.trialId}
              floatingLabelText='Select Trial'
              onChange={this.handleChangeDropDown.bind(this, 'trialId')} >
              {(this.state.listOfTrials && this.state.listOfTrials.length !== 0) &&
                this.state.listOfTrials.map((index) => (
                  <MenuItem
                    key={index.id}
                    value={index.id}
                    style={{ color: 'grey' }}
                    primaryText={index.name} />
                ))}
            </SelectField>
          </div>
        </Dialog>
      </div>
    )
  }
}

export default TrialManager
