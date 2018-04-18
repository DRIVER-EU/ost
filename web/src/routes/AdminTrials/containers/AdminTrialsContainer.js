import { connect } from 'react-redux'
import AdminTrials from '../components/AdminTrials'
import { getMessages, sendMessage, getObservation, getUsers, getRoles } from './../modules/admin_trials'

const mapDispatchToProps = {
  getMessages,
  sendMessage,
  getObservation,
  getUsers,
  getRoles
}

const mapStateToProps = (state) => ({
  messages: state.adminTrials.messages,
  isSendMessage: state.adminTrials.isSendMessage,
  observation: state.adminTrials.observation,
  usersList: state.adminTrials.usersList,
  rolesList: state.adminTrials.rolesList
})

export default connect(mapStateToProps, mapDispatchToProps)(AdminTrials)
