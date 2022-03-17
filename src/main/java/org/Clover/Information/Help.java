package org.Clover.Information;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.Clover.Clover;
import org.Clover.Utilities.Data;

import java.awt.*;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Help extends ListenerAdapter {

    private final Clover clover;
    public Help(Clover clover){
        this.clover = clover;
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        EmbedBuilder eb = new EmbedBuilder();
        Data data = new Data();

        if(args[0].equalsIgnoreCase(clover.getGuildConfig().get("prefix") + "help")) {
            if(args.length < 2) {

                eb.setColor(new Color(data.getColor()));
                eb.setTimestamp(Instant.now());
                eb.setFooter("Help Menu", data.getSelfAvatar(event));

                event.getChannel().sendMessageEmbeds(eb.build()).queue((message) -> {
                    message.delete().queueAfter(2, TimeUnit.MINUTES);
                    event.getMessage().delete().queueAfter(2, TimeUnit.MINUTES);
                    eb.clear();
                });
            }
        }
    }
}
