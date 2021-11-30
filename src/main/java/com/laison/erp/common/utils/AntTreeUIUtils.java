package com.laison.erp.common.utils;


import com.laison.erp.model.sys.SysDepartTreeModel;
import com.laison.erp.model.sys.SysDept;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @Author sdp
 * @Date 2021/4/1 17:52
 */
public class AntTreeUIUtils {
    public static List<SysDepartTreeModel> wrapTreeDataToTreeList(List<SysDept> recordList) {
        List<SysDepartTreeModel> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            SysDept depart = recordList.get(i);
            records.add(new SysDepartTreeModel(depart));
        }
        return records;
    }
}
