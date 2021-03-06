package dev.darkhorizon.es.virtualbosses.bosses.entities;

import dev.darkhorizon.es.virtualbosses.data.temp.TempData;
import dev.darkhorizon.es.virtualbosses.Main;
import dev.darkhorizon.es.virtualbosses.bosses.CustomBoss;
import dev.darkhorizon.es.virtualbosses.listeners.boss.BossSpawn;
import dev.darkhorizon.es.virtualbosses.utils.BossUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DamnedSkeleton implements CustomBoss<DamnedSkeleton> {

    private static final Main plugin = Main.getInstance();
    private static final TempData temp_data = TempData.getInstance();

    public static String name = "§f§lEsqueleto Maldito";
    public static int health = 350;

    /**
     * Method to generate the boss
     * @param loc Where the boss is generated
     */
    public DamnedSkeleton(@NotNull Location loc) {
        if (!loc.getWorld().isChunkLoaded(loc.getChunk())) {
            loc.getWorld().loadChunk(loc.getChunk());
        }
        Skeleton entity = loc.getWorld().spawn(loc, Skeleton.class);
        entity.setCustomName(name);
        entity.setCustomNameVisible(true);
        entity.setSkeletonType(Skeleton.SkeletonType.WITHER);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 2), true);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 8), true);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 4), true);
        entity.setMetadata("DamnedSkeleton", new FixedMetadataValue(plugin, "damned_skeleton"));
        entity.getEquipment().setHelmet(generateHelmet());
        entity.getEquipment().setChestplate(generateChestPlate());
        entity.getEquipment().setLeggings(generateLeggings());
        entity.getEquipment().setBoots(generateBoots());
        entity.getEquipment().setItemInHand(generateWeapon());
        entity.setMaxHealth(health);
        entity.setHealth(health);
        // CALL EVENT
        //TODO WEAPON
        temp_data.getEntities().put(entity.getUniqueId(), entity);
        Bukkit.getPluginManager().callEvent(new BossSpawn(entity));
    }

    @NotNull
    private ItemStack generateWeapon() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 12);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 7);
        item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 4);
        return item;
    }

    @NotNull
    private ItemStack generateHelmet() {
        ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 7);
        return item;
    }

    @NotNull
    private ItemStack generateChestPlate() {
        ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 7);
        return item;
    }

    @NotNull
    private ItemStack generateLeggings() {
        ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 7);
        return item;
    }

    @NotNull
    private ItemStack generateBoots() {
        ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 7);
        return item;
    }

    /**
     * Method to manage Boss skills
     * @param entity The boss
     */
    public static void playSkill(LivingEntity entity) {
        if (BossUtils.getChance(30)) {
            BossUtils.updateTarget((Skeleton) entity);
        }

    }

}
