package com.laison.erp.common.utils;


import com.laison.erp.common.constants.ActivitiConstant;
import com.laison.erp.config.exception.CustomerException;

/**
 * @Desc
 * @Author sdp
 * @Date 2020/8/11 18:56
 */
public class ActivitiUtils {
    /**
     * 格式  open_common_null_OrderOutside_null_change_xxx
     */
    public static String findRoleIdFromKey(String keyDefinition) {
        if (keyDefinition.contains("_")) {
            String[] keys = keyDefinition.split("_");
            if (keys.length >= 3) {
                return keys[2];
            }
        }
        return null;
    }

    /**
     * 获取操作的类型
     */
    public static String findOperateType(String processKey) throws Exception {
        return findDiffTypeKey(processKey, 0);
    }

    /**
     * 获取ROLE的类型
     */
    public static String findRoleId(String processKey) throws Exception {
        return findDiffTypeKey(processKey, 2);
    }

    /**
     * 前端上传的参数对象名
     */
    public static String findParamObjectName(String processKey) throws Exception {
        return findDiffTypeKey(processKey, 3);
    }

    /**
     * 开启或完成任务节点后相对应需要处理的方法名|null表示不需要任何处理
     */
    public static String findProcessMethodType(String processKey) throws Exception {
        return findDiffTypeKey(processKey, 4);
    }

    /**
     * 从key中获取任务类型,表示流程类型
     */
    public static String findOrderType(String processKey) throws Exception {
        return findDiffTypeKey(processKey, 5);
    }

    private static String findDiffTypeKey(String processKey, int index) throws Exception {
        if (StringUtils.isBlank(processKey)) throw CustomerException.create(ActivitiConstant.TASK_KEY_IS_NULL);
        if (!processKey.contains("_")) throw CustomerException.create(ActivitiConstant.TASK_KEY_IS_ERROR);
        String[] keys = processKey.split("_");
        if (index > keys.length - 1) throw CustomerException.create(ActivitiConstant.TASK_KEY_IS_ERROR);
        return keys[index];
    }


}
