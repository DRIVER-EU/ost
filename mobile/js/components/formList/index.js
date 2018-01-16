import React, { Component } from 'react';
import { connect } from 'react-redux';
import { View, Image } from 'react-native';
import {
  Container,
  Header,
  Title,
  Content,
  Text,
  Button,
  Icon,
  Left,
  Right,
  Body,
} from 'native-base';

import styles from './styles';

const background = require('../../../images/driver-mini-logo.png');

class FormList extends Component {
  static navigationOptions = {
    header: null,
  };
  static propTypes = {
    name: React.PropTypes.string,
    index: React.PropTypes.number,
    list: React.PropTypes.arrayOf(React.PropTypes.object),
    openDrawer: React.PropTypes.func,
  };

  render() {
    const { props: { name, index, list } } = this;
    return (
      <Container style={styles.container}>

        <Header style={{ backgroundColor: '#00497E' }}>
          <Left>
            <Button transparent onPress={() => this.props.navigation.goBack()}>
              <Icon name="ios-arrow-back" />
            </Button>
          </Left>

          <Body>
            <Title>{name ? this.props.name : 'Trial XYZ'}</Title>
          </Body>

          <Right />
        </Header>
        <Content padder>
          <View style={{ flex: 1, flexDirection: 'column', justifyContent: 'space-between' }}>
            <View style={styles.titleContainer}>
              <Text style={styles.title}>
                Observer Support Tool
              </Text>
            </View>
            <View style={styles.textContainer}>
              <Text style={styles.description}>
                Welcome to trial XYZ
              </Text>
            </View>
            <View style={styles.descriptionContainer}>
              <Text style={styles.descriptionText}>
                Information about trial
              </Text>
            </View>
            <View style={styles.descriptionContainer}>
              <Text style={styles.descriptionText}>
                Information about trial
              </Text>
            </View>
            <View style={styles.descriptionContainer}>
              <Text style={styles.descriptionText}>
                Information about trial
              </Text>
            </View>
            <View style={styles.thumbnailContainerStyle}>
              <Image source={background} style={styles.shadow} />
            </View>
            <View style={{ marginBottom: 15, flex: 1 }}>
              <Button
                full
                style={{ backgroundColor: '#00497E' }}
                onPress={() => this.props.navigation.navigate('BlankPage2')}
              >
                <View>
                  <Text style={{ color: '#FDB913' }}>Ok</Text>
                </View>
              </Button>
            </View>
          </View>
        </Content>
      </Container>
    );
  }
}

function bindAction(dispatch) {
  return {
    openDrawer: () => dispatch(openDrawer()),
  };
}

const mapStateToProps = state => ({
  name: state.user.name,
  index: state.list.selectedIndex,
  list: state.list.list,
});

export default connect(mapStateToProps, bindAction)(FormList);
