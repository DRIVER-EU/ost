import { connect } from 'react-redux'
import { addNewStage } from './../modules/newstage'
import { getTrialDetail } from '../../TrialDetail/modules/trialdetail'
import NewStageWrapper from '../components/NewStage'

const mapDispatchToProps = {
  addNewStage,
  getTrialDetail
}

const mapStateToProps = (state) => ({
  newStageDetail: state.newStageDetail.newStageDetail,
  trialName: state.trialDetail.trialName

})

export default connect(mapStateToProps, mapDispatchToProps)(NewStageWrapper)
