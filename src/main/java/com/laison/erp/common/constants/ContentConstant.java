package com.laison.erp.common.constants;



/**
 * 语言文本常量
 *
 * @author lihua
 */
public class ContentConstant {
    /**
     * 验证码不能为空
     */
    public static final String VCODE_ERROR = "{vcode.error}";
    /**
     * 请先登录
     */
    public static final String PLEASE_LOGIN = "{please.login}";
    public static final String LOGIN_ERROR = "{login.error}";
    public static final String USERNAME_AREADY_BINDED = "{username.already.binded}";
    public static final String SOCIAL_USER_AREADY_BINDED = "{social.user.aready.binded}";
    public static final String MISS_PARAM = "{miss.parameter}";
    /**
     * 主键不能为空
     */
    public static final String ID_NULL = "{id.null}";
    /**
     * 主键必须为空
     */
    public static final String ID_NOT_NULL = "{id.not.null}";
    /**
     * 未知错误
     */
    public static final String UNKNOWN_ERROR = "{unknown.error}";
    /**
     * 操作成功
     */
    public static final String OPERATE_SUCCESS = "{operate.success}";

    /**
     * 异常操作
     */
    public static final String OPERATE_ERROR = "{operate.error}";

    /**
     * 添加成功
     */
    public static final String ADD_SUCCESS = "{add.success}";

    /**
     * query成功
     */
    public static final String QUERY_SUCCESS = "{query.success}";

    /**
     * 更新成功
     */
    public static final String UPDATE_SUCCESS = "{update.success}";

    public static final String REG_SUCCESS = "{reg.success}";
    public static final String UN_REG_SUCCESS = "{un.reg.success}";
    public static final String REG_ERROR = "{reg.error}";
    public static final String UN_REG_ERROR = "{un.reg.error}";
    /**
     * 删除成功
     */
    public static final String DELETE_SUCCESS = "{delete.success}";
    /**
     * 父id不能为空
     */
    public static final String PID_NULL = "{pid.null}";
    /**
     * 父对象不存在
     */
    public static final String P_OBJECT_NOT_EXIST = "{p.object.not.exist}";
    /**
     * 对象不存在
     */
    public static final String OBJECT_NOT_EXIST = "{object.not.exist}";

    /**
     * 不能删除
     */
    public static final String CAN_NOT_DELETE = "{can.not.delete}";
    /**
     * 部门不存在
     */
    public static final String DEPT_NOT_EXIST = "{dept.not.exist}";

    /**
     * 部门id不能为空
     */
    public static final String DEPT_ID_NULL = "{dept.id.null}";
    /**
     * 角色id不能为空
     */
    public static final String ROLE_ID_NULL = "{role.id.null}";
    /**
     * 角色不存在
     */
    public static final String ROLE_NOT_EXIST = "{role.not.exist}";

    public static final String PASSWORD_NULL = "{password.null}";
    /**
     * 旧密码错误
     */
    public static final String ERROR_OLD_PASSWORD = "{error.old.password}";
    /**
     * 区域名字不能为空
     */
    public static final String REGION_NAME_NULL = "{region.name.null}";
    /**
     * 客户类型不存在
     */
    public static final String CUSTOMERTYPE_NOT_EXIST = "{customertype.not.exist}";
    /**
     * 没有找到原卡 please.first.add.card
     */
    public static final String PLEASE_FIRST_ADD_CARD = "{please.first.add.card}";
    /**
     * 客户不存在
     */
    public static final String CUSTOMER_NOT_EXIST = "{customer.not.exist}";
    /**
     * 客户卡不存在
     */
    public static final String CUSTOMER_CARD_NOT_EXIST = "{customer.card.not.exist}";
    /**
     * 机器不存在
     */
    public static final String MACHINE_NOT_EXIST = "{machine.not.exist}";
    /**
     * 机器已经存在未完成维修任务
     */
    public static final String MACHINE_ALLREADY_HAS_TASK = "{machine.already.has.task}";
    /**
     * 任务不存在
     */
    public static final String TASK_NOT_EXIST = "{task.not.exist}";
    public static final String TASK_TYPE_NOT_EXIST = "{task.type.not.exist}";
    /**
     * 任务已经完成
     */
    public static final String TASK_ALREADY_FINISHED = "{task.already.finished}";
    /**
     * 只有当前处理者能够操作
     */
    public static final String ONLY_HANDLER_CAN_OPERATE = "{only.handler.can.operate}";
    /**
     * 该卡无法挂失
     */
    public static final String CAN_NOT_SUSPEND = "{can.not.suspend}";
    /**
     * 该卡无法解挂
     */
    public static final String CAN_NOT_UNSUSPEND = "{can.not.unsuspend}";

    /**
     * 该卡无法注销
     */
    public static final String CAN_NOT_WRITE_OFF_CARD = "{can.not.write.off.card}";
    /**
     * 无法添加卡
     */
    public static final String CAN_NOT_ADD_CARD = "{can.not.add.card}";
    /**
     * 该卡当前是挂失状态
     */
    public static final String CARD_IS_SUSPEND_STATE = "{card.is.suspend.state}";
    /**
     * 无法注销当前用户
     */
    public static final String CAN_NOT_CANCEL_ACCOUNT = "{can.not.cancel.account}";
    /**
     * 卡不匹配
     */
    public static final String CARD_MISMATCH = "{card.mismatch}";
    /**
     * Please put the card into the card reader
     */
    public static final String PLEASE_READ_CARD = "{please.read.card}";//Please put the card into the card reader

    /**
     * 请先取消任务
     */
    public static final String CANCEL_TASK_FIRST = "{cancel.task.first}";
    public static final String ERROR_STATE = "{error.state}";
    /**
     *
     */
    public static final String MACHINE_USED_BY_OTHER = "{machine.used.by.other}";
    public static final String CAN_NOT_CHANGE = "Cannot Change";
    public static final String RECHARE_TOO_LARGE = "The amount of recharge is too large";
    public static final String CAN_NOT_MOVE = "{can.not.move}";
    public static final String CAN_NOT_RECYCLE = "{can.not.recycle}";

    public static final String VENDOR_BALANCE_INSUFFICIENT = "{can.not.recharge}";

    public static final String RECHARGEMUST_MORETHAN_OVERDRAFT = "{recharge.mustmorethan.overdraft}";

    public static final String CARDID_CANNOT_BENULL = "{cardid.not.null}";

    //region 设备主动上报异常事件

    public static final String OPENMACHINE = "{machine.report.openmachine}";
    public static final String ERRORRECORD = "{machine.report.errorrecord}";
    public static final String LOWPRESSURE = "{machine.report.lowpressure}";
    public static final String NOWATER = "{machine.report.nowater}";
    public static final String POWERIN = "{machine.report.powerin}";
    public static final String POWEROUT = "{machine.report.powerout}";

    //通信完成
    public static final String COMMSUCCESS = "{machine.comm.success}";
    //endregion

    public static final String LOGIN_NAME_ERROR = "{login.name.error}";
    public static final String LOGIN_PASSWORD_ERROR = "{login.password.error}";

    public static final String VENDOR_RECHARGEAMOUNT_ERROR = "{vendor.rechargeamount.error}";
    public static final String MAXRECHARGEAMOUNT_MUSTSMALLTHAN_MAXBALANCE = "{rechargeamount.morethan.maxbalance}";

    public static final String RECHARGE_AMOUNT_TOOLARGE = "{rechargeamount.too.large}";
    public static final String RECHARGE_AMOUNT_CANNOTLESSZERO = "{rechargeamount.cannot.lesszero}";
    public static final String PRICE_AMOUNT_TOOLARGE = "{priceamount.too.large}";
    public static final String MAX_AMOUNT_TOOLARGE = "{maxamount.too.large}";

    public static final String CARD_NOUSERRECORD = "{card.no.userecord}";
    public static final String USERNAME_IS_NULL = "{username.is.null}";
    public static final String USERNAME_EXIST = "{user.name.exist}";
    public static final String METER_NOT_EXIST = "{meter.not.exist}";
    public static final String METER_TYPE_NOT_EXIST = "{meter.type.not.exist}";

    //ami
    public static final String TASKS_OF_THE_SAME_TYPE_ALREADY_EXIST = "{tasks.of.the.same.type.already.exist}";
    public static final String CAN_NOT_ADD_TASK = "{can.not.add.task}";
    public static final String NOT_WRITABLE = "{not.writable}";
    public static final String NOT_READABLE = "{not.readable}";

    public static final String QUERY_TIME_IS_TOO_LONG = "{query.time.is.too.long}";
    public static final String REGION_NOT_EXIST = "{region.not.exist}";

    public static final String CAN_NOT_START = "{can.not.start}";
    public static final String COMMIT_COMPLETE = "{commit.complete}";
    public static final String HAD_APPLY = "{had.apply}";
    public static final String FORM_IS_NULL = "{form.is.null}";
    public static final String CUSTOMER_HAD_ORDER = "{customer.had.order}";
    public static final String BUSINESS_NOT_EXIST = "{business.not.exsit}";
    public static final String AUDIT_BACKED = "{backed}";
    public static final String AUDIT_PASSED = "{passed}";
    public static final String AUDIT_REJECT = "{rejected}";
    public static final String CANCEL_REASON = "{order.cancel}";
    public static final String ACT_STATUS_BACKED = "{act.status.backed}";
    public static final String ACTIVITI_NOT_EXIST = "{activiti.not.exist}";
    public static final String HAD_TASK_TO_AUDIT = "{had.task.to.audit}";
    public static final String CAN_NOT_CANCEL_PROCESS = "{task.had.execute}";
    public static final String QUICK_HANDLE_TASK = "{quick.handle.task}";
    public static final String YOU_HAD_TASK = "{you.had.task}";
    public static final String AUDIT_TASK = "{audit.taskname}";
    public static final String ANNT_NOT_EXIST = "{announcement.not.exist}";
    public static final String MSG_HAD_READ = "{msg.had.read}";
    public static final String BPMN_IS_NOT_EXIST = "{bpmn.model.is.null}";
    private static final String METER_HAD_IN_ORDER = "{meter.had.order}";
    private static final String METER_HAD_INSTALL = "{meter.had.install}";
    private static final String METER_REPAIR = "{meter.had.repair}";
    public static final String ENTRUST = "{entrust.by}";
    public static final String ENTRUSTS = "{entrust.use}";
    public static final String DEPLOY_OK = "{deploy.ok}";
    public static final String DEPLOY_NAME_ERROR = "{deploy.model.processname.error}";
    public static final String DEPLOY_PRIMARY = "{deploy.model.has.publish}";
    public static final String DEPLOY_ERROR = "{deploy.error}";
    public static final String UNKNOWN = "{unknown}";
    public static final String LEVEL_NORMAL = "{level_normal}";
    public static final String LEVEL_IMPORTANT = "{level_important}";
    public static final String LEVEL_URGENT = "{level_urgent}";
    public static final String LEVEL_VERY_URGENT = "{level_veryurgent}";
    public static final String BATCH_INSTALL = "{batchInstall}";
    public static final String SINGLE_INSTALL = "{singleInstall}";
    public static final String CANCEL_ACCOUNT = "{cancelAccount}";
    public static final String REFUND = "{refund}";
    public static final String REPAIR = "{repair}";
    public static final String CHANGE_METER = "{changeMeter}";
    public static final String READ_METER = "{readMeter}";
    public static final String FORM_TYPE = "{formType}";
    public static final String RESULT_noCommit = "{noCommit}";
    public static final String RESULT_inProcess = "{inProcess}";
    public static final String RESULT_alreayAdopt = "{alreayAdopt}";
    public static final String RESULT_alreayBack = "{alreayBack}";
    public static final String RESULT_alreayCancel = "{alreayCancel}";
    public static final String RESULT_alreayDel = "{alreayDel}";
    public static final String STATUS_draft = "{draft}";
    public static final String STATUS_inProcess = "{inProcess}";
    public static final String STATUS_alreadyEnd = "{alreadyEnd}";
    public static final String STATUS_alreayWithdraw = "{alreayWithdraw}";
    public static final String STATUS_inAudit = "{inAudit}";
    public static final String STATUS_errorstat = "{errorstat}";
    public static final String STATUS_alreayBack = "{alreayBack}";


}
