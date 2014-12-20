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

import uk.co.caprica.brue.core.domain.bridge.builder.ConfigBuilder;
import uk.co.caprica.brue.core.domain.bridge.config.Config;
import uk.co.caprica.brue.core.domain.bridge.result.Results;
import uk.co.caprica.brue.core.service.bridge.ConfigService;
import uk.co.caprica.brue.core.settings.bridge.BridgeSettings;

public final class ConfigServiceImpl extends AbstractBridgeService implements ConfigService {

    /**
     *
     */
    private static final String RESOURCE_PATH = "config";

    /**
     *
     */
    private static final String WHITELIST_PATH = "whitelist";

    /**
     *
     * @param bridgeSettings
     */
    protected ConfigServiceImpl(BridgeSettings bridgeSettings) {
        super(bridgeSettings, RESOURCE_PATH);
    }

    @Override
    public Config config() {
        return getResource(resourceUrl(), Config.class);
    }

    @Override
    public Results update(ConfigBuilder config) {
        return updateResource(resourceUrl(), config);
    }

    @Override
    public Results deleteUser(String username) {
        return deleteResource(resourceUrl(WHITELIST_PATH, username));
    }
}
