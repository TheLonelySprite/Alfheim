package com.github.thelonelysprite.alfheim.lexicon;

import com.github.thelonelysprite.alfheim.Constants;
import com.github.thelonelysprite.alfheim.recipes.CraftingRecipes;
import net.minecraft.util.EnumChatFormatting;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.KnowledgeType;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.ALexiconEntry;
import vazkii.botania.common.lexicon.LexiconData;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageImage;
import vazkii.botania.common.lexicon.page.PageText;

/**
 * Created by justin on 18/10/2014.
 */
public class AddonData {
    static LexiconCategory advancedAlfomancy;
    static LexiconEntry portal;
    static LexiconEntry notes;
    static String type = "advancedAlfomancy";
    public static KnowledgeType aaKnowledge;
    public static void init(){
        BotaniaAPI.addCategory(advancedAlfomancy = new LexiconCategory("alfheim.category.advancedAlfomancy") );
        aaKnowledge = BotaniaAPI.registerKnowledgeType(type, EnumChatFormatting.DARK_AQUA, false);
        portal = new ElvenEntry("portal");
        portal.setPriority().setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", CraftingRecipes.recipeImprovedGatewayCore), new PageCraftingRecipe("3", CraftingRecipes.recipeSpiritPylon),
                new PageImage("4", Constants.ENTRY_PORTAL0), new PageImage("5", Constants.ENTRY_PORTAL1), new PageImage("6", Constants.ENTRY_PORTAL2), new PageImage("7", Constants.ENTRY_PORTAL3),
                new PageImage("8", Constants.ENTRY_PORTAL4), new PageText("9"), new PageText("10"), new PageText("11"));
        BotaniaAPI.addEntry(portal, advancedAlfomancy);

        notes = new ElvenEntry("scribesNotes");
        notes.setLexiconPages(new PageText("0"),new PageText("1"),new PageText("2"),new PageText("3"));
        BotaniaAPI.addEntry(notes,LexiconData.categoryAlfhomancy);


    }
}
