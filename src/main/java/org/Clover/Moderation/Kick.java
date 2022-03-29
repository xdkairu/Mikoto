package org.Clover.Moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.Clover.Clover;
import org.Clover.Utilities.Data;

import java.awt.*;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Kick extends ListenerAdapter {

    private final Clover clover;
    Data data = new Data();
    EmbedBuilder eb = new EmbedBuilder();
    EmbedBuilder log = new EmbedBuilder();
    EmbedBuilder kicked = new EmbedBuilder();
    EmbedBuilder success = new EmbedBuilder();

    public Kick(Clover clover) {
        this.clover = clover;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("kick")) return;
        if (event.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            if (event.getOptions().size() == 1) {
                String mentioned = event.getOption("member").getAsMember().getAsMention();

                kicked.setDescription("You've been kicked from: " + event.getGuild().getName() + "\n\nReason: \n```\nNo reason specified\n```");
                kicked.setColor(new Color(data.successGreen));
                kicked.setTimestamp(Instant.now());
                kicked.setFooter("Kicked", data.getSelfAvatar(event));

                success.setDescription("You've kicked: " + mentioned + "\n\nReason:\n```\nNo reason specified\n```");
                success.setColor(new Color(data.successGreen));
                success.setTimestamp(Instant.now());
                success.setFooter("Kicked", data.getSelfAvatar(event));

                log.setDescription(mentioned + " has been kicked by " + event.getMember().getAsMention() + "\n\nReason:\n```\nNo reason specified\n```");
                log.setColor(new Color(data.successGreen));
                log.setTimestamp(Instant.now());
                log.setFooter("Kicked Log", data.getSelfAvatar(event));

                event.replyEmbeds(success.build()).queue((msg) -> {
                    success.clear();
                    msg.deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
                    event.getGuild().getTextChannelCache().getElementById(clover.getGuildConfig().get("logChannel")).sendMessageEmbeds(log.build()).queue((msg2) -> {
                        log.clear();
                        event.getOption("member").getAsMember().getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessageEmbeds(kicked.build()).queue();
                            kicked.clear();
                            event.getGuild().kick(event.getOption("member").getAsMember(), "No Reason Specified").queue();
                        });
                    });
                });
            } else if (event.getOptions().size() == 2) {
                String mentioned = event.getOption("member").getAsMember().getAsMention();
                String reason = event.getOption("reason").getAsString();

                kicked.setDescription("You've been kicked from: " + event.getGuild().getName() + "\n\nReason:\n```\n" + reason + "\n```");
                kicked.setColor(new Color(data.successGreen));
                kicked.setTimestamp(Instant.now());
                kicked.setFooter("Kicked", data.getSelfAvatar(event));

                success.setDescription("You've kicked: " + mentioned + " \n\nReason: \n```\n" + reason + "\n```");
                success.setColor(new Color(data.successGreen));
                success.setTimestamp(Instant.now());
                success.setFooter("Kicked", data.getSelfAvatar(event));

                log.setDescription(mentioned + " has been kicked by " + event.getMember().getAsMention() + "\n\nReason:\n```\n" + reason + "\n```");
                log.setColor(new Color(data.successGreen));
                log.setTimestamp(Instant.now());
                log.setFooter("Kicked Log", data.getSelfAvatar(event));

                event.replyEmbeds(success.build()).queue((msg) -> {
                    success.clear();
                    msg.deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
                    event.getGuild().getTextChannelCache().getElementById(clover.getGuildConfig().get("logChannel")).sendMessageEmbeds(log.build()).queue((msg2) -> {
                        log.clear();
                        event.getOption("member").getAsMember().getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessageEmbeds(kicked.build()).queue();
                            kicked.clear();
                            event.getGuild().kick(event.getOption("member").getAsMember(), reason + " | Kicked By " + event.getMember().getUser().getName()).queue();
                        });
                    });
                });
            }
        } else {
            eb.setDescription("Permission `Kick Members` needed to use this command.");
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
