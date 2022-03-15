package org.Clover.Moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.Clover.Utilities.Data;
import org.Clover.Utilities.RoleCheck;

import java.awt.*;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Clear extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        RoleCheck rc = new RoleCheck();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder success = new EmbedBuilder();
        Data data = new Data();

        if (args[0].equalsIgnoreCase(data.getPrefix() + "clear")) {
            if (rc.isOwner(event) || rc.isAdmin(event)) {
                if (args.length < 2) {
                    eb.setDescription("You didn't specify enough arguments");
                    eb.setColor(new Color(data.getColor()));
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Insufficient Arguments", event.getJDA().getSelfUser().getEffectiveAvatarUrl());

                    event.getChannel().sendMessageEmbeds(eb.build()).queue((message) -> {
                        message.delete().queueAfter(15, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if (args.length == 2) {
                    try {
                        Integer messageCount = Integer.parseInt(args[1]);
                        if (messageCount < 2) {
                            eb.setDescription("Too few messages to delete. Minimum amount of messages I can delete is 2");
                            eb.setColor(new Color(data.getColor()));
                            eb.setTimestamp(Instant.now());
                            eb.setFooter("Too Few Messages to Delete", data.getSelfAvatar(event));

                            event.getChannel().sendMessageEmbeds(eb.build()).queue((message) -> {
                                message.delete().queueAfter(15, TimeUnit.SECONDS);
                                event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                                eb.clear();
                            });
                        } else if (messageCount > 100) {
                            eb.setDescription("Maximum amount of messages I can delete at a time is 100");
                            eb.setColor(new Color(data.getColor()));
                            eb.setTimestamp(Instant.now());
                            eb.setFooter("Too Many Messages to Delete", data.getSelfAvatar(event));

                            event.getChannel().sendMessageEmbeds(eb.build()).queue((message) -> {
                                message.delete().queueAfter(15, TimeUnit.SECONDS);
                                event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                                eb.clear();
                            });
                        } else {
                            List<Message> messages = event.getChannel().getHistory().retrievePast(messageCount).complete();
                            event.getChannel().purgeMessages(messages);

                            eb.setDescription("Deleted " + messageCount.toString() + " messages from " + event.getChannel().getAsMention());
                            eb.setColor(new Color(data.getColor()));
                            eb.setTimestamp(Instant.now());
                            eb.setFooter("Clear Messages", event.getJDA().getSelfUser().getEffectiveAvatarUrl());

                            success.setDescription(event.getMember().getAsMention() + " deleted " + messageCount.toString() + " messages from: " + event.getChannel().getAsMention());
                            success.setColor(new Color(data.getColor()));
                            success.setFooter("Clear Messages", data.getSelfAvatar(event));
                            success.setTimestamp(Instant.now());

                            event.getChannel().sendMessageEmbeds(eb.build()).queue((message) -> {
                                message.delete().queueAfter(15, TimeUnit.SECONDS);
                                data.getLogChannel(event).sendMessageEmbeds(success.build()).queue((message2) -> {
                                    success.clear();
                                });
                                eb.clear();
                            });
                        }
                    } catch (NumberFormatException nfe) {
                        eb.setDescription("`" + args[1] + "` is not a valid number. Please input a number between 2 and 100");
                        eb.setColor(new Color(data.getColor()));
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Not a valid number", data.getSelfAvatar(event));

                        event.getChannel().sendMessageEmbeds(eb.build()).queue((message) -> {
                            message.delete().queueAfter(15, TimeUnit.SECONDS);
                            event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                            eb.clear();
                        });
                    }
                }
            } else {
                eb.setDescription(event.getMember().getAsMention() + ", You don't have the permission to manage messages for this server.");
                eb.setColor(new Color(data.getColor()));
                eb.setTimestamp(Instant.now());
                eb.setFooter("Insufficient Permissions", data.getSelfAvatar(event));

                event.getChannel().sendMessageEmbeds(eb.build()).queue((message) -> {
                    message.delete().queueAfter(15, TimeUnit.SECONDS);
                    event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                    eb.clear();
                });
            }
        }
    }
}