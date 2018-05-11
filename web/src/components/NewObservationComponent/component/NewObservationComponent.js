import React, { Component } from 'react'
import PropTypes from 'prop-types'
import DateComponent from '../../DateComponent/DateComponent'
import './NewObservationComponent.scss'
import { Checkbox, RaisedButton, TextField } from 'material-ui'
import _ from 'lodash'
import DateTimePicker from 'material-ui-datetimepicker'
import DatePickerDialog from 'material-ui/DatePicker/DatePickerDialog'
import TimePickerDialog from 'material-ui/TimePicker/TimePickerDialog'
import Slider from './Slider'
import radio from './Radio'
import Form from 'react-jsonschema-form-mui'
import { browserHistory } from 'react-router'
import Spinner from 'react-spinkit'
import FontIcon from 'material-ui/FontIcon'
import DropzoneComponent from 'react-dropzone-component'
import './Upload.scss'

const styles = {
  checkbox: {
    marginBottom: 5
  }
}

const widgets = {
  Slider: (props) => {
    return Slider(props)
  },
  radio: (props) => {
    return radio(props)
  }
}

class NewObservationComponent extends Component {
  constructor (props) {
    super()
    this.state = {
      observationForm: {
        name:'',
        description: '',
        schema: {},
        uiSchema: {},
        roles: [],
        formData: {}
      },
      images: [],
      listOfParticipants: [],
      dateTime: null,
      isLoading: false,
      attachmentDescription: ''
    }
  }

  static propTypes = {
    getSchema: PropTypes.func,
    observationForm: PropTypes.any,
    mode: PropTypes.string,
    params: PropTypes.any
  }

  componentWillMount () {
    this.props.getSchema(this.props.params.id_observation, this.props.params.id)
    this.setState({ isLoading: true })
    this.djsConfig = {
      addRemoveLinks: true,
      acceptedFiles: 'image/jpeg,image/png,image/gif,image/bmp',
      autoProcessQueue: false,
      thumbnailWidth: 300,
      thumbnailHeight: 300,
      thumbnailMethod: 'contain',
      uploadMultiple: true,
      parallelUploads: 1,
      maxFiles: 10
    }
    this.componentConfig = {
      iconFiletypes: ['.jpg', '.png', '.gif'],
      showFiletypeIcon: false,
      postUrl: 'no-url',
      removeFile: this.removeFile
    }
    this.eventHandlers = {
      init: this.initCallback.bind(this),
      addedfile: (file) => this.setFile(file),
      maxfilesexceeded: function (file) {
        this.removeAllFiles()
        this.addFile(file)
      }
    }
    this.myDropzone = {}
    this.setFile = this.setFile.bind(this)
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.observationForm && this.props.observationForm &&
      this.state.observationForm !== nextProps.observationForm) {
      let change = { ...this.state }
      change['observationForm']['schema'] = nextProps.observationForm.jsonSchema.schema
      change['observationForm']['uiSchema'] = nextProps.observationForm.jsonSchema.uiSchema
      change['observationForm']['formData'] = nextProps.observationForm.jsonSchema.formData
      change['observationForm']['roles'] = nextProps.observationForm.roles ? nextProps.observationForm.roles : []
      change['observationForm']['description'] = nextProps.observationForm.description

      this.setState({ change })
    }
    if (nextProps.mode) {
      let change = { ...this.state }
      change['listOfParticipants'] = []
      change['isLoading'] = false
      this.setState(change)
    }
  }

  initCallback (dropzone) {
    this.myDropzone = dropzone
  }

  setFile (image) {
    let change = [ ...this.state.images ]
    change.push(image)
    this.setState({ images: change })
  }

  removeFile () {
    if (this.myDropzone) {
      this.myDropzone.removeFile()
    }
  }

  static propTypes = {
    getSchema: PropTypes.func,
    observationForm: PropTypes.any,
    mode: PropTypes.string,
    params: PropTypes.any,
    sendObservation: PropTypes.func
  }

  submitObservation () {
    let send = {}
    let tab = []
    if (this.state.dateTime !== null) {
 //     send['dateTime'] = this.state.dateTime
    } else {
 //     send['dateTime'] = moment(new Date().getTime()).format('YYYY-MM-DDThh:mm:ss A')
    }
    for (let i = 0; i < this.state.listOfParticipants.length; i++) {
      tab.push(this.state.listOfParticipants[i].id)
    }
    send['observationTypeId'] = this.props.params.id_observation
    send['trialSessionId'] = this.props.params.id
    send['simulationTime'] = '2018-04-23T07:50:39+00:00'
    send['fieldValue'] = 'test'
    send['formData'] = this.state.observationForm.formData
    send['trialRoleIds'] = []
    send['descriptions'] = [this.state.attachmentDescription[1]]
    send['coordinates'] = [
      { 'longitude': 32.2,
        'latitude': 23.2,
        'altitude': 0.0 }
    ]
    send['attachments'] = this.state.images
    this.props.sendObservation(send)
  }

  changeObservation (object) {
    let change = { ...this.state.observationForm }
    change.formData = object.formData
    this.setState({ observationForm: change })
  }

  setDate = (dateTime) => this.setState({ dateTime })

  handleParticipants (id) {
    let change = [ ...this.state.listOfParticipants ]
    let chosenIndex = _.findIndex(change, { id: id })
    if (chosenIndex === -1) {
      change.push({ id: id })
    } else {
      change.splice(chosenIndex, 1)
    }
    this.setState({ listOfParticipants: change })
  }

  handleAllParticipants () {
    let change = [ ...this.state.listOfParticipants ]
    if (this.state.observationForm.roles.length > this.state.listOfParticipants.length) {
      change.splice(0, this.state.listOfParticipants.length)
      for (let i = 0; i < this.state.observationForm.roles.length; i++) {
        change.push({ id: this.state.observationForm.roles[i].id })
      }
    } else {
      change.splice(0, this.state.listOfParticipants.length)
    }
    this.setState({ listOfParticipants: change })
  }

  handleChecked (id) {
    let change = [ ...this.state.listOfParticipants ]
    let check = false
    let chosenIndex = ''
    if (this.props.mode) {
      let tab = []
      change.map((id) => (
        tab.push({ 'id': id.id })
      ))
      chosenIndex = _.findIndex(tab, { 'id': id })
    } else {
      chosenIndex = _.findIndex(change, { 'id': id })
    }
    if (chosenIndex !== -1) {
      check = true
    }
    return check
  }

  back () {
    browserHistory.push(`/trials/${this.props.params.id}`)
  }

  handleDescription (value) {
    this.setState({ attachmentDescription: value })
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box' style={{ height: '100%' }}>
          <div className='question-container'>
            {(this.props.mode !== 'viewAdmin' && this.props.mode !== 'profileQuestion') &&
            <div className={'buttons-obs'} style={{ textAlign: 'right' }}>
              <RaisedButton
                buttonStyle={{ width: '240px' }}
                backgroundColor='#244C7B'
                labelColor='#FCB636'
                label='Back to list of events'
                secondary
                icon={<FontIcon className='material-icons' style={{ margin: 0 }}>
                  <i className='material-icons'>keyboard_arrow_left</i></FontIcon>}
                onClick={this.back.bind(this)} />
            </div>}
            {this.state.isLoading && <div className='spinner-box'>
              <div className={'spinner'}>
                <Spinner fadeIn='none' className={'spin-item'} color={'#fdb913'} name='ball-spin-fade-loader' />
              </div>
            </div>}
            {(!this.state.isLoading) &&
            <div>
              <div className='trials-header'>
                <DateComponent />
                <div style={{ textAlign: 'center', borderBottom: '1px solid rgb(254, 185, 18)' }}>
                  {this.props.observationForm.name}
                </div>
              </div>
              <p className='title-obs'>{this.state.observationForm.name}</p>
              <p className='desc-obs'>{this.state.observationForm.description}</p>
              <p className='point-obs'>When:</p>
              <DateTimePicker
                disabled={this.props.mode !== 'new' && this.props.mode !== 'profileQuestion'}
                onChange={this.setDate}
                DatePicker={DatePickerDialog}
                TimePicker={TimePickerDialog}
                format='YYYY/MM/DD H:mm' />
              {this.state.observationForm.roles.length !== 0 && <div>
                <p className='point-obs'>Who:</p>
                {this.state.observationForm.roles.map((object) => (
                  <Checkbox
                    disabled={this.props.mode !== 'new' && this.props.mode !== 'profileQuestion'}
                    label={object.name}
                    checked={this.handleChecked(object.id)}
                    onCheck={this.handleParticipants.bind(this, object.id)}
                    style={styles.checkbox} />
              ))}
                {this.state.observationForm.roles.length > 0 &&
                <Checkbox
                  disabled={this.props.mode !== 'new' && this.props.mode !== 'profileQuestion'}
                  label='All'
                  checked={this.state.listOfParticipants.length === this.state.observationForm.roles.length}
                  onCheck={this.handleAllParticipants.bind(this)}
                  style={styles.checkbox} />
            }
              </div>
            }
              <p className='point-obs'>What:</p>
              <Form

                schema={this.state.observationForm.schema}
                uiSchema={this.state.observationForm.uiSchema}
                formData={this.state.observationForm.formData}
                widgets={widgets}
                onChange={(value) => this.changeObservation(value)} >
                { (this.props.mode === 'new' || this.props.mode === 'newmodal') && <div>
                  <p className='point-obs'>Attachments:</p>
                  <p>Description:</p>
                  <TextField value={this.state.attachmentDescription}
                    onChange={(event, value) => { this.handleDescription(value) }} />
                  <p>Files:</p>
                  <DropzoneComponent
                    config={this.componentConfig}
                    djsConfig={this.djsConfig}
                    eventHandlers={this.eventHandlers} />
                </div>}
                <div className={'buttons-center'}>
                  {(this.props.mode !== 'viewAdmin' && this.props.mode !== 'profileQuestion') &&
                  <div className={'buttons-observation'}>
                    <RaisedButton
                      buttonStyle={{ width: '200px' }}
                      backgroundColor='#244C7B'
                      labelColor='#FCB636'
                      label='Back'
                      secondary
                      onClick={this.back.bind(this)}
                  />
                  </div>
                }
                  {(this.props.mode === 'new' || this.props.mode === 'profileQuestion') &&
                  <div className={'submit buttons-observation'}>
                    <RaisedButton
                      buttonStyle={{ width: '200px' }}
                      backgroundColor='#244C7B'
                      labelColor='#FCB636'
                      label='Submit'
                      onClick={this.submitObservation.bind(this)} />
                  </div>
                }
                </div>
              </Form>
            </div>}
          </div>
        </div>
      </div>
    )
  }
}

export default NewObservationComponent

