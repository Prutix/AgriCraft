package com.infinityraider.agricraft.compat.computer.methods;

import com.infinityraider.agricraft.api.AgriApi;
import com.infinityraider.agricraft.api.stat.IAgriStat;
import com.infinityraider.agricraft.compat.computer.tiles.TileEntityPeripheral;
import com.infinityraider.agricraft.tiles.TileEntityCrop;
import com.infinityraider.agricraft.utility.StackHelper;
import java.util.ArrayList;
import java.util.Optional;

public class MethodGetStats extends MethodBase {

    public MethodGetStats() {
        super("getSpecimenStats", false, true, true);
    }

    @Override
    protected Object[] onMethodCalled(TileEntityCrop crop) throws MethodException {
        return Optional.ofNullable(crop.getSeed())
                .map(s -> s.getStat())
                .filter(IAgriStat::isAnalyzed)
                .map(s -> new Object[]{s.getGrowth(), s.getGain(), s.getStrength()})
                .orElse(null);
    }

    @Override
    protected Object[] onMethodCalled(TileEntityPeripheral peripheral) throws MethodException {
        IAgriStat stats = AgriApi.StatRegistry().get().valueOf(StackHelper.getTag(peripheral.getSpecimen())).orElse(null);
        return stats == null ? null : new Object[]{stats.getGrowth(), stats.getGain(), stats.getStrength()};
    }

    @Override
    protected ArrayList<MethodParameter> getParameters() {
        ArrayList<MethodParameter> pars = new ArrayList<>();
        pars.add(MethodParameter.DIRECTION_OPTIONAL);
        return pars;
    }

}
