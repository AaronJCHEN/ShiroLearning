package com.sjw.ShiroTest.Controller;

import com.sjw.ShiroTest.Pojo.ImportPdctPojo;
import com.sjw.ShiroTest.Service.AdminService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Watson on 03/13/2017.
 */
@CrossOrigin("http://localhost:8082")
@RestController
@RequestMapping("/ShiroTest/admin")
public class AdminController {
    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/uploadInit",method = RequestMethod.GET)
    public ModelAndView uploadInit(){
        return new ModelAndView("import.definition");
    }

    @RequestMapping(value = "/uploadToDB",method = RequestMethod.POST)
    public String uploadToDB(@RequestBody ImportPdctPojo[] importedList) throws Exception{
        int count = 0;
        for(ImportPdctPojo importedPdct : importedList){
            adminService.updateUploadRecord(importedPdct);
            count++;
        }
        return String.valueOf(count);
    }
}
