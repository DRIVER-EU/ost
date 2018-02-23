import React, { Component } from 'react';
import {
  View,
  Text,
  AsyncStorage,
  TouchableOpacity,
} from 'react-native';
import { CardSection } from './';


const styles = {
  thumbnailContainerStyle: {
    justifyContent: 'center',
    alignItems: 'center',
    marginLeft: 10,
    marginRight: 10,
    backgroundColor: '#87838B',
    height: 40,
    width: 40,
    borderRadius: 40 / 2,
    borderColor: '#737076',
    borderWidth: 1,
  },
  thumnailStyle: {
    color: 'white',
    fontSize: 18,
  },
  headerContentStyle: {
    flexDirection: 'column',
    justifyContent: 'flex-start',
    alignItems: 'stretch',
    position: 'relative',
    flex: 1,
  },
  headerTextStyle: {
    fontSize: 18,
  },
  subHeaderTextStyle: {
    fontSize: 10,
    paddingRight: 65,
  },
  dateTextStyle: {
    alignItems: 'stretch',
    position: 'absolute',
    top: 5,
    right: 5,
  },
};

class ObservationCard extends Component {

  static propTypes = {
    data: React.PropTypes.Array,
  };

  constructor() {
    super();
    this.state = {
      data: [],
      selectUser: '',
    };
  }

  componentWillMount() {
    this.setState({
      data: this.props.data,
    });
    AsyncStorage.getItem('selectUser').then(value => this.setState({ selectUser: JSON.parse(value) }));
  }


  componentWillReceiveProps(nextProps) {
    if (nextProps.data !== this.props.data) {
      this.setState({
        data: nextProps.data,
      });
    }
  }

  render() {
    return (
      <View>
        {this.state.data.map((data, i) => (
          (data.selectUser === this.state.selectUser || data.selectUser === 'All') &&
          <TouchableOpacity key={i}>
            <CardSection>
              <View style={styles.thumbnailContainerStyle}>
                <Text style={styles.thumnailStyle}>{data.message ? 'M' : 'O'}</Text>
              </View>
              <View style={styles.dateTextStyle}>
                <Text style={{ fontSize: 12 }}>
                  {data.dateTime}
                </Text>
              </View>
              {typeof (data.message) === 'string' &&
              <View style={styles.headerContentStyle}>
                <Text style={styles.headerTextStyle}>
                  Message
                </Text>
                <Text style={styles.subHeaderTextStyle}>
                  {data.message}
                </Text>
              </View>
              }
              {typeof (data.observationType) === 'string' &&
              <View style={styles.headerContentStyle}>
                <Text style={styles.headerTextStyle}>
                  {data.observationType}
                </Text>
                <Text style={styles.subHeaderTextStyle}>
                  {data.attachment}
                </Text>
              </View>
              }
            </CardSection>
          </TouchableOpacity>
            ))}
      </View>
    );
  }
}

export default ObservationCard;
