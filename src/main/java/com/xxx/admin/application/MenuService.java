package com.xxx.admin.application;

import com.xxx.admin.domain.modle.Menu;
import com.xxx.admin.domain.modle.TreeModel;
import com.xxx.admin.domain.repository.MenuRepository;
import com.xxx.admin.domain.repository.RoleRepository;
import com.xxx.admin.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Spring缓存注解@Cache,@CachePut , @CacheEvict，@CacheConfig使用
 * http://blog.csdn.net/sanjay_f/article/details/47372967
 * @Cacheable 的作用 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存
 * @CachePut 的作用 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用
 * @CachEvict 的作用 主要针对方法配置，能够根据一定的条件对缓存进行清空
 * Created by xiexx on 2016/9/17.
 */
@Service
@CacheConfig(cacheNames = "menulist")
public class MenuService {

    @Autowired
    protected MenuRepository menuRepository;

    @Autowired
    protected RoleRepository roleRepository;

    /*cache操作相关的注解中key都是spel表达式，字符串需要用''*/
    @Caching(
            put = @CachePut(key = "#menu.id"),
            evict = {@CacheEvict(key = "'list'"), @CacheEvict(value = "user-nav-menu", allEntries = true)}
    )
    public Menu create(Menu menu) {
        validate(menu);
        menuRepository.add(menu);
        return menu;
    }

    @Caching(
            put = @CachePut(key = "#menu.id"),
            evict = {@CacheEvict(key = "'list'"), @CacheEvict(value = "user-nav-menu", allEntries = true)}
    )
    public Menu modify(Menu menu) {
        validate(menu);
        menuRepository.update(menu);
        return menu;
    }


    @Cacheable
    public Menu get(String code) {
        return menuRepository.get(code);
    }

    @Caching(
            evict = {@CacheEvict(key = "'list'"), @CacheEvict(key = "#code"), @CacheEvict(value = "user-nav-menu", allEntries = true)}
    )
    public void delete(String code) {
        roleRepository.removeRoleMenuByMenuId(code);
        menuRepository.remove(code);
    }

    /**
     * 当调用这个方法的时候，会从一个名叫 list 的缓存中查询，如果没有，则执行实际的方法（即查询数据库），并将执行的结果存入缓存中，否则返回缓存中的对象
     * @return
     */
    @Cacheable(key = "'list'")
    public List<Menu> list() {
        List<Menu> list = menuRepository.list();
        TreeModel.sortByTree(list);
        return list;
    }

    @Caching(
            evict = {@CacheEvict(key = "'list'"), @CacheEvict(key = "#menu"), @CacheEvict(value = "user-nav-menu", allEntries = true)}
    )
    public void switchStatus(String menu, boolean disable) {
        menuRepository.switchStatus(menu, disable);
    }

    @Cacheable(value = "user-nav-menu")
    public List<Menu> getNavMenus(String uid) {
        List<Menu> list = null;
        if (SecurityUtil.isRoot()) {
            list = menuRepository.list();
        } else {
            list = menuRepository.getNavMenus(uid);
        }
        return (List<Menu>) TreeModel.buildTree(list);
    }

    private void validate(Menu menu) {
        Assert.hasText(menu.getId());
        Assert.hasText(menu.getLabel());
    }

}
