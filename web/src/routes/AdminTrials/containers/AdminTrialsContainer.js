import { connect } from 'react-redux'
import AdminTrials from '../components/AdminTrials'
import { getMessages, sendMessage, getObservation } from './../modules/admin_trials'

const mapDispatchToProps = {
  getMessages,
  sendMessage,
  getObservation
}

const mapStateToProps = (state) => ({
  messages: state.adminTrials.messages,
  isSendMessage: state.adminTrials.isSendMessage,
  observation: state.adminTrials.observation
})

export default connect(mapStateToProps, mapDispatchToProps)(AdminTrials)
