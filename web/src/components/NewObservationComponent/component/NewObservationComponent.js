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
import moment from 'moment'
import { toastr } from 'react-redux-toastr'

const toastrOptions = {
  timeOut: 3000
}

const styles = {
  checkbox: {
    marginBottom: 5
  },
  label: {
    color: 'red'
  }
}

const widgets = {
  slider: (props) => {
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
      // coords1ErrorText: '',
      // coords2ErrorText: '',
      // coords3ErrorText: '',
      // attachmentCoordinatesLong: '',
      // attachmentCoordinatesAlt: '',
      // attachmentCoordinatesLat: '',
      images: [],
      listOfParticipants: [],
      dateTime: moment(new Date().getTime()).format('YYYY-MM-DD kk:mm:ss'),
      isLoading: false,
      attachmentDescription: '',
      validParticipants: true,
      files: [],
      isShow: false,
      time: moment(props.trialTime ? props.trialTime : new Date().getTime()).format('DD/MM/YYYY HH:mm:ss')
    }
  }

  static propTypes = {
    getSchema: PropTypes.func,
    observationForm: PropTypes.any,
    mode: PropTypes.string,
    params: PropTypes.any,
    downloadFile: PropTypes.func,
    closeModal: PropTypes.func,
    observation: PropTypes.any,
    getTrialTime: PropTypes.func,
    trialTime: PropTypes.number
  }

  downloadFile (id, name) {
    this.props.downloadFile(id, name)
  }

  handleChangeTrialTime (time) {
    this.setState({
      time: time
    })
  }

  componentWillMount () {
    window.onkeypress = function (e) {
      if (e.charCode === 13) {
        e.preventDefault()
      }
    }
    if (this.props.params.id_observation) {
      this.props.getSchema(this.props.params.id_observation, this.props.params.id)
    }
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
      if (nextProps.observationForm.hasOwnProperty('time') && nextProps.observationForm.time !== null) {
        change['dateTime'] = nextProps.observationForm.time
      }
      if (nextProps.observationForm.hasOwnProperty('trialRoles') &&
        nextProps.observationForm.trialRoles.trial.length !== 0) {
        nextProps.observationForm.trialRoles.trial.map((name, index) => {
          change['observationForm']['roles'].push({ id: index, name: name })
        })
      }
      if (nextProps.observationForm.hasOwnProperty('trialRoles') &&
        nextProps.observationForm.trialRoles.check.length !== 0) {
        let index = []
        nextProps.observationForm.trialRoles.check.map((obj) => {
          index.push({ id:_.findIndex(change['observationForm']['roles'], { name: obj }) })
        })
        change['listOfParticipants'] = index
      } else {
        change['observationForm']['roles'] = nextProps.observationForm.roles ? nextProps.observationForm.roles : []
      }
      change['observationForm']['schema'] = nextProps.observationForm.jsonSchema.schema
      change['observationForm']['uiSchema'] = nextProps.observationForm.jsonSchema.uiSchema
      if (nextProps.observationForm.jsonSchema.formData) {
        change['observationForm']['formData'] = nextProps.observationForm.jsonSchema.formData
      }
      if (nextProps.observationForm.attachments) {
        change['observationForm']['attachments'] = nextProps.observationForm.attachments
      }
      change['observationForm']['description'] = nextProps.observationForm.description
      if (nextProps.observationForm.attachments && nextProps.observationForm.attachments.coordinates &&
        nextProps.observationForm.attachments.coordinates[0]) {
        /* eslint-disable */
        let coords = eval(nextProps.observationForm.attachments.coordinates[0].data)
        /* eslint-enable */
        if (coords.length > 0) {
          change['attachmentCoordinatesLong'] = coords[0].toString()
        }
        if (coords.length >= 2) {
          change['attachmentCoordinatesLat'] = coords[1].toString()
        }
        if (coords.length === 3) {
          change['attachmentCoordinatesAlt'] = coords[2].toString()
        }
      }
      if (nextProps.observationForm.attachments && nextProps.observationForm.attachments.descriptions) {
        change['attachmentDescription'] = nextProps.observationForm.attachments.descriptions[0].data
      }
      if (nextProps.observationForm.attachments && nextProps.observationForm.attachments.files) {
        change['files'] = nextProps.observationForm.attachments.files
      }
      change['isLoading'] = false
      if (!_.isEqual(change.observationForm, nextProps.observationForm)) {
        this.setState(change)
      }
    }
    if (nextProps.observation && nextProps.observation !== this.props.observation) {
      browserHistory.push(`/trials/${this.props.params.id}/select-observation`)
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
    for (let i = 0; i < this.state.listOfParticipants.length; i++) {
      tab.push(this.state.listOfParticipants[i].id)
    }
    send['observationTypeId'] = this.props.params.id_observation
    send['trialSessionId'] = this.props.params.id
    send['trialTime'] = this.state.time
    send['simulationTime'] = moment(this.state.dateTime, 'YYYY-MM-DD kk:mm:ss').format('YYYY-MM-DDTkk:mm:ssZ')
    send['fieldValue'] = ''
    send['formData'] = this.state.observationForm.formData
    send['trialRoleIds'] = tab
    send['descriptions'] = [this.state.attachmentDescription]
    send['coordinates'] = [
      { 'longitude': '',
        'latitude': '',
        'altitude': '' }
    ]
    send['attachments'] = this.state.images
    if (this.validateParticipants()) {
      this.props.sendObservation(send)
      if (this.props.mode === 'profileQuestion') {
        this.props.closeModal()
      }
    }
  }

  // validateCoords () {
  //   if (isNaN(this.state.attachmentCoordinatesLong)) {
  //     this.setState({ coords1ErrorText: 'Error: incorrect value' })
  //   } else {
  //     this.setState({ coords1ErrorText: '' })
  //   }
  //   if (isNaN(this.state.attachmentCoordinatesAlt)) {
  //     this.setState({ coords2ErrorText: 'Error: incorrect value' })
  //   } else {
  //     this.setState({ coords2ErrorText: '' })
  //   }
  //   if (isNaN(this.state.attachmentCoordinatesLat)) {
  //     this.setState({ coords3ErrorText: 'Error: incorrect value' })
  //   } else {
  //     this.setState({ coords3ErrorText: '' })
  //   }
  //   let valid = true
  //   if (isNaN(this.state.attachmentCoordinatesLong) ||
  //     isNaN(this.state.attachmentCoordinatesAlt) ||
  //     isNaN(this.state.attachmentCoordinatesLat)) {
  //     toastr.error('Observation form', 'Error! Please, check all fields in form.', toastrOptions)
  //     valid = false
  //   } else {
  //     this.setState({ coordsErrorText: '' })
  //   }
  //   return valid
  // }

  validateParticipants () {
    let valid = true
    if (this.state.observationForm.roles.length > 0 && this.state.listOfParticipants.length === 0) {
      toastr.error('Observation form', 'Error! Please, check all fields in form.', toastrOptions)
      valid = false
    }
    this.setState({ validParticipants: valid })
    return valid
  }

  changeObservation (object) {
    let change = { ...this.state.observationForm }
    change.formData = object.formData
    this.setState({ observationForm: change })
  }

  setDate = (dateTime) => this.setState({ dateTime: moment(dateTime).format('YYYY-MM-DD kk:mm:ss') })

  handleParticipants (id) {
    let change = [ ...this.state.listOfParticipants ]
    let chosenIndex = _.findIndex(change, { id: id })
    if (chosenIndex === -1) {
      change.push({ id: id })
    } else {
      change.splice(chosenIndex, 1)
    }
    this.setState({
      listOfParticipants: change,
      validParticipants: true
    })
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
    this.setState({
      listOfParticipants: change,
      validParticipants: true
    })
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
    browserHistory.push(`/trials/${this.props.params.id}/select-observation`)
  }

  handleDescription (value) {
    this.setState({ attachmentDescription: value })
  }

  handleError () {
    toastr.error('Observation form', 'Error! Please, check all fields in form.', toastrOptions)
  }

  handleOnSubmit (object) {
    if (object.length !== null && object.length !== 0) {
      this.submitObservation()
    }
  }

  componentDidMount () {
    this.props.getTrialTime()
  }

  componentDidUpdate (prevState, prevProps) {
    if (this.state.isShow && !prevProps.isShow) {
      setTimeout(() => { this.setState({ isShow: false }) }, 15000)
    }
  }

  // handleCoordinatesLong (value) {
  //   this.setState({ attachmentCoordinatesLong: value })
  // }

  // handleCoordinatesLatitude (value) {
  //   this.setState({ attachmentCoordinatesLat: value })
  // }

  // handleCoordinatesAlt (value) {
  //   this.setState({ attachmentCoordinatesAlt: value })
  // }

  showInfo = () => {
    let change = { ...this.state }
    change.isShow = !change.isShow
    this.setState(change)
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box' style={{ height: '100%' }}>
          <div className='question-container'>
            {(this.props.mode !== 'viewAdmin' && this.props.mode !== 'profileQuestion') &&
            <div className={'buttons-obs'} style={{ textAlign: 'right' }}>
              <RaisedButton
                buttonStyle={{ width: '300px' }}
                backgroundColor='#244C7B'
                labelColor='#FCB636'
                label='Back to list of Observations'
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
                <DateComponent
                  mode={this.props.mode}
                  desc={'Real Time: '}
                  />
                <DateComponent
                  trialTime={this.props.trialTime ? this.props.trialTime : new Date()}
                  desc={'Trial Time: '}
                  handleChangeTrialTime={(time) => this.handleChangeTrialTime(time)}
                  mode={this.props.mode} />
                <div style={{ textAlign: 'center', borderBottom: '1px solid rgb(254, 185, 18)' }}>
                  {this.props.observationForm.name}
                  <RaisedButton
                    style={{ margin: 10 }}
                    buttonStyle={{ width: 100 }}
                    backgroundColor='#244C7B'
                    labelColor='#FCB636'
                    label='INFO'
                    secondary
                    onClick={this.showInfo}
                  />
                </div>
              </div>
              <p className='title-obs'>{this.state.observationForm.name}</p>
              {this.state.isShow && <p className='desc-obs'>{this.state.observationForm.description}</p>}
              <p className='point-obs'>When:</p>
              <DateTimePicker
                disabled={this.props.mode !== 'new' && this.props.mode !== 'profileQuestion'}
                onChange={this.setDate}
                DatePicker={DatePickerDialog}
                TimePicker={TimePickerDialog}
                value={this.state.dateTime}
                format='YYYY-MM-DD kk:mm' />
              {this.state.observationForm.roles.length !== 0 && <div>
                <p className='point-obs'>Who:</p>
                {this.state.observationForm.roles.map((object, index) => (
                  <Checkbox
                    disabled={this.props.mode !== 'new' && this.props.mode !== 'profileQuestion'}
                    label={object.name}
                    checked={this.handleChecked(object.id)}
                    onCheck={this.handleParticipants.bind(this, object.id)}
                    style={styles.checkbox}
                    labelStyle={!this.state.validParticipants && styles.label} />
              ))}
                {this.state.observationForm.roles.length > 0 &&
                <Checkbox
                  disabled={this.props.mode !== 'new' && this.props.mode !== 'profileQuestion'}
                  label='All'
                  checked={this.state.listOfParticipants.length === this.state.observationForm.roles.length}
                  onCheck={this.handleAllParticipants.bind(this)}
                  style={styles.checkbox}
                  labelStyle={!this.state.validParticipants && styles.label} />
            }
              </div>
            }
              <p className='point-obs'>What:</p>
              <Form
                schema={this.state.observationForm.schema}
                uiSchema={this.state.observationForm.uiSchema}
                formData={this.state.observationForm.formData}
                widgets={widgets}
                showErrorList={false}
                onError={() => this.handleError()}
                onSubmit={(value) => this.handleOnSubmit(value)}
                onChange={(value) => this.changeObservation(value)}>
                <div>
                  <p className='point-obs'>Attachments:</p>
                  <p>Description:</p>
                  <TextField style={{ width:'100%' }}
                    value={this.state.attachmentDescription}
                    onChange={(event, value) => { this.handleDescription(value) }}
                    disabled={this.props.mode !== 'new' && this.props.mode !== 'profileQuestion'}
                    />
                  {/* <p className={'coords-title'}>Coordinates:</p>
                  <TextField value={this.state.attachmentCoordinatesLong}
                    style={{ marginRight:'20px', width: '150px' }}
                    onChange={(event, value) => { this.handleCoordinatesLong(value) }}
                    disabled={this.props.mode !== 'new' && this.props.mode !== 'profileQuestion'}
                    floatingLabelText='Longitude'
                    errorText={this.state.coords1ErrorText} />
                  <TextField value={this.state.attachmentCoordinatesLat}
                    style={{ marginRight:'20px', width: '150px' }}
                    onChange={(event, value) => { this.handleCoordinatesLatitude(value) }}
                    disabled={this.props.mode !== 'new' && this.props.mode !== 'profileQuestion'}
                    floatingLabelText='Latitude'
                    errorText={this.state.coords3ErrorText} />
                  <TextField value={this.state.attachmentCoordinatesAlt}
                    style={{ marginRight:'20px', width: '150px' }}
                    onChange={(event, value) => { this.handleCoordinatesAlt(value) }}
                    floatingLabelText='Altitude'
                    errorText={this.state.coords2ErrorText}
                    disabled={this.props.mode !== 'new' && this.props.mode !== 'profileQuestion'} /> */}
                  {false &&
                  <div>
                    {(this.props.mode === 'new' || this.props.mode === 'profileQuestion') &&
                    <div>
                      <p>Files:</p>
                      <DropzoneComponent
                        config={this.componentConfig}
                        djsConfig={this.djsConfig}
                        eventHandlers={this.eventHandlers} />
                    </div>
                  }
                    { (this.props.mode !== 'new' && this.props.mode !== 'profileQuestion') &&
                  this.state.files.length > 0 &&
                    <div><p>Files:</p>
                      {this.state.files.map(object => {
                        return (<div className={'pointer'} onClick={() => { this.downloadFile(object.id, object.data) }}
                      ><FontIcon className='material-icons' style={{ marginRight: '5px', top: '7px' }}>
                        <i className='material-icons'>attachment</i></FontIcon>
                          {object.data}</div>)
                      })}
                    </div>
                  }
                  </div>}
                </div>
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
                      type='submit' />
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

