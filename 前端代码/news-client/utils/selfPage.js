// pages/hot/hot.js

/* 公共的Page数据 */
const commonPageData = {
  areYouReady: false
};
/* 公共的Page方法 */
const commonPageMethod = {
  closeLoading() {
    setTimeout(() => {
      this.setData({
        areYouReady: true
      });
    }, 1000);
  }
};



const SelfPage = function(options) {
  /* 组合公共页面数据和页面自有数据 */
  const data = Object.assign(commonPageData, (options.data || {}));

  /* 为了实现Page的加载中动画，在onReady中做了钩子，模拟数据请求完成后显示页面 */
  const rawOnShow = options.onShow || (() => {});

  delete options.data;
  delete options.onShow;
  return Page({
    data,
    onShow() {
      if (this.pageInit) {
        this.pageInit().then(() => {
          this.closeLoading();
        });
      } else {
        this.closeLoading();
      }
      rawOnShow();
    },
    ...commonPageMethod,
    ...options
  });
}


module.exports = {
  SelfPage
};