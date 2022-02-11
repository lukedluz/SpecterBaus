package com.lucas.specterdepositos;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DepositoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
		if (s instanceof Player) {
			Player p = (Player) s;
			if (cmd.getName().equalsIgnoreCase("deposito")) {
				if (args.length == 0) {
					p.sendMessage(" ");
					p.sendMessage("§e/deposito acessar §fAcesse o seu depósito.");
					p.sendMessage("§e/deposito expandir §fExpanda seu depósito. §7(500K runas)");
					p.sendMessage("§e/deposito comprar §fCompre o seu depósito. §7(5K runas)");
					p.sendMessage(" ");
					return true;
				}
				if (args.length > 1) {
					p.sendMessage("§cVocê não pode inserir espaços aqui.");
					return true;
				}
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("acessar")) {
						if(!Main.getInstance().cache.containsKey(p.getName())) {
							p.sendMessage("§cVocê não tem um depósito.");
							return true;
						}
						Depositos b = Main.getInstance().cache.get(p.getName());
						Inventory inv = Bukkit.createInventory(null, b.getTamanho(), "Seu depósito.");
						if(b.getItens() == null) {
							p.openInventory(inv);
							return true;
						}
						for(String i : b.getItens()) {
							String[] split = i.split(";");
							int slot = Integer.valueOf(split[0]);
							try {
								ItemStack item = Conversao.desconverter(split[1]);
								inv.setItem(slot, item);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						p.openInventory(inv);
					}else if(args[0].equalsIgnoreCase("expandir")) {
						if(!Main.getInstance().cache.containsKey(p.getName())) {
							p.sendMessage("§cVocê não tem um depósito.");
							return true;
						}
						Depositos b = Main.getInstance().cache.get(p.getName());
						if(b.getTamanho() == 9*6) {
							p.sendMessage("§cSeu depósito virtual já foi expandido.");
							return true;
						}
						if(com.lucas.specterrunas.api.RunasAPI.getRunas(p) < 500000.0) {
							p.sendMessage("§cVocê não tem runas suficiente para fazer isso.");
							return true;
						}
						com.lucas.specterrunas.api.RunasAPI.removerRunas(p, 500000.0);
						b.setTamanho(9*6);
						p.sendMessage("§eVocê expandiu seu depósito.");
					}else if(args[0].equalsIgnoreCase("comprar")) {
						if(Main.getInstance().cache.containsKey(p.getName())) {
							p.sendMessage("§cVocê já tem um depósito.");
							return true;
						}
						if(com.lucas.specterrunas.api.RunasAPI.getRunas(p) < 5000.0) {
							p.sendMessage("§cVocê não tem runas suficiente para fazer isso.");
							return true;
						}
						com.lucas.specterrunas.api.RunasAPI.removerRunas(p, 5000.0);
						p.sendMessage("§eVocê comprou seu depósito, use §f/deposito expandir §epara expandir ele.");
						Main.getInstance().cache.put(p.getName(), new Depositos(p.getName(), 9*3, new ArrayList<String>()));
					}else {
						p.sendMessage("§cSub comando não encontrado.");
					}
					return true;
				}
			}
		}
		return true;
	}

}
