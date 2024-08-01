package de.cuzim1tigaaa.brainbow.team;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemUtils {

	public static ItemStack getColoredArmor(Team team, EquipmentSlot slot) {
		ItemStack item;
		switch(slot) {
			case HEAD -> item = new ItemStack(Material.LEATHER_HELMET);
			case CHEST -> item = new ItemStack(Material.LEATHER_CHESTPLATE);
			case LEGS -> item = new ItemStack(Material.LEATHER_LEGGINGS);
			case FEET -> item = new ItemStack(Material.LEATHER_BOOTS);
			default -> {
				return null;
			}
		}

		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		assert meta != null;

		meta.setColor(Color.fromRGB(team.getColor().getColor().getRGB()));
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 2, true);
		meta.setDisplayName("");

		item.setItemMeta(meta);
		return item;
	}

	public static void addKit(Player player, Team team) {
		player.getInventory().setItem(EquipmentSlot.HEAD, getColoredArmor(team, EquipmentSlot.HEAD));
		player.getInventory().setItem(EquipmentSlot.CHEST, getColoredArmor(team, EquipmentSlot.CHEST));
		player.getInventory().setItem(EquipmentSlot.LEGS, getColoredArmor(team, EquipmentSlot.LEGS));
		player.getInventory().setItem(EquipmentSlot.FEET, getColoredArmor(team, EquipmentSlot.FEET));

		ItemStack sword = new ItemStack(Material.WOODEN_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		assert swordMeta != null;

		swordMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
		swordMeta.setDisplayName("");

		sword.setItemMeta(swordMeta);
		player.getInventory().setItem(0, sword);


		ItemStack bow = new ItemStack(Material.WOODEN_SWORD);
		ItemMeta bowMeta = bow.getItemMeta();
		assert bowMeta != null;

		bowMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		bowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		bowMeta.setDisplayName("");

		bow.setItemMeta(bowMeta);
		player.getInventory().setItem(1, bow);

		player.getInventory().setItem(3, new ItemStack(Material.BAKED_POTATO, 16));
		player.getInventory().setItem(3, new ItemStack(Material.GOLDEN_APPLE, 1));
		player.getInventory().setItem(8, new ItemStack(Material.ARROW, 16));
	}
}