// pages/hot/hot.js
import {
    SelfPage
} from '../../utils/selfPage';

SelfPage({
    /**
     * 页面的初始数据
     */
    data: {
        hotstorySiteList: [{
                label: '知乎',
                name: 'zhihu'
            },
            {
                label: '百度',
                name: 'baidu'
            },
            {
                label: '微博',
                name: 'weibo'
            },
            {
                label: '网易',
                name: '163'
            },
            {
                label: '360',
                name: '360'
            },
            {
                label: '华尔街日报',
                name: 'wallstreetcn'
            },
            {
                label: '好奇心热报',
                name: 'qdaily'
            }
        ],
        hotTabActive: 0,
        hotList: []
    },
    pageInit() {

        const that = this;
        return new Promise((resolve) => {
            if (that.data.hotList.length > 0) {
                return resolve();
            }
            this.fetchSiteHotTopicList(this.data.hotstorySiteList[0].name).then(res => {
                console.log(res.subs);
                that.setData({
                    hotList: res.subs.map(sub => {
                        sub.items = sub.items.map(item => {
                            item.ext = item.ext.replace('热度', '');
                            return item;
                        });
                        return sub;
                    })
                });
                resolve();
            });
        });
    },
    fetchSiteHotTopicList(siteName) {
        return new Promise((resolve) => {
            console.log(siteName);
            wx.selfRequest.hotstory
                .list({ siteName })
                .then(res => {
                    resolve(res.data.data.site);
                });
        });
    },
    onGoToWebPage(event) {
        const iid = event.currentTarget.dataset.item.iid;
        const targetUrl = `https://www.anyknew.com/go/${iid}`;
        console.log(targetUrl);
        wx.navigateTo({
            url: `../web/web?targetUrl=${targetUrl}`
        });
    },
    onTabClick(event) {
        const targetSite = this.data.hotstorySiteList[event.detail.index];
        this.fetchSiteHotTopicList(targetSite.name).then(res => {
            console.log(res);
            this.setData({
                hotTabActive: 0,
                hotList: res.subs.map(sub => {
                    sub.items = sub.items.map(item => {
                        item.ext = item.ext.replace('热度', '');
                        return item;
                    });
                    return sub;
                })
            });
        });
    }
});