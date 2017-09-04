package com.cnpc.framework.base.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface UploaderService extends BaseService {

    /**
     * 创建文件
     *
     * @param file
     * @return
     */
    public File createFile(MultipartFile file);


    /**
     * 保存头像信息
     *
     * @param userId          用户ID
     * @param fileName       文件名
     * @param filePath       文件路径(相对路径)
     * @param dirPath        系统路径(绝对路径)
     */
    public String saveAvatar(String userId, String fileName, String filePath, String dirPath);

    /**
     * 创建文件
     *
     * @param file    文件
     * @param dirPath 文件存储路径
     * @return
     */
    public File createFile(MultipartFile file, String dirPath);

}
