import React from 'react';
import { AreaChart, Area, YAxis, XAxis, CartesianGrid, Tooltip, Legend } from 'recharts';
import moment from 'moment';

const BWAreaChart = props => {

  const formatXAxis = tickItem => {
    return moment(tickItem).format('DD/MM')
  }

  return (
    <>
    <AreaChart data={props.data} width={650} height={500}>
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis dataKey={props.XAxis} tickFormatter={formatXAxis}/>
      <YAxis />
      <Tooltip />
      <Legend />
      <Area name={props.dataKeyName1} dataKey={props.dataKey1} stackId="1" stroke="#148C1B" fill="#148C1B"/>
      <Area name={props.dataKeyName2} dataKey={props.dataKey2} stackId="1" stroke="#D0171F" fill="#D0171F"/> 
    </AreaChart>
    </>
  );
};

export default BWAreaChart;







