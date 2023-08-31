package hello.container;

import hello.servlet.HelloServlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;

/**
 * http://localhost/hello-servlet
 * 서블릿을 등록하는 방법
 * - 프로그래밍 방식 : 코딩을 많이 해야하지만 무한한 유연성을 제공
 * - /hello-servlet 경로를 상황에 따라서 바꾸어 외부 설정을 읽어서 등록할 수 있다.
 * - 서블릿 자체도 특정 조건에 따라서 if 문으로 분기해서 등록하거나 뺄 수 있다.
 * - 서블릿을 내가 직접 생성하기 때문에 생성자(new HelloServlet())에 필요한 정보를 넘길 수 있다.
 */
public class AppInitV1Servlet implements AppInit {

    @Override
    public void onStartup(ServletContext servletContext) {
        System.out.println("AppInitV1Servlet.onStartup");

        // 순수 서블릿 코드 등록
        ServletRegistration.Dynamic helloServlet = servletContext.addServlet("helloServlet", new HelloServlet());
        helloServlet.addMapping("/hello-servlet");
    }
}
