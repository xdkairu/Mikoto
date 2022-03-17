package org.Clover.Settings;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.Clover.Clover;
import org.Clover.Utilities.Data;
import org.Clover.Utilities.RoleCheck;

import java.awt.*;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class SetPrefix extends ListenerAdapter {

    private final Clover clover;
    public SetPrefix(Clover clover){
        this.clover = clover;
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Data data = new Data();
        RoleCheck rc = new RoleCheck();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder success = new EmbedBuilder();

        if (args[0].equalsIgnoreCase(clover.getGuildConfig().get("prefix") + "setprefix")) {
            if (rc.isOwner(event) || rc.isAdmin(event)) {
                if (args.length < 2) {
                        eb.setDescription("You didn't specify enough arguments");
                        eb.setColor(new Color(data.getColor()));
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Insufficient Arguments", data.getSelfAvatar(event));

                        event.getChannel().sendMessageEmbeds(eb.build()).queue((message) -> {
                            message.delete().queueAfter(10, TimeUnit.SECONDS);
                            event.getMessage().delete().queueAfter(10, TimeUnit.SECONDS);
                            eb.clear();
                        });
                } else {
                    clover.getGuildConfig().setPrefix(args[1]);
                    eb.setDescription("Successfully set the prefix to `" + args[1] + "`");
                    eb.setColor(new Color(data.getColor()));
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Prefix", data.getSelfAvatar(event));

                    success.setDescription(event.getMember().getAsMention() + " set the prefix to `" + args[1] + "`");
                    success.setColor(new Color(data.getColor()));
                    success.setFooter("Prefix", data.getSelfAvatar(event));
                    success.setTimestamp(Instant.now());

                    event.getChannel().sendMessageEmbeds(eb.build()).queue((message) -> {
                        message.delete().queueAfter(15, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                        eb.clear();
                        event.getGuild().getTextChannelCache().getElementById(clover.getGuildConfig().get("logChannel")).sendMessageEmbeds(success.build()).queue((message2) -> {
                            success.clear();
                        });
                    });

                    event.getGuild().modifyNickname(event.getGuild().getSelfMember(), "Clover | " + args[1]).queue();
                }
            }
        }
    }

}
