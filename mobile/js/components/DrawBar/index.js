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
import firebase from 'firebase';

const routes = [{ name: 'Home', display: 'Selected Trial' }];

export default class DrawBar extends React.Component {
  static navigationOptions = {
    header: null,
  };

  logOut = () => {
    firebase.auth().signOut();
    AsyncStorage.removeItem('loggedIn');
    this.props.navigation.navigate('Login');
  }

  render() {
    return (
      <Container>
        <Content>
          <Image
            source={{
              uri: 'https://github.com/GeekyAnts/NativeBase-KitchenSink/raw/react-navigation/img/drawer-cover.png',
            }}
            style={{
              height: 120,
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
            >
              <Image
                square
                style={{ height: 80, width: 70 }}
                source={{
                  uri: 'https://github.com/GeekyAnts/NativeBase-KitchenSink/raw/react-navigation/img/logo.png',
                }}
              />
              <View
                style={{ top: 10, right: 10, position: 'absolute' }}
              >
                <Button
                  transparent
                  onPress={this.logOut}
                >
                  <Icon active name="power" style={{ color: '#00497E' }} />
                </Button>
              </View>
            </TouchableOpacity>
          </Image>
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
