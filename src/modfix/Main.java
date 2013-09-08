/**
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 3
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*
*/

package modfix;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

//warning: this plugin requires ProtocolLib to run
public class Main extends JavaPlugin {

	private Logger log = Bukkit.getLogger();
	
	private ModFixConfig config;
	
	private MFCommandListener commandl;
	private MFBagFixListener bagl;
	private MFTableFixListener tablel;
	private MFChunkFixListener chunkl;
	private MFMinecartFreecamOpenFixListener mpl;
	private MFRailsFixListener rfl;	
	private MFFreecamInventoryOpenFix fciol;
	private MFHopperMinecartFix hpl;
	private MFBagFrameInsertFixListener bfil;
	
	public ProtocolManager protocolManager = null;
	
	@Override
	public void onEnable() {
		if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null)
		{
			log.severe("[ModFix] ProtolLib is not installed, install it first");
			log.severe("[ModFix] Shutting down server");
			Bukkit.shutdown();
			return;
		}
		else
		{
		    protocolManager = ProtocolLibrary.getProtocolManager();
		}
		
			//init config
			config = new ModFixConfig(this);
			config.loadConfig();
			//init command listener
			commandl = new MFCommandListener(this,config);
			getCommand("modfix").setExecutor(commandl);
			getServer().getPluginManager().registerEvents(commandl, this);
			//init bag bugfix listener
			bagl = new MFBagFixListener(this,config);
			getServer().getPluginManager().registerEvents(bagl, this);
			//init table bugfix listener
			tablel = new MFTableFixListener(this,config);
			getServer().getPluginManager().registerEvents(tablel, this);
			//init chunk bugfix listener
			chunkl = new MFChunkFixListener(this,config);
			getServer().getPluginManager().registerEvents(chunkl, this);
			//init minecart bugfix listener
			mpl = new MFMinecartFreecamOpenFixListener(this,config);
			getServer().getPluginManager().registerEvents(mpl, this);
			//init rails bugfix listener
			rfl = new MFRailsFixListener(this,config);
			getServer().getPluginManager().registerEvents(rfl, this);
			//init freecam fix listener
			fciol = new MFFreecamInventoryOpenFix(this,config);
			getServer().getPluginManager().registerEvents(fciol, this);
			//init hopperminecart fix listener
			hpl = new MFHopperMinecartFix(this,config);
			getServer().getPluginManager().registerEvents(hpl, this);
			//init bag insert into frame fix listener
			bfil = new MFBagFrameInsertFixListener(this,config);
			getServer().getPluginManager().registerEvents(bfil, this);
	}
	
	@Override
	public void onDisable() {
		//null variables for folks reloading plugins
		if (protocolManager != null)
		{
			config = null;
			commandl = null;
			HandlerList.unregisterAll(this);
			tablel = null;
			mpl = null;
			rfl = null;
			fciol = null;
			hpl = null;
			bfil = null;
			protocolManager.removePacketListeners(this);
			protocolManager = null;
		}
	}
	
	
	
}