// pages/my-comment/my-comment.js
import {
    SelfPage
} from '../../utils/selfPage';
SelfPage({

    /**
     * 页面的初始数据
     */
    data: {
        uid: null,
        userInfo: {},
        currentPage: 1,
        commentList: []
    },
    fetchUidByLocalCache() {
        if (!this.data.uid) {
            const userInfo = wx.getStorageSync('userInfo');
            if (!userInfo) {
                return null;
            } else {
                // this.data.userInfo = userInfo;
                this.setData({
                    userInfo
                });
                this.data.uid = userInfo.uid;
            }
        }
        return this.data.uid;
    },

    onNewsClick(e) {
        wx.navigateTo({
            url: `../detail/detail?title=${e.currentTarget.dataset.ntitle}&id=${e.currentTarget.dataset.nid}`
        });
    },

    onCommentClick(e) {
        console.log(e);
        wx.navigateTo({
            url: `../comment/comment?contentId=${e.currentTarget.dataset.nid}`
        });
    },

    pageInit() {

        if (!this.data.uid) {
            this.data.uid = this.fetchUidByLocalCache();
        }

        return new Promise((resolve) => {
            if (this.data.commentList.length) {
                resolve();
                return;
            }
            this.fetchCommentList({
                uid: this.data.uid,
                page: 1
            }).then(commentList => {
                this.setData({
                    commentList: commentList
                });
                resolve();
            });
        });
    },

    fetchCommentList({ uid, page, num = 20 } = {}) {
        return new Promise((resolve) => {
            wx.selfRequest.user.myComment({
                uid,
                page,
                num
            }).then(res => {
                if (res.data.data.count === 0) {
                    this.data.isLastPage = true;
                } else {
                    this.data.isLastPage = false;
                }
                resolve(res.data.data.commentList);
            }).catch(e => {
                console.error(e);
                resolve([]);
            });
        });
    },

    onReachBottom() {
        this.data.currentPage += 1;
        if (this.data.isLastPage) {
            return;
        }
        this.fetchCommentList({
            uid: this.data.uid,
            page: this.data.currentPage
        }).then(commentList => {
            this.setData({
                commentList: this.data.commentList.concat(commentList)
            });
        });
    }
});