package cf.mysty.structures;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		new Commands(this);
	}
	
	@Override
	public void onDisable()
	{
		
	}
}
