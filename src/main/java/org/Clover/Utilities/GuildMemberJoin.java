package org.Clover.Utilities;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class GuildMemberJoin extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        Data data = new Data();

        eb.setTitle(":wave: Welcome");
        eb.setThumbnail(event.getMember().getUser().getAvatarUrl());
        eb.setDescription("Hey " + event.getMember().getAsMention() + " welcome to **" + event.getGuild().getName() + "!**\n\n" +
                "Please keep up to date with " + event.getGuild().getGuildChannelById("950264276541648896").getAsMention() + "\n" +
                "You can chat and make news friends in " + event.getGuild().getGuildChannelById("916886277587079213").getAsMention() + "\n" +
                "Hope you enjoy your stay with us! :heart:");
        eb.setColor(new Color(data.successGreen));

        event.getGuild().getTextChannelById("955244017153626122").sendMessageEmbeds(eb.build()).queue((message1) -> {
            eb.clear();
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("955233320130736178")).queue();
        });
    }
}
