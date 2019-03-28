import React, { Component } from 'react';
import { connect } from 'react-redux';
import { View, Image, Dimensions } from 'react-native';
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
} from 'native-base';

import styles from './styles';

const background = require('../../../images/driver-mini-logo.png');

class FormList extends Component {
  static navigationOptions = {
    header: null,
  };
  static propTypes = {
    name: React.PropTypes.string,
    navigation: React.PropTypes.func,
  };

  constructor(props) {
    super(props);
    this.state = {
      heightEl: null,
      widthEl: null,
    };
  }

  onLayout() {
    const { width, height } = Dimensions.get('window');
    this.setState({
      widthEl: width,
      heightEl: height,
    });
  }

  imageHeight() {
    const { widthEl, heightEl } = this.state;
    switch (true) {
      case (heightEl > widthEl && widthEl > 650) :
        return 350;
      case (widthEl > heightEl && widthEl > 900) :
        return 500;
      case (heightEl > widthEl && widthEl < 650) :
        return 200;
      case (widthEl > heightEl && widthEl < 900) :
        return 350;
      default:
        return 200;
    }
  }

  render() {
    const { props: { name } } = this;
    const { widthEl, heightEl } = this.state;
    return (
      <Container style={styles.container}>
        <Header style={{ backgroundColor: '#00497E' }}>
          <Left>
            <Button transparent onPress={() => this.props.navigation.goBack()}>
              <Icon name="ios-arrow-back" />
            </Button>
          </Left>
          <Title style={{ alignSelf: 'center', justifyContent: 'center', marginHorizontal: 30 }}>{name ? this.props.name : 'Mass rescue operation in sea disaster'}</Title>
          <Right />
        </Header>
        <Content padder>
          <View style={{ flex: 1, flexDirection: 'column' }}>
            <View style={styles.titleContainer}>
              <Text style={styles.title}>
                Observer Support Tool
              </Text>
            </View>
            <View style={styles.textContainer}>
              <Text style={styles.description}>
              Example trial of TNO’s csCOP-tool.
              </Text>
            </View>
            <View style={styles.descriptionContainer}>
              <Text style={styles.descriptionText}>
              Example trial of TNO’s csCOP-tool. Session 1 without and Session 2 with this COP-tool. The scenario is about a tire recycling factory on fire with effects in two regions.
              </Text>
            </View>
            <Image
              onLayout={this.onLayout.bind(this)}
              source={background}
              style={{
                marginVertical: 20,
                height: this.imageHeight(),
                width: null,
                flex: 1 }}
            />
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
