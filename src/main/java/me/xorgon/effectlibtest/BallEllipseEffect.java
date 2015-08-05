package me.xorgon.effectlibtest;

import com.supaham.effectlibtest.Serializers;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Map;

/**
 * An effect that flies around the player.
 */
public class BallEllipseEffect extends Effect {

    Vector r; //Location relative to player
    Vector v; //Velocity
    Vector a; //Acceleration

    public BallEllipseEffect(EffectManager effectManager) {
        super(effectManager);
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        ParticleEffect particle = ParticleEffect.REDSTONE;
        if (r == null) {
            r = new Vector(1, 0, 0);
        }
        if (v == null) {
            v = new Vector(0, 0, 0.05);
        }
        if (a == null) {
            a = r.multiply(-0.05);
        }

        display(particle, location.clone().add(r));
        updateVectors();
    }

    private void updateVectors() {
        v = v.add(a);
        r = r.add(v);
    }

    public static final class BallEllipse implements Serializers.Serializer<BallEllipseEffect> {

        @Override
        public void deserialize(BallEllipseEffect effect, Map<String, String> map) {
        }

    }
}
