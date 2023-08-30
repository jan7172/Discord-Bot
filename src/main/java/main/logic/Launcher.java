package main.logic;

import main.data.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.InterfacedEventManager;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;


import java.awt.*;
import java.io.*;

public class Launcher extends ListenerAdapter {
    private final static InterfacedEventManager manager = new InterfacedEventManager();
    public static JDA jda;
    private final static String TOKEN = "ODQwMzAxOTI0ODc5MzAyNjg3.GIvaI4.U78kVKbY2P0OoWAlMRcY14DME5Q45wikLevN-0";

    private  final static String PREFIX = "-";
    private final static String MODPREFIX = "-getmod";
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

    private final static String FS = System.getProperty("file.separator");

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        try {
            System.out.println("Message !");
            String hvvMapVar = "HVV: Kamino > Mos Eisley > Naboo > Hoth > Takodana > Death Star II > Yavin 4 > Starkiller Base > Endor > Kashyyyk > Jakku > Bespin > Jabba's Palace > Kessel > Geonosis > Separatist Dreadnought > Republic Attack Cruiser > Felucia > Ajan Kloss\n";
            String hsMapVar = "Hero Showdown: Kamino > Mos Eisley > Naboo > Hoth > Takodana > Death Star II > Yavin 4 > Starkiller Base > Endor > Kashyyyk > Jakku > Bespin > Jabba's Palace > Kessel > Geonosis";
            String gaMapVar = "GA: Geonosis > Kashyyyk > Death Star 2 > Jakku > Endor > Kamino > Tatooine > Starkiller Base > Yavin 4 > Naboo > Hoth > Takodana > Crait\n";
            String modList = "noerror";
            File noError = new File("Mods" + FS + "No_Error_Popup.fbmod");
            File gmi = new File("Mods" + FS + "gmiV2_0930.fbmod");

            String[] args = event.getMessage().getContentRaw().split("\\s+");
            String command = args[0].toLowerCase().replaceFirst(PREFIX, "").toLowerCase();

            if (event.getMessage().getContentRaw().startsWith(PREFIX)) {
                if (command.equals("rotationhvv")) {
                    event.getMessage().reply(hvvMapVar).queue();
                } else if (command.equals("rotationhs")) {
                    event.getMessage().reply(hsMapVar).queue();
                } else if (command.equals("rotationga")) {
                    event.getMessage().reply(gaMapVar).queue();
                } else if (command.equals("modlist")) {
                    event.getMessage().replyEmbeds(getModListBuilder().build()).queue();
                } else if (event.getMessage().getContentRaw().startsWith(MODPREFIX)) {
                    if (args.length <= 1) {
                        event.getMessage().replyEmbeds(getModListBuilder().build()).queue();
                        //event.getMessage().reply("Please enter a parameter").queue();
                        return;
                    }
                    String modCommand = args[1].toLowerCase();
                    if (modCommand.equals("noerror")) {
                        event.getMessage().reply("Hier die Datei:").addFiles(FileUpload.fromData(noError)).queue();
                    } else if (modCommand.equals("gmi")) {
                        event.getMessage().reply("Hier die Datei").addFiles(FileUpload.fromData(gmi)).queue();
                    } else {
                        event.getMessage().replyEmbeds(getModListBuilder().build()).queue();
                    }
                }
                else if (command.equals("help")) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setTitle("R2D2 Bot");
                    embedBuilder.setColor(Color.RED);
                    embedBuilder.setDescription("R2D2 is a bot for Battlefront fans. You can use following commands:");
                    embedBuilder.addField("-rotationga", "Shows the map rotation for Galactic Assault", true);
                    embedBuilder.addField("-rotationhvv", "Shows the map rotation for Hero VS Villains", true);
                    embedBuilder.addField("-rotationhs", "Shows the map rotation for Hero Showdown", true);
                    embedBuilder.addField("-modlist", "Send you a list with all mods R2D2 can send you", true);
                    embedBuilder.addField("-getmod", "Send you a specific mod file for Battlefront 2.", true);

                    event.getMessage().replyEmbeds(embedBuilder.build()).queue();
                }
            }
        }
        catch (Exception e)
        {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Error");
            builder.setColor(Color.red);
            builder.getDescriptionBuilder().append(e);
            for (StackTraceElement element : e.getStackTrace()) {
                builder.getDescriptionBuilder().append("\r\n\tat ").append(element);
            }
            builder.setFooter("Error");
            event.getMessage().replyEmbeds(builder.build()).queue();
        }
    }

    private EmbedBuilder getModListBuilder() throws IOException {
        FileReader fileReader = new FileReader("Mods/mods.json");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String jsonString = "";
        while (bufferedReader.ready()) {
            jsonString += bufferedReader.readLine();
        }
        JSONArray mods = new JSONArray(jsonString);

        EmbedBuilder modEmbedBuilder = new EmbedBuilder();
        modEmbedBuilder.setTitle("List of all mods");
        modEmbedBuilder.setColor(Color.RED);
        //modEmbedBuilder.setDescription("List of all mods R2 can send you:");
        for (int i = 0; i < mods.length(); i++) {
            JSONObject modJson = mods.getJSONObject(i);
            modEmbedBuilder.addField(modJson.getString("displayName"), modJson.getString("description"), true);
        }
        return modEmbedBuilder;
    }
}