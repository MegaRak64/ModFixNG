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

package modfixng.nms.packets.v1_7_R4;

import modfixng.events.BlockDigPacketItemDropEvent;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.server.v1_7_R4.PacketPlayInBlockDig;
import net.minecraft.server.v1_7_R4.PacketPlayInListener;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;

public class BlockDig extends PacketPlayInBlockDig {

	@Override
	public void a(PacketPlayInListener packetplayinlistener) {
		if (packetplayinlistener instanceof PlayerConnection) {
			CraftPlayer cplayer = ((PlayerConnection) packetplayinlistener).getPlayer();
			if (cplayer.getHandle().playerConnection.isDisconnected()) {
				return;
			}
			if ((g() == 3) || (g() == 4)) {
				BlockDigPacketItemDropEvent event = new BlockDigPacketItemDropEvent(cplayer);
				Bukkit.getPluginManager().callEvent(event);
				if (event.isCancelled()) {
					return;
				}
			}
		}
		packetplayinlistener.a(this);
	}

}