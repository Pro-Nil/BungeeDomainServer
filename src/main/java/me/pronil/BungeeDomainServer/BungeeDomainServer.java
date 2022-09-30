package me.pronil.BungeeDomainServer;


import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;

import java.util.Arrays;


import java.util.HashMap;



public class BungeeDomainServer extends Plugin implements Listener {


private String[] server;

    public static HashMap<String, String[]> serverMap = new HashMap<>();


    @Override
    public void onEnable() {
        PluginManager pm = getProxy().getPluginManager();
        pm.registerListener(this, this);
    }











    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        String domain = event.getConnection().getVirtualHost().getHostName().toLowerCase();
        serverMap.put(event.getConnection().getName(), domain.split("\\.", 2));

    }

    @EventHandler
    public void onServerConnect(final ServerConnectEvent event) {
        if (serverMap.containsKey(event.getPlayer().getName())) {
            final ServerInfo target = ProxyServer.getInstance().getServerInfo(serverMap.get(event.getPlayer().getName())[0]);
            if (getProxy().getServers().containsKey(serverMap.get(event.getPlayer().getName())[0])) {
                event.setTarget(target);
                getProxy().getServers().get(serverMap.get(event.getPlayer().getName())[0]).ping(new Callback<ServerPing>() {
                    @Override
                    public void done(ServerPing result, Throwable error) {
                        if (error != null) {
                            ProxyServer.getInstance().getConsole().sendMessage("Couldn't connect " + event.getPlayer().getName() + " to " + serverMap.get(event.getPlayer().getName())[0] + " as the server is offline");
                        } else {
                            ProxyServer.getInstance().getConsole().sendMessage("Connected " + event.getPlayer().getName() + " to " + serverMap.get(event.getPlayer().getName())[0]);
                            serverMap.remove(event.getPlayer().getName());
                            event.setCancelled(true);
                        }
                    }
                });
            } else {
                ProxyServer.getInstance().getConsole().sendMessage("Could not connect " + event.getPlayer().getName() + " to " + serverMap.get(event.getPlayer().getName())[0] + " as the server does not exist in BungeeCord Config.yml");
           }
        }
    }

}
