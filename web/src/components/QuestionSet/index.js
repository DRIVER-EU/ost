import { connect } from 'react-redux'
import QuestionDetail from './component/QuestionDetail'

const mapDispatchToProps = {}

const mapStateToProps = (state) => ({
  name: state.menu.name
})

export default connect(mapStateToProps, mapDispatchToProps)(QuestionDetail)
