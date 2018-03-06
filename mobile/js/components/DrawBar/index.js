import React from 'react';
import { Image, TouchableOpacity, AsyncStorage, View } from 'react-native';
import {
  Button,
  Text,
  Container,
  List,
  ListItem,
  Content,
  Icon,
} from 'native-base';

const routes = [{ name: 'Home', display: 'Selected Trial' }];
const background = require('../../../images/driver-logo.png');

export default class DrawBar extends React.Component {
  static navigationOptions = {
    header: null,
  };

  logOut = () => {
    // AsyncStorage.removeItem('loggedIn');
    AsyncStorage.removeItem('selectUser');
    AsyncStorage.removeItem('role');
    this.props.navigation.navigate('Login');
  }

  render() {
    return (
      <Container>
        <Content>
          <View>
            <Image
              source={background}
              style={{
                height: 120,
                width: null,
                flex: 1,
                marginRight: 40,
                alignSelf: 'stretch',
                justifyContent: 'center',
                alignItems: 'center',
              }}
            >
              <TouchableOpacity
                style={{
                  height: 120,
                  alignSelf: 'stretch',
                  justifyContent: 'center',
                  alignItems: 'center',
                }}
                onPress={() => this.props.navigation.navigate('DrawerClose')}
              />
            </Image>
          </View>
          <View
            style={{ top: 0, right: 0, position: 'absolute' }}
          >
            <Button
              transparent
              onPress={this.logOut}
            >
              <Icon active name="power" style={{ color: '#00497E' }} />
            </Button>
          </View>
          <List
            dataArray={routes}
            renderRow={data => (
              <ListItem
                button
                onPress={() => this.props.navigation.navigate(data.name)}
              >
                <Text>{data.display}</Text>
              </ListItem>
              )}
          />
        </Content>
      </Container>
    );
  }
}
