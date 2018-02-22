import { connect } from 'react-redux'
import Trials from '../components/Trials'
import { getMessages, sendMessage, getObservation } from './../modules/trials'

const mapDispatchToProps = {
  getMessages,
  sendMessage,
  getObservation
}

const mapStateToProps = (state) => ({
  messages: state.trials.messages,
  isSendMessage: state.trials.isSendMessage,
  observation: state.trials.observation
})

export default connect(mapStateToProps, mapDispatchToProps)(Trials)
