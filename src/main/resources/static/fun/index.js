layui.extend({
    fun: 'lay/modules/fun',
    validate: 'lay/modules/validate'

}).define(['fun', 'conf'], function (exports) {

    layui.fun.initPage();

    console.log("\n %c Fun-Boot 1.0 %c https://github.com/u-fun/fun-boot %c 如果该项目对您有帮助的话，还望点个star给予精神支持！🐤", "color: #fff; font-size: .84rem;background: #366ed8; padding:5px 0;", "font-size: .84rem;background: #fff; border: 2px solid #b0e0a8;border-left: none; padding:3px 0;"," font-size: .84rem;background: #fcf9ec; padding:5px 0;margin-left: 8px");
    exports('index', {});
});