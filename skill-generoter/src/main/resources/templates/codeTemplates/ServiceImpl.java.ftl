package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.lrshuai.common.constant.Result;
import top.lrshuai.common.dto.PageDTO;
import org.springframework.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import top.lrshuai.mini.admin.admin.entity.User;


/**
* <p>
    * ${table.comment!} 服务实现类
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Service
@Transactional
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName}{

@Override
public Result getList(PageDTO dto) throws Exception {
IPage<${entity}> page = new Page<>();
if(dto.getPageNo() != null){
page.setCurrent(dto.getPageNo());
}
if(dto.getPageSize() != null){
page.setSize(dto.getPageSize());
}
QueryWrapper<${entity}> queryWrapper = new QueryWrapper();
//        if(!StringUtils.isEmpty(dto.getKeyword())){
//            queryWrapper.lambda()
//                    .like(${entity}::getAuther,dto.getKeyword())
//                    .like(${entity}::getContent,dto.getKeyword())
//                    .like(${entity}::getTitle,dto.getKeyword());
//        }
IPage<${entity}> iPage = this.page(page, queryWrapper);
return Result.ok(iPage);
}

@Override
public Result add(${entity} item, User user) throws Exception {
if(item.getCreateTime() == null){
item.setCreateTime(LocalDateTime.now());
}
this.save(item);
return Result.ok();
}

@Override
public Result edit(${entity} item, User user) throws Exception {
this.updateById(item);
return Result.ok();
}

@Override
public Result del(Long id, User user) throws Exception {
this.removeById(id);
return Result.ok();
}

@Override
public Result getDetail(Long id) throws Exception {
${entity} item = this.getOne(new QueryWrapper<${entity}>().lambda().eq(${entity}::getId,id));
return Result.ok(item);
}
}
