package com.intheeast.init;


import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import com.opensymphony.sitemesh.webapp.SiteMeshFilter;
import com.intheeast.config.AppConfig;

public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // 1. Spring 애플리케이션 설정 로드
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);

        // 2. ContextLoaderListener 등록
        servletContext.addListener(new ContextLoaderListener(rootContext));

        // 3. context-param 설정
        servletContext.setInitParameter("appName", "SiP University");
        servletContext.setInitParameter("recipe", "8.1");
        servletContext.setInitParameter("aboutThisRecipe",
                "<p>Recipe 8.1 shows how to create a simple user feedback form.</p>");

        // 4. SiteMesh 필터 등록
        FilterRegistration.Dynamic siteMeshFilter = servletContext.addFilter("sitemesh", new SiteMeshFilter());
        siteMeshFilter.addMappingForUrlPatterns(null, false, "/*");
        
        // 5. CharacterEncodingFilter 설정 및 등록
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", characterEncodingFilter);
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");

        // 6. DispatcherServlet 설정 및 등록
        AnnotationConfigWebApplicationContext servletContextConfig = new AnnotationConfigWebApplicationContext();
        servletContextConfig.register(AppConfig.class); // 누락된 설정 추가

        DispatcherServlet dispatcherServlet = new DispatcherServlet(servletContextConfig);
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");  // 정적 파일 처리를 고려한 수정
    }
}