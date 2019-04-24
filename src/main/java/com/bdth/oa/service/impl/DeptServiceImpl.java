package com.bdth.oa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bdth.oa.domain.Dept;
import com.bdth.oa.mapper.DeptMapper;
import com.bdth.oa.service.DeptService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author hujing
 * @since 2019-04-23
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

}
