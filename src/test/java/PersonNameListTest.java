import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PersonNameListTest {

    private MapperFactory mapperFactory = new DefaultMapperFactory
            .Builder()

            .build();

    @Test
    public void givenSrcWithListAndDestWithPrimitiveAttributes_whenMaps_thenCorrect() throws Exception {
        //Given
        mapperFactory.classMap(PersonNameList.class, PersonNameParts.class)
                .field("nameList[0]", "firstName")
                .field("nameList[1]", "lastName")
                .register();

        mapperFactory.classMap(Source.class, Dest.class)
                .field("age", "age")
                .fieldMap("name", "name")
                .mapNulls(false)
                .add()
                .byDefault()
                .register();


        MapperFacade mapper = mapperFactory.getMapperFacade();
        List<String> nameList = Arrays.asList("Sylvester", "Stallone");
        PersonNameList src = new PersonNameList(nameList);
        //When
        PersonNameParts dest = mapper.map(src, PersonNameParts.class);
        //Then
        assertEquals(dest.getFirstName(), "Sylvester");
        assertEquals(dest.getLastName(), "Stallone");
    }

    @Test
    public void givenSrcMapAndDestWithPrimitiveAttributes_whenMaps_thenCorrect() throws Exception {
        //Given
        mapperFactory.classMap(PersonNameMap.class, PersonNameParts.class)
                .field("nameMap['first']", "firstName")
                .field("nameMap[\"last\"]", "lastName")
                .register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("first", "Leonardo");
        nameMap.put("last", "DiCaprio");
        PersonNameMap src = new PersonNameMap(nameMap);
        //When
        PersonNameParts dest = mapper.map(src, PersonNameParts.class);
        //Then
        assertEquals(dest.getFirstName(), "Leonardo");
        assertEquals(dest.getLastName(), "DiCaprio");

    }

    @Test
    public void givenSrcWithNestedFields_whenMaps_thenCorrect() throws Exception {
        //Given
        mapperFactory.classMap(PersonContainer.class, PersonNameParts.class)
                .field("name.first", "firstName")
                .field("name.lastName", "lastName")
                .register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        PersonContainer src = new PersonContainer(new Name("Nick", "Canon"));
        //When
        PersonNameParts dest = mapper.map(src, PersonNameParts.class);
        //Then
        assertEquals(dest.getFirstName(), "Nick");
        assertEquals(dest.getFirstName(), "Canon");
    }
}