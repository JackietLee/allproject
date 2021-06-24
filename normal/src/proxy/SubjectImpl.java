package proxy;

public class SubjectImpl implements Subject {
    @Override
    public int request() {
        System.out.println("您好");
        return 1;
    }
}
