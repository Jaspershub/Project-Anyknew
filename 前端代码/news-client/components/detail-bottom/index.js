// components/detail-bottom/detail-bottom.js
Component({
    /**
     * 组件的属性列表
     */
    properties: {
        showActions: {
            type: Boolean,
            value: true
        },
        isCollection: {
            type: Boolean,
            value: false
        },
        isThumbup: {
            type: Boolean,
            value: false
        }
    },

    /**
     * 组件的初始数据
     */
    data: {

    },

    /**
     * 组件的方法列表
     */
    methods: {
        onInputClick() {
            this.triggerEvent('input', {}, {});
        },
        onThumbClick() {
            this.triggerEvent('thumb', {}, {});
        },
        onCommentClick() {
            this.triggerEvent('comment', {}, {});
        },
        onLikeClick() {
            this.triggerEvent('like', {}, {});
        }
    }
});