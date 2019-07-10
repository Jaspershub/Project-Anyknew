//app.js
import {
    ApiUtil
} from './utils/apiUtil';

App({
    onLaunch() {
        // 挂载网络请求工具
        wx.selfRequest = ApiUtil;
        wx.hideTabBar();
        // 获取用户信息
        wx.getSetting({
            success: res => {
                if (res.authSetting['scope.userInfo']) {
                    // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
                    wx.getUserInfo({
                        success: res => {
                            // iv 和 encryptedData
                            const loginData = {
                                encryptedData: res.encryptedData,
                                iv: res.iv
                            };
                            toLogin.call(this, loginData);
                            // 可以将 res 发送给后台解码出 unionId
                            // this.globalData.userInfo = res.userInfo

                            // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                            // 所以此处加入 callback 以防止这种情况
                            if (this.userInfoReadyCallback) {
                                this.userInfoReadyCallback(res)
                            }
                        }
                    });
                }
            }
        })
    },
    globalData: {
        userInfo: null
    }
});

const app = getApp();

function toLogin(loginData) {
    wx.login({
        success: res => {
            // code
            loginData.code = res.code;
            wx.showLoading({
                title: '登录中...',
            });
            wx.selfRequest.user.login({
                ...loginData
            }).then(res => {

                if (res.data.data) {
                    console.log('token已设置', res.data.data.token);
                    app.globalData.userInfo = res.data.data;
                    wx.setStorage({
                        key: 'userInfo',
                        data: res.data.data
                    });
                    wx.selfRequest.setToken(res.data.data.token);
                    wx.hideLoading();
                } else {
                    wx.getUserInfo({
                        success(res) {
                            const loginData = {
                                encryptedData: res.encryptedData,
                                iv: res.iv
                            };
                            wx.hideLoading();
                            console.error('再次获取data', loginData);
                            toLogin.call(this, loginData);
                        }
                    });
                }
            });
        }
    });
}