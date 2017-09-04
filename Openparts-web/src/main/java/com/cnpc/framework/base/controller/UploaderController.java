package com.cnpc.framework.base.controller;


import com.cnpc.framework.base.entity.SysFile;
import com.cnpc.framework.base.entity.User;
import com.cnpc.framework.base.pojo.AvatarResult;
import com.cnpc.framework.base.pojo.FileResult;
import com.cnpc.framework.base.pojo.MarkDownResult;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.UploaderService;
import com.cnpc.framework.util.SecurityUtil;
import com.cnpc.framework.utils.DateUtil;
import com.cnpc.framework.utils.FileUtil;
import com.cnpc.framework.utils.PropertiesUtil;
import com.cnpc.framework.utils.StrUtil;
import org.apache.commons.fileupload.util.Streams;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/file")
public class UploaderController {

    private static Logger logger= LoggerFactory.getLogger(UploaderController.class);

    //previewFileIconSettings
    public static Map fileIconMap=new HashMap();
    @Resource
    private UploaderService uploaderService;

    static {
        fileIconMap.put("doc" ,"<i class='fa fa-file-word-o text-primary'></i>");
        fileIconMap.put("docx","<i class='fa fa-file-word-o text-primary'></i>");
        fileIconMap.put("xls" ,"<i class='fa fa-file-excel-o text-success'></i>");
        fileIconMap.put("xlsx","<i class='fa fa-file-excel-o text-success'></i>");
        fileIconMap.put("ppt" ,"<i class='fa fa-file-powerpoint-o text-danger'></i>");
        fileIconMap.put("pptx","<i class='fa fa-file-powerpoint-o text-danger'></i>");
        fileIconMap.put("jpg" ,"<i class='fa fa-file-photo-o text-warning'></i>");
        fileIconMap.put("pdf" ,"<i class='fa fa-file-pdf-o text-danger'></i>");
        fileIconMap.put("zip" ,"<i class='fa fa-file-archive-o text-muted'></i>");
        fileIconMap.put("rar" ,"<i class='fa fa-file-archive-o text-muted'></i>");
        fileIconMap.put("default" ,"<i class='fa fa-file-o'></i>");
    }

    //从setting.properties文件中注入文件相对目录（相对目录为显示文件）
    //@Value("${uploaderPath}") 只有配置@Config才能注入
    private static final String uploaderPath=PropertiesUtil.getValue("uploaderPath");


    @RequestMapping("/avatarUpload")
    @ResponseBody
    public AvatarResult avatarUpload(String userId, HttpServletRequest httpRequest, HttpSession session) throws Exception {

        MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpRequest;
        Map<String, MultipartFile> fileMap = request.getFileMap();
        String contentType = request.getContentType();
        if (contentType.indexOf("multipart/form-data") >= 0) {
            AvatarResult result = new AvatarResult();
            result.setAvatarUrls(new ArrayList());
            result.setSuccess(false);
            result.setMsg("Failure!");

            // 定义一个变量用以储存当前头像的序号
            int avatarNumber = 1;
            User user = uploaderService.get(User.class, userId);
            if (user == null) {
                user = new User();
                user.setName("new");
            }
            // 文件名
            String fileName = user.getName() + "_" + (new Date()).getTime() + ".jpg";
            String relPath = PropertiesUtil.getValue("avatarPath");
            String dirPath = request.getRealPath("/");

            String initParams = "";

            BufferedInputStream inputStream;
            BufferedOutputStream outputStream;
            for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator(); it.hasNext(); avatarNumber++) {
                File filePath = new File(dirPath + relPath);
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                Map.Entry<String, MultipartFile> entry = it.next();
                MultipartFile mFile = entry.getValue();
                String fieldName = entry.getKey();
                Boolean isSourcePic = fieldName.equals("__source"); // 是否是原始图片域名称
                // 文件名，如果是本地或网络图片为原始文件名（不含扩展名）、如果是摄像头拍照则为 *FromWebcam
                // String name = fileItem.getName();
                // 当前头像基于原图的初始化参数（即只有上传原图时才会发送该数据），用于修改头像时保证界面的视图跟保存头像时一致，提升用户体验度。
                // 修改头像时设置默认加载的原图url为当前原图url+该参数即可，可直接附加到原图url中储存，不影响图片呈现。
                if (fieldName.equals("__initParams")) {
                    inputStream = new BufferedInputStream(mFile.getInputStream());
                    byte[] bytes = new byte[mFile.getInputStream().available()];
                    inputStream.read(bytes);
                    initParams = new String(bytes, "UTF-8");
                    inputStream.close();
                } else if (isSourcePic || fieldName.startsWith("__avatar")) {
                    String virtualPath = dirPath + relPath + "\\" + fileName;
                    if (avatarNumber > 1) {
                        fileName = avatarNumber + fileName;
                        virtualPath = dirPath + relPath + "\\" + fileName;
                    }
                    // 原始图片(file 域的名称：__source，如果客户端定义可以上传的话，可在此处理）。
                    if (isSourcePic) {
                        fileName = "source" + fileName;
                        virtualPath = dirPath + relPath + "\\" + fileName;
                        result.setSourceUrl(relPath + "/" + fileName);
                    }
                    // 头像图片(file 域的名称：__avatar1,2,3...)。
                    else {
                        result.getAvatarUrls().add(relPath + "/" + fileName);
                    }

                    inputStream = new BufferedInputStream(mFile.getInputStream());
                    outputStream = new BufferedOutputStream(new FileOutputStream(virtualPath.replace("/", "\\")));
                    Streams.copy(inputStream, outputStream, true);
                    inputStream.close();
                    outputStream.flush();
                    outputStream.close();
                    // 保存图片信息
                    result.setMsg(uploaderService.saveAvatar(userId, fileName, relPath + File.separator + fileName, dirPath));
                }

            }
            if (result.getSourceUrl() != null) {
                result.setSourceUrl(result.getSourceUrl() + initParams);
            }
            result.setSuccess(true);
            return result;
        }
        return null;
    }

    /**
     * markdown组件上传图片
     */
    @RequestMapping(value = "/markdownUpload", method = RequestMethod.POST)
    @ResponseBody
    public MarkDownResult markdownUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "editormd-image-file", required = false) MultipartFile attach) {
        try {
            String relPath = PropertiesUtil.getValue("markdownPath");
            String dirPath = request.getRealPath("/");
            //不存在目录 则创建
            File filePath = new File(dirPath + relPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            File realFile = new File(dirPath + relPath + File.separator + attach.getOriginalFilename());
            //上传的文件已存在 则对新上传的文件重命名
            if (realFile.exists()) {
                String fileName = DateUtil.format(new Date(), "yyyyMMddHHmmss") + "_" + attach.getOriginalFilename();
                realFile = new File(dirPath + relPath + File.separator + fileName);
            }
            boolean iscreate = FileUtil.copyInputStreamToFile(attach.getInputStream(), realFile);
            if (iscreate) {
                String url = relPath + File.separator + realFile.getName();
                url = request.getAttribute("basePath").toString() + url.replaceAll("\\\\", "/");
                return new MarkDownResult(1, "上传成功", url);
            } else {
                return new MarkDownResult(0, "上传失败", null);
            }
        } catch (IOException ex) {
            return new MarkDownResult(0, "上传失败:原因" + ex.getMessage().toString(), null);
        }
    }

    /**
     * 跳转到通用文件上传窗口
     * @return
     */
    @RequestMapping(value="/uploader",method = RequestMethod.GET)
    public String uploader(String config,HttpServletRequest request){
        request.setAttribute("config",config);
        return "base/file/file_uploader";
    }


    /**
     * 通用文件上传接口，存储到固定地址，以后存储到文件服务器地址
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public FileResult uploadFile(@RequestParam(value = "file", required = false) MultipartFile file,
                              HttpServletRequest request, HttpServletResponse response) throws IOException {
        MultipartFile[] files=new MultipartFile[]{file};

        return uploadMultipleFile(files,request,response);
    }

    /**
     * 多文件上传，用于uploadAsync=false(同步多文件上传使用)
     * @param files
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
    @ResponseBody
    public FileResult uploadMultipleFile(@RequestParam(value = "file", required = false) MultipartFile[] files,
                                         HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("the num of file:"+files.length);

        FileResult msg = new FileResult();

        ArrayList<Integer> arr = new ArrayList<>();
        //缓存当前的文件
        List<SysFile> fileList=new ArrayList<>();
        String dirPath = request.getRealPath("/");
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];

            if (!file.isEmpty()) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    File dir = new File(dirPath+uploaderPath);
                    if (!dir.exists())
                        dir.mkdirs();
                    //这样也可以上传同名文件了
                    String filePrefixFormat="yyyyMMddHHmmssS";
                    System.out.println(DateUtil.format(new Date(),filePrefixFormat));
                    String savedName=DateUtil.format(new Date(),filePrefixFormat)+"_"+file.getOriginalFilename();
                    String filePath=dir.getAbsolutePath() + File.separator + savedName;
                    File serverFile = new File(filePath);
                    //将文件写入到服务器
                    //FileUtil.copyInputStreamToFile(file.getInputStream(),serverFile);
                    file.transferTo(serverFile);
                    SysFile sysFile=new SysFile();
                    sysFile.setFileName(file.getOriginalFilename());
                    sysFile.setSavedName(savedName);
                    sysFile.setCreateDateTime(new Date());
                    sysFile.setUpdateDateTime(new Date());
                    sysFile.setCreateUserId(SecurityUtil.getUserId());
                    sysFile.setDeleted(0);
                    sysFile.setFileSize(file.getSize());
                    sysFile.setFilePath(uploaderPath+File.separator+savedName);
                    uploaderService.save(sysFile);
                    fileList.add(sysFile);
                    /*preview.add("<div class=\"file-preview-other\">\n" +
                            "<span class=\"file-other-icon\"><i class=\"fa fa-file-o text-default\"></i></span>\n" +
                            "</div>");*/

                    logger.info("Server File Location=" + serverFile.getAbsolutePath());
                } catch (Exception e) {
                    logger.error(   file.getOriginalFilename()+"上传发生异常，异常原因："+e.getMessage());
                    arr.add(i);
                } finally {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                }
            } else {
                arr.add(i);
            }
        }

        if(arr.size() > 0) {
            msg.setError("文件上传失败！");
            msg.setErrorkeys(arr);
        }
        FileResult preview=getPreivewSettings(fileList,request);
        msg.setInitialPreview(preview.getInitialPreview());
        msg.setInitialPreviewConfig(preview.getInitialPreviewConfig());
        msg.setFileIds(preview.getFileIds());
        return msg;
    }

    //删除某一项文件
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result delete(String id,HttpServletRequest request){
        SysFile sysFile=uploaderService.get(SysFile.class,id);
        String dirPath=request.getRealPath("/");
        FileUtil.delFile(dirPath+uploaderPath+File.separator+sysFile.getSavedName());
        uploaderService.delete(sysFile);
        return new Result();
    }

    @RequestMapping(value="/download/{id}",method = RequestMethod.GET)
    public void downloadFile(@PathVariable("id") String id,HttpServletRequest request,HttpServletResponse response) throws IOException {
        SysFile sysfile = uploaderService.get(SysFile.class, id);

        InputStream is = null;
        OutputStream os = null;
        File file = null;
        try {
            // PrintWriter out = response.getWriter();
            if (sysfile != null)
                file = new File(request.getRealPath("/")+sysfile.getFilePath());
            if (file != null && file.exists() && file.isFile()) {
                long filelength = file.length();
                is = new FileInputStream(file);
                // 设置输出的格式
                os = response.getOutputStream();
                response.setContentType("application/x-msdownload");
                response.setContentLength((int) filelength);
                response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(sysfile.getFileName().getBytes("GBK"),// 只有GBK才可以
                        "iso8859-1") + "\"");

                // 循环取出流中的数据
                byte[] b = new byte[4096];
                int len;
                while ((len = is.read(b)) > 0) {
                    os.write(b, 0, len);
                }
            } else {
                response.getWriter().println("<script>");
                response.getWriter().println(" modals.info('文件不存在!');");
                response.getWriter().println("</script>");
            }

        } catch (IOException e) {
            // e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * 获取字体图标map,base-file控件使用
     */
    @RequestMapping(value="/icons",method = RequestMethod.POST)
    @ResponseBody
    public Map getIcons(){
        return fileIconMap;
    }

    /**
     * 根据文件名获取icon
     * @param fileName 文件
     * @return
     */
    public String getFileIcon(String fileName){
        String ext= StrUtil.getExtName(fileName);
        return fileIconMap.get(ext)==null?fileIconMap.get("default").toString():fileIconMap.get(ext).toString();
    }

    /**
     * 根据附件IDS 获取文件
     * @param fileIds
     * @param request
     * @return
     */
    @RequestMapping(value="/getFiles",method = RequestMethod.POST)
    @ResponseBody
    public FileResult getFiles(String fileIds,HttpServletRequest request){
        List<SysFile> fileList=new ArrayList<>();
        if(!StrUtil.isEmpty(fileIds)) {
            String[] fileIdArr = fileIds.split(",");
            DetachedCriteria criteria = DetachedCriteria.forClass(SysFile.class);
            criteria.add(Restrictions.in("id", fileIdArr));
            criteria.addOrder(Order.asc("createDateTime"));
            fileList = uploaderService.findByCriteria(criteria);
        }
        return getPreivewSettings(fileList,request);
    }


    /**
     * 回填已有文件的缩略图
     * @param fileList 文件列表
     * @param request
     * @return initialPreiview initialPreviewConfig fileIds
     */
    public FileResult getPreivewSettings(List<SysFile> fileList,HttpServletRequest request){
        FileResult fileResult=new FileResult();
        List<String> previews=new ArrayList<>();
        List<FileResult.PreviewConfig> previewConfigs=new ArrayList<>();
        //缓存当前的文件
        String dirPath = request.getRealPath("/");
        String[] fileArr=new String[fileList.size()];
        int index=0;
        for (SysFile sysFile : fileList) {
            //上传后预览 TODO 该预览样式暂时不支持theme:explorer的样式，后续可以再次扩展
            //如果其他文件可预览txt、xml、html、pdf等 可在此配置
            if(FileUtil.isImage(dirPath+uploaderPath+File.separator+sysFile.getSavedName())) {
                previews.add("<img src='." + sysFile.getFilePath().replace(File.separator, "/") + "' class='file-preview-image kv-preview-data' " +
                        "style='width:auto;height:160px' alt='" + sysFile.getFileName() + " title='" + sysFile.getFileName() + "''>");
            }else{
                previews.add("<div class='kv-preview-data file-preview-other-frame'><div class='file-preview-other'>" +
                        "<span class='file-other-icon'>"+getFileIcon(sysFile.getFileName())+"</span></div></div>");
            }
            //上传后预览配置
            FileResult.PreviewConfig previewConfig=new FileResult.PreviewConfig();
            previewConfig.setWidth("120px");
            previewConfig.setCaption(sysFile.getFileName());
            previewConfig.setKey(sysFile.getId());
            // previewConfig.setUrl(request.getContextPath()+"/file/delete");
            previewConfig.setExtra(new FileResult.PreviewConfig.Extra(sysFile.getId()));
            previewConfig.setSize(sysFile.getFileSize());
            previewConfigs.add(previewConfig);
            fileArr[index++]=sysFile.getId();
        }
        fileResult.setInitialPreview(previews);
        fileResult.setInitialPreviewConfig(previewConfigs);
        fileResult.setFileIds(StrUtil.join(fileArr));
        return fileResult;
    }






}
