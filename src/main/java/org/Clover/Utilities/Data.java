package org.Clover.Utilities;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class Data {

    public static int getColor() {
        Random obj = new Random();
        int rand_num = obj.nextInt(0xffffff + 1);
        return rand_num;
    }

    public static String getSelfAvatar(MessageReceivedEvent event) {
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

}
