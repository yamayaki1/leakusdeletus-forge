package de.yamayaki.leakusdeletus;

import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("leakusdeletus")
public final class LeakusDeletus {
    public static Logger LOGGER;

    public LeakusDeletus() {
        LOGGER = LoggerFactory.getLogger("LeakusDeletus");

        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> {
            return new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true);
        });
    }
}
