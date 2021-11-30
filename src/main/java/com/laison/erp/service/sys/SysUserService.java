package com.laison.erp.service.sys;

import com.github.pagehelper.PageInfo;
import com.laison.erp.common.Result;
import com.laison.erp.model.activiti.ComboModel;
import com.laison.erp.model.common.LoginAppUser;
import com.laison.erp.model.sys.SysUser;
import com.laison.erp.model.sys.UserRoleView;
import com.laison.erp.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lihua
 * @ClassName: SysUserService
 * @Description: 实现类 SysUserServiceImpl
 * @date 2021-02-01 16:49:38
 */
public interface SysUserService extends BaseService<SysUser, String> {

    /**
     * CACHE_NAME     SysUser
     */
    public final static String CACHE_NAME = "SysUser";
    public final static String BRIEF_CACHE_NAME = "BriefSysUser";

    /**
     * 查询符合条件的 SysUser的个数
     *
     * @param  condition 条件
     * @return int count
     * @throws Exception
     */
    int countByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 删除符合条件的 SysUser
     *
     * @param condition 条件
     * @return 成功删除的个数
     * @throws Exception
     */
    int deleteByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 根据主键删除 SysUser
     *
     * @param  id
     * @return 成功删除的个数
     * @throws Exception
     */
    int deleteByPrimaryKey(String id) throws Exception;

    /**
     * 保存 SysUser
     *
     * @param  record
     * @return 成功保存的个数
     * @throws Exception
     */
    int save(SysUser record) throws Exception;

    /**
     * 查询所有符合条件的  SysUser
     *
     * @param  condition 条件
     * @return List<SysUser>
     * @throws Exception
     */
    List<SysUser> selectAllByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 分页查询所有符合条件的  SysUser
     *
     * @param  condition 条件 Integer pageNum 从1开始, Integer pageSize
     * @return PageInfo<SysUser>
     * @throws Exception
     */
    PageInfo<SysUser> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
            throws Exception;

    /**
     * 查询所有符合条件的  第一个SysUser
     *
     * @param  condition 条件
     * @return SysUser  record
     * @throws Exception
     */
    SysUser selectFirstByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 根据主键查询 SysUser
     *
     * @param  id
     * @return SysUser  record
     * @throws Exception
     */
    SysUser selectByPrimaryKey(String id) throws Exception;

    SysUser selectBriefByPrimaryKey(String id) throws Exception;

    /**
     * 将所有符合条件的SysUser 更新为SysUser  record
     *
     * @return SysUser  record
     * @throws Exception
     */
    int updateByCondition(SysUser record, HashMap<String, Object> condition) throws Exception;

    /**
     * 根据主键更新 SysUser
     *
     * @param  record
     * @return 更新成功的个数
     * @throws Exception
     */
    int updateByPrimaryKey(SysUser record) throws Exception;


    /**
     * 删除所有主键在List<String> ids的记录
     *
     * @param  ids
     * @return 更新成功的个数
     * @throws Exception
     */
    int deleteByPrimaryKeys(List<String> ids) throws Exception;

    LoginAppUser findByUsername(String username);

    void changePassword(String oldpass, String newpass) throws Exception;

    boolean usernameExist(String username);

    void resetPassword(String id, String password) throws Exception;

    Boolean accountRecharge(Map<String, Object> param) throws Exception;

    Result userBaseInfoExist(Map<String, Object> condition) throws Exception;

    String queryUserName(String creatorId);

    List<ComboModel> queryAllUserBackCombo();

    Result listUsersInSameDept(HttpServletRequest request) throws Exception;

    Result queryUserByDepId(String id) throws Exception;

    Result queryDeptLeaders(String deptId) throws Exception;

    UserRoleView findUserRoleById(String assignee) throws Exception;

    void changeUserAvatar(String filePath) throws Exception;

	void changePassword(SysUser user, String oldPwd, String newPwd) throws Exception;
	

	/**
	 * 
	 * @param user
	 * @param decimalAmount
	 * @throws Exception
	 */


	/**
	 *  给用户充值（代理）
	 * @param user
	 * @param decimalAmount
	 * @param source 来源  0=营业厅 1=pos机 2=第三方 3微信小程序
	 * @param payMode 支付方式 cash=0 paypal=1
	 *  @param rechargeId 第三方支付的支付id 
	 * @throws Exception
	 */
	void recharge(SysUser user, BigDecimal decimalAmount, Integer source, Integer payMode,String rechargeId) throws Exception;

    List<SysUser> selectUsersByIds(List<String> asList);
}
