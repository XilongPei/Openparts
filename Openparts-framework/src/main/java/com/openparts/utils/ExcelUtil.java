package com.openparts.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelUtil {
/*
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void saveToExcel(String path,Long...ids) throws FileNotFoundException{
        List<User> users=userService.find(ids);
        File file=new File(path);
        createExcel(new FileOutputStream(file), users);
    }
    public List<User> getFromExcel(File file) throws BiffException, IOException{
        return readExcel(file);
    }

    public List<User> readExcel(File file) throws BiffException, IOException   {
        List<User> userlist = new ArrayList<User>();
        Workbook rwb = null;
        String cellStr = null;
        User user = null;
        // ´´½¨ÊäÈëÁ÷
        // »ñÈ¡ExcelÎÄ¼þ¶ÔÏó
        if (file == null || !file.exists()) {
            return null;
        }
        InputStream stream = new FileInputStream(file);
        rwb = Workbook.getWorkbook(stream);
        // »ñÈ¡ÎÄ¼þµÄÖ¸¶¨¹¤×÷±í Ä¬ÈÏµÄµÚÒ»¸ö
        Sheet sheet = rwb.getSheet(0);
        // ÐÐÊý(±íÍ·µÄÄ¿Â¼²»ÐèÒª£¬´Ó1¿ªÊ¼)
        for (int i = 1; i < sheet.getRows(); i++) {
            // ´´½¨Ò»¸öuser¶ÔÏó¶ÔÓ¦Ò»ÐÐ£¬ ÓÃÀ´´æ´¢Ã¿Ò»ÁÐµÄÖµ
            user = new User();
            // ÁÐÊý
            cellStr = sheet.getCell(0, i).getContents().trim();// µÚÒ»ÁÐ£¬ÒµÖ÷ÐÕÃû
            user.setUsername(cellStr);
            cellStr = sheet.getCell(1, i).getContents().trim();// µ¥ÔªºÅ
            user.setUserAlias(cellStr);
            //ÉèÖÃÄ¬ÈÏÖµ
            user.setPassword(Constants.DEFAULT_PASSWORD);
            user.setCreateTime(new Date());
            user.setCreatorName(getCurrentUser().getUsername());

            // °Ñ¸Õ»ñÈ¡µÄÁÐ´æÈëuserlist
            userlist.add(user);
        }
        return userlist;
    }
    private void createExcel(OutputStream os,List<User> list){
        String[] heads={"ÓÃ»§Ãû","±ðÃû"};
        // ´´½¨¹¤×÷Çø
        WritableWorkbook workbook=null;
        try {
            workbook = Workbook.createWorkbook(os);
        // ´´½¨ÐÂµÄÒ»Ò³£¬sheetÖ»ÄÜÔÚ¹¤×÷²¾ÖÐÊ¹ÓÃ
        WritableSheet sheet = workbook.createSheet("user sheet1", 0);
        // ´´½¨µ¥Ôª¸ñ¼´¾ßÌåÒªÏÔÊ¾µÄÄÚÈÝ£¬new Label(0,0,"ÓÃ»§") µÚÒ»¸ö²ÎÊýÊÇcolumn µÚ¶þ¸ö²ÎÊýÊÇrow
        // µÚÈý¸ö²ÎÊýÊÇcontent£¬µÚËÄ¸ö²ÎÊýÊÇ¿ÉÑ¡Ïî,ÎªLabelÌí¼Ó×ÖÌåÑùÊ½
        // Í¨¹ýsheetµÄaddCell·½·¨Ìí¼ÓLabel£¬×¢ÒâÒ»¸öcell/labelÖ»ÄÜÊ¹ÓÃÒ»´ÎaddCell
        // µÚÒ»¸ö²ÎÊýÎªÁÐ£¬µÚ¶þ¸öÎªÐÐ£¬µÚÈý¸öÎÄ±¾ÄÚÈÝ
        //Ìí¼Ó×Ö¶ÎÃû
        for(int i=0;i<heads.length;i++){
            sheet.addCell(new Label(i,0,heads[i]));
        }
        //Ìí¼Ó×Ö¶ÎÄÚÈÝ
        for(int i=0;i<list.size();i++){
            sheet.addCell(new Label(0, i+1, list.get(i).getUsername()));
            sheet.addCell(new Label(1, i+1, list.get(i).getUserAlias()));

        }
        workbook.write();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            // ½«ÄÚÈÝÐ´µ½Êä³öÁ÷ÖÐ£¬È»ºó¹Ø±Õ¹¤×÷Çø£¬×îºó¹Ø±ÕÊä³öÁ÷

            try {
                if(workbook!=null)
                workbook.close();
            } catch (WriteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if(os!=null)
                os.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public HttpSession getSession() {
        return session;
    }
    public void setSession(HttpSession session) {
        this.session = session;
    }
    public UserService getUserService() {
        return userService;
    }
    public User getCurrentUser(){
        return (User) session.getAttribute(Constants.CURRENT_USER);

    }
*/
}
