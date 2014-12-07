package test;

import static uk.co.caprica.brue.domain.bridge.builder.GroupBuilder.group;
import static uk.co.caprica.brue.domain.bridge.builder.GroupStateBuilder.groupState;

import java.util.ArrayList;
import java.util.List;

import uk.co.caprica.brue.domain.bridge.builder.GroupBuilder;
import uk.co.caprica.brue.domain.bridge.builder.GroupStateBuilder;
import uk.co.caprica.brue.domain.bridge.result.CreateResult;
import uk.co.caprica.brue.okhttp.service.bridge.BridgeServiceImpl;
import uk.co.caprica.brue.okhttp.service.discovery.DiscoveryServiceImpl;
import uk.co.caprica.brue.service.bridge.BridgeErrorResultException;
import uk.co.caprica.brue.service.bridge.BridgeService;
import uk.co.caprica.brue.service.discovery.DiscoveryService;
import uk.co.caprica.brue.settings.bridge.BridgeSettings;

/**
 * Created by mark on 29/11/14.
 */
public class TempTest {

    public static void main(String[] args) throws Exception {
        try {
            DiscoveryService discoveryService = new DiscoveryServiceImpl();

//            ServiceDescription sd = discoveryService.serviceDescription();
//            System.out.println("sd=" + sd);

            BridgeService bridgeService = new BridgeServiceImpl(new TestBridgeSettings());

//            Authorisation a = new Authorisation("testuser", "bill456789");
//            AuthoriseResult ar = bridgeService.authorisation().authorise(a);
//            System.out.println("AR=" + ar);
//
//            System.out.println("delete=" + bridgeService.config().deleteUser("bill456789"));
//
//            if (true) return;
//
//            try {
//                bridgeService.group().delete(1);
//                bridgeService.group().delete(2);
//            }
//            catch (Exception e) {
//            }


//            System.out.println("Config=" + bridgeService.config().config());

//            System.out.println("Light1=" + bridgeService.light().light(1));
//            bridgeService.light().attributes(1, AttributesBuilder.attributes().name("Bedroom1"));
//            System.out.println("Light1=" + bridgeService.light().light(1));

            System.out.println(bridgeService.scene().scenes());

            if(true) return;

            System.out.println("Lights=" + bridgeService.light().lights());

            System.out.println("Groups=" + bridgeService.group().groups());

            List<String> lights = new ArrayList<String>();
            lights.add("1");
            lights.add("2");
            lights.add("3");
            GroupBuilder gs = group().name("BimbleGroup").lights("1", "2", "3");
            System.out.println("group=" + gs);

            CreateResult cr = bridgeService.group().create(gs);
            System.out.println("Create=" + cr + " -> " + cr.id());

            GroupStateBuilder state = groupState().on(true);
            System.out.println("state=" + state);

//            System.out.println(bridgeService.group().state(1, state));

            gs = group().name("NewBimble");
            System.out.println("update group name: " + bridgeService.group().attributes(1, gs));
            System.out.println("groups=" + bridgeService.group().groups());

            System.out.println("delete group 1 => " + bridgeService.group().delete(1));
            System.out.println("delete group 2 => " + bridgeService.group().delete(2));


//            System.out.println(bridgeService.light().state(1, state));
//            System.out.println(bridgeService.light().state(2, state));
//            System.out.println(bridgeService.light().state(3, state));

//            System.out.println("Create=" + bridgeService.group().create(gs).first());
//            System.out.println("Groups=" + bridgeService.group().groups());
//            bridgeService.group().delete(1);
//            System.out.println("Groups=" + bridgeService.group().groups());


//            System.out.println("config=" + bridgeService.config().config());
//            System.out.println("info=" + bridgeService.info().timezones());
//
//            System.out.println("Groups=" + bridgeService.group().groups());
//            System.out.println("Lights=" + bridgeService.light().lights());
//            System.out.println("Rules=" + bridgeService.rule().rules());
//            System.out.println("Schedules=" + bridgeService.schedule().schedules());
//            System.out.println("Scenes=" + bridgeService.scene().scenes());
//            System.out.println("Sensors=" + bridgeService.sensor().sensors());
//
//            System.out.println("Light1=" + bridgeService.light().light(1));
//            System.out.println("Group1=" + bridgeService.group().group(1));
        }
        catch (BridgeErrorResultException e) {
            System.out.println(e.result());
        }
    }

    static class TestBridgeSettings implements BridgeSettings {

        @Override
        public String host() {
            return "philips-hue";
        }

        @Override
        public String username() {
            return "newdeveloper";
        }
    }
}
