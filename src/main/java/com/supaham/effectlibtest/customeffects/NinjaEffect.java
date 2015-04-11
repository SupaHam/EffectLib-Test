package com.supaham.effectlibtest.customeffects;

import com.supaham.effectlibtest.Serializers.Serializer;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.util.ParticleEffect;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.Map.Entry;

public final class NinjaEffect extends Effect {
  
  public ParticleEffect particle = ParticleEffect.CLOUD;
  public float radius = .7f;
  public int particles = 20;
  public int particleAmountPerSpawn = 2;
  public float particleSpeed = 0.03F;

  public NinjaEffect(EffectManager effectManager) {
    super(effectManager);
  }

  @Override
  public void onRun() {
    Location location = getLocation()
        .subtract(0, ((LivingEntity) getEntity()).getEyeHeight() - .15, 0);
    double _2pi = Math.PI * 2;
    for (int i = 0; i < particles; i++) {
      double theta = _2pi * i / particles;
      Vector v = new Vector(radius * Math.cos(theta), 0, radius * Math.sin(theta));
      display(particle, location.add(v), particleSpeed, particleAmountPerSpawn);
      location.subtract(v);
    }
  }
  
  public static final class Ninja implements Serializer<NinjaEffect> {

    @Override
    public void deserialize(NinjaEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "radius":
            effect.radius = Float.parseFloat(entry.getValue());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
          case "particleAmountPerSpawn":
            effect.particleAmountPerSpawn = Integer.parseInt(entry.getValue());
            break;
          case "particleSpeed":
            effect.particleSpeed = Float.parseFloat(entry.getValue());
            break;
        }
      }
    }
  }
}
