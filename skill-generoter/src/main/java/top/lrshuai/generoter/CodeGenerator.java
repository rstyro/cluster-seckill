package top.lrshuai.generoter;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Mybatis-plus 的代码生成类
 */
public class CodeGenerator {

    // 基本包路径
    public static String packageName = "top.lrshuai.skill";
    //    public static String packageName="top.lrshuai.skill.admin";
    //admin 模块项目名称
    public static String adminProjectName = "skill-admin";
    // entity
    public static String entityProjectName = "skill-entitys";

    public static String controllerProjectName = "skill-controller";

    //页面生成根目录
    public static String projectPageRelativePath = "/" + adminProjectName + "/src/main/resources/templates/page/";
    //自定义 页面模板路径
    public static String pageTemplatesPath = "templates/pageTemplates/list.ftl";

    //自定义Controller模板路径
    public static String controlTemplatesPath = "templates/codeTemplates/Controller.java.ftl";
    //自定义ServiceImpl模板路径
    public static String serviceImplTemplatesPath = "templates/codeTemplates/ServiceImpl.java.ftl";
    //自定义Service模板路径
    public static String serviceTemplatesPath = "templates/codeTemplates/Service.java.ftl";
   // 自定义 entity 模板
    public static String entityTemplatesPath = "templates/codeTemplates/entity.java.ftl";


    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        String projectPath = System.getProperty("user.dir")+"/";
        // 全局配置
        mpg.setGlobalConfig(new GlobalConfig().setOutputDir(projectPath + controllerProjectName + "/src/main/java").setAuthor("rstyro").setOpen(false));

        // 数据源配置
        mpg.setDataSource(setDataSourceConfig());

        // 包配置
        PackageConfig pc = new PackageConfig();
        String modelName = scanner("模块名");
        pc.setModuleName(modelName);
        pc.setParent(packageName);
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = setStrategyConfig(pc.getModuleName());
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            public void initMap() {
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        //项目相对路径
        String projectRelative =  "/src/main/java/" + packageName.replace(".", "/") + "/" + modelName;
        // entity 肯定是要的,并且独立出来公用
        setCustomFileOutConfig(focList,entityTemplatesPath,projectPath +entityProjectName+projectRelative+ "/entity/%s" + StringPool.DOT_JAVA, CodeTypeName.ENTITY);
        String isGeneratorPage = scanner("是否是生成admin模板：1 -- 是，0 -- 否");
        if ("1".equals(isGeneratorPage)) {
            strategy.setRestControllerStyle(false);
            projectRelative= "/adminProjectName"+projectRelative;
            setAdminFileOutConfig(focList,projectPath,projectRelative,modelName);
        }else{
            // 下面的模板路径用的是mybatis-plus 自带的模板路径
            setCustomFileOutConfig(focList,"/templates/controller.java.ftl",projectPath + controllerProjectName+projectRelative + "/controller/%s"  + StringPool.DOT_JAVA, CodeTypeName.CONTROLLER);
            setCustomFileOutConfig(focList,"/templates/serviceImpl.java.ftl",projectPath + controllerProjectName+projectRelative + "/service/impl/%s" + StringPool.DOT_JAVA, CodeTypeName.SERVICE_IMPL);
            setCustomFileOutConfig(focList,"/templates/service.java.ftl",projectPath + controllerProjectName+projectRelative + "/service/%s" + StringPool.DOT_JAVA, CodeTypeName.SERVICE);
            setCustomFileOutConfig(focList,"/templates/mapper.java.ftl",projectPath + controllerProjectName+projectRelative + "/mapper/%sMapper" + StringPool.DOT_JAVA, CodeTypeName.ENTITY);
            setCustomFileOutConfig(focList,"/templates/mapper.xml.ftl",projectPath + controllerProjectName + "/src/main/resources/mapper/" + pc.getModuleName() + "/%sMapper" + StringPool.DOT_XML,CodeTypeName.ENTITY);
        }
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setEntity(null).setXml(null).setController(null).setService(null).setServiceImpl(null).setMapper(null).setEntityKt(null));
        mpg.setTemplateEngine(new MyFreemarkerTemplateEngine());
        mpg.setStrategy(strategy);
        mpg.execute();
    }



    public static DataSourceConfig  setDataSourceConfig(){
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/skill?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Hongkong");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        return dsc;
    }

    // 设置策略
    public static StrategyConfig  setStrategyConfig(String moduleName){
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.lrshuai.skill");
        strategy.setEntityLombokModel(true);
        strategy.setSuperControllerClass(packageName + ".base.BaseController");
        strategy.setInclude(scanner("表名"));
//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setRestControllerStyle(true);
//        strategy.setTablePrefix(moduleName + "_"); //是否去除表前缀
        return strategy;
    }

    /**
     * 自定义文件输出
     * @param focList
     * @param templatePath 模板路径
     * @param outFilePath 输出路径
     * @param codeTypeName format 类型
     */
    public static void setCustomFileOutConfig(List<FileOutConfig> focList, String templatePath, String outFilePath, CodeTypeName codeTypeName){
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return String.format(outFilePath, getTagClassName(tableInfo,codeTypeName));
            }
        });
    }

    public static String getTagClassName(TableInfo tableInfo, CodeTypeName codeTypeName){
        if(CodeTypeName.ENTITY.equals(codeTypeName)){
            return tableInfo.getEntityName();
        }else if(CodeTypeName.SERVICE.equals(codeTypeName)){
            return tableInfo.getServiceName();
        }else if(CodeTypeName.SERVICE_IMPL.equals(codeTypeName)){
            return tableInfo.getServiceImplName();
        }else if(CodeTypeName.CONTROLLER.equals(codeTypeName)){
            return tableInfo.getControllerName();
        }
        return null;
    }

    public static void setAdminFileOutConfig(List<FileOutConfig> focList,String projectPath,String projectRelative,String modelName){
        // 以下都是自定义模板
        setCustomFileOutConfig(focList,pageTemplatesPath,projectPath + projectPageRelativePath + modelName + "/%s_list.html", CodeTypeName.ENTITY);
        setCustomFileOutConfig(focList,controlTemplatesPath,projectPath + projectRelative + "/controller/%s"  + StringPool.DOT_JAVA, CodeTypeName.CONTROLLER);
        setCustomFileOutConfig(focList,serviceImplTemplatesPath,projectPath + projectRelative + "/service/impl/%s" + StringPool.DOT_JAVA, CodeTypeName.SERVICE_IMPL);
        setCustomFileOutConfig(focList,serviceTemplatesPath,projectPath + projectRelative + "/service/%s" + StringPool.DOT_JAVA, CodeTypeName.SERVICE);
        setCustomFileOutConfig(focList,"/templates/mapper.xml.ftl",projectPath + projectRelative + "/src/main/resources/mapper/" + modelName + "/%sMapper" + StringPool.DOT_XML,CodeTypeName.ENTITY);
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}
