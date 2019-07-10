// pages/auth/auth.js

// const checkAuthResult = (cb) => {
//     wx.getSetting({
//         success(res) {
//             if (res.authSetting['scope.userInfo']) {
//                 wx.switchTab({
//                     url: '/pages/index/index'
//                 });
//                 if (cb) cb(true);
//             } else {
//                 if (cb) cb(false);
//             }
//         }
//     });
// };

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

Page({
    data: {
        authFail: false
    },
    onLoad() {
        wx.hideTabBar();
    },
    onShow() {
        wx.getSetting({
            success(res) {
                if (res.authSetting['scope.userInfo']) {
                    wx.getUserInfo({
                        success: res => {
                            // iv 和 encryptedData
                            const loginData = {
                                encryptedData: res.encryptedData,
                                iv: res.iv
                            };
                            console.log('fuckkkkkk', loginData);
                            toLogin.call(this, loginData);
                            wx.showLoading({
                                title: '登录中...'
                            });
                            setTimeout(() => {
                                wx.hideLoading();
                                wx.switchTab({
                                    url: '/pages/index/index'
                                });
                            }, 2000);
                        }
                    });
                }
            }
        });
    },
    onGotUserInfo() {
        const that = this;
        wx.getSetting({
            success(res) {
                if (res.authSetting['scope.userInfo']) {
                    wx.getUserInfo({
                        success: res => {
                            // iv 和 encryptedData
                            const loginData = {
                                encryptedData: res.encryptedData,
                                iv: res.iv
                            };
                            console.log('fuckkkkkk', loginData);
                            toLogin.call(this, loginData);
                            wx.showLoading({
                                title: '登录中...'
                            });
                            setTimeout(() => {
                                wx.hideLoading();
                                wx.switchTab({
                                    url: '/pages/index/index'
                                });
                            }, 2000);
                        }
                    });
                } else {
                    that.setData({
                        authFail: true
                    });
                }
            }
        });
    }
})