package com.laison.erp.service.sys.impl;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laison.erp.common.Result;
import com.laison.erp.common.constants.ConfigConstant;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.*;
import com.laison.erp.config.exception.CustomerException;
import com.laison.erp.dao.sys.SysUserDao;
import com.laison.erp.dao.sys.SysUserRoleDao;
import com.laison.erp.model.activiti.ComboModel;
import com.laison.erp.model.common.LoginAppUser;
import com.laison.erp.model.sys.*;
import com.laison.erp.service.sys.SysDeptService;
import com.laison.erp.service.sys.SysMenuService;
import com.laison.erp.service.sys.SysRoleService;
import com.laison.erp.service.sys.SysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;


/**
 * @author lihua 496583747@qq.com
 * @date 2021-02-01 16:49:38
 */
@Service
public class SysUserServiceImpl implements SysUserService {


    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    SysUserRoleDao sysUserRoleDao;
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysDeptService sysDeptService;
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public int countByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        return sysUserDao.selectCountByExample(example);
    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)// 清空  缓存
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        SpringContextUtils.getCache(BRIEF_CACHE_NAME).clear();
        return sysUserDao.deleteByExample(example);
    }

    @Override
    @CacheEvict(value = CACHE_NAME, key = "#id") // 清空 缓存
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKey(String id) throws Exception {
        SpringContextUtils.getCache(BRIEF_CACHE_NAME).evict(id);
        return sysUserDao.deleteByPrimaryKey(id);
    }

    /**
     * 保存用户信息
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int save(SysUser record) throws Exception {
        //验证username是否已经添加了
        String username = record.getUsername();
        if (StringUtils.isBlank(username)) {
            throw CustomerException.create(ContentConstant.USERNAME_IS_NULL);
        }
        int userCount = sysUserDao.selectUserCountByUsername(username);
        if (userCount > 0) {
            throw CustomerException.create(ContentConstant.USERNAME_EXIST);
        }
        //验证用户部门信息
        String deptId = record.getDeptId();
        SysDept sysDept = sysDeptService.selectByPrimaryKey(deptId);
        //验证用户角色信息
        String roleId = record.getRoleId();
        SysRole sysRole = sysRoleService.selectByPrimaryKey(roleId);
        if (!sysDept.getType().equals(sysRole.getType())) {
            throw CustomerException.create(ContentConstant.OPERATE_ERROR);
        }
        Date date = DateUtils.deptUtcDate(deptId);
        String sysUserId = LoginUserUtil.getSysUser().getId();
        record.setCreateTime(date)
                .setUpdateTime(date)
                .setCreateBy(sysUserId)
                .setUpdateBy(sysUserId)
                .setLanguage(sysDept.getConfig().getLanguage())
                .setStatus(true)
                .setDelFlag(false)
                .setPassword(passwordEncoder.encode("0000"))//默认密码四个0
                .setType(sysDept.getType());
        Boolean randomPassword = sysDept.getConfig().getRandomPassword();
        if(randomPassword) {//随机密码、通过邮件发送
        	 String randomString = RandomUtil.randomString("abc0123456789",8);
        	 record.setPassword(passwordEncoder.encode(randomString));
        	 
        	 MailUtil.sendPassword(record.getUsername(),randomString, record.getEmail());
        	 
        }
        Integer changePassWordTime = sysDept.getConfig().getChangePassWordTime();
        if(changePassWordTime>1) {
        	Date nextChangePassTime = DateUtil.offset(date, DateField.DAY_OF_MONTH, changePassWordTime).toJdkDate();
        	record.setNextChangePassTime(nextChangePassTime);
        }
        int count = sysUserDao.insertSelective(record);
        //关联用户角色表
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(roleId);
        sysUserRole.setUserId(record.getId());
        sysUserRoleDao.insertSelective(sysUserRole);
        return count;
    }

    @Override
    public List<SysUser> selectAllByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        if (ConfigConstant.ISOLATE_ENABLE) {
            LoginUserUtil.addIsolate(example);
        }
        return sysUserDao.selectByExample(example);
    }

    @Override
    public PageInfo<SysUser> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
            throws Exception {
        Example example = createExample(condition);
        if (ConfigConstant.ISOLATE_ENABLE) {
            LoginUserUtil.addIsolate(example);
        }
        PageHelper.startPage(pageNum, pageSize, true);
        String orderByClause = (String) condition.get("orderByClause");
        if (orderByClause != null) {
            example.setOrderByClause(orderByClause);
        }
        List<SysUser> list = sysUserDao.selectByExample(example);
        for (SysUser sysUser : list) {
            List<SysRole> roles = sysRoleService.findRolesByUserId(sysUser.getId());
            if (!CollectionUtils.isEmpty(roles)) {
                SysRole sysRole = roles.get(0);
                sysUser.setSysRole(sysRole);
                sysUser.setRoleId(sysRole.getId());
                sysUser.setRoleName(sysRole.getName());
            }
            SysDept sysDept = sysDeptService.selectByPrimaryKey(sysUser.getDeptId());
            sysUser.setSysDept(sysDept);
            sysUser.setDeptName(sysDept.getName());
        }
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public SysUser selectFirstByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        String orderByClause = (String) condition.get("orderByClause");
        if (orderByClause != null) {
            example.setOrderByClause(orderByClause);
        }
        PageHelper.startPage(1, 1, true);
        List<SysUser> list = sysUserDao.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }

    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "#id", sync = true)//表示并发，等待第一个缓存后才进行查询
    public SysUser selectByPrimaryKey(String id) throws Exception {
        SysUser sysUser = sysUserDao.selectByPrimaryKey(id);
        if (sysUser != null) {
            SysDept sysDept = sysDeptService.selectByPrimaryKey(sysUser.getDeptId());
            ArrayList<String> subDeptIds = sysDeptService.findSubDeptIds(sysUser.getDeptId());
            sysUser.setSubDeptIds(subDeptIds);
            if (ConfigConstant.ISOLATE_ENABLE) {
                Integer isolateLevel = ConfigConstant.ISOLATE_LEVEL;
                String deptid = null;
                if (sysDept.getLevel() <= isolateLevel) {
                    deptid = sysDept.getId();
                } else {
                    String paths = sysDept.getPaths();
                    String[] split = paths.split(",");
                    deptid = split[isolateLevel + 1];
                }
                List<String> deptIds = sysDeptService.findSubDeptIds(deptid);
                sysUser.setSysDeptIds(deptIds);
            }
            sysUser.setSysDept(sysDept);
            List<SysRole> roles = sysRoleService.findRolesByUserId(id);
            if (!CollectionUtils.isEmpty(roles)) {
                SysRole sysRole = roles.get(0);
                sysUser.setSysRole(sysRole);

            }
            SysMenu sysMenu = sysMenuService.selectByPrimaryKey("0");
            if (id.equals("1") || id.equals("2")) {
                sysUser.setSysMenu(sysMenu);
            } else {
                sysMenu = sysMenuService.findUserMenus(id, sysMenu);
            }
            sysUser.setSysMenu(sysMenu);
            Set<String> permissions = new HashSet<String>();
            List<String> authoritys = null;
            if (sysUser.getId().equals("1") || sysUser.getId().equals("2")) {
                SysMenuService sysMenuService = SpringContextUtils.getBean(SysMenuService.class);
                authoritys = sysMenuService.findAllAuthoritys();
            } else {
                authoritys = sysUserDao.findUserAuthoritys(sysUser.getId());

            }
            for (String authority : authoritys) {
                String[] split = authority.split(",");
                for (String auth : split) {
                    if (!StringUtils.isBlank(auth)) {
                        permissions.add(auth);
                    }
                }
            }
            sysUser.setPermissions(permissions);// 设置权限集合


        }
        return sysUser;
    }

    @Override
    @Cacheable(value = BRIEF_CACHE_NAME, key = "#id", sync = true)//表示并发，等待第一个缓存后才进行查询
    public SysUser selectBriefByPrimaryKey(String id) throws Exception {
        SysUser sysUser = sysUserDao.selectByPrimaryKey(id);
        if (sysUser != null) {
            List<SysRole> roles = sysRoleService.findRolesByUserId(id);
            if (!CollectionUtils.isEmpty(roles)) {
                SysRole sysRole = roles.get(0);
                sysUser.setSysRole(sysRole);
            }
        }
        return sysUser;
    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)// 清空  缓存
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByCondition(SysUser record, HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        SpringContextUtils.getCache(BRIEF_CACHE_NAME).clear();
        return sysUserDao.updateByExample(record, example);
    }

    @Override
    @CacheEvict(value = CACHE_NAME, key = "#record.id") // 清除 缓存
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByPrimaryKey(SysUser record) throws Exception {
        SysUser old = selectByPrimaryKey(record.getId());
        if (old == null) {
            throw new Exception(ContentConstant.OBJECT_NOT_EXIST);
        }
        record.setUsername(null);//用户名不能修改
        record.setPassword(null);//密码不能修改
        String newdeptId = record.getDeptId();
        String newroleId = record.getRoleId();
        if (newroleId != null && !newroleId.equals(old.getRoleId())) {//修改了role
            SysRole sysRole = sysRoleService.selectByPrimaryKey(newroleId);
            if (sysRole == null) {
                throw new Exception(ContentConstant.ROLE_NOT_EXIST);
            }
            if (newdeptId != null && !newdeptId.equals(old.getDeptId())) {//修改了部门
                SysDept sysDept = sysDeptService.selectByPrimaryKey(newdeptId);
                if (sysDept == null) {
                    throw new Exception(ContentConstant.DEPT_NOT_EXIST);
                }
                if (!sysDept.getType().equals(sysRole.getType())) {
                    throw new Exception(ContentConstant.OPERATE_ERROR);
                }
                record.setType(sysDept.getType());
            } else {//没修改部门
                record.setType(null);
                record.setDeptId(null);
            }

            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(record.getId());
            sysUserRoleDao.delete(sysUserRole);//删除原来的关联
            sysUserRole.setRoleId(newroleId);
            sysUserRoleDao.insertSelective(sysUserRole);//添加新的关联
        } else {//没修改role
            record.setType(null);
            record.setDeptId(null);
        }
        Cache cache = SpringContextUtils.getCache(CACHE_NAME);
        cache.evict(record.getId());
        SpringContextUtils.getCache(BRIEF_CACHE_NAME).evict(record.getId());
        record.setUpdateTime(DateUtils.utcDate());
        record.setUpdateBy(LoginUserUtil.getSysUser().getId());
        sysUserDao.updateByPrimaryKeySelective(record);
        cache = SpringContextUtils.getCache(SysDeptService.CACHE_NAME);
        cache.clear();
        cache = SpringContextUtils.getCache(SysDeptService.STRUCTURE_CACHE_NAME);
        cache.clear();
        return 1;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKeys(List<String> ids) throws Exception {
        Example example = new Example(SysUser.class);
        Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        Cache cache = SpringContextUtils.getCache(CACHE_NAME);
        ids.forEach(cache::evict);
        SpringContextUtils.getCache(BRIEF_CACHE_NAME).clear();

        return sysUserDao.deleteByExample(example);
    }

    @Override
    public LoginAppUser findByUsername(String username) {
        SysUser user = new SysUser();
        user.setUsername(username);
        user = sysUserDao.selectOne(user);

        if (user != null) {
            LoginAppUser loginAppUser = new LoginAppUser();
            BeanUtils.copyProperties(user, loginAppUser);

            List<SysRole> sysRoles = sysRoleService.findRolesByUserId(user.getId());
            HashSet<SysRole> setRoles = new HashSet<>();
            setRoles.addAll(sysRoles);
            Set<String> permissions = new HashSet<String>();
            List<String> authoritys = null;
            if (loginAppUser.getId().equals("1") || loginAppUser.getId().equals("2")) {
                SysMenuService sysMenuService = SpringContextUtils.getBean(SysMenuService.class);
                authoritys = sysMenuService.findAllAuthoritys();
            } else {
                authoritys = sysUserDao.findUserAuthoritys(loginAppUser.getId());

            }
            for (String authority : authoritys) {
                String[] split = authority.split(",");
                for (String auth : split) {
                    if (!StringUtils.isBlank(auth)) {
                        permissions.add(auth);
                    }
                }
            }
           
            loginAppUser.setPermissions(permissions);// 设置权限集合


            return loginAppUser;
        }
        return null;
    }

    /**
     * 修改密码
     *
     * @param oldpass 旧密码
     * @param newpass 新密码
     */
    @Override
    public void changePassword(String oldpass, String newpass) throws Exception {
        SysUser sysUser = LoginUserUtil.getSysUser();
        changePassword(sysUser, oldpass, newpass);

    }
    
    @Override
	public void changePassword(SysUser sysUser, String oldpass, String newpass) throws Exception {
    	 String encodedPassword = sysUser.getPassword();
         if (!passwordEncoder.matches(oldpass, encodedPassword)) {//旧密码要base64加密
             throw CustomerException.create(2,ContentConstant.ERROR_OLD_PASSWORD);//errorcode=2 兼容pos机器改密码
         }
         //sysUser.setPassword(passwordEncoder.encode(newpass));
         //2019年10月16日16:04:07 这边前端发过来的密码已经是Base64加密的，需要先解密，再加密存储
         //密码先解密成明文

         String decodePwd = new String(Base64Decoder.decode(newpass));
         SysDept sysDept = sysDeptService.selectByPrimaryKey(sysUser.getDeptId());
         sysUser.setPassword(passwordEncoder.encode(decodePwd));
         SysUser sysUserUpdate = new SysUser().setId(sysUser.getId()).setPassword(sysUser.getPassword());
         Integer changePassWordTime = sysDept.getConfig().getChangePassWordTime();
         if(changePassWordTime>1) {
         	Date nextChangePassTime = DateUtil.offset(DateUtils.deptUtcDate(sysUser.getDeptId()), DateField.DAY_OF_MONTH, changePassWordTime).toJdkDate();
         	sysUserUpdate.setNextChangePassTime(nextChangePassTime);
         }
         sysUserDao.updateByPrimaryKeySelective(sysUserUpdate);
         SpringContextUtils.getCache(CACHE_NAME).evict(sysUser.getId());
         SpringContextUtils.getCache(BRIEF_CACHE_NAME).evict(sysUser.getId());
		
	}

    @Override
    public boolean usernameExist(String username) {
        SysUser record = new SysUser();
        record.setUsername(username);
        int count = sysUserDao.selectCount(record);
        return count > 0;
    }

    /**
     * 重置密码
     *
     * @param id       用户id
     * @param password 新密码
     * @throws Exception 
     */
    @Override
    public void resetPassword(String id, String password) throws Exception {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        SysUser old = selectBriefByPrimaryKey(id);
        SysDept sysDept = sysDeptService.selectByPrimaryKey(old.getDeptId());
        Boolean randomPassword = sysDept.getConfig().getRandomPassword();
        if(randomPassword) {//随机密码、通过邮件发送
       	 String randomString = RandomUtil.randomString("abc0123456789",8);
       	 sysUser.setPassword(passwordEncoder.encode(randomString));
       	 MailUtil.sendPassword(old.getUsername(),randomString, old.getEmail());
       }else {
    	   sysUser.setPassword(passwordEncoder.encode(password));
       }
        sysUserDao.updateByPrimaryKeySelective(sysUser);
        //清除缓存
        SpringContextUtils.getCache(CACHE_NAME).evict(id);
        SpringContextUtils.getCache(BRIEF_CACHE_NAME).evict(id);
    }


    //Vendor 账户充值  
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    //@CacheEvict(value = CACHE_NAME, key = "#userID") // 清空 缓存
    public Boolean accountRecharge(Map<String, Object> param) throws Exception {

        String userID = ParamUtils.getStringWE(param, "userID", ContentConstant.MISS_PARAM);
        BigDecimal rechargeNumber = ParamUtils.getBigDecimalWE(param, "rechargeNumber");
        //对当前的充值金额执行判断，如果当前的金额小于等于0则返回异常

        if (NumberUtil.isLess(rechargeNumber, BigDecimal.ZERO)) {
            throw new Exception(ContentConstant.OBJECT_NOT_EXIST);
        }
        RLock lock = ReddisonUtil.getSysUserLock(userID);
        int result = 0;
        try {
            lock.lock();
            SysUser olduser = sysUserDao.selectByPrimaryKey(userID);
            if (olduser == null) {
                throw new Exception(ContentConstant.OBJECT_NOT_EXIST);
            }
            SysUser update = new SysUser();
            update.setId(userID);
            update.setBalance(olduser.getBalance().add(rechargeNumber));
            result = sysUserDao.updateByPrimaryKeySelective(olduser);
            SpringContextUtils.getCache(CACHE_NAME).evict(userID);
            SpringContextUtils.getCache(BRIEF_CACHE_NAME).evict(userID);
        } finally {
            lock.unlock();
        }
        return result > 0;
    }

    /**
     * 验证用户的相关的基础信息是否存在
     */
    @Override
    public Result userBaseInfoExist(Map<String, Object> condition) throws Exception {
        Example example = new Example(SysUser.class);
        Criteria criteria = example.createCriteria();
        String type = "";
        if (condition != null) {
            //判断用户名
            type = checkExistType(condition, criteria, type, "username");
            //判断邮箱
            type = checkExistType(condition, criteria, type, "email");
            //判断电话
            type = checkExistType(condition, criteria, type, "phone");
            //判断手机号
            type = checkExistType(condition, criteria, type, "telephone");
        }
        int count = sysUserDao.selectCountByExample(example);
        Result result = new Result();
        boolean exist = true;
        String msg = "";
        if (count > 0) {
            switch (type) {
                case "username":
                    msg = "{username.is.exist}";
                    break;
                case "email":
                    msg = "{email.is.exist}";
                    break;
                case "phone":
                    msg = "{phone.is.exist}";
                    break;
                case "telephone":
                    msg = "{telephone.is.exist}";
                    break;
                default:
                    exist = false;
                    break;
            }
        } else {
            exist = false;
        }
        result.put("exist", exist);
        result.put("msg", msg);
        return result;
    }

    @Override
    public String queryUserName(String creatorId) {
        return sysUserDao.queryUserName(creatorId);
    }

    @Override
    public List<ComboModel> queryAllUserBackCombo() {
        Example example = new Example(SysUser.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", 1);
        criteria.andEqualTo("delFlag", 0);
        List<SysUser> sysUsers = sysUserDao.selectByExample(example);
        List<ComboModel> list = new ArrayList<ComboModel>();
        for (SysUser user : sysUsers) {
            ComboModel model = new ComboModel();
            model.setTitle(user.getRealname());
            model.setId(user.getId());
            model.setUsername(user.getUsername());
            list.add(model);
        }
        return list;
    }

    /**
     * 获取当前登录用户总部门下所有的用户信息
     */
    @Override
    public Result listUsersInSameDept(HttpServletRequest request) throws Exception {
        List<String> deptIds = LoginUserUtil.getSysUser().getSysDeptIds();
        if (CollectionUtils.isEmpty(deptIds)) {
            return Result.error("{dept.is.null}");
        }
        String username = request.getParameter("username");
        List<SysUser> sysUsers = sysUserRoleDao.selectByDepts(deptIds);
        List<SysUserModel> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sysUsers)) {
            for (SysUser sysUser : sysUsers) {
                sysUser.setSysDept(sysDeptService.selectByPrimaryKey(sysUser.getDeptId()));
                if (StringUtils.isNotBlank(username)) {
                    if (sysUser.getUsername().contains(username)) {
                        result.add(new SysUserModel(sysUser));
                    }
                } else {
                    result.add(new SysUserModel(sysUser));
                }
            }
        }
        return Result.okData(result);
    }

    /**
     * 获取 当前部门及其子部门的所有用户信息
     *
     * @param id 最外层的部门id
     */
    @Override
    public Result queryUserByDepId(String id) throws Exception {
        List<String> deptIds = sysDeptService.findSubDeptIds(id);
        if (CollectionUtils.isEmpty(deptIds)) {
            return Result.error("{dept.is.null}");
        }
        if (!deptIds.contains(id)) {
            deptIds.add(id);
        }
        List<SysUser> sysUsers = sysUserRoleDao.selectByDepts(deptIds);
        List<SysUserModel> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sysUsers)) {
            for (SysUser sysUser : sysUsers) {
                sysUser.setSysDept(sysDeptService.selectByPrimaryKey(sysUser.getDeptId()));
                result.add(new SysUserModel(sysUser));
            }
        }
        return Result.okData(result);
    }

    /**
     * 获取对应部门下的领导信息，不再将所有用户叠加到一起了
     */
    @Override
    public Result queryDeptLeaders(String deptId) throws Exception {
        SysDept sysDept = sysDeptService.selectByPrimaryKey(deptId);
        SysUser sysUser = queryDeptLeaders(sysDept);
        if (sysUser != null) {
            sysDept.setDeptManager(sysUser);
        }
        return Result.okData(sysDept);
    }

    private SysUser queryDeptLeaders(SysDept sysDept) throws Exception {
        if (sysDept == null) return null;
        String managerId = sysDept.getManagerId();
        if (StringUtils.isBlank(managerId)) {
            return null;
        }
        return selectByPrimaryKey(managerId);
    }

    @Override
    public UserRoleView findUserRoleById(String assignee) throws Exception {
        return sysUserRoleDao.findUserRoleById(assignee);
    }

    @Override
    public void changeUserAvatar(String filePath) throws Exception {
        SysUser sysUser = LoginUserUtil.getSysUser();
        SysUser sysUserUpdate = new SysUser().setId(sysUser.getId()).setAvatar(filePath);
        sysUserDao.updateByPrimaryKeySelective(sysUserUpdate);
        SpringContextUtils.getCache(CACHE_NAME).evict(sysUser.getId());
        SpringContextUtils.getCache(BRIEF_CACHE_NAME).evict(sysUser.getId());
    }

    @Override
    public List<SysUser> selectUsersByIds(List<String> userIds) {
        return sysUserDao.selectByUserIds(userIds);
    }


    private String checkExistType(Map<String, Object> condition, Criteria criteria, String type, String username) {
        if (condition.get(username) != null && !"".equals(condition.get(username))) {
            type = username;
            criteria.andEqualTo(username, condition.get(username));
        }
        return type;
    }

    private Example createExample(HashMap<String, Object> condition) {
        Example example = new Example(SysUser.class);
        Criteria criteria = example.createCriteria();
        if (condition != null) {
            if (condition.get("id") != null && !"".equals(condition.get("id"))) {
                criteria.andEqualTo("id", condition.get("id"));
            }
            if (condition.get("type") != null && !"".equals(condition.get("type"))) {
                criteria.andEqualTo("type", condition.get("type"));
            }
            if (condition.get("delFlag") != null && !"".equals(condition.get("delFlag"))) {
                criteria.andEqualTo("delFlag", condition.get("delFlag"));
            }
            if (condition.get("realname") != null && !"".equals(condition.get("realname"))) {
                criteria.andLike("realname", "%" + condition.get("realname") + "%");
            }
            if (condition.get("username") != null && !"".equals(condition.get("username"))) {
                criteria.andLike("username", "%" + condition.get("username") + "%");
            }
            if (condition.get("email") != null && !"".equals(condition.get("email"))) {
                criteria.andLike("email", "%" + condition.get("email") + "%");
            }
            if (condition.get("phone") != null && !"".equals(condition.get("phone"))) {
                criteria.andLike("phone", "%" + condition.get("phone") + "%");
            }
            if (condition.get("telephone") != null && !"".equals(condition.get("telephone"))) {
                criteria.andLike("telephone", "%" + condition.get("telephone") + "%");
            }
            if (condition.get("deptId") != null && !"".equals(condition.get("deptId"))) {
                criteria.andEqualTo("deptId", condition.get("deptId"));
            }
        }
        return example;
    }

	@Override
	@Transactional
	public void recharge(SysUser user, BigDecimal decimalAmount, Integer source, Integer payMode,String rechargeId) throws Exception {
		
//		RechargeLog rechargeLog=new RechargeLog().setAmount(decimalAmount).
//				setTargetUser(user.getId()).setOperatorTime(DateUtils.deptUtcDate(user.getDeptId()))
//				.setPaymode(payMode).setSource(source).setLastBalance(user.getBalance()).setRechargeId(rechargeId);
//		if(source.equals(TradeView.POS)) {
//			rechargeLog.setOperatorId(user.getId());
//		}else {
//			rechargeLog.setOperatorId(LoginUserUtil.getSysUser().getId());
//		}
//		rechargeLogDao.insertSelective(rechargeLog);
		SysUser update = new SysUser().setId(user.getId()).setBalance(user.getBalance().add(decimalAmount));
		sysUserDao.updateByPrimaryKey(update);
		SpringContextUtils.getCache(CACHE_NAME).evict(user.getId());
        SpringContextUtils.getCache(BRIEF_CACHE_NAME).evict(user.getId());
	}

	


}
