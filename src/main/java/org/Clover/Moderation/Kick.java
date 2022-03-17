package org.Clover.Moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.Clover.Clover;
import org.Clover.Utilities.Data;
import org.Clover.Utilities.RoleCheck;

import java.awt.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Kick extends ListenerAdapter {

    private final Clover clover;
    public Kick(Clover clover){
        this.clover = clover;
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Data data = new Data();
        RoleCheck rc = new RoleCheck();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder success = new EmbedBuilder();
        EmbedBuilder kicked = new EmbedBuilder();

        if (args[0].equalsIgnoreCase(clover.getGuildConfig().get("prefix") + "kick")) {
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
                } else if (args.length < 3) {
                    Member mentioned = event.getMessage().getMentionedMembers().get(0);
                    String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));

                    kicked.setDescription("You've been kicked from: " + event.getGuild().getName() + "\n\nReason: \n```\nThere was no reason specified\n```");
                    kicked.setColor(new Color(data.getColor()));
                    kicked.setTimestamp(Instant.now());
                    kicked.setFooter("Kicked", data.getSelfAvatar(event));

                    eb.setDescription("You've kicked: " + mentioned.getAsMention() + "\n\nReason:\n```\nNo reason specified\n```");
                    eb.setColor(new Color(data.getColor()));
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Kicked", data.getSelfAvatar(event));

                    success.setDescription(mentioned.getAsMention() + " has been kicked by " + event.getMember().getAsMention() + "\n\nReason:\n```\n" + reason + "\n```");
                    success.setColor(new Color(data.getColor()));
                    success.setTimestamp(Instant.now());
                    success.setFooter("Kicked", data.getSelfAvatar(event));

                    mentioned.getUser().openPrivateChannel().queue((channel) -> {
                        channel.sendMessageEmbeds(kicked.build()).queue();
                        kicked.clear();

                        event.getChannel().sendMessageEmbeds(eb.build()).queue((message) -> {
                            message.delete().queueAfter(10, TimeUnit.SECONDS);
                            event.getMessage().delete().queueAfter(10, TimeUnit.SECONDS);
                            event.getGuild().getTextChannelCache().getElementById(clover.getGuildConfig().get("logChannel")).sendMessageEmbeds(success.build()).queue((message2) -> {
                                success.clear();
                            });
                            eb.clear();
                            event.getGuild().kick(mentioned, "No reason specified").queue();
                        });
                    });
                } else {
                    Member mentioned = event.getMessage().getMentionedMembers().get(0);
                    String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));

                    kicked.setDescription("You've been kicked from: " + event.getGuild().getName() + "\n\nReason:\n```\n" + reason + "\n```");
                    kicked.setColor(new Color(data.getColor()));
                    kicked.setTimestamp(Instant.now());
                    kicked.setFooter("Kicked", data.getSelfAvatar(event));

                    eb.setDescription("You've kicked: " + mentioned.getAsMention() + " \n\nReason: \n```\n" + reason + "\n```");
                    eb.setColor(new Color(data.getColor()));
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Kicked", data.getSelfAvatar(event));

                    success.setDescription(mentioned.getAsMention() + " has been kicked by " + event.getMember().getAsMention() + "\n\nReason:\n```\n" + reason + "\n```");
                    success.setColor(new Color(data.getColor()));
                    success.setTimestamp(Instant.now());
                    success.setFooter("Kicked", data.getSelfAvatar(event));

                    mentioned.getUser().openPrivateChannel().queue((channel) -> {
                        channel.sendMessageEmbeds(kicked.build()).queue();
                        kicked.clear();

                        event.getChannel().sendMessageEmbeds(eb.build()).queue((message) -> {
                            message.delete().queueAfter(10, TimeUnit.SECONDS);
                            event.getMessage().delete().queueAfter(10, TimeUnit.SECONDS);
                            event.getGuild().getTextChannelCache().getElementById(clover.getGuildConfig().get("logChannel")).sendMessageEmbeds(success.build()).queue((message2) -> {
                                success.clear();
                            });
                            eb.clear();
                            event.getGuild().kick(mentioned, reason).queue();
                        });
                    });
                }
            } else {
                eb.setDescription("You don't have permission to use that command.");
                eb.setColor(new Color(data.getColor()));
                eb.setTimestamp(Instant.now());
                eb.setFooter("Insufficient Permissions", data.getSelfAvatar(event));
                event.getChannel().sendMessageEmbeds(eb.build()).queue((message) -> {
                    message.delete().queueAfter(10, TimeUnit.SECONDS);
                    event.getMessage().delete().queueAfter(10, TimeUnit.SECONDS);
                    eb.clear();
                });
            }
        }
    }
}
