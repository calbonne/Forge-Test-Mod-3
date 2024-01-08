package net.calbone.macmod.block.custom;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class RedstoneRouter extends RedstoneTorchBlock {

    public RedstoneRouter(Properties p_55678_) {
        super(p_55678_);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.valueOf(true)));
    }
}
