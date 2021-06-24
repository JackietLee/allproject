package rpc;


import java.util.Iterator;
import java.util.ServiceLoader;

public class AobingServiceImpl implements AobingService {
    public String hello(String name) {
        System.out.println("Yo man Hello，I am" + name);
        return "Yo man Hello，I am" + name;
    }


    public static void main(String[] args) {
        ServiceLoader<AobingService> load = ServiceLoader.load(AobingService.class);
        Iterator<AobingService> iterator = load.iterator();
        while (iterator.hasNext()){
            AobingService next = iterator.next();
            next.hello("111");

        }
    }
}