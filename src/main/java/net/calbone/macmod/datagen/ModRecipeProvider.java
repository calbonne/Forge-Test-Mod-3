package net.calbone.macmod.datagen;

import net.calbone.macmod.MacMod;
import net.calbone.macmod.block.ModBlocks;
import net.calbone.macmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> SAPPHIRE_SMELTABLES = List.of(
            ModItems.RAW_SAPPHIRE.get(),
            ModBlocks.RAW_SAPPHIRE_BLOCK.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        oreSmelting(pFinishedRecipeConsumer, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 100, "sapphire");
        oreBlasting(pFinishedRecipeConsumer, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 100, "sapphire");

        oreSmelting(pFinishedRecipeConsumer, List.of(ModBlocks.RAW_RUBY_BLOCK.get()), RecipeCategory.MISC, ModItems.RUBY.get(), 0.25f, 100, "ruby");
        oreBlasting(pFinishedRecipeConsumer, List.of(ModBlocks.RAW_RUBY_BLOCK.get()), RecipeCategory.MISC, ModItems.RUBY.get(), 0.25f, 100, "ruby");
        //redstone to ruby
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RUBY.get())
                .pattern("SS")
                .pattern("SS")
                .define('S', Items.REDSTONE)
                .unlockedBy(getHasName(ModItems.RUBY.get()), has(ModItems.RUBY.get()))
                .save(pFinishedRecipeConsumer);
        //ruby to redstone
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.REDSTONE, 4)
                .requires(ModItems.RUBY.get())
                .unlockedBy(getHasName(ModItems.RUBY.get()), has(ModItems.RUBY.get()))
                .save(pFinishedRecipeConsumer);

        //metal detector recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.METAL_DETECTOR.get())
                .pattern("#I ")
                .pattern("IS ")
                .pattern("  S")
                .define('S', Items.STICK)
                .define('I', Items.IRON_INGOT)
                .define('#', ModItems.RUBY.get())
                .unlockedBy(getHasName(ModItems.RUBY.get()), has(ModItems.RUBY.get()))
                .save(pFinishedRecipeConsumer);
    }

    // coppied over from Recipe provider class
    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    //changed last variable (path Suffix) to include the Mod ID
    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pSuffix) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                    pExperience, pCookingTime, pSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, MacMod.MOD_ID + ":" + pSuffix + "_" + getItemName(itemlike));
        }

    }
}
