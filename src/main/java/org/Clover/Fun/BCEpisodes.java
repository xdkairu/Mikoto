package org.Clover.Fun;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.Clover.Clover;
import org.Clover.Utilities.Data;
import org.Clover.Utilities.Episodes;

import java.awt.*;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class BCEpisodes extends ListenerAdapter {

    private final Clover clover;
    Data data = new Data();
    EmbedBuilder eb = new EmbedBuilder();

    public BCEpisodes(Clover clover) {
        this.clover = clover;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("blackclover")) return;
        if (event.getOptions().size() == 1) {

            eb.setThumbnail("https://i.imgur.com/WLKozg4.jpeg");
            eb.setDescription(String.format("**Title**: `%s`\n**Episode**: `%s`\n**Link**: [MyAnimeList](%s, \"Black Clover Episode Link\")", clover.getEpisodes().getTitle(event.getOption("episode").getAsString()), event.getOption("episode").getAsString(), clover.getEpisodes().getUrl(event.getOption("episode").getAsString())));
            eb.setColor(new Color(data.successGreen));
            eb.setTimestamp(Instant.now());
            eb.setFooter("Black Clover Episode", data.getSelfAvatar(event));

            event.replyEmbeds(eb.build()).queue((msg) -> {
                eb.clear();
                msg.deleteOriginal().queueAfter(2, TimeUnit.MINUTES);
            });
        }
    }
}
