package top.lrshuai.skill.user.service.impl;

import top.lrshuai.skill.user.entity.Users;
import top.lrshuai.skill.user.mapper.UsersMapper;
import top.lrshuai.skill.user.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2020-05-28
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
