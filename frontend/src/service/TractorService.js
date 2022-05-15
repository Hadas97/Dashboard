import axios from 'axios';

async function getAllTractors() {
    return axios({
        method: 'get',
        url: `http://localhost:8080/tractor/getAllTractors`,
        headers: { 'content-type': 'application/json' },
        accept: 'application/json',
    });
}

async function getTractorExecutedTaksksPercent() {
    return axios({
        method: 'get',
        url: `http://localhost:8080/tractor/getTractorExecutedTaksksPercent`,
        headers: { 'content-type': 'application/json' },
        accept: 'application/json',
    });
}

export {getAllTractors, getTractorExecutedTaksksPercent};