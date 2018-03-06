import React, { Component } from 'react';
import { Text, View, LayoutAnimation, TextInput, TouchableOpacity, Slider, AsyncStorage } from 'react-native';
import axios from 'axios';
import {
  Container,
  Header,
  Title,
  Content,
  Button,
  Left,
  Right,
  Body,
  CheckBox,
  Radio,
  ListItem,
  Card,
  CardItem,
} from 'native-base';
import DatePicker from 'react-native-datepicker';
import moment from 'moment';
import Icon from 'react-native-vector-icons/MaterialIcons';
import styles from './styles';

class FormObservation extends Component {
  static navigationOptions = {
    header: null,
  };
  static propTypes = {
    navigation: React.PropTypes.func,
  };


  constructor() {
    super();
    this.state = {
      date: moment(new Date().getTime()).format('DD/MM/YYYY hh:mm'),
      radioOne: false,
      radioTwo: false,
      checkBoxAll: false,
      checkBoxWhoOne: false,
      checkBoxWhoTwo: false,
      showDes: false,
      showVoice: false,
      showPic: false,
      showLoc: false,
      value: 0,
      numberOfQuestion: null,
      title: '',
      description: '',
      descriptionValue: '',
      role: '',
      selectUser: '',
      slider: 0,
    };
  }

  componentDidMount = () => {
    AsyncStorage.getItem('numberOfQuestion').then(value => this.setState({ numberOfQuestion: Number(value) }));
    AsyncStorage.getItem('title').then(value => this.setState({ title: JSON.parse(value) }));
    AsyncStorage.getItem('description').then(value => this.setState({ description: JSON.parse(value) }));
    AsyncStorage.getItem('role').then(value => this.setState({ role: JSON.parse(value) }));
    AsyncStorage.getItem('selectUser').then(value => this.setState({ selectUser: JSON.parse(value) }));
  }

  componentWillUpdate = () => {
    LayoutAnimation.spring();
  }

  checkPerformance(first, second) {
    if (this.state[first]) {
      this.setState({
        [first]: false,
        [second]: false,
      });
    } else {
      this.setState({
        [first]: true,
        [second]: false,
      });
    }
  }

  makeObservation() {
    if (this.state.numberOfQuestion !== 2 && this.state.numberOfQuestion !== 3) {
      const data = {
        name: this.state.selectUser,
        selectUser: this.state.selectUser,
        role: this.state.role,
        dateTime: this.state.date,
        observationType: this.state.title,
        who: this.getParticipant(),
        what: this.checkPerformanceValue(),
        attachment: this.state.descriptionValue,
      };
      axios.post('http://192.168.1.15:8080/api/anonymous/observation', data)
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
      this.props.navigation.navigate('BlankPage2');
    }
  }

  checkPerformanceValue() {
    if (this.state.radioOne && !this.state.radioTwo) {
      return 'Good performance';
    } else if (!this.state.radioOne && this.state.radioTwo) {
      return 'Poor performance';
    }
    return '';
  }

  getParticipant() {
    const { checkBoxAll, checkBoxWhoOne, checkBoxWhoTwo } = this.state;
    switch (true) {
      case (checkBoxAll || (checkBoxWhoOne && checkBoxWhoTwo)) :
        return 'Participant 1, Participant 2';
      case (checkBoxWhoOne && !checkBoxWhoTwo) :
        return 'Participant 1';
      case (checkBoxWhoTwo && !checkBoxWhoOne) :
        return 'Participant 2';
      default :
        return '';
    }
  }

  change(value) {
    this.setState(() => ({
      value: parseFloat(value),
    }));
  }


  checkAll() {
    this.setState({
      checkBoxAll: !this.state.checkBoxAll,
      checkBoxWhoOne: !this.state.checkBoxAll,
      checkBoxWhoTwo: !this.state.checkBoxAll,
    });
  }

  changeSlider(value) {
    this.setState(() => ({
      value: parseFloat(value),
    }));
  }

  render() {
    const { numberOfQuestion } = this.state;
    return (
      <Container style={{ paddingBottom: 20, backgroundColor: 'white' }}>
        <Header style={{ backgroundColor: '#00497E' }}>
          <Left>
            <Button transparent onPress={() => this.props.navigation.navigate('NewObservation')}>
              <Icon style={{ color: 'white' }} size={20} name="arrow-back" />
            </Button>
          </Left>
          <Title style={{ alignSelf: 'center', justifyContent: 'center' }}>{moment(new Date().getTime()).format('DD/MM/YYYY hh:mm')}</Title>
          <Right />
        </Header>
        <Content style={{ backgroundColor: 'white' }}>
          <View style={styles.titleObservationContainer}>
            <Text style={styles.titleObservation}>{this.state.title}</Text>
          </View>
          <View style={styles.desObservContainer}>
            <Text style={styles.desObservation}>
              {this.state.description}
            </Text>
          </View>

          <View style={styles.titleObservationContainer}>
            <Text style={styles.titleObservation}>When:</Text>
          </View>
          <View style={styles.desObservationContainer}>
            <View style={{ flexDirection: 'row', justifyContent: 'flex-start' }}>
              <DatePicker
                style={{ width: 220 }}
                date={this.state.date}
                mode="datetime"
                placeholder="select date"
                format="DD/MM/YYYY hh:mm"
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
                onPress={() => this.setState({ date: moment(new Date().getTime()).format('DD/MM/YYYY hh:mm') })}
                light
              >
                <Text>Now</Text>
              </Button>
            </View>
          </View>
          <View>
            <View style={styles.titleObservationContainer}>
              <Text style={styles.titleObservation}>Who:</Text>
            </View>
            <View style={{ paddingLeft: 15, paddingRight: 15 }}>
              <ListItem
                onPress={() => this.setState({ checkBoxWhoOne: !this.state.checkBoxWhoOne })}
              >
                <CheckBox
                  color={'#00497E'}
                  onPress={() => this.setState({ checkBoxWhoOne: !this.state.checkBoxWhoOne })}
                  checked={this.state.checkBoxAll ? this.state.checkBoxAll : this.state.checkBoxWhoOne}
                />
                <Body>
                  <Text style={{ paddingLeft: 10 }}>Participant 1</Text>
                </Body>
              </ListItem>
              <ListItem
                onPress={() => this.setState({ checkBoxWhoTwo: !this.state.checkBoxWhoTwo })}
              >
                <CheckBox
                  color={'#00497E'}
                  onPress={() => this.setState({ checkBoxWhoTwo: !this.state.checkBoxWhoTwo })}
                  checked={this.state.checkBoxAll ? this.state.checkBoxAll : this.state.checkBoxWhoTwo}
                />
                <Body>
                  <Text style={{ paddingLeft: 10 }} >Participant 2</Text>
                </Body>
              </ListItem>
              <ListItem onPress={() => this.checkAll()}>
                <CheckBox
                  color={'#00497E'}
                  onPress={() => this.checkAll()}
                  checked={this.state.checkBoxAll}
                />
                <Body>
                  <Text style={{ paddingLeft: 10 }} >All</Text>
                </Body>
              </ListItem>
            </View>
          </View>
          {numberOfQuestion === 1 &&
          <View>
            <View style={styles.titleObservationContainer}>
              <Text style={styles.titleObservation}>What:</Text>
            </View>
            <View style={styles.desObservationContainer}>
              <Text style={styles.desObservation}>
                Mark good OR poor and add free text about the communication between teams observed.
              </Text>
            </View>
            <View style={{ paddingLeft: 15, paddingRight: 15 }}>
              <ListItem onPress={() => this.checkPerformance('radioOne', 'radioTwo')}>
                <Text>Good performance</Text>
                <Right>
                  <Radio selected={this.state.radioOne} onPress={() => this.checkPerformance('radioOne', 'radioTwo')} />
                </Right>
              </ListItem>
              <ListItem onPress={() => this.checkPerformance('radioTwo', 'radioOne')}>
                <Text>Poor performance</Text>
                <Right>
                  <Radio selected={this.state.radioTwo} onPress={() => this.checkPerformance('radioTwo', 'radioOne')} />
                </Right>
              </ListItem>
            </View>
            </View>
          }
          {numberOfQuestion === 2 &&
          <View>
            <View style={styles.titleObservationContainer}>
              <Text style={styles.titleObservation}>What:</Text>
            </View>
            <View style={styles.desObservationContainer}>
              <Text style={styles.desObservation}>
                How long did this information sharing take?
              </Text>
            </View>
            <View style={styles.container}>
              <Text style={styles.text}>{this.state.value > 1 ? `${String(this.state.value)} minutes` : `${String(this.state.value)} minute` }</Text>
              <Slider
                step={1}
                maximumValue={60}
                onValueChange={this.changeSlider.bind(this)}
                value={this.state.slider}
              />
            </View>
            <View style={styles.desObservationContainer}>
              <Text style={styles.desObservation}>
                How accurate was the sharing of information?
              </Text>
            </View>
            <View style={{ paddingHorizontal: 20 }}>
              <Card>
                <TextInput
                  placeholder={'enter text'}
                  underlineColorAndroid="rgba(0,0,0,0)"
                  multiline numberOfLines={3}
                  style={{ flex: 1 }}
                />
              </Card>
            </View>
            <View style={styles.desObservationContainer}>
              <Text style={styles.desObservation}>
                Because I observed:
              </Text>
            </View>
            <View style={{ paddingHorizontal: 20 }}>
              <Card>
                <TextInput
                  placeholder={'enter text'}
                  underlineColorAndroid="rgba(0,0,0,0)"
                  multiline numberOfLines={3}
                  style={{ flex: 1 }}
                />
              </Card>
            </View>
            </View>
          }
          {numberOfQuestion === 3 &&
          <View>
            <View style={styles.titleObservationContainer}>
              <Text style={styles.titleObservation}>What:</Text>
            </View>
            <View style={styles.desObservationContainer}>
              <Text style={styles.desObservation}>
                Which COP-tool functionality was used?
              </Text>
            </View>
            <View style={{ paddingHorizontal: 20 }}>
              <CardItem>
                <Body>
                  <View style={{ flexDirection: 'row' }}>
                    <View style={styles.locationContainer}>
                      <Text style={styles.locationLabel}>X:</Text>
                      <TextInput style={styles.locationInput} />
                    </View>
                    <View style={styles.locationContainer}>
                      <Text style={styles.locationLabel}>Y:</Text>
                      <TextInput style={styles.locationInput} />
                    </View>
                  </View>
                  <View style={{ flexDirection: 'row' }}>
                    <View style={styles.locationContainer}>
                      <Text style={styles.locationLabel}>Other:</Text>
                      <TextInput style={styles.locationInput} />
                    </View>
                    <View style={styles.locationContainer}>
                      <Text style={styles.locationLabel}>
                      Here:
                    </Text>
                      <TouchableOpacity style={styles.buttonLocation}>
                        <Icon name="my-location" size={15} />
                        <Text style={{ marginLeft: 5 }}>here</Text>
                      </TouchableOpacity>
                    </View>
                  </View>
                </Body>
              </CardItem>
            </View>
            <View style={styles.desObservationContainer}>
              <Text style={styles.desObservation}>
                What did you observe?
              </Text>
            </View>
            <View style={{ paddingHorizontal: 20 }}>
              <Card>
                <TextInput
                  placeholder={'enter text'}
                  underlineColorAndroid="rgba(0,0,0,0)"
                  multiline numberOfLines={3}
                  style={{ flex: 1 }}
                />
              </Card>
            </View>
            </View>
          }
          <Button
            full
            light style={{ marginBottom: 5, marginTop: 15 }}
            onPress={() => this.setState({ showDes: !this.state.showDes })}
          >
            <Text>Description</Text>
          </Button>
          {this.state.showDes &&
          <Card style={{ padding: 5 }}>
            <TextInput
              placeholder={'enter text'}
              underlineColorAndroid="rgba(0,0,0,0)"
              multiline numberOfLines={3}
              style={{ flex: 1 }}
              onChangeText={descriptionValue => this.setState({ descriptionValue })}
              value={this.state.descriptionValue}
            />
          </Card>}
          <Button
            full
            light
            disabled
            style={{ marginBottom: 5 }}
            onPress={() => this.setState({ showLoc: !this.state.showLoc })}
          >
            <Text>Location</Text>
          </Button>
          {this.state.showLoc && <Card>
            <CardItem>
              <Body>
                <View style={{ flexDirection: 'row' }}>
                  <View style={styles.locationContainer}>
                    <Text style={styles.locationLabel}>X:</Text>
                    <TextInput style={styles.locationInput} />
                  </View>
                  <View style={styles.locationContainer}>
                    <Text style={styles.locationLabel}>Y:</Text>
                    <TextInput style={styles.locationInput} />
                  </View>
                </View>
                <View style={{ flexDirection: 'row' }}>
                  <View style={styles.locationContainer}>
                    <Text style={styles.locationLabel}>Other:</Text>
                    <TextInput style={styles.locationInput} />
                  </View>
                  <View style={styles.locationContainer}>
                    <Text style={styles.locationLabel}>
                      Here:
                    </Text>
                    <TouchableOpacity style={styles.buttonLocation}>
                      <Icon name="my-location" size={15} />
                      <Text style={{ marginLeft: 5 }}>here</Text>
                    </TouchableOpacity>
                  </View>
                </View>
              </Body>
            </CardItem>
          </Card>}
          <Button
            full
            light
            disabled
            style={{ marginBottom: 5 }}
            onPress={() => this.setState({ showPic: !this.state.showPic })}
          >
            <Text>Pictures</Text>
          </Button>
          {this.state.showPic && <Card>
            <CardItem>
              <Body>
                <View style={{ flexDirection: 'row', flexWrap: 'wrap', marginBottom: 10 }}>
                  <View style={{ paddingRight: 5 }}>
                    <Icon name="photo" size={30} />
                  </View>
                  <View style={{ paddingRight: 5 }}>
                    <Icon name="photo-album" size={30} />
                  </View>
                  <View style={{ paddingRight: 5 }}>
                    <Icon name="photo-camera" size={30} />
                  </View>
                  <View style={{ paddingRight: 10 }}>
                    <Icon name="monochrome-photos" size={30} />
                  </View>
                  <View>
                    <TouchableOpacity style={styles.addPhoto}>
                      <Icon name="add" size={15} />
                    </TouchableOpacity>
                  </View>
                </View>
              </Body>
            </CardItem>
          </Card>}
          <Button
            full
            light
            disabled
            style={{ marginBottom: 5 }}
            onPress={() => this.setState({ showVoice: !this.state.showVoice })}
          >
            <Text>Voice</Text>
          </Button>
          {this.state.showVoice && <Card>
            <CardItem>
              <Body>
                <View style={{ flexDirection: 'row', flexWrap: 'wrap', marginBottom: 10 }}>
                  <View style={{ paddingRight: 5 }}>
                    <Icon name="voicemail" size={30} />
                  </View>
                  <View style={{ paddingRight: 10 }}>
                    <Icon name="voicemail" size={30} />
                  </View>
                  <View>
                    <TouchableOpacity style={styles.addPhoto}>
                      <Icon name="add" size={15} />
                    </TouchableOpacity>
                  </View>
                </View>
              </Body>
            </CardItem>
          </Card>}
          <View style={styles.titleObservationContainer}>
            <Button
              full
              style={{ backgroundColor: '#00497E' }}
              onPress={() => this.makeObservation()}
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
