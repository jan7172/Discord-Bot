package main.logic;

import main.data.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.InterfacedEventManager;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Launcher extends ListenerAdapter {
    private final static InterfacedEventManager manager = new InterfacedEventManager();
    public static JDA jda;
    private final static String TOKEN = "ODM1MjY5Nzk1NzY2OTkyOTA3.GBdp7c.uSoDkN9pDC5H93ylrINU5RmQITAfutiOUpMJ2o";

    private  final static String PREFIX = "-";
    private final static Database database = Database.getInstance();



    public static void main(String[] args) {
        try {
            database.initConnection();
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
        System.out.println("Message !");
        String hvvMapVar = "HVV: Kamino > Mos Eisley > Naboo > Hoth > Takodana > Death Star II > Yavin 4 > Starkiller Base > Endor > Kashyyyk > Jakku > Bespin > Jabba's Palace > Kessel > Geonosis > Separatist Dreadnought > Republic Attack Cruiser > Felucia > Ajan Kloss\n";
        String hsMapVar = "Hero Showdown: Kamino > Mos Eisley > Naboo > Hoth > Takodana > Death Star II > Yavin 4 > Starkiller Base > Endor > Kashyyyk > Jakku > Bespin > Jabba's Palace > Kessel > Geonosis";
        String gaMapVar = "GA: Geonosis > Kashyyyk > Death Star 2 > Jakku > Endor > Kamino > Tatooine > Starkiller Base > Yavin 4 > Naboo > Hoth > Takodana > Crait\n";
        String helpVar = "This Answer will help you soon";
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String command = args[0].toLowerCase().replaceFirst(PREFIX, "").toLowerCase();
        if (event.getMessage().getContentRaw().startsWith(PREFIX)) {
            if(command.equals("rotationhvv")){
                event.getMessage().reply(hvvMapVar).queue();
            } else if(command.equals("rotationhs")){
                event.getMessage().reply(hsMapVar).queue();
            } else if(command.equals("rotationga")) {
                event.getMessage().reply(gaMapVar).queue();
            } else if(command.equals("help")) {
                event.getMessage().reply(helpVar).queue();
            } else if(command.equals("embed")) {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Test");
                embedBuilder.setColor(Color.RED);
                embedBuilder.setDescription("Das hier ist eine Beschreibung");
                embedBuilder.addField("test über", "asdasdjashdkjsa", true);
                embedBuilder.addField("test über", "asdasdjashdkjsa", true);
                embedBuilder.addField("test über", "asdasdjashdkjsa", true);
                embedBuilder.addField("test über", "asdasdjashdkjsa", true);
                event.getMessage().replyEmbeds(embedBuilder.build()).queue();
            }
        }

    }
}