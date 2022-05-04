package org.Mikoto;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.Mikoto.Information.*;
import org.Mikoto.Moderation.*;
import org.Mikoto.Utilities.*;
import org.Mikoto.Wholesome.*;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Mikoto {

    private final Config config;
    private final JDABuilder Mikoto;

    private Mikoto() throws LoginException {
        config = new Config(this);
        config.load();

        Mikoto = JDABuilder.createDefault(getConfig().get("token"));
        Mikoto.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES);
        Mikoto.setMemberCachePolicy(MemberCachePolicy.ALL);
        Mikoto.enableCache(CacheFlag.ACTIVITY, CacheFlag.ONLINE_STATUS);
        Mikoto.setActivity(Activity.watching("Noragami"));
        Mikoto.setStatus(OnlineStatus.DO_NOT_DISTURB);

        Mikoto.addEventListeners(
                // Information Commands
                new Userinfo(this),

                // Moderation Commands
                new Clear(this),
                new Kick(this),
                new Ban(this),
                new Unmute(this),
                new Mute(this),

                //Wholesome
                new Hug(this),

                //Utilities
                new Ready()
        );

        Mikoto.build();
    }

    public static void main(String[] args) throws LoginException {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
        new Mikoto();
    }

    public Config getConfig(){
        return config;
    }
}
