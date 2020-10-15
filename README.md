<h1 align="center">Welcome to FunBoot 👋</h1>
<p align="center">
<a href="https://github.com/mrdjun/fun-boot"><img alt="JDK" src="https://img.shields.io/badge/JDK-1.8-orange.svg"/></a>
<a href="http://mrdjun.github.io"><img alt="Author" src="https://img.shields.io/badge/Author-DJun-blue"/></a>
<a href="https://jq.qq.com/?_wv=1027&k=57LIuZr"><img alt="QQ群" src="https://img.shields.io/badge/chat-Coder%E5%A4%A7%E5%AE%B6%E5%BA%AD-yellow"/>
</a>
<a href="https://mrdjun.github.io/">
<img alt="Documentation" src="https://img.shields.io/badge/documentation-yes-brightgreen.svg" target="_blank" /></a>
</p>

### 🎉预览

文档地址：[点击跳转直通车](http://mrdjun.github.io/)

### ✨ 简介

> FunBoot是一款带有管理后台和App端让您快速开发的脚手架项目 🏠[文档](https://mrdjun.github.io/) ⬇️[下载](https://github.com/mrdjun/fun-boot) 📚[English Document](https://mrdjun.github.io/#/en-us/generic/README)
> 
> FunBoot采用全新的Java Web框架——Spring Boot 2.1.5，省去了如往日的SSH项目中的大量繁琐的XML配置，使得二次开发更加简便。在数据持久层方面选择的是MyBatis开源框架与PageHelper插件，可以快速的实现对单表的增删改查。为什么不选择MyBatisPlus或JPA省去写SQL？原因有二：其一，控制性能。其二，学习SQL性能优化。缓存采用的是性能出众的Redis。
> 
> 在后台权限管理系统的安全方面，采用时下流行的Apache Shiro，可以实现按钮级别的权限控制（若当前用户无此按钮权限，则不显示该按钮），前端页面采用Bootstrap 框架，风格统一，自适应。此外，在FunBoot中还封装了 Bootstrap 的多个插件，若需使用，直接调用即可。
> 
> 在APP端的安全方面，采用的是JJWT框架与Redis做安全认证和权限认证。
> 
> 脚手架自带App端和Admin端的代码生成工具，Admin端带有HTML页面代码生成，两端均自带权限验证，无论你是学习还是做外包项目，这都是你非常好的选择，上手快速，开发高效。
> 
> 随后作者将学习MyBatis的源码，手写一个低配的MyBatis框架，如果您也有兴趣，欢迎加群，一同进步！

## 内置功能

1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  参数管理：对系统动态配置常用参数。
8.  通知公告：系统通知公告信息发布维护。
9.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. 登录日志：系统登录日志记录查询包含登录异常。
11. 在线用户：当前系统中活跃用户状态监控。
12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14. 系统接口：根据业务代码自动生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. 在线构建器：拖动表单元素生成相应的HTML代码。
17. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

## 🎨 App端与Admin端技术概要

| 端名  | 技术                          |
| :---- | :---------------------------- |
| app   | JWT                           |
| admin | Shiro + Thymeleaf + Bootstrap |

## 🚀 快速使用

**在使用之前，请务必阅读并同意 [License](https://mrdjun.github.io/#/generic/LICENSE.md) 文件中的条款，否则请勿安装使用本脚手架。**

## 📝 讨论区

提交BUG或需求等等请务必先阅读 [https://mrdjun.github.io/#/guide/feedback](https://mrdjun.github.io/#/guide/feedback.md)

如有问题可以在 GitHub 提 issue, 也可在下方的讨论组里

1. GitHub issue: [https://github.com/mrdjun/fun-boot/issues](https://github.com/mrdjun/fun-boot/issues)
2. QQ 群: 183579482
3. 微信群: 还未创建微信群，敬请期待！
