import PropTypes from 'prop-types'
import React, { Component } from 'react'
import ReactTable from 'react-table'
import './ActiveSessions.scss'
import SessionDetail from './SessionDetail'

class ActiveSessions extends Component {
  constructor (props) {
    super(props)
    this.state = {
      activeSessionsList: this.props.activeSessionsList,
      sessionDetail: this.props.sessionDetail,
      selectSession: null
    }
  }
  static propTypes = {
    getActiveSessions: PropTypes.func,
    activeSessionsList: PropTypes.array,
    sessionDetail: PropTypes.array,
    getSessionDetail: PropTypes.func
  };
  componentDidMount () {
    if (this.props.getActiveSessions) {
      this.props.getActiveSessions()
    }
  }
  componentWillReceiveProps (nextProps) {
    this.setState({
      activeSessionsList: nextProps.activeSessionsList,
      sessionDetail: nextProps.sessionDetail
    })
  }
  render () {
    const sessionColumns = [
      {
        Header: 'Active Sessions',
        columns: [
          {
            Header: 'Id',
            accessor: 'id',
            width: 100,
            style: { textAlign: 'right' }
          },
          {
            Header: 'Name',
            accessor: 'trialSessionName',
            style: { textAlign: 'left' }
          },
          {
            Header: 'Actual Stage Name',
            accessor: 'lastTrialStage',
            style: { textAlign: 'left' }
          },
          {
            Header: 'Manual',
            accessor: 'manualStageChange',
            Cell: props => (
              <span className='manual'>{props.value ? 'yes' : 'no'}</span>
            ),
            style: { textAlign: 'left' }
          },
          {
            Header: 'Status',
            accessor: 'status',
            style: { textAlign: 'left' }
          }
        ]
      }
    ]
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='stage-container'>
            <div className='table__wrapper'>
              <ReactTable
                data={this.state.activeSessionsList}
                columns={sessionColumns}
                multiSort
                showPagination={false}
                defaultPageSize={500}
                minRows={0}
                getTdProps={(state, rowInfo) => {
                  if (rowInfo && rowInfo.row) {
                    return {
                      onClick: e => {
                        this.setState({
                          selectSession: rowInfo.original
                        })
                      },
                      onDoubleClick: e => {
                        this.viewUserRole()
                      },
                      style: {
                        background: this.state.selectSession
                          ? rowInfo.original.id === this.state.selectSession.id
                            ? '#e5e5e5'
                            : ''
                          : '',
                        cursor: 'pointer'
                      }
                    }
                  }
                }}
              />
            </div>
            {this.state.selectSession && (
              <SessionDetail
                id={this.state.selectSession.id}
                getSessionDetail={this.props.getSessionDetail}
                sessionDetail={this.state.sessionDetail}
              />
            )}
          </div>
        </div>
      </div>
    )
  }
}
export default ActiveSessions
