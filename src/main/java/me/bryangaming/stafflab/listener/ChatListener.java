package me.bryangaming.stafflab.listener;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.builder.ReplaceableBuilder;
import me.bryangaming.stafflab.managers.SenderManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private SenderManager senderManager;

    public ChatListener(PluginCore pluginCore){
        this.senderManager = pluginCore.getManagers().getSenderManager();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){

        if (event.getPlayer().hasMetadata("freeze")){
            event.setCancelled(true);
            return;
        }

        if (event.getPlayer().hasMetadata("staffchat")) {
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                if (senderManager.hasPermission(onlinePlayer, "watch.staffchat")) {
                    return;
                }


                senderManager.sendMessage(onlinePlayer, "staffchat.format",
                        ReplaceableBuilder.create("%player%", event.getPlayer().getName()),
                        ReplaceableBuilder.create("%message%", String.join(" ", event.getMessage())));
            });
        }
    }
}
