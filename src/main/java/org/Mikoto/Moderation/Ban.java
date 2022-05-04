package org.Mikoto.Moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.Mikoto.Mikoto;
import org.Mikoto.Utilities.Data;

import java.awt.*;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Ban extends ListenerAdapter {

    private final Mikoto mikoto;
    Data data = new Data();
    EmbedBuilder eb = new EmbedBuilder();
    EmbedBuilder banned = new EmbedBuilder();
    EmbedBuilder log = new EmbedBuilder();
    EmbedBuilder success = new EmbedBuilder();

    public Ban(Mikoto mikoto) {
        this.mikoto = mikoto;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("ban")) return;
        if (event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            if (event.getOptions().size() == 1) {
                String mentioned = event.getOption("member").getAsMember().getAsMention();

                banned.setDescription("You've been banned from: " + event.getGuild().getName() + "\n\nReason: \n```\nNo Reason Specified\n```");
                banned.setColor(new Color(data.successGreen));
                banned.setTimestamp(Instant.now());
                banned.setFooter("Banned", data.getSelfAvatar(event));

                success.setDescription("You've banned: " + mentioned + "\n\nReason:\n```\nNo Reason Specified\n```");
                success.setColor(new Color(data.successGreen));
                success.setTimestamp(Instant.now());
                success.setFooter("Banned", data.getSelfAvatar(event));

                log.setDescription(mentioned + " has been banned by " + event.getMember().getAsMention() + "\n\nReason:\n```\nNo Reason Specified\n```");
                log.setColor(new Color(data.successGreen));
                log.setTimestamp(Instant.now());
                log.setFooter("Banned Log", data.getSelfAvatar(event));

                event.replyEmbeds(success.build()).queue((msg) -> {
                    success.clear();
                    msg.deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
                    event.getGuild().getTextChannelCache().getElementById(mikoto.getConfig().get("logChannelID")).sendMessageEmbeds(log.build()).queue((msg2) -> {
                        log.clear();
                        event.getOption("member").getAsMember().getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessageEmbeds(banned.build()).queue();
                            banned.clear();
                            event.getGuild().ban(event.getOption("member").getAsMember().getUser(), 7, "No Reason Specified").queue();
                        });
                    });
                });
            } else if (event.getOptions().size() == 2) {
                String mentioned = event.getOption("member").getAsMember().getAsMention();
                String reason = event.getOption("reason").getAsString();

                banned.setDescription("You've been banned from: " + event.getGuild().getName() + "\n\nReason:\n```\n" + reason + "\n```");
                banned.setColor(new Color(data.successGreen));
                banned.setTimestamp(Instant.now());
                banned.setFooter("Banned", data.getSelfAvatar(event));

                success.setDescription("You've banned: " + mentioned + " \n\nReason: \n```\n" + reason + "\n```");
                success.setColor(new Color(data.successGreen));
                success.setTimestamp(Instant.now());
                success.setFooter("Banned", data.getSelfAvatar(event));

                log.setDescription(mentioned + " has been banned by " + event.getMember().getAsMention() + "\n\nReason:\n```\n" + reason + "\n```");
                log.setColor(new Color(data.successGreen));
                log.setTimestamp(Instant.now());
                log.setFooter("Banned Log", data.getSelfAvatar(event));

                event.replyEmbeds(success.build()).queue((msg) -> {
                    success.clear();
                    msg.deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
                    event.getGuild().getTextChannelCache().getElementById(mikoto.getConfig().get("logChannelID")).sendMessageEmbeds(log.build()).queue((msg2) -> {
                        log.clear();
                        event.getOption("member").getAsMember().getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessageEmbeds(banned.build()).queue();
                            banned.clear();
                            event.getGuild().ban(event.getOption("member").getAsMember().getUser(), 7, reason).queue();
                        });
                    });
                });
            }
        } else {
            eb.setDescription("Permission `Ban Members` needed to use this command.");
            eb.setColor(new Color(data.failedRed));
            eb.setTimestamp(Instant.now());
            eb.setFooter("Insufficient Permissions", data.getSelfAvatar(event));

            event.replyEmbeds(eb.build()).queue((msg) -> {
                eb.clear();
                msg.deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
            });
        }
    }
}

