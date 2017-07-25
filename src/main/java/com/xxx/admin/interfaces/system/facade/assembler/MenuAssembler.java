package com.xxx.admin.interfaces.system.facade.assembler;

import com.xxx.admin.domain.modle.Menu;
import com.xxx.admin.infrastructure.BeanUtil;
import com.xxx.admin.interfaces.system.facade.commondobject.MenuCreateCommand;
import com.xxx.admin.interfaces.system.facade.commondobject.MenuUpdateCommond;

/**
 * Created by xiexx on 2016/10/30.
 */
public class MenuAssembler {

    public static Menu updateCommendToDomain(String id, MenuUpdateCommond updateCommond) {
        Menu menu=new Menu();
      BeanUtil.copeProperties(updateCommond,menu);
        menu.setId(id);
        return menu;
    }

    public static Menu createCommendToDomain(MenuCreateCommand creteCommand){
        Menu menu=new Menu();
        BeanUtil.copeProperties(creteCommand,menu);
        return menu;
    }
}
