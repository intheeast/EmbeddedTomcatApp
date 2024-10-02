package com.intheeast;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.intheeast.config.AppConfig;

import java.io.File;

public class EmbeddedTomcatApp {

    public static void main(String[] args) throws Exception {
        // 임베디드 톰캣 생성
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        // Spring 애플리케이션 컨텍스트 설정
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);

        // DispatcherServlet 생성
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

        // 톰캣의 컨텍스트 경로 설정 (기본 경로로 설정)
        String contextPath = "";  // 빈 문자열("")로 기본 경로 사용
        String docBase = new File("src/main/webapp").getAbsolutePath();  // target 폴더 내 webapp 설정
        Context ctx = tomcat.addWebapp(contextPath, docBase);

        if (ctx == null) {
            System.out.println("Context creation failed.");
            return;
        }

        // 톰캣 서블릿 설정
        tomcat.addServlet(contextPath, "dispatcherServlet", dispatcherServlet);
        ctx.addServletMappingDecoded("/", "dispatcherServlet");

        tomcat.getConnector();  // 톰캣 커넥터 초기화
        tomcat.start();
        tomcat.getServer().await();  // 서버 실행 대기
    }
}
