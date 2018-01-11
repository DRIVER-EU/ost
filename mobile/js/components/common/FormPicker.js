import React, { Component } from 'react';
import { TouchableOpacity,
  View,
  Modal,
  TouchableWithoutFeedback,
  Text,
  StyleSheet,
  Platform,
  Picker,
  TextInput,
 } from 'react-native';

class FormPicker extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modalVisible: false,
    };
  }

  goNext() {
    this.setState({ modalVisible: false });
    this.props.showButton();
  }

  render() {
    if (Platform.OS === 'android') {
      return (
        <Picker
          selectedValue={this.props.value}
          onValueChange={this.props.onValueChange}
        >
          {this.props.items.map((i, index) => (
            <Picker.Item key={index} label={i.label} value={i.value} />
            ))}
        </Picker>
      );
    }
    const selectedItem = this.props.items.find(
          i => i.value === this.props.value
        );
    const selectedLabel = selectedItem ? selectedItem.label : this.props.items[0].label;

    return (
      <View style={styles.inputContainer}>
        <TouchableOpacity
          onPress={() => this.setState({ modalVisible: true })}
        >
          <TextInput
            style={styles.input}
            editable={false}
            placeholder="Select trial"
            onChangeText={(searchString) => {
              this.setState({ searchString });
            }}
            value={selectedLabel}
          />
        </TouchableOpacity>
        <Modal
          animationType="slide"
          transparent
          visible={this.state.modalVisible}
        >
          <TouchableWithoutFeedback
            onPress={() => this.setState({ modalVisible: true })}
          >
            <View style={styles.modalContainer}>
              <View style={styles.modalContent}>
                <Text
                  style={{ color: '#00497E' }}
                  onPress={() => this.goNext()}
                >
                  Done
                </Text>
              </View>
              <View
                onStartShouldSetResponder={evt => true}
                onResponderReject={(evt) => {}}
              >
                <Picker
                  selectedValue={this.props.value}
                  onValueChange={this.props.onValueChange}
                >
                  {this.props.items.map((i, index) => (
                    <Picker.Item
                      key={index}
                      label={i.label}
                      value={i.value}
                    />
                      ))}
                </Picker>
              </View>
            </View>
          </TouchableWithoutFeedback>
        </Modal>
      </View>
    );
  }
  }

const styles = StyleSheet.create({
  container: {
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
  inputContainer: {
    ...Platform.select({
      ios: {
        borderBottomColor: 'gray',
        borderBottomWidth: 1,
      },
    }),
  },
  input: {
    height: 40,
  },
  modalContainer: {
    flex: 1,
    justifyContent: 'flex-start',
    paddingTop: 60,
  },
  modalContent: {
    justifyContent: 'flex-start',
    flexDirection: 'row',
    padding: 4,
    backgroundColor: '#ececec',
  },
});

export default FormPicker;
