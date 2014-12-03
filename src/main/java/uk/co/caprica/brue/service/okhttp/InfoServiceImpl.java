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

import java.util.List;

import uk.co.caprica.brue.service.InfoService;
import uk.co.caprica.brue.service.settings.BridgeSettings;

import com.fasterxml.jackson.core.type.TypeReference;

public final class InfoServiceImpl extends AbstractBridgeService implements InfoService {

    /**
     *
     */
    private static final String RESOURCE_PATH = "info";

    /**
     *
     */
    private static final TypeReference<List<String>> timezoneListTypeReference = new TypeReference<List<String>>() {};

    /**
     *
     * @param bridgeSettings
     */
    protected InfoServiceImpl(BridgeSettings bridgeSettings) {
        super(bridgeSettings, RESOURCE_PATH);
    }

    @Override
    public List<String> timezones() {
        return getResource(resourceUrl("timezones"), timezoneListTypeReference);
    }
}
