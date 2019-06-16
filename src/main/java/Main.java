import java.util.ArrayList;
import java.util.List;


public class Main {

    private static List<Request> requestList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        createRequestList();
        Client nettyClient = new Client("localhost", 7070);

        try {
            nettyClient.run();
            for (Request request : requestList) {
                nettyClient.getChannel().writeAndFlush(request).sync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            nettyClient.shutdown();
        }
    }

    private static void createRequestList() {
        Request request = new Request();
        request.setAction(Action.SET);
        request.setKey("k1");
        ArrayList<String> array = new ArrayList();
        array.add("a");
        array.add("b");
        array.add("c");
        request.setValue(array);
        requestList.add(request);

        Request request1 = new Request();
        request1.setAction(Action.SET);
        request1.setKey("k2");
        array = new ArrayList<>();
        array.add("d");
        array.add("e");
        array.add("f");
        request1.setValue(array);
        requestList.add(request1);

        Request request8 = new Request();
        request8.setAction(Action.SET);
        request8.setKey("k3");
        array = new ArrayList<>();
        array.add("g");
        array.add("h");
        array.add("i");
        request8.setValue(array);
        requestList.add(request8);

        Request request2 = new Request();
        request2.setAction(Action.LEFT_ADD);
        request2.setKey("k3");
        request2.setAdd("x");
        requestList.add(request2);

        Request request3 = new Request();
        request3.setAction(Action.RIGHT_ADD);
        request3.setKey("k1");
        request3.setAdd("y");
        requestList.add(request3);

        Request request4 = new Request();
        request4.setAction(Action.GET);
        request4.setKey("k1");
        requestList.add(request4);

        Request request5 = new Request();
        request5.setAction(Action.GET);
        request5.setKey("k2");
        requestList.add(request5);

        Request request6 = new Request();
        request6.setAction(Action.GET);
        request6.setKey("k3");
        requestList.add(request6);

        Request request7 = new Request();
        request7.setAction(Action.GET_ALL_KEYS);
        requestList.add(request7);
    }
}
