package com.sjw.ShiroTest.Enhance;

import com.sjw.ShiroTest.Pojo.BrowsePojo;
import com.sjw.ShiroTest.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by Watson on 01/19/2017.
 */
public class BrowseNumEnhance implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(BrowseNumEnhance.class);

    private BrowsePojo browse;

    private ProductService productService;

    public BrowseNumEnhance(BrowsePojo browse,ProductService productService){
        this.browse = browse;
        this.productService = productService;
    }

    @Override
    public void run() {
        BrowsePojo existedRecord = productService.getBrowseDetailService(browse);
        if (existedRecord == null) {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            browse.setBrowseDate(currentTime);
            productService.createBrowseRecordService(browse);
        }
        else{
            int old_num = existedRecord.getBrowseTimes();
            old_num++;
            existedRecord.setBrowseTimes(old_num);
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            existedRecord.setBrowseDate(currentTime);
            productService.updateBrowseTimesService(existedRecord);
        }
        logger.info(browse.getUsername()+" update the browse record of pdct id "+browse.getProductId());
    }
}
