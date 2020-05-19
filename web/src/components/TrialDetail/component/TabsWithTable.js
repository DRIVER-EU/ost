import React, { Component } from 'react'
import { Tabs, Tab } from 'material-ui/Tabs'
import ReactTable from 'react-table'
import RaisedButton from 'material-ui/RaisedButton'
import { browserHistory } from 'react-router'
import PropTypes from 'prop-types'

class TabsWithTable extends Component {
  constructor () {
    super()
    this.state = {
      activeTab: 'stage',
      stage: null,
      session: null,
      role: null
    }
  }
  static propTypes = {
    trialId: PropTypes.any,
    sessionList: PropTypes.array,
    stageList: PropTypes.array,
    roleList: PropTypes.array,
    tabs: PropTypes.any
  };
  handleChangeTab = value => {
    this.setState({
      activeTab: value
    })
  };
  viewItem () {
    if (this.state[this.state.activeTab]) {
      browserHistory.push(
        `/trial-manager/trial-detail/${this.props.trialId}/${this.state.activeTab}/${
          this.state[this.state.activeTab].id
        }`
      )
    }
  }
  newItem () {
    browserHistory.push(`/trial-manager/trial-detail/${this.props.trialId}/new${this.state.activeTab}`)
  }
  render () {
    const columns = [
      {
        Header: 'Id',
        accessor: 'id',
        width: 100,
        style: { textAlign: 'right' }
      },
      {
        Header: 'Name',
        accessor: 'name',
        style: { textAlign: 'left' }
      }
    ]
    const sessionColumns = [
      {
        Header: 'Id',
        accessor: 'id',
        width: 100,
        style: { textAlign: 'right' }
      },
      {
        Header: 'Name',
        accessor: 'name',
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
        Cell: props => <span className='manual'>{props.value ? 'yes' : 'no'}</span>,
        style: { textAlign: 'left' }
      },
      {
        Header: 'Status',
        accessor: 'status',
        style: { textAlign: 'left' }
      }
    ]
    return (
      <div>
        <Tabs value={this.state.activeTab} onChange={this.handleChangeTab}>
          <Tab label='Session' value='session'>
            <div>
              <ReactTable
                data={this.props.sessionList}
                columns={sessionColumns}
                multiSort
                showPagination={false}
                minRows={0}
                defaultPageSize={500}
                getTdProps={(state, rowInfo) => {
                  if (rowInfo && rowInfo.row) {
                    return {
                      onClick: e => {
                        this.setState({
                          session: rowInfo.original
                        })
                      },
                      onDoubleClick: e => {
                        this.viewItem()
                      },
                      style: {
                        background: this.state.session
                          ? rowInfo.original.id === this.state.session.id
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
          </Tab>
          <Tab label='Stage' value='stage'>
            <div>
              <ReactTable
                data={this.props.stageList}
                columns={columns}
                multiSort
                defaultPageSize={500}
                showPagination={false}
                minRows={0}
                getTdProps={(state, rowInfo) => {
                  if (rowInfo && rowInfo.row) {
                    return {
                      onClick: e => {
                        this.setState({
                          stage: rowInfo.original
                        })
                      },
                      onDoubleClick: e => {
                        this.viewItem()
                      },
                      style: {
                        background: this.state.stage
                          ? rowInfo.original.id === this.state.stage.id
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
          </Tab>
          <Tab label='Role' value='role'>
            <div>
              <ReactTable
                data={this.props.roleList}
                columns={columns}
                multiSort
                defaultPageSize={500}
                showPagination={false}
                minRows={0}
                getTdProps={(state, rowInfo) => {
                  if (rowInfo && rowInfo.row) {
                    return {
                      onClick: e => {
                        this.setState({
                          role: rowInfo.original
                        })
                      },
                      onDoubleClick: e => {
                        this.viewItem()
                      },
                      style: {
                        background: this.state.role
                          ? rowInfo.original.id === this.state.role.id
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
          </Tab>
        </Tabs>
        {this.props.tabs && (
          <div className='action-btns'>
            <RaisedButton
              buttonStyle={{ width: '200px' }}
              backgroundColor='#244C7B'
              labelColor='#FCB636'
              label='+ New'
              onClick={this.newItem.bind(this)}
              type='Button'
            />
            <RaisedButton
              buttonStyle={{ width: '200px' }}
              backgroundColor={
                this.state[this.state.activeTab] ? '#FCB636' : '#ccc'
              }
              labelColor='#fff'
              label='Edit'
              type='Button'
              disabled={!this.state[this.state.activeTab]}
              onClick={this.viewItem.bind(this)}
            />
          </div>
        )}
      </div>
    )
  }
}
export default TabsWithTable
