package dev.darkhorizon.es.virtualbosses.gui.PerCraft;

import dev.darkhorizon.es.virtualbosses.gui.GUI;
import dev.darkhorizon.es.virtualbosses.items.CommonItems;
import dev.darkhorizon.es.virtualbosses.items.PerCraft.LMItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class LanzaMeteoros implements GUI {

    private final CommonItems citems = CommonItems.getInstance();
    private final LMItems lm_items = LMItems.getInstance();

    public static String title = "§0§l✦ §cLanza Meteoros §0§l✦";

    public void generateInventory(Player p) {
        Inventory inv = Bukkit.createInventory(p, 45, title);

        inv.setItem(1, lm_items.getTiroCertero(p));
        inv.setItem(2, lm_items.getEssencia(p));
        inv.setItem(3, lm_items.getKoth(p));
        inv.setItem(4, lm_items.getString(p));
        inv.setItem(5, lm_items.getStick(p));
        inv.setItem(6, citems.getEstrellaCaida(8));
        inv.setItem(7, citems.getLibroMaestro(5));


        inv.setItem(0, citems.getSeparator(15));
        for (int i = 8; i < 19; i++) {
            inv.setItem(i, citems.getSeparator(15));
        }
        for (int i = 26; i < 45; i++) {
            inv.setItem(i, citems.getSeparator(15));
        }

        inv.setItem(40, citems.getCraft());

        p.openInventory(inv);
    }
}