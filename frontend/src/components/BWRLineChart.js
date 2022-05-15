import React from 'react';
import { LineChart, Line, YAxis, XAxis, CartesianGrid, Tooltip, Legend } from 'recharts';
import moment from 'moment';

const BWRLineChart = props => {

  function formatXAxis(tickItem) {
    return moment(tickItem).format('DD/MM')
  }

  const CustomTooltip = ({ active, payload, label }) => {
    if (active) {
      return (
        <div className="costum-tooltip" >
          <p >{`${label}`}</p>
          <p style={{color: `${payload[0].color}`}}>{`${payload[0].name} : ${payload[0].value}%`}</p>
        </div>
      );
    }
    return null;
  };

  return (
    <>
      <LineChart data={props.data} width={650} height={250}>
        {/* <text x={300} y={20}>
          <tspan fontSize="15">{props.title}</tspan>
        </text> */}
        <CartesianGrid strokeDasharray="3 3"/>
        <XAxis dataKey={props.XAxis} tickFormatter={formatXAxis}/>
        <YAxis />
        <Tooltip content={<CustomTooltip />}/>
        <Legend />
        <Line name={props.dataKeyName} dataKey={props.dataKey} stroke="#337AB7" activeDot={{ r: 6 }}/>
      </LineChart>
      </>
  );
};

export default BWRLineChart;



