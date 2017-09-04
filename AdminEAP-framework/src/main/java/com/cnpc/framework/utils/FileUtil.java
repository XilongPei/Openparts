package com.cnpc.framework.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by cnpc on 2016/12/9.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
public class FileUtil {

    /**
     * 删除文件夹里面的所有文件
     *
     * @param path 文件夹路径 如 c:/fqf
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
            }
        }
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹路径及名称 如c:/fqf
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // 删除空文件夹

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath 源文件路径
     * @param newPath 复制后路径
     * @return 文件大小
     */
    public static int copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
            return bytesum;
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 复制文件流到新的文件
     *
     * @param inStream 文件流
     * @param file     新文件
     * @return 是否复制成功
     */
    public static boolean copyInputStreamToFile(final InputStream inStream, File file) throws IOException {
        int bytesum = 0;
        int byteread = 0;
        byte[] buffer = new byte[1024];
        FileOutputStream fs = new FileOutputStream(file);
        while ((byteread = inStream.read(buffer)) != -1) {
            bytesum += byteread; //字节数 文件大小
            fs.write(buffer, 0, byteread);
        }
        inStream.close();
        fs.close();
        return true;
    }

    /**
     * 删除指定路径下的文件
     *
     * @param filePathAndName 文件路径
     */
    public static void delFile(String filePathAndName) {
        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            java.io.File myDelFile = new java.io.File(filePath);
            myDelFile.delete();

        } catch (Exception e) {
            System.out.println("删除文件操作出错");
            e.printStackTrace();
        }

    }

    /**
     * 判断文件是否是图像文件
     */
    public static boolean isImage(String name) {
        boolean valid = true;
        try {
            Image image = ImageIO.read(new File(name));
            if (image == null) {
                valid = false;
                System.out.println("The file" + name + "could not be opened , it is not an image");
            }
        } catch (IOException ex) {
            valid = false;
            System.out.println("The file" + name + "could not be opened , an error occurred.");
        }
        return valid;
    }


    public static String generateZipFile(String basePath, String zipFileName, String... fileNames) {
        byte[] buffer = new byte[1024];
        String strZipName = basePath + zipFileName;
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipName));
            for (String fileName : fileNames) {
                File file = new File(basePath + fileName);
                FileInputStream fis = new FileInputStream(file);
                out.putNextEntry(new ZipEntry(file.getName()));
                int len;
                //读入需要下载的文件的内容，打包到zip文件

                while ((len = fis.read(buffer)) > 0) {

                    out.write(buffer, 0, len);

                }
                out.closeEntry();
                fis.close();
            }
            out.close();
            return strZipName;
        }catch (IOException ex){
            return null;
        }
    }
}
