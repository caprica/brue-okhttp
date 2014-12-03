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

import uk.co.caprica.brue.domain.Schedule;
import uk.co.caprica.brue.service.ScheduleService;
import uk.co.caprica.brue.service.settings.BridgeSettings;

import com.fasterxml.jackson.core.type.TypeReference;

public final class ScheduleServiceImpl extends AbstractBridgeService implements ScheduleService {

    /**
     *
     */
    private static final String RESOURCE_PATH = "schedules";

    /**
     *
     */
    private static final TypeReference<Map<Long,Schedule>> scheduleMapTypeReference = new TypeReference<Map<Long,Schedule>>(){};

    /**
     *
     * @param bridgeSettings
     */
    protected ScheduleServiceImpl(BridgeSettings bridgeSettings) {
        super(bridgeSettings, RESOURCE_PATH);
    }

    @Override
    public Map<Long, Schedule> schedules() {
        return getResource(resourceUrl(), scheduleMapTypeReference);
    }

    @Override
    public Schedule schedule(Integer scheduleId) {
        return getResource(resourceUrl(scheduleId), Schedule.class);
    }

//    @Override
//    public SuccessResponse create(GroupSpec groupSpec) {
//        return null;
//    }
//
//    @Override
//    public void update(GroupSpec groupSpec) {
//
//    }

    @Override
    public void delete(Integer scheduleId) {
        deleteResource(resourceUrl(scheduleId));
    }
}
