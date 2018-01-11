import React, { Component } from 'react';
import { StyleSheet } from 'react-native';
import CodePush from 'react-native-code-push';
import firebase from 'firebase';
import { Container, Content, Text, View } from 'native-base';
import Modal from 'react-native-modalbox';
import MainStackRouter from './Routers/MainStackRouter';
import ProgressBar from './components/loaders/ProgressBar';

import theme from './themes/base-theme';

const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: null,
    height: null,
  },
  modal: {
    justifyContent: 'center',
    alignItems: 'center',
  },
  modal1: {
    height: 300,
  },
});

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showDownloadingModal: false,
      showInstalling: false,
      downloadProgress: 0,
    };
  }

  componentWillMount = () => {
    firebase.initializeApp({
      apiKey: 'AIzaSyC00x0IZcNXwUmewYfsRuDW6uN-QgvP5po',
      authDomain: 'authentication-671b3.firebaseapp.com',
      databaseURL: 'https://authentication-671b3.firebaseio.com',
      projectId: 'authentication-671b3',
      storageBucket: 'authentication-671b3.appspot.com',
      messagingSenderId: '579205546055',
    });
  }

  componentDidMount() {
    CodePush.sync(
      { updateDialog: true, installMode: CodePush.InstallMode.IMMEDIATE },
      (status) => {
        switch (status) {
          case CodePush.SyncStatus.DOWNLOADING_PACKAGE:
            this.setState({ showDownloadingModal: true });
            this._modal.open();
            break;
          case CodePush.SyncStatus.INSTALLING_UPDATE:
            this.setState({ showInstalling: true });
            break;
          case CodePush.SyncStatus.UPDATE_INSTALLED:
            this._modal.close();
            this.setState({ showDownloadingModal: false });
            break;
          default:
            break;
        }
      },
      ({ receivedBytes, totalBytes }) => {
        this.setState({ downloadProgress: (receivedBytes / totalBytes) * 100 });
      }
    );
  }

  render() {
    if (this.state.showDownloadingModal) {
      return (
        <Container
          theme={theme}
          style={{ backgroundColor: theme.defaultBackgroundColor }}
        >
          <Content style={styles.container}>
            <Modal
              style={[styles.modal, styles.modal1]}
              backdrop={false}
              ref={(c) => {
                this._modal = c;
              }}
              swipeToClose={false}
            >
              <View
                style={{
                  flex: 1,
                  alignSelf: 'stretch',
                  justifyContent: 'center',
                  padding: 20,
                }}
              >
                {this.state.showInstalling
                  ? <Text
                    style={{
                      color: theme.brandPrimary,
                      textAlign: 'center',
                      marginBottom: 15,
                      fontSize: 15,
                    }}
                  >
                      Installing update...
                    </Text>
                  : <View
                    style={{
                      flex: 1,
                      alignSelf: 'stretch',
                      justifyContent: 'center',
                      padding: 20,
                    }}
                  >
                    <Text
                      style={{
                        color: theme.brandPrimary,
                        textAlign: 'center',
                        marginBottom: 15,
                        fontSize: 15,
                      }}
                    >
                        Downloading update...
                        {' '}
                      {`${parseInt(this.state.downloadProgress, 10)} %`}
                    </Text>
                    <ProgressBar
                      color="theme.brandPrimary"
                      progress={parseInt(this.state.downloadProgress, 10)}
                    />
                  </View>}
              </View>
            </Modal>
          </Content>
        </Container>
      );
    }

    return <MainStackRouter />;
  }
}

export default App;
