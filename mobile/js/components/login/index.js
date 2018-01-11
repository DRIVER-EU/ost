import React, { Component } from 'react';
import { Image, Text, AsyncStorage, View } from 'react-native';
import firebase from 'firebase';
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
    navigation: React.PropTypes.object,
  };
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
      error: '',
      loading: false,
      loggedIn: null,
    };
  }

  componentWillMount = () => {
    firebase.auth().onAuthStateChanged((user) => {
      if (user) {
        this.setState({ loggedIn: true });
        this.props.navigation.navigate('Home');
      } else {
        this.setState({ loggedIn: false });
      }
      AsyncStorage.setItem('loggedIn', JSON.stringify(this.state.loggedIn));
    });
  }

  onButtonPress = () => {
    const { email, password } = this.state;
    this.setState({ error: '', loading: true });
    firebase.auth().signInWithEmailAndPassword(email, password)
    .then(this.onLoginSuccess.bind(this))
    .catch(() => {
      firebase.auth().createUserWithEmailAndPassword(email, password)
            .then(this.onLoginSuccess.bind(this))
            .catch(this.onLoginFail.bind(this));
    });
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
    return (
      <Container>
        <Header style={{ backgroundColor: '#00497E' }} />
        <Content style={{ backgroundColor: '#FBFAFA' }}>
          <View style={{ flex: 1, flexDirection: 'column', justifyContent: 'space-between' }} >
            <Image source={background} style={styles.shadow} />
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
          </View>
        </Content>
      </Container>
    );
  }
}

export default Login;
