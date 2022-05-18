
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Driver;
import java.util.*;


/**
 * 代码生成器演示
 *
 * @author hubin
 * @since 2016-12-01
 */
public class MysqlGenerator  {


    // 输出的目录
    public static final String OUT_DIR = "D:\\doc\\WorkSpace\\vegetables\\src\\main\\java";

    /**
     * mysql配置
     */
    //mysql用户名
    public static final String MYSQL_USER_NAME = "vegetables";
    //mysql密码
    public static final String MYSQL_PASS_WORD = "vegetables0928..";

    //表前缀
    public static final String TABLE_PREFIX = "skl_";


    /**
     * java配置
     */

    //控制器包名
    public static final String CONTROLLER_NAME = "controller.mgmt";
    public static final String SERVICE_NAME = "service";
    public static final String SERVICE_IMPL_NAME = "service.impl";
    //model
    public static final String MODEL_NAME = "model";
    //mapper包名
    public static final String MAPPER_NAME = "dao";
    //xml路径
    public static final String XML_URL = "D:\\doc\\WorkSpace\\vegetables\\src\\main\\resources\\mapper\\";

    //自定义实体，公共字段
    public static final String[] SUPER_ENTITY_COLUMNS = new String[]{"id", "deleted", "create_time", "create_people", "modify_time", "modify_people"};


    /*****************************生成项目需要修改的配置****************************************/

    //项目名称
    public static final String PROJECT_NAME = "";


    //mysql的url
    public static final String MYSQL_URL = "jdbc:mysql://124.222.96.147:3306/vegetables?useUnicode=true&characterEncoding=utf-8";
    //需要生成的表名
    public static final String[] TABLE_NAMES = new String[]{"test"};


    //自定义包路径
    public static final String PACKAGE_URL = "com.example.vegetables";

    //自定义公共字段实体父类
    public static final String SUPER_ENTITY_CLASS = "com.example.vegetables.model.BaseEntity";

    //是否覆盖 service controller等 默认覆盖entity
    private static boolean IS_OVER_CONTROLLER = true;
    private static boolean IS_OVER_SERVICE = true;
    private static boolean IS_OVER_SERVICE_IMPL = true;
    private static boolean IS_OVER_MAPPER = true;
    private static boolean IS_OVER_XML = true;

    /*****************************生成项目需要修改的配置****************************************/


    /**
     * MySQL 生成演示
     */
    public static void main(String[] args) {
        // int result = scanner();
        int result = 1;
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        //  tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
                // 全局配置
                new GlobalConfig()
                        .setOutputDir(PROJECT_NAME + OUT_DIR)//输出目录
                        .setFileOverride(true)// 是否覆盖文件
                        .setActiveRecord(true)// 开启 activeRecord 模式
                        .setEnableCache(false)// XML 二级缓存
                        .setBaseResultMap(true)// XML ResultMap
                        .setBaseColumnList(true)// XML columList
                        //.setKotlin(true) 是否生成 kotlin 代码
                        .setAuthor("朱归华")
                       // .setSwagger2(true)
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                // .setEntityName("%sEntity");
                // .setMapperName("%sDao")
                // .setXmlName("%sDao")
                // .setServiceName("MP%sService")
                // .setServiceImplName("%sServiceDiy")
                // .setControllerName("%sAction")
        ).setDataSource(
                // 数据源配置
                new DataSourceConfig()
                        .setDbType(DbType.MYSQL) // 数据库类型
                        .setTypeConvert(new MySqlTypeConvert() {
                            // 自定义数据库表字段类型转换【可选】
                            @Override
                            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                                System.out.println("转换类型：" + fieldType);
                                // if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
                                //    return DbColumnType.BOOLEAN;
                                // }
                                //处理3.1.2之后日期的问题
                                if (fieldType.toLowerCase().contains("datetime")) {
                                    return DbColumnType.DATE;
                                }
                                //处理tinyint类型转换成int
                                if (fieldType.toLowerCase().contains("tinyint")) {
                                    return DbColumnType.INTEGER;
                                }
                                return super.processTypeConvert(globalConfig, fieldType);
                            }
                        })
                        .setDbQuery(new MySqlQuery() {

                            /**
                             * 重写父类预留查询自定义字段<br>
                             * 这里查询的 SQL 对应父类 tableFieldsSql 的查询字段，默认不能满足你的需求请重写它<br>
                             * 模板中调用：  table.fields 获取所有字段信息，
                             * 然后循环字段获取 field.customMap 从 MAP 中获取注入字段如下  NULL 或者 PRIVILEGES
                             */
                            @Override
                            public String[] fieldCustom() {
                                return new String[]{"NULL", "PRIVILEGES"};
                            }
                        })
                        .setDriverName(Driver.class.getName())
                        .setUsername(MYSQL_USER_NAME)
                        .setPassword(MYSQL_PASS_WORD)
                        .setUrl(MYSQL_URL)
        ).setStrategy(
                // 策略配置
                new StrategyConfig()
                        // .setCapitalMode(true)// 全局大写命名
                        // .setDbColumnUnderline(true)//全局下划线命名
                        .setTablePrefix(new String[]{TABLE_PREFIX})// 此处可以修改为您的表前缀
                        .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                        .setInclude(TABLE_NAMES) // 需要生成的表
                        // .setExclude(new String[]{"test"}) // 排除生成的表
                        // 自定义实体父类
                        .setSuperEntityClass(SUPER_ENTITY_CLASS)
                        // 自定义实体，公共字段
                        .setSuperEntityColumns(SUPER_ENTITY_COLUMNS)
                        .setTableFillList(tableFillList)
                        .setEntityBooleanColumnRemoveIsPrefix(true)
                        // 自定义 mapper 父类
                        // .setSuperMapperClass("com.baomidou.demo.TestMapper")
                        // 自定义 service 父类
                        // .setSuperServiceClass("com.baomidou.demo.TestService")
                        // 自定义 service 实现类父类
                        // .setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl")
                        // 自定义 controller 父类
                        // .setSuperControllerClass("com.baomidou.demo.TestController")
                        // 【实体】是否生成字段常量（默认 false）
                        // public static final String ID = "test_id";
                        // .setEntityColumnConstant(true)
                        // 【实体】是否为构建者模型（默认 false）
                        // public User setName(String name) {this.name = name; return this;}
                        // .setEntityBuilderModel(true)
                        // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                        .setEntityLombokModel(true)
                // Boolean类型字段是否移除is前缀处理
                // .setEntityBooleanColumnRemoveIsPrefix(true)
                // .setRestControllerStyle(true)
                // .setControllerMappingHyphenStyle(true)
        ).setPackageInfo(
                // 包配置
                new PackageConfig()
                        // .setModuleName("")
                        .setParent(PACKAGE_URL)// 自定义包路径
                        .setController(CONTROLLER_NAME)// 这里是控制器包名，默认 web
                        .setService(SERVICE_NAME)
                        .setServiceImpl(SERVICE_IMPL_NAME)
                        .setEntity(MODEL_NAME)
                        .setMapper(MAPPER_NAME)
        ).setCfg(
                // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                new InjectionConfig() {
                    @Override
                    public void initMap() {
                        Map<String, Object> map = new HashMap<>();
                        //   map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                        this.setMap(map);
                    }
                }.setFileOutConfigList(Collections.singletonList(new FileOutConfig(
                        "/templates/mapper.xml" + ((1 == result) ? ".ftl" : ".vm")) {
                    // 自定义输出文件目录
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        return PROJECT_NAME + XML_URL + tableInfo.getEntityName() + ".xml";
                    }
                }))
        );

        TemplateConfig templateConfig = new TemplateConfig();
        if (!IS_OVER_CONTROLLER) {
            templateConfig.setController(null);
        }
        if (!IS_OVER_SERVICE) {
            templateConfig.setService(null);
        }
        if (!IS_OVER_SERVICE_IMPL) {
            templateConfig.setServiceImpl(null);
        }
        templateConfig.setEntity("template/entity2.java");
        if (!IS_OVER_MAPPER) {
            templateConfig.setMapper(null);
        }
        if (!IS_OVER_XML) {
            templateConfig.setXml(null);
        }
        mpg.setTemplate(templateConfig);

        // 执行生成
        if (1 == result) {
            mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        }
        mpg.execute();

        // 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
        //System.err.println(mpg.getCfg().getMap().get("abc"));
    }

}
