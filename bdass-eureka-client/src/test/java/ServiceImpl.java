//import com.google.common.collect.Maps;

import com.google.common.collect.Maps;

import javax.annotation.Resource;
import java.util.List;

public class ServiceImpl implements Service {
    public String initA;
    public String initB;
    @Resource
    RelationImpl relationImpl;
    public String getResult(Integer type) {
        List<String> list = relationImpl.listResult(Maps.newHashMap());
        if(list.size() == 0) return "error";
        return "success";
    }
    public static void main(String[] a) {
        ServiceImpl s = new ServiceImpl();
        s.getResult(1);
    }
}
