package com.task.task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.task.task.data.api.converter.RestaurantsAPIConverter;
import com.task.task.data.api.converter.RestaurantsAPIConverterImpl;
import com.task.task.data.api.models.response.RestaurantsApiResponse;
import com.task.task.data.storage.PreferenceRepository;
import com.task.task.domain.model.RestaurantInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class DeviceApiConverterImplTest extends BaseTest {

    @Mock
    PreferenceRepository preferenceRepository;

    private RestaurantsApiResponse[] apiResponse;

    private RestaurantsAPIConverter restaurantsAPIConverter;

    @Before
    public void setUp() throws Exception {
        restaurantsAPIConverter = new RestaurantsAPIConverterImpl(preferenceRepository);
        Gson gson = new GsonBuilder().create();
        apiResponse = gson.fromJson(readJsonFile("restaurants_values.json"), RestaurantsApiResponse[].class);
    }

    @After
    public void tearDown() throws Exception {
        apiResponse = null;
        restaurantsAPIConverter = null;
    }

    @Test
    public void apiResponseNotNullTest() throws Exception {

        assertNotNull(restaurantsAPIConverter.convertToRestarauntInfo(Arrays.asList(apiResponse)));
    }

    @Test
    public void apiResponseNullTest() throws Exception {
        assertEquals(new ArrayList<>(), restaurantsAPIConverter.convertToRestarauntInfo(null));
    }

    @Test
    public void apiResponseAttributesNotNullJson() throws Exception {
        final List<RestaurantInfo> currentDevices = restaurantsAPIConverter.convertToRestarauntInfo(Arrays.asList(apiResponse));
        for (RestaurantInfo currentDevice : currentDevices) {
            assertNotNull(currentDevice.name);
            assertNotNull(currentDevice.address);
            assertNotNull(currentDevice.latitude);
            assertNotNull(currentDevice.longitude);
        }
    }
}