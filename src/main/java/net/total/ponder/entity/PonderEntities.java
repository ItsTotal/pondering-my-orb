package net.total.ponder.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.total.ponder.Ponder;

public class PonderEntities {

    public static final EntityType<FushigiProjectileEntity> FUSHIGI = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Ponder.MOD_ID, "fushigi"),
            EntityType.Builder.<FushigiProjectileEntity>create(FushigiProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.35f,0.35f).build("fushigi_proj"));

    public static final EntityType<EvilPonderOrb> EVIL_PONDER_ORB = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Ponder.MOD_ID, "ponder_orb"),
            EntityType.Builder.<EvilPonderOrb>create(EvilPonderOrb::new, SpawnGroup.MISC)
                    .dimensions(1, 1).build("ponder_orb"));

    public static void registerModEntityTypes() {
        Ponder.LOGGER.info("Registering EntityTypes for " + Ponder.MOD_ID);
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(EVIL_PONDER_ORB, EvilPonderOrb.createCubeAttributes().build());
    }
}
