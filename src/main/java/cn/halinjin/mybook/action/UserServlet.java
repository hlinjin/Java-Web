package cn.halinjin.mybook.action;


import cn.halinjin.mybook.bean.User;
import cn.halinjin.mybook.biz.UserBiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    //构建UserBiz的对象
    UserBiz userBiz=new UserBiz();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);  //核心代码都写在doPost所以就让doGet直接调用doPost方法
    }

    /**
     * 一个Servlet下面只对应一个地址/user，但是我们可以通过配置参数?type=xx
     * 让一个Servlet可以完成多种不同的请求但是都需要和用户有关
     * /user?type=login 登录   '/'代表的是项目的根目录下的webapp文件
     * webapp
     *  |-login.html
     *  |-user?type=login 这个相当于是访问网络资源的路径
     *  |-index.jsp
     * /user?type=exit 安全退出
     * /user?type=modifyPwd 修改密码
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter(); //PrintWriter响应输出流对象，用来提示错误，帮助我打印信息

        //1.判断用户请求的类型为login
        String method = req.getParameter("type");
        switch (method) {
            case "login":
                //2.从login.html中获取用户名和密码,验证码
                String name = req.getParameter("name");
                String pwd = req.getParameter("pwd");
                String userCode = req.getParameter("valcode");

                //2.2提取session中的验证码，进行判断
                String code = session.getAttribute("code").toString();
                //不区分大小写
                if (!code.equalsIgnoreCase(userCode)) {
                    out.println("<script>alert('验证码输入错误');location.href='login.html';</script>");
                    return;
                }
                //3.调用UserBiz的getUser方法，根据用户名和密码获取对应的用户对象
                User user = userBiz.getUser(name, pwd);
                //4.判断用户是否为null：
                if (user == null) {
                    // 4.1如果null表示用户名或密码不正确，提示错误，回到登录页面。
                    out.println("<script>alert('用户名或密码不存在');location.href='login.html';</script>");
                } else {
                    // /4.2非空：表示登录成功，将用户对象保存到session中，提示登录成功后，将页面跳转到index.jsp。
                    session.setAttribute("user", user);//user-->Object
                    out.println("<script>alert('登录成功');location.href='default.jsp';</script>");
                }
                break;
            case "exit":
                //验证用户是否登录
                if (session.getAttribute("user")==null){
                    out.println("<script>alert('请登录');parent.window.location.href='login.html';</script>");
                    return;
                }

                //清除session
                session.invalidate();
                //跳转到login.html(框架中需要回去)
                out.println("<script>location.href='login.html';</script>");
                break;
            case "modifyPwd":
                //验证用户是否登录
                if (session.getAttribute("user")==null){
                    out.println("<script>alert('请登录');parent.window.location.href='login.html';</script>");
                    return;
                }

                //修改密码
                //1.获取用户输入的新的密码
                String newPwd = req.getParameter("newpwd");
                //2.获取用户的编号 -session
                long id = ((User) session.getAttribute("user")).getId();//向下转型成User对象拿到id存到long类型中
                //3.调用Biz层方法
                int count = userBiz.modifyPwd(id, newPwd);

                //4.响应 -可以参考exit
                if (count > 0) {
                    out.println("<script>alert('密码修改成功');parent.window.location.href='login.html';</script>");
                } else {
                    out.println("<script>alert('密码修改失败');</script>");
                }
                break;
        }

    }
}





