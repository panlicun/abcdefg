package com.plc.abcdefg.oauth.auth.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import com.plc.abcdefg.kernel.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * <p>
  * 管理员表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过账号获取用户
     */
    User getByAccount(@Param("account") String account);

}