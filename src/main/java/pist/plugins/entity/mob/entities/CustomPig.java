package main.java.pist.plugins.entity.mob.entities;

import main.java.pist.plugins.entity.mob.object.MobDTO;
import main.java.pist.plugins.entity.mob.object.enums.MobPersonality;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;

public class CustomPig extends EntityPig {

	public CustomPig(Location loc, MobDTO mob) {
		super(((CraftWorld)loc.getWorld()).getHandle());
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		
		MobPersonality personality = mob.getPersonality();
		
		getAttributeInstance(GenericAttributes.maxHealth).setValue(mob.getHealth());
		//getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(mob.getSpeed());
		
		getAttributeMap().b(GenericAttributes.ATTACK_DAMAGE).setValue(mob.getPower());;
		getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(mob.getPower());
		
		clearTargetsAndGoals();
		
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
		this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
		if(personality == MobPersonality.평화) {
			this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));
		} else if(personality == MobPersonality.중립) {
			this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 1.0D, false));
			this.targetSelector.a(0, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
		} else if(personality == MobPersonality.적대) {
			this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 1.0D, false));
			this.targetSelector.a(0, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
		}
	}
	
	public boolean damageEntity(DamageSource damagesource, float f) {
	    if (isInvulnerable(damagesource))
	      return false; 
	    Entity entity = damagesource.getEntity();
	    if (entity != null && !(entity instanceof EntityHuman) && !(entity instanceof EntityArrow))
	      f = (f + 1.0F) / 2.0F; 
	    return super.damageEntity(damagesource, f);
	  }
	  
	  public boolean B(Entity entity) {
	    boolean flag = entity.damageEntity(DamageSource.mobAttack(this), (int)getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue());
	    if (flag)
	      a(this, entity); 
	    return flag;
	  }
	
	public void clearTargetsAndGoals() {
        try {
            Field bField;
            bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            bField.set(goalSelector, new LinkedHashSet<>());
            Field cField;
            cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            cField.set(targetSelector, new LinkedHashSet<>());
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
	
}
