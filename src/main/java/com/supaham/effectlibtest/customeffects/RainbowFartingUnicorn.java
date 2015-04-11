package com.supaham.effectlibtest.customeffects;

import com.supaham.effectlibtest.EffectLibTestPlugin;
import com.supaham.effectlibtest.Serializers.Serializer;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class RainbowFartingUnicorn extends Effect {

  // TODO HAO TEL UNICORN IS DONE POOP SO I CAN KIL IT
  private final Random random = new Random();
  private Horse unicorn;

  public RainbowFartingUnicorn(EffectManager effectManager) {
    super(effectManager);
    type = EffectType.REPEATING;
    period = 2;
    iterations = 100;
  }

  @Override
  public void onRun() {
    spawn();
    Location location = this.unicorn.getLocation().subtract(0, 1, 0);
    setLocation(location); // store last location to spawn unicorn next run
    DyeColor[] values = DyeColor.values();
    Item item = location.getWorld()
        .dropItem(location.clone().add(0, 1, 0),
                  new ItemStack(Material.WOOL, 0, (short) random.nextInt(values.length)));
    item.setMetadata("lol", new FixedMetadataValue(EffectLibTestPlugin.getPlugin(), item.getUniqueId()));
    item.setPickupDelay(100);
    item.setVelocity(location.getDirection().multiply(-1));
  }
  
  private void spawn() {
    if (this.unicorn == null || this.unicorn.isDead()) {
      Location location = getLocation();
      Block block = location.getBlock().getRelative(BlockFace.DOWN);
      if (!block.getType().isSolid()) {
        block.setType(Material.DIAMOND_BLOCK);
      }
      this.unicorn = location.getWorld().spawn(location, Horse.class);
      setEntity(this.unicorn);
      this.unicorn.setAdult();
      this.unicorn.setColor(Color.WHITE);
      this.unicorn.setStyle(Style.WHITE);
    }
  }

  public static final class RFU implements Serializer<RainbowFartingUnicorn> {

    @Override
    public void deserialize(RainbowFartingUnicorn effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          default:
            break;
        }
      }
    }
  }
}
