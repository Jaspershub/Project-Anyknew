import regeneratorRuntime from './runtime';
const METHOD_CONST = {
    'get': 'GET',
    'post': 'POST',
    'put': 'PUT',
    'delete': 'DELETE'
}

const request = (options) => {
    if (options.showLoading) {
        wx.showLoading({
            title: '加载中',
        });
    }
    return new Promise(resolve => {
        wx.request({
            url: options.url,
            data: options.data,
            method: options.method,
            header: options.header,
            success(res) {
                resolve({
                    code: 0,
                    data: res
                });
            },
            fail(err) {
                resolve({
                    code: 1,
                    data: err,
                    msg: '请求失败'
                });
            },
            complete() {
                wx.hideLoading();
            }
        });
    });
}

class RequestUtil {
    constructor({
        token
    } = {}) {
        this.tokenName = 'token';
        this.token = token || '';
    }

    getToken() {
        return this.token;
    }

    resetToken(token) {
        this.token = token;
    }

    resetTokenKey(tokenName) {
        this.tokenName = tokenName;
    }

    get({
        url,
        data = {},
        success,
        fail,
        complete,
        header = {},
        showLoading = false
    } = {}) {
        // const innerHeader = Object.assign({
        //     [this.tokenName]: this.token
        // }, header);

        if (this.token) {
            data[this.tokenName] = this.token;
        }

        const options = {
            url,
            data,
            success,
            fail,
            header,
            method: METHOD_CONST.get,
            complete,
            showLoading
        };
        return new Promise((resolve) => {
            request(options).then(res => {
                resolve(res.data);
            });
        });
    }
    post({
        url,
        data,
        success,
        fail,
        complete,
        header = {},
        showLoading = false
    } = {}) {
        const innerHeader = Object.assign({
            "content-type": "application/x-www-form-urlencoded"
        }, header);
        if (this.token) {
            data[this.tokenName] = this.token;
        }
        console.log(data);
        const options = {
            url,
            data,
            header: innerHeader,
            success,
            fail,
            method: METHOD_CONST.post,
            complete,
            showLoading
        };
        return new Promise(async(resolve) => {
            const res = await request(options);
            resolve(res.data);
        });
    }
}
module.exports = {
    RequestUtil
};