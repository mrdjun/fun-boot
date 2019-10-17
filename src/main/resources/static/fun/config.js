layui.define(function (exports) {
    exports('conf', {
        container: 'fun',
        containerBody: 'fun-body',
        v: '2.0',
        // base: layui.cache.base,
        // css: layui.cache.base + 'css/',
        // views: layui.cache.base + 'views/',
        base: '',
        css: '',
        views: '',
        viewLoadBar: true,
        debug: layui.cache.debug,
        name: 'fun',
        entry: '/index',
        engine: '',
        eventName: 'fun-event',
        tableName: 'fun',
        requestUrl: '/',
        requestHeaders: {},
        logoutHttpCode: 200
    })
});
