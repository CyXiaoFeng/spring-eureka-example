import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class ServiceImplTest {
    @Mocked
    ServiceImpl serviceImpl;
    @Injectable
    RelationImpl relationImpl;
    @Test
    public void testResult() {
        new Expectations(){{
//            relationImpl.listResult((Map) any);
//            result = Arrays.asList(new String[]{"111","bbb"});
            serviceImpl.getResult(anyInt);
            result = "error";
        }};
        assertEquals("success",serviceImpl.getResult(0));
    }
}