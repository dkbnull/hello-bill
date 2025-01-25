package cn.wbnull.hellobill.db.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Types;
import java.util.Collections;

/**
 * Mybatis Plus 代码生成器
 *
 * @author null  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
public class CodeGenerator {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/hellobill?characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static final String PACKAGE_PATH = System.getProperty("user.dir") + "/hello-bill-db/src/main/java";
    private static final String RESOURCES_MAPPER_PATH = System.getProperty("user.dir") + "/hello-bill-db/src/main/resources/mapper/temp";
    private static final String PACKAGE_PARENT = "cn.wbnull.hellobill.db.temp";

    public static void main(String[] args) {
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig(builder -> builder
                        .author("null")
                        .outputDir(PACKAGE_PATH)
                        .disableOpenDir())
                .dataSourceConfig(builder -> builder
                        .typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder -> builder
                        .parent(PACKAGE_PARENT)
                        .pathInfo(Collections.singletonMap(OutputFile.xml, RESOURCES_MAPPER_PATH))
                )
                .strategyConfig(builder -> builder
                        .entityBuilder()
                        .disableSerialVersionUID()
                        .enableTableFieldAnnotation()
                        .enableLombok()
                        .idType(IdType.INPUT)
                        .mapperBuilder()
                        .mapperAnnotation(Mapper.class)
                        .enableBaseColumnList()
                        .enableBaseResultMap()
                        .controllerBuilder().disable()
                        .serviceBuilder().disable()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
