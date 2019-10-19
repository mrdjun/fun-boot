<h1 align="center"><a href="https://github.com/mrdjun" target="_blank">Fun-Boot</a></h1>

<p align="center">
<a href="https://github.com/mrdjun/fun-boot"><img alt="JDK" src="https://img.shields.io/badge/JDK-1.8-orange.svg"/></a>
<a href="http://mrdjun.github.io"><img alt="Author" src="https://img.shields.io/badge/Author-DJun-blue"/></a>
<a href="https://jq.qq.com/?_wv=1027&k=57LIuZr"><img alt="QQ群" src="https://img.shields.io/badge/chat-Coder%E5%A4%A7%E5%AE%B6%E5%BA%AD-yellow"/></a>
<a href="https://github.com/mrdjun/fun-boot/blob/master/LICENSE"><img alt="license" src="https://img.shields.io/github/license/java-aodeng/hope.svg?style=flat-square"/></a>
</p>

## 【正在开发中，敬请期待...】
# 简介
> 一款现代化的脚手架项目。集成APP的api与后端api于一体:beers:<br>
> 个人博客：https://blog.csdn.net/qq_41647999 <br>
> 主语言 [java] 欢迎star
------------------------------

# 文档
团队正在奋力开发，待开发完成后，完善并公布文档。

## 近期更新预览
- [x]SSM框架搭建，含Swagger、Druid、动态数据源配置
- [x]整合常用工具类
- [x]全局拦截器、异常捕获处理
- [x]整合配置Redis，常用命令工具类配置，通过Jedis执行Redis命令
- [x]修改Banner、项目启动验证Redis连接状态
- [x]调整全局对i18n的支持具有扩展性
- [x]app端登录接口
- [x]app端需登录注解与免登录注解
- [x]App端 JWT+Redis 登录认证
- [x]异步工厂构建（线程池）
- [x]登录日志记录异步实现
- [x]操作日志记录异步实现
- [x]接口限流的注解，升级登录次数记录的功能
- [x]后端登录接口
- [x]实现后端登录验证码拦截器
- [x]后端登录验证码生成工具
- [x]整合文件上传工具类commons-fileupload，完成文件的上传、下载（完）
- [x]后端整合Shiro+Redis（完）
- [x]后端登录功能，含Shiro Realm、登录密码加盐、加账号加密解密（完）
- [x]防前端重复提交的注解（完）
- [x]重新规划整理项目接口与结构，更加清晰明了（完）
- [x]完善代码注释，让初次接触FunBoot的朋友更加易于学习（完）
- [x]国庆期间停更四天，出去溜达溜达（2019/10/1 - 4）
- [x]定制代码生成器（细节完善:1、删除entity多余字段 2、需要把int与long字段映射为Integer与Long的问题），减少写重复代码，提高对业务的专注性（完）
- [ ]基于LayUI 开发基础版后台页面（后端菜单渲染,后端首页与其它系统页面）
- [ ]将基础版修改为Ajax加载HTML代码渲染页面
- [ ]大力优化后端页面UI
- [ ]JVM与服务器资源监控
- [ ]数据库账号密码加密

## 架构
|名称|技术|
|-|-|
|app端|JWT + Redis|
|admin端|Thymeleaf + LayUI + Shiro + Redis|

## 结构
|前缀|模块|
|-|-|
|ums|用户管理|
|sys|系统管理|

## api规划
|URI|名称|
|-|-|
| /app/**|app接口地址|
|/admin/**|admin接口地址|

# My link：

- [开发作者：Mr.DJun-#个人博客：读万卷书，行万里路，赚很多钱🥚](http://mrdjun.github.io)
- [QQ交流群](https://jq.qq.com/?_wv=1027&k=57LIuZr)
- [Github](https://github.com/mrdjun)
- [开源组织](https://github.com/u-fun)
- [联系邮箱：](https://github.com/mrdjun/fun-boot)mr.djun@qq.com
