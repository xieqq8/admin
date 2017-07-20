package org.bumishi.admin.infrastructure;


import org.springframework.beans.BeanUtils;

/**
 * @author qiang.xie
 * @date 2016/9/29
 */
public class BeanUtil {

    public static void copeProperties(Object from,Object dest){
        try {
            BeanUtils.copyProperties(from, dest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
