import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCompletableFuture  {
    static Logger logger = LoggerFactory.getLogger(TestCompletableFuture.class);
    public static void main(String [] args) throws CustomException, ExecutionException, InterruptedException {
        List<Host> lh = new ArrayList<>();
        for(int i= 0;i<10;i++) {
            lh.add(new Host("name" + i,i+""));
        }
        Map<String,Object> map = lh.stream().
                collect(Collectors.toMap(c->{return String.format("%s%s",c.getName(),c.getAge());},
                c->c));
        for(String key:map.keySet()) {
//            logger.info("key={},value={}",key,map.get(key));
        }
        String original = "Message";
        CompletableFuture cf = CompletableFuture.supplyAsync(()->original).thenApply(s -> s.toLowerCase())
                .thenCombine(CompletableFuture.completedFuture(original).thenApply(x -> {return 1;}),
                        (s1, s2) -> s1 + s2);
        System.out.println("MESSAGEmessage".equals(cf.getNow(null)));

        String test = CompletableFuture.supplyAsync(()->original).get();
        String test1 = CompletableFuture.completedFuture(String.valueOf(22)).get();

        cf.thenAccept(xx->{
            logger.info("xx->{}",xx);
        });

        Map og = new HashMap();
        CompletableFuture cf1 = CompletableFuture.completedFuture(map).thenAccept(m->{
            for(String key:m.keySet()) {
                logger.info("key={},value={}",key,map.get(key));
            }
        });

        cf.getNow(null);
        assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());

    }

    private static Integer square(Object x) {
        return (Integer)x*(Integer)x;
    }
}
class Host {
    Host(String name, String age) {
        this.name = name;
        this.age = age;

    }
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    String age;

}
