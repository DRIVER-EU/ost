import React, { Component } from 'react';
import { Text, View, LayoutAnimation } from 'react-native';
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
  CheckBox,
  Radio,
  ListItem,
} from 'native-base';
import DatePicker from 'react-native-datepicker';
import moment from 'moment';
import styles from './styles';

class FormObservation extends Component {
  static navigationOptions = {
    header: null,
  };

  constructor() {
    super();
    this.state = {
      date: new Date(),
      radioOne: false,
      radioTwo: false,
      radioThree: false,
      radioFour: false,
      checkBoxOne: false,
      checkBoxTwo: false,
      checkBoxAll: false,
    };
  }

  componentWillUpdate = () => {
    LayoutAnimation.spring();
  }

  returnDate = () => moment(this.state.date.getTime()).format('DD/MM/YYYY h:mm a')

  checkAll() {
    this.setState({
      checkBoxAll: !this.state.checkBoxAll,
      checkBoxOne: !this.state.checkBoxAll,
      checkBoxTwo: !this.state.checkBoxAll,
    });
  }

  render() {
    return (
      <Container style={{ paddingBottom: 20, backgroundColor: 'white' }}>
        <Header style={{ backgroundColor: '#00497E' }}>

          <Left style={{ flex: 1 }}>
            <Button transparent onPress={() => this.props.navigation.navigate('NewObservation')}>
              <Icon name="ios-arrow-back" />
            </Button>
          </Left>

          <Body style={{ flex: 4 }}>
            <Title>{moment(new Date().getTime()).format('DD/MM/YYYY h:mm')}</Title>
          </Body>

          <Right style={{ flex: 1 }} />
        </Header>

        <Content style={{ backgroundColor: 'white' }}>
          <View style={styles.titleObservationContainer}>
            <Text style={styles.titleObservation}>Behaviour of team 1</Text>
          </View>
          <View style={styles.desObservationContainer}>
            <Text style={styles.desObservation}>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin consequat rutrum. Etiam ut libero sagittis, vestibulum enim in, sollicitudin ipsum.</Text>
          </View>
          <View style={styles.titleObservationContainer}>
            <Text style={styles.titleObservation}>When:</Text>
          </View>
          <View style={styles.desObservationContainer}>
            <View style={{ flexDirection: 'row', justifyContent: 'flex-start' }}>
              <DatePicker
                style={{ width: 250 }}
                date={this.state.date}
                mode="datetime"
                placeholder="select date"
                format="DD/MM/YYYY h:mm a"
                confirmBtnText="Confirm"
                cancelBtnText="Cancel"
                customStyles={{
                  dateIcon: {
                    position: 'absolute',
                    left: 0,
                    top: 4,
                    marginLeft: 0,
                  },
                  dateInput: {
                    marginLeft: 36,
                  },
                  btnTextConfirm: {
                    color: '#00497E',
                  },
                }}
                onDateChange={(date) => { this.setState({ date }); }}
              />
              <Button
                style={{ height: 38 }}
                onPress={() => this.setState({ date: new Date() })}
                light
              >
                <Text>Now</Text>
              </Button>
            </View>

          </View>
          <View style={styles.titleObservationContainer}>
            <Text style={styles.titleObservation}>Who:</Text>
          </View>
          <View style={{ paddingLeft: 15, paddingRight: 15 }}>
            <ListItem onPress={() => this.setState({ checkBoxOne: !this.state.checkBoxOne })}>
              <CheckBox
                color={'#00497E'}
                checked={this.state.checkBoxOne}
              />
              <Body>
                <Text style={{ paddingLeft: 10 }} >Participant 1</Text>
              </Body>
            </ListItem>
            <ListItem onPress={() => this.setState({ checkBoxTwo: !this.state.checkBoxTwo })}>
              <CheckBox
                color={'#00497E'}
                checked={this.state.checkBoxTwo}
              />
              <Body>
                <Text style={{ paddingLeft: 10 }} >Participant 2</Text>
              </Body>
            </ListItem>
            <ListItem onPress={() => this.checkAll()}>
              <CheckBox
                color={'#00497E'}
                checked={this.state.checkBoxAll}
              />
              <Body>
                <Text style={{ paddingLeft: 10 }} >All</Text>
              </Body>
            </ListItem>
          </View>
          <View style={styles.titleObservationContainer}>
            <Text style={styles.titleObservation}>What:</Text>
          </View>
          <View style={styles.desObservationContainer}>
            <Text style={styles.desObservation}>
              Some additional description...
            </Text>
          </View>
          <View style={{ paddingLeft: 15, paddingRight: 15 }}>
            <ListItem onPress={() => this.setState({ radioOne: !this.state.radioOne })}>
              <Text>Radio button one</Text>
              <Right>
                <Radio selected={this.state.radioOne} />
              </Right>
            </ListItem>
            <ListItem onPress={() => this.setState({ radioTwo: !this.state.radioTwo })}>
              <Text>Radio button two</Text>
              <Right>
                <Radio selected={this.state.radioTwo} />
              </Right>
            </ListItem>
            <ListItem onPress={() => this.setState({ radioThree: !this.state.radioThree })}>
              <Text>Radio button three</Text>
              <Right>
                <Radio selected={this.state.radioThree} />
              </Right>
            </ListItem>
            <ListItem onPress={() => this.setState({ radioFour: !this.state.radioFour })}>
              <Text>Radio button four</Text>
              <Right>
                <Radio selected={this.state.radioFour} />
              </Right>
            </ListItem>
          </View>
          <View style={styles.titleObservationContainer}>
            <Button
              full
              style={{ backgroundColor: '#00497E' }}
              onPress={() => this.props.navigation.navigate('BlankPage2')}
            >
              <Text style={{ color: '#FDB913' }}>Make observation</Text>
            </Button>
          </View>
        </Content>
      </Container>
    );
  }
}

export default FormObservation;
