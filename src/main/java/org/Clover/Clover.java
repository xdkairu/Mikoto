package org.Clover;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.Clover.Information.*;
import org.Clover.Moderation.*;
import org.Clover.Utilities.*;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Clover {

    private final Config config;
    private final GuildConfig guildConfig;
    private final Database database;
    private final JDABuilder clover;

    private Clover() throws LoginException {
        config = new Config(this);
        config.load();
        this.database = new Database(this);
        database.connect();
        guildConfig = new GuildConfig(this);
        guildConfig.load();

        clover = JDABuilder.createDefault(getConfig().get("token"));
        clover.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES);
        clover.setMemberCachePolicy(MemberCachePolicy.ALL);
        clover.enableCache(CacheFlag.ACTIVITY, CacheFlag.ONLINE_STATUS);
        clover.setActivity(Activity.playing("with my food"));
        clover.setStatus(OnlineStatus.DO_NOT_DISTURB);

        clover.addEventListeners(
                // Information Commands
                new Userinfo(this),

                // Moderation Commands
                new Clear(this),
                new Kick(this),
                new Ban(this),
//                new Unmute(this),
//                new Tempmute(this),
//                new Mute(this)

                //Utilities
                new Ready()
        );

        clover.build();
    }

    public static void main(String[] args) throws LoginException {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
        new Clover();
    }

    public Database getDatabase(){
        return database;
    }

    public Config getConfig(){
        return config;
    }

    public GuildConfig getGuildConfig() { return guildConfig;}

}
