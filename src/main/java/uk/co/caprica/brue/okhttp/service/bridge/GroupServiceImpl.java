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

import uk.co.caprica.brue.domain.bridge.Group;
import uk.co.caprica.brue.domain.bridge.builder.GroupBuilder;
import uk.co.caprica.brue.domain.bridge.builder.GroupStateBuilder;
import uk.co.caprica.brue.domain.bridge.result.CreateResult;
import uk.co.caprica.brue.domain.bridge.result.DeleteResult;
import uk.co.caprica.brue.domain.bridge.result.UpdateResult;
import uk.co.caprica.brue.service.bridge.GroupService;
import uk.co.caprica.brue.settings.bridge.BridgeSettings;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;

public final class GroupServiceImpl extends AbstractBridgeService implements GroupService {

    /**
     *
     */
    private static final String RESOURCE_PATH = "groups";

    /**
     *
     */
    private static final String ACTION_PATH = "action";

    /**
     *
     */
    private static final TypeReference<Map<Integer,Group>> groupMapTypeReference = new TypeReference<Map<Integer,Group>>(){};

    /**
     *
     * @param bridgeSettings
     */
    protected GroupServiceImpl(BridgeSettings bridgeSettings) {
        super(bridgeSettings, RESOURCE_PATH);
    }

    @Override
    public Map<Integer, Group> groups() {
        return ImmutableMap.copyOf(getResource(resourceUrl(), groupMapTypeReference));
    }

    @Override
    public Group group(Integer groupId) {
        return getResource(resourceUrl(groupId), Group.class);
    }

    @Override
    public CreateResult create(GroupBuilder group) {
        return createResource(resourceUrl(), group);
    }

    @Override
    public DeleteResult delete(Integer groupId) {
        return deleteResource(resourceUrl(groupId));
    }

    @Override
    public UpdateResult attributes(Integer groupId, GroupBuilder group) {
        return updateResource(resourceUrl(groupId), group);
    }

    @Override
    public UpdateResult state(Integer groupId, GroupStateBuilder state) {
        return updateResource(resourceUrl(groupId, ACTION_PATH), state);
    }
}
