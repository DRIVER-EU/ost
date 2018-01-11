
const React = require('react-native');

const { StyleSheet, Dimensions, Platform } = React;

const deviceHeight = Dimensions.get('window').height;

export default {
  buttonAddContainer: {
    bottom: 20,
    right: 20,
    backgroundColor: '#00497E',
    height: 58,
    width: 58,
    borderRadius: 58 / 2,
    position: 'absolute',
    shadowColor: '#000',
    shadowOffset: { width: 5, height: 5 },
    shadowOpacity: 0.5,
    shadowRadius: 5,
    elevation: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  buttonAddStyle: {
    ...Platform.select({
      ios: {
        fontSize: 28,
      },
      android: {
        fontSize: 22,
      },
    }),
    color: 'white',
  },
};
