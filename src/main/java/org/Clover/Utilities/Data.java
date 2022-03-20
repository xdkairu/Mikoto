package org.Clover.Utilities;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Data {

    public static int failedRed = 0xFF0000;
    public static int successGreen = 0x00FF00;

    public String getOnlineEmote(Guild guild) {
        return guild.getEmoteById("955076536468402227").getAsMention();
    }

    public String getAwayEmote(Guild guild) {
        return guild.getEmoteById("955076536304795658").getAsMention();
    }

    public String getDndEmote(Guild guild) {
        return guild.getEmoteById("955076536451616818").getAsMention();
    }

    public String getInvisibleEmote(Guild guild) {
        return guild.getEmoteById("955076536346742814").getAsMention();
    }

    public static String getSelfAvatar(SlashCommandInteractionEvent event) {
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

}
