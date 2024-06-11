package hello.core.singleton;

public class StatefulService {
    // 빈으로 등록된 클래스에서는 공유 필드 사용하지마
//    private int price;

    public int order(String name, int price) {
        System.out.println("name = " + name + ", price = " + price);
//        this.price = price;
        return price;
    }

//    public int getPrice() {
//        return price;
//    }
}
