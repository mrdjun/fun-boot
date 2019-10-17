layui.extend({
    loadBar: 'lay/modules/loadBar'

}).define(['jquery', 'element', 'loadBar'], function (exports) {
    var $ = layui.jquery
        , conf = layui.conf
        , loadBar = layui.loadBar
        , self = {
        ie8:
            navigator.appName === 'Microsoft Internet Explorer' &&
            navigator.appVersion.split(';')[1].replace(/[ ]/g, '') === 'MSIE8.0',
        container: $('#' + conf.container),
        containerBody: null
    };
    conf.viewTabs = true;

    //解析普通文件
    self.render = function (fileUrl, callback) {
        self.loadHtml(fileUrl, function (res) {
            var htmlElem = $('<div>' + res.html + '</div>');
            var params = self.fillHtml(res.url, htmlElem, 'html');
            if ($.isFunction(callback)) callback(params)
        })
    };

    self.fillHtml = function (url, htmlElem, modeName) {
        var fluid = htmlElem.find('.layui-fluid[lay-title]');
        var title = '';
        if (fluid.length > 0) {
            title = fluid.attr('lay-title');
            // self.setTitle(title)
        }

        var container = self.containerBody || self.container;
        container[modeName](htmlElem.html());
        if (modeName === 'prepend') {
            self.parse(container.children('[lay-url="' + url + '"]'))
        } else {
            self.parse(container)
        }
        return {title: title, url: url, htmlElem: htmlElem}
    };

    //加载 tab
    self.renderTabs = function (route, callback) {
        var tab = self.tab;
        tab.change(route, callback)
    };

    //加载layout文件
    self.renderLayout = function (callback, url) {
        if (url === undefined) url = 'layout';
        self.containerBody = null;

        self.render(url, function (res) {
            self.containerBody = $('#' + conf.containerBody);
            if (conf.viewTabs === true) {
                self.containerBody.addClass('fun-tabs-body')
            }
            layui.fun.appBody = self.containerBody;
            if ($.isFunction(callback)) callback()
        })
    };

    //加载单页面
    self.renderIndPage = function (fileUrl, callback) {
        self.renderLayout(function () {
            self.containerBody = null;
            if ($.isFunction(callback)) callback()
        }, fileUrl)
    };

    self.request = function (params) {
        params = self.createRequestParams(params);
        $.ajax(params)
    };

    self.createRequestParams = function (params) {
        var success = params.success
            , error = params.error;

        if (params.api) {
            if (!layui.api[params.api]) {
                self.log('请求错误 api.' + params.api + ' 不存在');
                return
            }
            params.url = conf.requestUrl + layui.api[params.api]
        } else if (params.url) {
            params.url = conf.requestUrl + params.url
        }

        var defaultParams = {
            timeout: 5000,
            type: 'get',
            dataType: 'json',
            headers: conf.requestHeaders || {},
            success: function (res) {
                if ($.isFunction(success)) success(res)
            },

            error: function (res) {

                layer.msg('请检查您的网络连接');
                self.log('请检查您的网络连接，错误信息：' + JSON.stringify(res))

                if ($.isFunction(error)) error(res)
            }
        };

        if (self.ie8) {
            if (conf.debug) $.support.cors = true;
            defaultParams.cache = false
        }

        delete params.success;
        delete params.error;

        // 把第二个参数合并到第一个参数
        return $.extend(defaultParams, params)
    };

    exports('view', self);
});