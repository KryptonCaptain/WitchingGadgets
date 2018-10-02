package witchinggadgets.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.WandTriggerRegistry;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.lib.utils.Utils;
import witchinggadgets.WitchingGadgets;
import witchinggadgets.common.blocks.tiles.TileEntityBlastfurnace;
import witchinggadgets.common.items.ItemClusters;
import witchinggadgets.common.items.baubles.ItemCloak;
import witchinggadgets.common.magic.WGEnchantSoulbound;
import witchinggadgets.common.util.Utilities;
import witchinggadgets.common.util.recipe.InfernalBlastfurnaceRecipe;
import witchinggadgets.common.util.recipe.PhotoDevelopingRecipe;
import witchinggadgets.common.util.recipe.SpinningRecipe;
import witchinggadgets.common.util.registry.MetalFluidData;
import witchinggadgets.common.util.research.WGFakeResearchItem;
import witchinggadgets.common.util.research.WGResearchItem;
import cpw.mods.fml.common.registry.GameRegistry;

public class WGResearch
{
	public static HashMap<String,Object> recipeList = new HashMap<String,Object>();
	public static final ResourceLocation[] wgbackgrounds = {new ResourceLocation("witchinggadgets:textures/gui/research/WGResearchBack.png")}; 

	public static void setupResearchPages()
	{
		ResearchCategories.registerCategory("WITCHGADG", new ResourceLocation("witchinggadgets:textures/gui/research/WGIcon.png"), wgbackgrounds[0]);
	}

	public static void registerRecipes()
	{
		AspectList infusionAspects;
		AspectList craftingAspects;
		AspectList alchemyAspects;

		ItemStack standardCloak = new ItemStack(WGContent.ItemCloak,1,0);
		/**
		infusionAspects = new AspectList().add(Aspect.DEATH, 40).add(Aspect.METAL, 40).add(Aspect.WEAPON, 75).add(Aspect.ENTROPY, 50).add(Aspect.HUNGER, 25);
		InfusionRecipe infR_vorp = ThaumcraftApi.addInfusionCraftingRecipe("VORPALBLADE",new ItemStack(Item.swordIron),4,infusionAspects,new ItemStack(ConfigItems.itemFocusPortableHole", 0),new ItemStack[] {new ItemStack(ConfigItems.itemShard", 5),new ItemStack(ConfigItems.itemShard", 5),new ItemStack(Item.enderPearl),new ItemStack(Item.enderPearl),new ItemStack(Item.ghastTear)});
		 */

		registerShapedOreRecipe("GEMCUTTING","_TOOLS", new ItemStack(WGContent.ItemMaterial,1,8), "qfi","sss", 'q',"gemQuartz", 'f',Items.flint, 'i',"ingotIron", 's',"stickWood");

		if(Config.allowMirrors)
		{
			//WALLMIRROR
			infusionAspects = new AspectList().add(Aspect.VOID, 20).add(Aspect.TRAVEL, 20).add(Aspect.ELDRITCH, 20).add(Aspect.CRYSTAL, 20);
			registerInfusionRecipe("WALLMIRROR","",new ItemStack(WGContent.BlockWallMirror),8,infusionAspects,new ItemStack(ConfigBlocks.blockMirror),new ItemStack[] {new ItemStack(ConfigItems.itemFocusPortableHole),new ItemStack(ConfigItems.itemShard, 1, 5),new ItemStack(Items.ender_pearl),new ItemStack(Items.gold_ingot),new ItemStack(Items.gold_ingot),new ItemStack(Blocks.quartz_block,1,1)});
		}
		//SCANCAMERA
		craftingAspects = new AspectList().add(Aspect.AIR, 20).add(Aspect.EARTH, 20).add(Aspect.ORDER, 10);
		registerArcaneRecipe("SCANCAMERA", "", new ItemStack(WGContent.ItemScanCamera), craftingAspects, "wl ","pmt","wl ", 't',ConfigItems.itemThaumometer, 'm',new ItemStack(ConfigItems.itemResource, 1, 10), 'p',Blocks.glass_pane, 'w',new ItemStack(ConfigBlocks.blockWoodenDevice,1,6), 'l',Items.leather);
		IArcaneRecipe developingRecipe = new PhotoDevelopingRecipe();
		ThaumcraftApi.getCraftingRecipes().add(developingRecipe);
		recipeList.put("SCANCAMERA_DEVELOP",developingRecipe);
		registerShapelessOreRecipe("SCANCAMERA", "_CLEARPLATE", new ItemStack(ConfigItems.itemResource,1,10), new ItemStack(WGContent.ItemMaterial,1,9));

		craftingAspects = new AspectList().add(Aspect.ORDER, 10);
		registerArcaneRecipe("CALCULATOR", "", new ItemStack(WGContent.ItemMaterial,1,7), craftingAspects, "srs","sbs","sgs", 's',"stickWood", 'r',"dyeRed", 'b',"dyeBlue", 'g',"dyeGreen");

		//CLOAKS
		if (WGConfig.moduleCloak) {
			craftingAspects = new AspectList().add(Aspect.AIR,7);
			registerArcaneRecipe("CLOAK","",standardCloak, craftingAspects, " F ","FFF","FFF", 'F', new ItemStack(ConfigItems.itemResource, 1, 7));
			
			if (WGConfig.capeSpectral) {
				infusionAspects = new AspectList().add(Aspect.SOUL, 8).add(Aspect.TRAVEL, 8).add(Aspect.ELDRITCH, 4).add(Aspect.SENSES,4);
				registerInfusionRecipe("CLOAK_SPECTRAL","",new ItemStack(WGContent.ItemCloak,1,1),3,infusionAspects,new ItemStack(WGContent.ItemCloak),new ItemStack[] {new ItemStack(Items.potionitem,1,8270),new ItemStack(WGContent.ItemMaterial,1,5),new ItemStack(Items.ender_pearl),new ItemStack(WGContent.ItemMaterial,1,5)});
			}
			if (WGConfig.capeStorage) {
				craftingAspects = new AspectList().add(Aspect.AIR,20).add(Aspect.ENTROPY,15).add(Aspect.ORDER, 10);
				registerArcaneRecipe("CLOAK_STORAGE","", new ItemStack(WGContent.ItemCloak,1,2)/*ItemCloak.getCloakWithTag("STORAGE")*/, craftingAspects, "SCS"," B ", 'C',"travelgearCloakBase", 'S',new ItemStack(WGContent.ItemMaterial,1,3), 'B',new ItemStack(WGContent.ItemBag));
			}
			if (WGConfig.capeWolf) {
				craftingAspects = new AspectList().add(Aspect.FIRE,10).add(Aspect.ENTROPY,20).add(Aspect.EARTH, 15);
				registerArcaneRecipe("CLOAK_WOLF","", new ItemStack(WGContent.ItemCloak,1,3)/*ItemCloak.getCloakWithTag("WOLF")*/, craftingAspects, " W ","WCW", 'C',"travelgearCloakBase", 'W',new ItemStack(WGContent.ItemMaterial,1,6));
			}
			if(WGModCompat.tfRavensFeather!=null && WGConfig.capeRaven)	{
				craftingAspects = new AspectList().add(Aspect.AIR,15).add(Aspect.ORDER, 15);
				registerArcaneRecipe("CLOAK_RAVEN","", new ItemStack(WGContent.ItemCloak,1,4)/*ItemCloak.getCloakWithTag("RAVEN")*/, craftingAspects, " F ","FCF","FSF", 'C',"travelgearCloakBase", 'S',new ItemStack(ConfigItems.itemShard,1,0), 'F',new ItemStack(WGModCompat.tfRavensFeather));
			}
			
			if (WGConfig.moduleKama) {
				craftingAspects = new AspectList().add(Aspect.AIR,5).add(Aspect.ORDER,5);
				for(int cm=0; cm<ItemCloak.subNames.length; cm++)
					registerArcaneRecipe("CLOAKKAMA",("_"+cm),new ItemStack(WGContent.ItemKama,1,cm), craftingAspects, "B","C", 'B',"baubleBeltBase", 'C',new ItemStack(WGContent.ItemCloak,1,cm));
				//super("CLOAKKAMA", new ItemStack(), new AspectList().add().add(), new Object[]{"B","C", 'B', "baubleBeltBase", 'C', });

				//			recipeList.put("CLOAKKAMA_"+cm, new KamaRecipe(cm));
			}
		}
		
		craftingAspects = new AspectList().add(Aspect.ORDER,5).add(Aspect.EARTH,3);
		registerArcaneRecipe("ETHEREALWALL","",new ItemStack(WGContent.BlockStoneDevice,6,0), craftingAspects, "SsS", "STS", "S S", 'S',new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6), 's', new ItemStack(ConfigItems.itemShard, 1, 32767), 'T', new ItemStack(Blocks.redstone_torch));

		craftingAspects = new AspectList().add(Aspect.ORDER,10).add(Aspect.ENTROPY,10);
		registerArcaneRecipe("AGEINGSTONE","",new ItemStack(WGContent.BlockStoneDevice,1,1), craftingAspects, " s ", "SCS", " s ", 'S',new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6), 's', new ItemStack(ConfigItems.itemShard, 1, 32767), 'C', new ItemStack(Items.clock));

		craftingAspects = new AspectList().add(Aspect.ORDER,8).add(Aspect.WATER,4).add(Aspect.EARTH, 4);
		registerArcaneRecipe("ICEEXTRUDER","",new ItemStack(WGContent.BlockWoodenDevice,1,1), craftingAspects, " W ", "ISI", "wSw", 'S',new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6), 'w', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6), 'W', new ItemStack(ConfigBlocks.blockCrystal,1,2), 'I', new ItemStack(Blocks.packed_ice) );

		craftingAspects = new AspectList().add(Aspect.ENTROPY,4).add(Aspect.EARTH,8);
		registerArcaneRecipe("STONEEXTRUDER","",new ItemStack(WGContent.BlockWoodenDevice,1,2), craftingAspects, " P ", "WSL", "wSw", 'S',new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6), 'w', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6), 'W', new ItemStack(Items.water_bucket), 'L', new ItemStack(Items.lava_bucket), 'P', new ItemStack(ConfigItems.itemPickThaumium));

		craftingAspects = new AspectList().add(Aspect.ORDER,5).add(Aspect.AIR,5);
		registerArcaneRecipe("SPINNINGWHEEL","",new ItemStack(WGContent.BlockWoodenDevice), craftingAspects, "I W", " T ", 'T',new ItemStack(ConfigBlocks.blockTable), 'I', new ItemStack(Items.iron_ingot), 'W', "plankWood");

		craftingAspects = new AspectList().add(Aspect.FIRE,20).add(Aspect.WATER,10).add(Aspect.ORDER, 10);
		registerArcaneRecipe("SAUNASTOVE","",new ItemStack(WGContent.BlockWoodenDevice,1,4), craftingAspects, "SCS", "WBW", "WWW", 'S',new ItemStack(Blocks.stone_slab), 'C', "blockCoal", 'W', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6), 'B',new ItemStack(Items.bucket));

		craftingAspects = new AspectList().add(Aspect.ORDER, 30);
		registerArcaneRecipe("LABELLIB","",new ItemStack(WGContent.BlockWoodenDevice,1,5), craftingAspects, "BLW", " T ", 'B',new ItemStack(Items.book), 'L', new ItemStack(ConfigItems.itemResource,1,13), 'W', new ItemStack(ConfigItems.itemInkwell), 'T',new ItemStack(ConfigBlocks.blockTable));

		if(Config.allowMirrors)
		{
			craftingAspects = new AspectList().add(Aspect.AIR,10).add(Aspect.WATER,10).add(Aspect.ORDER, 10);
			registerArcaneRecipe("MIRRORPUMP","",new ItemStack(WGContent.BlockMetalDevice,1,0), craftingAspects, " B ", "HCT", " B ", 'T',new ItemStack(ConfigBlocks.blockTube), 'B',new ItemStack(ConfigBlocks.blockWoodenDevice), 'C',new ItemStack(ConfigBlocks.blockMetalDevice,1,9), 'H',new ItemStack(Blocks.hopper));
		}

		if (WGConfig.moduleBag) {
			craftingAspects = new AspectList().add(Aspect.ENTROPY,5).add(Aspect.AIR,5);
			registerShapelessArcaneRecipe("BAGOFTRICKS","_CLOTH",new ItemStack(WGContent.ItemMaterial,2,3), craftingAspects, new ItemStack(WGContent.ItemMaterial,1,0), new ItemStack(WGContent.ItemMaterial,1,0), new ItemStack(WGContent.ItemMaterial,1,0), new ItemStack(WGContent.ItemMaterial,1,2));
			
			craftingAspects = new AspectList().add(Aspect.ORDER,20).add(Aspect.AIR,20);
			registerArcaneRecipe("BAGOFTRICKS","_BAG",new ItemStack(WGContent.ItemBag), craftingAspects, "C C", "C C", "CCC", 'C', new ItemStack(WGContent.ItemMaterial,1,3));

			if (WGConfig.bagHungry) {
				craftingAspects = new AspectList().add(Aspect.ORDER,10).add(Aspect.AIR,10);
				registerArcaneRecipe("HUNGERBAG","",new ItemStack(WGContent.ItemBag,1,3), craftingAspects, " H ", "CBC", 'C',new ItemStack(WGContent.ItemMaterial,1,3), 'H',new ItemStack(ConfigBlocks.blockChestHungry), 'B',new ItemStack(WGContent.ItemBag));
			}
			if (WGConfig.bagEnder) {
				infusionAspects = new AspectList().add(Aspect.VOID, 8).add(Aspect.ELDRITCH, 4).add(Aspect.MAGIC, 4);
				registerInfusionRecipe("ENDERBAG","",new ItemStack(WGContent.ItemBag,1,2),3,infusionAspects,new ItemStack(WGContent.ItemBag,1,0),new ItemStack[] {new ItemStack(Blocks.ender_chest), new ItemStack(WGContent.ItemMaterial,1,5), new ItemStack(Items.ender_eye), new ItemStack(WGContent.ItemMaterial,1,5)});
			}
			if (WGConfig.bagVoid) {
				infusionAspects = new AspectList().add(Aspect.VOID, 16).add(Aspect.ELDRITCH, 16).add(Aspect.ENTROPY, 32);
				registerInfusionRecipe("VOIDBAG","",new ItemStack(WGContent.ItemBag,1,1),4,infusionAspects,new ItemStack(WGContent.ItemBag,1,0),new ItemStack[] {new ItemStack(ConfigItems.itemResource,1,17), new ItemStack(WGContent.ItemMaterial,1,3), new ItemStack(ConfigItems.itemResource,1,17), new ItemStack(WGContent.ItemMaterial,1,3) });
			}
			
		}
		
		craftingAspects = new AspectList().add(Aspect.ENTROPY,5).add(Aspect.ORDER,5);
		registerShapelessArcaneRecipe("ADVANCEDROBES","_CLOTH",new ItemStack(WGContent.ItemMaterial,1,5), craftingAspects, new ItemStack(WGContent.ItemMaterial,1,0), new ItemStack(WGContent.ItemMaterial,1,2),new ItemStack(WGContent.ItemMaterial,1,2), new ItemStack(WGContent.ItemMaterial,1,1));
		
		craftingAspects = new AspectList().add(Aspect.ORDER,10).add(Aspect.ENTROPY,10);
		registerArcaneRecipe("ADVANCEDROBES","_CHEST",new ItemStack(WGContent.ItemAdvancedRobeChest), craftingAspects, " C ", "CRC", 'C', new ItemStack(WGContent.ItemMaterial,1,5), 'R', new ItemStack(ConfigItems.itemChestRobe));
		craftingAspects = new AspectList().add(Aspect.ORDER,10).add(Aspect.ENTROPY,10);
		registerArcaneRecipe("ADVANCEDROBES","_LEGS",new ItemStack(WGContent.ItemAdvancedRobeLegs), craftingAspects, " C ","CRC", 'C', new ItemStack(WGContent.ItemMaterial,1,5), 'R', new ItemStack(ConfigItems.itemLegsRobe));
				
		craftingAspects = new AspectList().add(Aspect.ENTROPY,10).add(Aspect.FIRE, 10);
		registerArcaneRecipe("WGBAUBLES","_WOLFVAMBRACES",new ItemStack(WGContent.ItemMagicalBaubles,1,2), craftingAspects, " P ","PVP", 'P', new ItemStack(WGContent.ItemMaterial,1,6), 'V', "travelgearVambraceBase");

		craftingAspects = new AspectList().add(Aspect.ORDER,10).add(Aspect.EARTH, 10);
		registerArcaneRecipe("WGBAUBLES","_KNOCKBACKSHOULDERS",new ItemStack(WGContent.ItemMagicalBaubles,1,1), craftingAspects, " S ","ETE", 'E', new ItemStack(ConfigItems.itemShard,1,3), 'S', "travelgearShoulderBase", 'T',"ingotThaumium");
		
		ItemStack luckyCoin = new ItemStack(ConfigItems.itemResource,1,18);
		luckyCoin.addEnchantment(Enchantment.fortune, 1);
		luckyCoin.addEnchantment(Enchantment.looting, 1);
		craftingAspects = new AspectList().add(Aspect.ORDER,30);
		registerArcaneRecipe("WGBAUBLES","_COIN",luckyCoin, craftingAspects, "BCB","CCC","BCB", 'C',  new ItemStack(ConfigItems.itemResource,1,18), 'B', Items.enchanted_book);

		/**
		 * INFUSION
		 */
		
		infusionAspects = new AspectList().add(Aspect.MINE, 8).add(Aspect.TOOL, 4).add(Aspect.MOTION, 4).add(Aspect.AIR,8);
		registerInfusionRecipe("WGBAUBLES","_HASTEVAMBRACES",new ItemStack(WGContent.ItemMagicalBaubles,1,3),2,infusionAspects,OreDictionary.getOres("travelgearVambraceBase").get(0),new ItemStack[] {new ItemStack(Items.gold_ingot),new ItemStack(Items.sugar),new ItemStack(Items.potionitem,1,8194),new ItemStack(Items.sugar)});

		ItemStack stack_ingot = !OreDictionary.getOres("ingotSilver").isEmpty()?OreDictionary.getOres("ingotSilver").get(0): new ItemStack(Items.iron_ingot);
		infusionAspects = new AspectList().add(Aspect.FLIGHT,16).add(Aspect.MOTION, 8).add(Aspect.AIR,16);
		registerInfusionRecipe("WGBAUBLES","_DOUBLEJUMPSHOULDERS",new ItemStack(WGContent.ItemMagicalBaubles,1,0),2,infusionAspects,OreDictionary.getOres("travelgearShoulderBase").get(0),new ItemStack[] {new ItemStack(Items.feather),stack_ingot,new ItemStack(Items.feather),new ItemStack(ConfigItems.itemShard,1,0),new ItemStack(Items.feather),stack_ingot});

		infusionAspects = new AspectList().add(Aspect.AIR,16).add(Aspect.WEAPON, 8).add(Aspect.ORDER,8);
		registerInfusionRecipe("WGBAUBLES","_SNIPERRING",new ItemStack(WGContent.ItemMagicalBaubles,1,5),2,infusionAspects,new ItemStack(ConfigItems.itemBaubleBlanks,1,1),new ItemStack[] {new ItemStack(ConfigItems.itemPrimalArrow,1,0),new ItemStack(ConfigItems.itemPrimalArrow,1,1),new ItemStack(ConfigItems.itemPrimalArrow,1,2),new ItemStack(ConfigItems.itemPrimalArrow,1,3),new ItemStack(ConfigItems.itemPrimalArrow,1,4),new ItemStack(ConfigItems.itemPrimalArrow,1,5)});

		infusionAspects = new AspectList().add(Aspect.GREED,32).add(Aspect.TOOL,16);
		registerInfusionRecipe("WGBAUBLES","_LUCKRING",new ItemStack(WGContent.ItemMagicalBaubles,1,4),3,infusionAspects,luckyCoin,new ItemStack[] {new ItemStack(Items.gold_ingot),new ItemStack(Items.dye,1,4),stack_ingot,new ItemStack(Items.dye,1,4),stack_ingot,new ItemStack(Items.dye,1,4),stack_ingot,new ItemStack(Items.dye,1,4)});

		infusionAspects = new AspectList().add(Aspect.TRAVEL,4).add(Aspect.MIND, 6).add(Aspect.TOOL,2);
		registerInfusionRecipe("LABYRINTHSTRING","",new ItemStack(WGContent.ItemMaterial,1,11),2,infusionAspects,new ItemStack(ConfigBlocks.blockMagicalLog,1,0),new ItemStack[] {new ItemStack(Items.ender_pearl),new ItemStack(WGContent.ItemMaterial,1,0),new ItemStack(WGContent.ItemMaterial,1,0),new ItemStack(WGContent.ItemMaterial,1,0)});


		
		//Primordial Gear
		if (WGConfig.modulePrimal) {
			infusionAspects = new AspectList().add(Aspect.AIR,16).add(Aspect.FIRE,16).add(Aspect.EARTH,16).add(Aspect.WATER,16).add(Aspect.ORDER,16).add(Aspect.ENTROPY,16);
			registerInfusionRecipe("EMPOWERPEARL","",new ItemStack(ConfigItems.itemEldritchObject,1,3),3,infusionAspects,new ItemStack(WGContent.ItemMaterial,1,12),new ItemStack[] {new ItemStack(ConfigItems.itemShard,1,0),new ItemStack(ConfigItems.itemShard,1,1),new ItemStack(ConfigItems.itemShard,1,2),new ItemStack(ConfigItems.itemShard,1,3),new ItemStack(ConfigItems.itemShard,1,4),new ItemStack(ConfigItems.itemShard,1,5)});

			if (WGConfig.moduleGemcutting) {
				infusionAspects = new AspectList().add(Aspect.MAGIC,32).add(Aspect.CRYSTAL,16).add(Aspect.TOOL,8).add(Aspect.AIR,8).add(Aspect.FIRE,8).add(Aspect.WATER,8).add(Aspect.EARTH,8).add(Aspect.ORDER,8).add(Aspect.ENTROPY,8);
				registerInfusionRecipe("PRIMORDIALGLOVE","",new ItemStack(WGContent.ItemPrimordialGlove),6,infusionAspects,new ItemStack(ConfigBlocks.blockStoneDevice,1,11),new ItemStack[] {new ItemStack(WGContent.ItemMaterial,1,5), new ItemStack(ConfigItems.itemResource,1,17), new ItemStack(ConfigItems.itemResource,1,16), new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemResource,1,16), new ItemStack(ConfigItems.itemResource,1,17) });
			}
			infusionAspects = new AspectList().add(Aspect.MAGIC,64).add(Aspect.METAL,128).add(Aspect.WEAPON,64).add(Aspect.AIR,32).add(Aspect.FIRE,32).add(Aspect.WATER,32).add(Aspect.EARTH,32).add(Aspect.ORDER,32).add(Aspect.ENTROPY,32);
			registerInfusionRecipe("PRIMORDIALWEAPONRY","_CLAYMORE",new ItemStack(WGContent.ItemPrimordialSword),10,infusionAspects,new ItemStack(WGContent.BlockMetalDevice,1,1),new ItemStack[] {new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemSwordVoid), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(ConfigItems.itemWandRod,1,0), new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemWispEssence),new ItemStack(ConfigItems.itemSwordVoid) });

			infusionAspects = new AspectList().add(Aspect.MAGIC,64).add(Aspect.METAL,128).add(Aspect.TOOL,32).add(Aspect.WEAPON,32).add(Aspect.AIR,32).add(Aspect.FIRE,32).add(Aspect.WATER,32).add(Aspect.EARTH,32).add(Aspect.ORDER,32).add(Aspect.ENTROPY,32);
			registerInfusionRecipe("PRIMORDIALWEAPONRY","_HAMMER",new ItemStack(WGContent.ItemPrimordialHammer),10,infusionAspects,new ItemStack(WGContent.BlockMetalDevice,1,1),new ItemStack[] {new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemPickVoid), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(ConfigItems.itemWandRod,1,0), new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemWispEssence),new ItemStack(ConfigItems.itemSwordVoid) });

			infusionAspects = new AspectList().add(Aspect.MAGIC,64).add(Aspect.METAL,128).add(Aspect.TOOL,32).add(Aspect.WEAPON,32).add(Aspect.AIR,32).add(Aspect.FIRE,32).add(Aspect.WATER,32).add(Aspect.EARTH,32).add(Aspect.ORDER,32).add(Aspect.ENTROPY,32);
			registerInfusionRecipe("PRIMORDIALWEAPONRY","_GREATAXE",new ItemStack(WGContent.ItemPrimordialAxe),10,infusionAspects,new ItemStack(WGContent.BlockMetalDevice,1,1),new ItemStack[] {new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemAxeVoid), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(ConfigItems.itemWandRod,1,0), new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemWispEssence),new ItemStack(ConfigItems.itemAxeVoid) });

			//
			infusionAspects = new AspectList().add(Aspect.MAGIC,64).add(Aspect.METAL,128).add(Aspect.ARMOR,32).add(Aspect.AIR,32).add(Aspect.FIRE,32).add(Aspect.WATER,32).add(Aspect.EARTH,32).add(Aspect.ORDER,32).add(Aspect.ENTROPY,32);
			registerInfusionRecipe("PRIMORDIALARMOR","_HELMET",new ItemStack(WGContent.ItemPrimordialHelm),10,infusionAspects,new ItemStack(WGContent.BlockMetalDevice,1,1),new ItemStack[] {new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemResource,1,16), new ItemStack(ConfigItems.itemHelmetFortress), new ItemStack(ConfigItems.itemResource,1,16), new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(ConfigItems.itemWispEssence) });
			//ThaumcraftApi.addWarpToItem(new ItemStack(WGContent.ItemPrimordialAxe), 2);
			infusionAspects = new AspectList().add(Aspect.MAGIC,64).add(Aspect.METAL,128).add(Aspect.ARMOR,32).add(Aspect.AIR,32).add(Aspect.FIRE,32).add(Aspect.WATER,32).add(Aspect.EARTH,32).add(Aspect.ORDER,32).add(Aspect.ENTROPY,32);
			registerInfusionRecipe("PRIMORDIALARMOR","_CUIRASS",new ItemStack(WGContent.ItemPrimordialChest),10,infusionAspects,new ItemStack(WGContent.BlockMetalDevice,1,1),new ItemStack[] {new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemResource,1,16), new ItemStack(ConfigItems.itemChestFortress), new ItemStack(ConfigItems.itemResource,1,16), new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(ConfigItems.itemWispEssence) });
			//ThaumcraftApi.addWarpToItem(new ItemStack(WGContent.ItemPrimordialAxe), 2);
			infusionAspects = new AspectList().add(Aspect.MAGIC,64).add(Aspect.METAL,128).add(Aspect.ARMOR,32).add(Aspect.AIR,32).add(Aspect.FIRE,32).add(Aspect.WATER,32).add(Aspect.EARTH,32).add(Aspect.ORDER,32).add(Aspect.ENTROPY,32);
			registerInfusionRecipe("PRIMORDIALARMOR","_GREAVES",new ItemStack(WGContent.ItemPrimordialLegs),10,infusionAspects,new ItemStack(WGContent.BlockMetalDevice,1,1),new ItemStack[] {new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemResource,1,16), new ItemStack(ConfigItems.itemLegsFortress), new ItemStack(ConfigItems.itemResource,1,16), new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(ConfigItems.itemWispEssence) });
			//ThaumcraftApi.addWarpToItem(new ItemStack(WGContent.ItemPrimordialAxe), 2);
			infusionAspects = new AspectList().add(Aspect.MAGIC,64).add(Aspect.METAL,128).add(Aspect.ARMOR,32).add(Aspect.AIR,32).add(Aspect.FIRE,32).add(Aspect.WATER,32).add(Aspect.EARTH,32).add(Aspect.ORDER,32).add(Aspect.ENTROPY,32);
			registerInfusionRecipe("PRIMORDIALARMOR","_BOOTS",new ItemStack(WGContent.ItemPrimordialBoots),10,infusionAspects,new ItemStack(WGContent.BlockMetalDevice,1,1),new ItemStack[] {new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemResource,1,16), new ItemStack(ConfigItems.itemBootsTraveller), new ItemStack(ConfigItems.itemResource,1,16), new ItemStack(ConfigItems.itemEldritchObject,1,3), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(ConfigItems.itemWispEssence) });
			//ThaumcraftApi.addWarpToItem(new ItemStack(WGContent.ItemPrimordialAxe), 2);

			//		for(ItemPrimordialArmor.PrimordialArmorUpgrade pau : ItemPrimordialArmor.PrimordialArmorUpgrade.values())
			//		{
			//			registerInfusionRecipe("PRIMODIALARMORUPGRADE_"+pau,"_HELMET",new Object[]{"primordialUpgrade",new NBTTagByte((byte)pau.ordinal())},8,pau.getAspects(),new ItemStack(WGContent.ItemPrimordialHelm),pau.getCompenents());
			//			registerInfusionRecipe("PRIMODIALARMORUPGRADE_"+pau,"_CUIRASS",new Object[]{"primordialUpgrade",new NBTTagByte((byte)pau.ordinal())},8,pau.getAspects(),new ItemStack(WGContent.ItemPrimordialChest),pau.getCompenents());
			//			registerInfusionRecipe("PRIMODIALARMORUPGRADE_"+pau,"_GREAVES",new Object[]{"primordialUpgrade",new NBTTagByte((byte)pau.ordinal())},8,pau.getAspects(),new ItemStack(WGContent.ItemPrimordialLegs),pau.getCompenents());
			//			registerInfusionRecipe("PRIMODIALARMORUPGRADE_"+pau,"_BOOTS",new Object[]{"primordialUpgrade",new NBTTagByte((byte)pau.ordinal())},8,pau.getAspects(),new ItemStack(WGContent.ItemPrimordialBoots),pau.getCompenents());
			//		}
			infusionAspects = new AspectList().add(Aspect.SENSES,32).add(Aspect.AURA,16).add(Aspect.ARMOR,16);
			registerInfusionRecipe("HELMGOGGLES","_PRIMORDIAL",new Object[]{"goggles",new NBTTagByte((byte)1)},5,infusionAspects,new ItemStack(WGContent.ItemPrimordialHelm,1,32767),new ItemStack[] { new ItemStack(Items.slime_ball), new ItemStack(ConfigItems.itemGoggles, 1, 32767) });
			infusionAspects = new AspectList().add(Aspect.MIND,64).add(Aspect.HEAL,64).add(Aspect.ARMOR, 16);
			registerInfusionRecipe("MASKGRINNINGDEVIL","_PRIMORDIAL",new Object[]{"mask",new NBTTagByte((byte)0)},8,infusionAspects,new ItemStack(WGContent.ItemPrimordialHelm,1,32767),new ItemStack[] { new ItemStack(Items.dye, 1, 0), new ItemStack(Items.iron_ingot), new ItemStack(Items.leather), new ItemStack(ConfigBlocks.blockCustomPlant, 1, 2), new ItemStack(ConfigItems.itemZombieBrain), new ItemStack(Items.iron_ingot) });
			infusionAspects = new AspectList().add(Aspect.ENTROPY,64).add(Aspect.DEATH,64).add(Aspect.ARMOR, 16);
			registerInfusionRecipe("MASKANGRYGHOST","_PRIMORDIAL",new Object[]{"mask",new NBTTagByte((byte)1)},8,infusionAspects,new ItemStack(WGContent.ItemPrimordialHelm,1,32767),new ItemStack[] { new ItemStack(Items.dye, 1, 15), new ItemStack(Items.iron_ingot), new ItemStack(Items.leather), new ItemStack(Items.poisonous_potato), new ItemStack(Items.skull, 1, 1), new ItemStack(Items.iron_ingot) });
			infusionAspects = new AspectList().add(Aspect.UNDEAD,64).add(Aspect.LIFE,64).add(Aspect.ARMOR, 16);
			registerInfusionRecipe("MASKSIPPINGFIEND","_PRIMORDIAL",new Object[]{"mask",new NBTTagByte((byte)2)},8,infusionAspects,new ItemStack(WGContent.ItemPrimordialHelm,1,32767),new ItemStack[] { new ItemStack(Items.dye, 1, 1), new ItemStack(Items.iron_ingot), new ItemStack(Items.leather), new ItemStack(Items.ghast_tear), new ItemStack(Items.milk_bucket), new ItemStack(Items.iron_ingot) });
		}
		
		/**
		 * ENCHANTMENT
		 */
		//TODO enchant recipes
		infusionAspects = new AspectList().add(Aspect.DARKNESS, 4).add(Aspect.CRYSTAL, 8).add(Aspect.MAGIC, 8);
		if (WGConfig.moduleGemcutting) 
			registerInfusionEnchantmentRecipe("ENCH_INVISIBLEGEAR", "", WGContent.enc_invisibleGear, 2, infusionAspects, new ItemStack[] {new ItemStack(Items.quartz),new ItemStack(ConfigItems.itemResource,1,14),new ItemStack(WGContent.ItemMaterial,1,13)});
		else 
			registerInfusionEnchantmentRecipe("ENCH_INVISIBLEGEAR", "", WGContent.enc_invisibleGear, 2, infusionAspects, new ItemStack[] {new ItemStack(Items.quartz),new ItemStack(ConfigItems.itemResource,1,14),new ItemStack(ConfigItems.itemEldritchObject,1,0)});
		WGModCompat.thaumicTinkererRegisterEnchantment(WGContent.enc_invisibleGear, "witchinggadgets:textures/gui/research/icon_ench_invisGear.png", new AspectList().add(Aspect.AIR, 25).add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 15), "ENCH_INVISIBLEGEAR");

		infusionAspects = new AspectList().add(Aspect.LIGHT, 4).add(Aspect.SENSES, 8).add(Aspect.MAGIC, 8);
		registerInfusionEnchantmentRecipe("ENCH_UNVEILING", "", WGContent.enc_unveiling, 2, infusionAspects, new ItemStack[] {new ItemStack(Items.golden_carrot),new ItemStack(ConfigItems.itemResource,1,14)});
		WGModCompat.thaumicTinkererRegisterEnchantment(WGContent.enc_unveiling, "witchinggadgets:textures/gui/research/icon_ench_unveiling.png", new AspectList().add(Aspect.AIR, 25).add(Aspect.ORDER, 20).add(Aspect.WATER, 10), "ENCH_UNVEILING");

		infusionAspects = new AspectList().add(Aspect.MOTION, 6).add(Aspect.DARKNESS, 8).add(Aspect.MAGIC, 8);
		registerInfusionEnchantmentRecipe("ENCH_STEALTH", "", WGContent.enc_stealth, 2, infusionAspects, new ItemStack[] {new ItemStack(Items.potionitem,1,8206),new ItemStack(ConfigItems.itemResource,1,14)});
		WGModCompat.thaumicTinkererRegisterEnchantment(WGContent.enc_stealth, "witchinggadgets:textures/gui/research/icon_ench_stealth.png", new AspectList().add(Aspect.AIR, 10).add(Aspect.ORDER, 20).add(Aspect.EARTH, 10), "ENCH_STEALTH");

		infusionAspects = new AspectList().add(Aspect.WEAPON, 12).add(Aspect.DARKNESS, 8).add(Aspect.MAGIC, 4);
		registerInfusionEnchantmentRecipe("ENCH_BACKSTAB", "", WGContent.enc_backstab, 3, infusionAspects, new ItemStack[] {new ItemStack(Items.iron_sword),new ItemStack(Items.potionitem,1,8206),new ItemStack(ConfigItems.itemResource,1,14)});
		WGModCompat.thaumicTinkererRegisterEnchantment(WGContent.enc_backstab, "witchinggadgets:textures/gui/research/icon_ench_backstab.png", new AspectList().add(Aspect.AIR, 20).add(Aspect.ENTROPY, 20).add(Aspect.FIRE, 20), "ENCH_BACKSTAB");

		infusionAspects = new AspectList().add(Aspect.ARMOR, 12).add(Aspect.TRAP, 8).add(Aspect.MAGIC, 4);
		registerInfusionEnchantmentRecipe("ENCH_RIDEPROTECT", "", WGContent.enc_rideProtect, 3, infusionAspects, new ItemStack[] {new ItemStack(ConfigItems.itemResource,1,14),new ItemStack(Blocks.piston),new ItemStack(Blocks.piston)});
		WGModCompat.thaumicTinkererRegisterEnchantment(WGContent.enc_rideProtect, "witchinggadgets:textures/gui/research/icon_ench_rideProtect.png", new AspectList().add(Aspect.AIR, 20).add(Aspect.ENTROPY, 20).add(Aspect.ORDER, 20), "ENCH_RIDEPROTECT");

		infusionAspects = new AspectList().add(Aspect.SOUL, 8).add(Aspect.MAGIC, 12).add(Aspect.GREED, 4).add(Aspect.ELDRITCH, 8);
		registerInfusionEnchantmentRecipe("ENCH_SOULBOUND", "", WGContent.enc_soulbound, 1, infusionAspects, new ItemStack[] {new ItemStack(Items.ender_eye), new ItemStack(Items.ender_pearl), new ItemStack(Items.name_tag)});
		WGModCompat.thaumicTinkererRegisterEnchantment(WGContent.enc_soulbound, "witchinggadgets:textures/gui/research/icon_ench_soulbound.png", new AspectList().add(Aspect.AIR, 10).add(Aspect.ENTROPY, 10).add(Aspect.ORDER, 20), "ENCH_SOULBOUND");
		
		//book special
		ItemStack soulBook = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(WGContent.enc_soulbound, 1));
		craftingAspects = new AspectList().add(Aspect.ORDER,30).add(Aspect.AIR, 15).add(Aspect.ENTROPY, 15);
		registerArcaneRecipe("ENCH_SOULBOUND", "_BOOK", soulBook, craftingAspects, 
				" E ","GBG"," P ", 
				'E', new ItemStack(Items.ender_eye), 
				'B', new ItemStack(Items.enchanted_book),
				'P', new ItemStack(Items.ender_pearl),
				'G', new ItemStack(Items.gold_ingot));	

		
		/**
		 * ALCHEMY
		 */
		alchemyAspects = new AspectList().add(Aspect.PLANT,4).add(Aspect.ENTROPY,4).add(Aspect.MAGIC,4);
		registerAlchemyRecipe("ROSEVINE","", new ItemStack(WGContent.BlockRoseVine), new ItemStack(Blocks.double_plant,1,4), alchemyAspects);
		alchemyAspects = new AspectList().add(Aspect.PLANT,2).add(Aspect.LIFE,1);
		registerAlchemyRecipe("ALCHEMICALTRANSMOGRIFY","_GRASS", new ItemStack(Blocks.grass), new ItemStack(Blocks.dirt), alchemyAspects);
		alchemyAspects = new AspectList().add(Aspect.PLANT,2).add(Aspect.DARKNESS,1);
		registerAlchemyRecipe("ALCHEMICALTRANSMOGRIFY","_MYCEL", new ItemStack(Blocks.mycelium), new ItemStack(Blocks.dirt), alchemyAspects);
		alchemyAspects = new AspectList().add(Aspect.ENTROPY,3);
		registerAlchemyRecipe("ALCHEMICALTRANSMOGRIFY","_SAND", new ItemStack(Blocks.sand), new ItemStack(Blocks.cobblestone), alchemyAspects);
		alchemyAspects = new AspectList().add(Aspect.CRYSTAL,3);
		registerAlchemyRecipe("ALCHEMICALTRANSMOGRIFY","_FLINT", new ItemStack(Items.flint), new ItemStack(Blocks.gravel), alchemyAspects);

		alchemyAspects = new AspectList().add(Aspect.METAL,1).add(Aspect.ORDER,1);
		registerAlchemyRecipe("PURECINNABAR","", new ItemStack(ConfigItems.itemNugget, 1, 21), "oreCinnabar", alchemyAspects);
		addBlastTrippling("Cinnabar");

		if (WGConfig.moduleGemcutting) {
			alchemyAspects = new AspectList().add(Aspect.VOID,2).add(Aspect.CRYSTAL,4);
			registerAlchemyRecipe("CRYSTALCAPSULE","", new ItemStack(WGContent.ItemCapsule), new ItemStack(Items.bucket), alchemyAspects);
		}
		
		for(int iOre=0; iOre<ItemClusters.subNames.length; iOre++)
		{
			if(WGConfig.allowClusters)
			{
				alchemyAspects = new AspectList().add(Aspect.METAL,1).add(Aspect.ORDER,1);
				if(!OreDictionary.getOres("ore"+ItemClusters.subNames[iOre]).isEmpty() && !OreDictionary.getOres("ingot"+ItemClusters.subNames[iOre]).isEmpty())
				{
					registerAlchemyRecipe("METALLURGICPERFECTION_CLUSTERS","_"+ItemClusters.subNames[iOre], new ItemStack(WGContent.ItemCluster, 1, iOre), "ore"+ItemClusters.subNames[iOre], alchemyAspects);
					setupCluster(ItemClusters.subNames[iOre]);
				}
			}
			if(WGConfig.allowTransmutations)
			{
				boolean bb = !OreDictionary.getOres("nugget"+ItemClusters.subNames[iOre]).isEmpty() && !OreDictionary.getOres("ingot"+ItemClusters.subNames[iOre]).isEmpty();
				if(bb)
				{
					ItemStack ingot = OreDictionary.getOres("ingot"+ItemClusters.subNames[iOre]).get(0);
					alchemyAspects = ThaumcraftApi.objectTags.get( Arrays.asList(new Object[] { ingot.getItem(), Integer.valueOf(ingot.getItemDamage()) }) );
					if(alchemyAspects==null)
						alchemyAspects = new AspectList();
					alchemyAspects.remove(Aspect.METAL);
					alchemyAspects.add(Aspect.METAL, 2);
					Iterator<Entry<Aspect,Integer>> it = alchemyAspects.aspects.entrySet().iterator();
					while(it.hasNext())
					{
						Entry<Aspect,Integer> e = it.next();
						if(e.getKey()==null||e.getValue()==null)
							it.remove();
					}
					ItemStack nuggets = Utilities.copyStackWithSize(OreDictionary.getOres("nugget"+ItemClusters.subNames[iOre]).get(0), 3);
					registerAlchemyRecipe("METALLURGICPERFECTION_TRANSMUTATION","_"+ItemClusters.subNames[iOre], nuggets, "nugget"+ItemClusters.subNames[iOre], alchemyAspects);
				}
			}
		}

		/**
		 * SPINNING
		 */
		SpinningRecipe spin_Thread = new SpinningRecipe(new ItemStack(WGContent.ItemMaterial,2,0), new Object[] {Items.string, Items.string, Items.string, Items.string});
		SpinningRecipe.addRecipe(spin_Thread);

		SpinningRecipe spin_goldThread = new SpinningRecipe(new ItemStack(WGContent.ItemMaterial,2,1), new Object[] {Items.string, Items.string, Items.gold_nugget, Items.gold_nugget});
		SpinningRecipe.addRecipe(spin_goldThread);

		SpinningRecipe spin_thaumiumThread = new SpinningRecipe(new ItemStack(WGContent.ItemMaterial,2,2), new Object[] {Items.string, Items.string, "nuggetThaumium", "nuggetThaumium"});
		SpinningRecipe.addRecipe(spin_thaumiumThread);
		/**
		WeavingRecipe weave_void = new WeavingRecipe("BAGOFTRICKS", new ItemStack(WGContent.ItemMaterial,2,3),new AspectList().add(Aspect.VOID, 8), new Object[] {new ItemStack(WGContent.ItemMaterial,1,0), new ItemStack(WGContent.ItemMaterial,1,0), new ItemStack(WGContent.ItemMaterial,1,0), new ItemStack(WGContent.ItemMaterial,1,0), new ItemStack(WGContent.ItemMaterial,1,2), new ItemStack(WGContent.ItemMaterial,1,2)});
		WitchingGadgets.instance.customRecipeHandler.addRecipe(weave_void);

		ItemStack fabStack = new ItemStack(ConfigItems.itemResource, 2, 7);
		WeavingRecipe weave_enchFabric = new WeavingRecipe("ENCHFABRIC", fabStack,new AspectList().add(Aspect.MAGIC, 2), new Object[] {new ItemStack(WGContent.ItemMaterial,1,0), new ItemStack(WGContent.ItemMaterial,1,0)});
		WitchingGadgets.instance.customRecipeHandler.addRecipe(weave_enchFabric);

		WeavingRecipe weave_fleece = new WeavingRecipe("ADVANCEDROBES", new ItemStack(WGContent.ItemMaterial,2,5),new AspectList().add(Aspect.MAGIC, 8).add(Aspect.TAINT, 2), new Object[] {new ItemStack(WGContent.ItemMaterial,1,0), new ItemStack(WGContent.ItemMaterial,1,0), new ItemStack(WGContent.ItemMaterial,1,0), new ItemStack(WGContent.ItemMaterial,1,2)});
		WitchingGadgets.instance.customRecipeHandler.addRecipe(weave_fleece);
		 */
		registerCompoundRecipe("GEMCUTTING","",new AspectList(),1,2,1, new Object[] {new ItemStack(WGContent.ItemMaterial,1,8),new ItemStack(ConfigBlocks.blockTable)} );


		craftingAspects = new AspectList().add(Aspect.AIR, 15).add(Aspect.ORDER, 15);
		registerCompoundRecipe("LOOM","",craftingAspects,2,2,3,
				new ItemStack(Blocks.fence), null,
				new ItemStack(Blocks.iron_bars), null,
				new ItemStack(Blocks.fence), null,
				"plankWood", "plankWood",
				"slabWood", "slabWood",
				"plankWood", "plankWood" );

		ItemStack ifBlFrStair = new ItemStack(TileEntityBlastfurnace.stairBlock,1,TileEntityBlastfurnace.stairBlock!=Blocks.stone_brick_stairs?1:0);
		craftingAspects = new AspectList().add(Aspect.FIRE, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50);
		registerCompoundRecipe("INFERNALBLASTFURNACE","",craftingAspects,3,3,3,
				ifBlFrStair,ifBlFrStair,ifBlFrStair,
				ifBlFrStair,new ItemStack(Blocks.lava),ifBlFrStair,
				ifBlFrStair,ifBlFrStair,ifBlFrStair,

				new ItemStack(TileEntityBlastfurnace.brickBlock[9]),new ItemStack(TileEntityBlastfurnace.brickBlock[10]),new ItemStack(TileEntityBlastfurnace.brickBlock[11]),
				new ItemStack(TileEntityBlastfurnace.brickBlock[12]),new ItemStack(TileEntityBlastfurnace.brickBlock[13]),new ItemStack(TileEntityBlastfurnace.brickBlock[14]),
				new ItemStack(TileEntityBlastfurnace.brickBlock[15]),new ItemStack(TileEntityBlastfurnace.brickBlock[16]),new ItemStack(TileEntityBlastfurnace.brickBlock[17]),

				new ItemStack(TileEntityBlastfurnace.brickBlock[0]),new ItemStack(TileEntityBlastfurnace.brickBlock[1]),new ItemStack(TileEntityBlastfurnace.brickBlock[2]),
				new ItemStack(TileEntityBlastfurnace.brickBlock[3]),new ItemStack(TileEntityBlastfurnace.brickBlock[4]),new ItemStack(TileEntityBlastfurnace.brickBlock[5]),
				new ItemStack(TileEntityBlastfurnace.brickBlock[6]),new ItemStack(TileEntityBlastfurnace.brickBlock[7]),new ItemStack(TileEntityBlastfurnace.brickBlock[8]) );

		WandTriggerRegistry.registerWandBlockTrigger(WitchingGadgets.instance.wgWandManager, 0, Blocks.fence, -1);
		WandTriggerRegistry.registerWandBlockTrigger(WitchingGadgets.instance.wgWandManager, 0, Blocks.iron_bars, -1);

		if(WGModCompat.railcraftAllowBlastFurnace())
			WandTriggerRegistry.registerWandBlockTrigger(WitchingGadgets.instance.wgWandManager, 1, TileEntityBlastfurnace.brickBlock[0], -1);
		else
		{
			WandTriggerRegistry.registerWandBlockTrigger(WitchingGadgets.instance.wgWandManager, 1, TileEntityBlastfurnace.brickBlock[0], -1);
			WandTriggerRegistry.registerWandBlockTrigger(WitchingGadgets.instance.wgWandManager, 1, TileEntityBlastfurnace.brickBlock[4], -1);
			WandTriggerRegistry.registerWandBlockTrigger(WitchingGadgets.instance.wgWandManager, 1, TileEntityBlastfurnace.brickBlock[10], -1);
		}
		WandTriggerRegistry.registerWandBlockTrigger(WitchingGadgets.instance.wgWandManager, 1, TileEntityBlastfurnace.stairBlock, -1);

		addBlastTrippling("Iron");
		addBlastTrippling("Gold");
		addBlastTrippling("Copper");
		addBlastTrippling("Tin");
		addBlastTrippling("Silver");
		addBlastTrippling("Lead");
		InfernalBlastfurnaceRecipe.addRecipe(new ItemStack(ConfigItems.itemResource,3,3), "clusterCinnabar",1, 440,false).addBonus(new ItemStack(ConfigItems.itemNugget,1,5));
		for(String name : OreDictionary.getOreNames())
			if(name.startsWith("cluster"))
				addBlastTrippling(name.substring("cluster".length()));

		if(WGModCompat.loaded_TCon)
		{
			if(WGConfig.smelteryResultForClusters>0)
			{	
				WGModCompat.addTConSmelteryRecipe("clusterIron", "blockIron", 600, "iron.molten", WGConfig.smelteryResultForClusters);
				WGModCompat.addTConSmelteryRecipe("clusterGold", "blockGold", 400, "gold.molten", WGConfig.smelteryResultForClusters);
				WGModCompat.addTConSmelteryRecipe("clusterCopper", "blockCopper", 350, "copper.molten", WGConfig.smelteryResultForClusters);
				WGModCompat.addTConSmelteryRecipe("clusterTin", "blockTin", 400, "tin.molten", WGConfig.smelteryResultForClusters);
				WGModCompat.addTConSmelteryRecipe("clusterSilver", "blockSilver", 550, "silver.molten", WGConfig.smelteryResultForClusters);
				WGModCompat.addTConSmelteryRecipe("clusterLead", "blockLead", 400, "lead.molten", WGConfig.smelteryResultForClusters);
			}
			WGModCompat.addTConDryingRecipe(new ItemStack(ConfigItems.itemZombieBrain), 20*6*5, new ItemStack(WGContent.ItemMagicFoodstuffs,1,2));
		}

	}

	public static void registerResearch()
	{
		AspectList researchAspects;
		ResearchPage[] pages;
		
		//WGPOTIONS
		researchAspects = new AspectList();
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.WGPOTIONS.1"), new ResearchPage("witchinggadgets_research_page.WGPOTIONS.2")};
		getResearchItem("WGPOTIONS", "WITCHGADG", researchAspects, 0, 0, 0, new ResourceLocation("witchinggadgets:textures/gui/research/icon_potioneffects.png")).setRound().setAutoUnlock().setPages(pages).registerResearchItem();

		
		//SPINNINGWHEEL
		researchAspects = new AspectList();
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.SPINNINGWHEEL.1"), new ResearchPage("witchinggadgets_research_page.SPINNINGWHEEL.2"), new ResearchPage((ShapedArcaneRecipe) recipeList.get("SPINNINGWHEEL")), new ResearchPage("witchinggadgets_research_page.SPINNINGWHEEL.r1"), new ResearchPage("witchinggadgets_research_page.SPINNINGWHEEL.r2"), new ResearchPage("witchinggadgets_research_page.SPINNINGWHEEL.r3")};
		getResearchItem("SPINNINGWHEEL", "WITCHGADG", researchAspects, 4, 0, 0, new ItemStack(WGContent.BlockWoodenDevice,1,0)).setRound().setAutoUnlock().setPages(pages).registerResearchItem();
		
		//ORIGINAL ENCHFABRIC
		//getFakeResearchItem("ENCHFABRIC", "ARTIFICE", 8,-1, new ItemStack(ConfigItems.itemResource, 1, 7)).registerResearchItem();
		new FakeResearchItem("WG.ENCHFABRIC", "WITCHGADG", "ENCHFABRIC", "ARTIFICE", 8, -1, ResearchCategories.getResearch("ENCHFABRIC").icon_item).registerResearchItem();
		
		//ADVANCED ROBES
		researchAspects = new AspectList().add(Aspect.CLOTH, 3).add(Aspect.MAGIC, 4).add(Aspect.TAINT, 1);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.ADVANCEDROBES.1"), new ResearchPage((ShapelessArcaneRecipe) recipeList.get("ADVANCEDROBES_CLOTH")), new ResearchPage((ShapedArcaneRecipe) recipeList.get("ADVANCEDROBES_CHEST")), new ResearchPage((ShapedArcaneRecipe) recipeList.get("ADVANCEDROBES_LEGS"))};
		getResearchItem("ADVANCEDROBES", "WITCHGADG", researchAspects, 6, -1, 3, new ItemStack(WGContent.ItemMaterial,1,5)).setParents(new String[] { "WG.ENCHFABRIC", "SPINNINGWHEEL" }).setPages(pages).setConcealed().setSecondary().registerResearchItem();
		
		//TODO bags
		
		if (WGConfig.moduleBag) {
			
			//BAG CLOTH
			researchAspects = new AspectList().add(Aspect.CLOTH, 2).add(Aspect.VOID, 2).add(Aspect.HUNGER, 2);
			pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.BAGOFTRICKS.1"), new ResearchPage((ShapelessArcaneRecipe) recipeList.get("BAGOFTRICKS_CLOTH")), new ResearchPage((ShapedArcaneRecipe) recipeList.get("BAGOFTRICKS_BAG"))};
			getResearchItem("BAGOFTRICKS", "WITCHGADG", researchAspects, 5, 1, 1, new ItemStack(WGContent.ItemBag,1,0)).setParents(new String[] { "SPINNINGWHEEL" }).setConcealed().setPages(pages).registerResearchItem();
			
			if (WGConfig.bagEnder) {
				//ENDERBAG
				researchAspects = new AspectList().add(Aspect.CLOTH, 2).add(Aspect.ELDRITCH, 3).add(Aspect.VOID, 3);
				pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.ENDERBAG.1"), new ResearchPage((InfusionRecipe) recipeList.get("ENDERBAG"))};
				getResearchItem("ENDERBAG", "WITCHGADG", researchAspects, 7, 2, 1, new ItemStack(WGContent.ItemBag,1,2)).setPages(pages).setParents(new String[] { "BAGOFTRICKS","INFUSION" }).setHidden().setSecondary().setItemTriggers(new ItemStack[] { new ItemStack(Blocks.ender_chest,1,32767) }).setAspectTriggers(new Aspect[] { Aspect.ELDRITCH }).registerResearchItem();
			}
			if (WGConfig.bagHungry) {
				//HUNGERBAG
				researchAspects = new AspectList().add(Aspect.CLOTH, 2).add(Aspect.HUNGER, 3).add(Aspect.VOID, 3);
				pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.HUNGERBAG.1"), new ResearchPage((IArcaneRecipe) recipeList.get("HUNGERBAG"))};
				getResearchItem("HUNGERBAG", "WITCHGADG", researchAspects, 7, 1, 1, new ItemStack(WGContent.ItemBag,1,3)).setPages(pages).setParents(new String[] { "BAGOFTRICKS","HUNGRYCHEST" }).setConcealed().setSecondary().registerResearchItem();
			}
			if (WGConfig.bagVoid) {
				//ORIGINAL ELDRITCHMINOR
				//getFakeResearchItem("ELDRITCHMINOR", "ELDRITCH", 2,2, new ResourceLocation("thaumcraft", "textures/misc/r_eldritchminor.png")).setSpecial().registerResearchItem();
				new FakeResearchItem("WG.ELDRITCHMINOR", "WITCHGADG", "ELDRITCHMINOR", "ELDRITCH", 2, 2, ResearchCategories.getResearch("ELDRITCHMINOR").icon_resource).registerResearchItem();
				
				//VOIDBAG
				researchAspects = new AspectList().add(Aspect.CLOTH, 2).add(Aspect.VOID, 3).add(Aspect.ENTROPY, 5).add(Aspect.ELDRITCH, 3);
				pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.VOIDBAG.1"), new ResearchPage((InfusionRecipe) recipeList.get("VOIDBAG"))};
				getResearchItem("VOIDBAG", "WITCHGADG", researchAspects, 4, 3	, 1, new ItemStack(WGContent.ItemBag,1,1)).setParents(new String[] { "BAGOFTRICKS","WG.ELDRITCHMINOR" }).setConcealed().setSecondary().setPages(pages).registerResearchItem();
			}
		}
		
		
		
		//TODO TG/cloaks
		//WGBAUBLES
		researchAspects = new AspectList().add(Aspect.CLOTH, 1).add(Aspect.MAGIC, 1).add(Aspect.ARMOR, 1);
		pages = new ResearchPage[]{
				new ResearchPage("witchinggadgets_research_page.WGBAUBLES.1"), new ResearchPage((ShapedArcaneRecipe) recipeList.get("WGBAUBLES_KNOCKBACKSHOULDERS")), 
				new ResearchPage("witchinggadgets_research_page.WGBAUBLES.2"), new ResearchPage((ShapedArcaneRecipe) recipeList.get("WGBAUBLES_WOLFVAMBRACES")),
				new ResearchPage("witchinggadgets_research_page.WGBAUBLES.3"), new ResearchPage((InfusionRecipe) recipeList.get("WGBAUBLES_HASTEVAMBRACES")),
				new ResearchPage("witchinggadgets_research_page.WGBAUBLES.4"), new ResearchPage((InfusionRecipe) recipeList.get("WGBAUBLES_DOUBLEJUMPSHOULDERS")),
				new ResearchPage("witchinggadgets_research_page.WGBAUBLES.5"), new ResearchPage((InfusionRecipe) recipeList.get("WGBAUBLES_SNIPERRING")),
				new ResearchPage("witchinggadgets_research_page.WGBAUBLES.6"), new ResearchPage((InfusionRecipe) recipeList.get("WGBAUBLES_LUCKRING")), new ResearchPage((ShapedArcaneRecipe) recipeList.get("WGBAUBLES_COIN"))
		};
		getResearchItem("WGBAUBLES", "WITCHGADG", researchAspects, 4, -2, 1, new ItemStack(WGContent.ItemMagicalBaubles,1,2)).setParents("THAUMIUM").setPages(pages).registerResearchItem();

		
		if (WGConfig.moduleCloak) {
			
			//CLOAK
			ItemStack standardCloak = new ItemStack(WGContent.ItemCloak,1,0);//ItemCloak.getCloakWithTag("STANDARD");
			researchAspects = new AspectList().add(Aspect.CLOTH, 1).add(Aspect.AIR,1).add(Aspect.ARMOR,1).add(Aspect.MAGIC, 1);
			pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.CLOAK.1"), new ResearchPage((IArcaneRecipe)recipeList.get("CLOAK"))};
			getResearchItem("CLOAK", "WITCHGADG", researchAspects, 5, -4, 2, standardCloak).setParentsHidden(new String[] { "ENCHFABRIC" }).setConcealed().setPages(pages).registerResearchItem();

			if (WGConfig.capeSpectral) {
				researchAspects = new AspectList().add(Aspect.CLOTH, 4).add(Aspect.TRAVEL, 2).add(Aspect.SOUL,2).add(Aspect.DARKNESS, 4);
				pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.CLOAK_SPECTRAL.1"), new ResearchPage((InfusionRecipe)recipeList.get("CLOAK_SPECTRAL")), new ResearchPage("witchinggadgets_research_page.CLOAK_SPECTRAL.2")};
				getResearchItem("CLOAK_SPECTRAL", "WITCHGADG", researchAspects, 7, -4, 3, new ItemStack(WGContent.ItemCloak,1,1)).setParents("CLOAK").setParentsHidden("INFUSION").setPages(pages).setConcealed().setSecondary().registerResearchItem();
			}
			if (WGConfig.capeStorage) {
				researchAspects = new AspectList().add(Aspect.CLOTH, 4).add(Aspect.VOID, 6).add(Aspect.HUNGER, 4);
				pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.CLOAK_STORAGE.1"), new ResearchPage((IArcaneRecipe)recipeList.get("CLOAK_STORAGE"))};
				getResearchItem("CLOAK_STORAGE", "WITCHGADG", researchAspects, 7, -3, 3, new ItemStack(WGContent.ItemCloak,1,2)).setParents("CLOAK").setParentsHidden("BAGOFTRICKS" ).setPages(pages).setConcealed().setSecondary().registerResearchItem();
			}
			if (WGConfig.capeWolf) {
				researchAspects = new AspectList().add(Aspect.CLOTH, 4).add(Aspect.BEAST, 4).add(Aspect.HUNGER, 4);
				pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.CLOAK_WOLF.1"), new ResearchPage((IArcaneRecipe)recipeList.get("CLOAK_WOLF"))};
				getResearchItem("CLOAK_WOLF", "WITCHGADG", researchAspects, 7, -5, 3, new ItemStack(WGContent.ItemCloak,1,3)).setParents("CLOAK" ).setPages(pages).setConcealed().setSecondary().registerResearchItem();
			}
			
			if(WGModCompat.tfRavensFeather!=null && WGConfig.capeRaven)
			{
				researchAspects = new AspectList().add(Aspect.CLOTH, 4).add(Aspect.AIR, 4).add(Aspect.FLIGHT, 4).add(Aspect.TRAVEL, 2);
				pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.CLOAK_RAVEN.1"), new ResearchPage((IArcaneRecipe)recipeList.get("CLOAK_RAVEN"))};
				getResearchItem("CLOAK_RAVEN", "WITCHGADG", researchAspects, 6, -6, 3, new ItemStack(WGContent.ItemCloak,1,4)).setParents("CLOAK" ).setPages(pages).setConcealed().setSecondary().registerResearchItem();
			}
			
			if (WGConfig.moduleKama) {
				//KAMA
				researchAspects = new AspectList().add(Aspect.CLOTH, 2).add(Aspect.ARMOR, 2);
				ArrayList<ShapedArcaneRecipe> recList = new ArrayList();
				for(int cm=0; cm<ItemCloak.subNames.length; cm++)
					recList.add((ShapedArcaneRecipe)recipeList.get("CLOAKKAMA_"+cm));
				pages = new ResearchPage[]{new ResearchPage("witchinggadgets_research_page.CLOAKKAMA.1"), new ResearchPage((ShapedArcaneRecipe[]) recList.toArray(new ShapedArcaneRecipe[0]))};
				getResearchItem("CLOAKKAMA", "WITCHGADG", researchAspects, 4, -5, 1, new ItemStack(WGContent.ItemKama,1,0)).setParents("WGBAUBLES","CLOAK").setConcealed().setSecondary().setPages(pages).registerResearchItem();
			}
		}
		
		//ORIGINAL ARCANESTONE
		//getFakeResearchItem("ARCANESTONE", "ARTIFICE", -3,-4,  new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6)).registerResearchItem();
		new FakeResearchItem("WG.ARCANESTONE", "WITCHGADG", "ARCANESTONE", "ARTIFICE", -3, -4, ResearchCategories.getResearch("ARCANESTONE").icon_item).registerResearchItem();
		//STONEEXTRUDER
		researchAspects = new AspectList().add(Aspect.EARTH, 4).add(Aspect.MECHANISM, 2).add(Aspect.TOOL, 2);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.STONEEXTRUDER.1"), new ResearchPage((ShapedArcaneRecipe) recipeList.get("STONEEXTRUDER")) };
		getResearchItem("STONEEXTRUDER", "WITCHGADG", researchAspects, -3, -6, 1, new ItemStack(WGContent.BlockWoodenDevice,1,2)).setParents("WG.ARCANESTONE","THAUMIUM").setPages(pages).registerResearchItem();
		//ICEEXTRUDER
		researchAspects = new AspectList().add(Aspect.COLD, 4).add(Aspect.MECHANISM, 2).add(Aspect.TOOL, 2);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.ICEEXTRUDER.1"), new ResearchPage((ShapedArcaneRecipe) recipeList.get("ICEEXTRUDER")) };
		getResearchItem("ICEEXTRUDER", "WITCHGADG", researchAspects, -3, -7, 1, new ItemStack(WGContent.BlockWoodenDevice,1,1)).setParents("STONEEXTRUDER").setPages(pages).setSecondary().registerResearchItem();
		
		//AGEINGSTONE
		researchAspects = new AspectList().add(Aspect.LIFE,3).add(Aspect.MECHANISM,3);
		if(Aspect.getAspect("tempus")!=null)researchAspects.add(Aspect.getAspect("tempus"), 2);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.AGEINGSTONE.1"), new ResearchPage((ShapedArcaneRecipe) recipeList.get("AGEINGSTONE")) };
		getResearchItem("AGEINGSTONE", "WITCHGADG", researchAspects, -5, -5, 2, new ItemStack(WGContent.BlockStoneDevice,1,1)).addWarp(1).setParents("WG.ARCANESTONE").setPages(pages).setSecondary().registerResearchItem();
		//ETHEREALWALL
		researchAspects = new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.MECHANISM, 2).add(Aspect.EARTH, 4);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.ETHEREALWALL.1"), new ResearchPage("witchinggadgets_research_page.ETHEREALWALL.2"), new ResearchPage((ShapedArcaneRecipe) recipeList.get("ETHEREALWALL")) };
		getResearchItem("ETHEREALWALL", "WITCHGADG", researchAspects, -4, -6, 2, new ItemStack(WGContent.BlockStoneDevice,1,0)).setParents("WG.ARCANESTONE").setPages(pages).setSecondary().registerResearchItem();

		
		//ORIGINAL BATHSALTS
		//getFakeResearchItem("BATHSALTS", "ALCHEMY", -1,-4,  new ItemStack(ConfigItems.itemBathSalts)).registerResearchItem();
		new FakeResearchItem("WG.BATHSALTS", "WITCHGADG", "BATHSALTS", "ALCHEMY", -1, -4, ResearchCategories.getResearch("BATHSALTS").icon_item).registerResearchItem();
		//SAUNA
		researchAspects = new AspectList().add(Aspect.WATER, 3).add(Aspect.FIRE, 3).add(Aspect.MECHANISM, 1);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.SAUNASTOVE.1"), new ResearchPage("witchinggadgets_research_page.SAUNASTOVE.2"), new ResearchPage((ShapedArcaneRecipe) recipeList.get("SAUNASTOVE")) };
		getResearchItem("SAUNASTOVE", "WITCHGADG", researchAspects, -1,-3, 1, new ItemStack(WGContent.BlockWoodenDevice,1,4)).setParents("WG.BATHSALTS").setPages(pages).setSecondary().setConcealed().registerResearchItem();

		//ORIGINAL JAR
		//getFakeResearchItem("JARLABEL", "ALCHEMY", -2,2,  new ItemStack(ConfigBlocks.blockJar)).registerResearchItem();
		new FakeResearchItem("WG.JARLABEL", "WITCHGADG", "JARLABEL", "ALCHEMY", -2, 2, ResearchCategories.getResearch("JARLABEL").icon_item).registerResearchItem();
		//LABELLIB
		researchAspects = new AspectList().add(Aspect.SENSES, 4).add(Aspect.MIND, 4).add(Aspect.TOOL, 2);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.LABELLIB.1"), new ResearchPage((ShapedArcaneRecipe) recipeList.get("LABELLIB")) };
		getResearchItem("LABELLIB", "WITCHGADG", researchAspects, -3, 1, 2, new ItemStack(WGContent.BlockWoodenDevice,1,5)).setParents("WG.JARLABEL").setPages(pages).setSecondary().registerResearchItem();

		
		//MIRROR
		if(Config.allowMirrors)
		{	
			//			//ORIGINAL MIRROR
			//			getFakeResearchItem("MIRROR", "ARTIFICE", 2,-7, new ItemStack(ConfigBlocks.blockMirror, 1, 0)).registerResearchItem();
			//			//MIRROR
			//			researchAspects = new AspectList().add(Aspect.VOID, 4).add(Aspect.TRAVEL, 8).add(Aspect.ELDRITCH, 6).add(Aspect.CRYSTAL, 6);
			//			pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.WALLMIRROR.1"),new ResearchPage("witchinggadgets_research_page.WALLMIRROR.2"), new ResearchPage((InfusionRecipe)recipeList.get("WALLMIRROR")) };
			//			getResearchItem("WALLMIRROR", "WITCHGADG", researchAspects, 1, -5, 2, new ItemStack(WGContent.BlockWallMirror)).addWarp(2).setPages(pages).setParents("WGFAKEMIRROR").setSecondary().setConcealed().registerResearchItem();

			//getFakeResearchItem("MIRRORESSENTIA", "ARTIFICE", -2,-2,  new ItemStack(ConfigBlocks.blockMirror,1,6)).registerResearchItem();
			new FakeResearchItem("WG.MIRRORESSENTIA", "WITCHGADG", "MIRRORESSENTIA", "ARTIFICE", -2, -2, ResearchCategories.getResearch("MIRRORESSENTIA").icon_item).registerResearchItem();

			//MIRRORPUMP
			researchAspects = new AspectList().add(Aspect.TOOL, 1).add(Aspect.WATER, 1).add(Aspect.TRAVEL, 1).add(Aspect.SLIME, 1);
			pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.MIRRORPUMP.1"), new ResearchPage((IArcaneRecipe)recipeList.get("MIRRORPUMP")) };
			getResearchItem("MIRRORPUMP", "WITCHGADG", researchAspects, -3, -1, 2, new ItemStack(WGContent.BlockMetalDevice,1,0)).setPages(pages).setParents("WG.MIRRORESSENTIA").registerResearchItem();

		}


		//SCANCAMERA
		researchAspects = new AspectList().add(Aspect.SENSES, 1).add(Aspect.MIND, 1).add(Aspect.SOUL, 1).add(Aspect.CRYSTAL, 1);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.SCANCAMERA.1"), new ResearchPage((IArcaneRecipe)recipeList.get("SCANCAMERA")), new ResearchPage("witchinggadgets_research_page.SCANCAMERA.2"), new ResearchPage((IArcaneRecipe)recipeList.get("SCANCAMERA_DEVELOP")), new ResearchPage((IRecipe)recipeList.get("SCANCAMERA_CLEARPLATE"))};
		getResearchItem("SCANCAMERA", "WITCHGADG", researchAspects, -1, -6, 2, new ItemStack(WGContent.ItemScanCamera)).setPages(pages).registerResearchItem();
		//CALCULATOR
		researchAspects = new AspectList().add(Aspect.TOOL, 1).add(Aspect.MIND, 1).add(Aspect.MECHANISM, 1);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.CALCULATOR.1"), new ResearchPage((IArcaneRecipe)recipeList.get("CALCULATOR")) };
		getResearchItem("CALCULATOR", "WITCHGADG", researchAspects, 0, -2, 1, new ItemStack(WGContent.ItemMaterial,1,7)).setPages(pages).setParents("INFUSION").registerResearchItem();
		//LABYRINTHSTRING
		researchAspects = new AspectList().add(Aspect.TOOL, 1).add(Aspect.MIND, 1).add(Aspect.TRAVEL, 1);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.LABYRINTHSTRING.1"), new ResearchPage((InfusionRecipe)recipeList.get("LABYRINTHSTRING")) };
		getResearchItem("LABYRINTHSTRING", "WITCHGADG", researchAspects, 1, -6, 1, new ItemStack(WGContent.ItemMaterial,1,11)).setPages(pages).setParents("INFUSION").registerResearchItem();


		
		//ORIGINAL ALCHEMICALMANUFACTURE
		//getFakeResearchItem("ALCHEMICALMANUFACTURE", "ALCHEMY", -4,-3, new ResourceLocation("thaumcraft", "textures/misc/r_alchman.png")).registerResearchItem();
		new FakeResearchItem("WG.ALCHEMICALMANUFACTURE", "WITCHGADG", "ALCHEMICALMANUFACTURE", "ALCHEMY", -4, -3, ResearchCategories.getResearch("ALCHEMICALMANUFACTURE").icon_resource).registerResearchItem();
		//ALCHEMICALTRANSFORM
		researchAspects = new AspectList().add(Aspect.PLANT, 4).add(Aspect.LIFE, 2).add(Aspect.WATER, 2);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.ALCHEMICALTRANSMOGRIFY.1"), new ResearchPage((CrucibleRecipe) recipeList.get("ALCHEMICALTRANSMOGRIFY_GRASS")), new ResearchPage((CrucibleRecipe) recipeList.get("ALCHEMICALTRANSMOGRIFY_MYCEL")), new ResearchPage((CrucibleRecipe) recipeList.get("ALCHEMICALTRANSMOGRIFY_SAND")), new ResearchPage((CrucibleRecipe) recipeList.get("ALCHEMICALTRANSMOGRIFY_FLINT"))};
		getResearchItem("ALCHEMICALTRANSMOGRIFY", "WITCHGADG", researchAspects, -6, -3, 1, new ItemStack(Blocks.grass)).setSecondary().setParents(new String[] { "WG.ALCHEMICALMANUFACTURE" }).setConcealed().setPages(pages).registerResearchItem();
		//ROSEVINE
		researchAspects = new AspectList().add(Aspect.PLANT, 2).add(Aspect.AIR, 3).add(Aspect.ENTROPY, 2);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.ROSEVINE.1"), new ResearchPage((CrucibleRecipe) recipeList.get("ROSEVINE"))};
		getResearchItem("ROSEVINE", "WITCHGADG", researchAspects, -6, -4, 1, new ItemStack(WGContent.BlockRoseVine)).setSecondary().setParents("WG.ALCHEMICALMANUFACTURE").setConcealed().setPages(pages).registerResearchItem();

		
		//TODO ores
		//ORIGINAL PUREIRON
		//getFakeResearchItem("PUREIRON", "ALCHEMY", -5, 0, new ItemStack(ConfigItems.itemNugget, 1, 16)).registerResearchItem();
		new FakeResearchItem("WG.PUREIRON", "WITCHGADG", "PUREIRON", "ALCHEMY", -5, 0, ResearchCategories.getResearch("PUREIRON").icon_item).registerResearchItem();
		//PURECINNABAR
		researchAspects = new AspectList().add(Aspect.METAL,5).add(Aspect.ORDER, 1).add(Aspect.POISON, 1);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.PURECINNABAR.1"), new ResearchPage((CrucibleRecipe) recipeList.get("PURECINNABAR"))};
		getResearchItem("PURECINNABAR", "WITCHGADG", researchAspects, -6, 1, 1, new ItemStack(ConfigItems.itemNugget, 1, 21)).setConcealed().setSecondary().setParents("WG.PUREIRON").setPages(pages).registerResearchItem();

		
		
		//METALLURGICPERFECTION_CLUSTERS
		if(WGConfig.allowClusters)
		{
			ArrayList<ResearchPage> clusterPages = new ArrayList<ResearchPage>();
			clusterPages.add(new ResearchPage("witchinggadgets_research_page.METALLURGICPERFECTION_CLUSTERS.1"));
			for(String ore : ItemClusters.subNames)
				if(recipeList.containsKey("METALLURGICPERFECTION_CLUSTERS_"+ore))
					clusterPages.add( new ResearchPage((CrucibleRecipe)recipeList.get("METALLURGICPERFECTION_CLUSTERS_"+ore)) );
			pages = clusterPages.toArray(new ResearchPage[0]);
			researchAspects = new AspectList().add(Aspect.METAL,20).add(Aspect.ORDER, 10).add(Aspect.CRYSTAL, 10);
			ArrayList<String> clusterParents = new ArrayList<String>();
			clusterParents.add("WG.PUREIRON");
			clusterParents.add("PUREGOLD");
			if(Utilities.researchExists("ALCHEMY", "PURECOPPER"))
				clusterParents.add("PURECOPPER");
			if(Utilities.researchExists("ALCHEMY", "PURETIN"))
				clusterParents.add("PURETIN");
			if(Utilities.researchExists("ALCHEMY", "PURESILVER"))
				clusterParents.add("PURESILVER");
			if(Utilities.researchExists("ALCHEMY", "PURELEAD"))
				clusterParents.add("PURELEAD");
			clusterParents.add("PURECINNABAR");
			getResearchItem("METALLURGICPERFECTION_CLUSTERS", "WITCHGADG", researchAspects, -7, 0, 1, new ResourceLocation("witchinggadgets:textures/gui/research/icon_mp_cluster.png")).setConcealed().setSecondary().setSpecial().setParents(clusterParents.toArray(new String[0])).setPages(pages).registerResearchItem();
		}
		
		
		if(WGConfig.allowTransmutations)
		{
			//ORIGINAL TRANSIRON
			//getFakeResearchItem("TRANSIRON", "ALCHEMY", -5,-1, new ItemStack(ConfigItems.itemNugget, 1, 0)).registerResearchItem();
			new FakeResearchItem("WG.TRANSIRON", "WITCHGADG", "TRANSIRON", "ALCHEMY", -5, -1, ResearchCategories.getResearch("TRANSIRON").icon_item).registerResearchItem();
			
			//METALLURGICPERFECTION_TRANSMUTATION
			ArrayList<ResearchPage> transmutePages = new ArrayList<ResearchPage>();
			transmutePages.add(new ResearchPage("witchinggadgets_research_page.METALLURGICPERFECTION_TRANSMUTATION.1"));
			for(String ore : ItemClusters.subNames)
				if(recipeList.containsKey("METALLURGICPERFECTION_TRANSMUTATION_"+ore))
					transmutePages.add( new ResearchPage((CrucibleRecipe)recipeList.get("METALLURGICPERFECTION_TRANSMUTATION_"+ore)) );
			pages = transmutePages.toArray(new ResearchPage[0]);
			researchAspects = new AspectList().add(Aspect.METAL,20).add(Aspect.ORDER, 10).add(Aspect.EXCHANGE, 10);
			ArrayList<String> transmuteParents = new ArrayList<String>();
			transmuteParents.add("WG.TRANSIRON");
			transmuteParents.add("TRANSGOLD");
			if(Utilities.researchExists("ALCHEMY", "TRANSCOPPER"))
				transmuteParents.add("TRANSCOPPER");
			if(Utilities.researchExists("ALCHEMY", "TRANSTIN"))
				transmuteParents.add("TRANSTIN");
			if(Utilities.researchExists("ALCHEMY", "TRANSSILVER"))
				transmuteParents.add("TRANSSILVER");
			if(Utilities.researchExists("ALCHEMY", "TRANSLEAD"))
				transmuteParents.add("TRANSLEAD");
			getResearchItem("METALLURGICPERFECTION_TRANSMUTATION", "WITCHGADG", researchAspects, -7, -1, 1, new ResourceLocation("witchinggadgets:textures/gui/research/icon_mp_trans.png")).setConcealed().setSecondary().setSpecial().setParents(transmuteParents.toArray(new String[0])).setPages(pages).registerResearchItem();
		}

		//TODO blast
		//ORIGINAL INFERNALFURNACE
		//getFakeResearchItem("INFERNALFURNACE", "ARTIFICE", 2,-2, new ResourceLocation("thaumcraft", "textures/misc/r_infernalfurnace.png")).registerResearchItem();
		new FakeResearchItem("WG.INFERNALFURNACE", "WITCHGADG", "INFERNALFURNACE", "ARTIFICE", 2, -2, ResearchCategories.getResearch("INFERNALFURNACE").icon_resource).registerResearchItem();
		//INFERNALBLASTFURNACE
		researchAspects = new AspectList().add(Aspect.FIRE,2).add(Aspect.METAL, 1).add(Aspect.CRAFT, 1).add(Aspect.DARKNESS, 1).add(Aspect.TAINT, 1);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.INFERNALBLASTFURNACE.1"), new ResearchPage("witchinggadgets_research_page.INFERNALBLASTFURNACE.2"), new ResearchPage((List) recipeList.get("INFERNALBLASTFURNACE")), new ResearchPage("witchinggadgets_research_page.INFERNALBLASTFURNACE.3")};
		getResearchItem("INFERNALBLASTFURNACE", "WITCHGADG", researchAspects, 2, 0, 2, new ResourceLocation("witchinggadgets:textures/gui/research/icon_blastfurnace.png")).addWarp(3).setConcealed().setPages(pages).setParents("WG.INFERNALFURNACE").registerResearchItem();

		
		//TODO gems
		if (WGConfig.moduleGemcutting) {
			//GEMCUTTING
			researchAspects = new AspectList().add(Aspect.CRYSTAL,1).add(Aspect.ORDER, 1).add(Aspect.MAGIC, 1).add(Aspect.CRAFT, 1);
			pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.GEMCUTTING.1"), new ResearchPage((ShapedOreRecipe) recipeList.get("GEMCUTTING_TOOLS")), new ResearchPage((List) recipeList.get("GEMCUTTING")), new ResearchPage("witchinggadgets_research_page.GEMCUTTING.2"), new ResearchPage("witchinggadgets_research_page.GEMCUTTING.3"), new ResearchPage("witchinggadgets_research_page.GEMCUTTING.4"), new ResearchPage("witchinggadgets_research_page.GEMCUTTING.5"), new ResearchPage("witchinggadgets_research_page.GEMCUTTING.6"), new ResearchPage("witchinggadgets_research_page.GEMCUTTING.7"+(!Config.allowMirrors?".noMirrors":""))};
			getResearchItem("GEMCUTTING", "WITCHGADG", researchAspects, 1, -4, 2, new ItemStack(WGContent.BlockWoodenDevice,1,3)).setPages(pages).registerResearchItem();
			//CRYSTALCAPSULE
			researchAspects = new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.ORDER, 2).add(Aspect.VOID, 4);
			pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.CRYSTALCAPSULE.1"), new ResearchPage((CrucibleRecipe) recipeList.get("CRYSTALCAPSULE"))};
			getResearchItem("CRYSTALCAPSULE", "WITCHGADG", researchAspects, 1, -3, 2, new ItemStack(WGContent.ItemCapsule)).setPages(pages).setSecondary().setParents("GEMCUTTING").registerResearchItem();

		}
		
		
		//TODO enchants
		//ORIGINAL INFUSIONENCHANTMENT
		//getFakeResearchItem("INFUSIONENCHANTMENT", "ARTIFICE", -4,3, new ResourceLocation("thaumcraft:textures/misc/r_enchant.png")).setSiblings().registerResearchItem();
		new FakeResearchItem("WG.INFUSIONENCHANTMENT", "WITCHGADG", "INFUSIONENCHANTMENT", "ARTIFICE", -4, 3, ResearchCategories.getResearch("INFUSIONENCHANTMENT").icon_resource).registerResearchItem();
		
		//ENCH_INVISIBLEGEAR
		researchAspects = new AspectList().add(Aspect.MAGIC, 2).add(Aspect.CRYSTAL, 4).add(Aspect.DARKNESS, 4);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.ENCH_INVISIBLEGEAR.1"), new ResearchPage((InfusionEnchantmentRecipe) recipeList.get("ENCH_INVISIBLEGEAR"))};
		getResearchItem("ENCH_INVISIBLEGEAR", "WITCHGADG", researchAspects, -5, 5, 2, new ResourceLocation("witchinggadgets:textures/gui/research/icon_ench_invisGear.png")).setParents("WG.INFUSIONENCHANTMENT").setConcealed().setSecondary().setPages(pages).registerResearchItem();
		//ENCH_REVEALING
		researchAspects = new AspectList().add(Aspect.MAGIC, 2).add(Aspect.SENSES, 4).add(Aspect.LIGHT, 4);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.ENCH_UNVEILING.1"), new ResearchPage((InfusionEnchantmentRecipe) recipeList.get("ENCH_UNVEILING"))};
		getResearchItem("ENCH_UNVEILING", "WITCHGADG", researchAspects, -6, 3, 2, new ResourceLocation("witchinggadgets:textures/gui/research/icon_ench_unveiling.png")).setParents("WG.INFUSIONENCHANTMENT").setConcealed().setSecondary().setPages(pages).registerResearchItem();
		//ENCH_STEALTH
		researchAspects = new AspectList().add(Aspect.MAGIC, 2).add(Aspect.MOTION, 4).add(Aspect.DARKNESS, 4);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.ENCH_STEALTH.1"), new ResearchPage((InfusionEnchantmentRecipe) recipeList.get("ENCH_STEALTH"))};
		getResearchItem("ENCH_STEALTH", "WITCHGADG", researchAspects, -6, 4, 2, new ResourceLocation("witchinggadgets:textures/gui/research/icon_ench_stealth.png")).setParents("WG.INFUSIONENCHANTMENT").setConcealed().setSecondary().setPages(pages).registerResearchItem();
		//ENCH_BACKSTAB
		researchAspects = new AspectList().add(Aspect.MAGIC, 2).add(Aspect.WEAPON, 4).add(Aspect.DARKNESS, 4);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.ENCH_BACKSTAB.1"), new ResearchPage((InfusionEnchantmentRecipe) recipeList.get("ENCH_BACKSTAB"))};
		getResearchItem("ENCH_BACKSTAB", "WITCHGADG", researchAspects, -7, 4, 2, new ResourceLocation("witchinggadgets:textures/gui/research/icon_ench_backstab.png")).setParents("ENCH_STEALTH").setConcealed().setSecondary().setPages(pages).registerResearchItem();
		//ENCH_RIDEPROTECT
		researchAspects = new AspectList().add(Aspect.MAGIC, 2).add(Aspect.TRAP, 4).add(Aspect.ARMOR, 4);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.ENCH_RIDEPROTECT.1"), new ResearchPage((InfusionEnchantmentRecipe) recipeList.get("ENCH_RIDEPROTECT"))};
		getResearchItem("ENCH_RIDEPROTECT", "WITCHGADG", researchAspects, -4, 5, 2, new ResourceLocation("witchinggadgets:textures/gui/research/icon_ench_rideProtect.png")).setParents("WG.INFUSIONENCHANTMENT").setConcealed().setSecondary().setPages(pages).registerResearchItem();
		
		//ENCH_SOULBOUND
		researchAspects = new AspectList().add(Aspect.MAGIC, 4).add(Aspect.ELDRITCH, 2).add(Aspect.SOUL, 2);
		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.ENCH_SOULBOUND.1"), new ResearchPage((InfusionEnchantmentRecipe) recipeList.get("ENCH_SOULBOUND")), new ResearchPage((ShapedArcaneRecipe)recipeList.get("ENCH_SOULBOUND_BOOK"))};
		getResearchItem("ENCH_SOULBOUND", "WITCHGADG", researchAspects, -3, 4, 2, new ResourceLocation("witchinggadgets:textures/gui/research/icon_ench_soulbound.png")).setParents("WG.INFUSIONENCHANTMENT").setConcealed().setSecondary().setPages(pages).registerResearchItem();

		
		

		//TODO primal
		if (WGConfig.modulePrimal) {
			//ORIGINAL PRIMPEARL
			//getFakeResearchItem("PRIMPEARL", "ELDRITCH", 0, 4, new ItemStack(ConfigItems.itemEldritchObject, 1, 3)).setSpecial().registerResearchItem();
			new FakeResearchItem("WG.PRIMPEARL", "WITCHGADG", "PRIMPEARL", "ELDRITCH", 0, 4, ResearchCategories.getResearch("PRIMPEARL").icon_item).registerResearchItem();
			
			//PRIMORDIALGEARSET
			researchAspects = new AspectList().add(Aspect.MAGIC,1).add(Aspect.ENERGY,1).add(Aspect.AIR,1).add(Aspect.FIRE,1).add(Aspect.WATER,1).add(Aspect.EARTH,1).add(Aspect.ORDER,1).add(Aspect.ENTROPY,1);
			pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.PRIMORDIALGEARSET.1"),new ResearchPage("witchinggadgets_research_page.PRIMORDIALGEARSET.2")};
			getResearchItem("PRIMORDIALGEARSET", "WITCHGADG", researchAspects, 0,6,3, new ResourceLocation("witchinggadgets:textures/gui/research/icon_primordialGear.png"))
				.setParents("VOIDMETAL","WG.PRIMPEARL").setPages(pages).registerResearchItem();
			//EMPOWERPEARL
			researchAspects = new AspectList().add(Aspect.MAGIC,8).add(Aspect.CRYSTAL,4).add(Aspect.VOID,4).add(Aspect.ENERGY,4);
			pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.EMPOWERPEARL.1"), new ResearchPage((InfusionRecipe) recipeList.get("EMPOWERPEARL"))};
			getResearchItem("EMPOWERPEARL", "WITCHGADG", researchAspects, 0,2,1, new ItemStack(WGContent.ItemMaterial,1,12))
				.setSecondary().setParents("WG.PRIMPEARL").setItemTriggers(new ItemStack(WGContent.ItemMaterial,1,12)).setHidden().setPages(pages).registerResearchItem();

			if (WGConfig.moduleGemcutting) {
			//PRIMORDIALGLOVE
			researchAspects = new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.MAGIC, 1).add(Aspect.TOOL, 1).add(Aspect.AIR,1).add(Aspect.FIRE,1).add(Aspect.WATER,1).add(Aspect.EARTH,1).add(Aspect.ORDER,1).add(Aspect.ENTROPY,1);
			pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.PRIMORDIALGLOVE.1"), new ResearchPage((InfusionRecipe) recipeList.get("PRIMORDIALGLOVE")), new ResearchPage("witchinggadgets_research_page.PRIMORDIALGLOVE.2")};
			getResearchItem("PRIMORDIALGLOVE", "WITCHGADG", researchAspects, 2, 5, 2, new ItemStack(WGContent.ItemPrimordialGlove))
				.setParents("VOIDMETAL","PRIMORDIALGEARSET","WG.PRIMPEARL").setConcealed().setPages(pages).registerResearchItem();
			}
			
			//PRIMORDIALWEAPONRY
			researchAspects = new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.WEAPON, 1).add(Aspect.TOOL, 1).add(Aspect.AIR,1).add(Aspect.FIRE,1).add(Aspect.WATER,1).add(Aspect.MAGIC, 2).add(Aspect.EARTH,1).add(Aspect.ORDER,1);
			pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.PRIMORDIALWEAPONRY.1"), new ResearchPage("witchinggadgets_research_page.PRIMORDIALWEAPONRY.2"), new ResearchPage("witchinggadgets_research_page.PRIMORDIALWEAPONRY.3"), new ResearchPage((InfusionRecipe) recipeList.get("PRIMORDIALWEAPONRY_CLAYMORE")), new ResearchPage((InfusionRecipe) recipeList.get("PRIMORDIALWEAPONRY_HAMMER")), new ResearchPage((InfusionRecipe) recipeList.get("PRIMORDIALWEAPONRY_GREATAXE"))};
			getResearchItem("PRIMORDIALWEAPONRY", "WITCHGADG", researchAspects, -2, 7, 3, new ResourceLocation("witchinggadgets:textures/gui/research/icon_primordialWeaponry.png"))
				.setParents("PRIMORDIALGEARSET").setConcealed().setPages(pages).registerResearchItem();

			//		//PRIMORDIALCLAYMORE
			//		researchAspects = new AspectList().add(Aspect.WEAPON, 1).add(Aspect.MAGIC, 1).add(Aspect.AIR,1).add(Aspect.FIRE,1).add(Aspect.WATER,1).add(Aspect.EARTH,1).add(Aspect.ORDER,1).add(Aspect.ENTROPY,1);
			//		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.PRIMORDIALCLAYMORE.1"), new ResearchPage((InfusionRecipe) recipeList.get("PRIMORDIALCLAYMORE"))};
			//		getResearchItem("PRIMORDIALCLAYMORE", "WITCHGADG", researchAspects, -4, 2, 3, new ItemStack(WGContent.ItemPrimordialSword)).setParents("PRIMORDIALGEARSET","ELEMENTALSWORD").setConcealed().setPages(pages).registerResearchItem();
			//		//PRIMORDIALHAMMER
			//		researchAspects = new AspectList().add(Aspect.WEAPON, 1).add(Aspect.TOOL, 1).add(Aspect.MAGIC, 1).add(Aspect.AIR,1).add(Aspect.FIRE,1).add(Aspect.WATER,1).add(Aspect.EARTH,1).add(Aspect.ORDER,1).add(Aspect.ENTROPY,1);
			//		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.PRIMORDIALHAMMER.1"), new ResearchPage((InfusionRecipe) recipeList.get("PRIMORDIALHAMMER"))};
			//		getResearchItem("PRIMORDIALHAMMER", "WITCHGADG", researchAspects, -4, 3, 3, new ItemStack(WGContent.ItemPrimordialHammer)).setParents("PRIMORDIALGEARSET","ELEMENTALPICK").setConcealed().setPages(pages).registerResearchItem();
			//		//PRIMORDIALGREATAXE
			//		researchAspects = new AspectList().add(Aspect.WEAPON, 1).add(Aspect.TOOL, 1).add(Aspect.MAGIC, 1).add(Aspect.AIR,1).add(Aspect.FIRE,1).add(Aspect.WATER,1).add(Aspect.EARTH,1).add(Aspect.ORDER,1).add(Aspect.ENTROPY,1);
			//		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.PRIMORDIALGREATAXE.1"), new ResearchPage((InfusionRecipe) recipeList.get("PRIMORDIALGREATAXE"))};
			//		getResearchItem("PRIMORDIALGREATAXE", "WITCHGADG", researchAspects, -4, 4, 3, new ItemStack(WGContent.ItemPrimordialAxe)).setParents("PRIMORDIALGEARSET","ELEMENTALAXE").setConcealed().setPages(pages).registerResearchItem();


			//PRIMORDIALHELMET
			researchAspects = new AspectList().add(Aspect.ARMOR, 1).add(Aspect.MAGIC, 1).add(Aspect.AIR,1).add(Aspect.FIRE,1).add(Aspect.WATER,1).add(Aspect.EARTH,1).add(Aspect.ORDER,1).add(Aspect.ENTROPY,1);
			pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.PRIMORDIALARMOR.1"),new ResearchPage("witchinggadgets_research_page.PRIMORDIALARMOR.2"), new ResearchPage((InfusionRecipe) recipeList.get("PRIMORDIALARMOR_HELMET")), new ResearchPage((InfusionRecipe) recipeList.get("PRIMORDIALARMOR_CUIRASS")), new ResearchPage((InfusionRecipe) recipeList.get("PRIMORDIALARMOR_GREAVES")), new ResearchPage((InfusionRecipe) recipeList.get("PRIMORDIALARMOR_BOOTS"))};
			getResearchItem("PRIMORDIALARMOR", "WITCHGADG", researchAspects, 2, 7, 3, new ResourceLocation("witchinggadgets:textures/gui/research/icon_primordialArmor.png"))
				.setParents("PRIMORDIALGEARSET","ARMORFORTRESS").setConcealed().setPages(pages).registerResearchItem();
			//		//PRIMORDIALCUIRASS
			//		researchAspects = new AspectList().add(Aspect.ARMOR, 1).add(Aspect.MAGIC, 1).add(Aspect.AIR,1).add(Aspect.FIRE,1).add(Aspect.WATER,1).add(Aspect.EARTH,1).add(Aspect.ORDER,1).add(Aspect.ENTROPY,1);
			//		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.PRIMORDIALCUIRASS.1"), new ResearchPage((InfusionRecipe) recipeList.get("PRIMORDIALCUIRASS"))};
			//		getResearchItem("PRIMORDIALCUIRASS", "WITCHGADG", researchAspects, -1, 6, 3, new ItemStack(WGContent.ItemPrimordialChest)).setParents("PRIMORDIALGEARSET").setConcealed().setPages(pages).registerResearchItem();
			//		//PRIMORDIALGREAVES
			//		researchAspects = new AspectList().add(Aspect.ARMOR, 1).add(Aspect.MAGIC, 1).add(Aspect.AIR,1).add(Aspect.FIRE,1).add(Aspect.WATER,1).add(Aspect.EARTH,1).add(Aspect.ORDER,1).add(Aspect.ENTROPY,1);
			//		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.PRIMORDIALGREAVES.1"), new ResearchPage((InfusionRecipe) recipeList.get("PRIMORDIALGREAVES"))};
			//		getResearchItem("PRIMORDIALGREAVES", "WITCHGADG", researchAspects, 0, 6, 3, new ItemStack(WGContent.ItemPrimordialLegs)).setParents("PRIMORDIALGEARSET").setConcealed().setPages(pages).registerResearchItem();
			//		//PRIMORDIALBOOTS
			//		researchAspects = new AspectList().add(Aspect.ARMOR, 1).add(Aspect.MAGIC, 1).add(Aspect.AIR,1).add(Aspect.FIRE,1).add(Aspect.WATER,1).add(Aspect.EARTH,1).add(Aspect.ORDER,1).add(Aspect.ENTROPY,1);
			//		pages = new ResearchPage[]{ new ResearchPage("witchinggadgets_research_page.PRIMORDIALBOOTS.1"), new ResearchPage((InfusionRecipe) recipeList.get("PRIMORDIALBOOTS"))};
			//		getResearchItem("PRIMORDIALBOOTS", "WITCHGADG", researchAspects, 1, 5, 3, new ItemStack(WGContent.ItemPrimordialBoots)).setParents("PRIMORDIALGEARSET").setConcealed().setPages(pages).registerResearchItem();

		}

	}

	public static void modifyStandardThaumcraftResearch()
	{
		//Add Thaumium Shears
		ResearchItem thaumium = ResearchCategories.getResearch("THAUMIUM");
		ResearchPage[] pages = thaumium.getPages();
		ResearchPage[] newPages = new ResearchPage[pages.length+1];
		for(int i=0;i<7;i++)
			newPages[i] = pages[i];
		newPages[7] = new ResearchPage((IRecipe)recipeList.get("THAUMIUMSHEARS"));
		for(int i=8;i<newPages.length;i++)
			newPages[i] = pages[i-1];
		thaumium.setPages(newPages);
	}

	private static void registerArcaneRecipe(String tag, String tagAddon, ItemStack result, AspectList craftingAspects, Object... recipe)
	{
		ShapedArcaneRecipe arcaneRecipe = ThaumcraftApi.addArcaneCraftingRecipe(tag,result,craftingAspects,recipe);
		recipeList.put(tag+tagAddon, arcaneRecipe);
	}

	private static void registerShapelessArcaneRecipe(String tag, String tagAddon, ItemStack result, AspectList craftingAspects, Object... recipe)
	{
		ShapelessArcaneRecipe arcaneRecipe = ThaumcraftApi.addShapelessArcaneCraftingRecipe(tag,result,craftingAspects,recipe);
		recipeList.put(tag+tagAddon, arcaneRecipe);
	}

	private static void registerAlchemyRecipe(String tag, String tagAddon, ItemStack result, Object catalyst, AspectList alchemyAspects)
	{
		CrucibleRecipe crucibleRecipe = ThaumcraftApi.addCrucibleRecipe(tag, result, catalyst, alchemyAspects);
		recipeList.put(tag+tagAddon, crucibleRecipe);
	}

	private static void registerInfusionRecipe(String tag, String tagAddon, Object result, int difficulty, AspectList infusionAspects, ItemStack centralIngredient, ItemStack[] otherIngredients)
	{
		InfusionRecipe infusionRecipe = ThaumcraftApi.addInfusionCraftingRecipe(tag, result, difficulty, infusionAspects, centralIngredient, otherIngredients);
		recipeList.put(tag+tagAddon, infusionRecipe);
	}
	private static void registerInfusionEnchantmentRecipe(String tag, String tagAddon, Enchantment enchantment, int difficulty, AspectList infusionAspects, ItemStack[] otherIngredients)
	{
		InfusionEnchantmentRecipe infusionRecipe = ThaumcraftApi.addInfusionEnchantmentRecipe(tag, enchantment, difficulty, infusionAspects, otherIngredients);
		recipeList.put(tag+tagAddon, infusionRecipe);
	}

	private static void registerCompoundRecipe(String tag, String tagAddon, AspectList creationAspects, int sizeX, int sizeY, int sizeZ, Object... recipe)
	{
		List<Object> compoundRecipe = Arrays.asList(new Object[] {creationAspects, Integer.valueOf(sizeX), Integer.valueOf(sizeY), Integer.valueOf(sizeZ), Arrays.asList(recipe)});
		recipeList.put(tag+tagAddon, compoundRecipe);
	}

	private static void registerShapelessOreRecipe(String tag, String tagAddon, ItemStack result, Object... recipe)
	{
		ShapelessOreRecipe oreRecipe = new ShapelessOreRecipe(result,recipe);
		GameRegistry.addRecipe(oreRecipe);
		recipeList.put(tag+tagAddon, oreRecipe);
	}

	private static void registerShapedOreRecipe(String tag, String tagAddon, ItemStack result, Object... recipe)
	{
		ShapedOreRecipe oreRecipe = new ShapedOreRecipe(result,recipe);
		GameRegistry.addRecipe(oreRecipe);
		recipeList.put(tag+tagAddon, oreRecipe);
	}


	private static WGResearchItem getResearchItem(String tag, String category, AspectList researchAspects, int xPos, int yPos, int complexity, Object icon)
	{
		WGResearchItem item = null;
		if(icon instanceof ItemStack)
			item = new WGResearchItem(tag, category, researchAspects, xPos, yPos, complexity, (ItemStack)icon);
		if(icon instanceof ResourceLocation)
			item = new WGResearchItem(tag, category, researchAspects, xPos, yPos, complexity, (ResourceLocation)icon);
		return item;
	}
	/*
	private static WGFakeResearchItem getFakeResearchItem(String original, String originalCat, int xPos, int yPos, Object icon)
	{
		WGFakeResearchItem item = null;
		if(icon instanceof ItemStack)
			item = new WGFakeResearchItem("WGFAKE"+original, "WITCHGADG", original, originalCat, xPos, yPos, (ItemStack)icon);
		if(icon instanceof ResourceLocation)
			item = new WGFakeResearchItem("WGFAKE"+original, "WITCHGADG", original, originalCat, xPos, yPos, (ResourceLocation) icon);
		return item;
	}*/

	private static void setupCluster(String name)
	{
		String fluid = MetalFluidData.getOreFluidName(name);
		int fluidTemp = MetalFluidData.getOreFluidTemp(name);
		fluidTemp = fluidTemp>0?fluidTemp:550;

		String ore = "ore"+name;
		String cluster = "cluster"+name;
		String ingot = "ingot"+name;
		String nugget = "nugget"+name;
		ItemStack clusterStack = ItemClusters.getCluster(name);

		if(!OreDictionary.getOres(nugget).isEmpty())
		{
			if(!OreDictionary.getOres(ore).isEmpty())
				ThaumcraftApi.addSmeltingBonus(ore, OreDictionary.getOres(nugget).get(0));
			if(!OreDictionary.getOres(cluster).isEmpty())
				ThaumcraftApi.addSmeltingBonus(cluster, OreDictionary.getOres(nugget).get(0));
		}

		if(!OreDictionary.getOres(cluster).isEmpty())
		{
			if(!OreDictionary.getOres(ingot).isEmpty())
			{
				ItemStack ingots = OreDictionary.getOres(ingot).get(0);
				if(clusterStack!=null)
				{
					FurnaceRecipes.smelting().func_151394_a(clusterStack, Utilities.copyStackWithSize(ingots,2), 1.0F);
					if(!OreDictionary.getOres(ore).isEmpty())
						Utils.addSpecialMiningResult(OreDictionary.getOres(ore).get(0), clusterStack, 1f);
				}
			}
			if(WGModCompat.loaded_TCon && WGConfig.smelteryResultForClusters>0 && FluidRegistry.getFluid(fluid)!=null)
				WGModCompat.addTConSmelteryRecipe(cluster, "block"+name, fluidTemp, fluid, WGConfig.smelteryResultForClusters);
		}
	}

	static void addBlastTrippling(String name)
	{
		if(!OreDictionary.getOres("ingot"+name).isEmpty())
		{
			InfernalBlastfurnaceRecipe r = InfernalBlastfurnaceRecipe.addRecipe(Utilities.copyStackWithSize(OreDictionary.getOres("ingot"+name).get(0),3), "cluster"+name,1, 440,false);
			if(r!=null && !OreDictionary.getOres("nugget"+name).isEmpty())
				r.addBonus(OreDictionary.getOres("nugget"+name).get(0));
		}
	}
	//	private static WGResearchItem getResearchItem(String tag, String category, AspectList researchAspects, int xPos, int yPos, int complexity, ResourceLocation icon)
	//	{
	//		WGResearchItem item = new WGResearchItem(tag, category, researchAspects, xPos, yPos, complexity, icon);
	//		return item;
	//	}
}