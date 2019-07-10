// pages/collect/collect.js
import {
    SelfPage
} from '../../utils/selfPage';
SelfPage({
    data: {
        uid: '',
        currentPage: 1,
        contentlist: []
    },
    onNewsItemClick(e) {
        wx.navigateTo({
            url: `../detail/detail?title=${e.target.dataset.item.ntitle}&id=${e.target.dataset.item.nid}`
        });
    },
    fetchUidByLocalCache() {
        if (!this.data.uid) {
            const userInfo = wx.getStorageSync('userInfo');
            if (!userInfo) {
                return null;
            } else {
                this.data.uid = userInfo.uid;
            }
        }
        return this.data.uid;
    },
    pageInit() {
        if (!this.data.uid) {
            this.data.uid = this.fetchUidByLocalCache();
        }
        return new Promise((resolve) => {
            if (this.data.contentlist.length) {
                resolve();
                return;
            }
            this.fetchContentList({
                uid: this.data.uid,
                page: 1
            }).then(contentlist => {
                this.setData({
                    contentlist: contentlist
                });
                resolve();
            });
        });
    },
    fetchContentList({ uid, page, num = 20 } = {}) {
        return new Promise((resolve) => {
            wx.selfRequest.user.myCollection({
                uid,
                page,
                num
            }).then(res => {
                if (res.data.data.count === 0) {
                    this.data.isLastPage = true;
                } else {
                    this.data.isLastPage = false;
                }
                resolve(res.data.data.collectionList);
            }).catch(e => {
                console.error(e);
                resolve([]);
            });
        });
    },

});