import React, { Component } from 'react';
import { Image, Text, AsyncStorage, View, Dimensions } from 'react-native';
import {
  Container,
  Content,
  Header,
  Button,
} from 'native-base';
import { CardSection, Input, Spinner } from '../common';
import styles from './styles';

const background = require('../../../images/driver-mini-logo.png');


class Login extends Component {
  static navigationOptions = {
    header: null,
  };
  static propTypes = {
    navigation: React.PropTypes.func,
  };
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
      error: '',
      loading: false,
      loggedIn: null,
      heightEl: null,
      widthEl: null,
      log: false,
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
        return 370;
      case (widthEl > heightEl && widthEl > 900) :
        return 350;
      case (heightEl > widthEl && widthEl < 650) :
        return 200;
      case (widthEl > heightEl && widthEl < 900) :
        return 350;
      default:
        return 200;
    }
  }

  onButtonPress = () => {
    const { email, password } = this.state;
    this.setState({ error: '', loading: true });
    if (email === 'test1@test.com' && password === 'test') {
      AsyncStorage.setItem('role', JSON.stringify('Team 1'));
      AsyncStorage.setItem('selectUser', JSON.stringify('Test User 1'));
      this.setState({
        loading: false,
        email: '',
        password: '',
        error: '',
      });
      this.props.navigation.navigate('Home');
    } else if (email === 'test2@test.com' && password === 'test') {
      AsyncStorage.setItem('role', JSON.stringify('Team 1'));
      AsyncStorage.setItem('selectUser', JSON.stringify('Test User 2'));
      this.setState({
        loading: false,
        email: '',
        password: '',
        error: '',
      });
      this.props.navigation.navigate('Home');
    } else {
      this.setState({
        loading: false,
        error: 'Authentication failed.',
      });
    }
  }

  onLoginSuccess() {
    this.setState({
      loading: false,
      email: '',
      password: '',
      error: '',
    });
    this.props.navigation.navigate('Home');
  }

  onLoginFail() {
    this.setState({
      loading: false,
      error: 'Authentication failed.',
    });
  }

  renderButton() {
    return (
      <Button
        full
        style={{ backgroundColor: '#00497E' }}
        onPress={this.onButtonPress}
      >
        <View>
          {this.state.loading ?
            <Spinner size="small" /> :
            <Text style={{ color: '#FDB913' }}>Log in</Text> }
        </View>
      </Button>);
  }

  render() {
    const { widthEl, heightEl } = this.state;
    return (
      <Container style={{ backgroundColor: '#FBFAFA' }}>
        <Header style={{ backgroundColor: '#00497E' }} />
        <Content>
          <Image
            onLayout={this.onLayout.bind(this)}
            source={background}
            style={{
              flex: 1,
              marginTop: 40,
              marginBottom: 40,
              marginHorizontal: widthEl > heightEl && widthEl > 800 ? 150 : 20,
              width: null,
              height: this.imageHeight() }}
          />
          <View style={styles.containerStyle}>
            <CardSection>
              <Input
                label="Email"
                placeholder="user@gmail.com"
                value={this.state.email}
                onChangeText={email => this.setState({ email })}
              />
            </CardSection>
            <CardSection>
              <Input
                label="Password"
                secureTextEntry
                placeholder="password"
                value={this.state.password}
                onChangeText={password => this.setState({ password })}
              />
            </CardSection>
            <Text style={styles.errorTextStyle}>
              {this.state.error}
            </Text>
            <CardSection>
              <View style={{ flex: 1 }}>
                {this.renderButton()}
              </View>
            </CardSection>
          </View>
        </Content>
      </Container>
    );
  }
}

export default Login;
