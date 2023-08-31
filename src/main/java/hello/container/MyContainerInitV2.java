package hello.container;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * 어플리케이션 초기화 과정
 * 1. @HandlesTypes 애노테이션에 애플리케이션 초기화 인터페이스를 지정한다.
 *      - 여기서는 앞서 만든 AppInit.class 인터페이스를 지정했다.
 * 2. 서블릿 컨테이너 초기화( ServletContainerInitializer )는 파라미터로 넘어오는 Set<Class<?>> c 에
 *    애플리케이션 초기화 인터페이스의 구현체들을 모두 찾아서 클래스 정보로 전달한다.
 *      - 여기서는 @HandlesTypes(AppInit.class) 를 지정했으므로 AppInit.class 의 구현체인 AppInitV1Servlet.class 정보가 전달된다.
 *      - 참고로 객체 인스턴스가 아니라 클래스 정보를 전달하기 때문에 실행하려면 객체를 생성해서 사용해야한다.
 * 3. appInitClass.getDeclaredConstructor().newInstance()
 *      - 리플렉션을 사용해서 객체를 생성한다. 참고로 이 코드는 new AppInitV1Servlet() 과 같다 생각하면 된다.
 * 4. appInit.onStartup(ctx)
 *      - 애플리케이션 초기화 코드를 직접 실행하면서 서블릿 컨테이너 정보가 담긴 ctx 도 함께 전달한다.
 *
 * 초기화는 다음 순서로 진행된다.
 * 1. 서블릿 컨테이너 초기화 실행
 *      - resources/META-INF/services/jakarta.servlet.ServletContainerInitializer
 * 2. 애플리케이션 초기화 실행
 *      - @HandlesTypes(AppInit.class)
 */
@HandlesTypes(AppInit.class)
public class MyContainerInitV2 implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("MyContainerInitV2.onStartup");
        System.out.println("MyContainerInitV2 c = " + c);
        System.out.println("MyContainerInitV2 ctx = " + ctx);

        //class hello.container.AppInitV1Servlet
        for (Class<?> appInitClass : c) {
            try {
                // new AppInitV1Servlet()과 같은 코드
                AppInit appInit = (AppInit) appInitClass.getDeclaredConstructor().newInstance();
                appInit.onStartup(ctx);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
