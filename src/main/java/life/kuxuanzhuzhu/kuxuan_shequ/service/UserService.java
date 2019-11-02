package life.kuxuanzhuzhu.kuxuan_shequ.service;

import life.kuxuanzhuzhu.kuxuan_shequ.mapper.UserMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 邓鑫鑫
 * @date 2019年07月25日 21:00:11
 * @Description
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    /**
     * 获取用户信息后，判断该用户是否在数据库已存在记录，进行新增或更新操作
     * @param user
     */
    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if(null != dbUser){
            //更新
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }else{
            //新增
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
    }
}
