import React, { Component } from 'react'
import PropTypes from 'prop-types'
import DateComponent from '../../DateComponent/DateComponent'
import './ViewTrials.scss'
import { Accordion, AccordionItem } from 'react-sanfona'
import RaisedButton from 'material-ui/RaisedButton'
import { browserHistory } from 'react-router'
import FloatingActionButton from 'material-ui/FloatingActionButton'
import ContentAdd from 'material-ui/svg-icons/content/add'
import moment from 'moment'
import TextField from 'material-ui/TextField'
import Dialog from 'material-ui/Dialog'
import FlatButton from 'material-ui/FlatButton'
import _ from 'lodash'
import SummaryOfObservationModal from '../../SummaryOfObservationModal/SummaryOfObservationModal'
import Paper from 'material-ui/Paper'

const styles = {
  paper: {
    marginTop: 15,
    padding: 15
  },
  commentTitle: {
    sizeFont: 18
  }
}

class ViewTrials extends Component {
  constructor (props) {
    super(props)
    this.state = {
      viewTrials: [],
      selectedObj: {},
      showModal: false,
      trialSession: { name: '' },
      listOfTrials: props.listOfTrials.data ? props.listOfTrials.data : [],
      interval: '',
      id: props.params.id,
      open: false,
      answerId: '',
      answerRemove: '',
      answerRemoveErrorText: '',
      comment: '',
      commentErrorText: '',
      commentModal: false
    }
  }

  static propTypes = {
    getViewTrials: PropTypes.func,
    viewTrials: PropTypes.array,
    params: PropTypes.any,
    trialSession: PropTypes.any,
    getTrialSession: PropTypes.func,
    getTrials: PropTypes.func,
    listOfTrials: PropTypes.object,
    observationForm: PropTypes.any,
    downloadFile: PropTypes.func,
    sendObservation: PropTypes.func,
    clearTrialList: PropTypes.func,
    removeAnswer: PropTypes.func,
    editComment: PropTypes.func,
    editedComment: PropTypes.object
  }

  componentWillMount () {
    this.props.getTrials()
    this.props.getViewTrials(this.props.params.id)
    this.props.getTrialSession(this.props.params.id)
    let interval = setInterval(() => {
      this.props.getViewTrials(this.props.params.id)
      this.props.getTrialSession(this.props.params.id)
    }, 3000)
    this.setState({ interval: interval })

    this.handleFindObservation()
  }

  componentWillUnmount () {
    this.props.clearTrialList()
    clearInterval(this.state.interval)
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.listOfTrials.data &&
      !_.isEqual(nextProps.listOfTrials.data, this.state.listOfTrials) &&
      nextProps.listOfTrials.data !== this.props.listOfTrials) {
      this.setState(
        { listOfTrials: nextProps.listOfTrials.data },
        () => this.handleFindObservation()
      )
    }
    if (nextProps.viewTrials &&
      nextProps.viewTrials !== this.state.viewTrials &&
      nextProps.viewTrials !== this.props.viewTrials) {
      this.setState({ viewTrials: nextProps.viewTrials })
    }
    if (nextProps.trialSession && nextProps.trialSession.trialName && this.props.trialSession) {
      this.setState({ trialSession: nextProps.trialSession })
    }
    if (nextProps.editedComment && nextProps.editedComment !== this.props.editedComment) {
      this.props.getViewTrials(this.props.params.id)
    }
  }

  viewEvent (id) {
    browserHistory.push(`/trials/${this.props.params.id}/question/${id}`)
  }

  newObservation () {
    browserHistory.push(`/trials/${this.props.params.id}/select-observation`)
  }

  handleFindObservation () {
    let list = [...this.state.listOfTrials]
    let index = _.findIndex(list, { 'id': parseInt(this.props.params.id) })
    if (index !== -1 && !list[index].initHasAnswer && list[index].initHasAnswer !== null) {
      let change = { ...this.state }
      change['selectedObj'] = { id: list[index].initId }
      change['showModal'] = true
      this.setState(change)
    }
  }

  handleShowModal () {
    this.setState({ showModal: !this.state.showModal })
  }

  handleOpen (id) {
    this.setState({
      open: true,
      answerId: id
    })
  }

  handleClose = () => {
    this.setState({ open: false })
  }

  handleRemoveAnswer () {
    if (this.state.answerRemove !== '') {
      this.props.removeAnswer(this.state.answerId)
      this.handleClose()
    }
  }

  handleChangeTextField (name, e) {
    let change = { ...this.state }
    change[name] = e.target.value
    this.setState(change, () => this.validateTextField(name))
  }

  validateTextField (name) {
    let change = { ...this.state }
    let error = name + 'ErrorText'
    if (change[name] !== '') {
      change[error] = ''
    } else {
      change[error] = 'This field must be filled out!'
    }
    this.setState(change)
  }

  handleCommentModalClose = () => {
    this.setState({ commentModal: false })
  }

  handleCommentModal (object) {
    this.setState({
      selectedObject: object,
      comment: object.comment,
      commentModal: true
    })
  }

  handleAddComment () {
    if (this.state.comment) {
      this.setState({ commentModal: false },
        () => this.props.editComment(
          this.state.selectedObject.id,
          { comment: this.state.comment }
        ))
    }
  }

  render () {
    const actions = [
      <FlatButton
        label='Cancel'
        primary
        onTouchTap={this.handleClose}
      />,
      <FlatButton
        label='Delete'
        primary
        keyboardFocused
        onTouchTap={() => this.handleRemoveAnswer()}
      />
    ]
    const commentActions = [
      <FlatButton
        label='Cancel'
        primary
        onTouchTap={this.handleCommentModalClose}
      />,
      <FlatButton
        label='Send'
        primary
        keyboardFocused
        onTouchTap={() => this.handleAddComment()}
      />
    ]
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='view-trials-container'>
            <div className='trial-title'>
              <div>Trial: {this.state.trialSession.trialName}</div>
            </div>
            <div className='trials-header'>
              <div>List of events</div>
              <DateComponent />
            </div>
            <Accordion>
              {this.state.viewTrials.map((object) => {
                return (
                  <AccordionItem key={object.id} title={
                    <h3 className={'react-sanfona-item-title cursor-pointer' +
                      ((object.type !== 'EVENT') ? ' observation' : ' message')}>
                      {object.name}
                      <div className={'time'}>
                        {moment(object.time, 'YYYY-MM-DDThh:mmZ').format('DD/MM/YYYY kk:mm:ss')}
                      </div>
                    </h3>} expanded={false}>
                    <div>
                      <p>{object.description}</p>
                      { object.type !== 'EVENT' &&
                      <div style={{ display: 'table', margin: '0 auto' }}>
                        <RaisedButton
                          buttonStyle={{ width: '200px' }}
                          backgroundColor='#244C7B'
                          labelColor='#FCB636'
                          label={object.comment ? 'Edit comment' : 'Comment'}
                          secondary
                          onClick={this.handleCommentModal.bind(this, object)} />
                        <RaisedButton
                          buttonStyle={{ width: '200px' }}
                          style={{ margin: '0 10px' }}
                          backgroundColor='#244C7B'
                          labelColor='#FCB636'
                          onClick={this.viewEvent.bind(this, object.id)}
                          label='View' />
                        <RaisedButton
                          buttonStyle={{ width: '200px' }}
                          backgroundColor='red'
                          labelColor='#fff'
                          label='Remove'
                          onClick={() => this.handleOpen(object.id)} />
                      </div>
                      }
                      {object.comment &&
                      <Paper style={styles.paper} zDepth={1}>
                        <p style={{ fontSize: 18, fontWeight: 'bold' }}>Comment:</p>
                        <p style={{ fontStyle: 'italic' }}>{object.comment}</p>
                      </Paper>}
                    </div>
                  </AccordionItem>
                )
              })}
            </Accordion>
            <FloatingActionButton onClick={this.newObservation.bind(this)} className={'observation-add'} secondary>
              <ContentAdd />
            </FloatingActionButton>
          </div>
          <SummaryOfObservationModal
            mode={'usermodal'}
            show={this.state.showModal}
            object={this.state.selectedObj}
            handleShowModal={this.handleShowModal.bind(this)}
            params={this.state.id}
            observationForm={this.props.observationForm}
            downloadFile={this.props.downloadFile}
            sendObservation={this.props.sendObservation} />
        </div>
        <Dialog
          title='Do you want to remove this answer?'
          actions={actions}
          modal={false}
          open={this.state.open}
          onRequestClose={this.handleClose}>
          The answer will be permanently deleted. Please provide, why do you want to remove it:
            <TextField
              value={this.state.answerRemove}
              hintText='enter the answer'
              errorText={this.state.answerRemoveErrorText}
              multiLine
              fullWidth
              rows={3}
              onChange={this.handleChangeTextField.bind(this, 'answerRemove')} />
        </Dialog>
        <Dialog
          title='Comment'
          actions={commentActions}
          modal={false}
          open={this.state.commentModal}
          onRequestClose={this.handleCommentModalClose}>
          <TextField
            value={this.state.comment}
            hintText='enter the comment'
            errorText={this.state.commentErrorText}
            multiLine
            fullWidth
            rows={5}
            onChange={this.handleChangeTextField.bind(this, 'comment')} />
        </Dialog>
      </div>
    )
  }
}

export default ViewTrials
