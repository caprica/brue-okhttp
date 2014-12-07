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

import uk.co.caprica.brue.domain.bridge.Sensor;
import uk.co.caprica.brue.service.bridge.SensorService;
import uk.co.caprica.brue.settings.bridge.BridgeSettings;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;

public final class SensorServiceImpl extends AbstractBridgeService implements SensorService {

    /**
     *
     */
    private static final String RESOURCE_PATH = "sensors";

    /**
     *
     */
    private static final TypeReference<Map<Integer,Sensor>> sensorMapTypeReference = new TypeReference<Map<Integer,Sensor>>(){};

    /**
     *
     * @param bridgeSettings
     */
    protected SensorServiceImpl(BridgeSettings bridgeSettings) {
        super(bridgeSettings, RESOURCE_PATH);
    }

    @Override
    public Map<Integer, Sensor> sensors() {
        return ImmutableMap.copyOf(getResource(resourceUrl(), sensorMapTypeReference));
    }

    @Override
    public Sensor sensor(Integer sensorId) {
        return getResource(resourceUrl(sensorId), Sensor.class);
    }
}
