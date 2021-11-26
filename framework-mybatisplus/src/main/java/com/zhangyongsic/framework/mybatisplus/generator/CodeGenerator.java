package com.zhangyongsic.framework.mybatisplus.generator;


import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author zhang yong
 */
public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //String projectPath = System.getProperty("user.dir");
        //gc.setOutputDir(projectPath + "c:/codes/src/main/java");
        gc.setOutputDir("c:/codes/src/main/java");
        gc.setAuthor("daddy");
        gc.setOpen(false);
        autoGenerator.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/smdh2?serverTimezone=Hongkong&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        autoGenerator.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));

		/*-------------------------------------
		|                修改包                |
		=======================================*/

        pc.setParent("com.fengyan.smdh2.module");

        autoGenerator.setPackageInfo(pc);
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                // 自定义输入文件名称
                return "c:/codes/src/main/resources/mapper/" + pc.getModuleName() + "/"
                        + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        autoGenerator.setCfg(cfg);
        autoGenerator.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 下划线转驼峰命名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 是否为lombok模型
        strategy.setEntityLombokModel(true);
        // 生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        // 自定义继承的Controller类全称，带包名
        //strategy.setSuperControllerClass("com.fengyan.smdh.admin.core.controller.BaseController");
        // 自定义继承的Entity类全称，带包名
        //strategy.setSuperEntityClass("com.fengyan.smdh.admin.core.entity.BaseEntity");
        // 自定义基础的Entity类，公共字段
        //strategy.setSuperEntityColumns("id");
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);

		/*-------------------------------------
		|             修改表前缀                |
		=======================================*/
        // 表前缀
        strategy.setTablePrefix("dh_");

        strategy.setInclude(scanner("表名"));
        autoGenerator.setStrategy(strategy);
        // FreemarkerTemplate
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }

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
