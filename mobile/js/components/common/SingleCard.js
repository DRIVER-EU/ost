import React, { Component } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  AsyncStorage,
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

class SingleCard extends Component {

  static propTypes = {
    data: React.PropTypes.Array,
    goNext: React.PropTypes.func,
  };

  goNext(i, title, description) {
    if (this.props.data[i].id !== undefined) {
      AsyncStorage.setItem('numberOfQuestion', JSON.stringify(this.props.data[i].id));
      AsyncStorage.setItem('title', JSON.stringify(title));
      AsyncStorage.setItem('description', JSON.stringify(description));
    }
    if (this.props.goNext) {
      return this.props.goNext();
    }
    return null;
  }

  render() {
    return (
      <View>
        {this.props.data.map((data, i) => (
          <TouchableOpacity key={i} onPress={this.goNext.bind(this, i, data.label, data.description)}>
            <CardSection>
              <View style={styles.thumbnailContainerStyle}>
                <Text style={styles.thumnailStyle}>{data.label.substring(0, 1)}</Text>
              </View>
              {data.date &&
                <View style={styles.dateTextStyle}>
                  <Text style={{ fontSize: 12 }}>
                    {data.date}
                  </Text>
                </View>}
              <View style={styles.headerContentStyle}>
                <Text style={styles.headerTextStyle}>
                  {data.label}
                </Text>
                <Text style={styles.subHeaderTextStyle}>
                  {data.description}
                </Text>
              </View>
            </CardSection>
          </TouchableOpacity>
            ))}
      </View>
    );
  }
}

export default SingleCard;
