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

package uk.co.caprica.brue.service.okhttp;

import java.util.Map;

import uk.co.caprica.brue.domain.Light;
import uk.co.caprica.brue.domain.builder.StateBuilder;
import uk.co.caprica.brue.domain.result.UpdateResult;
import uk.co.caprica.brue.service.LightService;
import uk.co.caprica.brue.service.settings.BridgeSettings;

import com.fasterxml.jackson.core.type.TypeReference;

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
        return getResource(resourceUrl(), lightMapTypeReference);
    }

    @Override
    public Light light(Integer lightId) {
        return getResource(resourceUrl(lightId), Light.class);
    }

    @Override
    public UpdateResult state(Integer lightId, StateBuilder state) {
        return updateResource(resourceUrl(lightId, STATE_PATH), state);
    }
}
