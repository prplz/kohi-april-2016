package net.kohi.april.swords;

import net.kohi.kspigot.event.packet.PacketSendEvent;
import net.minecraft.server.v1_7_R4.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SwordsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!new SimpleDateFormat("MM/dd").format(new Date()).equals("04/01")) return;
        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPacketSend(PacketSendEvent event) {
                if (event.getPacket() instanceof PacketPlayOutWindowItems) {
                    PacketPlayOutWindowItems packet = (PacketPlayOutWindowItems) event.getPacket();
                    for (ItemStack item : packet.getItems()) {
                        fuck(item);
                    }
                } else if (event.getPacket() instanceof PacketPlayOutSetSlot) {
                    PacketPlayOutSetSlot packet = (PacketPlayOutSetSlot) event.getPacket();
                    fuck(packet.getItemStack());
                } else if (event.getPacket() instanceof PacketPlayOutEntityEquipment) {
                    PacketPlayOutEntityEquipment packet = (PacketPlayOutEntityEquipment) event.getPacket();
                    if (packet.getSlot() == 0) {
                        fuck(packet.getItemStack());
                    }
                }
            }
        }, this);
    }

    private void fuck(ItemStack item) {
        if (item != null && item.getItem() == Items.DIAMOND_SWORD) {
            item.setItem(Items.WOOD_SWORD);
            item.setData(0);
            item.addEnchantment(Enchantment.DURABILITY, 10);
        }
    }
}
