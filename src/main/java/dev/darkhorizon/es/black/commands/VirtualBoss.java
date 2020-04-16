package dev.darkhorizon.es.black.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.ChunkCoordIntPair;
import com.comphenix.protocol.wrappers.MultiBlockChangeInfo;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import dev.darkhorizon.es.black.bosses.entities.*;
import dev.darkhorizon.es.black.data.temp.TempData;
import dev.darkhorizon.es.black.Main;
import dev.darkhorizon.es.black.bosses.CustomBoss;
import dev.darkhorizon.es.black.config.Lang;
import dev.darkhorizon.es.black.config.Perms;
import dev.darkhorizon.es.black.gui.BossList;
import dev.darkhorizon.es.black.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import java.lang.reflect.InvocationTargetException;

public class VirtualBoss implements CommandExecutor {

    private static final Main plugin = Main.getPlugin(Main.class);
    private static final TempData temp_data = TempData.getInstance();
    private static final Lang lang = Lang.getInstance();
    private static final Perms perms = Perms.getInstance();


    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            this.manageCommand((Player) commandSender, strings);
        } else {
            commandSender.sendMessage("¡Este commando solo se puede ejecutar via usuario!");
        }
        return true;
    }

    private void manageCommand(Player launcher, String[] args) {
        if (args.length == 0) {
            launcher.sendMessage("/virtualboss time");
            launcher.sendMessage("/virtualboss list");
            if (launcher.isOp()) {
                launcher.sendMessage("/virtualboss spawn mob");
                launcher.sendMessage("/virtualboss killall");
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("time")) {
                launcher.sendMessage("UNIMPLEMENTED");
                return;
            }
            if (args[0].equalsIgnoreCase("list")) {
                GUI gui = new BossList();
                gui.generateInventory(launcher);
                return;
            }
            if (args[0].equalsIgnoreCase("killall") && launcher.hasPermission(perms.vb_kill)) {
                int count = 0;
                while (!temp_data.getEntities().isEmpty() && count < 1000) {
                    try {
                        for (LivingEntity e : temp_data.getEntities().values()) {
                            temp_data.getEntities().remove(e.getUniqueId());
                            if (temp_data.getBoss_damagers().get(e.getUniqueId()) != null) {
                                temp_data.getBoss_damagers().remove(e.getUniqueId());
                            }
                            e.remove();
                            count++;
                        }
                    } catch (Exception ignored) {
                    }

                }
                launcher.sendMessage("§cSe han eliminado " + count + " entidades");
                return;
            }
            if (args[0].equalsIgnoreCase("spawn") && launcher.hasPermission(perms.vb_spawn)) {
                launcher.sendMessage("Bosses Disponibles");
                launcher.sendMessage("/virtualboss spawn creeper");
                launcher.sendMessage("/virtualboss spawn reyzombie");
                launcher.sendMessage("/virtualboss spawn invocator");
                launcher.sendMessage("/virtualboss spawn golem");
                launcher.sendMessage("/virtualboss spawn skeleton");
                return;
            }

        } else {
            if (args[0].equals("spawn")) {
                if (args[1].equalsIgnoreCase("creeper") && launcher.hasPermission(perms.vb_spawn)) {
                    CustomBoss<ExplosiveCreeper> boss = new ExplosiveCreeper(launcher.getLocation());
                    launcher.sendMessage("Has creado un Boss" + args[1] + "en tu localizacion.");
                    return;
                }
                if (args[1].equalsIgnoreCase("reyzombie") && launcher.hasPermission(perms.vb_spawn)) {
                    CustomBoss<ZombieKing> boss = new ZombieKing(launcher.getLocation());
                    launcher.sendMessage("Has creado un Boss " + args[1] + "en tu localizacion.");
                    return;
                }
                if (args[1].equalsIgnoreCase("skeleton") && launcher.hasPermission(perms.vb_spawn)) {
                    CustomBoss<DamnedSkeleton> boss = new DamnedSkeleton(launcher.getLocation());
                    launcher.sendMessage("Has creado un Boss " + args[1] + "en tu localizacion.");
                    return;
                }
                if (args[1].equalsIgnoreCase("invocator") && launcher.hasPermission(perms.vb_spawn)) {
                    CustomBoss<Invocator> boss = new Invocator(launcher.getLocation());
                    launcher.sendMessage("Has creado un Boss " + args[1] + "en tu localizacion.");
                    return;
                }
                if (args[1].equalsIgnoreCase("golem") && launcher.hasPermission(perms.vb_spawn)) {
                    CustomBoss<ArmoredGolem> boss = new ArmoredGolem(launcher.getLocation());
                    launcher.sendMessage("Has creado un Boss " + args[1] + " en tu localizacion.");
                    return;
                }
                if (args[1].equalsIgnoreCase("test2") && launcher.hasPermission(perms.vb_spawn)) {
                    ZombieKing.generateSoldiers(launcher);
                }
                if (args[1].equalsIgnoreCase("test") && launcher.hasPermission(perms.vb_spawn)) {
                    Location loc = launcher.getLocation();
                    //Location nueva = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()+5, loc.getBlockZ());
                    //launcher.sendBlockChange(nueva, Material.STONE, (byte) 0);
                    /*ProtocolManager pm = ProtocolLibrary.getProtocolManager();
                    PacketContainer packet = pm.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
                    packet.getModifier().writeDefaults();
                    packet.getBlockPositionModifier().write(0,
                            new BlockPosition(loc.getBlockX(), loc.getBlockY()+4, loc.getBlockZ()));
                    WrappedBlockData block = WrappedBlockData.createData(Material.STONE);
                    packet.getBlockData().write(0, block);
                    try {
                        pm.sendServerPacket(launcher, packet);
                    } catch (Exception ignored) {}

                    */
                    ProtocolManager pm = ProtocolLibrary.getProtocolManager();
                    PacketContainer packet = pm.createPacket(PacketType.Play.Server.MULTI_BLOCK_CHANGE);
                    Chunk chunk = Bukkit.getWorld("world").getChunkAt(0, 0);
                    ChunkCoordIntPair chunkcoords = new ChunkCoordIntPair(chunk.getX(),
                            chunk.getZ());
                    System.out.println(WrappedBlockData.createData(Material.GOLD_BLOCK).getType());
                    MultiBlockChangeInfo[] change = new MultiBlockChangeInfo[2];
                    change[0] = new MultiBlockChangeInfo(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()+5, loc.getBlockZ()),
                            WrappedBlockData.createData(Material.GOLD_BLOCK));
                    change[1] = new MultiBlockChangeInfo(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()+6, loc.getBlockZ()),
                            WrappedBlockData.createData(Material.GOLD_BLOCK));


                    packet.getChunkCoordIntPairs().write(0, chunkcoords);
                    packet.getMultiBlockChangeInfoArrays().write(0, change);


                    try {
                        pm.sendServerPacket(launcher, packet);
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    launcher.sendMessage("Has realizado el test");
                    return;
                }
            }
        }
    }
}
