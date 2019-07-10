// pages/mine/mine.js
import {
  SelfPage
} from '../../utils/selfPage';
SelfPage({
  data: {
    authorized: false
  },
  onLoad() {
    const that = this;
    wx.getSetting({
      success(res) {
        that.setData({
          authorized: res.authSetting['scope.userInfo'] ? true : false
        });
        // res.authSetting = {
        //   "scope.userInfo": true,
        //   "scope.userLocation": true
        // }
      }
    });
  },
  onGotUserInfo(e) {
    const that = this;
    wx.getSetting({
      success(res) {
        that.setData({
          authorized: res.authSetting['scope.userInfo'] ? true : false
        });
      }
    });
  }
});