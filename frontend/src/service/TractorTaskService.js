import axios from 'axios';

async function getAllTractorTasksDataSum() {
    return axios({
        method: 'get',
        url: `http://localhost:8080/tractorTasksData/getAllTractorTasksDataSum`,
        headers: { 'content-type': 'application/json' },
        accept: 'application/json',
    });
}

async function getTractorTasksDataByTractorId(tractorId) {
    return axios({
        method: 'get',
        url: `http://localhost:8080/tractorTasksData/getTractorTasksDataByTractorId?tractorId=${tractorId}`,
        headers: { 'content-type': 'application/json' },
        accept: 'application/json',
    });
} 

export {getAllTractorTasksDataSum, getTractorTasksDataByTractorId};

//let url = `${getValue('papiBaseUrl')}