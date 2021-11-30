package com.laison.erp.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laison.erp.common.Result;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.DateUtils;
import com.laison.erp.common.utils.LoginUserUtil;
import com.laison.erp.common.utils.SpringContextUtils;
import com.laison.erp.common.utils.StringUtils;
import com.laison.erp.dao.sys.SysMenuDao;
import com.laison.erp.model.sys.SysMenu;
import com.laison.erp.service.sys.SysMenuService;
import com.laison.erp.service.sys.SysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * @author lihua 496583747@qq.com
 * @date 2021-02-01 11:02:08
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {


    @Autowired
    private SysMenuDao sysMenuDao;


    @Override
    public int countByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        return sysMenuDao.selectCountByExample(example);
    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)// 清空  缓存
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        return sysMenuDao.deleteByExample(example);
    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)// 清空  缓存
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKey(String id) throws Exception {
        Example example = new Example(SysMenu.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId", id);
        int count = sysMenuDao.selectCountByExample(example);
        if (count > 0) {
            throw new Exception(ContentConstant.CAN_NOT_DELETE);
        }
        SysMenu sysMenu = sysMenuDao.selectByPrimaryKey(id);
        if (sysMenu != null) {

            SysMenu q = new SysMenu().setParentId(sysMenu.getParentId());
            count = sysMenuDao.selectCount(q);//我的父亲有几个孩子
            if (count == 1) {
                SysMenu up = new SysMenu().setId(sysMenu.getParentId()).setHasChild(false);
                sysMenuDao.updateByPrimaryKeySelective(up);
            }

        }
        Cache cache = SpringContextUtils.getCache(SysUserService.CACHE_NAME);
        cache.clear();
        return sysMenuDao.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int save(SysMenu record) throws Exception {
        String parentId = record.getParentId();
        SysMenu pmenu = sysMenuDao.selectByPrimaryKey(parentId);
        if (pmenu == null) {
            throw new Exception(ContentConstant.P_OBJECT_NOT_EXIST);
        }
        Integer pmenuType = pmenu.getMenuType();
        Integer menuType = record.getMenuType();
        //则要验证儿子类型
        if (pmenuType.equals(SysMenu.DIR_TYPE)) {//目录下能添加目录和url
            if (menuType.equals(SysMenu.BUTTON_TYPE)) {
                throw new Exception("目录下不能添加按钮");
            }
        } else if (pmenuType.equals(SysMenu.URL_TYPE)) {//url下只能添加botton
            if (!menuType.equals(SysMenu.BUTTON_TYPE)) {
                throw new Exception("URL目录下只能添加按钮");
            }
        } else {
            throw new Exception("按钮类型下午饭添加子菜单");
        }
        String uid = LoginUserUtil.getSysUser().getId();
        record.setCreateBy(uid);
        record.setUpdateBy(uid);
        record.setCreateTime(DateUtils.utcDate());
        record.setUpdateTime(DateUtils.utcDate());
        record.setLevel(pmenu.getLevel() + 1);
        record.setPaths(pmenu.getPaths() + pmenu.getId() + ",");
        if (!pmenu.getHasChild()) {
            pmenu.setHasChild(true);
            sysMenuDao.updateByPrimaryKeySelective(pmenu);
        }
        Cache cache = SpringContextUtils.getCache(CACHE_NAME);
        cache.clear();
        cache = SpringContextUtils.getCache(SysUserService.CACHE_NAME);
        cache.clear();
        return sysMenuDao.insertSelective(record);

    }

    @Override
    public List<SysMenu> selectAllByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        String orderByClause = (String) condition.get("orderByClause");
        if (orderByClause != null) {
            example.setOrderByClause(orderByClause);
        }
        return sysMenuDao.selectByExample(example);
    }

    @Override
    public PageInfo<SysMenu> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
            throws Exception {
        Example example = createExample(condition);

        PageHelper.startPage(pageNum, pageSize, true);
        String orderByClause = (String) condition.get("orderByClause");
        if (orderByClause != null) {
            example.setOrderByClause(orderByClause);
        }
        List<SysMenu> list = sysMenuDao.selectByExample(example);
        PageInfo<SysMenu> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public SysMenu selectFirstByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        String orderByClause = (String) condition.get("orderByClause");
        if (orderByClause != null) {
            example.setOrderByClause(orderByClause);
        }
        PageHelper.startPage(1, 1, true);
        List<SysMenu> list = sysMenuDao.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }

    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "#id", sync = true)
    public SysMenu selectByPrimaryKey(String id) throws Exception {

        SysMenu sysMenu = sysMenuDao.selectByPrimaryKey(id);
        loadchildMenus(sysMenu);
        return sysMenu;
    }

    private void loadchildMenus(SysMenu sysMenu) {
        String id = sysMenu.getId();


        // 开始设置子组织
        List<SysMenu> menus = sysMenuDao.selectChildMenusByParentId(id);
        if (!CollectionUtils.isEmpty(menus)) {
            for (SysMenu menu : menus) {
                loadchildMenus(menu);
            }
        }
        sysMenu.setChildren(menus);

    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)// 清空  缓存
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByCondition(SysMenu record, HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        return sysMenuDao.updateByExample(record, example);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = CACHE_NAME, allEntries = true)// 清空  缓存
    public int updateByPrimaryKey(SysMenu record) throws Exception {
        record.setLevel(null);
        record.setPaths(null);
        record.setParentId(null);
        Cache cache = SpringContextUtils.getCache(CACHE_NAME);
        cache.clear();
        cache = SpringContextUtils.getCache(SysUserService.CACHE_NAME);
        cache.clear();
        return sysMenuDao.updateByPrimaryKeySelective(record);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKeys(List<String> ids) throws Exception {
        Example example = new Example(SysMenu.class);
        Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        Cache cache = SpringContextUtils.getCache(CACHE_NAME);
        ((List<String>) ids).forEach((id) -> cache.evict(id));
        return sysMenuDao.deleteByExample(example);
    }

    private Example createExample(HashMap<String, Object> condition) {
        Example example = new Example(SysMenu.class);
        Criteria criteria = example.createCriteria();
        if (condition != null) {
            if (condition.get("id") != null && !"".equals(condition.get("id"))) {
                criteria.andEqualTo("id", condition.get("id"));
            }
            if (condition.get("level") != null && !"".equals(condition.get("level"))) {
                criteria.andEqualTo("level", condition.get("level"));
            }
            if (condition.get("title") != null && !"".equals(condition.get("title"))) {
                criteria.andLike("title", "%" + condition.get("title") + "%");
            }
            if (condition.get("pids") != null) {
                criteria.andIn("parentId", (List) condition.get("pids"));
            }
        }
        return example;
    }

    @Override
    public List<String> findAllAuthoritys() {
        return sysMenuDao.findAllAuthoritys();
    }

    @Override
    public SysMenu findUserMenus(String uid, SysMenu allSysMenu) {
        Set<String> menuIds = sysMenuDao.findAllUserMenuIds(uid);
        menuIds.add("0");
        menuIds.add("1");
        menuIds.add("2");
        menuIds.add("3");
        menuIds.add("4");
        return selectMenu(menuIds, allSysMenu);
    }


    private SysMenu selectMenu(Set<String> menuIds, SysMenu sysMenu) {
        if (!menuIds.contains(sysMenu.getId())) {
            return null;
        } else {
            List<SysMenu> childMenus = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(childMenus)) {
                Iterator<SysMenu> iterator = childMenus.iterator();
                while (iterator.hasNext()) {
                    SysMenu next = iterator.next();
                    SysMenu returnMenu = selectMenu(menuIds, next);
                    if (returnMenu == null) {
                        iterator.remove();
                    }
                }
            }
            return sysMenu;
        }

    }

    @Override
    public List<String> findAllMenus() {
        return sysMenuDao.findAllMenus();
    }

    /**
     * 获取当前节点的菜单树
     */
    @Override
    @Cacheable(value = CACHE_NAME, key = "#id", sync = true)
    public Result findMenuTree(String id) throws Exception {
        if (StringUtils.isBlank(id)) {
            id = "0";
        }
        //使用递归法获取全部的数据结构
        SysMenu root = sysMenuDao.selectByPrimaryKey(id);
        if (root == null) return Result.error("查询的菜单不存在");
        //查询所有子菜单为该节点的菜单列表
        rebuildMenu(root);
        return Result.okData(root);
    }

    private void rebuildMenu(SysMenu sysMenu) {
        if (sysMenu.getHasChild()) {
            List<SysMenu> sysMenus = sysMenuDao.selectChildMenusByParentId(sysMenu.getId());
            if (CollectionUtils.isNotEmpty(sysMenus)) {
                for (SysMenu m : sysMenus) {
                    rebuildMenu(m);
                }
            }
            sysMenu.setChildren(sysMenus);
        }
    }
}
