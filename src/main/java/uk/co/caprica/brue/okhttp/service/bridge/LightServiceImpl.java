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

import java.util.Map;

import uk.co.caprica.brue.core.domain.bridge.Light;
import uk.co.caprica.brue.core.domain.bridge.builder.AttributesBuilder;
import uk.co.caprica.brue.core.domain.bridge.builder.LightStateBuilder;
import uk.co.caprica.brue.core.domain.bridge.result.Results;
import uk.co.caprica.brue.core.service.bridge.LightService;
import uk.co.caprica.brue.core.settings.bridge.BridgeSettings;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;

public final class LightServiceImpl extends AbstractBridgeService implements LightService {

    /**
     *
     */
    private static final String RESOURCE_PATH = "lights";

    /**
     *
     */
    private static final String STATE_PATH = "state";

    /**
     *
     */
    private static final TypeReference<Map<Integer,Light>> lightMapTypeReference = new TypeReference<Map<Integer,Light>>(){};

    /**
     *
     * @param bridgeSettings
     */
    protected LightServiceImpl(BridgeSettings bridgeSettings) {
        super(bridgeSettings, RESOURCE_PATH);
    }

    @Override
    public Map<Integer,Light> lights() {
        return ImmutableMap.copyOf(getResource(resourceUrl(), lightMapTypeReference));
    }

    @Override
    public Light light(Integer lightId) {
        return getResource(resourceUrl(lightId), Light.class);
    }

    @Override
    public Results attributes(Integer lightId, AttributesBuilder attributes) {
        return updateResource(resourceUrl(lightId), attributes);
    }

    @Override
    public Results state(Integer lightId, LightStateBuilder state) {
        return updateResource(resourceUrl(lightId, STATE_PATH), state);
    }
}
