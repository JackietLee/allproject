package proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//        System.out.println("开始");
        Object o1 = methodProxy.invokeSuper(o, objects);
//        System.out.println("结束");
        return o1;
    }

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        for(int i=0;i<10000000;i++){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SubjectImpl.class);
        enhancer.setCallback(new CglibProxy());
        SubjectImpl o = (SubjectImpl)enhancer.create();
//        int request = o.request();
        }
        System.out.println(System.currentTimeMillis()-l);
    }

    public void notransaction_exception_requiresNew_requiresNew(){

        throw new RuntimeException();

    }
}
