import com.elinext.test.dao.EventDAO;
import com.elinext.test.dao.impl.InMemoryEventDaoImpl;
import com.elinext.injector.Injector;
import com.elinext.injector.impl.InjectorImpl;
import com.elinext.provider.Provider;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class InjectorTest {

    @Test
    void doSomeThings() {
        Injector injector = new InjectorImpl();
        injector.bind(EventDAO.class, InMemoryEventDaoImpl.class);
        Provider<EventDAO> provider = injector.getProvider(EventDAO.class);

        assertNotNull(provider);
        assertNotNull(provider.getInstance());
        assertSame(InMemoryEventDaoImpl.class, provider.getInstance().getClass());
    }


}
