package com.movision.facade.h5wechat;

import com.movision.mybatis.count.entity.Count;
import com.movision.mybatis.count.service.CountService;
import com.movision.mybatis.post.service.PostService;
import com.movision.utils.oss.MovisionOssClient;
import com.movision.utils.propertiesLoader.PropertiesLoader;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author shuxf
 * @Date 2017/8/18 15:48
 */
@Service
public class WechatH5Facade extends JPanel {
    private static Logger log = LoggerFactory.getLogger(WechatH5Facade.class);
    //下面是模板图片的路径
    String timgurl = PropertiesLoader.getValue("wechat.h5.domain");
    String newurl = PropertiesLoader.getValue("wechat.newh5.domain");//新图片路径
    String newurl2 = PropertiesLoader.getValue("wechat.h5.mofo");//新图片路径
    String headImg = PropertiesLoader.getValue("wechat.erweima.domain");//二维码路径
    String lihunurl = PropertiesLoader.getValue("wechat.lihun.domain");
    String lihunxieyiurl = PropertiesLoader.getValue("wechat.lihunxieyi.domain");

    @Autowired
    private CountService countService;

    public Map<String, Object> imgCompose(String manname, String womanname, int type, String msex, String wsex) {
        Map<String, Object> map = new HashMap<>();
//        public static void exportImg1(){
//            int width = 100;
//            int height = 100;
//            String s = "你好";
//
//            File file = new File("d:/image.jpg");
//
//            Font font = new Font("Serif", Font.BOLD, 10);
//            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            Graphics2D g2 = (Graphics2D)bi.getGraphics();
//            g2.setBackground(Color.WHITE);
//            g2.clearRect(0, 0, width, height);
//            g2.setPaint(Color.RED);
//
//            FontRenderContext context = g2.getFontRenderContext();
//            Rectangle2D bounds = font.getStringBounds(s, context);
//            double x = (width - bounds.getWidth()) / 2;
//            double y = (height - bounds.getHeight()) / 2;
//            double ascent = -bounds.getY();
//            double baseY = y + ascent;
//
//            g2.drawString(s, (int)x, (int)baseY);
//
//            try {
//                ImageIO.write(bi, "jpg", file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

//        (String username,String headImg)
        if (type == 1) {//结婚证
            try {
                InputStream is = new FileInputStream(timgurl);
                map = He(is, manname, womanname, msex, wsex);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ImageFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type == 2) {//离婚证
            try {
                InputStream is = new FileInputStream(lihunurl);
                map = He(is, manname, womanname, msex, wsex);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ImageFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    public Map He(InputStream is, String manname, String womanname, String msex, String wsex) {
        Map map = new HashMap();
        try {

            //通过JPEG图象流创建JPEG数据流解码器
            JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(is);
            //解码当前JPEG数据流，返回BufferedImage对象
            BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();
            //得到画笔对象
            //Graphics g = buffImg.getGraphics();
            Graphics2D g = (Graphics2D) buffImg.getGraphics();
            //创建你要附加的图象。//-----------------------------------------------这一段是将小图片合成到大图片上的代码
            //小图片的路径
            ImageIcon imgIcon = new ImageIcon(headImg);
            //得到Image对象。
            Image img = imgIcon.getImage();
            //将小图片绘到大图片上。
            //5,300 .表示你的小图片在大图片上的位置。
            //g.drawImage(img, 400, 15, null);

            g.fillRect(0, 0, getWidth(), getHeight());
            g.rotate(0, 900, 15);
            g.drawImage(img, 470, 820, this);
            //g.rotate(30);
            //设置颜色。
            g.setColor(Color.BLACK);

            //最后一个参数用来设置字体的大小
            Font f = new Font("宋体", Font.PLAIN, 20);
            Color color = new Color(112, 104, 115);
            Color[] mycolor = {color, Color.LIGHT_GRAY};
            // g.setColor(mycolor);
            g.setFont(f);
            //   平移原点到图形环境的中心
            g.translate(this.getWidth() / 2, this.getHeight() / 2);
            //10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            //g.drawString(msex, 160, 610);//合成男的名字new String(message.getBytes("utf8"),"gbk");
            //g.drawString(manname, 650, 1500);//合成女的名字
            // g.setColor(color);
            for (int i = 0; i < 1; i++) {
                g.rotate(0 * Math.PI / 180, 0, 0);
                g.setPaint(mycolor[i % 2]);
                g.drawString(manname, 130, 625);
                g.drawString(womanname, 130, 765);
                g.drawString(manname, 130, 270);
                g.drawString(msex, 410, 625);
                g.drawString(wsex, 410, 765);

            }
            g.dispose();

            //OutputStream os;

            //os = new FileOutputStream("d:/union.jpg");
            String shareFileName = System.currentTimeMillis() + ".jpg";

            map.put("status", 200);
            map.put("url", shareFileName);
            String url = newurl + shareFileName;
            //  os = new FileOutputStream(shareFileName);
            //创键编码器，用于编码内存中的图象数据。
            //JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
            // en.encode(buffImg);
            ImageIO.write(buffImg, "png", new File(url));//图片的输出路径
            map.put("newurl", newurl2 + "/upload/wechat/" + shareFileName);
            is.close();
            //修改参与次数
            int ta = updateTake(1077);
            map.put("ta", ta);
            //  os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ImageFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 修改参与次数
     *
     * @param id
     * @return
     */
    public int updateTake(int id) {
        int takeCount = countService.updateTakeCount(id);
        return takeCount;
    }

    /**
     * 修改访问次数
     *
     * @param id
     * @return
     */
    public int updateAccessCount(int id) {
        int takeCount = countService.updateAccessCount(id);
        return takeCount;
    }


    /**
     * 离婚协议
     *
     * @param manname
     * @param womanname
     * @param
     * @param
     * @param
     * @return
     */
    public Map<String, Object> imgComposeLi(String manname, String womanname, String content) {
        Map<String, Object> map = new HashMap<>();
        try {
            InputStream is = new FileInputStream(lihunxieyiurl);
            map = LiHun(is, manname, womanname, content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ImageFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 离婚协议
     *
     * @param is
     * @param manname
     * @param womanname
     * @param
     * @param
     * @return
     */
    public Map LiHun(InputStream is, String manname, String womanname, String content) {
        Map map = new HashMap();
        try {
            Calendar calendar = Calendar.getInstance();
            String year = calendar.get(Calendar.YEAR) + "";//年份
            String month = (calendar.get(Calendar.MONTH) + 1) + "";//月份
            String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
            //通过JPEG图象流创建JPEG数据流解码器
            JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(is);
            //解码当前JPEG数据流，返回BufferedImage对象
            BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();
            //得到画笔对象
            //Graphics g = buffImg.getGraphics();
            Graphics2D g = (Graphics2D) buffImg.getGraphics();
            //创建你要附加的图象。//-----------------------------------------------这一段是将小图片合成到大图片上的代码
            //小图片的路径
            ImageIcon imgIcon = new ImageIcon(headImg);
            //得到Image对象。
            Image img = imgIcon.getImage();
            //将小图片绘到大图片上。
            //5,300 .表示你的小图片在大图片上的位置。
            //g.drawImage(img, 400, 15, null);

            g.fillRect(0, 0, getWidth(), getHeight());
            g.rotate(0, 900, 15);
            g.drawImage(img, 80, 880, this);
            //g.rotate(30);
            //设置颜色。
            g.setColor(Color.BLACK);
            log.info(manname);
            log.info(womanname);
            //最后一个参数用来设置字体的大小
            Font f = new Font("方正静蕾简体", Font.PLAIN, 30);
            Color color = new Color(112, 104, 115);
            Color[] mycolor = {color, Color.LIGHT_GRAY};
            // g.setColor(mycolor);
            g.setFont(f);
            //   平移原点到图形环境的中心
            g.translate(this.getWidth() / 2, this.getHeight() / 2);
            //10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            //g.drawString(msex, 160, 610);//合成男的名字new String(message.getBytes("utf8"),"gbk");
            //g.drawString(manname, 650, 1500);//合成女的名字
            // g.setColor(color);
            for (int i = 0; i < 1; i++) {
                g.rotate(5 * Math.PI / 180, 0, 0);
                g.setPaint(mycolor[i % 2]);
                g.drawString(manname, 210, 164);
                g.drawString(womanname, 210, 208);
                g.drawString(manname, 266, 809);
                g.drawString(womanname, 620, 812);
                g.drawString(content, 290, 270);
                g.drawString(year, 113, 850);
                g.drawString(month, 187, 850);
                g.drawString(day, 220, 850);
                g.drawString(year, 455, 853);
                g.drawString(month, 532, 853);
                g.drawString(day, 571, 853);
            }
            g.dispose();

            //OutputStream os;

            //os = new FileOutputStream("d:/union.jpg");
            String shareFileName = System.currentTimeMillis() + ".jpg";

            map.put("status", 200);
            map.put("url", shareFileName);
            String url = newurl + shareFileName;
            //  os = new FileOutputStream(shareFileName);
            //创键编码器，用于编码内存中的图象数据。
            //JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
            // en.encode(buffImg);
            ImageIO.write(buffImg, "png", new File(url));//图片的输出路径
            map.put("newurl", newurl2 + "/upload/wechat/" + shareFileName);
            is.close();
            //修改参与次数
            //int ta = updateTake(1077);
            //map.put("ta", ta);
            //  os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ImageFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }




}