// pages/comment/comment.js
import {
    SelfPage
} from '../../utils/selfPage';
SelfPage({

    /**
     * 页面的初始数据
     */
    data: {
        uid: null,
        commentDetail: '',
        contentId: '',
        commentPlaceholder: '有何高见，展开讲讲',
        showCommentInput: false,
        currentPage: 1,
        toCid: '',
        commentList: [],
        isLastPage: false
    },

    onInputClick() {
        this.data.toCid = '';
        this.setData({
            commentPlaceholder: '有何高见，展开讲讲',
            showCommentInput: true
        });
        // console.log(`this.data.toCid=${this.data.toCid}`);
    },

    onCommentItemClick(event) {
        const targetItem = event.currentTarget.dataset.item;
        this.data.toCid = targetItem.cid;
        this.setData({
            commentPlaceholder: `回复@${targetItem.wname}`,
            showCommentInput: true
        });
        // console.log(`this.data.toCid=${this.data.toCid}`);
    },

    pageInit() {
        return new Promise((resolve) => {
            if (this.data.commentList.length) {
                resolve();
                return;
            }
            this.fetchCommentList({
                nid: this.data.contentId,
                page: 1
            }).then(commentList => {
                this.setData({
                    commentList: commentList
                });
                resolve();
            });
        });
    },

    onReachBottom() {
        this.data.currentPage += 1;
        if (this.data.isLastPage) {
            return;
        }
        this.fetchCommentList({
            nid: this.data.contentId,
            page: this.data.currentPage
        }).then(commentList => {
            this.setData({
                commentList: this.data.commentList.concat(commentList)
            });
        });
    },

    fetchCommentList({ nid, page, num = 20 } = {}) {
        return new Promise((resolve) => {
            wx.selfRequest.article.comment({
                nid,
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

    onPostComment() {
        const uid = this.fetchUidByLocalCache();
        if (!uid) {
            console.error('未登录...');
            // TODO 登录啊
            return;
        }
        const data = {
            from_uid: uid,
            nid: this.data.contentId,
            content: this.data.commentDetail,
            to_cid: this.data.toCid || ''
        };
        wx.selfRequest.user.comment(data).then(res => {
            if (res.data.data.result === 100) {
                console.log('评论成功');
                this.data.currentPage = 1;
                this.fetchCommentList({
                    nid: this.data.contentId,
                    page: 1
                }).then(commentList => {
                    this.setData({
                        commentList
                    });
                });
                this.setData({
                    commentDetail: ''
                });
            } else {
                console.log('评论失败');
            }
            this.onCommentInputClose();
        });
    },

    onCommentInputClose() {
        this.setData({
            showCommentInput: false
        });
    },

    onLoad(options) {
        this.data.contentId = options.contentId;
    },


    onInputChange(event) {
        this.data.commentDetail = event.detail;
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
    }
})