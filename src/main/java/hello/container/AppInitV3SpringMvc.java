package hello.container;

import hello.spring.HelloConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 섹션2. 스프링MVC 서블릿 컨테이너 초기화 지원
 *  - 스프링MVC도 우리가 지금까지 한 것 처럼 서블릿 컨테이너 초기화 파일에 초기화 클래스를 등록해두었다.
 *  - 그리고 WebApplicationInitializer 인터페이스를 애플리케이션 초기화 인터페이스로 지정해두고, 이것을 생성해서 실행한다.
 *  - 따라서 스프링 MVC를 사용한다면 WebApplicationInitializer 인터페이스만 구현하면 AppInitV3SpringMvc 에서 본 것 처럼 편리하게 애플리케이션 초기화를 사용할 수 있다.
 */
public class AppInitV3SpringMvc implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("AppInitV3Springmvc.onStratup");

        /**
         * 스프링 컨테이너 생성
         */
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(HelloConfig.class);

        //
        /**
         * 스프링 MVC 디스패처 서블릿 생성, 스플링 컨테이너 연결
         */
        DispatcherServlet dispatcher = new DispatcherServlet(appContext);

        //
        /**
         * 디스패처 서블릿을 서블릿 컨테이너에 등록 (이름 주의! dispatcherV3)
         */
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcherV3", dispatcher);

        /**
         * 모든 요청이 디스패처 서블릿을 통하도록 설정
         */
        servlet.addMapping("/");
    }
}
