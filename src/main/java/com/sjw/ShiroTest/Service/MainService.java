package com.sjw.ShiroTest.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Watson on 01/11/2017.
 */
public interface MainService {
    List<Map> getMenuService();
    List<Map> getSubMenuService(int id);
}
