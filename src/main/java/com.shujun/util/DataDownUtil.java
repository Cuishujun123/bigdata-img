package com.shujun.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

/**
 * 大数据大文件批量处理工具类
 *
 * @Author CuiShujun
 * @Date 2019/6/26
 */
public class DataDownUtil {

    /**
     * java 人口
     * @param args
     */
    public static void main(String[] args) {
        String url = "https://www.douban.com";
        String encoding = "utf-8";

        //1、根据url地址和网页编码集获取网页的源代码
        String html = getHtmlResourceByUrl(url, encoding);

        //2、将图像下载到服务器
        dowmImg(html);
    }

    /**
     * 根据url地址和网页编码集获取网页的源代码
     *
     * @param url
     * @param encoding
     * @return
     */
    public static String getHtmlResourceByUrl(String url, String encoding) {
        URL urlObj = null;
        InputStreamReader isr = null;
        BufferedReader bf = null;

        // 存储源代码的容器
        StringBuffer buffer = new StringBuffer();

        try {
            // 建立网络来连接
            urlObj = new URL(url);
            // 打开网络连接
            URLConnection connection = urlObj.openConnection();
            // 建立文件的输入流
            isr = new InputStreamReader(connection.getInputStream());
            // 建立文件缓冲流
            bf = new BufferedReader(isr);

            String temp = null;
            while (null != (temp = bf.readLine())) {
                // 一边读、一边写
                buffer.append(temp);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != isr) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return buffer.toString();
    }

    /**
     * 图像批量下载到服务器
     *
     * @param imgUrl
     * @param filePath
     */
    public static void getImg(String imgUrl, String filePath) {
        FileOutputStream fos = null;
        InputStream isr = null;

        // 创建文件目录
        File files = new File(filePath);
        if (!files.exists()) {
            files.mkdirs();
        }
        // 获取文件后缀名
        String fileName = imgUrl.substring(imgUrl.lastIndexOf("/"));
        try {
            // 根据网络图像url地址
            URL url = new URL(imgUrl);
            URLConnection conn = url.openConnection();
            // 获取地址输入流
            isr = conn.getInputStream();
            // 创建文件
            File file = new File(filePath + fileName);
            fos = new FileOutputStream(file);

            int temp = 0;
            while (-1 != (temp = isr.read())) {
                fos.write(temp);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != isr) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void dowmImg(String html) {
        Document document = Jsoup.parse(html);
        // 获取网页中所有图片标签<img src='' alt='' width='' hright=''>
        Elements img = document.getElementsByTag("img");
        Iterator<Element> iterator = img.iterator();
        while (iterator.hasNext()) {
            Element ele = iterator.next();
            // 获取标签中某个属性
            String src = ele.attr("src");
            // 获取包含http开头并且是JPG或PNG格式的图片
            if (src.startsWith("http") && src.indexOf("jpg") != -1) {
                // 下载文件
                getImg(src, "D:\\img");
            }
        }

    }
}
