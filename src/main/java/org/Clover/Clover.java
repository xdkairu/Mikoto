package org.Clover;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.Clover.Information.Help;
import org.Clover.Moderation.Clear;
import org.Clover.Utilities.Config;
import org.Clover.Utilities.Database;

import javax.security.auth.login.LoginException;

public class Clover {

    private final Config config;
    private final Database database;
    private final JDABuilder clover;

    private Clover() throws LoginException {
        config = new Config(this);
        config.load();
        this.database = new Database(this);
        database.connect();

        clover = JDABuilder.createDefault(getConfig().get("token"));
        clover.enableIntents(GatewayIntent.GUILD_MEMBERS);
        clover.setMemberCachePolicy(MemberCachePolicy.ALL);
        clover.setActivity(Activity.competing("Wizard King"));
        clover.setStatus(OnlineStatus.DO_NOT_DISTURB);

        clover.addEventListeners(
                new Clear(),
                new Help()
        );

        clover.build();
    }

    public static void main(String[] args) throws LoginException {
        new Clover();
    }

    public Database getDatabase(){
        return database;
    }

    public Config getConfig(){
        return config;
    }

}
