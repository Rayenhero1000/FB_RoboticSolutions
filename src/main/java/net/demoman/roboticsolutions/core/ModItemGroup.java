package net.demoman.roboticsolutions.core;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {

    public static final ItemGroup ROBOTIC_SOLUTIONS_GROUP = new ItemGroup("roboticSolutionsTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModRegistration.MEMORY_JUICE_BUCKET.get());
        }

    };
}
