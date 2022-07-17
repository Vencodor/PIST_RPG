package main.java.pist.api.function;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Damage {
	//player .getHandle() .damage() 에서 DamageSource를 GRNERIC로 하면 EntityDamageEvent 호출, playerAttack로 하면 Enttiy Damage By Entity 호출
	public void damage(LivingEntity target, Entity damager, float damage) {
		
        try {
            Class<?> craftliving = target.getClass();
            Method getHandle = craftliving.getDeclaredMethod("getHandle");
            if (getHandle != null) {
                // Dealing the damage
                Object nmsObject = getHandle.invoke(target);
                Class<?> nmsliving = nmsObject.getClass();
                while (!nmsliving.getSimpleName().toLowerCase().equalsIgnoreCase("entityliving")) {
                    nmsliving = nmsliving.getSuperclass();
                }
                Class<?> damageSourceClass = getNMSClass("net.minecraft.server", "DamageSource");
                Field dsF = damageSourceClass.getDeclaredField("GENERIC");
                dsF.setAccessible(true);
                Object sourceType = dsF.get(null);
                Method dmg = nmsliving.getMethod("damageEntity", damageSourceClass, float.class);
                double s = 0.4000000059604645D * 1.5;
                Vector v = damager.getLocation().getDirection().multiply(s)
                        .setY((target.isOnGround() ? s: 0));
                target.setVelocity(v.normalize().multiply(0.4000000059604645D * 1.25));
                dmg.invoke(nmsObject, sourceType, damage);
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();

        }
    }
	
	public Class<?> getNMSClass(String path, String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName(path + "." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
	
}
