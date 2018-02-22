import React, { Component } from 'react';
import { TouchableOpacity } from 'react-native';
import {
  Container,
  Header,
  Title,
  Content,
  Button,
  Icon,
  Left,
  Right,
  Body,
} from 'native-base';
import moment from 'moment';
import axios from 'axios';
import _ from 'lodash';
import ObservationCard from '../common/ObservationCard';
import styles from './styles';


const dataArr = [
  {
    label: 'Communication Channel',
    description: 'Communication channel with vessel is created',
    date: '01/01/2018 11:48',
  },
  {
    label: 'Calling services',
    description: 'Organisation  calls other emergency services',
    date: '02/01/2018 12:32',
  },
  {
    label: 'Passenger list',
    description: 'Providing crew and passenger lists',
    date: '03/01/2018 18:31',
  },
  {
    label: 'Sending help',
    description: 'Sending ships and helicopters',
    date: '04/01/2018 00:42',
  },
  {
    label: 'List of delivered',
    description: 'Creating list of delivered people and publication',
    date: '05/01/2018 17:05',
  },
];

class BlankPage2 extends Component {
  static navigationOptions = {
    header: null,
  };

  static propTypes = {
    navigation: React.PropTypes.func,
  };

  constructor() {
    super();
    this.state = {
      answers: [],
      sort: { type: 'dateTime', order: 'asc' },
      changeDataTableSorted: [],
      sortObservation: true,
    };
  }

  componentDidMount() {
    this.getData();
    this.interval = setInterval(() => this.getData(), 2000);
  }

  componentWillUnmount() {
    clearInterval(this.interval);
  }


  getData() {
    const dataArray = [];
    axios.get('http://192.168.1.15:8080/api/anonymous/observation')
      .then((response) => {
        response.data.map(data => (
          dataArray.push(data)
        ));
        axios.get('http://192.168.1.15:8080/api/anonymous/message')
        .then((responseTwo) => {
          responseTwo.data.map(data => (
            dataArray.push(data)
          ));
          this.setState({
            answers: dataArray,
          });
          this.sortFunction();
        })
        .catch((error) => {
          console.log(error);
        });
      })
      .catch((error) => {
        console.log(error);
      });
  }

  sortFunction() {
    const observations = [...this.state.answers];
    for (let i = 0; i < observations.length; i++) {
      observations[i].dateTime = moment(observations[i].dateTime, 'DD/MM/YYYY hh:mm').unix();
    }
    const order = _.orderBy(observations, ['dateTime'], ['asc']);
    for (let i = 0; i < order.length; i++) {
      order[i].dateTime = moment.unix(order[i].dateTime).format('DD/MM/YYYY hh:mm');
    }
    this.setState({
      changeDataTableSorted: order,
    });
  }

  render() {
    return (
      <Container>
        <Header style={{ backgroundColor: '#00497E' }}>

          <Left style={{ flex: 1 }}>
            <Button transparent onPress={() => this.props.navigation.navigate('FormList')}>
              <Icon name="ios-arrow-back" />
            </Button>
          </Left>

          <Body style={{ flex: 4 }}>
            <Title>{moment(new Date().getTime()).format('DD/MM/YYYY h:mm')}</Title>
          </Body>

          <Right style={{ flex: 1 }} />
        </Header>

        <Content>
          <ObservationCard data={this.state.changeDataTableSorted} />
        </Content>
        <TouchableOpacity style={styles.buttonAddContainer} onPress={() => this.props.navigation.navigate('NewObservation')}>
          <Icon name="add" style={styles.buttonAddStyle} />
        </TouchableOpacity>
      </Container>
    );
  }
}

export default BlankPage2;
