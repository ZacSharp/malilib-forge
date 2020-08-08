package fi.dy.masa.malilib.config;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class ConfigManagerImpl implements ConfigManager
{
    private final Map<String, ModConfig> configHandlers = new HashMap<>();

    ConfigManagerImpl()
    {
    }

    @Override
    public void registerConfigHandler(ModConfig handler)
    {
        final String modId = handler.getModId();

        if (this.configHandlers.containsKey(modId))
        {
            throw new IllegalArgumentException("Tried to override an existing config handler for mod ID '" + modId + "'");
        }

        handler.getConfigOptionCategories().forEach((category) -> category.getConfigOptions().forEach((config) -> config.setModId(modId)));

        this.configHandlers.put(modId, handler);
    }

    @Override
    @Nullable
    public ModConfig getConfigHandler(String modId)
    {
        return this.configHandlers.get(modId);
    }

    @Override
    public boolean saveConfigsIfChanged(String modId)
    {
        ModConfig handler = this.configHandlers.get(modId);

        if (handler != null)
        {
            return handler.onConfigsPotentiallyChanged();
        }

        return false;
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public List<ModConfig> getAllModConfigs()
    {
        ArrayList<ModConfig> list = new ArrayList<>(this.configHandlers.values());
        list.sort(Comparator.comparing(ModConfig::getModName));
        return list;
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void loadAllConfigs()
    {
        for (ModConfig handler : this.configHandlers.values())
        {
            handler.load();
        }
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void saveAllConfigs()
    {
        for (ModConfig handler : this.configHandlers.values())
        {
            handler.saveIfDirty();
        }
    }
}
