package com.sjw.ShiroTest.Service;

import com.sjw.ShiroTest.Pojo.ImportPdctPojo;
import com.sjw.ShiroTest.Pojo.ProductPojo;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

/**
 * Created by Watson on 03/13/2017.
 */
public interface AdminService {
    List<ImportPdctPojo> uploadPreview(Workbook wb) throws Exception;
    void updateUploadRecord(ImportPdctPojo importPdct);
}
