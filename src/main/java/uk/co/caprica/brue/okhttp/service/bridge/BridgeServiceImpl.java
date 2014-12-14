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

import uk.co.caprica.brue.core.service.bridge.AuthorisationService;
import uk.co.caprica.brue.core.service.bridge.BridgeService;
import uk.co.caprica.brue.core.service.bridge.ConfigService;
import uk.co.caprica.brue.core.service.bridge.GroupService;
import uk.co.caprica.brue.core.service.bridge.InfoService;
import uk.co.caprica.brue.core.service.bridge.LightService;
import uk.co.caprica.brue.core.service.bridge.RuleService;
import uk.co.caprica.brue.core.service.bridge.SceneService;
import uk.co.caprica.brue.core.service.bridge.ScheduleService;
import uk.co.caprica.brue.core.service.bridge.SensorService;
import uk.co.caprica.brue.core.settings.bridge.BridgeSettings;

public class BridgeServiceImpl implements BridgeService {

    private final AuthorisationService authoriseService;

    private final ConfigService configService;

    private final GroupService groupService;

    private final InfoService infoService;

    private final LightService lightService;

    private final RuleService ruleService;

    private final SceneService sceneService;

    private final ScheduleService scheduleService;

    private final SensorService sensorService;

    public BridgeServiceImpl(BridgeSettings bridgeSettings) {
        this.authoriseService = new AuthorisationServiceImpl(bridgeSettings);
        this.configService    = new ConfigServiceImpl       (bridgeSettings);
        this.groupService     = new GroupServiceImpl        (bridgeSettings);
        this.infoService      = new InfoServiceImpl         (bridgeSettings);
        this.lightService     = new LightServiceImpl        (bridgeSettings);
        this.ruleService      = new RuleServiceImpl         (bridgeSettings);
        this.sceneService     = new SceneServiceImpl        (bridgeSettings);
        this.scheduleService  = new ScheduleServiceImpl     (bridgeSettings);
        this.sensorService    = new SensorServiceImpl       (bridgeSettings);
    }

    @Override
    public AuthorisationService authorisation() {
        return authoriseService;
    }

    @Override
    public ConfigService config() {
        return configService;
    }

    @Override
    public GroupService group() {
        return groupService;
    }

    @Override
    public InfoService info() {
        return infoService;
    }

    @Override
    public LightService light() {
        return lightService;
    }

    @Override
    public RuleService rule() {
        return ruleService;
    }

    @Override
    public SceneService scene() {
        return sceneService;
    }

    @Override
    public ScheduleService schedule() {
        return scheduleService;
    }

    @Override
    public SensorService sensor() {
        return sensorService;
    }
}
