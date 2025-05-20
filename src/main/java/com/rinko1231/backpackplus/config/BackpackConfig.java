package com.rinko1231.backpackplus.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BackpackConfig {
        public static final ModConfigSpec SPEC;
        public static ModConfigSpec.IntValue InvSlotCount;
    public static ModConfigSpec.IntValue upgradeSlotCount;

        static
        {
            ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
            BUILDER.push("BackpackPlus Config");

            InvSlotCount = BUILDER
                    .defineInRange("Number of inventory slots in the backpack", 144,9,114514);
            upgradeSlotCount = BUILDER
                    .defineInRange("Number of upgrade slots in the backpack", 10,10,12);


            SPEC = BUILDER.build();
        }


    }