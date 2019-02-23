import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DestTest {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    @Test
    public void givenSrcAndDest_whenMaps_thenCorrect() {
        //Given
        mapperFactory.classMap(Source.class, Dest.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Source src = new Source("Baelung", 10);
        //When
        Dest dst = mapper.map(src, Dest.class);
        //Then
        assertEquals(dst.getAge(), src.getAge());
        assertEquals(dst.getName(), src.getName());
    }

    @Test
    public void givenSrcAndDest_whenMapsUsingBoundMapper_thenCorect() {
        //Given
        BoundMapperFacade<Source, Dest>
                boundMapper = mapperFactory.getMapperFacade(Source.class, Dest.class);
        Source src = new Source("baeldung", 10);
        //When
        Dest dst = boundMapper.map(src);
        //Then
        assertEquals(dst.getAge(), src.getAge());
        assertEquals(dst.getName(), src.getName());
    }

}