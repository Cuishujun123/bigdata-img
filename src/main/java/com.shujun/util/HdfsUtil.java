package com.shujun.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 大数据分布式文件系统工具类
 *
 * @Author CuiShujun
 * @Date 2019/6/26
 */
public class HdfsUtil {

    private static FileSystem fs = null;

    static {
        // 构建配置信息
        Configuration conf = new Configuration();
        // 赋值
        conf.set("fs.defaultFS","hdfs://ip:port");
        // 操作分布式文件系统
        try {
            fs = FileSystem.get(new URI("hdfs://ip:port"), conf, "hdfs");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 分布式文件上传
     */
    public void FileUpload() {
        try {
            fs.copyFromLocalFile(new Path("图片的本地地址"), new Path("hdfs保存位置"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
