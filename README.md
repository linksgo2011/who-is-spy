# who-is-spy
A game for team building 

[![CircleCI](https://circleci.com/gh/linksgo2011/who-is-spy.svg?style=svg)](https://circleci.com/gh/linksgo2011/who-is-spy)


# CI

https://circleci.com/gh/linksgo2011/who-is-spy

# 入口

http://spygame.printf.cn:8086/

# how to run 

- download h2 
- run `./h2.sh` 
- `./gadlew bootrun`

# Done

- 临时数据库
- 房主
    - 创建房间
    - 查看游戏信息（房主）
    - 开始游戏
    - 开始投票
    - 开始下一轮
   
- 玩家
   - 进入房间
   - 查看游戏信息（玩家）
   - 投票

# version2

- 房主
    - 创建词库 // will remove

# Phrase1

- 房主
    - 踢人
    - 结束游戏
    - 开始游戏（升级）
        - 指定角色分配规则（spy的数量）
- 玩家
   - 投票详情
     
- 词库贡献者
    - 贡献词库（只是接受英文）
    
- UX微调
- 帮助
    - 游戏规则说明
    - 软件操作指南

# deadline

12.25 圣诞节前 


# how to contribute this project 

## 工作流程

1.联系我们设置权限
2.创建Pull request到master分支
3.检查CI是否通过，然后合并代码

## 可选设置

安装 Zen hub查看卡墙 https://chrome.google.com/webstore/detail/zenhub-for-github/ogcgkffhplmphkaahpmffcafajaocjbd?hl=en-US

  
## 进入H2 embed的数据管理平台

访问 http://{hostname}/h2-console，进入h2的管理平台然后输入和codebase中同样的配置即可

