
const React = require('react-native');

const { StyleSheet, Dimensions } = React;

const deviceHeight = Dimensions.get('window').height;

export default {
  buttonAddContainer: {
    backgroundColor: '#00497E',
    height: 58,
    width: 58,
    borderRadius: 58 / 2,
    borderColor: '#117aff',
    borderWidth: 1,
    position: 'absolute',
    top: deviceHeight - (deviceHeight / 8),
    right: 20,
  },
  buttonAddStyle: {
    color: 'white',
    fontSize: 28,
  },
};
