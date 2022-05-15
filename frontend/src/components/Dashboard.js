import React, { useState, useEffect } from 'react';
import './Dashboard.css';
import BWRAreaChart from './BWRAreaChart';
import BWRLineChart from './BWRLineChart';
import BWRPieChart from './BWRPieChart';
import Dropdown from 'react-dropdown';
import Alert from '@mui/material/Alert';
import 'react-dropdown/style.css';
import { getAllTractorTasksDataSum, getTractorTasksDataByTractorId } from '../service/TractorTaskService';
import { getAllTractors, getTractorExecutedTaksksPercent } from '../service/TractorService';

const INIT_HEADER = "Total Tractors Tasks Dashboard";

const Dashboard = () => {

  const [errorsMsg, setErrorsMsgs] = useState();

  const [dashboardHeader, setDashboardHeader] = useState(INIT_HEADER);

  const [tractorTasksData, setTractorTasksData] = useState([]);

  const [allTractorsTasksData, setAllTractorsTasksData] = useState([]);

  const [tractorTasksDataPercent, setTractorTasksDataPercent] = useState([]);

  const [tractors, setTractors] = useState([]);

  const [tractorsExecutedPercent, setTractorsExecutedPercent] = useState([]);

  useEffect(() => {

    getAllTractorTasksDataSum().then(response => {
      if (response.data) {
        let tasks = response.data.reverse();
        setAllTractorsTasksData(tasks);
        setTractorTasksData(tasks);
        calculateTractorsTasksDataPercent(tasks);
      }
    }).catch(e => {
      setErrorsMsgs("server error");
      console.error(e);
    });

    getAllTractors().then(response => {
      if (response.data) {
        mapTractorsData(response.data);
      }
    }).catch(e => {
      setErrorsMsgs("server error");
      console.error(e);
    });

    getTractorExecutedTaksksPercent().then(response => {
      if (response.data) {
        mapTractorsExecutedPercentData(response.data);
      }
    }).catch(e => {
      setErrorsMsgs("server error");
      console.error(e);
    });
    
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const calculateTractorsTasksDataPercent = (tasks) => {
    const tasksExecutedPercent = tasks.map(tasks => {
      return { 'tasksDate': tasks.tasksDate, 'tasksExecutedPercent': calculatePercent(tasks) };
    })
    setTractorTasksDataPercent(tasksExecutedPercent);
  }

  const calculatePercent = (tasks) => {
    return Math.round(tasks.numTasksExecuted / tasks.numTasksPlanned * 100);
  }

  const mapTractorsData = (data) => {
    const tractorsMap = data.map(tractor => {
      return { 'label': tractor.tractorName, 'value': tractor.id };
    })
    tractorsMap.unshift({ 'label': 'All tractors', 'value': 'All' })
    setTractors(tractorsMap);
  }

  const mapTractorsExecutedPercentData = (data) => {
    const tractorsExecutedPercent = data.map(tractor => {
      return { ...tractor, color: "#" + Math.floor(Math.random()*16777215).toString(16) }
    }) 
    setTractorsExecutedPercent(tractorsExecutedPercent);
  }

  const onTractorSelect = (selected) => {
    if (selected.value !== 'All') {
      getTractorTasksDataByTractorId(selected.value).then(response => {
        if (response.data) {
          setTractorTasksData(response.data);
          calculateTractorsTasksDataPercent(response.data);
          setDashboardHeader(selected.label + " Tasks Dashboard")
        } else {
          setErrorsMsgs("No data for selected tractor");
        }
      }).catch(e => {
        setErrorsMsgs("server error");
        console.error(e);
      });
    } else {
      setTractorTasksData(allTractorsTasksData);
      calculateTractorsTasksDataPercent(allTractorsTasksData);
      setDashboardHeader(INIT_HEADER);
    }
  }

  return (
    <div >
      <h2>{dashboardHeader}</h2>
      {errorsMsg && <Alert severity="error">{errorsMsg}</Alert>}
      <Dropdown options={tractors} onChange={onTractorSelect}
        placeholder="Select a tractor" className='tractor-select' />
      <div className='charts-container'>
        <BWRAreaChart data={tractorTasksData} dataKey1={'numTasksExecuted'} dataKey2={'numTasksPlanned'} 
            XAxis={'tasksDate'} dataKeyName1={'Tasks Executed'} dataKeyName2={'Tasks Planned'}/>
        <BWRLineChart data={tractorTasksDataPercent} dataKey={'tasksExecutedPercent'} 
            XAxis={'tasksDate'} dataKeyName={'Tasks Executed Percent'}/>
        <BWRPieChart data={tractorsExecutedPercent} dataKey={'tractorPercent'} nameKey={'tractorName'}/>
      </div>
    </div>
  )
};

export default Dashboard;
