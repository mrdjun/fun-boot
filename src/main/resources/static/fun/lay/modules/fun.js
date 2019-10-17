layui.extend({
    conf: 'config',
    api: 'lay/modules/api',
    view: 'lay/modules/view'

}).define(['conf', 'view', 'jquery', 'api', 'loadBar', 'layer'], function (exports) {
    var conf = layui.conf
        , view = layui.view
        , element = layui.element
        , $ = layui.jquery
        , layer = layui.layer
        , self = {}
        , POPUP_DATA = {}
        , loadBar = layui.loadBar
        ,windowWidth = $(window).width();

    conf.viewTabs = true;
    self.route = layui.router();
    self.view = view;
    self.ie8 = view.ie8;
    self.api = layui.api;
    self.get = view.request;
    self.shrinkCls = 'fun-sidebar-shrink';
    self.routeLeaveFunc = null;
    self.isInit = false;

    self.routeLeave = function (callback) {
        this.routeLeaveFunc = callback
    };

    self.initPage = function (initCallback) {
        layer.alert('页面初始化')
    };

    // 初始化视图区域
    self.initView = function (route) {
        if (!self.route.href || self.route.href === '/') {
            self.route = layui.router('#' + conf.entry);
            route = self.route
        }
        route.fileurl = '/' + route.path.join('/');

        if ($.inArray(route.fileurl, conf.indPage) === -1) {
            var loadRenderPage = function (params) {
                if (conf.viewTabs === true) {
                    view.renderTabs(route)
                } else {
                    view.render(route.fileurl)
                }
            };

            if (view.containerBody == null) {
                //加载layout文件
                view.renderLayout(function () {
                    //重新渲染导航
                    element.render('nav', 'fun-sidebar');
                    //加载视图文件
                    loadRenderPage()
                })
            } else {
                //layout文件已加载，加载视图文件
                loadRenderPage()
            }
        } else {
            //加载单页面
            view.renderIndPage(route.fileurl, function () {
                if (conf.viewTabs === true) view.tab.clear()
            })
        }
    };


    // 打印错误信息
    self.log = function (msg, type) {
        if (conf.debug === false) return;
        if (type === undefined) type = 'error';
        console.error(msg)
    };

    // ajax get请求
    self.get = function (url, params, success) {
        if (params) {
            params.invalidate_ie_cache = new Date();
        }
        $.get(url, params, function (r) {
            resolveResponse(r, success);
        })
    };

    // ajax post请求
    self.post = function (url, params, success) {
        if (params) {
            params.invalidate_ie_cache = new Date();
        }
        $.post(url, params, function (r) {
            resolveResponse(r, success);
        })
    };

    // 判断 a种的属性是否 b都有，并且弱相等
    self.nativeEqual = function (a, b) {
        var aProps = Object.getOwnPropertyNames(a);
        var bProps = Object.getOwnPropertyNames(b);
        for (var i = 0; i < aProps.length; i++) {
            var propName = aProps[i];
            if (!compare(a[propName], b[propName])) {
                return false;
            }
        }
        return true;
    };

    // ----------------- 弹窗类 --------------------- //

    self.alert = {};

    function alertParams(msg, params) {
        params.time = 3000;
        params.shade = 0;
        params.btn = null;
        params.title = [
            '<i class="layui-icon layui-icon-' +
            params.titleIco +
            '" style="font-size:12px;background:' +
            params.titleIcoColor +
            ';display:inline-block;font-weight:600;position:relative;top:-2px;height:21px;line-height:21px;text-align:center;width:21px;color:#fff;border-radius:50%;margin-right:12px;"></i>' +
            (msg || '请填写提示信息'),
            'background:#fff;border:none;font-weight:500;font-size:14px;color:#08132b;margin-bottom:-50px;padding:16px;height:45px;line-height:14px;padding-bottom:0;'
        ];
        params.offset = '40px';
        params.area = [windowWidth <= 750 ? '80%' : '400px'];
    }

    self.alert.success = function (msg, params) {
        params = params || {};
        params.titleIco = 'ok';
        params.titleIcoColor = '#30d180';
        alertParams(msg, params);
        self.modal.base('', params);
    };

    self.alert.warn = function (msg, params) {
        params = params || {};
        params.titleIco = 'exclaimination';
        params.titleIcoColor = '#ffc107';
        alertParams(msg, params);
        self.modal.base('', params);
    };

    self.alert.error = function (msg, params) {
        params = params || {};
        params.titleIco = 'close';
        params.titleIcoColor = '#ff5652';
        alertParams(msg, params);
        self.modal.base('', params);
    };

    self.alert.info = function (msg, params) {
        params = params || {};
        params.titleIco = 'infomation';
        params.titleIcoColor = '#2db7f5';
        alertParams(msg, params);
        self.modal.base('', params);
    };

    // ----------------- 模态框类 --------------------- //
    self.modal = {};

    self.modal.base = function (msg, params) {
        params = params || {};
        params.titleIcoColor = params.titleIcoColor || '#5a8bff';
        params.titleIco = params.titleIco || 'exclaimination';
        params.title = params.title || [
            '<i class="layui-icon layui-icon-' +
            params.titleIco +
            '" style="font-size:12px;background:' +
            params.titleIcoColor +
            ';display:inline-block;position:relative;top:-2px;height:21px;line-height:21px;text-align:center;width:21px;color:#fff;border-radius:50%;margin-right:12px;"></i>' +
            params.titleValue,
            'background:#fff;border:none;font-weight:bold;font-size:16px;color:#08132b;padding-top:10px;height:36px;line-height:46px;padding-bottom:0;'
        ];
        params = $.extend(
            {
                skin: 'layui-layer-admin-modal fun-alert',
                area: [windowWidth <= 750 ? '60%' : '400px'],
                closeBtn: 0,
                shadeClose: false
            },
            params
        );
        layer.alert(msg, params);
    };

    self.modal.confirm = function (title, msg, yes, no, params) {
        params = params || {};
        params.titleIco = 'exclaimination';
        params.titleIcoColor = '#ffc107';
        params.titleValue = title;
        params.shadeClose = false;
        params = $.extend({
            btn: ['确定', '取消']
            , yes: function (index, layero) {
                yes && (yes)();
                layer.close(index);
            }
            , btn2: function (index, layero) {
                no && (no)();
            }
        }, params);
        self.modal.base(msg, params);
    };

    self.modal.info = function (title, msg, yes, params) {
        params = params || {};
        params.titleIco = 'infomation';
        params.titleIcoColor = '#2db7f5';
        params.titleValue = title;
        params.shadeClose = false;
        params = $.extend({
            btn: ['确定']
            , yes: function (index, layero) {
                yes && (yes)();
                layer.close(index);
            }
        }, params);
        self.modal.base(msg, params);
    };

    self.modal.warn = function (title, msg, yes, params) {
        params = params || {};
        params.titleIco = 'exclaimination';
        params.titleIcoColor = '#ffc107';
        params.titleValue = title;
        params.shadeClose = false;
        params = $.extend({
            btn: ['确定']
            , yes: function (index, layero) {
                yes && (yes)();
                layer.close(index);
            }
        }, params);
        self.modal.base(msg, params);
    };

    self.modal.success = function (title, msg, yes, params) {
        params = params || {};
        params.titleIco = 'ok';
        params.titleIcoColor = '#30d180';
        params.titleValue = title;
        params.shadeClose = false;
        params = $.extend({
            btn: ['确定']
            , yes: function (index, layero) {
                yes && (yes)();
                layer.close(index);
            }
        }, params);
        self.modal.base(msg, params);
    };

    self.modal.error = function (title, msg, yes, params) {
        params = params || {};
        params.titleIco = 'close';
        params.titleIcoColor = '#ff5652';
        params.titleValue = title;
        params.shadeClose = false;
        params = $.extend({
            btn: ['确定']
            , yes: function (index, layero) {
                yes && (yes)();
                layer.close(index);
            }
        }, params);
        self.modal.base(msg, params);
    };

    self.modal.open = function (title, url, params) {
        params = $.extend({
            url: url,
            maxmin: true,
            shadeClose: false,
            title: [
                (title || '请填写头部信息'),
                'font-size:16px;color:#08132b;line-height:46px;padding-bottom:0;border-bottom:1px solid #fcfcfc;background-color:#fcfcfc'
            ]
        }, params);
        self.popup(params);
    };

    self.modal.view = function (title, url, params) {
        params = $.extend({
            url: url,
            maxmin: false,
            shadeClose: false,
            title: [
                title,
                'font-size:15px;color:#08132b;line-height:46px;height:48px;padding-bottom:0;background-color:#fff;border-bottom:none'
            ],
            area: $(window).width() <= 750 ? ['80%', '80%'] : ['50%', '60%']
        }, params);
        self.popup(params);
    };

    // 页面在弹框中打开
    self.popup = function (params) {
        var url = params.url || '';
        var success = params.success || function () {
        };
        params.skin = 'layui-layer-admin-page';
        POPUP_DATA = params.data || {};
        var defaultParams = {
            type: 1,
            area: $(window).width() <= 750 ? ['90%', '90%'] : ['60%', '90%'],
            shadeClose: true
        };

        if (self.isUrl(url)) {
            params.type = 2;
            params.content = url;
            layer.open($.extend(defaultParams, params));
            return
        }

        view.tab.del(url);

        view.loadHtml(conf.views + url, function (res) {
            var htmlElem = $('<div>' + res.html + '</div>');

            if (params.title === undefined) {
                params.title = htmlElem.find('title').text() || '信息';
                if (params.title) htmlElem.find('title').remove()
            }

            params.content = htmlElem.html();
            params.success = function (layer, index) {
                success(layer, index);

                view.parse(layer);
            };

            params = $.extend(defaultParams, params);
            layer.open($.extend(defaultParams, params));
        });
    };

    self.loadHtml = function (url, callback) {
        url = url || conf.entry;
        loadBar.start();

        var queryIndex = url.indexOf('?');
        if (queryIndex !== -1) url = url.slice(0, queryIndex);

        $.ajax({
            url:
                (url.indexOf(conf.base) === 0 ? '' : conf.views) +
                url +
                conf.engine +
                '?v=' +
                conf.v,
            type: 'get',
            data: {
                'invalid_ie_cache': new Date().getTime()
            },
            dataType: 'html',
            success: function (r) {
                var result;
                try {
                    result = JSON.parse(r);
                } catch (e) {
                    result = {'code': 'err'};
                }
                if (result.code === 401) {
                    self.notify('登录失效', '登录已失效，请重新登录', function () {
                        window.location.reload();
                        window.location.hash = '';
                    });
                    loadBar.finish();
                    return;
                }
                if (result.code === 403) {
                    self.tab.change('/403');
                    loadBar.finish();
                    return;
                }
                if (result.code === 404) {
                    self.tab.change('/404');
                    loadBar.finish();
                    return;
                }
                if (result.code === 500) {
                    self.tab.change('/500');
                    loadBar.finish();
                    return;
                }
                callback({html: r, url: url});
                loadBar.finish()
            },
            error: function (res) {
                if (res.code === 404) {
                    self.tab.change('/404');
                }
                if (res.code === 403) {
                    self.tab.change('/403');
                }
                if (res.code === 500) {
                    self.tab.change('/500');
                }
                self.log(
                    '请求视图文件异常\n文件路径：' + url + '\n状态：' + res.code
                );
                loadBar.error();
            }
        })
    };

    // url 正则
    self.isUrl = function (str) {
        return /^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/.test(
            str
        )
    };

    // 请求处理
    function resolveResponse(r, f) {
        if (r.code === 200) {
            f(r) && (f)();
        } else if (r.code === 401) {
            self.modal.info('登录失效', '登录已失效，请重新登录', function () {
                window.location.href = ctx + 'login';
            });
        } else if (r.code === 403) {
            self.alert.warn('对不起，您暂无该操作权限');
        } else {
            self.alert.error(r.message ? r.message : '操作失败');
            console.error(r);
        }
    }

    // 判断a的属性是否b也有
    function compare(a, b) {
        if (a === '' && b === null) {
            return true;
        } else if (a === null && b === '') {
            return true;
        } else {
            return a == b;
        }
    }

    function parseParams(param, key, encode) {
        if (param == null) return '';
        var arr = [];
        var t = typeof (param);
        if (t === 'string' || t === 'number' || t === 'boolean') {
            arr.push(key + '=' + ((encode == null || encode) ? encodeURIComponent(param) : param));
        } else {
            for (var i in param) {
                var k = key == null ? i : key + (param instanceof Array ? '[' + i + ']' : '.' + i);
                arr.push(parseParams(param[i], k, encode));
            }
        }
        return arr.join("&");
    }

    // 解析 BASE64文件内容 for IE，Edge
    function createFile(urlData, fileType) {
        var bytes = window.atob(urlData),
            n = bytes.length,
            u8arr = new Uint8Array(n);
        while (n--) {
            u8arr[n] = bytes.charCodeAt(n);
        }
        return new Blob([u8arr], {type: fileType});
    }

    exports('fun', self);
});