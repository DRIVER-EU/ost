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
import SingleCard from '../common/SingleCard';
import styles from './styles';


const dataArr = [
  {
    label: 'A Trial XYZ',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
    date: '04/01/2018 12:42',
  },
  {
    label: 'B Trial One',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
    date: '04/01/2018 12:42',
  },
  {
    label: 'C Trial Two',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
    date: '04/01/2018 12:42',
  },
  {
    label: 'Trial Three',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
    date: '04/01/2018 12:42',
  },
  {
    label: 'Trial Four',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
    date: '04/01/2018 12:42',
  },
  {
    label: 'Trial Five',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
    date: '04/01/2018 12:42',
  },
  {
    label: 'A Trial Six',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
    date: '04/01/2018 12:42',
  },
  {
    label: 'B Trial Seven',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
    date: '04/01/2018 12:42',
  },
  {
    label: 'B Trial Seven',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
    date: '04/01/2018 12:42',
  },
  {
    label: 'B Trial Seven',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
    date: '04/01/2018 12:42',
  },
  {
    label: 'B Trial Seven',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
    date: '04/01/2018 12:42',
  },
  {
    label: 'B Trial Seven',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
    date: '04/01/2018 12:42',
  },
  {
    label: 'B Trial Seven',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
    date: '04/01/2018 12:42',
  },
  {
    label: 'B Trial Seven',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
    date: '04/01/2018 12:42',
  },
  {
    label: 'B Trial Seven',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
    date: '04/01/2018 12:42',
  },
];

class BlankPage2 extends Component {
  static navigationOptions = {
    header: null,
  };

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
          <SingleCard data={dataArr} />
        </Content>
        <TouchableOpacity style={styles.buttonAddContainer} onPress={() => this.props.navigation.navigate('NewObservation')}>
          <Icon name="add" style={styles.buttonAddStyle} />
        </TouchableOpacity>
      </Container>
    );
  }
}

export default BlankPage2;
