import { connect } from 'react-redux'
import { addNewTrial } from './../modules/newtrial'
import NewTrialWrapper from '../components/NewTrial'

const mapDispatchToProps = {
  addNewTrial
}

const mapStateToProps = (state) => ({
  newTrialDetail: state.newTrialDetail.newTrialDetail
})

export default connect(mapStateToProps, mapDispatchToProps)(NewTrialWrapper)
