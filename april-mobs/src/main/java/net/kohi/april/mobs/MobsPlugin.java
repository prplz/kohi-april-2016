package net.kohi.april.mobs;

import net.kohi.kspigot.event.packet.PacketSendEvent;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_7_R4.WatchableObject;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MobsPlugin extends JavaPlugin {

    private final List<WatchableObject> boner = Arrays.asList(new WatchableObject(4, 2, "Dinnerbone"), new WatchableObject(4, 10, "Dinnerbone"));

    @Override
    public void onEnable() {
        if (!new SimpleDateFormat("MM/dd").format(new Date()).equals("04/01")) return;
        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPacketSend(PacketSendEvent event) {
                if (event.getPacket() instanceof PacketPlayOutSpawnEntityLiving) {
                    PacketPlayOutSpawnEntityLiving packet = (PacketPlayOutSpawnEntityLiving) event.getPacket();
                    if (packet.getEntityType() == PacketPlayOutSpawnEntityLiving.ENDERMAN ||
                            packet.getEntityType() == PacketPlayOutSpawnEntityLiving.CREEPER) {
                        Bukkit.getScheduler().runTaskLater(MobsPlugin.this, () -> {
                            PacketPlayOutEntityMetadata metaPacket = new PacketPlayOutEntityMetadata();
                            metaPacket.setEntityId(packet.getEntityId());
                            metaPacket.setMetadata(boner);
                            event.getPlayer().sendPacket(metaPacket);
                        }, 1);
                    }
                }
            }
        }, this);
    }
}
