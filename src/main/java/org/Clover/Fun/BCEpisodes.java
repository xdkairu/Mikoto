package org.Clover.Fun;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.Clover.Clover;
import org.Clover.Utilities.Data;

import java.awt.*;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class BCEpisodes extends ListenerAdapter {

    private static final String MESSAGE = "**Title**: `%s`\n**Episode**: `%s`\n**Link**: [MyAnimeList](%s \"Black Clover Episode Link\")";
    private static final String ERROR_MESSAGE = "Invalid episode number!";
    private final Clover clover;
    Data data = new Data();

    public BCEpisodes(Clover clover) {
        this.clover = clover;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("blackclover") || event.getOptions().size() != 1) {
            return;
        }

        int number;
        try {
            number = event.getOption("episode").getAsInt();
        } catch (NullPointerException e) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setDescription("`" + event.getOption("episode") + "` is not an episode from Black Clover");
            eb.setColor(new Color(Data.failedRed));
            eb.setTimestamp(Instant.now());
            eb.setFooter("Invalid Episode Number", data.getSelfAvatar(event));

            event.replyEmbeds(eb.build()).queue((msg) -> {
                eb.clear();
                msg.deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
            });
            return;
        }

        var episode = clover.getEpisodes().get(number);

        if (episode == null) {
            event.reply(ERROR_MESSAGE).setEphemeral(true).queue();
            return;
        }

        var embed = new EmbedBuilder();
        embed.setThumbnail("https://i.imgur.com/WLKozg4.jpeg");
        embed.setDescription(String.format(MESSAGE, episode.name(), number, episode.url()));
        embed.setColor(new Color(data.successGreen));
        embed.setTimestamp(Instant.now());
        embed.setFooter("Black Clover Episode", data.getSelfAvatar(event));

        event.replyEmbeds(embed.build()).queue((msg) -> {
            embed.clear();
            msg.deleteOriginal().queueAfter(2, TimeUnit.MINUTES);
        });
    }
}
