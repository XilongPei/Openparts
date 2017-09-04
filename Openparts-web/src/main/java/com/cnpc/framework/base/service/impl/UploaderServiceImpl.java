package com.cnpc.framework.base.service.impl;

import com.cnpc.framework.base.entity.UserAvatar;
import com.cnpc.framework.base.service.UploaderService;
import com.cnpc.framework.base.service.UserService;
import com.cnpc.framework.utils.PropertiesUtil;
import com.cnpc.framework.utils.StrUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * 附件上传控制器
 *
 * @author cnpc
 */
@Service("uploaderService")
public class UploaderServiceImpl extends BaseServiceImpl implements UploaderService {

    @Resource
    private UserService userService;

    /**
     * 创建文件
     *
     * @param file
     * @return
     */
    public File createFile(MultipartFile file) {

        String dirPath = PropertiesUtil.getValue("uploadPath");
        return createFile(file, dirPath);
    }

    public File createFile(MultipartFile file, String dirPath) {

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String filePath = dirPath + "/" + (new Date().getTime()) + "_" + file.getOriginalFilename();
        File newFile = new File(filePath);
        try {
            InputStream ins = file.getInputStream();
            OutputStream os = new FileOutputStream(newFile);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = ins.read(buffer, 0, 1024)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFile;
    }


    public String saveAvatar(String userId, String fileName, String filePath, String dirPath) {
        UserAvatar avatar = null;
        if(!StrUtil.isEmpty(userId)&&!userId.equals("0"))
                avatar=userService.getAvatarByUserId(userId);
        String avatar_id=avatar==null?null:avatar.getId();
        //图片替换
        if (avatar != null) {
            File file = new File(dirPath + avatar.getSrc());
            if (file.exists())
                file.delete();
            avatar.setName(fileName);
            avatar.setSrc(filePath);
            avatar.setUpdateDateTime(new Date());
            this.update(avatar);
        }
        // 新增图片
        else {
            avatar = new UserAvatar();
            avatar.setName(fileName);
            avatar.setSrc(filePath);
            avatar.setCreateDateTime(new Date());
            avatar.setUserId(userId);
            avatar_id=this.save(avatar).toString();
        }
        return avatar_id;
    }


}
