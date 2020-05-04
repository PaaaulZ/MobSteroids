package com.paaaulz.mobsteroids.items;

import java.util.List;
import com.paaaulz.mobsteroids.ModItems;
import com.paaaulz.mobsteroids.Reference;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShepherdsCrook extends Item
{
    private static final Logger LOGGER = LogManager.getLogger();

    public ShepherdsCrook(String name)
    {
        super(new Properties().group(ItemGroup.MISC));
        setRegistryName(Reference.MOD_ID, name);
        ModItems.ITEMS.add(this);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        LOGGER.info("Target is " + target.getClass());
        if (target instanceof AnimalEntity)
        {
            // Ok I hit an animal
            LOGGER.info("OK target is good: " + target.getClass());
            AnimalEntity animalEntity = (AnimalEntity) target;
            // Makes the animal start following me when I hold the crook
            animalEntity.goalSelector.addGoal(0, new TemptGoal((CreatureEntity) target, 1.25D, Ingredient.fromItems(ModItems.SHEPHERDSCROOK), false));
            // Prevent the animal from panicking and running around randomly.
            animalEntity.setRevengeTarget(null);
        }
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {

        // CAN'T DO IT THIS WAY, NEED TO OVERRIDE MAX DISTANCE BEFORE ANIMAL GETS DISTRACTED

        ItemStack itemstack = playerIn.getHeldItem(handIn);
        double playerX = playerIn.getPosition().getX();
        double playerY = playerIn.getPosition().getY();
        double playerZ = playerIn.getPosition().getZ();
        double maxDistanceX = playerX + 60.0;
        double maxDistanceY = playerY + 5.0;
        double maxDistanceZ = playerZ + 60.0;
        // Play bell sound
        worldIn.playSound((int) playerX,(int) playerY,(int) playerZ, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.05F, false);
        // Get back animal's attention by restarting the goal
        // 1) Search for animals nearby
        AxisAlignedBB nearby = new AxisAlignedBB(playerX, playerY, playerZ, maxDistanceX, maxDistanceY, maxDistanceY);
        List<LivingEntity> animalsNearby = worldIn.getEntitiesWithinAABB(AnimalEntity.class, nearby);
        for(LivingEntity livingentity : animalsNearby)
        {
            if (livingentity instanceof AnimalEntity)
            {
                // If is animal regain interest by restarting TemptGoal
                ((AnimalEntity) livingentity).goalSelector.getRunningGoals().filter((running_goal) -> { return running_goal.getGoal().getClass().equals(TemptGoal.class); }).forEach(PrioritizedGoal::startExecuting);
            }
        }
        // Return action success
        return ActionResult.func_226248_a_(itemstack);
    }

}
