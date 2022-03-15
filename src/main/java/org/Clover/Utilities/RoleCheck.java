package org.Clover.Utilities;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RoleCheck {

    public static boolean isOwner(MessageReceivedEvent event) {
        return event.getMember().getRoles().contains(event.getGuild().getRoleById("920734037654536202"));
    }

    public static boolean isAdmin(MessageReceivedEvent event) {
        return event.getMember().getRoles().contains(event.getGuild().getRoleById("950285041878069249"));
    }
}
