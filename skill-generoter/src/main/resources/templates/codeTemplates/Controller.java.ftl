package top.lrshuai.mini.admin.${package.ModuleName}.controller;
import top.lrshuai.common.annotation.Permission;
import top.lrshuai.common.annotation.PermissionType;
import top.lrshuai.mini.admin.${package.ModuleName}.entity.${table.entityName};
import top.lrshuai.mini.admin.${package.ModuleName}.service.${table.serviceName};
import top.lrshuai.common.dto.PageDTO;
import top.lrshuai.mini.admin.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
* <p>
    * ${table.comment!} 前端控制器
    * </p>
*
* @author rstyro
* @since ${.now?date}
*/
@Controller
@RequestMapping("/${package.ModuleName}/${table.entityPath}")
public class ${table.controllerName} extends BaseController {

private final static String qxurl = "${package.ModuleName}/${table.entityPath}/list";

@Autowired
private ${table.serviceName} ${table.entityPath}Service;

@GetMapping("/list")
public String list(Model model, PageDTO dto) throws Exception {
System.out.println(dto);
model.addAttribute("keyword",dto.getKeyword());
model.addAttribute("list",${table.entityPath}Service.getList(dto));
return "page/${package.ModuleName}/${table.entityPath}_list";
}

@PostMapping(value="/add")
@ResponseBody
@Permission(url = qxurl,type = PermissionType.ADD)
public Object add(${table.entityName} item) throws Exception {
item.setId(null);
return ${table.entityPath}Service.add(item,this.getAdminUser());
}

@PostMapping(value="/edit")
@ResponseBody
@Permission(url = qxurl,type = PermissionType.EDIT)
public Object edit(${table.entityName} item) throws Exception {
return ${table.entityPath}Service.edit(item,this.getAdminUser());
}

@PostMapping(value="/del")
@ResponseBody
@Permission(url = qxurl,type = PermissionType.DEL)
public Object del(Long id) throws Exception {
return ${table.entityPath}Service.del(id,this.getAdminUser());
}

@GetMapping(value="/query")
@ResponseBody
@Permission(url = qxurl,type = PermissionType.QUERY)
public Object query(Long id) throws Exception {
return ${table.entityPath}Service.getDetail(id);
}

}
