package sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.startsWith;

public class MessageSenderTest {

    @DisplayName("Проверка отправки текста на русском языке, если ip относится к российскому сегменту адресов")
    @Test
    void test_send_message_Russia() {
        Location location = new Location(null, Country.RUSSIA, null, 0);
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(startsWith("172.")))
                .thenReturn(location);
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");
        String preferences = messageSender.send(headers);
        String expected = "Добро пожаловать";
        Assertions.assertEquals(expected, preferences);
    }

    @DisplayName("Проверка отправки текста на английском языке, если ip не относится к американскому сегменту адресов")
    @Test
    void test_send_message_USA() {
        Location location = new Location(null, Country.USA, null, 0);
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(startsWith("96.")))
                .thenReturn(location);
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.123.12.19");
        String preferences = messageSender.send(headers);
        String expected = "Welcome";
        Assertions.assertEquals(expected, preferences);
    }
}