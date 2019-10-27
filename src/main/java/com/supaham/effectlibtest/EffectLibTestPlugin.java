package com.supaham.effectlibtest;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;

import com.supaham.effectlibtest.Serializers.Serializer;
import com.supaham.effectlibtest.customeffects.NinjaEffect;
import com.supaham.effectlibtest.customeffects.NinjaEffect.Ninja;
import com.supaham.effectlibtest.customeffects.RainbowFartingUnicorn;
import com.supaham.effectlibtest.customeffects.RainbowFartingUnicorn.RFU;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.AnimatedBallEffect;
import de.slikey.effectlib.effect.ArcEffect;
import de.slikey.effectlib.effect.AtomEffect;
import de.slikey.effectlib.effect.BigBangEffect;
import de.slikey.effectlib.effect.BleedEffect;
import de.slikey.effectlib.effect.CircleEffect;
import de.slikey.effectlib.effect.CloudEffect;
import de.slikey.effectlib.effect.ColoredImageEffect;
import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.effect.CubeEffect;
import de.slikey.effectlib.effect.CylinderEffect;
import de.slikey.effectlib.effect.DiscoBallEffect;
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

import me.xorgon.effectlibtest.BallEllipseEffect;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EffectLibTestPlugin extends JavaPlugin {

    private static final Splitter KEYS_SPLITTER = Splitter.on(',').trimResults();
    private static final Splitter KEY_VALUE_SPLITTER = Splitter.on('=').trimResults();
    private static EffectLibTestPlugin plugin;

    private final Map<String, Class<? extends Effect>> effectClasses = new HashMap<>();
    private EffectManager effectManager;

    public EffectLibTestPlugin() {
        Preconditions.checkArgument(plugin == null, "plugin already initialized.");
        plugin = this;
    }

    {
        effectClasses.put("animatedball", AnimatedBallEffect.class);
        effectClasses.put("arc", ArcEffect.class);
        effectClasses.put("atom", AtomEffect.class);
        effectClasses.put("bigbang", BigBangEffect.class);
        effectClasses.put("bleed", BleedEffect.class);
        effectClasses.put("circle", CircleEffect.class);
        effectClasses.put("cloud", CloudEffect.class);
        effectClasses.put("coloredimage", ColoredImageEffect.class);
        effectClasses.put("cone", ConeEffect.class);
        effectClasses.put("cube", CubeEffect.class);
        effectClasses.put("cylinder", CylinderEffect.class);
        effectClasses.put("discoball", DiscoBallEffect.class);
        effectClasses.put("dna", DnaEffect.class);
        effectClasses.put("donut", DonutEffect.class);
        effectClasses.put("dragon", DragonEffect.class);
        effectClasses.put("earth", EarthEffect.class);
        effectClasses.put("explode", ExplodeEffect.class);
        effectClasses.put("flame", FlameEffect.class);
        effectClasses.put("fountain", FountainEffect.class);
        effectClasses.put("grid", GridEffect.class);
        effectClasses.put("heart", HeartEffect.class);
        effectClasses.put("helix", HelixEffect.class);
        effectClasses.put("hill", HillEffect.class);
        effectClasses.put("icon", IconEffect.class);
        effectClasses.put("image", ImageEffect.class);
        effectClasses.put("jump", JumpEffect.class);
        effectClasses.put("line", LineEffect.class);
        effectClasses.put("love", LoveEffect.class);
        effectClasses.put("music", MusicEffect.class);
        effectClasses.put("shield", ShieldEffect.class);
        effectClasses.put("skyrocket", SkyRocketEffect.class);
        effectClasses.put("smoke", SmokeEffect.class);
        effectClasses.put("sphere", SphereEffect.class);
        effectClasses.put("star", StarEffect.class);
        effectClasses.put("text", TextEffect.class);
        effectClasses.put("tornado", TornadoEffect.class);
        effectClasses.put("trace", TraceEffect.class);
        effectClasses.put("turn", TurnEffect.class);
        effectClasses.put("vortex", VortexEffect.class);
        effectClasses.put("warp", WarpEffect.class);
        effectClasses.put("wave", WaveEffect.class);
    }

    @Override
    public void onEnable() {
        effectManager = new EffectManager(this);

        EffectLibTestPlugin.getPlugin().registerClass("ninja", NinjaEffect.class, new Ninja());
        EffectLibTestPlugin.getPlugin().registerClass("rfu", RainbowFartingUnicorn.class, new RFU());
        EffectLibTestPlugin.getPlugin().registerClass("ballellipse", BallEllipseEffect.class, new BallEllipseEffect.BallEllipse());
    }

    @Override
    public void onDisable() {
        effectManager.dispose();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "You need to specify an effect");
            return true;
        }
        Class<? extends Effect> clazz = this.effectClasses.get(args[0].toLowerCase());
        if (clazz == null) {
            sender.sendMessage(ChatColor.RED + args[0] + " is not a valid effect.");
            return true;
        }

        Effect effect;
        try {
            effect = clazz.getConstructor(EffectManager.class).newInstance(this.effectManager);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {
            sender.sendMessage(ChatColor.RED + "Error occurred, please check the console.");
            e.printStackTrace();
            return true;
        }

        Map<String, String> serialized = new HashMap<>();
        if (args.length > 1) {
            Serializer<Effect> serializer = (Serializer<Effect>) Serializers.getSerializer(clazz);
            if (serializer == null) {
                sender.sendMessage(ChatColor.RED + args[0] + " is not supported by this plugin.");
                return true;
            }
            Iterator<String> it = KEYS_SPLITTER.split(Joiner.on(",").join(args)).iterator();
            while (it.hasNext()) {
                String pair = it.next();
                Iterator<String> splitit = KEY_VALUE_SPLITTER.split(pair).iterator();
                String key = splitit.next();
                serialized.put(key, splitit.hasNext() ? splitit.next() : null);
            }
            Serializers.DEFAULT_SERIALIZER.deserialize(effect, serialized);
            serializer.deserialize(effect, serialized);
        }
        Player player = (Player) sender;
        if (serialized.containsKey("loc")) {
            effect.setLocation(player.getLocation());
        } else if (serialized.containsKey("eyeloc")) {
            effect.setLocation(player.getEyeLocation());
        } else {
            effect.setEntity(player);
        }
        effect.setTarget(player.getTargetBlock(null, 100).getLocation());
        effect.start();
        return true;
    }

    public <T extends Effect> void registerClass(String name, Class<T> clazz) {
        registerClass(name, clazz, null);
    }

    public <T extends Effect> void registerClass(String name, Class<T> clazz,
                                                 Serializer<T> serializer) {
        plugin.getLogger().info("Registering " + clazz + " under '" + name + "'.");
        this.effectClasses.put(name.toLowerCase(), clazz);
        if (serializer != null) {
            Serializers.register(clazz, serializer);
        }
    }

    public static EffectLibTestPlugin getPlugin() {
        return plugin;
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }
}
