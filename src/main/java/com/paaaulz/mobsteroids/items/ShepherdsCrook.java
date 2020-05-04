package com.paaaulz.mobsteroids.items;

import java.util.List;
import com.paaaulz.mobsteroids.ModItems;
import com.paaaulz.mobsteroids.Reference;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShepherdsCrook extends AxeItem
{
    String name = "shepherds_crook";
    float bypassAttackDamage = 0.0F;
    private static final Logger LOGGER = LogManager.getLogger();

    public ShepherdsCrook(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder)
    {
        super(ItemTier.WOOD, 0, -2.4F, (new Item.Properties()).group(ItemGroup.TOOLS));
        setRegistryName(Reference.MOD_ID, name);
        ModItems.ITEMS.add(this);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        // Tempts hit animal
        if (target instanceof AnimalEntity)
        {
            // Ok I hit an animal
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
        // Tempts all animals around
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        double playerX = playerIn.getPosition().getX();
        double playerY = playerIn.getPosition().getY();
        double playerZ = playerIn.getPosition().getZ();
        double maxDistanceX = playerX + 100.0;
        double maxDistanceY = playerY + 10.0;
        double maxDistanceZ = playerZ + 100.0;
        // Play bell sound
        worldIn.playSound((int) playerX,(int) playerY,(int) playerZ, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.05F, false);
        // Get back animal's attention by restarting the goal
        // 1) Search for animals nearby
        AxisAlignedBB nearby = new AxisAlignedBB(playerX, playerY, playerZ, maxDistanceX, maxDistanceY, maxDistanceZ);
        List<LivingEntity> animalsNearby = worldIn.getEntitiesWithinAABB(AnimalEntity.class, nearby);
        for(LivingEntity livingentity : animalsNearby)
        {
            if (livingentity instanceof AnimalEntity)
            {
                // If is animal regain interest by restarting TemptGoal
                ((AnimalEntity) livingentity).goalSelector.getRunningGoals().filter((running_goal) -> { return running_goal.getGoal().getClass().equals(TemptGoal.class); }).forEach(PrioritizedGoal::startExecuting);
                long temptGoals = ((AnimalEntity) livingentity).goalSelector.getRunningGoals().filter((running_goal) -> { return running_goal.getGoal().getClass().equals(TemptGoal.class); }).count();
                if (temptGoals > 0)
                {
                    ((AnimalEntity) livingentity).goalSelector.getRunningGoals().filter((running_goal) -> { return running_goal.getGoal().getClass().equals(TemptGoal.class); }).forEach(PrioritizedGoal::startExecuting);
                }
                else
                {
                    ((AnimalEntity) livingentity).goalSelector.addGoal(0, new TemptGoal((CreatureEntity) livingentity, 1.25D, Ingredient.fromItems(ModItems.SHEPHERDSCROOK), false));
                }
            }
        }
        // Return action success
        return ActionResult.func_226248_a_(itemstack);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack)
    {
        // Set item name
        return new TranslationTextComponent("Shepherd's crook");
    }
}
