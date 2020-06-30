package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import top.lrshuai.common.constant.Result;
import top.lrshuai.common.dto.PageDTO;
import top.lrshuai.mini.admin.admin.entity.User;

/**
* <p>
    * ${table.comment!} 服务类
    * </p>
*
* @author rstyro
* @since ${.now?date}
*/
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {
public Result getList(PageDTO dto) throws  Exception;
public Result add(${entity} item, User user) throws  Exception;
public Result edit(${entity} item, User user) throws  Exception;
public Result del(Long id, User user) throws  Exception;
public Result getDetail(Long id) throws  Exception;
}
