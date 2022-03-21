package org.Clover.Information;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.Clover.Clover;
import org.Clover.Utilities.Data;

import java.awt.*;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Userinfo extends ListenerAdapter {

    private final Clover clover;
    Data data = new Data();

    public Userinfo(Clover clover) {
        this.clover = clover;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("userinfo")) return;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
        String status = event.getMember().getOnlineStatus().toString();
        String emote = data.getInvisibleEmote(event.getGuild());

        if (status == "ONLINE") {
            emote = data.getOnlineEmote(event.getGuild()) + " Online";
        }
        if (status == "IDLE") {
            emote = data.getAwayEmote(event.getGuild()) + " Idle";
        }
        if (status == "DO_NOT_DISTURB") {
            emote = data.getDndEmote(event.getGuild()) + " Do Not Disturb";
        }
        if (status == "INVISIBLE") {
            emote = data.getInvisibleEmote(event.getGuild()) + " Offline";
        }
        if (status == "OFFLINE") {
            emote = data.getInvisibleEmote(event.getGuild()) + " Offline";
        }

        if (event.getOptions().size() == 0) {
            String roles = "";
            roles = event.getMember().getRoles().stream().map((rol) -> ", " + rol.getName()).reduce(roles, String::concat);
            if (roles.isEmpty())
                roles = "None";
            else
                roles = roles.substring(2);

            String game = "";
            try {
                game = event.getMember().getActivities().get(0).getName();
            } catch (IndexOutOfBoundsException e) {
                    game = "Not Playing";
            }

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(new Color(data.successGreen));
            eb.setTimestamp(Instant.now());
            eb.setThumbnail(event.getMember().getUser().getAvatarUrl());
            eb.addField("Username", event.getMember().getUser().getAsTag(), true);
            eb.addField("Nickname", event.getMember().getEffectiveName(), true);
            eb.addField("Status", emote, true);
            eb.addField("Playing", game, true);
            eb.addField("Joined Guild", event.getMember().getTimeJoined().format(dtf), true);
            eb.addField("Joined Discord", event.getMember().getUser().getTimeCreated().format(dtf), true);
            eb.addField("Roles", roles, true);
            eb.setFooter("Userinfo", data.getSelfAvatar(event));

            event.replyEmbeds(eb.build()).queue((msg) -> {
                eb.clear();
                msg.deleteOriginal().queueAfter(2, TimeUnit.MINUTES);
            });
        } else if (event.getOptions().size() == 1) {
            Member mentioned = event.getOption("member").getAsMember();

            String roles = "";
            roles = mentioned.getRoles().stream().map((rol) -> ", " + rol.getName()).reduce(roles, String::concat);
            if (roles.isEmpty())
                roles = "None";
            else
                roles = roles.substring(2);

            String game = "";
            try {
                game = mentioned.getActivities().get(0).getName();
            } catch (IndexOutOfBoundsException e) {
                game = "Not Playing";
            }

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(new Color(data.successGreen));
            eb.setTimestamp(Instant.now());
            eb.setThumbnail(mentioned.getUser().getAvatarUrl());
            eb.addField("Username", mentioned.getUser().getAsTag(), true);
            eb.addField("Nickname", mentioned.getEffectiveName(), true);
            eb.addField("Status", emote, true);
            eb.addField("Playing", game, true);
            eb.addField("Joined Guild", mentioned.getTimeJoined().format(dtf), true);
            eb.addField("Joined Discord", mentioned.getUser().getTimeCreated().format(dtf), true);
            eb.addField("Roles", roles, true);
            eb.setFooter("Userinfo", data.getSelfAvatar(event));

            event.replyEmbeds(eb.build()).queue((msg) -> {
                eb.clear();
                msg.deleteOriginal().queueAfter(2, TimeUnit.MINUTES);
            });
        }
    }
}
