import React, { Component } from 'react';
import { AsyncStorage } from 'react-native';
import { connect } from 'react-redux';
import firebase from 'firebase';
import {
  Container,
  Header,
  Title,
  Content,
  Button,
  Icon,
  Left,
  Body,
  Right,
} from 'native-base';
import { Grid, Row } from 'react-native-easy-grid';
import { DrawerNavigator, NavigationActions } from 'react-navigation';
import BlankPage2 from '../blankPage2';
import FormList from '../formList';
import NewObservation from '../newObservation';
import FormObservation from '../formObservation';
import DrawBar from '../DrawBar';
import { setIndex } from '../../actions/list';
import { openDrawer } from '../../actions/drawer';
import styles from './styles';
import SingleCard from '../common/SingleCard';


const dataArr = [
  {
    label: 'A Trial XYZ',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
  },
  {
    label: 'B Trial One',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
  },
  {
    label: 'C Trial Two',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
  },
  {
    label: 'Trial Three',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum. Donec sagittis, justo in porta porttitor, tellus tellus efficitur nulla',
  },
];

class Home extends Component {
  static navigationOptions = {
    header: null,
  };

  logOut = () => {
    firebase.auth().signOut();
    AsyncStorage.removeItem('loggedIn');
    DrawerNav.dispatch(
      NavigationActions.reset({
        index: 0,
        actions: [NavigationActions.navigate({ routeName: 'Home' })],
      })
    );
    DrawerNav.goBack();
  }

  render() {
    return (
      <Container style={styles.container}>
        <Header style={{ backgroundColor: '#00497E' }}>
          <Left>
            <Button
              transparent
              onPress={() => DrawerNav.navigate('DrawerOpen')}
            >
              <Icon active name="menu" />
            </Button>
          </Left>
          <Body>
            <Title>Selected Trial</Title>
          </Body>
          <Right>
            <Button
              transparent
              onPress={this.logOut}
            >
              <Icon active name="power" />
            </Button>
          </Right>
        </Header>
        <Content>
          <SingleCard goNext={() => this.props.navigation.navigate('FormList')} data={dataArr} />
        </Content>
      </Container>
    );
  }
}

function bindAction(dispatch) {
  return {
    setIndex: index => dispatch(setIndex(index)),
    openDrawer: () => dispatch(openDrawer()),
  };
}
const mapStateToProps = state => ({
  name: state.user.name,
  list: state.list.list,
});

const HomeSwagger = connect(mapStateToProps, bindAction)(Home);
const DrawNav = DrawerNavigator(
  {
    Home: { screen: HomeSwagger },
    BlankPage2: { screen: BlankPage2 },
    FormList: { screen: FormList },
    NewObservation: { screen: NewObservation },
    FormObservation: { screen: FormObservation },
  },
  {
    contentComponent: props => <DrawBar {...props} />,
  }
);
const DrawerNav = null;
DrawNav.navigationOptions = ({ navigation }) => {
  DrawerNav = navigation;
  return {
    header: null,
  };
};

export default DrawNav;
