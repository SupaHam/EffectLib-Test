package me.xorgon.effectlibtest;

import com.supaham.effectlibtest.Serializers;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.util.ParticleEffect;
import me.xorgon.effectlibtest.util.ColorUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Map;

/**
 * An effect that flies around the player.
 */
public class BallEllipseEffect extends Effect {

    Vector r = new Vector(1, 0, 0); //Location relative to player
    Vector v = r.clone().crossProduct(new Vector(0, 1, 0)).normalize().multiply(0.15); //Velocity
    Vector a = new Vector(0, 0, 0); //Acceleration

    int[] rgb = new int[]{225, 0, 0};

    Vector offR = new Vector(0, 0, 0);

    Color[] colors = new Color[2];

    int particles = 3;

    public BallEllipseEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 1;
        iterations = 250;
        int[] cRGB = ColorUtils.contrastRGB(rgb[0], rgb[1], rgb[2]);
        colors[0] = Color.fromRGB(cRGB[0]);
        colors[1] = Color.fromRGB(cRGB[1]);
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        ParticleEffect particle = ParticleEffect.REDSTONE;

        Vector rel = r.add(offR);

        display(particle, location.add(rel), colors[0]);
        location.subtract(rel); //Making sure not to modify location.

        display(particle, location.subtract(rel), colors[1]);
        location.add(rel);

        //Extra particles and interpolation code.
        Vector vDiv = v.multiply(1.0 / particles);
        v.multiply(particles);
        if (particles > 1) {
            for (int n = 2; n <= particles; n++) {
                Vector dif = vDiv.multiply(n - 1);
                vDiv.multiply(1.0 / (n - 1));

                display(particle, location.add(rel.subtract(dif)), colors[0]);
                rel.add(dif);
                location.subtract(rel.subtract(dif));
                rel.add(dif);

                display(particle, location.subtract(rel.subtract(dif)), colors[1]);
                rel.add(dif);
                location.add(rel.subtract(dif));
                rel.add(dif);
            }
        }

        r.subtract(offR);

        updateVectors();
    }

    private void updateVectors() {
        a = r.clone().multiply(1 / r.length()).multiply(-v.length() * v.length());
        v.add(a);
        r.add(v);
    }


    public static final class BallEllipse implements Serializers.Serializer<BallEllipseEffect> {

        @Override
        public void deserialize(BallEllipseEffect effect, Map<String, String> map) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                switch (entry.getKey()) {
                    case "r":
                        effect.rgb[0] = Integer.valueOf(entry.getValue());
                        break;
                    case "g":
                        effect.rgb[1] = Integer.valueOf(entry.getValue());
                        break;
                    case "b":
                        effect.rgb[2] = Integer.valueOf(entry.getValue());
                        break;
                    case "particles":
                        effect.particles = Integer.valueOf(entry.getValue());
                        break;
                    case "offy":
                        effect.offR = new Vector(0, Double.valueOf(entry.getValue()), 0);
                        break;
                }
                int[] cRGB = ColorUtils.contrastRGB(effect.rgb[0], effect.rgb[1], effect.rgb[2]);
                effect.colors[0] = Color.fromRGB(cRGB[0]);
                effect.colors[1] = Color.fromRGB(cRGB[1]);
            }
        }

    }
}
