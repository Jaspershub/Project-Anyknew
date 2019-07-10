// pages/detail/detail.js
import {
  SelfPage
} from '../../utils/selfPage';
import Notify from '../../components/vant/notify/notify';

SelfPage({

  /**
   * 页面的初始数据
   */
  data: {
    nid: '',
    title: '',
    contentList: {},
    uid: null,
    isCollection: false,
    isThumbup: false,
    commentDetail: '',
    showCommentInput: false
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

  onThumbClick() {
    const uid = this.fetchUidByLocalCache();
    if (!uid) {
      console.error('未登录...');
      // TODO 登录啊
      return;
    }
    wx.showLoading({
      title: '处理中...'
    });
    wx.selfRequest.user.thumbup({
      uid,
      nid: this.data.nid,
      thumbup_check: this.data.isThumbup ? '101' : '100'
    }).then(res => {
      wx.hideLoading();
      console.log('点赞结果', res);
      this.setData({
        isThumbup: !this.data.isThumbup
      });
    });
  },

  onLikeClick() {
    const uid = this.fetchUidByLocalCache();
    if (!uid) {
      console.error('未登录...');
      // TODO 登录啊
      return;
    }

    wx.showLoading({
      title: '处理中...'
    });

    wx.selfRequest.user.collection({
      uid,
      nid: this.data.nid,
      ntitle: this.data.title,
      col_check: this.data.isCollection ? '101' : '100'
    }).then(res => {
      wx.hideLoading();
      console.log('收藏结果', res);
      this.setData({
        isCollection: !this.data.isCollection
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
      nid: this.data.nid,
      content: this.data.commentDetail,
      to_cid: ""
    };
    wx.showLoading({
      title: '评论发表中...'
    });
    wx.selfRequest.user.comment(data).then(res => {
      wx.hideLoading();
      if (res.data.data.result === 100) {
        console.log('评论成功');
        Notify({
          text: '评论成功，进入评论列表看看吧',
          duration: 1000,
          backgroundColor: '#1989fa'
        });
        this.setData({
          commentDetail: ''
        });
      } else {
        Notify({
          text: '评论失败，好像网络出错了',
          duration: 1000,
          backgroundColor: '#1989fa'
        });
        console.log('评论失败');
      }
      this.onCommentInputClose();
    });
  },

  onCommentClick() {
    wx.navigateTo({
      url: `../comment/comment?contentId=${this.data.nid}`
    });
  },

  onInputClick() {
    this.setData({
      showCommentInput: true
    });
  },

  onCommentInputClose() {
    this.setData({
      showCommentInput: false
    });
  },

  pageInit() {
    const uid = this.fetchUidByLocalCache();
    if (!uid) {
      console.error('未登录...');
      // TODO 登录啊
      return;
    }
    return new Promise((resolve) => {

      wx.selfRequest.article.detail({
        nid: this.data.nid,
        uid
      }).then(res => {
        console.log(res);
        const result = res.data.data;
        this.setData({
          isCollection: result.collection === 101,
          isThumbup: result.thumbup === 101,
          contentList: result.content
        });
        resolve();
      });
    });
  },

  onInputChange(event) {
    this.data.commentDetail = event.detail;
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.data.nid = options.id;
    this.setData({
      title: options.title
    });
  }
});