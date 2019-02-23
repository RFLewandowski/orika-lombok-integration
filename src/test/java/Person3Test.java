import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Person3Test {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private Mapper<Personne3, Person3> customMapper = new PersonCustomMapper();

    @Test
    public void givenSrcAndDest_whenCustomMapperWorks_thenCorrect() throws Exception {
        mapperFactory.classMap(Personne3.class, Person3.class)
                .customize(customMapper)
                .register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        String dateTime = "2007-06-26T20:22:39Z";
        long timestamp = new Long("1182882159000");
        Personne3 personne3 = new Personne3("Leornardo", timestamp);
        Person3 person3 = mapper.map(personne3, Person3.class);

        assertEquals(person3.getDtob(), dateTime);
    }

    @Test
    public void givenSrcAndDest_whenCustomMapperWorksBidirectionally_thenCorrect() {
        mapperFactory.classMap(Personne3.class, Person3.class)
                .customize(customMapper).register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        String dateTime = "2007-06-26T20:22:39Z";
        long timestamp = new Long("1182882159000");
        Person3 person3 = new Person3("Leornardo", dateTime);
        Personne3 personne3 = mapper.map(person3, Personne3.class);

        assertEquals(personne3.getDtob(), timestamp);
    }

}