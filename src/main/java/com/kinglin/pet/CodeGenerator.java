package com.kinglin.pet;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Collections;

/**
 * @author huangjl
 * @description 代码生成器
 * @since 2023-05-12 13:27
 */
public class CodeGenerator {

    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String URL = "jdbc:mysql://192.168.4.32:3306/pet?characterEncoding=utf-8&serverTimeZone=UTC&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";
    private static final String PACKAGE_NAME = "com.kinglin.pet";
    private static final String AUTHOR = "huangjl";
    private static final String OUT_PATH = PROJECT_PATH + "\\src\\main\\java\\";
    private static final String MAPPER_PATH = PROJECT_PATH + "\\src\\main\\java\\com\\kinglin\\pet\\mapper\\";

    public static void execute(String[] tableName) {
        FastAutoGenerator.create(URL, USERNAME, PASSWORD).globalConfig(builder -> {
            builder.author(AUTHOR).outputDir(OUT_PATH).disableOpenDir().enableSwagger();
        }).packageConfig(builder -> {
            builder.parent(PACKAGE_NAME).mapper("dao").pathInfo(Collections.singletonMap(OutputFile.xml, MAPPER_PATH));
        }).strategyConfig(builder -> {
            builder.addInclude(tableName).serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImpl")
                    .entityBuilder().fileOverride().enableChainModel().enableLombok().enableTableFieldAnnotation()
                    .addTableFills(new Column("create_time", FieldFill.INSERT)).addTableFills(new Column("update_time", FieldFill.UPDATE))
                    .controllerBuilder().enableRestStyle()
                    .mapperBuilder().fileOverride();
        }).templateEngine(new FreemarkerTemplateEngine()).execute();
    }

    public static void main(String[] args) {
        String[] tableName = {"appointment", "owner", "pet", "service", "store"};
        CodeGenerator.execute(tableName);
    }

}
