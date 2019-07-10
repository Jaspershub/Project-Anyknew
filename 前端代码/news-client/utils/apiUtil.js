import {
    RequestUtil
} from './requestUtil';

const requestUtil = new RequestUtil();
const BASE = 'http://hotnews.free.idcfengye.com';
// const BASE = 'http://localhost:8080';
const NORMAL_ARTICLE_BASE = 'http://route.showapi.com';
const HOT_ARTICLE_BASE = 'https://www.anyknew.com/go';

const ApiBaseConfig = {
    user: `${BASE}/user`,
    article: `${BASE}/article`,
    hotstory: `${BASE}/hotstory`,
};

const ShowApiConfig = {
    showapi_sign: '601d5ad6e3d14075a8a1bc61b47dedb8',
    showapi_appid: 90451,
};

const formatterDateTime = () => {
    var date = new Date()
    var month = date.getMonth() + 1
    var datetime = date.getFullYear() +
        "" // "年"
        +
        (month >= 10 ? month : "0" + month) +
        "" // "月"
        +
        (date.getDate() < 10 ? "0" + date.getDate() : date
            .getDate()) +
        "" +
        (date.getHours() < 10 ? "0" + date.getHours() : date
            .getHours()) +
        "" +
        (date.getMinutes() < 10 ? "0" + date.getMinutes() : date
            .getMinutes()) +
        "" +
        (date.getSeconds() < 10 ? "0" + date.getSeconds() : date
            .getSeconds());
    return datetime;
};

const ApiUtil = {
    setToken(token) {
        requestUtil.resetToken(token);
    },
    getToken() {
        console.log(`get token = ${requestUtil.getToken()}`)
        return requestUtil.getToken();
    },
    showapi: {
        channelList() {
            const data = Object.assign({ showapi_timestamp: formatterDateTime() }, ShowApiConfig);
            const url = `${NORMAL_ARTICLE_BASE}/109-34`;
            return requestUtil.get({
                url,
                data
            });
        },
        contentlist({
            channelId,
            page,
            title = '',
            needContent = 0,
            needHtml = 0,
            needAllList = 1,
            maxResult = 20
        } = {}) {
            const url = `${NORMAL_ARTICLE_BASE}/109-35`;
            const data = Object.assign({
                showapi_timestamp: formatterDateTime(),
                channelId,
                page,
                title,
                needContent,
                needHtml,
                needAllList,
                maxResult
            }, ShowApiConfig);
            return requestUtil.get({
                url,
                data,
                showLoading: true
            });
        }
    },
    user: {
        login({ code, encryptedData, iv } = {}) {
            const url = `${ApiBaseConfig.user}/login`;
            return requestUtil.get({
                url,

                data: {
                    encryptedData,
                    code,
                    iv
                }
            });
        },
        myCollection({ uid, page, num } = {}) {
            const url = `${ApiBaseConfig.user}/myCollection`;
            return requestUtil.post({
                url,
                data: {
                    uid,
                    page,
                    num
                }
            });
        },
        collection({ uid, nid, ntitle, col_check } = {}) {
            const url = `${ApiBaseConfig.user}/collection`;
            return requestUtil.post({
                url,
                data: {
                    uid,
                    nid,
                    ntitle,
                    col_check
                }
            });
        },
        deleteComment({ cid } = {}) {
            const url = `${ApiBaseConfig.user}/deleteComment`;
            return requestUtil.post({
                url,
                data: {
                    cid
                }
            });
        },
        myComment({ uid, page, num } = {}) {
            const url = `${ApiBaseConfig.user}/myComment`;
            return requestUtil.post({
                url,
                data: { uid, page, num }
            });
        },
        comment({ from_uid, nid, content, to_cid } = {}) {
            const url = `${ApiBaseConfig.user}/comment`;
            return requestUtil.post({
                url,
                data: { from_uid, nid, content, to_cid }
            });
        },
        thumbup({ uid, nid, thumbup_check } = {}) {
            const url = `${ApiBaseConfig.user}/thumbup`;
            return requestUtil.post({
                url,
                data: { uid, nid, thumbup_check }
            });
        }
    },
    article: {
        readNum({ nid, ntitle, channelid } = {}) {
            const url = `${ApiBaseConfig.article}/readNum`;
            return requestUtil.get({
                url,
                data: { nid, ntitle, channelid }
            });
        },
        detail({ nid, uid } = {}) {
            const url = `${ApiBaseConfig.article}/detail`;
            return requestUtil.get({
                url,
                data: { nid, uid }
            });
        },
        comment({ nid, page, num } = {}) {
            const url = `${ApiBaseConfig.article}/comment`;
            return requestUtil.get({
                url,
                data: { nid, page, num }
            });
        },
        thumbup({ nid, uid } = {}) {
            const url = `${ApiBaseConfig.article}/thumbup`;
            return requestUtil.post({
                url,
                data: { nid, uid }
            });
        }
    },
    hotstory: {
        list({ siteName } = {}) {
            const url = `${ApiBaseConfig.hotstory}/${siteName}`;
            return requestUtil.get({
                url,
                showLoading: true
            });
        }
    }
};

module.exports = {
    ApiUtil
};