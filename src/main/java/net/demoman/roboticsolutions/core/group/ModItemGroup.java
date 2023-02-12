package net.demoman.roboticsolutions.core.group;

import net.demoman.roboticsolutions.core.BlockRegistration;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {

    public static final ItemGroup ROBOTIC_SOLUTIONS_GROUP = new ItemGroup("roboticSolutionsTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BlockRegistration.DOTTED_GRAY_CONCRETE_FLOOR.get());
        }

    };
}
