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

public class Mute extends ListenerAdapter {

    private final Mikoto mikoto;
    Data data = new Data();
    EmbedBuilder eb = new EmbedBuilder();
    EmbedBuilder muted = new EmbedBuilder();
    EmbedBuilder log = new EmbedBuilder();
    EmbedBuilder success = new EmbedBuilder();

    public Mute(Mikoto mikoto) {
        this.mikoto = mikoto;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("mute")) return;
        if (event.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS)) {
            if (event.getOptions().size() == 1) {
                String mentioned = event.getOption("member").getAsMember().getAsMention();

                muted.setDescription("You've been muted on: " + event.getGuild().getName() + "\n\nReason: \n```\nNo reason specified\n```");
                muted.setColor(new Color(data.successGreen));
                muted.setTimestamp(Instant.now());
                muted.setFooter("Muted", data.getSelfAvatar(event));

                success.setDescription("You've muted: " + mentioned + "\n\nReason:\n```\nNo reason specified\n```");
                success.setColor(new Color(data.successGreen));
                success.setTimestamp(Instant.now());
                success.setFooter("Muted", data.getSelfAvatar(event));

                log.setDescription(event.getMember().getAsMention() + " muted " + mentioned + "\n\nReason: \n```No reason specified\n```");
                log.setColor(new Color(data.successGreen));
                log.setTimestamp(Instant.now());
                log.setFooter("Muted Log", data.getSelfAvatar(event));

                event.replyEmbeds(success.build()).queue((msg) -> {
                    success.clear();
                    msg.deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
                    event.getGuild().getTextChannelCache().getElementById(mikoto.getConfig().get("logChannel")).sendMessageEmbeds(log.build()).queue((msg2) -> {
                        log.clear();
                        event.getOption("member").getAsMember().getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessageEmbeds(muted.build()).queue();
                            muted.clear();
                            event.getGuild().addRoleToMember(event.getOption("member").getAsMember(), event.getGuild().getRolesByName("Muted", true).get(0)).queue();
                        });
                    });
                });
            } else if (event.getOptions().size() == 2) {
                String mentioned = event.getOption("member").getAsMember().getAsMention();
                String reason = event.getOption("reason").getAsString();

                muted.setDescription("You've been muted on: " + event.getGuild().getName() + "\n\nReason:\n```\n" + reason + "\n```");
                muted.setColor(new Color(data.successGreen));
                muted.setTimestamp(Instant.now());
                muted.setFooter("Muted", data.getSelfAvatar(event));

                success.setDescription("You've muted: " + mentioned + " \n\nReason: \n```\n" + reason + "\n```");
                success.setColor(new Color(data.successGreen));
                success.setTimestamp(Instant.now());
                success.setFooter("Muted", data.getSelfAvatar(event));

                log.setDescription(mentioned + " has been muted by " + event.getMember().getAsMention() + "\n\nReason:\n```\n" + reason + "\n```");
                log.setColor(new Color(data.successGreen));
                log.setTimestamp(Instant.now());
                log.setFooter("Muted Log", data.getSelfAvatar(event));

                event.replyEmbeds(success.build()).queue((msg) -> {
                    success.clear();
                    msg.deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
                    event.getGuild().getTextChannelCache().getElementById(mikoto.getConfig().get("logChannel")).sendMessageEmbeds(log.build()).queue((msg2) -> {
                        log.clear();
                        event.getOption("member").getAsMember().getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessageEmbeds(muted.build()).queue();
                            muted.clear();
                            event.getGuild().addRoleToMember(event.getOption("member").getAsMember(), event.getGuild().getRolesByName("Muted", true).get(0)).queue();
                        });
                    });
                });
            }
        } else {
            eb.setDescription("Permission `Mute Members` needed to use this command.");
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
