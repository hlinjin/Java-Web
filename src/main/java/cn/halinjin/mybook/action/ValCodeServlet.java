package cn.halinjin.mybook.action;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


@WebServlet(urlPatterns = "/code",loadOnStartup = 1)//服务器启动了就把Servlet准备好
public class ValCodeServlet extends HttpServlet {
    Random random=new Random();
    /**
    * 获取随机字符串
    * @return*/
    private String getRandomStr(){
        String str="1234567890ABCDPQRSYUefghjkmnxzv";
        StringBuilder sb=new StringBuilder();  //需要会变化的字符串，创建StringBuilder对象
        for(int i=0;i<=3;i++){                 //生成四个随机字符
            int index=random.nextInt(str.length()); //下标通过随机我的字符串长度
            char letter=str.charAt(index);     //通过随机的下标字符串长度每次拿到一个字符
            sb.append(letter);                 //把字符追加到sb的末尾也就是StringBuilder的末尾
        }
        return sb.toString();
    }

    /**
    * 获取背景色0-255
    * @return*/
    private Color getBackColor(){
        int red=random.nextInt(256);
        int green=random.nextInt(256);
        int blue=random.nextInt(256);

        return new Color(red,green,blue);
    }

    /**
     * 获取前景色
     * @param bgColor
     * @return
     */
    private Color getForeColor(Color bgColor){
        int red=255-bgColor.getRed();
        int green=255-bgColor.getGreen();
        int blue=255-bgColor.getRed();

        return new Color(red,green,blue);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置响应格式为图片：jpg
        resp.setContentType("image/jpg");/*设置MIME类型对应的.jpg的格式在Tomcat安装目录下有一个conf文件下的web.xml按Ctrl+f输入
                                          * 对应的格式它就会展示给你看MIME类型应该设置成什么格式，比如.jpg就是image/jpg*/
        //2.图片对象
        BufferedImage bufferedImage=new BufferedImage(50,23,BufferedImage.TYPE_INT_RGB);
        //3.获取画布对象
        Graphics g= bufferedImage.getGraphics();
        //4.设置背景颜色
        Color bgColor=getBackColor();
        g.setColor(bgColor);   //把bgColor给背景颜色他才会用背景颜色去填充长方形
        //5.画背景
        g.fillRect(0,5,50,23);
        //6.设置前颜色
        Color foreColor=getForeColor(bgColor);
        g.setColor(foreColor);
        //设置字体
        g.setFont(new Font("黑体",Font.BOLD,20));
        //7.将随机字符串存到session*
        String randomStr=getRandomStr();
        HttpSession session=req.getSession();
        session.setAttribute("code",randomStr);
        g.drawString(randomStr,5,20);
        //8.噪点(30个白色正方形)
        for (int i=0;i<30;i++){
            g.setColor(Color.white);
            int x=random.nextInt(50);
            int y=random.nextInt(23);
            g.fillRect(x,y,1,1);
        }
        //9.将这个张内存的图片输出到响应流
        ServletOutputStream sos= resp.getOutputStream();
        ImageIO.write(bufferedImage,"jpeg",sos);
    }
}
