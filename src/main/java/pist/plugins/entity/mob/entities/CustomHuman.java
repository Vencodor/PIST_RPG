package main.java.pist.plugins.entity.mob.entities;

import com.mojang.authlib.GameProfile;
import main.java.pist.plugins.entity.mob.object.MobDTO;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import java.util.UUID;

public class CustomHuman extends EntityPlayer {

	public CustomHuman(Location loc, MobDTO mob) {
		super(((CraftServer)Bukkit.getServer()).getServer()
				, ((CraftWorld)loc.getWorld()).getHandle()
				, new GameProfile(UUID.randomUUID(), "test")
				, new PlayerInteractManager(((CraftWorld)loc.getWorld()).getHandle()));
		
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		
		/*
		MobPersonality personality = mob.getPersonality();
		
		getAttributeInstance(GenericAttributes.maxHealth).setValue(mob.getHealth());
		//getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(mob.getSpeed());
		
		getAttributeMap().b(GenericAttributes.ATTACK_DAMAGE).setValue(mob.getPower());
		getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(mob.getPower());
		
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
		*/
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

	@Override
	public boolean isSpectator() {
		return false;
	}

	@Override
	public boolean z() {
		return false;
	}
	
}
