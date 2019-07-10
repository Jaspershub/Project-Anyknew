'use strict';
// const BASE = 'http://127.0.0.1:7001/xcx/api/v1';
const BASE = 'https://5ccbf4d3f47db80014010cf0.mockapi.io/api/v1';

// 路由表
const ApiConfig = {
    articles(id) {
        id = id || '';
        return `${BASE}/articles/${id}`;
    }
};

module.exports = {
    ApiConfig
};