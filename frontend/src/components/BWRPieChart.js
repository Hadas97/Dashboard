import React from 'react';
import {  PieChart, Pie, Cell, Tooltip, Legend } from 'recharts';


const BWRPieChart = props => {
  
    const CustomTooltip = ({ active, payload, label }) => {
        debugger
        if (active) {
          return (
            <div className="costum-tooltip" >
              <p style={{color: `${payload[0].color}`}}>{`${payload[0].name}: `}</p>
              <p>{`${payload[0].value}% Tasks Executed`}</p>
            </div>
          );
        }
        return null;
      };

  return (
      <PieChart width={650} height={300} style={{ top: "180px", right: "500px"}}>
      <Pie data={props.data} color="#000000" dataKey={props.dataKey} nameKey={props.nameKey}
            cx="300" cy="200" outerRadius={90} fill="#8884d8" >
          {
              props.data.map((entry, index) => 
              <Cell key={`cell-${index}`} fill={entry.color} />
              )
          }
      </Pie>
      <Tooltip content={<CustomTooltip />} />
      <Legend layout="vetical"/>
      </PieChart>
  );
};

export default BWRPieChart;
