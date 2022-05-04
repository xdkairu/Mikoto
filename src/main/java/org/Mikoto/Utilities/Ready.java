package org.Mikoto.Utilities;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public class Ready extends ListenerAdapter {

    public void onReady(ReadyEvent event) {
        //Clear Command
        event.getJDA().upsertCommand("clear", "Clears messages in a channel")
                .addOption(OptionType.STRING, "amount", "The amount of messages you want to clear", true, false).queue();

        //Ban Command
        event.getJDA().upsertCommand("ban", "Ban a member from the server")
                .addOption(OptionType.MENTIONABLE,"member", "The member you wish to ban", true, false)
                .addOption(OptionType.STRING, "reason", "The reason for banning the member", true, false)
                .addOption(OptionType.ATTACHMENT, "proof", "Proof of the reason for the kick", false, false).queue();

        //Kick Command
        event.getJDA().upsertCommand("kick", "Kick a member from the server")
                .addOption(OptionType.MENTIONABLE, "member", "The member you wish to kick", false, false)
                .addOption(OptionType.STRING, "reason", "The reason for kicking the member", false, false)
                .addOption(OptionType.ATTACHMENT, "proof", "Proof of the reason for the kick", false, false).queue();

        //Mute Command
        event.getJDA().upsertCommand("mute", "Mutes a member from the server")
                .addOption(OptionType.MENTIONABLE, "member", "The member you wish to mute", true, false)
                .addOption(OptionType.STRING, "reason", "The reason for muting the member", true, false)
                .addOption(OptionType.ATTACHMENT, "proof", "Proof of the reason for the mute", false, false).queue();

        //Unmute Command
        event.getJDA().upsertCommand("unmute", "Unmutes a member from the server")
                .addOption(OptionType.MENTIONABLE, "member", "The member you wish to unmute", true, false).queue();

        //Userinfo Command
        event.getJDA().upsertCommand("userinfo", "Shows a users information")
                .addOption(OptionType.MENTIONABLE, "member", "A members information you wish to see", false, false).queue();

        //Hug Command
        event.getJDA().upsertCommand("hug", "Hug someone or if you are lonely yourself")
                .addOption(OptionType.MENTIONABLE, "member", "The person you want to hug", false, false).queue();
    }
}
