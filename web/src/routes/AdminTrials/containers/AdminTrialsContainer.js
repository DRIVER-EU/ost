import { connect } from 'react-redux'
import AdminTrials from '../components/AdminTrials'
import { getMessages, sendMessage, getObservation,
  getUsers, getRoles, getStages, setStage } from './../modules/admin_trials'
import { getSchemaView } from './../../Question/modules/question'
import { downloadFile } from './../../NewObservation/modules/newobservation'

const mapDispatchToProps = {
  getMessages,
  sendMessage,
  getObservation,
  getUsers,
  getRoles,
  getStages,
  setStage,
  getSchemaView,
  downloadFile
}

const mapStateToProps = (state) => ({
  messages: state.adminTrials.messages,
  isSendMessage: state.adminTrials.isSendMessage,
  observation: state.adminTrials.observation,
  usersList: state.adminTrials.usersList,
  rolesList: state.adminTrials.rolesList,
  stagesList: state.adminTrials.stagesList,
  stageActive: state.adminTrials.stageActive,
  observationForm: state.question.observationForm
})

export default connect(mapStateToProps, mapDispatchToProps)(AdminTrials)
