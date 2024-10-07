package com.intheeast.init;

import jakarta.servlet.FilterRegistration;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import com.opensymphony.sitemesh.webapp.SiteMeshFilter;
import com.intheeast.config.AppConfig;

public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Spring 애플리케이션 컨텍스트 설정
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);

        // 리스너 추가 (ContextLoaderListener)
        servletContext.addListener(new ContextLoaderListener(rootContext));

        // 전역 파라미터 설정
        addGlobalParameters(servletContext);

        // 필터 설정
        addFilters(servletContext);

        // 서블릿 설정 (DispatcherServlet)
        addDispatcherServlet(servletContext);
    }

    // 전역 파라미터 설정 메서드
    private void addGlobalParameters(ServletContext servletContext) {
        servletContext.setInitParameter("appName", "SiP University");
        servletContext.setInitParameter("recipe", "8.1");
        servletContext.setInitParameter("aboutThisRecipe",
                "<p>Recipe 8.1 shows how to create a simple user feedback form.</p>");
    }

    // 필터 추가 메서드
    private void addFilters(ServletContext servletContext) {
        // SiteMesh 필터 설정
        FilterRegistration.Dynamic siteMeshFilter = servletContext.addFilter("sitemesh", new SiteMeshFilter());
        siteMeshFilter.addMappingForUrlPatterns(null, false, "/*");

        // CharacterEncodingFilter 설정
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", characterEncodingFilter);
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");
    }

    // DispatcherServlet 설정 메서드
    private void addDispatcherServlet(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext servletContextConfig = new AnnotationConfigWebApplicationContext();
        servletContextConfig.register(AppConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(servletContextConfig);
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");

        // 파일 업로드 설정
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                System.getProperty("java.io.tmpdir"), // OS에 따른 임시 디렉토리
                20971520,  // 최대 파일 크기: 20MB
                41943040,  // 전체 요청 크기: 40MB
                5242880);  // 임계값: 5MB
        registration.setMultipartConfig(multipartConfigElement);
    }
}
