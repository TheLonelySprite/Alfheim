package com.github.thelonelysprite.alfheim.lexicon;

import vazkii.botania.api.lexicon.IAddonEntry;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;

/**
 * Created by justin on 18/10/2014.
 */
public class AlfheimEntry extends LexiconEntry implements IAddonEntry {
    public AlfheimEntry(String unlocalizedName) {
        super(unlocalizedName, AddonData.advancedAlfomancy);
        setKnowledgeType(AddonData.aaKnowledge);
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
