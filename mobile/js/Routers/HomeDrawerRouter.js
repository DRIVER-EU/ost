import React, { Component } from 'react';
import Home from '../components/home/';
import BlankPage2 from '../components/blankPage2';
import NewObservation from '../components/newObservation';
import FormList from '../components/formList';
import FormObservation from '../components/formObservation';
import { DrawerNavigator } from 'react-navigation';
import DrawBar from '../components/DrawBar';

export default (DrawNav = DrawerNavigator(
  {
    Home: { screen: Home },
    BlankPage2: { screen: BlankPage2 },
    FormList: { screen: FormList },
    NewObservation: { screen: NewObservation },
    FormObservation: { screen: FormObservation },
  },
  {
    contentComponent: props => <DrawBar {...props} />,
  }
));
