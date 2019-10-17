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

    self.render = function (elem) {
        // if (typeof elem == 'string') elem = $('#' + elem);
        // var action = elem.get(0).tagName === 'SCRIPT' ? 'next' : 'find';
        // elem[action]('[is-template]').remove();
        // view.parse(elem)
    };

    // layer.alert('页面初始化')
    self.initPage = function (initCallback) {
        //加载样式文件
        layui.each(layui.conf.style, function (index, url) {
            layui.link(url + '?v=' + conf.v)
        });
        self.initView(self.route);
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

    //根据当前加载的 URL高亮左侧导航
    self.sidebarFocus = function (url) {
        url = url || self.route.href;
        var elem = $('#app-sidebar')
            .find('[lay-href="' + url + '"]')
            .eq(0);
        // $bread.empty();
        if (elem.length > 0) {
            // 生成面包屑
            // var breadHtml = '';
            // elem.parents('dl').prev('a').each(function (k, v) {
            //     var $this = $(this);
            //     breadHtml += '<a>' + $this[0].innerText + ' / </a>';
            // });
            // breadHtml += '<a>' + elem[0].innerText+ ' </a>';
            // $bread.append(breadHtml);

            elem.parents('.layui-nav-item').addClass('layui-nav-itemed')
                .siblings().removeClass('layui-nav-itemed');
            elem.click();
        }
    };
    self.flexible = function (open) {
        if (open === true) {
            view.container.removeClass(self.shrinkCls)
        } else {
            view.container.addClass(self.shrinkCls)
        }
    };
    self.on = function (name, callback) {
        return layui.onevent(conf.eventName, 'system(' + name + ')', callback)
    };
    self.event = function (name, params) {
        layui.event(conf.eventName, 'system(' + name + ')', params)
    };
    self.csshref = function (name) {
        name = name === undefined ? self.route.path.join('/') : name;
        return conf.css + 'views/' + name + '.css' + '?v=' + conf.v
    };
    self.prev = function (n) {
        if (n === undefined) n = -1;
        window.history.go(n)
    };
    self.navigate = function (url) {
        if (url === conf.entry) url = '/';
        window.location.hash = url
    };
    self.data = function (settings, storage) {
        if (settings === undefined) return layui.data(conf.tableName);
        if ($.isArray(settings)) {
            layui.each(settings, function (i) {
                layui.data(conf.tableName, settings[i], storage)
            })
        } else {
            layui.data(conf.tableName, settings, storage)
        }
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