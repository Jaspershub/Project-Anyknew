// pages/web/web.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        targetUrl: 'https://www.baidu.com'
    },
    onLoad(options) {
        this.setData({
            targetUrl: options.targetUrl
        });
    }
})