package com.lucas.specterdepositos;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	public static Main getInstance() {
		return Main.getPlugin(Main.class);
	}

	public SpecterConfig data = new SpecterConfig(this, "data.yml");
	public HashMap<String, Depositos> cache = new HashMap<>();

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage("§7==========================");
		Bukkit.getConsoleSender().sendMessage("§7| §bSpecterDepositos       §7|");
		Bukkit.getConsoleSender().sendMessage("§7| §bVersão 1.0             §7|");
		Bukkit.getConsoleSender().sendMessage("§7| §fStatus: §aLigado       §7|");
		Bukkit.getConsoleSender().sendMessage("§7==========================");
		Bukkit.getConsoleSender().sendMessage("");
		data.saveDefaultConfig();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getCommand("depositos").setExecutor(new DepositoCommand());
		data.getConfig().getConfigurationSection("Depositos").getKeys(false).forEach(a -> cache.put(a, new Depositos(a,
				data.getInt("Depositos." + a + ".Tamanho"), data.getConfig().getStringList("Depositos." + a + ".Itens"))));
	}

	@Override
	public void onDisable() {
		Depositos.getAll().forEach(b -> b.save());
	}

	@EventHandler
	public void salvar(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if (e.getInventory().getTitle().equals("Seu depósito.")) {
			ArrayList<String> list = new ArrayList<>();
			for(int i = 0; i < e.getInventory().getSize(); i++) {
				if(e.getInventory().getItem(i) != null) {
					list.add(i + ";" + Conversao.converter(e.getInventory().getItem(i)));
				}
			}
			cache.get(p.getName()).setItens(list);
		}
	}
}
