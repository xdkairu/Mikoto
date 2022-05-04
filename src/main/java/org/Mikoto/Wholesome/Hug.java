package org.Mikoto.Wholesome;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.Mikoto.Mikoto;
import org.Mikoto.Utilities.Data;

import java.awt.*;
import java.time.Instant;
import java.util.Random;

public class Hug extends ListenerAdapter {

    private final Mikoto mikoto;
    Data data = new Data();
    EmbedBuilder eb = new EmbedBuilder();

    String[] images = {
            "https://i.imgur.com/R8TWjFy.gif",
            "https://i.imgur.com/sYoYQ9q.gif",
            "https://i.imgur.com/ePMOR3R.gif",
            "https://i.imgur.com/iHNej6L.gif",
            "https://i.imgur.com/VRcglCR.gif",
            "https://i.imgur.com/aBpkK7x.gif",
            "https://i.imgur.com/xQudAlA.gif",
            "https://i.imgur.com/3trtPPo.gif",
            "https://i.imgur.com/TikwgIF.gif",
            "https://i.imgur.com/Cd4E0FF.gif",
            "https://i.imgur.com/44rFDFH.gif",
            "https://i.imgur.com/bNNXdrk.gif",
            "https://i.imgur.com/e2uA9C1.gif",
            "https://i.imgur.com/IJx0HdC.gif",
            "https://i.imgur.com/3nk9hka.gif",
            "https://i.imgur.com/VYJUSJL.gif",
            "https://i.imgur.com/SpHj56t.gif",
            "https://i.imgur.com/BYqstBh.gif"
    };

    Random random = new Random();
    int image = random.nextInt(images.length);

    public Hug(Mikoto mikoto) {
        this.mikoto = mikoto;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("hug")) return;
        if (event.getOptions().size() == 0) {
            eb.setDescription("You hugged yourself, you fr lonely.");
            eb.setImage(images[image]);
            eb.setColor(new Color(data.successGreen));
            eb.setTimestamp(Instant.now());
            eb.setFooter("Hug", data.getSelfAvatar(event));

            event.replyEmbeds(eb.build()).queue();

        } else if (event.getOptions().size() == 1) {
            Member mentioned = event.getOption("member").getAsMember();

            eb.setDescription("You hugged **" + mentioned.getAsMention() + "**");
            eb.setImage(images[image]);
            eb.setColor(new Color(data.successGreen));
            eb.setTimestamp(Instant.now());
            eb.setFooter("Hug", data.getSelfAvatar(event));

            event.replyEmbeds(eb.build()).queue();
        }
    }
}
