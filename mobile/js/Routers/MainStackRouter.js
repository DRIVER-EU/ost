import React, { Component } from 'react';
import { StackNavigator } from 'react-navigation';
import Login from '../components/login/';
import Home from '../components/home/';
import BlankPage from '../components/blankPage';
import FormList from '../components/formList';
import HomeDrawerRouter from './HomeDrawerRouter';


HomeDrawerRouter.navigationOptions = ({ navigation }) => ({
  header: null,
});
export default (StackNav = StackNavigator({
  Login: { screen: Login },
  Home: { screen: Home },
  BlankPage: { screen: BlankPage },
  FormList: { screen: FormList },
}));
