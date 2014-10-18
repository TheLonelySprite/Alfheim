package com.github.thelonelysprite.alfheim.lexicon;

import net.minecraft.util.EnumChatFormatting;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.IAddonEntry;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.common.lexicon.LexiconData;

/**
 * Created by justin on 18/10/2014.
 */
public class ElvenEntry extends LexiconEntry implements IAddonEntry {
    public ElvenEntry(String unlocalizedName) {
        super(unlocalizedName, LexiconData.categoryAlfhomancy);
        setKnowledgeType(BotaniaAPI.elvenKnowledge);
    }
    @Override
    public LexiconEntry setLexiconPages(LexiconPage... pages) {
        for(LexiconPage page : pages)
            page.unlocalizedName = "alfheim.page." + getLazyUnlocalizedName() + page.unlocalizedName;
        return super.setLexiconPages(pages);
    }
    @Override
    public String getUnlocalizedName() {
        return "alfheim.entry." + super.getUnlocalizedName();
    }
    public String getLazyUnlocalizedName() {
        return super.getUnlocalizedName();
    }


    @Override
    public String getSubtitle() {
        return "Alfheim";
    }
}
