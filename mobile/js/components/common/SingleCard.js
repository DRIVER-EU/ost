import React, { Component } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
} from 'react-native';
import { CardSection } from './';

class SingleCard extends Component {

  static propTypes = {
    data: React.PropTypes.array,
  };

  goNext() {
    if (this.props.goNext) {
      return () => this.props.goNext();
    }
    return null;
  }

  render() {
    return (
      <View>
        {this.props.data.map((data, i) => (
          <TouchableOpacity key={i} onPress={this.goNext()}>
            <CardSection>
              <View style={styles.thumbnailContainerStyle}>
                <Text style={styles.thumnailStyle}>{data.label.substring(0, 1)}</Text>
              </View>
              <View style={styles.headerContentStyle}>
                <View style={styles.titleDate}>
                  <Text style={styles.headerTextStyle}>
                    {data.label}
                  </Text>
                  {data.date &&
                  <Text style={styles.dateTextStyle}>
                    {data.date}
                  </Text>
                  }
                </View>
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
  },
  headerTextStyle: {
    fontSize: 18,
    flex: 2,
  },
  subHeaderTextStyle: {
    fontSize: 10,
    paddingRight: 65,
  },
  titleDate: {
    flexDirection: 'row',
    justifyContent: 'flex-start',
  },
  dateTextStyle: {
    fontSize: 12,
    flex: 1,
    paddingRight: 65,
  },
};

export default SingleCard;
