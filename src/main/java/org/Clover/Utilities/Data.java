package org.Clover.Utilities;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Data {

    public static int failedRed = 0xFF0000;
    public static int successGreen = 0x00FF00;

    public String getOnlineEmote(Guild guild) {
        return guild.getEmoteById("970478166592340019").getAsMention();
    }

    public String getAwayEmote(Guild guild) {
        return guild.getEmoteById("970478166550409236").getAsMention();
    }

    public String getDndEmote(Guild guild) {
        return guild.getEmoteById("970478166298738808").getAsMention();
    }

    public String getInvisibleEmote(Guild guild) {
        return guild.getEmoteById("970478166256812032").getAsMention();
    }

    public static String getSelfAvatar(SlashCommandInteractionEvent event) {
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

}
