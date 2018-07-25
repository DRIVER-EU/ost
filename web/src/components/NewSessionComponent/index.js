import { connect } from 'react-redux'
import NewSessionComponent from './component/NewSessionComponent'

const mapDispatchToProps = {
}

const mapStateToProps = (state) => ({
  name: state.menu.name
})

export default connect(mapStateToProps, mapDispatchToProps)(NewSessionComponent)
