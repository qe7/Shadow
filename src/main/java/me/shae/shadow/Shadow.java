package me.shae.shadow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.shae.shadow.events.impl.game.InitializeEvent;
import me.shae.shadow.events.impl.game.ShutdownEvent;
import me.shae.shadow.managers.impl.FontManager;
import me.shae.shadow.managers.api.ManagerFactory;
import me.shae.shadow.managers.impl.EventManager;
import me.shae.shadow.managers.impl.ModuleManager;
import me.shae.shadow.utils.FontAdaptor;

@Getter
public final class Shadow {

    @Getter
    private static Shadow instance;

    private final String name, version;

    private final Gson gson;

    public Shadow() {
        instance = this;

        this.name = "Shadow";
        this.version = "1.0.0";

        this.gson = new GsonBuilder().setPrettyPrinting().create();

        ManagerFactory.register(FontManager.class, new FontManager());
        ManagerFactory.register(EventManager.class, new EventManager());
        ManagerFactory.register(ModuleManager.class, new ModuleManager());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ManagerFactory.get(EventManager.class).publishEvent(new ShutdownEvent());
        }));
    }

    public void onInitialize() {
        ManagerFactory.get(EventManager.class).publishEvent(new InitializeEvent());
    }
}
