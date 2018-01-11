import React, { Component } from 'react';
import { Text } from 'react-native';
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
import SingleCard from '../common/SingleCard';
import styles from './styles';

const dataArr = [
  {
    label: 'Observation One',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
  },
  {
    label: 'Observation Two',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
  },
  {
    label: 'Observation Three',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
  },
  {
    label: 'Observation Four',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
  },
  {
    label: 'Observation Five',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
  },
  {
    label: 'Observation Six',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
  },
  {
    label: 'Observation Seven',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
  },
  {
    label: 'Observation Eight',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
  },
];

class NewObservation extends Component {
  static navigationOptions = {
    header: null,
  };

  render() {
    return (
      <Container>
        <Header style={{ backgroundColor: '#00497E' }}>
          <Left>
            <Button transparent onPress={() => this.props.navigation.navigate('BlankPage2')}>
              <Icon name="ios-arrow-back" />
            </Button>
          </Left>
          <Body>
            <Title>New Observation</Title>
          </Body>
          <Right />
        </Header>
        <Content>
          <SingleCard goNext={() => this.props.navigation.navigate('FormObservation')} data={dataArr} />
        </Content>
      </Container>
    );
  }
}

export default NewObservation;
