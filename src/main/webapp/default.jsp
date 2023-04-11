<%--
  Created by IntelliJ IDEA.
  User: halinjin
  Date: 2022/12/15
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>图书管理系统</title>
    <link rel="stylesheet" href="./css/default.css">
</head>
<body id="body-pd">
<div class="l-navbar" id="navbar">
    <nav class="nav">
        <div>
            <div class="nav_brand">
                <!-- <ion-icon name="menu-outline" class="nav_toggle" id="nav_toggle"></ion-icon> -->


                <ion-icon name="person-outline" class="nav_toggle" id="nav-toggle"></ion-icon>
                <a class="nav_logo">${user.name}</a>
            </div>
            <div class="nav_list">
                <div class="nav_link collapse">
                    <ion-icon name="folder-outline" class="nav_icon" ></ion-icon>
                    <span class="nav_name">图书管理</span>
                    <ion-icon name="chevron-down-outline" class="collapse__link" ></ion-icon>
                    <ul class="collapse_menu">
                        <a href="book_add.jsp" class="collapse__sublink" target="Frame">添加图书</a>
                        <a href="book?type=query&pageIndex=1" class="collapse__sublink" target="Frame">图书列表</a>
                    </ul>
                </div>

                <div class="nav_link collapse">
                    <ion-icon name="folder-outline" class="nav_icon" ></ion-icon>
                    <span class="nav_name">图书类型管理</span>
                    <ion-icon name="chevron-down-outline" class="collapse__link" ></ion-icon>
                    <ul class="collapse_menu">
                        <li><a href="type_add.jsp" class="collapse__sublink" target="Frame">添加类型</a></li>
                        <li><a href="type_list.jsp" class="collapse__sublink" target="Frame">类型列表</a></li>
                    </ul>
                </div>

                <a href="./set_pwd.jsp" class="nav_link" target="Frame">
                    <ion-icon name="settings-outline" class="nav_icon" ></ion-icon>
                    <span class="nav_name">修改密码</span>
                </a>
            </div>
        </div>
        <a href="user?type=exit" class="nav_link">
            <ion-icon name="log-out-outline" class="nav_icon" ></ion-icon>
            <span class="nav_name">退出登陆</span>
        </a>
    </nav>
</div>
<div>
    <iframe name="Frame" style="width: 100%;height: 720px;" frameborder="0"></iframe>
</div>


<script src="https://unpkg.com/ionicons@5.1.2/dist/ionicons.js"></script>
<script src="./js/default.js"></script>
</body>
</html>
