package com.gitee.osinn.im.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务实现类
 *
 * @author 乐天
 * @since 2018-10-07
 */
@Service
@Qualifier(value = "imUserService")
public class ImUserServiceImpl extends ServiceImpl<ImUserMapper, ImUser> implements IImUserService {


    @Override
    public ImUser getById(Long id) {
        return super.getById(id);
    }

    @Override
    public ImUser getByLoginName(String loginName) {
        QueryWrapper<ImUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", loginName);
        return baseMapper.selectOne(queryWrapper);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerUser(ImUser imUser) {
        baseMapper.insert(imUser);
    }

    @Override
    public Map<String, Long> groupByDept() {
        List<Map> list =  baseMapper.groupByDept();
        Map<String,Long> res = new HashMap<>();
        for(Map map:list){
            if(map.get("dept_id")!=null){
                res.put((String) map.get("dept_id"),(Long)map.get("count"));
            }

        }
        return res;
    }
}
