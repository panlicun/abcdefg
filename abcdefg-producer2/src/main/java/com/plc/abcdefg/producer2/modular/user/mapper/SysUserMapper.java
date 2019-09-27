package com.plc.abcdefg.producer2.modular.user.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.plc.abcdefg.producer2.modular.user.model.SysUser;
import com.plc.producer2.modular.user.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 管理员表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过账号获取用户
     */
    SysUser getByAccount(@Param("account") String account);

    List<SysUser> selectAllByPage(@Param("page") Page<SysUser> page);
}