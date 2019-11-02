package life.kuxuanzhuzhu.kuxuan_shequ.service;

import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomErrorCode;
import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomException;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.KxUserMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.model.KxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 邓鑫鑫
 * @date 2019年09月27日 16:10:56
 * @Description
 */
@Service
public class KxUserService {

    @Autowired
    private KxUserMapper kxUserMapper;


    public Integer insert(KxUser kxUser){
        Integer row = kxUserMapper.insert(kxUser);
        if(row == 1){
            return row;
        }
        throw new CustomException(CustomErrorCode.REGISTER_FAIL);
    }

    public KxUser selectByToken(String token){
        return kxUserMapper.selectByToken(token);
    }


    public KxUser selectByEmail(String email){
        return kxUserMapper.selectByEmail(email);
    }

    public KxUser login(String email,String pass){
        return kxUserMapper.login(email,pass);
    }
}
