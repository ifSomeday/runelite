package net.runelite.client.plugins.notifier;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import javax.inject.Inject;

import com.google.gson.JsonObject;
import com.google.inject.Provides;

import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.Skill;
import net.runelite.api.events.ConfigChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.http.api.RuneLiteAPI;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@PluginDescriptor(
    name = "Push Notifier",
    description = "Notifies you with PushBullet when user-specified conditions are met",
    tags = {"notification", "push", "alert"}
)
@Slf4j
public class NotifierPlugin extends Plugin {

    @Inject
    private Client client;

    @Inject
    private NotifierConfig config;

    @Provides
    NotifierConfig getConfig(ConfigManager configManager) {
        return(configManager.getConfig(NotifierConfig.class));
    }

    @Override
    protected void startUp() throws Exception {
        return;
    }

    @Override
    protected void shutDown() throws Exception {
        return;
    }

    private boolean notified = false;

    @Subscribe
    public void onGameTick(GameTick event) {
        
        final Player local = client.getLocalPlayer();

        int statLevel = 0;

        switch(config.notifyCondition()) {

            case HITPOINTS:
                statLevel = getCurrentHitpoints();
                break;
            case PRAYER:
                statLevel = getCurrentPrayer();
                break;
            case NONE:
                //no break;
            default:
                return;

        }

        boolean shouldNotify = false;
        int threshold = config.threshold();

        switch(config.notifyOperation()) {

            case EQUAL:
                shouldNotify = (statLevel == threshold);
                break;
            case GREATER_THAN:
                shouldNotify = (statLevel > threshold);
                break;
            case GREATER_THAN_OR_EQUAL:
                shouldNotify = (statLevel >= threshold);
                break;
            case LESS_THAN:
                shouldNotify = (statLevel < threshold);
                break;
            case LESS_THAN_OR_EQUAL:
                shouldNotify = (statLevel <= threshold);
                break;
            default:
                return;
            
        }

        if(!shouldNotify && notified) {
            notified = false;
            return;
        }

        if(shouldNotify && !notified){
            sendNotification();
        }

    }

    private void sendNotification() {
        notified = true;

        log.info("Sending pushbullet notifcation");

        if(config.pause()) {
            return;
        }

        String token = "o.OZ39QzQbARezM3Xz5GGnDQqI5jqPBZ2C";

        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient httpClient = RuneLiteAPI.CLIENT;
        
        JsonObject bodyObj = new JsonObject();
        bodyObj.addProperty("title", "RuneLite Notification");
        bodyObj.addProperty("body", String.format("Your %s stat is now %s %d", config.notifyCondition(), config.notifyCondition(), config.threshold()));
        bodyObj.addProperty("email", config.emailAddress());
        bodyObj.addProperty("type", "note");
        String postBody = bodyObj.toString();

        log.info("Request:\n {}", postBody);

        Request request = new Request.Builder()
        .url("https://api.pushbullet.com/v2/pushes")
        .addHeader("Access-Token", token)
        .post(RequestBody.create(JSON, postBody))
        .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if(!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
            
            //log.info(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getCurrentHitpoints() {
        return(getCurrentStatLevel(Skill.HITPOINTS));
    }

    private int getCurrentPrayer() {
        return(getCurrentStatLevel(Skill.PRAYER));
    }

    private int getCurrentStatLevel(Skill skill) {
        return(client.getBoostedSkillLevel(skill));
    }

}