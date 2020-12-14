package cf.mysty.structures;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.WorldGenMineshaftConfiguration;

public class Commands implements CommandExecutor
{
	Generator<WorldGenMineshaftConfiguration> gen;
	
	public Commands(Main main)
	{
		main.getCommand("s").setExecutor(this);
		gen = new Generator<WorldGenMineshaftConfiguration>();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if (!(sender instanceof Player))
			return true;
		
		Player player = (Player) sender;
		
		if (args.length > 0)
		{
			if (args[0].equalsIgnoreCase("gen"))
			{
				try
				{
					gen.generate(player.getLocation());
				}
				catch (NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e)
				{
					e.printStackTrace();
				}
				player.sendMessage(ChatColor.GOLD + "Generated Structure");
				return true;
			}
		}
		return true;
	}
}
