/*
 * Copyright 2014 Caprica Software Limited
 * (http://www.capricasoftware.co.uk)
 *
 * This file is part of Brue.
 *
 * Brue is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * Brue is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Brue.  If not, see <http://www.gnu.org/licenses/>.
 */

package uk.co.caprica.brue.okhttp.service.bridge;

import java.io.IOException;
import java.util.List;

import uk.co.caprica.brue.domain.bridge.authorisation.Authorisation;
import uk.co.caprica.brue.domain.bridge.result.AuthoriseDetail;
import uk.co.caprica.brue.domain.bridge.result.AuthoriseResult;
import uk.co.caprica.brue.domain.bridge.result.CreateDetail;
import uk.co.caprica.brue.domain.bridge.result.CreateResult;
import uk.co.caprica.brue.domain.bridge.result.DeleteDetail;
import uk.co.caprica.brue.domain.bridge.result.DeleteResult;
import uk.co.caprica.brue.domain.bridge.result.ErrorDetail;
import uk.co.caprica.brue.domain.bridge.result.ErrorResult;
import uk.co.caprica.brue.domain.bridge.result.UpdateDetail;
import uk.co.caprica.brue.domain.bridge.result.UpdateResult;
import uk.co.caprica.brue.service.bridge.BridgeErrorResultException;
import uk.co.caprica.brue.service.bridge.BridgeIOException;
import uk.co.caprica.brue.service.bridge.BridgeResponseException;
import uk.co.caprica.brue.settings.bridge.BridgeSettings;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

// FIXME push httpClient to a common static base class, e.g. to share with DiscoveryService

/**
 * Base implementation for a Philips Hue Bridge service that uses HTTP to communicate with the bridge.
 * <p>
 * This is a simple HTTP client wrapper with specific behaviour to simplify and optimise interactions with a Philips Hue bridge.
 */
abstract class AbstractBridgeService {

    /**
     * Shared HTTP Client.
     * <p>
     * Once created, this client is completely thread-safe and can safely be re-used.
     */
    private static final OkHttpClient httpClient = new OkHttpClient();

    /**
     * Shared JSON Object Mapper.
     * <p>
     * Once created and configured, this object mapper is completely thread-safe and can be safely re-used.
     */
    private static final ObjectMapper objectMapper = new ObjectMapper() {
        {registerModule(new GuavaModule());}
    };

    /**
     *
     */
    private static final String TEMPLATE_BRIDGE_URL = "http://%s/api";

    /**
     * Standard HTTP Accept header name.
     */
    private static final String HEADER_ACCEPT = "Accept";

    /**
     * Standard media type for JSON requests and responses.
     */
    private static final String MEDIA_TYPE_JSON = "application/json";

    /**
     *
     */
    private final BridgeSettings bridgeSettings;

    /**
     * Resource path (part of the URL) for this service.
     */
    private final String resourcePath;

    /**
     *
     *
     * @param bridgeSettings
     */
    protected AbstractBridgeService(BridgeSettings bridgeSettings) {
        this(bridgeSettings, null);
    }

    /**
     *
     *
     * @param bridgeSettings
     * @param resourcePath
     */
    protected AbstractBridgeService(BridgeSettings bridgeSettings, String resourcePath) {
        this.bridgeSettings = bridgeSettings;
        this.resourcePath = resourcePath;
    }

    /**
     *
     *
     * @param authorisation
     * @return
     */
    protected final AuthoriseResult authoriseResource(Authorisation authorisation) {
        Request request = new Request.Builder().url(bridgeUrl()).post(requestBody(authorisation)).addHeader(HEADER_ACCEPT, MEDIA_TYPE_JSON).build();
        List<AuthoriseDetail> result = executeRequest(request, typeOf(AuthoriseResult.TYPE_REFERENCE));
        return new AuthoriseResult(result);
    }

    /**
     * Perform an HTTP GET request to get a resource.
     *
     * @param resourceUrl
     * @param responseType
     * @param <T>
     * @return
     */
    protected final <T> T getResource(String resourceUrl, Class<T> responseType) {
        return getResource(resourceUrl, typeOf(responseType));
    }

    /**
     *
     *
     * @param resourceUrl
     * @param responseType
     * @param <T>
     * @return
     */
    protected final <T> T getResource(String resourceUrl, TypeReference<T> responseType) {
        return getResource(resourceUrl, typeOf(responseType));
    }

    /**
     * Perform an HTTP POST request to create a resource.
     *
     * @param resourceUrl
     * @param object
     * @return
     */
    protected final CreateResult createResource(String resourceUrl, Object object) {
        Request request = new Request.Builder().url(resourceUrl).post(requestBody(object)).addHeader(HEADER_ACCEPT, MEDIA_TYPE_JSON).build();
        List<CreateDetail> result = executeRequest(request, typeOf(CreateResult.TYPE_REFERENCE));
        return new CreateResult(result);
    }

    /**
     * Perform an HTTP PUT request to update a resource.
     *
     * @param resourceUrl
     * @param object
     * @return
     */
    protected final UpdateResult updateResource(String resourceUrl, Object object) {
        Request request = new Request.Builder().url(resourceUrl).put(requestBody(object)).addHeader(HEADER_ACCEPT, MEDIA_TYPE_JSON).build();
        List<UpdateDetail> result = executeRequest(request, typeOf(UpdateResult.TYPE_REFERENCE));
        return new UpdateResult(result);
    }

    /**
     * Perform an HTTP DELETE request to delete a resource.
     *
     * @param resourceUrl
     */
    protected final DeleteResult deleteResource(String resourceUrl) {
        Request request = new Request.Builder().url(resourceUrl).delete().build();
        List<DeleteDetail> result = executeRequest(request, typeOf(DeleteResult.TYPE_REFERENCE));
        return new DeleteResult(result);
    }

    /**
     *
     *
     * @return
     */
    private String bridgeUrl() {
        return String.format(TEMPLATE_BRIDGE_URL, bridgeSettings.host());
    }

    /**
     *
     *
     * @return
     */
    protected final String resourceUrl() {
        return String.format("%s/%s/%s", bridgeUrl(), bridgeSettings.username(), resourcePath);
    }

    /**
     *
     *
     * @param requestPath
     * @return
     */
    protected final String resourceUrl(Object requestPath, Object... morePath) {
        String result = String.format("%s/%s", resourceUrl(), requestPath.toString());
        if (morePath != null) {
            StringBuilder sb = new StringBuilder(result.length() + 20);
            sb.append(result);
            for (Object o : morePath) {
                sb.append('/').append(o);
            }
            result = sb.toString();
        }
        return result;
    }

    /**
     *
     *
     * @param type
     * @return
     */
    private JavaType typeOf(Class<?> type) {
        return objectMapper.getTypeFactory().constructType(type);
    }

    /**
     *
     *
     * @param type
     * @return
     */
    private JavaType typeOf(TypeReference<?> type) {
        return objectMapper.getTypeFactory().constructType(type);
    }

    /**
     *
     *
     * @param resourceUrl
     * @param responseType
     * @param <T>
     * @return
     */
    private <T> T getResource(String resourceUrl, JavaType responseType) {
        Request request = new Request.Builder().url(resourceUrl).addHeader(HEADER_ACCEPT, MEDIA_TYPE_JSON).build();
        return executeRequest(request, responseType);
    }

    /**
     *
     *
     * @param object
     * @return
     */
    private RequestBody requestBody(Object object) {
        try {
            String body = objectMapper.writer().writeValueAsString(object);
            return RequestBody.create(MediaType.parse(MEDIA_TYPE_JSON), body);
        }
        catch (IOException e) {
            throw new BridgeIOException(e);
        }
    }

    /**
     * Execute a request, synchronously.
     *
     * @param request
     * @param responseType
     * @param <T>
     * @return
     */
    private <T> T executeRequest(Request request, JavaType responseType) {
        try {
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                if (responseType != null) {
                    return response(response, responseType);
                }
                else {
                    return null;
                }
            }
            else {
                throw new BridgeResponseException(response.code(), response.message());
            }
        }
        catch (IOException e) {
            throw new BridgeIOException(e);
        }
    }

    /**
     * Extract a domain object from an HTTP response body.
     * <p>
     * Note that the response body must be explicitly closed (and in this case is done so by using the try-with-resources
     * approach).
     *
     * @param response
     * @param responseType
     * @param <T>
     * @return
     */
    private <T> T response(Response response, JavaType responseType) {
        try (ResponseBody responseBody = response.body()) {
            T result = mapResponse(responseBody.string(), responseType);
            return result;
        }
        catch (IOException e) {
            throw new BridgeIOException(e);
        }
    }

    /**
     * Map a JSON response to a domain object.
     * <p>
     * This method has been factored out to simplify the unfortunate necessary handling of checked IOException.
     *
     * @param body
     * @param responseType
     * @param <T>
     * @return
     */
    private <T> T mapResponse(String body, JavaType responseType) {
        try {
            T result = objectMapper.reader().withType(responseType).readValue(body);
            return result;
        }
        catch (IOException e) {
            try {
                List<ErrorDetail> result = objectMapper.reader().withType(ErrorResult.TYPE_REFERENCE).readValue(body);
                throw new BridgeErrorResultException(new ErrorResult(result));
            }
            catch (IOException f) {
                // Failed to unmarshal the error response, so throw the *original* exception
                throw new BridgeIOException(e);
            }
        }
    }
}
