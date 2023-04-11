package cn.halinjin.mybook.listener;

import cn.halinjin.mybook.bean.Type;
import cn.halinjin.mybook.biz.TypeBiz;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class TypeServletContentListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        //1.获取当前数据库中所有的类型信息
        TypeBiz biz=new TypeBiz();
        List<Type> types=biz.getAll();

        //2.获取application对象
        ServletContext application=servletContextEvent.getServletContext();//拿到ServletContext的接口对象的名字叫application

        //3.将信息存在application对象中
        application.setAttribute("types",types);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
