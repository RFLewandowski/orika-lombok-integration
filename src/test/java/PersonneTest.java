import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonneTest {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    @Test
    public void givenSrcAndDestWithDifferentFieldNames_whenMaps_thenCorrect() throws Exception {
        mapperFactory.classMap(Personne.class, Person.class)
                .field("nom", "name")
                .field("surnom","nickname")
                .field("age","age")
                .register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Personne frenchPerson = new Personne("Claire", "cla",25);
        Person englishPerson = mapper.map(frenchPerson,Person.class);

        assertEquals(englishPerson.getName(), frenchPerson.getNom());
        assertEquals(englishPerson.getNickname(), frenchPerson.getSurnom());
        assertEquals(englishPerson.getAge(), frenchPerson.getAge());
    }


    @Test
    public void givenSrcAndDest_whenCanExcludeField_thenCorrect() {
        mapperFactory.classMap(Personne.class, Person.class)
                .exclude("nom")
                .field("surnom", "nickname")
                .field("age", "age")
                .register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Personne frenchPerson = new Personne("Claire", "cla", 25);
        Person englishPerson = mapper.map(frenchPerson, Person.class);

        assertEquals(null, englishPerson.getName());
        assertEquals(englishPerson.getNickname(), frenchPerson.getSurnom());
        assertEquals(englishPerson.getAge(), frenchPerson.getAge());
    }
}