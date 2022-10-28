package geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

public class GeoServiceImplTest {

    @DisplayName("Проверка определения локации по ip")
    @Test
    public void TestLocationIp() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location locationExp1 = new Location(null, null, null, 0);
        Location locationExp2 = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
        Location locationExp3 = new Location("New York", Country.USA, " 10th Avenue", 32);
        Location locationExp4 = new Location("Moscow", Country.RUSSIA, null, 0);
        Location locationExp5 = new Location("New York", Country.USA, null,  0);

        Assertions.assertEquals(locationExp1.getCity(), geoService.byIp("127.0.0.1").getCity());
        Assertions.assertEquals(locationExp2.getCity(), geoService.byIp("172.0.32.11").getCity());
        Assertions.assertEquals(locationExp3.getCity(), geoService.byIp("96.44.183.149").getCity());
        Assertions.assertEquals(locationExp4.getCity(), geoService.byIp("172.0.0.1").getCity());
        Assertions.assertEquals(locationExp5.getCity(), geoService.byIp("96.0.0.1").getCity());
    }
}