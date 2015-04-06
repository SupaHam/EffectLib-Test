package com.supaham.effectlibtest;

import com.google.common.base.Preconditions;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.AnimatedBallEffect;
import de.slikey.effectlib.effect.ArcEffect;
import de.slikey.effectlib.effect.AtomEffect;
import de.slikey.effectlib.effect.BigBangEffect;
import de.slikey.effectlib.effect.BleedEffect;
import de.slikey.effectlib.effect.CircleEffect;
import de.slikey.effectlib.effect.CloudEffect;
import de.slikey.effectlib.effect.ColoredImageEffect;
import de.slikey.effectlib.effect.ColoredImageEffect.Plane;
import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.effect.CubeEffect;
import de.slikey.effectlib.effect.CylinderEffect;
import de.slikey.effectlib.effect.DiscoBallEffect;
import de.slikey.effectlib.effect.DiscoBallEffect.Direction;
import de.slikey.effectlib.effect.DnaEffect;
import de.slikey.effectlib.effect.DonutEffect;
import de.slikey.effectlib.effect.DragonEffect;
import de.slikey.effectlib.effect.EarthEffect;
import de.slikey.effectlib.effect.ExplodeEffect;
import de.slikey.effectlib.effect.FlameEffect;
import de.slikey.effectlib.effect.FountainEffect;
import de.slikey.effectlib.effect.GridEffect;
import de.slikey.effectlib.effect.HeartEffect;
import de.slikey.effectlib.effect.HelixEffect;
import de.slikey.effectlib.effect.HillEffect;
import de.slikey.effectlib.effect.IconEffect;
import de.slikey.effectlib.effect.ImageEffect;
import de.slikey.effectlib.effect.JumpEffect;
import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.effect.LoveEffect;
import de.slikey.effectlib.effect.MusicEffect;
import de.slikey.effectlib.effect.ShieldEffect;
import de.slikey.effectlib.effect.SkyRocketEffect;
import de.slikey.effectlib.effect.SmokeEffect;
import de.slikey.effectlib.effect.SphereEffect;
import de.slikey.effectlib.effect.StarEffect;
import de.slikey.effectlib.effect.TextEffect;
import de.slikey.effectlib.effect.TornadoEffect;
import de.slikey.effectlib.effect.TraceEffect;
import de.slikey.effectlib.effect.TurnEffect;
import de.slikey.effectlib.effect.VortexEffect;
import de.slikey.effectlib.effect.WarpEffect;
import de.slikey.effectlib.effect.WaveEffect;
import de.slikey.effectlib.util.ParticleEffect;

import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Sound;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class contains a Map of {@link Serializer}s to their effects classes.
 *
 * @see {@link #getSerializer(Class)}
 */
public class Serializers {

  private static final Map<Class<? extends Effect>, Serializer> SERIALIZERS
      = new HashMap<>();
  public static final DefaultSerializer DEFAULT_SERIALIZER = new DefaultSerializer();
  private static final EffectManager mgr = EffectLibTestPlugin.getPlugin().getEffectManager();

  static {
    SERIALIZERS.put(AnimatedBallEffect.class, new AnimatedBall());
    SERIALIZERS.put(ArcEffect.class, new Arc());
    SERIALIZERS.put(AtomEffect.class, new Atom());
    SERIALIZERS.put(BigBangEffect.class, new BigBang());
    SERIALIZERS.put(BleedEffect.class, new Bleed());
    SERIALIZERS.put(CircleEffect.class, new Circle());
    SERIALIZERS.put(CloudEffect.class, new Cloud());
    SERIALIZERS.put(ColoredImageEffect.class, new ColoredImage());
    SERIALIZERS.put(ConeEffect.class, new Cone());
    SERIALIZERS.put(CubeEffect.class, new Cube());
    SERIALIZERS.put(CylinderEffect.class, new Cylinder());
    SERIALIZERS.put(DiscoBallEffect.class, new DiscoBall());
    SERIALIZERS.put(DnaEffect.class, new Dna());
    SERIALIZERS.put(DonutEffect.class, new Donut());
    SERIALIZERS.put(DragonEffect.class, new Dragon());
    SERIALIZERS.put(EarthEffect.class, new Earth());
    SERIALIZERS.put(ExplodeEffect.class, new Explode());
    SERIALIZERS.put(FlameEffect.class, new Flame());
    SERIALIZERS.put(FountainEffect.class, new Fountain());
    SERIALIZERS.put(GridEffect.class, new Grid());
    SERIALIZERS.put(HeartEffect.class, new Heart());
    SERIALIZERS.put(HelixEffect.class, new Helix());
    SERIALIZERS.put(HillEffect.class, new Hill());
    SERIALIZERS.put(IconEffect.class, new Icon());
    SERIALIZERS.put(ImageEffect.class, new Image());
    SERIALIZERS.put(JumpEffect.class, new Jump());
    SERIALIZERS.put(LineEffect.class, new Line());
    SERIALIZERS.put(LoveEffect.class, new Love());
    SERIALIZERS.put(MusicEffect.class, new Music());
    SERIALIZERS.put(ShieldEffect.class, new Shield());
    SERIALIZERS.put(SkyRocketEffect.class, new SkyRocket());
    SERIALIZERS.put(SmokeEffect.class, new Smoke());
    SERIALIZERS.put(SphereEffect.class, new Sphere());
    SERIALIZERS.put(StarEffect.class, new Star());
    SERIALIZERS.put(TextEffect.class, new Text());
    SERIALIZERS.put(TornadoEffect.class, new Tornado());
    SERIALIZERS.put(TraceEffect.class, new Trace());
    SERIALIZERS.put(TurnEffect.class, new Turn());
    SERIALIZERS.put(VortexEffect.class, new Vortex());
    SERIALIZERS.put(WarpEffect.class, new Warp());
    SERIALIZERS.put(WaveEffect.class, new Wave());
  }

  /**
   * Gets a {@link Serializer} from an {@link Effect} class. The Serializer returned is typically
   * x from xEffect.
   *
   * @param effectClass effect class to get serializer for
   * @param <T> type of effect class for the serializer to return
   *
   * @return serializer
   */
  public static <T extends Effect> Serializer<T> getSerializer(Class<T> effectClass) {
    return SERIALIZERS.get(effectClass);
  }

  public static <T extends Effect> Serializer<T> register(Class<T> clazz,
                                                          Serializer<T> serializer) {
    Preconditions.checkNotNull(clazz, "effect class cannot be null.");
    Preconditions.checkNotNull(serializer, "serializer cannot be null.");
    return SERIALIZERS.put(clazz, serializer);
  }

  public interface Serializer<T extends Effect> {

    void deserialize(T effect, Map<String, String> map);
  }

  public static final class DefaultSerializer implements Serializer<Effect> {

    @Override
    public void deserialize(Effect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "type":
            effect.type = EffectType.valueOf(entry.getValue().toUpperCase());
            break;
          case "color":
            effect.color = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "speed":
            effect.speed = Float.parseFloat(entry.getValue());
            break;
          case "delay":
            effect.delay = Integer.parseInt(entry.getValue());
            break;
          case "period":
            effect.period = Integer.parseInt(entry.getValue());
            break;
          case "iterations":
            effect.iterations = Integer.parseInt(entry.getValue());
            break;
          case "visibleRange":
            effect.visibleRange = Float.parseFloat(entry.getValue());
            break;
          case "locationUpdateInterval":
            effect.locationUpdateInterval = Integer.parseInt(entry.getValue());
            break;
          case "autoOrient":
            effect.autoOrient = Boolean.valueOf(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class AnimatedBall implements Serializer<AnimatedBallEffect> {

    @Override
    public void deserialize(AnimatedBallEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
          case "particlesPerIteration":
            effect.particlesPerIteration = Integer.parseInt(entry.getValue());
            break;
          case "size":
            effect.size = Float.parseFloat(entry.getValue());
            break;
          case "xFactor":
            effect.xFactor = Float.parseFloat(entry.getValue());
            break;
          case "yFactor":
            effect.yFactor = Float.parseFloat(entry.getValue());
            break;
          case "zFactor":
            effect.zFactor = Float.parseFloat(entry.getValue());
            break;
          case "xOffset":
            effect.xOffset = Float.parseFloat(entry.getValue());
            break;
          case "yOffset":
            effect.yOffset = Float.parseFloat(entry.getValue());
            break;
          case "zOffset":
            effect.zOffset = Float.parseFloat(entry.getValue());
            break;
          case "xRotation":
            effect.xRotation = Double.parseDouble(entry.getValue());
            break;
          case "yRotation":
            effect.yRotation = Double.parseDouble(entry.getValue());
            break;
          case "zRotation":
            effect.zRotation = Double.parseDouble(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Arc implements Serializer<ArcEffect> {

    @Override
    public void deserialize(ArcEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
          case "height":
            effect.height = Float.parseFloat(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Atom implements Serializer<AtomEffect> {

    @Override
    public void deserialize(AtomEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particleNucleus":
            effect.particleNucleus = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "colorNucleus":
            effect.colorNucleus = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "particleOrbital":
            effect.particleOrbital = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "colorOrbital":
            effect.colorOrbital = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "radius":
            effect.radius = Integer.parseInt(entry.getValue());
            break;
          case "radiusNucleus":
            effect.radiusNucleus = Float.parseFloat(entry.getValue());
            break;
          case "particlesNucleus":
            effect.particlesNucleus = Integer.parseInt(entry.getValue());
            break;
          case "particlesOrbital":
            effect.particlesOrbital = Integer.parseInt(entry.getValue());
            break;
          case "orbitals":
            effect.orbitals = Integer.parseInt(entry.getValue());
            break;
          case "rotation":
            effect.rotation = Double.parseDouble(entry.getValue());
            break;
          case "angularVelocity":
            effect.angularVelocity = Double.parseDouble(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class BigBang implements Serializer<BigBangEffect> {

    @Override
    public void deserialize(BigBangEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "fireworkType":
            effect.fireworkType = Type.valueOf(entry.getValue().toUpperCase());
            break;
          case "color":
            effect.color = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "color2":
            effect.color2 = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "color3":
            effect.color3 = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "fadeColor":
            effect.fadeColor = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "intensity":
            effect.intensity = Integer.parseInt(entry.getValue());
            break;
          case "radius":
            effect.radius = Float.parseFloat(entry.getValue());
            break;
          case "explosions":
            effect.explosions = Integer.parseInt(entry.getValue());
            break;
          case "soundInterval":
            effect.soundInterval = Integer.parseInt(entry.getValue());
            break;
          case "sound":
            effect.sound = Sound.valueOf(entry.getValue().toUpperCase());
            break;
          case "soundVolume":
            effect.soundVolume = Float.parseFloat(entry.getValue());
            break;
          case "soundPitch":
            effect.soundPitch = Float.parseFloat(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Bleed implements Serializer<BleedEffect> {

    @Override
    public void deserialize(BleedEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "hurt":
            effect.hurt = Boolean.valueOf(entry.getValue());
            break;
          case "duration":
            effect.duration = Integer.parseInt(entry.getValue());
            break;
          case "color":
            effect.color = Integer.parseInt(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Circle implements Serializer<CircleEffect> {

    @Override
    public void deserialize(CircleEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "xRotation":
            effect.xRotation = Double.parseDouble(entry.getValue());
            break;
          case "yRotation":
            effect.yRotation = Double.parseDouble(entry.getValue());
            break;
          case "zRotation":
            effect.zRotation = Double.parseDouble(entry.getValue());
            break;
          case "angularVelocityX":
            effect.angularVelocityX = Double.parseDouble(entry.getValue());
            break;
          case "angularVelocityY":
            effect.angularVelocityY = Double.parseDouble(entry.getValue());
            break;
          case "angularVelocityZ":
            effect.angularVelocityZ = Double.parseDouble(entry.getValue());
            break;
          case "radius":
            effect.radius = Float.parseFloat(entry.getValue());
            break;
          case "xSubtract":
            effect.xSubtract = Double.parseDouble(entry.getValue());
            break;
          case "ySubtract":
            effect.ySubtract = Double.parseDouble(entry.getValue());
            break;
          case "zSubtract":
            effect.zSubtract = Double.parseDouble(entry.getValue());
            break;
          case "enableRotation":
            effect.enableRotation = Boolean.valueOf(entry.getValue());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Cloud implements Serializer<CloudEffect> {

    @Override
    public void deserialize(CloudEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "cloudParticle":
            effect.cloudParticle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "cloudColor":
            effect.cloudColor = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "mainParticle":
            effect.mainParticle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "cloudSize":
            effect.cloudSize = Float.parseFloat(entry.getValue());
            break;
          case "particleRadius":
            effect.particleRadius = Float.parseFloat(entry.getValue());
            break;
          case "yOffset":
            effect.yOffset = Double.parseDouble(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class ColoredImage implements Serializer<ColoredImageEffect> {

    @Override
    public void deserialize(ColoredImageEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "fileName":
            effect.loadFile(new File(entry.getValue()));
            break;
          case "stepX":
            effect.stepX = Integer.parseInt(entry.getValue());
            break;
          case "stepY":
            effect.stepY = Integer.parseInt(entry.getValue());
            break;
          case "size":
            effect.size = Float.parseFloat(entry.getValue());
            break;
          case "enableRotation":
            effect.enableRotation = Boolean.valueOf(entry.getValue());
            break;
          case "plane":
            effect.plane = Plane.valueOf(entry.getValue().toUpperCase());
            break;
          case "angularVelocityX":
            effect.angularVelocityX = Double.parseDouble(entry.getValue());
            break;
          case "angularVelocityY":
            effect.angularVelocityY = Double.parseDouble(entry.getValue());
            break;
          case "angularVelocityZ":
            effect.angularVelocityZ = Double.parseDouble(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Cone implements Serializer<ConeEffect> {

    @Override
    public void deserialize(ConeEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "lengthGrow":
            effect.lengthGrow = Float.parseFloat(entry.getValue());
            break;
          case "angularVelocity":
            effect.angularVelocity = Double.parseDouble(entry.getValue());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
          case "radiusGrow":
            effect.radiusGrow = Float.parseFloat(entry.getValue());
            break;
          case "particlesCone":
            effect.particlesCone = Integer.parseInt(entry.getValue());
            break;
          case "rotation":
            effect.rotation = Double.parseDouble(entry.getValue());
            break;
          case "randomize":
            effect.randomize = Boolean.valueOf(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Cube implements Serializer<CubeEffect> {

    @Override
    public void deserialize(CubeEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "edgeLength":
            effect.edgeLength = Float.parseFloat(entry.getValue());
            break;
          case "angularVelocityX":
            effect.angularVelocityX = Double.parseDouble(entry.getValue());
            break;
          case "angularVelocityY":
            effect.angularVelocityY = Double.parseDouble(entry.getValue());
            break;
          case "angularVelocityZ":
            effect.angularVelocityZ = Double.parseDouble(entry.getValue());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
          case "enableRotation":
            effect.enableRotation = Boolean.valueOf(entry.getValue());
            break;
          case "outlineOnly":
            effect.outlineOnly = Boolean.valueOf(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Cylinder implements Serializer<CylinderEffect> {

    @Override
    public void deserialize(CylinderEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "radius":
            effect.radius = Float.parseFloat(entry.getValue());
            break;
          case "height":
            effect.height = Float.parseFloat(entry.getValue());
            break;
          case "angularVelocityX":
            effect.angularVelocityX = Double.parseDouble(entry.getValue());
            break;
          case "angularVelocityY":
            effect.angularVelocityY = Double.parseDouble(entry.getValue());
            break;
          case "angularVelocityZ":
            effect.angularVelocityZ = Double.parseDouble(entry.getValue());
            break;
          case "rotationX":
            effect.rotationX = Double.parseDouble(entry.getValue());
            break;
          case "rotationY":
            effect.rotationY = Double.parseDouble(entry.getValue());
            break;
          case "rotationZ":
            effect.rotationZ = Double.parseDouble(entry.getValue());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
          case "enableRotation":
            effect.enableRotation = Boolean.valueOf(entry.getValue());
            break;
          case "solid":
            effect.solid = Boolean.valueOf(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class DiscoBall implements Serializer<DiscoBallEffect> {

    @Override
    public void deserialize(DiscoBallEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "sphereRadius":
            effect.sphereRadius = Float.parseFloat(entry.getValue());
            break;
          case "max":
            effect.max = Integer.parseInt(entry.getValue());
            break;
          case "sphereParticle":
            effect.sphereParticle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "lineParticle":
            effect.lineParticle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "sphereColor":
            effect.sphereColor = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "lineColor":
            effect.lineColor = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "maxLines":
            effect.maxLines = Integer.parseInt(entry.getValue());
            break;
          case "lineParticles":
            effect.lineParticles = Integer.parseInt(entry.getValue());
            break;
          case "sphereParticles":
            effect.sphereParticles = Integer.parseInt(entry.getValue());
            break;
          case "direction":
            effect.direction = Direction.valueOf(entry.getValue().toUpperCase());
            break;
        }
      }
    }
  }

  public static final class Dna implements Serializer<DnaEffect> {

    @Override
    public void deserialize(DnaEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particleHelix":
            effect.particleHelix = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "colorHelix":
            effect.colorHelix = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "particleBase1":
            effect.particleBase1 = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "colorBase1":
            effect.colorBase1 = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "colorBase2":
            effect.colorBase2 = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "particleBase2":
            effect.particleBase2 = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "radials":
            effect.radials = Double.parseDouble(entry.getValue());
            break;
          case "radius":
            effect.radius = Float.parseFloat(entry.getValue());
            break;
          case "particlesHelix":
            effect.particlesHelix = Integer.parseInt(entry.getValue());
            break;
          case "particlesBase":
            effect.particlesBase = Integer.parseInt(entry.getValue());
            break;
          case "length":
            effect.length = Float.parseFloat(entry.getValue());
            break;
          case "grow":
            effect.grow = Float.parseFloat(entry.getValue());
            break;
          case "baseInterval":
            effect.baseInterval = Float.parseFloat(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Donut implements Serializer<DonutEffect> {

    @Override
    public void deserialize(DonutEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "particlesCircle":
            effect.particlesCircle = Integer.parseInt(entry.getValue());
            break;
          case "circles":
            effect.circles = Integer.parseInt(entry.getValue());
            break;
          case "radiusDonut":
            effect.radiusDonut = Float.parseFloat(entry.getValue());
            break;
          case "radiusTube":
            effect.radiusTube = Float.parseFloat(entry.getValue());
            break;
          case "xRotation":
            effect.xRotation = Double.parseDouble(entry.getValue());
            break;
          case "yRotation":
            effect.yRotation = Double.parseDouble(entry.getValue());
            break;
          case "zRotation":
            effect.zRotation = Double.parseDouble(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Dragon implements Serializer<DragonEffect> {

    @Override
    public void deserialize(DragonEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "pitch":
            effect.pitch = Float.parseFloat(entry.getValue());
            break;
          case "arcs":
            effect.arcs = Integer.parseInt(entry.getValue());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
          case "stepsPerIteration":
            effect.stepsPerIteration = Integer.parseInt(entry.getValue());
            break;
          case "length":
            effect.length = Float.parseFloat(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Earth implements Serializer<EarthEffect> {

    @Override
    public void deserialize(EarthEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "precision":
            effect.precision = Integer.parseInt(entry.getValue());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
          case "radius":
            effect.radius = Float.parseFloat(entry.getValue());
            break;
          case "mountainHeight":
            effect.radius = Float.parseFloat(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Explode implements Serializer<ExplodeEffect> {

    @Override
    public void deserialize(ExplodeEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "amount":
            effect.amount = Integer.parseInt(entry.getValue());
            break;
          case "speed":
            effect.speed = Float.parseFloat(entry.getValue());
            break;
          case "sound":
            effect.sound = Sound.valueOf(entry.getValue().toUpperCase());
            break;
        }
      }
    }
  }

  public static final class Flame implements Serializer<FlameEffect> {

    @Override
    public void deserialize(FlameEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          default:
            break;
        }
      }
    }
  }

  public static final class Fountain implements Serializer<FountainEffect> {

    @Override
    public void deserialize(FountainEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "strands":
            effect.strands = Integer.parseInt(entry.getValue());
            break;
          case "particlesStrands":
            effect.particlesStrand = Integer.parseInt(entry.getValue());
            break;
          case "particlesSpout":
            effect.particlesSpout = Integer.parseInt(entry.getValue());
            break;
          case "radius":
            effect.radius = Float.parseFloat(entry.getValue());
            break;
          case "radiusSpout":
            effect.radiusSpout = Float.parseFloat(entry.getValue());
            break;
          case "height":
            effect.height = Float.parseFloat(entry.getValue());
            break;
          case "heightSpout":
            effect.heightSpout = Float.parseFloat(entry.getValue());
            break;
          case "rotation":
            effect.rotation = Double.parseDouble(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Grid implements Serializer<GridEffect> {

    @Override
    public void deserialize(GridEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "rows":
            effect.rows = Integer.parseInt(entry.getValue());
            break;
          case "columns":
            effect.columns = Integer.parseInt(entry.getValue());
            break;
          case "widthCell":
            effect.widthCell = Float.parseFloat(entry.getValue());
            break;
          case "heightCell":
            effect.heightCell = Float.parseFloat(entry.getValue());
            break;
          case "particlesWidth":
            effect.particlesWidth = Integer.parseInt(entry.getValue());
            break;
          case "particlesHeight":
            effect.particlesHeight = Integer.parseInt(entry.getValue());
            break;
          case "rotation":
            effect.rotation = Double.parseDouble(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Heart implements Serializer<HeartEffect> {

    @Override
    public void deserialize(HeartEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
          case "xRotation":
            effect.xRotation = Double.parseDouble(entry.getValue());
            break;
          case "yRotation":
            effect.yRotation = Double.parseDouble(entry.getValue());
            break;
          case "zRotation":
            effect.zRotation = Double.parseDouble(entry.getValue());
            break;
          case "yFactor":
            effect.yFactor = Double.parseDouble(entry.getValue());
            break;
          case "xFactor":
            effect.xFactor = Double.parseDouble(entry.getValue());
            break;
          case "factorInnerSpike":
            effect.factorInnerSpike = Double.parseDouble(entry.getValue());
            break;
          case "compressYFactorTotal":
            effect.compressYFactorTotal = Double.parseDouble(entry.getValue());
            break;
          case "compilaction":
            effect.compilaction = Float.parseFloat(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Helix implements Serializer<HelixEffect> {

    @Override
    public void deserialize(HelixEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "strands":
            effect.strands = Integer.parseInt(entry.getValue());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
          case "radius":
            effect.radius = Float.parseFloat(entry.getValue());
            break;
          case "curve":
            effect.curve = Float.parseFloat(entry.getValue());
            break;
          case "rotation":
            effect.rotation = Double.parseDouble(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Hill implements Serializer<HillEffect> {

    @Override
    public void deserialize(HillEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "height":
            effect.height = Float.parseFloat(entry.getValue());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
          case "edgeLength":
            effect.edgeLength = Float.parseFloat(entry.getValue());
            break;
          case "yRotation":
            effect.yRotation = Double.parseDouble(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Icon implements Serializer<IconEffect> {

    @Override
    public void deserialize(IconEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "yOffset":
            effect.yOffset = Integer.parseInt(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Image implements Serializer<ImageEffect> {

    @Override
    public void deserialize(ImageEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "fileName":
            effect.loadFile(new File(entry.getValue()));
            break;
          case "invert":
            effect.invert = Boolean.valueOf(entry.getValue());
            break;
          case "stepX":
            effect.stepX = Integer.parseInt(entry.getValue());
            break;
          case "stepY":
            effect.stepY = Integer.parseInt(entry.getValue());
            break;
          case "size":
            effect.size = Float.parseFloat(entry.getValue());
            break;
          case "enableRotation":
            effect.enableRotation = Boolean.valueOf(entry.getValue());
            break;
          case "plane":
            effect.plane = ImageEffect.Plane.valueOf(entry.getValue().toUpperCase());
            break;
          case "angularVelocityX":
            effect.angularVelocityX = Double.parseDouble(entry.getValue());
            break;
          case "angularVelocityY":
            effect.angularVelocityY = Double.parseDouble(entry.getValue());
            break;
          case "angularVelocityZ":
            effect.angularVelocityZ = Double.parseDouble(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Jump implements Serializer<JumpEffect> {

    @Override
    public void deserialize(JumpEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "power":
            effect.power = Float.parseFloat(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Line implements Serializer<LineEffect> {

    @Override
    public void deserialize(LineEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "isZigZag":
            effect.isZigZag = Boolean.valueOf(entry.getValue());
            break;
          case "zigZags":
            effect.zigZags = Integer.parseInt(entry.getValue());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Love implements Serializer<LoveEffect> {

    @Override
    public void deserialize(LoveEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
        }
      }
    }
  }

  public static final class Music implements Serializer<MusicEffect> {

    @Override
    public void deserialize(MusicEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "radialsPerStep":
            effect.radialsPerStep = Double.parseDouble(entry.getValue());
            break;
          case "radius":
            effect.radius = Float.parseFloat(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Shield implements Serializer<ShieldEffect> {

    @Override
    public void deserialize(ShieldEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "radius":
            effect.radius = Integer.parseInt(entry.getValue());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
          case "sphere":
            effect.sphere = Boolean.parseBoolean(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class SkyRocket implements Serializer<SkyRocketEffect> {

    @Override
    public void deserialize(SkyRocketEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          default:
            break;
        }
      }
    }
  }

  public static final class Smoke implements Serializer<SmokeEffect> {

    @Override
    public void deserialize(SmokeEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
        }
      }
    }
  }

  public static final class Sphere implements Serializer<SphereEffect> {

    @Override
    public void deserialize(SphereEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "radius":
            effect.radius = Double.parseDouble(entry.getValue());
            break;
          case "yOffset":
            effect.yOffset = Double.parseDouble(entry.getValue());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Star implements Serializer<StarEffect> {

    @Override
    public void deserialize(StarEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
          case "spikeHeight":
            effect.spikeHeight = Float.parseFloat(entry.getValue());
            break;
          case "spikesHalf":
            effect.spikesHalf = Integer.parseInt(entry.getValue());
            break;
          case "innerRadius":
            effect.innerRadius = Float.parseFloat(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Text implements Serializer<TextEffect> {

    @Override
    public void deserialize(TextEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "text":
            effect.text = entry.getValue();
            break;
          case "invert":
            effect.invert = Boolean.parseBoolean(entry.getValue());
            break;
          case "stepX":
            effect.stepX = Integer.parseInt(entry.getValue());
            break;
          case "stepY":
            effect.stepY = Integer.parseInt(entry.getValue());
            break;
          case "size":
            effect.size = Float.parseFloat(entry.getValue());
            break;
          case "font":
            throw new UnsupportedOperationException("You can't set font nerd.");
        }
      }
    }
  }

  public static final class Tornado implements Serializer<TornadoEffect> {

    @Override
    public void deserialize(TornadoEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "tornadoParticle":
            effect.tornadoParticle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "tornadoColor":
            effect.tornadoColor = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "cloudParticle":
            effect.cloudParticle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "cloudColor":
            effect.cloudColor = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "cloudSize":
            effect.cloudSize = Float.parseFloat(entry.getValue());
            break;
          case "yOffset":
            effect.yOffset = Double.parseDouble(entry.getValue());
            break;
          case "tornadoHeight":
            effect.tornadoHeight = Float.parseFloat(entry.getValue());
            break;
          case "maxTornadoRadius":
            effect.maxTornadoRadius = Float.parseFloat(entry.getValue());
            break;
          case "showCloud":
            effect.showCloud = Boolean.parseBoolean(entry.getValue());
            break;
          case "showTornado":
            effect.showTornado = Boolean.parseBoolean(entry.getValue());
            break;
          case "distance":
            effect.distance = Double.parseDouble(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Trace implements Serializer<TraceEffect> {

    @Override
    public void deserialize(TraceEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "refresh":
            effect.refresh = Integer.parseInt(entry.getValue());
            break;
          case "maxWayPoints":
            effect.maxWayPoints = Integer.parseInt(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Turn implements Serializer<TurnEffect> {

    @Override
    public void deserialize(TurnEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "step":
            effect.step = Float.parseFloat(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Vortex implements Serializer<VortexEffect> {

    @Override
    public void deserialize(VortexEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "radius":
            effect.radius = Float.parseFloat(entry.getValue());
            break;
          case "grow":
            effect.grow = Float.parseFloat(entry.getValue());
            break;
          case "radials":
            effect.radials = Double.parseDouble(entry.getValue());
            break;
          case "circles":
            effect.circles = Integer.parseInt(entry.getValue());
            break;
          case "helixes":
            effect.helixes = Integer.parseInt(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Warp implements Serializer<WarpEffect> {

    @Override
    public void deserialize(WarpEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "radius":
            effect.radius = Float.parseFloat(entry.getValue());
            break;
          case "particles":
            effect.particles = Integer.parseInt(entry.getValue());
            break;
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "grow":
            effect.grow = Float.parseFloat(entry.getValue());
            break;
          case "rings":
            effect.rings = Integer.parseInt(entry.getValue());
            break;
        }
      }
    }
  }

  public static final class Wave implements Serializer<WaveEffect> {

    @Override
    public void deserialize(WaveEffect effect, Map<String, String> map) {
      for (Entry<String, String> entry : map.entrySet()) {
        switch (entry.getKey()) {
          case "particle":
            effect.particle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "cloudParticle":
            effect.cloudParticle = ParticleEffect.valueOf(entry.getValue().toUpperCase());
            break;
          case "cloudColor":
            effect.cloudColor = Color.fromRGB(Integer.parseInt(entry.getValue()));
            break;
          case "velocity":
            // TODO
            throw new UnsupportedOperationException();
          case "particlesFront":
            effect.particlesFront = Integer.parseInt(entry.getValue());
            break;
          case "particlesBack":
            effect.particlesBack = Integer.parseInt(entry.getValue());
            break;
          case "rows":
            effect.rows = Integer.parseInt(entry.getValue());
            break;
          case "lengthFront":
            effect.lengthFront = Float.parseFloat(entry.getValue());
            break;
          case "lengthBack":
            effect.lengthBack = Float.parseFloat(entry.getValue());
            break;
          case "depthFront":
            effect.depthFront = Float.parseFloat(entry.getValue());
            break;
          case "heightBack":
            effect.heightBack = Float.parseFloat(entry.getValue());
            break;
          case "height":
            effect.height = Float.parseFloat(entry.getValue());
            break;
          case "width":
            effect.width = Float.parseFloat(entry.getValue());
            break;
        }
      }
    }
  }
}
