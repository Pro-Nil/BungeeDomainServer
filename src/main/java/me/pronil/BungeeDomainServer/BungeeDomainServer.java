package me.pronil.BungeeDomainServer;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;

import java.util.Arrays;


public class BungeeDomainServer extends Plugin implements Listener {

private String[] server;

    @Override
    public void onEnable() {
        PluginManager pm = getProxy().getPluginManager();
        pm.registerListener(this, this);
    }



    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        String domain = event.getConnection().getVirtualHost().getHostName().toLowerCase();
        server = domain.split("\\.",2);

    }
    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {

        ServerInfo target = ProxyServer.getInstance().getServerInfo(server[0]);
        event.setTarget(target);

    }




}
