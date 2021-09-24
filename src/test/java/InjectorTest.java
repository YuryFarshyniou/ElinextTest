import com.elinext.exceptions.BindingNotFoundException;
import com.elinext.exceptions.ConstructorNotFoundException;
import com.elinext.exceptions.TooManyConstructorsException;
import com.elinext.injector.Injector;
import com.elinext.injector.impl.InjectorImpl;
import com.elinext.provider.Provider;
import com.elinext.tests_cases.example_test.dao.EventDAO;
import com.elinext.tests_cases.example_test.dao.impl.InMemoryEventDaoImpl;
import com.elinext.tests_cases.example_test.service.EventService;
import com.elinext.tests_cases.example_test.service.impl.EventServiceImpl;
import com.elinext.tests_cases.task_4.service.EventService1;
import com.elinext.tests_cases.task_4.service.impl.EventService1Impl;
import com.elinext.tests_cases.task_5.part_1.service.EventService2;
import com.elinext.tests_cases.task_5.part_1.service.impl.EventService2Impl;
import com.elinext.tests_cases.task_5.part_2.service.EventService2Dot1;
import com.elinext.tests_cases.task_5.part_2.service.impl.EventService2Dot1Impl;
import com.elinext.tests_cases.task_6.service.EventService3;
import com.elinext.tests_cases.task_6.service.impl.EventService3Impl;
import com.elinext.tests_cases.task_7.SomeInterface2;
import com.elinext.tests_cases.task_7.service.EventService4;
import com.elinext.tests_cases.task_7.service.impl.EventService4Impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InjectorTest {

    @Test
    void ifClassHasConstructorWithInjectAnnotation() {
        Injector injector = new InjectorImpl();
        injector.bind(EventDAO.class, InMemoryEventDaoImpl.class);
        Provider<EventDAO> provider = injector.getProvider(EventDAO.class);

        assertNotNull(provider);
        assertNotNull(provider.getInstance());
        assertSame(InMemoryEventDaoImpl.class, provider.getInstance().getClass());
    }

    @Test
    void ifClassContainsSeveralConstructorsWithInjectAnnotation() {
        Injector injector = new InjectorImpl();
        injector.bind(EventService1.class, EventService1Impl.class);
        Assertions.assertThrows(TooManyConstructorsException.class, () -> injector.getProvider(EventService1.class));
    }

    @Test
    void ifClassDoesNotHaveConstructorsWithInjectAnnotation() {
        Injector injector = new InjectorImpl();
        injector.bind(EventService2.class, EventService2Impl.class);
        Provider<EventService2> provider = injector.getProvider(EventService2.class);

        assertNotNull(provider);
        assertNotNull(provider.getInstance());
        assertSame(EventService2Impl.class, provider.getInstance().getClass());
    }

    @Test
    void ifClassHasNeitherConstructorsWithInjectNorDefaultConstructors() {
        Injector injector = new InjectorImpl();
        injector.bind(EventService2Dot1.class, EventService2Dot1Impl.class);
        Assertions.assertThrows(ConstructorNotFoundException.class, () -> injector.getProvider(EventService2Dot1.class));
    }

    @Test
    void throwBindingNotFoundException() {
        Injector injector = new InjectorImpl();
        injector.bind(EventService3.class, EventService3Impl.class);
        Assertions.assertThrows(BindingNotFoundException.class, () -> injector.getProvider(EventService3.class));
    }

    @Test
    void ifProviderReturnsNull() {
        Injector injector = new InjectorImpl();
        injector.bind(EventService4.class, EventService4Impl.class);
        Provider<SomeInterface2> provider = injector.getProvider(SomeInterface2.class);
        assertNull(provider.getInstance());
    }

    @Test
    void testPrototypeBinding() {
        Injector injector = new InjectorImpl();
        injector.bind(EventService.class, EventServiceImpl.class);
        Provider<EventService> provider = injector.getProvider(EventService.class);
        EventService eventService = provider.getInstance();

        Provider<EventService> provider2 = injector.getProvider(EventService.class);
        EventService eventService2 = provider2.getInstance();
        assertNotSame(eventService, eventService2);
    }

    @Test
    void testSingletonBinding() {
        Injector injector = new InjectorImpl();
        injector.bindSingleton(EventService.class, EventServiceImpl.class);
        Provider<EventService> provider = injector.getProvider(EventService.class);
        EventService eventService = provider.getInstance();

        Provider<EventService> provider2 = injector.getProvider(EventService.class);
        EventService eventService2 = provider2.getInstance();
        assertSame(eventService, eventService2);
    }

}
