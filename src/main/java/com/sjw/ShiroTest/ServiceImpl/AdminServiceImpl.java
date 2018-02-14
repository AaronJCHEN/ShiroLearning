package com.sjw.ShiroTest.ServiceImpl;

import com.sjw.ShiroTest.Dao.AdminDao;
import com.sjw.ShiroTest.Dao.AuthDao;
import com.sjw.ShiroTest.Dao.ProductDao;
import com.sjw.ShiroTest.Pojo.ImportPdctPojo;
import com.sjw.ShiroTest.Pojo.ProductPojo;
import com.sjw.ShiroTest.Pojo.UserPojo;
import com.sjw.ShiroTest.Service.AdminService;
import jdk.nashorn.internal.objects.annotations.Property;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Watson on 03/13/2017.
 */
@Service
@PropertySource("classpath:settings.properties")
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;

    @Autowired
    AuthDao authDao;

    @Autowired
    ProductDao productDao;

    @Value("${upload.titles}")
    private String columnName;

    @Override
    public List<ImportPdctPojo> uploadPreview(Workbook wb) throws Exception {
        Sheet sheet = wb.getSheetAt(0);
        Row rowOne = sheet.getRow(0);
        List<ImportPdctPojo> uploadPreview = new LinkedList<>();
        if (rowOne.getLastCellNum() != columnName.split(",").length)
            throw new RuntimeException("The upload template is wrong");
        for (int j = 0;j<=sheet.getLastRowNum();j++){
            if (!columnName.contains(rowOne.getCell(j).toString()))
                throw new RuntimeException("The upload template is wrong");
        }
        for (int i = 1;i<=sheet.getLastRowNum();i++){
            Row row = sheet.getRow(i);
            /**
             * 0.Product_Name
             * 1.Price
             * 2.Category
             * 3.Tags
             * 4.Amount
             * 5.Admin_Name
             */
            ImportPdctPojo importRecord = new ImportPdctPojo(row);
            uploadPreview.add(importRecord);
        }
        return uploadPreview;
    }

    @Override
    @Transactional
    public void updateUploadRecord(ImportPdctPojo importPdct) {
        ProductPojo product = productDao.getProductDetailByName(importPdct.getName());
        UserPojo admin = authDao.getUserByName(importPdct.getAdminName());
        if (product == null){
            product = (ProductPojo) importPdct;
            product.setRemains(importPdct.getAmount());
            productDao.createNewProduct(product);
        }
        else{
            if (importPdct.getAmount()>0) {
                product.setRemains(product.getRemains() + importPdct.getAmount());
                productDao.updateRemainCount(product);
            }
            else
                throw new RuntimeException("Import Amount of "+importPdct.getName()+" is wrong");
        }

        importPdct.setId(product.getId());
        importPdct.setAdminId(admin.getId());
        adminDao.updateUploadRecord(importPdct);
    }
}
