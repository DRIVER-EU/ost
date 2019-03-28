
const React = require('react-native');

const { StyleSheet } = React;
export default {
  container: {
    backgroundColor: '#FBFAFA',
  },
  row: {
    flex: 1,
    alignItems: 'center',
  },
  text: {
    fontSize: 20,
    marginBottom: 15,
    alignItems: 'center',
    flex: 1,
  },
  mt: {
    marginTop: 18,
  },
  containerPicker: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  content: {
    marginLeft: 15,
    marginRight: 15,
    marginBottom: 5,
    alignSelf: 'stretch',
    justifyContent: 'center',
  },
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
    justifyContent: 'space-around',
  },
  headerTextStyle: {
    fontSize: 18,
    paddingRight: 65,
  },
  subHeaderTextStyle: {
    fontSize: 10,
    paddingRight: 65,
  },
};
