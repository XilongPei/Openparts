package com.cnpc.framework.base.pojo;

import java.util.List;
import java.util.Map;

/**
 * Created by billJiang on 2017/3/7.
 * e-mail:475572229@qq.com  qq:475572229
 * 文件上传结果
 * see http://plugins.krajee.com/file-input#ajax-uploads
 */
public class FileResult {

    private String error;
    private List<Integer> errorkeys;
    private List<String> initialPreview;
    private List<PreviewConfig> initialPreviewConfig;

    //private List<Object> initialPreviewThumbTags;*/

    /**
     *  whether to append the content to the initialPreview if you already set an initialPreview on INIT.
     *  If not set this defaults to true. If set to false, the plugin will overwrite the initialPreview content.
     */
    private boolean append;
    //自定义 已上传附件ID
    private String fileIds;

    public FileResult(){
        append=true;
        //status=Status.SUCCESS;
    }


    public static class PreviewConfig {
        String caption;
        String width;
        String url;
        String key;
        Extra extra;
        Long size;

        public static class Extra{
            String id;
            public Extra(){

            }
            public Extra(String id){
                this.id=id;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public void setSize(Long size) {
            this.size = size;
        }

        public Long getSize() {
            return size;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Extra getExtra() {
            return extra;
        }

        public void setExtra(Extra extra) {
            this.extra = extra;
        }
    }

    public List<String> getInitialPreview() {
        return initialPreview;
    }

    public void setInitialPreview(List<String> initialPreview) {
        this.initialPreview = initialPreview;
    }

    public List<PreviewConfig> getInitialPreviewConfig() {
        return initialPreviewConfig;
    }

    public void setInitialPreviewConfig(List<PreviewConfig> initialPreviewConfig) {
        this.initialPreviewConfig = initialPreviewConfig;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Integer> getErrorkeys() {
        return errorkeys;
    }

    public void setErrorkeys(List<Integer> errorkeys) {
        this.errorkeys = errorkeys;
    }


    public boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }
}
