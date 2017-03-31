package com.sjw.ShiroTest.Pojo;

import org.apache.ibatis.type.Alias;
import org.apache.poi.ss.usermodel.Row;

import java.io.Serializable;

/**
 * Created by Watson on 03/15/2017.
 */
@Alias(value = "ImportPdct")
public class ImportPdctPojo extends ProductPojo{
    private static final long serialVersionUID = 8677796113768572450L;

    private int amount;
    private int adminId;
    private String adminName;

    public ImportPdctPojo() {
    }

    public ImportPdctPojo(Row row){
        this.setName(row.getCell(0).getStringCellValue());
        this.setPrice((float) row.getCell(1).getNumericCellValue());
        this.setCategory(row.getCell(2).getStringCellValue());
        this.setTags(row.getCell(3).getStringCellValue());
        this.amount = (int) row.getCell(4).getNumericCellValue();
        this.adminName = row.getCell(5).getStringCellValue();
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
