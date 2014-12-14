package uk.co.caprica.brue.okhttp.service.discovery;

import java.io.IOException;

import org.simpleframework.xml.core.Persister;

import uk.co.caprica.brue.core.domain.discovery.ServiceDescription;
import uk.co.caprica.brue.core.service.discovery.DiscoveryException;
import uk.co.caprica.brue.core.service.discovery.DiscoveryIOException;
import uk.co.caprica.brue.core.service.discovery.DiscoveryService;
import uk.co.caprica.brue.okhttp.service.AbstractOkHttpService;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

public final class DiscoveryServiceImpl extends AbstractOkHttpService implements DiscoveryService {

    private static final String SERVICE_URL = "http://philips-hue/description.xml";

    /**
     *
     *
     * This component is fully thread-safe.
     */
    private static final Persister persister = new Persister();

    @Override
    public ServiceDescription serviceDescription() {
        ServiceDescription result;
        try {
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder().url(SERVICE_URL).get().addHeader(HEADER_ACCEPT, MEDIA_TYPE_XML).build();
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response(response);
            }
            else {
                result = null;
            }
        }
        catch(IOException e) {
            throw new DiscoveryIOException(e);
        }
        return result;
    }

    private ServiceDescription response(Response response) {
        try (ResponseBody responseBody = response.body()) {
            return persister.read(ServiceDescription.class, responseBody.string());
        }
        catch (Exception e) {
            throw new DiscoveryException(e);
        }
    }
}
