**[中文](https://github.com/Jude95/KeyChain/blob/master/README.md)** | **[English](https://github.com/Jude95/KeyChain/blob/master/README-en.md)**  

# 密钥盒子

数据安全问题非常严重的现在，您需要高强度密钥与物理隔离于网络的密钥存储。密钥盒子目的就在此。您可以放心使用它保存您的账号。不用担心泄漏。   
密钥盒子没有申请任何权限，意味着它无法将您的密码上传网络，也不能存到您的SD卡。数据加密存储于自己的私有本地数据库。密钥盒子使用AES(高级加密标准)算法进行加密，没有加密密钥破解需花数亿年。   
APP不会保存加密密钥，而是将启动时您输入的解锁密码作为加密密钥，而验证您的解锁密码则是直接尝试用它解密设置解锁密码时加密的特定文本。所以解锁密码也是同样安全的。  

# 下载
[直接下载](http://7xn7nj.com2.z0.glb.qiniucdn.com/passwordbox1.5.apk)

# 截图
![01](https://raw.githubusercontent.com/Jude95/KeyChain/master/screenshot_zh/01.png)
![02](https://raw.githubusercontent.com/Jude95/KeyChain/master/screenshot_zh/02.png)
![03](https://raw.githubusercontent.com/Jude95/KeyChain/master/screenshot_zh/03.png)
![04](https://raw.githubusercontent.com/Jude95/KeyChain/master/screenshot_zh/04.png)

# 开源库使用
[Beam](https://github.com/Jude95/Beam)  
[SwipeBackHelper](https://github.com/Jude95/SwipeBackHelper)  
[EasyRecyclerView](https://github.com/Jude95/EasyRecyclerView)  
[TAGView](https://github.com/Jude95/TAGView)   
[Utils](https://github.com/Jude95/Utils)  
[material-dialogs](https://github.com/afollestad/material-dialogs)  
[gson](https://github.com/google/gson)  
[material-ripple](https://github.com/balysv/material-ripple)  
[jpinyin](https://github.com/stuxuhai/jpinyin)  
[butterknife](https://github.com/JakeWharton/butterknife)  

License
-------

    Copyright 2015 Jude

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
