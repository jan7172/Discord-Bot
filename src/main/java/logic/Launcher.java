package logic;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.InterfacedEventManager;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;

public class Launcher extends ListenerAdapter {
    private final static InterfacedEventManager manager = new InterfacedEventManager();
    public static JDA jda;
    private final static String TOKEN = "token";

    private  final static String PREFIX = "~";



    public static void main(String[] args) {
        try {
            manager.register(new Launcher());
            JDABuilder builder = JDABuilder.create(
                    TOKEN,
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.DIRECT_MESSAGES,
                    GatewayIntent.MESSAGE_CONTENT,
                    GatewayIntent.GUILD_MESSAGE_REACTIONS,
                    GatewayIntent.GUILD_MEMBERS);
            builder.setEventManager(manager);
            builder.setEnableShutdownHook(true);
            builder.setActivity(Activity.watching(PREFIX + "help"));
            builder.setStatus(OnlineStatus.ONLINE);
            jda = builder.build();
        } catch (Exception e) {
            System.exit(0);
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        System.out.println("nachricht!");
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String command = args[0].toLowerCase().replaceFirst(PREFIX, "");
        if (event.getMessage().getContentRaw().startsWith(PREFIX)) {
            if(command.equalsIgnoreCase("ping")){
                event.getMessage().reply("Pong!").queue();
            }

        }

    }
}