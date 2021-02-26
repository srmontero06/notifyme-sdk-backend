package ch.ubique.notifyme.sdk.backend.ws.service;

import ch.ubique.notifyme.sdk.backend.data.PushRegistrationDataService;
import ch.ubique.notifyme.sdk.backend.model.PushRegistrationOuterClass.PushRegistration;
import ch.ubique.notifyme.sdk.backend.model.PushRegistrationOuterClass.PushType;
import ch.ubique.pushservice.pushconnector.PushConnectorService;
import ch.ubique.pushservice.shared.AndroidPushData;
import ch.ubique.pushservice.shared.ApplePushData;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhoneHeartbeatSilentPush {

    private static final Logger logger = LoggerFactory.getLogger(PhoneHeartbeatSilentPush.class);

    private final PushConnectorService pushConnectorService;
    private final PushRegistrationDataService pushRegistrationDataService;

    public PhoneHeartbeatSilentPush(
            final PushConnectorService pushConnectorService,
            final PushRegistrationDataService pushRegistrationDataService) {
        this.pushConnectorService = pushConnectorService;
        this.pushRegistrationDataService = pushRegistrationDataService;
    }

    public void sendHeartbeats() {

        final var iodPushTokens =
                pushRegistrationDataService.getPushRegistrationByType(PushType.IOD).stream()
                        .map(PushRegistration::getPushToken)
                        .peek(pt -> logger.info("{}: {}", PushType.IOD, pt))
                        .collect(Collectors.toSet());
        final var iosPushTokens =
                pushRegistrationDataService.getPushRegistrationByType(PushType.IOS).stream()
                        .map(PushRegistration::getPushToken)
                        .peek(pt -> logger.info("{}: {}", PushType.IOS, pt))
                        .collect(Collectors.toSet());
        final var androidPushTokens =
                pushRegistrationDataService.getPushRegistrationByType(PushType.AND).stream()
                        .map(PushRegistration::getPushToken)
                        .peek(pt -> logger.info("{}: {}", PushType.AND, pt))
                        .collect(Collectors.toSet());

        logger.info(
                "Found {} iOD and {} iOS and {} android push tokens",
                iodPushTokens.size(),
                iosPushTokens.size(),
                androidPushTokens.size());

        final var appleIodPushData = createAppleSilentPushData(iodPushTokens, true);
        final var appleIosPushData = createAppleSilentPushData(iosPushTokens);
        final var androidPushData = createAndroidSilentPushData(androidPushTokens);

        logger.info(
                "Found {} iOD and {} iOS and {} android push tokens in pushData",
                appleIodPushData.getPushToken().size(),
                appleIosPushData.getPushToken().size(),
                androidPushData.getPushToken().size());

        // pushConnectorService.push(
        //         Arrays.asList(appleIodPushData, appleIosPushData, androidPushData));
        final var response = pushConnectorService.push(Collections.singletonList(appleIodPushData));
        response.forEach(r -> {
            logger.info("response: {}, code: {}", r.getErrorMsg(), r.getStatus());
        });
    }

    private ApplePushData createAppleSilentPushData(final Set<String> applePushTokens) {
        return createAppleSilentPushData(applePushTokens, false);
    }

    private ApplePushData createAppleSilentPushData(
            final Set<String> applePushTokens, final boolean sandbox) {
        final var applePushData = new ApplePushData();
        applePushData.setContentAvailable(1);
        applePushData.setPushToken(applePushTokens);
        applePushData.setSandbox(sandbox);
        return applePushData;
    }

    private AndroidPushData createAndroidSilentPushData(final Set<String> androidPushTokens) {
        final var androidPushData = new AndroidPushData();
        androidPushData.setPushToken(androidPushTokens);
        return androidPushData;
    }
}
