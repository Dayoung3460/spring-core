package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    // public으로 열어서 객체 인스턴스가 필요하면 이 static메소드만 통해서 조회 가능
    // getInstance()를 호출하면 항상 같은 인스턴스를 반환
    public static SingletonService getInstance() {
        return instance;
    }

    // 생성자를 private으로 해서 외부에서 new SingletonService() 못하도록 막음
    private SingletonService() {

    }

    public void logic() {
        System.out.println("Call singleton object logic");
    }
}
