package org.Clover.Utilities;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class RoleCheck {

    public static boolean isOwner(SlashCommandInteractionEvent event) {
        return event.getMember().getRoles().contains(event.getGuild().getRoleById("920734037654536202"));
    }

    public static boolean isAdmin(SlashCommandInteractionEvent event) {
        return event.getMember().getRoles().contains(event.getGuild().getRoleById("950285041878069249"));
    }
}
