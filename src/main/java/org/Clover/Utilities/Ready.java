package org.Clover.Utilities;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public class Ready extends ListenerAdapter {

    public void onReady(ReadyEvent event) {
        //Clear Command
        event.getJDA().getGuildById("916886277587079210").upsertCommand("clear", "Clears messages in a channel")
                .addOption(OptionType.STRING, "amount", "The amount of messages you want to clear", false, false).queue();

        //Ban Command
        event.getJDA().getGuildById("916886277587079210").upsertCommand("ban", "Ban a member from the server")
                .addOption(OptionType.MENTIONABLE,"member", "The member you wish to ban", false, false)
                .addOption(OptionType.STRING, "reason", "The reason for banning the member", false, false)
                .addOption(OptionType.ATTACHMENT, "proof", "Proof of the reason for the kick", false, false).queue();

        //Kick Command
        event.getJDA().getGuildById("916886277587079210").upsertCommand("kick", "Kick a member from the server")
                .addOption(OptionType.MENTIONABLE, "member", "The member you wish to kick", false, false)
                .addOption(OptionType.STRING, "reason", "The reason for kicking the member", false, false)
                .addOption(OptionType.ATTACHMENT, "proof", "Proof of the reason for the kick", false, false).queue();

        //Userinfo Command
        event.getJDA().getGuildById("916886277587079210").upsertCommand("userinfo", "Shows a users information")
                .addOption(OptionType.MENTIONABLE, "member", "A members information you wish to see", false, false).queue();
    }
}
