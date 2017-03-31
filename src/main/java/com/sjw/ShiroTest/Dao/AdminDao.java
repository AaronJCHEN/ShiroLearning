package com.sjw.ShiroTest.Dao;

import com.sjw.ShiroTest.Pojo.ImportPdctPojo;
import org.mybatis.spring.annotation.MapperScan;

/**
 * Created by Watson on 03/13/2017.
 */
@MapperScan
public interface AdminDao {
    void updateUploadRecord(ImportPdctPojo importPdct);
}
