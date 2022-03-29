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

public class Unmute extends ListenerAdapter {

    private final Clover clover;
    Data data = new Data();
    EmbedBuilder eb = new EmbedBuilder();
    EmbedBuilder unmuted = new EmbedBuilder();
    EmbedBuilder log = new EmbedBuilder();
    EmbedBuilder success = new EmbedBuilder();

    public Unmute(Clover clover) {
        this.clover = clover;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("unmute")) return;
        if (event.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS)) {
            if (event.getOptions().size() == 1) {
                String mentioned = event.getOption("member").getAsMember().getAsMention();

                unmuted.setDescription("You've been unmuted on: " + event.getGuild().getName());
                unmuted.setColor(new Color(data.successGreen));
                unmuted.setTimestamp(Instant.now());
                unmuted.setFooter("Unmuted", data.getSelfAvatar(event));

                success.setDescription("You've unmuted: " + mentioned);
                success.setColor(new Color(data.successGreen));
                success.setTimestamp(Instant.now());
                success.setFooter("Unmuted", data.getSelfAvatar(event));

                log.setDescription(event.getMember().getAsMention() + " unmuted " + mentioned);
                log.setColor(new Color(data.successGreen));
                log.setTimestamp(Instant.now());
                log.setFooter("Unmuted Log", data.getSelfAvatar(event));

                event.replyEmbeds(success.build()).queue((msg) -> {
                    success.clear();
                    msg.deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
                    event.getGuild().getTextChannelCache().getElementById(clover.getGuildConfig().get("logChannel")).sendMessageEmbeds(log.build()).queue((msg2) -> {
                        log.clear();
                        event.getOption("member").getAsMember().getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessageEmbeds(unmuted.build()).queue();
                            unmuted.clear();
                            event.getGuild().removeRoleFromMember(event.getOption("member").getAsMember(), event.getGuild().getRolesByName("Muted", true).get(0)).queue();
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
