package com.laison.erp.common.constants;


import com.laison.erp.common.utils.StringUtils;

import java.util.Map;

/**
 * @Desc
 * @Author sdp
 * @Date 2021/4/1 10:17
 */
public class ActivitiConstant {
    /**
     * 流程状态 已激活
     */
    public static final Integer PROCESS_STATUS_ACTIVE = 1;

    /**
     * 流程状态 暂停挂起
     */
    public static final Integer PROCESS_STATUS_SUSPEND = 0;

    /**
     * 资源类型 XML
     */
    public static final int RESOURCE_TYPE_XML = 0;

    /**
     * 资源类型 图片
     */
    public static final int RESOURCE_TYPE_IMAGE = 1;

    /**
     * 状态 待提交申请
     */
    public static final int STATUS_TO_APPLY = 0;

    /**
     * 状态 处理中
     */
    public static final int STATUS_DEALING = 1;

    /**
     * 状态 处理结束
     */
    public static final int STATUS_FINISH = 2;

    /**
     * 状态 已撤回
     */
    public static final int STATUS_CANCELED = 3;
    /**
     * 状态，审批中
     */
    public static final int STATUS_IN_AUDIT = 4;
    /**
     * 状态，异常
     */
    public static final int STATUS_EXCEPTION = 5;
    /**
     * 状态，驳回
     */
    public static final int STATUS_BACK = 6;

    /**
     * 结果 待提交
     */
    public static final int RESULT_TO_SUBMIT = 0;

    /**
     * 结果 处理中
     */
    public static final int RESULT_DEALING = 1;

    /**
     * 结果 通过
     */
    public static final int RESULT_PASS = 2;

    /**
     * 结果 驳回
     */
    public static final int RESULT_FAIL = 3;

    /**
     * 结果 撤回
     */
    public static final int RESULT_CANCEL = 4;

    /**
     * 结果 删除
     */
    public static final int RESULT_DELETED = 5;

    /**
     * 节点类型 开始节点
     */
    public static final int NODE_TYPE_START = 0;

    /**
     * 节点类型 用户任务
     */
    public static final int NODE_TYPE_TASK = 1;

    /**
     * 节点类型 结束
     */
    public static final int NODE_TYPE_END = 2;

    /**
     * 节点类型 排他网关
     */
    public static final int NODE_TYPE_EG = 3;

    /**
     * 节点类型 平行网关
     */
    public static final int NODE_TYPE_PG = 4;

    /**
     * E
     * 节点关联类型 角色
     */
    public static final int NODE_ROLE = 0;

    /**
     * 节点关联类型 用户
     */
    public static final int NODE_USER = 1;

    /**
     * 节点关联类型 部门
     */
    public static final int NODE_DEPARTMENT = 2;

    /**
     * 节点关联类型 操作人的部门负责人
     */
    public static final int NODE_DEP_HEADER = 3;

    /**
     * 待办消息id
     */
    public static final String MESSAGE_TODO_ID = "124717033060306944";
    /**
     * 发起人
     */
    public static final String STARTER = "starter";

    /**
     * 通过消息id
     */
    public static final String MESSAGE_PASS_ID = "124743474544119808";

    /**
     * 驳回消息id
     */
    public static final String MESSAGE_BACK_ID = "124744392996032512";

    /**
     * 委托消息id
     */
    public static final String MESSAGE_DELEGATE_ID = "124744706050494464";

    /**
     * 待办消息
     */
    public static final String MESSAGE_TODO_CONTENT = "待审批";

    /**
     * 通过消息
     */
    public static final String MESSAGE_PASS_CONTENT = "申请流程已通过";

    /**
     * 驳回消息
     */
    public static final String MESSAGE_BACK_CONTENT = "申请流程已驳回";

    /**
     * 委托消息
     */
    public static final String MESSAGE_DELEGATE_CONTENT = "被委托待审批";

    /**
     * 候选
     */
    public static final String EXECUTOR_candidate = "candidate";
    /**
     * 执行任务用户类型 - 通过
     */
    public static final String EXECUTOR_TYPE_p = "actualExecutor_p";
    /**
     * 执行任务用户类型 - 驳回
     */
    public static final String EXECUTOR_TYPE_b = "actualExecutor_b";
    /**
     * 执行任务用户类型 - startEvent
     */
    public static final String EXECUTOR_START = "startEvent";
    /**
     * 执行任务用户类型 - endEvent
     */
    public static final String EXECUTOR_END = "endEvent";

    /**
     * 删除理由前缀
     */
    public static final String DELETE_PRE = "deleted-";

    /**
     * 取消理由前缀
     */
    public static final String CANCEL_PRE = "canceled-";
    /**
     * 通过标记
     */
    public static final String PASSED_FLAG2 = "{passed}";
    public static final String PASSED_FLAG = "completed";
    public static final String PASSED_MSG = "{commit.complete}";
    /**
     * 撤回标记
     */
    public static final String BACKED_FLAG = "backed";
    public static final String BACKED_MSG = "{backed}";

    /**
     * 驳回表计
     */
    public static final String REJECT_FLAG = "rejected";
    public static final String REJECT_MSG = "{rejected}";

    /**
     * 标题关键字
     */
    public static final String TITLE_KEY = "title";

    /**
     * 流程实例状态
     */
    public static final int PROC_INST_APPROVE = 0;

    public static final int PROC_INST_NOT_APPROVE = 1;

    public static final String RESOLVED = "RESOLVED";

    /**
     * 通告对象类型（USER:指定用户，ALL:全体用户）
     */
    public static final String MSG_TYPE_UESR = "USER";
    public static final String MSG_TYPE_ALL = "ALL";
    /**
     * 通知分类
     * 1 ->公告 2->系统
     */
    public static final String MSG_CATEGORY_NOTICE = "1";
    public static final String MSG_CATEGORY_SYS = "2";
    /**
     * 发布状态（0未发布，1已发布，2已撤销）
     */
    public static final String NO_SEND = "0";
    public static final String HAS_SEND = "1";
    public static final String HAS_CANCLE = "2";

    /**
     * 阅读状态（0未读，1已读）
     */
    public static final String HAS_READ_FLAG = "1";
    public static final String NO_READ_FLAG = "0";

    /**
     * 优先级（priority  0-一般，1-重要 2-紧急 3-非常紧急）
     */
    public static final String PRIORITY_L = "L";
    public static final String PRIORITY_M = "M";
    public static final String PRIORITY_H = "H";
    public static final String PRIORITY_VH = "VH";
    public static final int PRIORITY_NORMAL = 1;
    public static final int PRIORITY_IMPORTANT = 2;
    public static final int PRIORITY_URGENT = 3;
    public static final int PRIORITY_VERY_URGENT = 4;
    /**
     * 短信模板方式  0 .登录模板、1.注册模板、2.忘记密码模板
     */
    public static final String SMS_TPL_TYPE_0 = "0";
    public static final String SMS_TPL_TYPE_1 = "1";
    public static final String SMS_TPL_TYPE_2 = "2";

    /**
     * 状态(0无效1有效)
     */
    public static final String STATUS_0 = "0";
    public static final String STATUS_1 = "1";
    /**
     * 同步工作流引擎1同步0不同步
     */
    public static final int ACT_SYNC_1 = 1;
    public static final int ACT_SYNC_0 = 0;

    /**
     * 消息类型1:通知公告2:系统消息
     */
    public static final String MSG_CATEGORY_1 = "1";
    public static final String MSG_CATEGORY_2 = "2";
    /**
     * 未删除
     */
    public static final Integer DEL_FLAG_0 = 0;


    /**
     * websocket专用
     */
    /**
     * 消息json key:cmd
     */
    public static final String MSG_CMD = "cmd";
    public static final String MSG_COUNT = "operateCount";
    /**
     * 消息类型 topic 系统通知
     */
    public static final String CMD_TOPIC = "topic";
    /**
     * 消息json key:userId
     */
    public static final String MSG_USER_ID = "userId";
    public static final String MSG_USER_NAME = "userName";
    /**
     * 消息json key:msgId
     */
    public static final String MSG_ID = "msgId";

    /**
     * 消息json key:msgTxt
     */
    public static final String MSG_TXT = "msgTxt";
    public static final String MSG_CONTENT = "msgContent";

    /**
     * 消息类型 user 用户消息
     */
    public static final String CMD_USER = "user";
    /**
     * 消息类型 heartcheck
     */
    public static final String CMD_CHECK = "heartcheck";
    /**
     * 表
     */
    /**
     * 工单主表
     */
    public static final int TASK_TYPE_ALL = 0;
    public static final int TASK_TYPE_TODO = 1;
    public static final int TASK_TYPE_DONE = 2;
    public static final int TASK_TYPE_APPLY = 3;
    public static final String TABLE_OUTSIDE_ORDER = "order_outside";
    public static final String CUSTOMER_ID = "customerId";
    public static final String CUSTOMER_NAME = "customerName";
    public static final String METER_IDS = "meterIds";
    public static final String METER_NUMBERS = "meterNumbers";
    public static final String FALSE = "false";
    public static final String TRUE = "true";
    public static final String PARTICIPANT_ID = "participantId";
    public static final String REAL_TABLE_ID = "relateTableId";
    public static final String REAL_TABLE_NAME = "relateTableName";
    public static final String NODE_ID = "nodeId";
    public static final String PROC_DEF_ID = "procDefId";
    public static final String ROLE_IDS = "roleIds";
    public static final String USERS = "users";
    public static final String DEPT_IDS = "departmentIds";
    public static final String DEPTS = "departments";
    public static final String DEPT_MANAGER_IDS = "departmentManageIds";
    public static final String DEPT_MANAGERS = "departmentManages";
    public static final String FORM_VARIABLES = "formVariables";
    public static final String CHOOSE_SPONSOR = "chooseSponsor";
    public static final String CHOOSE_DEPT_LEADER = "chooseDepHeader";
    public static final String FORM_INFO = "formInfo";
    public static final String FORM_ID = "formId";

    public static final String CURRENT = "current";
    public static final String PAGE_SIZE = "pageSize";
    public static final String TASK_TYPE = "taskType";

    public static final String ERROR_CODE = "code";
    public static final String DATA = "data";
    public static final String PAGE_INFO = "pageInfo";
    public static final String PROCESS_NAME = "processName";
    public static final String PROCESS_KEY = "processKey";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String NEWEST = "newest";
    public static final String LATEST = "latest";
    public static final String NAME = "name";
    public static final String ROLEIDS = "roles";
    public static final String DEPTIDS = "depts";
    public static final String ASSIGNESS = "assignees";
    public static final String PROC_INST_ID = "procInstId";
    public static final String DISABLE = "disable";
    public static final String IS_FIRST_NODE = "isFirstNode";
    public static final String TODO = "todo";
    public static final String DONE = "done";
    public static final String TYPE = "type";
    public static final String LEVEL = "level";
    public static final String TODO_COUNT = "todoCount";
    public static final String DONE_COUNT = "doneCount";
    public static final String TOTAL_COUNT = "totalCount";
    public static final String DATE_STATS = "dateStats";
    public static final String DATE_STATS_COUNT = "dateStatsCount";
    public static final String RECORD_STATS = "recordStats";
    public static final String TYPE_STATS = "typeStats";
    public static final String LEVEL_STATS = "levelStats";
    public static final String METER_STATS = "meterStats";
    public static final String CUSTOMER_STATS = "customerStats";


    public static final int ISOLATION_LEVEL = ConfigConstant.ISOLATE_LEVEL;

    /**
     * 插入成功
     */
    public final static String ADD_OK = "{add.ok}";

    /**
     * 申请成功
     */
    public final static String APPLY_OK = "{apply.ok}";

    /**
     * 重新申请成功
     */
    public final static String RE_APPLY_OK = "{re.apply.ok}";

    /**
     * 审核成功
     */
    public final static String AUDIT_OK = "{audit.adopt}";

    /**
     * 退购成功
     */
    public final static String REFUND_OK = "{refund.ok}";

    /**
     * 删除成功
     */
    public final static String DEL_OK = "{del.ok}";
    public final static String OBJECT_NOT_EXIST = "{object.not.exist}";
    //添加失败
    public static final String ADD_ERROR = "{add.error}";
    //删除失败
    public static final String DEL_FAILED = "{del.failed}";
    /**
     * 更新成功
     */
    public final static String UPDATE_OK = "{update.ok}";
    public final static String UPDATE_FAILED = "{update.failed}";
    public static final String DATA_NOT_EXIST = "{data.not.exist}";
    //添加工单失败
    public static final String ADD_ORDER_ERROR = "{add.order.error}";
    //流程部署id为空
    public static final String DEPLOY_IS_NULL = "{deploy.is.null}";
    //task key的异常
    public static final String TASK_KEY_IS_NULL = "{task.key.is.null}";
    public static final String TASK_KEY_IS_ERROR = "{task.key.is.error}";
    //任务完成成功
    public static final String TASK_COMPLETE_SUCCESS = "{task.complete.success}";
    //任务完成失败
    public static final String TASK_COMPLETE_FAILED = "{task.complete.failed}";
    //任务没有全部完成，当前只是针对安装工单
    public static final String TASK_NEVER_COMPLETE = "{task.never.complete}";
    //工单为空
    public static final String ORDER_IS_NULL = "{order.is.null}";
    //任务已完成
    public static final String TASK_FINISHED = "{task.finished}";
    //任务未启动
    public static final String TASK_NOT_START = "{task.not.start}";
    //任务移交失败
    public static final String TRANSFER_OK = "{transfer.ok}";
    public static final String TRANSFER_ERROR = "{transfer.error}";
    //移除失败
    public static final String REMOVE_ERROR = "{remove.error}";
    //移除成功
    public static final String REMOVE_OK = "{remove.ok}";
    //恢复失败
    public static final String RECOVERY_ERROR = "{recovery.error}";
    //恢复成功
    public static final String RECOVERY_OK = "{recovery.ok}";
    //任务审核失败
    public static final String TASK_AUDIT_ERROR = "{task.audit.error}";
    /**
     * 工单相关
     */
    //参数类型错误
    public static final String PARAM_TYPE_ERROR = "{param.type.error}";
    //登录用户信息为空
    public static final String LOGIN_USER_IS_NULL = "{login.user.is.null}";
    //部署流程失败
    public static final String DEPLOY_FLOW_FAILED = "{deploy.flow.failed}";
    //部署流程成功
    public static final String DEPLOY_FLOW_SUCCESS = "{deploy.flow.success}";
    //流程文件未空
    public static final String FLOW_FILE_IS_NULL = "{flow.file.is.null}";
    //流程文件类型错误
    public static final String FLOW_FILE_TYPE_ERROR = "{flow.file.type.error}";
    //任务挂起
    public static final String TASK_SUSPEN = "{task.suspension}";
    //任务签收成功
    public static final String TASK_CLAIM_FAILED = "{task.claim.failed}";
    //任务为空
    public static final String TASK_IS_NULL = "{task.is.null}";
    //用户信息为空
    public static final String CUSTOMER_IS_NULL = "{customer.is.null}";
    //选择任务进行提交
    public static final String SELECT_TASK_TO_COMMIT = "{select.task.to.commit}";
    //配件为空
    public static final String PARTS_INFO_IS_NULL = "{parts.info.is.null}";
    //添加配件成功
    public static final String ADD_PARTS_ERROR = "{add.parts.error}";
    //安装工单失败
    public static final String SETUP_ERROR = "{setup.error}";
    //退购失败
    public static final String REFUND_ERROR = "{refund.error}";
    //流程组存在
    public static final String GROUP_EXIST = "{group.is.exist}";
    //流程用户存在
    public static final String USER_EXIST = "{user.is.exist}";
    //提交失败
    public static final String APPLY_ERROR = "{apply.error}";
    //工单尚未开启
    public static final String ORDER_NEVER_OPEN = "{order.never.open}";
    public static final String JSON_PARSE_ERROR = "{json.parse.error}";
    public static final String METER_NOT_STORAGE = "{meter.not.storage}";
    public static final String CUSTOMER_TYPE_NOT_MATCH = "{customer.type.not.match}";
    public static final String OFFLINE_REFUND_ERROR = "{offline.refund.error}";
    public static final String OFFLINE_CHANGE_ERROR = "{offline.change.error}";
    public static final String OFFLINE_REPAIR_ERROR = "{offline.repair.error}";
    public static final String OFFLINE_CANCEL_ERROR = "{offline.cancel.error}";
    public static final String OFFLINE_INSPECTION_ERROR = "{offline.inspection.error}";


    /**
     * 查询工单，申请，待办，已办
     */
    public static final int ORDER_APPROVE = 0;
    public static final int ORDER_TODO = 1;
    public static final int ORDER_DONE = 2;
    public static final int ORDER_ALL = 3;
    public static final String ORDER_TYPE = "orderType";
    public static final String ORDER_LEVEL = "orderLevel";
    public static final String ORDER_SOURCE = "orderSource";
    public static final String CHANGE_TYPE = "change";
    public static final String INSTALL_TYPE = "install";
    //我参与的工单
    public static final int MY_PARTICIPANT_ORDER = 0;
    //我创建的工单
    public static final int MY_CREATE_ORDER = 1;
    //我待办的任务
    public static final int MY_TODO_TASK = 2;
    //我完成的任务
    public static final int MY_DONE_TASK = 3;
    //回收站的工单
    public static final int MY_RECYCLE_ORDER = 4;
    /**
     * 正常状态
     */
    public static final int NORMAL = 0;
    /**
     * 未完成
     */
    public static final int UNFINISHED = 0;
    /**
     * 已完成
     */
    public static final int FINISHED = 1;

    /**
     * 被删除了，不去真正的删除，只是放入回收站状态
     */
    public static final int DELETED = 1;
    /**
     * 人工分配
     */
    public static final int HANDLE_ASSIGN = 0;
    /**
     * 自动分配
     */
    public static final int AUTO_ASSIGN = 1;
    /**
     * 任务已激活
     */
    public static final int TASK_DEFAULT = 0;
    /**
     * 任务已激活
     */
    public static final int TASK_ACTIVE = 1;
    /**
     * 任务已挂起
     */
    public static final int TASK_SUSPENSION = 2;
    /**
     * 工单详情对应信息
     */

    public static final String ORDER_OUTSIDE = "order_outside";
    public static final int TYPE_BATCH_INSTALL = 1;
    public static final String ORDER_FOR_INSTALL_BATCH = "order_for_batchinstall";
    public static final int ORDER_INSTALL_SINGLE = 2;
    public static final String ORDER_FOR_INSTALL_SINGLE = "order_for_singleinstall";
    public static final int ORDER_CANCEL_ACCOUNT = 3;
    public static final String ORDER_FOR_CANCEL_ACCOUNT = "order_for_cancelaccount";
    public static final int ORDER_REFUND = 4;
    public static final String ORDER_FOR_REFUND = "order_for_refund";
    public static final int ORDER_REPAIR = 5;
    public static final String ORDER_FOR_REPAIR = "order_for_repair";
    public static final int ORDER_CHANGE_METER = 6;
    public static final String ORDER_FOR_CHANGE_METER = "order_for_changemeter";
    public static final int ORDER_READ_METER = 7;
    public static final String ORDER_FOR_READ_METER = "order_read_meter";
    public static final int ORDER_FORM = 999;
    public static final String ORDER_FOR_FORM = "order_form";

    public static final int NEVER_INSTALL = 0;
    public static final int HAD_INSTALL = 1;
    public static final int INSTALL_FAILED = 2;

    public static final int INSTALL_NEVER = 0;
    public static final int INSTALL_NOT_ALL = 1;
    public static final int INSTALL_ALL = 2;

    /**
     * 参数key
     */
    public static final String KEY_TOTAL_INSTALL_COUNT = "totalInstallCount";
    public static final String KEY_HAD_INSTALL_COUNT = "hadInstallCount";
    public static final String KEY_NEVER_INSTALL_COUNT = "neverInstallCount";
    public static final String ORDER_INSTALL_LIST = "installList";
    public static final String FAULT_REASON_IDS = "faultReasonIds";
    public static final String COMMENT = "comment";
    public static final String AUDIT_REJECT = "{audit.reject}";
    public static final String IN_PROCESS = "{inProcess}";
    public static final String TASK_ID = "taskId";
    public static final String TASK_KEY = "backTaskKey";
    public static final String PRIORITY = "priority";
    public static final String START_NODE_MUST_SET_FORM = "{start.node.must.set.form}";
    public static final String START_NODE_MUST_SET_AUTHORIZE = "{start.node.must.set.authorize}";
    public static final String START_NODE_MUST_SET_FORM_AUTHORIZE = "{start.node.must.set.form_authorize}";

    public static String buildOrderTypeName(int type) {
        switch (type) {
            case TYPE_BATCH_INSTALL:
                return ContentConstant.BATCH_INSTALL;
            case ORDER_INSTALL_SINGLE:
                return ContentConstant.SINGLE_INSTALL;
            case ORDER_CANCEL_ACCOUNT:
                return ContentConstant.CANCEL_ACCOUNT;
            case ORDER_REFUND:
                return ContentConstant.REFUND;
            case ORDER_REPAIR:
                return ContentConstant.REPAIR;
            case ORDER_CHANGE_METER:
                return ContentConstant.CHANGE_METER;
            case ORDER_READ_METER:
                return ContentConstant.READ_METER;
        }
        return ContentConstant.FORM_TYPE;
    }

    public static String buildOrderLevelName(int level) {
        switch (level) {
            case PRIORITY_NORMAL:
                return ContentConstant.LEVEL_NORMAL;
            case PRIORITY_IMPORTANT:
                return ContentConstant.LEVEL_IMPORTANT;
            case PRIORITY_URGENT:
                return ContentConstant.LEVEL_URGENT;
            case PRIORITY_VERY_URGENT:
                return ContentConstant.LEVEL_VERY_URGENT;
        }
        return ContentConstant.UNKNOWN;
    }

    public static String buildOrderResultName(int result) {
        switch (result) {
            case RESULT_TO_SUBMIT:
                return ContentConstant.RESULT_noCommit;
            case RESULT_DEALING:
                return ContentConstant.RESULT_inProcess;
            case RESULT_PASS:
                return ContentConstant.RESULT_alreayAdopt;
            case RESULT_FAIL:
                return ContentConstant.RESULT_alreayBack;
            case RESULT_CANCEL:
                return ContentConstant.RESULT_alreayCancel;
            case RESULT_DELETED:
                return ContentConstant.RESULT_alreayDel;
        }
        return ContentConstant.UNKNOWN;
    }

    public static String buildOrderStatusName(int status) {
        switch (status) {
            case STATUS_TO_APPLY:
                return ContentConstant.STATUS_draft;
            case STATUS_DEALING:
                return ContentConstant.STATUS_inProcess;
            case STATUS_FINISH:
                return ContentConstant.STATUS_alreadyEnd;
            case STATUS_CANCELED:
                return ContentConstant.STATUS_alreayWithdraw;
            case STATUS_IN_AUDIT:
                return ContentConstant.STATUS_inAudit;
            case STATUS_EXCEPTION:
                return ContentConstant.STATUS_errorstat;
            case STATUS_BACK:
                return ContentConstant.STATUS_alreayBack;
        }
        return ContentConstant.UNKNOWN;
    }

    public static String buildComment(String comment) {
        if (StringUtils.isBlank(comment)) return "";
        if ((comment.equals(ActivitiConstant.PASSED_FLAG)||comment.equals(ActivitiConstant.PASSED_FLAG2))) {
            return ActivitiConstant.AUDIT_OK;
        }
        return comment;
    }
}
