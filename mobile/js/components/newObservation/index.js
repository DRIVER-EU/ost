import React, { Component } from 'react';
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

const dataArr = [
  {
    category: 'Communication between teams A and B',
    description: 'Good communication is defined as information sharing in an effective manner by the sender and correct information reception by the receiver.Poor communication shows a lack in effectiveness, completeness and or incorrect understanding by the receiver.',
    id: 1,
    label: 'Communication between teams A and B',
  },
  {
    category: 'Situational awareness',
    description: 'Good situational awareness is observed if location based information is well handled and no mistakes are made between (pieces of) information received.Poor situational awareness is observed when location based information is missed, mixed up, incomplete or incorrectly handled.',
    id: 1,
    label: 'Situational awareness',
  },
  {
    category: 'Incident location shared',
    description: 'Note down this observation if incident source location is shared.',
    id: 2,
    label: 'Incident location shared',
  },
  {
    category: 'COP-tool use',
    description: 'Note down this observation if the COP-tool is used.',
    id: 3,
    label: 'COP-tool use',
  },
];

class NewObservation extends Component {
  static navigationOptions = {
    header: null,
  };

  static propTypes = {
    navigation: React.PropTypes.func,
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
