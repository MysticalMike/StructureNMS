package cf.mysty.structures;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

import net.minecraft.server.v1_16_R3.BiomeBase;
import net.minecraft.server.v1_16_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_16_R3.ChunkGenerator;
import net.minecraft.server.v1_16_R3.DefinedStructureManager;
import net.minecraft.server.v1_16_R3.GeneratorAccessSeed;
import net.minecraft.server.v1_16_R3.IRegistryCustom;
import net.minecraft.server.v1_16_R3.PlayerChunkMap;
import net.minecraft.server.v1_16_R3.StructureBoundingBox;
import net.minecraft.server.v1_16_R3.StructureGenerator;
import net.minecraft.server.v1_16_R3.StructureStart;
import net.minecraft.server.v1_16_R3.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_16_R3.WorldGenMineshaft;
import net.minecraft.server.v1_16_R3.WorldGenMineshaftConfiguration;
import net.minecraft.server.v1_16_R3.WorldServer;

public class Generator<C extends WorldGenFeatureConfiguration>
{
	public void generate(Location loc) throws NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		// First Section
		
		int chunkX = loc.getChunk().getX();
		int chunkY = (int)(loc.getY() / 16) * 16;
		int chunkZ = loc.getChunk().getZ();
		
		StructureBoundingBox boundingBox = StructureBoundingBox.a();
		
		long seed = loc.getWorld().getSeed();
		
		Method method = StructureGenerator.class.getDeclaredMethod("a", int.class, int.class, StructureBoundingBox.class, int.class, long.class);
		method.setAccessible(true);
		
		StructureStart<C> structureStart = (StructureStart<C>) method.invoke(StructureGenerator.MINESHAFT, chunkX, chunkZ, boundingBox, 0, seed);
		//StructureStart<WorldGenMineshaftConfiguration> structureStart = StructureGenerator.MINESHAFT.a(chunkX, chunkZ, boundingBox, 0, seed);
		
		// Second Section
		
		WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
		IRegistryCustom registryCustom = world.r();
		
		PlayerChunkMap chunkMap = world.getChunkProvider().playerChunkMap;
		ChunkGenerator chunkGenerator = chunkMap.chunkGenerator;
		
		Field definedStructureManager = PlayerChunkMap.class.getDeclaredField("definedStructureManager");
		definedStructureManager.setAccessible(true);
		DefinedStructureManager structureManager = (DefinedStructureManager) definedStructureManager.get(chunkMap);
		
		BiomeBase biomeBase = world.getChunkAt(chunkX, chunkZ).getBiomeIndex().getBiome(chunkX, chunkY, chunkZ); // This might not work
		C configuration = (C) new WorldGenMineshaftConfiguration(seed, WorldGenMineshaft.Type.NORMAL);
		
		structureStart.a(registryCustom, chunkGenerator, structureManager, chunkX, chunkZ, biomeBase, configuration);
		
		// Third Section
		
		Random rand = new Random();
		ChunkCoordIntPair chunkCoordPair = new ChunkCoordIntPair(chunkX, chunkZ);
		
		structureStart.a((GeneratorAccessSeed) world, null, null, rand, StructureBoundingBox.a(), chunkCoordPair);
	}
}
