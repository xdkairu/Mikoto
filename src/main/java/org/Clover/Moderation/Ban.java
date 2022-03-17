package org.Clover.Moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
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

public class Ban extends ListenerAdapter {

    private final Clover clover;
    public Ban(Clover clover){
        this.clover = clover;
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        RoleCheck rc = new RoleCheck();
        Data data = new Data();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder banned = new EmbedBuilder();
        EmbedBuilder success = new EmbedBuilder();

        if (args[0].equalsIgnoreCase(clover.getGuildConfig().get("prefix") + "ban")) {
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

                    banned.setDescription("You've been banned from: " + event.getGuild().getName() + "\n\nReason: \n```\nThere was no reason specified\n```");
                    banned.setColor(new Color(data.getColor()));
                    banned.setTimestamp(Instant.now());
                    banned.setFooter("Banned", data.getSelfAvatar(event));

                    eb.setDescription("You've banned: " + mentioned.getAsMention() + "\n\nReason:\n```\nNo reason specified\n```");
                    eb.setColor(new Color(data.getColor()));
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Banned", data.getSelfAvatar(event));

                    success.setDescription(mentioned.getAsMention() + " has been banned by " + event.getMember().getAsMention() + "\n\nReason:\n```\n" + reason + "\n```");
                    success.setColor(new Color(data.getColor()));
                    success.setTimestamp(Instant.now());
                    success.setFooter("Banned", data.getSelfAvatar(event));

                    mentioned.getUser().openPrivateChannel().queue((channel) -> {
                        channel.sendMessageEmbeds(banned.build()).queue();
                        banned.clear();

                        event.getChannel().sendMessageEmbeds(eb.build()).queue((message) -> {
                            message.delete().queueAfter(10, TimeUnit.SECONDS);
                            event.getMessage().delete().queueAfter(10, TimeUnit.SECONDS);
                            event.getGuild().getTextChannelCache().getElementById(clover.getGuildConfig().get("logChannel")).sendMessageEmbeds(success.build()).queue((message2) -> {
                                success.clear();
                            });
                            eb.clear();
                            event.getGuild().ban(mentioned, 7, "No Reason Specified").queue();
                        });
                    });

                } else {
                    Member mentioned = event.getMessage().getMentionedMembers().get(0);
                    String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));

                    banned.setDescription("You've been banned from: " + event.getGuild().getName() + "\n\nReason:\n```\n" + reason + "\n```");
                    banned.setColor(new Color(data.getColor()));
                    banned.setTimestamp(Instant.now());
                    banned.setFooter("Banned", data.getSelfAvatar(event));

                    eb.setDescription("You've banned: " + mentioned.getAsMention() + " \n\nReason: \n```\n" + reason + "\n```");
                    eb.setColor(new Color(data.getColor()));
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Banned", data.getSelfAvatar(event));

                    success.setDescription(mentioned.getAsMention() + " has been banned by " + event.getMember().getAsMention() + "\n\nReason:\n```\n" + reason + "\n```");
                    success.setColor(new Color(data.getColor()));
                    success.setTimestamp(Instant.now());
                    success.setFooter("Banned", data.getSelfAvatar(event));

                    mentioned.getUser().openPrivateChannel().queue((channel) -> {
                        channel.sendMessageEmbeds(banned.build()).queue();
                        banned.clear();

                        event.getChannel().sendMessageEmbeds(eb.build()).queue((message) -> {
                            message.delete().queueAfter(10, TimeUnit.SECONDS);
                            event.getMessage().delete().queueAfter(10, TimeUnit.SECONDS);
                            event.getGuild().getTextChannelCache().getElementById(clover.getGuildConfig().get("logChannel")).sendMessageEmbeds(success.build()).queue((message2) -> {
                                success.clear();
                            });
                            eb.clear();
                            event.getGuild().ban(mentioned, 7, reason).queue();
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
