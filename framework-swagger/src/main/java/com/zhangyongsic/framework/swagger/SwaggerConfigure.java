package com.zhangyongsic.framework.swagger;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**

 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfigure {

    @Bean
    @ConditionalOnMissingBean
    public Docket createRestApi(){
        ParameterBuilder headerBuilder = new ParameterBuilder();
        List<Parameter> parameters = Lists.newArrayList();
        headerBuilder.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameters.add(headerBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);

    }

    /**
     * 构建ApiInfo对象
     * @return
     */
    private ApiInfo buildApiInfo(){
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("smdh2接口文档")
                .description("smdh2")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
        return apiInfo;
    }
}
