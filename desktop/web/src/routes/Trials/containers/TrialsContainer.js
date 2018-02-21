import { connect } from 'react-redux'
import Trials from '../components/Trials'
import { getMessages, sendMessage } from './../modules/trials'

const mapDispatchToProps = {
  getMessages,
  sendMessage
}

const mapStateToProps = (state) => ({
  messages: state.trials.messages,
  isSendMessage: state.trials.isSendMessage
})

export default connect(mapStateToProps, mapDispatchToProps)(Trials)
