//index.js
//获取应用实例
import {
    SelfPage
} from '../../utils/selfPage';

SelfPage({
    data: {
        currentActiveTab: {},
        keyword: '',
        currentPage: 1,
        currentChannelId: '',
        allPages: 1,
        tabList: [],
        contentlist: [],
        searchTimer: null
    },
    pageInit() {
        const that = this;
        return new Promise((resolve) => {

            if (that.data.currentActiveTab.channelId) {
                resolve();
                return;
            }

            wx.getSetting({
                success(res) {
                    if (res.authSetting['scope.userInfo']) {
                        wx.selfRequest.showapi.channelList().then(res => {
                            if (res.data.showapi_res_code === 0) {
                                const tabList = res.data.showapi_res_body.channelList;
                                if (!tabList.length) {
                                    resolve();
                                    return;
                                }
                                that.setData({
                                    tabList
                                });
                                that.data.currentActiveTab = tabList[0];
                                that.fetchContentList({
                                    channelId: tabList[0].channelId,
                                    page: that.data.currentPage,
                                }).then((contentlist) => {
                                    that.setData({
                                        contentlist
                                    });
                                    resolve();
                                });
                            } else {
                                console.error('网络出错了~');
                                resolve();
                            }
                        });
                    }
                }
            });
        });
    },
    onLoad() {
        wx.getSetting({
            success(res) {
                if (!res.authSetting['scope.userInfo']) {
                    wx.switchTab({
                        url: '/pages/auth/auth'
                    });
                }
            }
        });
    },
    onTabClick(event) {
        this.data.currentActiveTab = this.data.tabList[event.detail.index];
        const channelId = this.data.currentActiveTab.channelId;
        this.data.currentPage = 1;
        const that = this;
        this.fetchContentList({
            channelId,
            title: this.data.keyword,
            page: this.data.currentPage
        }).then(contentlist => {
            that.setData({
                contentlist
            })
        });
    },
    fetchContentList({ channelId, page, title = '' } = {}) {
        const that = this;
        return new Promise(resolve => {
            wx.selfRequest.showapi.contentlist({
                channelId,
                page,
                title
            }).then(res => {
                const contentlist = res.data.showapi_res_body.pagebean.contentlist;
                that.setData({
                    allPages: res.data.showapi_res_body.pagebean.allPages
                });
                resolve(contentlist);
            }).catch(e => {
                console.error(e);
                that.setData({
                    contentlist: []
                });
                resolve([]);
            });
        });

    },
    onReachBottom() {
        this.data.currentPage += 1;
        if (this.data.currentPage > this.data.allPages) {
            return;
        }
        this.fetchContentList({
            title: this.data.keyword,
            channelId: this.data.currentActiveTab.channelId,
            page: this.data.currentPage
        }).then(contentlist => {
            this.setData({
                contentlist: this.data.contentlist.concat(contentlist)
            });
        });
    },
    onNewsItemClick(e) {
        wx.navigateTo({
            url: `../detail/detail?title=${e.target.dataset.item.title}&id=${e.target.dataset.item.id}`
        });
    },
    onSearchClick() {
        this.fetchContentList({
            channelId: this.data.currentActiveTab.channelId,
            title: this.data.keyword,
            page: this.data.currentPage
        }).then(contentlist => {
            this.setData({
                contentlist: contentlist
            });
        });
    },

    onSearchChange(e) {
        if (this.data.searchTimer) {
            clearTimeout(this.data.searchTimer);
        }
        this.data.searchTimer = setTimeout(() => {
            this.setData({
                keyword: e.detail
            });
            this.onSearchClick();
        }, 1000);
    }
});