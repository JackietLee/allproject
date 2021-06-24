package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxy {

    private Object target;

    public JdkProxy(Object object){
        this.target = object;
    }

    public Object getProxyInstance(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("开始");
                Object invoke = method.invoke(target, args);
                System.out.println("结束");
                return invoke;
            }
        });
    }

    public static void main(String[] args) {
        Subject proxyInstance = (Subject) new JdkProxy(new SubjectImpl()).getProxyInstance();
        int request = proxyInstance.request();
        System.out.println(request);
    }
}
