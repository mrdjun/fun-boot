<h1 align="center">Welcome to FunBoot 👋</h1>
<p align="center">
<a href="https://github.com/mrdjun/fun-boot"><img alt="JDK" src="https://img.shields.io/badge/JDK-1.8-orange.svg"/></a>
<a href="http://mrdjun.github.io"><img alt="Author" src="https://img.shields.io/badge/Author-DJun-blue"/></a>
<a href="https://jq.qq.com/?_wv=1027&k=57LIuZr"><img alt="QQ群" src="https://img.shields.io/badge/chat-Coder%E5%A4%A7%E5%AE%B6%E5%BA%AD-yellow"/>
</a>
<a href="https://mrdjun.github.io/">
<img alt="Documentation" src="https://img.shields.io/badge/documentation-yes-brightgreen.svg" target="_blank" /></a>
</p>

### ✨ 简介

> FunBoot是一款带有管理后台和App端让您快速开发的脚手架项目 🏠[文档](https://mrdjun.github.io/) ⬇️[下载](https://github.com/mrdjun/fun-boot) 📚[English Document](https://github.com/chaitin/xray/tree/master/docs/en-us/generic)
> 
> FunBoot采用全新的Java Web框架——Spring Boot 2.1.1，省去了如往日的SSH项目中的大量繁琐的XML配置，使得二次开发更加简便。在数据持久层方面选择的是MyBatis开源框架与PageHelper插件，可以快速的实现对单表的增删改查。为什么不选择MyBatisPlus或JPA省去写SQL？原因有二：其一，控制性能。其二，学习SQL性能优化。缓存采用的是性能出众的Redis。
> 
> 在后台权限管理系统的安全方面，采用时下流行的Apache Shiro，可以实现按钮级别的权限控制（若当前用户无此按钮权限，则不显示该按钮），前端页面采用Bootstrap 框架，风格统一，自适应。此外，在FunBoot中还封装了 Bootstrap 的多个插件，若需使用，直接调用即可。
> 
> 在APP端的安全方面，采用的是JJWT框架与Redis做安全认证和权限认证。
> 
> 脚手架自带App端和Admin端的代码生成工具，Admin端带有HTML页面代码生成，两端均自带权限验证，无论你是学习还是做外包项目，这都是你非常好的选择，上手快速，开发高效。
> 
> 随后作者将学习MyBatis的源码，手写一个低配的MyBatis框架，如果您也有兴趣，欢迎加群，一同进步！

## 🎨 App端与Admin端技术概要

| 端名    | 技术                                  |
|:----- |:----------------------------------- |
| app   | JJWT + Redis                        |
| admin | Shiro + Redis+Thymeleaf + Bootstrap |

## 🚀 快速使用

**在使用之前，请务必阅读并同意 [License](https://mrdjun.github.io/generic/LICENSE.md) 文件中的条款，否则请勿安装使用本脚手架。**

## 📝 讨论区

提交BUG或需求等等请务必先阅读 [https://mrdjun.github.io/mrdjun/fun-boot/doc/feedback](guide/feedback.md)

如有问题可以在 GitHub 提 issue, 也可在下方的讨论组里

1. GitHub issue: [https://github.com/mrdjun/fun-boot/issues](https://github.com/mrdjun/fun-boot/issues)
2. QQ 群: 183579482
3. 微信群: 还未创建微信群，敬请期待！
